package mh;

import mh.tipos.*;
import mh.algoritmos.*;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class P4 {

    public static final int[] SEED = {111, 222, 333, 123, 321};
    public static final int NUMH = 30;
    public static final double ALPHA = 1.0;
    public static final double BETA = 2.0;
    public static final double RHO = 0.1;
    public static final double ELITISMO = 15.0;
    public static final int[] T = {1, 1};
    public static final int[] MAXITER = {5, 5};
    public static final String[] filename = {"lin105", "kroA100"};
//    public static final String[] filename = {"ch130", "a280"};
    public static Hormiga[] solOPT, solG;
    public static SistemaHormigas[][] solSH;
    public static SistemaElitista[][] solSHE;
    public static Lista<Nodo> listaCiu;
    public static int CIU;
    public static Matriz distancias;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //RESULTADOS
        solOPT = new Hormiga[T.length];
        solG = new Hormiga[T.length];
        solSH = new SistemaHormigas[T.length][SEED.length];
        solSHE = new SistemaElitista[T.length][SEED.length];

        //PROBLEMAS
        for (int t = 0; t < T.length; t++) {
            //MATRIZ DISTANCIAS
            listaCiu = Parser.leerCiu(filename[t] + ".tsp");
            CIU = listaCiu.size();
            distancias = new Matriz(CIU, CIU);
            distancias.construir(listaCiu);

            //SOLUCION OPTIMA
            solOPT[t] = new Hormiga();
            solOPT[t].cerrados = Parser.leerTour(filename[t] + ".opt.tour");
            solOPT[t].coste = distancias.costeCamino(solOPT[t].cerrados);
            solOPT[t].eval = 1;
            System.out.println("Optima - " + filename[t] + ".opt.tour");
            System.out.println(solOPT[t].coste + "\t" + solOPT[t].eval);
            System.out.println(solOPT[t] + "\n");

            //mostrar grafo
            Grafo g = new Grafo(solOPT[t].cerrados);
            g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            g.setBounds(200, 350, 800, 400);
            g.setTitle(filename[t] + ".tsp - Optima");
            g.setVisible(true);

            //SOLUCION GREEDY
            solG[t] = new Hormiga();
            solG[t].cerrados = Greedy.solG();
            solG[t].coste = distancias.costeCamino(solG[t].cerrados);
            solG[t].eval = 1;
            System.out.println("Greedy - " + filename[t] + ".tsp");
            System.out.println(solG[t].coste + "\t" + solG[t].eval);
            System.out.println(solG[t] + "\n");

            //mostrar grafo
            Grafo g1 = new Grafo(solG[t].cerrados);
            g1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            g1.setBounds(200, 350, 800, 400);
            g1.setTitle(filename[t] + ".tsp - Greedy");
            g1.setVisible(true);

            int i = 2;
            //SH
            System.out.println("SH - " + filename[t] + ".tsp - " + T[t] + " min");
//            for (int i = 0; i < SEED.length; i++) {
            solSH[t][i] = new SistemaHormigas(SEED[i], t);
            System.out.println(solSH[t][i].elite.coste + "\t" + solSH[t][i].eval);
            if (i == 2) {
                //mostrar grafo
                Grafo g2 = new Grafo(solSH[t][i].elite.cerrados);
                g2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g2.setBounds(200, 350, 800, 400);
                g2.setTitle(filename[t] + ".tsp - SH");
                g2.setVisible(true);
//                }
            }
            System.out.println("");

            //SHE
            System.out.println("SHE - " + filename[t] + ".tsp - " + T[t] + " min");
//            for (int i = 0; i < SEED.length; i++) {
            solSHE[t][i] = new SistemaElitista(SEED[i], t);
            System.out.println(solSHE[t][i].elite.coste + "\t" + solSHE[t][i].eval);
            if (i == 2) {
                //mostrar grafo
                Grafo g3 = new Grafo(solSHE[t][i].elite.cerrados);
                g3.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g3.setBounds(200, 350, 800, 400);
                g3.setTitle(filename[t] + ".tsp - SHE");
                g3.setVisible(true);
            }
//            }
            System.out.println("");

            System.out.println("\n---------------------\n");
        }

        //GUARDAR
//        Parser.escribir("RESULTADOS.txt");
    }
}
