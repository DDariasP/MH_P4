package mh.algoritmos;

import mh.P4;
import mh.tipos.*;
import java.util.Random;

/**
 *
 * @author diego
 */
public class SistemaElitista {

    public final int SEED;
    public Random rand;
    public final double TAU0;
    public MDouble TAU;
    public Hormiga[] ant;
    public Hormiga elite;
    public int eval;
    public int iter;
    public Lista convergencia;

    public SistemaElitista(int s, int t) {
        SEED = s;
        rand = new Random(SEED);
        TAU0 = 1.0 / (P4.CIU * P4.solG[t].mejor.coste);
        TAU = new MDouble(P4.CIU, P4.CIU, TAU0);
        ant = new Hormiga[P4.NUMH];
        elite = Hormiga.NULA;
        eval = 0;
        iter = 0;
        convergencia = new Lista<Integer>();
        SHE(t);
    }

    public final void SHE(int t) {

        while (true) {
            //NODO INICIAL
            for (int i = 0; i < P4.NUMH; i++) {
                int pos = rand.nextInt(P4.CIU);
                Nodo inicial = P4.listaCiu.get(pos);
                ant[i] = new Hormiga(i, inicial, P4.listaCiu);
            }

            //CONSTRUIR SOLUCIONES
            for (int i = 0; i < P4.NUMH; i++) {
                for (int j = 1; j < P4.CIU; j++) {
                    ant[i].transicion(TAU, rand);
                }
                ant[i].coste = P4.distancias.costeCamino(ant[i].cerrados);
                eval++;
                ant[i].eval = eval;
            }

            //MEJOR ACTUAL
            Hormiga actual = Hormiga.mejor(ant);

            //ACTUALIZAR FEROMONA
            TAU = Hormiga.actualizacion(ant, TAU, iter, elite);

            //MEJOR GLOBAL
            if (elite.coste > actual.coste) {
                elite = actual;
            }

            //CONVERGENCIA
            if (iter % P4.RATIO[t] == 0) {
                convergencia.add(elite.coste);
                System.out.println("iter=" + iter);
            }

            //ITERACIONES
            iter++;
            if (iter > P4.MAXITER[t]) {
                break;
            }
        }
    }

}
