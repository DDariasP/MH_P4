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
    public Hormiga ant;

    public Greedy() {
        int pos = rand.nextInt(P4.CIU);
        Nodo inicial = P4.listaCiu.get(pos);
        ant = new Hormiga(SEED, inicial, P4.listaCiu);

        while (ant.abiertos.size() > 0) {
            Nodo actual = ant.cerrados.tail();
            int posibles = ant.abiertos.size();
            Nodo siguiente = ant.abiertos.get(0);
            int costeS = P4.distancias.m[actual.id][siguiente.id];

            for (int i = 1; i < posibles; i++) {
                Nodo candidato = ant.abiertos.get(i);
                int costeC = P4.distancias.m[actual.id][candidato.id];
                if (costeS > costeC) {
                    siguiente = candidato;
                    costeS = costeC;
                }
            }
            ant.abiertos.remove(siguiente);
            ant.cerrados.add(siguiente);
        }

        ant.coste = P4.distancias.costeCamino(ant.cerrados);
        ant.eval = 1;
    }

}
