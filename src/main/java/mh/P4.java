package mh;

import mh.tipos.*;
import mh.algoritmos.*;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class P4 {

    public static final int[] SEED = {111, 222, 333, 123, 321};
    public static final int NUMH = 30;
    public static final int ELITISMO = 15;
    public static final int[] T = {3, 8};
    public static Lista<Nodo> listaCiu;
    public static int ciu;
    public static Matriz distancias;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        listaCiu = Parser.leerCiu("ch130.tsp");
        ciu = listaCiu.size();
        distancias = new Matriz(ciu, ciu);
        distancias.construir(listaCiu);

        Lista<Integer> solopt = Parser.leerTour("ch130.opt.tour");
        int opt = distancias.costeCamino(solopt);
        

    }
}
