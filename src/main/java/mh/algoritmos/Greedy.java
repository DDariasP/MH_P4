package mh.algoritmos;

import mh.P4;
import mh.tipos.*;

/**
 *
 * @author diego
 */
public class Greedy {

    public final int tam;
    public Hormiga[] ant;
    public Hormiga mejor;
    public int medio, peor;

    public Greedy() {

        tam = P4.listaCiu.size();
        ant = new Hormiga[tam];
        peor = Integer.MIN_VALUE;
        mejor = new Hormiga();
        mejor.coste = Integer.MAX_VALUE;

        for (int i = 0; i < tam; i++) {

            Nodo inicial = P4.listaCiu.get(i);
            ant[i] = new Hormiga(i, inicial, P4.listaCiu);

            while (ant[i].abiertos.size() > 0) {
                Nodo actual = ant[i].cerrados.tail();
                int posibles = ant[i].abiertos.size();
                Nodo siguiente = ant[i].abiertos.get(0);
                int costeS = P4.distancias.m[actual.id][siguiente.id];

                for (int j = 1; j < posibles; j++) {
                    Nodo candidato = ant[i].abiertos.get(j);
                    int costeC = P4.distancias.m[actual.id][candidato.id];
                    if (costeS > costeC) {
                        siguiente = candidato;
                        costeS = costeC;
                    }
                }

                ant[i].abiertos.remove(siguiente);
                ant[i].cerrados.add(siguiente);
            }

            ant[i].coste = P4.distancias.costeCamino(ant[i].cerrados);
            if (mejor.coste > ant[i].coste) {
                mejor = ant[i];
            }
            if (peor < ant[i].coste) {
                peor = ant[i].coste;
            }
        }

        int sum = 0;
        for (int i = 0; i < tam; i++) {
            sum = sum + ant[i].coste;
        }
        medio = sum / tam;

    }

}
