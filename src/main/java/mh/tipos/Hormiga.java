package mh.tipos;

import mh.P4;
import java.util.Random;

/**
 *
 * @author diego
 */
public class Hormiga {

    public static final Hormiga NULA = new Hormiga();
    public final String id;
    public Lista<Nodo> abiertos, cerrados;
    public int coste;
    public int eval;

    public Hormiga() {
        id = "HN";
        abiertos = new Lista<>();
        cerrados = new Lista<>();
        coste = Integer.MAX_VALUE;
        eval = Integer.MAX_VALUE;
    }

    public Hormiga(int a, Nodo b, Lista<Nodo> c) {
        id = "H" + a;
        abiertos = new Lista<>(c);
        cerrados = new Lista<>();
        abiertos.remove(b);
        cerrados.add(b);
        coste = 0;
        eval = 0;
    }

    public void transicion(MDouble TAU, Random rand) {
        Nodo actual = cerrados.tail();
        int posibles = abiertos.size();
        double[] num = new double[posibles];
        double sum = 0.0;

        for (int i = 0; i < posibles; i++) {
            int pos = abiertos.get(i).id;
            double a = Math.pow(TAU.m[actual.id][pos], P4.ALPHA);
            double b = Math.pow(P4.ETA.m[actual.id][pos], P4.BETA);
            num[i] = a * b;
            sum = sum + num[i];
        }

        double[] roulette = new double[posibles];
        for (int i = 0; i < posibles; i++) {
            roulette[i] = num[i] / sum;
        }

        double random = rand.nextDouble();
        double acum = 0.0;
        boolean encontrado = false;
        int pos = 0;
        while (!encontrado && pos < posibles) {
            acum = acum + roulette[pos];
            if (random <= acum) {
                encontrado = true;
            }
            pos++;
        }
        pos--;

        Nodo siguiente = abiertos.get(pos);
        abiertos.remove(siguiente);
        cerrados.add(siguiente);
    }

    public static MDouble actualizacion(Hormiga[] m, MDouble TAU, double iter) {
        for (int x = 0; x < P4.CIU; x++) {
            for (int y = 0; y < P4.CIU; y++) {
                if (x != y) {
                    double evapora = (1.0 - P4.RHO) * TAU.m[x][y];

                    double aporte = 0.0;
                    for (int i = 0; i < P4.NUMH; i++) {
                        if (Nodo.arco(m[i].cerrados, x, y) || Nodo.arco(m[i].cerrados, y, x)) {
                            aporte = aporte + (1.0 / m[i].coste);
                        }
                    }
                    
                    TAU.m[x][y] = evapora + aporte;
                }
            }
        }
        return TAU;
    }

    public static MDouble actualizacion(Hormiga[] m, MDouble TAU, double iter, Hormiga elite) {
        for (int x = 0; x < P4.CIU; x++) {
            for (int y = 0; y < P4.CIU; y++) {
                if (x != y) {
                    double evapora = (1.0 - P4.RHO) * TAU.m[x][y];

                    double aporte = 0.0;
                    for (int i = 0; i < P4.NUMH; i++) {
                        if (Nodo.arco(m[i].cerrados, x, y) || Nodo.arco(m[i].cerrados, y, x)) {
                            aporte = aporte + (1.0 / m[i].coste);
                        }
                    }

                    double refuerzo = 0.0;
                    if (Nodo.arco(elite.cerrados, x, y) || Nodo.arco(elite.cerrados, y, x)) {
                        refuerzo = P4.ELITISMO * (1.0 / elite.coste);
                    }

                    TAU.m[x][y] = evapora + aporte + refuerzo;
                }
            }
        }
        return TAU;
    }

    public static Hormiga mejor(Hormiga[] m) {
        Hormiga mejor = Hormiga.NULA;
        for (int i = 0; i < m.length; i++) {
            if (mejor.coste > m[i].coste) {
                mejor = m[i];
            }
        }
        return mejor;
    }

    @Override
    public String toString() {
        String output = cerrados.toString();
        return output;
    }

}
