package mh.algoritmos;

import mh.P4;
import mh.tipos.*;
import java.util.Random;

/**
 *
 * @author diego
 */
public class SistemaHormigas {

    public final int SEED;
    public Random rand;
    public final double TAU0;
    public Tabla TAU;
    public Hormiga[] m;
    public Hormiga elite;
    public int eval;
    public Lista convergencia;

    public SistemaHormigas(int s, int t) {
        SEED = s;
        rand = new Random(SEED);
        TAU0 = 1.0 / (P4.CIU * P4.solG[t].coste);
        TAU = new Tabla(P4.CIU, P4.CIU, TAU0);
        m = new Hormiga[P4.NUMH];
        convergencia = new Lista<Integer>();
        SH(t);
    }

    public final void SH(int t) {
        elite = Hormiga.NULA;
        eval = 0;

//        int limite = P4.T[t] * 60;
//        long endTime = System.currentTimeMillis() + limite * 1000;
        int iter = 0;
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
            TAU = Hormiga.actualizacion(m, TAU, iter);

            //MEJOR GLOBAL
            if (elite.coste > actual.coste) {
                elite = actual;
            }

            //CONVERGENCIA
            if (iter % P4.RATIO[t] == 0) {
                convergencia.add(actual.coste);
                System.out.println("iter=" + iter);
            }

            //ITERACIONES
            iter++;
            if (iter > P4.MAXITER[t]) {
                //TIEMPO LIMITE
//            if (System.currentTimeMillis() > endTime) {
                break;
            }
        }
    }

}
