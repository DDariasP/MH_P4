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
    public static final int[] MAXITER = {100, 100};
    public static final int[] RATIO = {4, 4};
    public static final String[] filename = {"ch130", "a280"};
    public static Hormiga[] solOPT, solG;
    public static SistemaHormigas[][] solSH;
    public static SistemaElitista[][] solSHE;
    public static Lista<Nodo> listaCiu;
    public static int CIU;
    public static Matriz distancias;
    public static Tabla ETA;

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
            ETA = new Tabla(CIU, CIU);
            ETA.construir(distancias);

            //SOLUCION OPTIMA
            solOPT[t] = new Hormiga();
            solOPT[t].cerrados = Parser.leerTour(filename[t] + ".opt.tour");
            solOPT[t].coste = distancias.costeCamino(solOPT[t].cerrados);
            solOPT[t].eval = 1;
            System.out.println("Optima - " + filename[t] + ".opt.tour");
            System.out.println(solOPT[t].coste + "\t" + solOPT[t].eval);
            System.out.println(solOPT[t] + "\n");
            //CAMINO
            Grafo gOPT = new Grafo(solOPT[t].cerrados);
            gOPT.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            gOPT.setBounds(200, 350, 800, 400);
            gOPT.setTitle(filename[t] + ".tsp - Optima");
            gOPT.setVisible(true);

            //SOLUCION GREEDY
            Greedy G = new Greedy();
            solG[t] = G.m;
            System.out.println("Greedy - " + filename[t] + ".tsp");
            System.out.println(solG[t].coste + "\t" + solG[t].eval);
            System.out.println(solG[t] + "\n");
            //CAMINO
            Grafo gG = new Grafo(solG[t].cerrados);
            gG.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            gG.setBounds(200, 350, 800, 400);
            gG.setTitle(filename[t] + ".tsp - Greedy");
            gG.setVisible(true);

            int i = 2;
            //SH
            System.out.println("SH - " + filename[t] + ".tsp - " + MAXITER[t] + " iter");
//            System.out.println("SH - " + filename[t] + ".tsp - " + T[t] + " min");
//            for (int i = 0; i < SEED.length; i++) {
            solSH[t][i] = new SistemaHormigas(SEED[i], t);
            System.out.println(solSH[t][i].elite.coste + "\t" + solSH[t][i].eval);
            if (i == 2) {
                //CAMINO
                Grafo gSH = new Grafo(solSH[t][i].elite.cerrados);
                gSH.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                gSH.setBounds(200, 350, 800, 400);
                gSH.setTitle(filename[t] + ".tsp - SH");
                gSH.setVisible(true);
                //CONVERGENCIA
                Grafica cSH = new Grafica(solSH[t][i].convergencia, t);
                cSH.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                cSH.setBounds(200, 350, 800, 400);
                cSH.setTitle(filename[t] + ".tsp - SH");
                cSH.setVisible(true);
            }
//            }
            System.out.println("");

            //SHE
            System.out.println("SHE - " + filename[t] + ".tsp - " + MAXITER[t] + " iter");
//            System.out.println("SHE - " + filename[t] + ".tsp - " + T[t] + " min");
//            for (int i = 0; i < SEED.length; i++) {
            solSHE[t][i] = new SistemaElitista(SEED[i], t);
            System.out.println(solSHE[t][i].elite.coste + "\t" + solSHE[t][i].eval);
            if (i == 2) {
                //CAMINO
                Grafo gSHE = new Grafo(solSHE[t][i].elite.cerrados);
                gSHE.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                gSHE.setBounds(200, 350, 800, 400);
                gSHE.setTitle(filename[t] + ".tsp - SHE");
                gSHE.setVisible(true);
                //CONVERGENCIA
                Grafica cSHE = new Grafica(solSHE[t][i].convergencia, t);
                cSHE.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                cSHE.setBounds(200, 350, 800, 400);
                cSHE.setTitle(filename[t] + ".tsp - SHE");
                cSHE.setVisible(true);
            }
//            }
            System.out.println("");

            System.out.println("\n---------------------\n");
        }

        //GUARDAR
        Parser.escribir("RESULTADOS.txt");
    }
}
