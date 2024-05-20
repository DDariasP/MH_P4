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
    public Tabla TAU;
    public Hormiga[] m;
    public Hormiga elite;
    public int eval;

    public SistemaElitista(int s, int t) {
        SEED = s;
        rand = new Random(SEED);
        TAU0 = 1.0 / (P4.CIU * P4.solG[t].coste);
        TAU = new Tabla(P4.CIU, P4.CIU, TAU0);
        m = new Hormiga[P4.NUMH];
        SHE(t);
    }

    public final void SHE(int t) {
        elite = Hormiga.NULA;
        eval = 0;

//        int limite = P4.T[t] * 60;
//        long endTime = System.currentTimeMillis() + limite * 1000;
//        double iter = 1.0;
        int iter = 1;
        while (true) {
            //NODO INICIAL
            for (int i = 0; i < P4.NUMH; i++) {
                int pos = rand.nextInt(P4.CIU);
                Nodo inicial = P4.listaCiu.get(pos);
                m[i] = new Hormiga(i, inicial, P4.listaCiu);
            }

            //CONSTRUIR SOLUCIONES
            for (int i = 0; i < P4.NUMH; i++) {
                for (int j = 1; j < P4.CIU; j++) {
                    m[i].transicion(TAU, rand);
                }
                m[i].coste = P4.distancias.costeCamino(m[i].cerrados);
                eval++;
                m[i].eval = eval;
            }

            //MEJOR ACTUAL
            Hormiga actual = Hormiga.mejor(m);

            //ACTUALIZAR FEROMONA
            TAU = Hormiga.actualizacion(m, TAU, iter, elite);

            //MEJOR GLOBAL
            if (elite.coste > actual.coste) {
                elite = actual;
            }

            if (iter % 100 == 0) {
                System.out.println(actual.coste + "\titer=" + iter);
                System.out.println(actual);

            }

            //TIEMPO LIMITE
            iter++;
            if (iter >= P4.MAXITER[t]) {
//            if (System.currentTimeMillis() > endTime) {
                break;
            }
        }
    }

}
