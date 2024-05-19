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

    public static Lista<Nodo> solG() {
        Matriz m = P4.distancias;
        Lista<Nodo> abiertos = new Lista<>(P4.listaCiu);
        Lista<Nodo> cerrados = new Lista<>();
        int ini = rand.nextInt(P4.CIU);
        Nodo inicial = abiertos.get(ini);
        abiertos.remove(ini);
        cerrados.add(inicial);
        Lista<Nodo> candidatos;

        int n = abiertos.size();
        Nodo actual = inicial;
        while (n > 0) {
            candidatos = new Lista<>();
            for (int i = 0; i < n; i++) {
                Nodo tmp = abiertos.get(i);
                Nodo candidato = new Nodo(tmp, m.s[actual.id][tmp.id]);
                candidatos.add(candidato);
            }
            Nodo.sort(candidatos);
            Nodo siguiente = candidatos.get(0);
            abiertos.remove(siguiente);
            n = abiertos.size();
            cerrados.add(siguiente);
        }

        return cerrados;
    }

}
