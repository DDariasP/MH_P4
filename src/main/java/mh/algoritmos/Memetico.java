package mh.algoritmos;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Random;
import static javax.swing.WindowConstants.*;
import mh.*;
import mh.tipos.*;

/**
 *
 * @author diego
 */
public class Memetico {

    public final int SEED;
    public Random rand;
    public Cromosoma[] cromMM;
    public Lista[] convergencia;
    public final int optG;
    public final double optP;
    public final int id;
    public final String nombre;
    public final Color color;
    public int gen, eval, maxeval, evalBL, maxBL;
    public Lista<Cromosoma> cacheOpt;

    public Memetico(int a, String b, int c, double d, int e) {
        SEED = a;
        rand = new Random(SEED);
        cromMM = new Cromosoma[P4.NUMP];
        convergencia = new Lista[P4.NUMP];
        for (int i = 0; i < P4.NUMP; i++) {
            convergencia[i] = new Lista<Integer>();
        }
        optG = c;
        optP = d;
        nombre = b + optG + "-" + optP;
        id = e;
        switch (id) {
            case 0:
                color = Color.GREEN;
                break;
            case 1:
                color = Color.CYAN;
                break;
            case 2:
                color = Color.MAGENTA;
                break;
            case 3:
                color = Color.YELLOW;
                break;
            default:
                throw new AssertionError();
        }
    }

    public void ejecutarMM() {
        for (int i = 0; i < P4.NUMP; i++) {
            double time = System.currentTimeMillis();
            cromMM[i] = MM(i);
            time = ((System.currentTimeMillis() - time) / 6000);
            String t = new DecimalFormat("#.00").format(time);
            System.out.println(t + " seg");
            System.out.println("lastGen=" + gen);
            System.out.println("lastEval=" + eval);
            System.out.println("lastBL=" + evalBL);
            System.out.println("coste=" + cromMM[i].coste);
            System.out.println("eval=" + cromMM[i].eval);
            System.out.println("evalBL=" + cromMM[i].evalBL);
            if (i == 2 && SEED == 333) {
                Grafica g = new Grafica(convergencia[i], nombre, color, P4.RATIOMM[id]);
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 400);
                g.setTitle(nombre + " - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Cromosoma MM(int tamP) {
        int[] P = P4.P[tamP];
        int ciu = P[0];
        maxBL = P4.BL[id] * ciu;
        evalBL = 0;
        maxeval = P4.MAXMM[id] * ciu;
        eval = 0;
        gen = 0;
        Lista listaGen = P4.listaGen.get(tamP);
        Matriz listaDist = P4.listaDist.get(tamP);
        Cromosoma tmp;
        cacheOpt = new Lista<>();

        //INICIALIZACION
        Lista<Cromosoma> inicial = new Lista<>();
        tmp = Cromosoma.genGreedy(listaGen, listaDist, rand);
        tmp.coste = Cromosoma.funCoste(tmp, listaDist);
        eval++;
        tmp.eval = eval;
        inicial.add(tmp);
        Cromosoma elite = tmp;
        convergencia[tamP].add(elite.coste);

        for (int i = 1; i < P4.POBLACION; i++) {
            tmp = Cromosoma.genRandom(listaGen, rand);
            tmp.coste = Cromosoma.funCoste(tmp, listaDist);
            eval++;
            tmp.eval = eval;
            inicial.add(tmp);
        }

        //GENERACIONES
        Lista<Cromosoma> actual = inicial;
        while (eval <= maxeval - 2) {
            //SELECCION Y RECOMBINACION
            Lista<Cromosoma> siguiente = new Lista<>();
            int descendientes = 0;
            while (eval <= maxeval - 2 && descendientes <= P4.POBLACION - 2) {
                Cromosoma padre1 = Cromosoma.torneo(P4.TORNEO, actual, listaDist, rand);
                Cromosoma padre2 = Cromosoma.torneo(P4.TORNEO, actual, listaDist, rand);
                double cruce = rand.nextDouble();
                if (cruce >= 1.0 - P4.CRUCE) {
                    Cromosoma[] hijos = new Cromosoma[2];
                    if (id == 0 || id == 1) {
                        Cromosoma.cruceOX(padre1, padre2, hijos, rand);
                    } else {
                        Cromosoma.cruceAEX(padre1, padre2, hijos, rand);
                    }
                    hijos[0].coste = Cromosoma.funCoste(hijos[0], listaDist);
                    eval++;
                    hijos[0].eval = eval;
                    siguiente.add(hijos[0]);
                    descendientes++;
                    hijos[1].coste = Cromosoma.funCoste(hijos[1], listaDist);
                    eval++;
                    hijos[1].eval = eval;
                    siguiente.add(hijos[1]);
                    descendientes++;
                } else {
                    siguiente.add(padre1);
                    descendientes++;
                    siguiente.add(padre2);
                    descendientes++;
                }
            }
            //REEMPLAZAMIENTO
            if (descendientes == P4.POBLACION) {
                Cromosoma.sort(actual);
                Cromosoma.sort(siguiente);
                for (int i = 0; i < P4.ELITISMO; i++) {
                    siguiente.remove(siguiente.size() - 1);
                }
                for (int i = 0; i < P4.ELITISMO; i++) {
                    tmp = actual.get(i);
                    siguiente.add(tmp);
                }
                Cromosoma.sort(siguiente);
                actual = siguiente;
                gen++;
                //OPTIMIZACION
                if (gen % optG == 0) {
                    optimizacionAM(actual, listaDist);
                    Cromosoma.sort(actual);
                }
                //RESULTADO
                if (gen % P4.RATIOMM[id] == 0) {
                    convergencia[tamP].add(actual.get(0).coste);
                }
                if (elite.coste > actual.get(0).coste) {
                    elite = actual.get(0);
                }
            } else {
                convergencia[tamP].add(elite.coste);
            }
        }

        return elite;
    }

    public void optimizacionAM(Lista<Cromosoma> poblacion, Matriz listaDist) {
        boolean[] disponibles = new boolean[P4.POBLACION];
        for (int i = 0; i < P4.POBLACION; i++) {
            disponibles[i] = true;
        }
        for (int i = 0; i < P4.POBLACION; i++) {
            double opt = rand.nextDouble();
            if (opt >= 1 - optP) {
                int pos = -1;
                while (pos == -1 || !disponibles[pos]) {
                    pos = rand.nextInt(P4.POBLACION);
                }
                disponibles[pos] = false;
                Cromosoma tmp = poblacion.get(pos);
                if (!cacheOpt.contains(tmp)) {
                    if (cacheOpt.size() == P4.CACHE) {
                        cacheOpt.remove(0);
                    }
                    cacheOpt.add(tmp);
                    tmp = optBL(tmp, listaDist);
                    poblacion.replace(pos, tmp);
                    if (cacheOpt.size() == P4.CACHE) {
                        cacheOpt.remove(0);
                    }
                    cacheOpt.add(tmp);
                }
            }
        }
    }

    private Cromosoma optBL(Cromosoma inicial, Matriz listaDist) {
        int iter = 0;
        inicial.coste = Cromosoma.funCoste(inicial, listaDist);
        iter++;
        evalBL++;
        inicial.evalBL = evalBL;
        Cromosoma actual = inicial;
        Cromosoma siguiente;
        while (iter < maxBL) {
            siguiente = Cromosoma.gen2opt(actual, rand);
            siguiente.coste = Cromosoma.funCoste(siguiente, listaDist);
            siguiente.eval = inicial.eval;
            iter++;
            evalBL++;
            siguiente.evalBL = evalBL;
            if (actual.coste > siguiente.coste) {
                actual = siguiente;
            }
        }
        return actual;
    }
}
