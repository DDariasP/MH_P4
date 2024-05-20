package mh.algoritmos;

import mh.P4;
import mh.tipos.*;
import java.util.Random;

/**
 *
 * @author diego
 */
public class Greedy {

    public static final int SEED = 333;
    public static Random rand = new Random(SEED);
    public Hormiga m;

    public Greedy() {
        int pos = rand.nextInt(P4.CIU);
        Nodo inicial = P4.listaCiu.get(pos);
        m = new Hormiga(SEED, inicial, P4.listaCiu);

        while (m.abiertos.size() > 0) {
            Nodo actual = m.cerrados.tail();
            int posibles = m.abiertos.size();
            Nodo siguiente = m.abiertos.get(0);
            int costeS = P4.distancias.s[actual.id][siguiente.id];

            for (int i = 1; i < posibles; i++) {
                Nodo candidato = m.abiertos.get(i);
                int costeC = P4.distancias.s[actual.id][candidato.id];
                if (costeS > costeC) {
                    siguiente = candidato;
                    costeS = costeC;
                }
            }
            m.abiertos.remove(siguiente);
            m.cerrados.add(siguiente);
        }

        m.coste = P4.distancias.costeCamino(m.cerrados);
        m.eval = 1;
    }

}
