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

    public void transicion(double[][] TAU, Random rand) {
        Nodo actual = cerrados.tail();
        int posibles = abiertos.size();
        double sum = 0.0;
        double[] ruleta = new double[posibles];
        for (int i = 0; i < posibles; i++) {
            int pos = abiertos.get(i).id;
            double a = Math.pow(TAU[actual.id][pos], P4.ALPHA);
            double ETA = 1.0 / TAU[actual.id][pos];
            double b = Math.pow(ETA, P4.BETA);
            double num = a * b;
            ruleta[i] = num;
            sum = sum + num;
        }
        ruleta[0] = ruleta[0] / (sum - ruleta[0]);
        for (int i = 1; i < posibles; i++) {
            double num = ruleta[i];
            double den = sum - num;
            double P = num / den;
            ruleta[i] = ruleta[i - 1] + P;
        }

        double r = rand.nextDouble();
        int iter = 0;
        boolean encontrado = false;
        while (!encontrado && iter < posibles) {
            if (r < ruleta[iter]) {
                encontrado = true;
            }
            iter++;
        }
        Nodo siguiente = abiertos.get(iter - 1);
        abiertos.remove(siguiente);
        cerrados.add(siguiente);
    }

    public static double[][] actualizacion(Hormiga[] m, double[][] TAU, double iter) {
        for (int x = 0; x < P4.CIU; x++) {
            for (int y = 0; y < P4.CIU; y++) {

                double evapora = (1.0 - P4.RHO) * TAU[x][y] * (iter - 1.0);

                double aporte = 0.0;
                for (int i = 0; i < P4.NUMH; i++) {
                    if (Nodo.arco(m[i].cerrados, x, y)) {
                        aporte = aporte + (1.0 / m[i].coste);
                    }
                }

                TAU[x][y] = evapora + aporte;
            }
        }
        return TAU;
    }

    public static double[][] actualizacion(Hormiga[] m, double[][] TAU, double iter, Hormiga elite) {
        for (int x = 0; x < P4.CIU; x++) {
            for (int y = 0; y < P4.CIU; y++) {

                double evapora = (1.0 - P4.RHO) * TAU[x][y] * (iter - 1.0);

                double aporte = 0.0;
                for (int i = 0; i < P4.NUMH; i++) {
                    if (Nodo.arco(m[i].cerrados, x, y)) {
                        aporte = aporte + (1.0 / m[i].coste);
                    }
                }

                double refuerzo = 0.0;
                if (Nodo.arco(elite.cerrados, x, y)) {
                    refuerzo = P4.ELITISMO * (1.0 / elite.coste);
                }

                TAU[x][y] = evapora + aporte + refuerzo;
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
