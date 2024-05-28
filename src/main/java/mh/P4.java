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
    public static final int[] MAXITER = {10, 10};
    public static final int[] RATIO = {1, 1};
    public static final String[] P = {"ch130", "a280"};
    public static Hormiga[] solOPT;
    public static Greedy[] solG;
    public static SistemaHormigas[][] solSH;
    public static SistemaElitista[][] solSHE;
    public static Lista<Integer>[] convSH;
    public static Lista<Integer>[] convSHE;
    public static Lista<Nodo> listaCiu;
    public static int CIU;
    public static MInteger distancias;
    public static MDouble ETA;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //RESULTADOS
        solOPT = new Hormiga[P.length];
        solG = new Greedy[P.length];
        solSH = new SistemaHormigas[P.length][SEED.length];
        solSHE = new SistemaElitista[P.length][SEED.length];

        //PROBLEMAS
        for (int t = 0; t < P.length; t++) {
            //CONVERGENCIA
            convSH = new Lista[SEED.length];
            convSHE = new Lista[SEED.length];

            //MATRIZ DISTANCIAS
            listaCiu = Parser.leerCiu(P[t] + ".tsp");
            CIU = listaCiu.size();
            distancias = new MInteger(CIU, CIU);
            distancias.construir(listaCiu);
            ETA = new MDouble(CIU, CIU);
            ETA.construir(distancias);

            //SOLUCION OPTIMA
            solOPT[t] = new Hormiga();
            solOPT[t].cerrados = Parser.leerTour(P[t] + ".opt.tour");
            solOPT[t].coste = distancias.costeCamino(solOPT[t].cerrados);
            System.out.println("Optima - " + P[t] + ".opt.tour");
            System.out.println(solOPT[t].coste);
            System.out.println(solOPT[t] + "\n");
            //CAMINO
            Grafo gOPT = new Grafo(solOPT[t].cerrados);
            gOPT.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            gOPT.setBounds(200, 350, 800, 400);
            gOPT.setTitle(P[t] + ".tsp - Optima");
            gOPT.setVisible(true);

            //SOLUCION GREEDY
            solG[t] = new Greedy();
            System.out.println("Greedy - " + P[t] + ".tsp");
            System.out.println(solG[t].peor + "\tpeor");
            System.out.println(solG[t].medio + "\tmedio");
            System.out.println(solG[t].mejor.coste + "\tmejor\n");
            //CAMINO
            Grafo gG = new Grafo(solG[t].mejor.cerrados);
            gG.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            gG.setBounds(200, 350, 800, 400);
            gG.setTitle(P[t] + ".tsp - Greedy");
            gG.setVisible(true);

            //SH
            System.out.println("SH - " + P[t] + ".tsp - " + MAXITER[t] + " iter");
            for (int i = 0; i < SEED.length; i++) {
                solSH[t][i] = new SistemaHormigas(SEED[i], t);
                System.out.println(solSH[t][i].elite.coste + "\t" + solSH[t][i].elite.eval);
                convSH[i] = solSH[t][i].convergencia;
            }
            //CAMINO
            int cmSH = Integer.MAX_VALUE;
            int posSH = -1;
            for (int i = 0; i < SEED.length; i++) {
                if (cmSH > solSH[t][i].elite.coste) {
                    posSH = i;
                }
            }
            Grafo gSH = new Grafo(solSH[t][posSH].elite.cerrados);
            gSH.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            gSH.setBounds(200, 350, 800, 400);
            gSH.setTitle(P[t] + ".tsp - SH");
            gSH.setVisible(true);
            //CONVERGENCIA
            Grafica cSH = new Grafica(convSH, t);
            cSH.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            cSH.setBounds(200, 350, 800, 400);
            cSH.setTitle(P[t] + ".tsp - SH");
            cSH.setVisible(true);
            System.out.println("");

            //SHE
            System.out.println("SHE - " + P[t] + ".tsp - " + MAXITER[t] + " iter");
            for (int i = 0; i < SEED.length; i++) {
                solSHE[t][i] = new SistemaElitista(SEED[i], t);
                System.out.println(solSHE[t][i].elite.coste + "\t" + solSHE[t][i].elite.eval);
                convSHE[i] = solSHE[t][i].convergencia;
            }
            //CAMINO
            int cmSHE = Integer.MAX_VALUE;
            int posSHE = -1;
            for (int i = 0; i < SEED.length; i++) {
                if (cmSHE > solSHE[t][i].elite.coste) {
                    posSHE = i;
                }
            }
            Grafo gSHE = new Grafo(solSHE[t][posSHE].elite.cerrados);
            gSHE.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            gSHE.setBounds(200, 350, 800, 400);
            gSHE.setTitle(P[t] + ".tsp - SHE");
            gSHE.setVisible(true);
            //CONVERGENCIA
            Grafica cSHE = new Grafica(convSHE, t);
            cSHE.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            cSHE.setBounds(200, 350, 800, 400);
            cSHE.setTitle(P[t] + ".tsp - SHE");
            cSHE.setVisible(true);
            System.out.println("");

            System.out.println("\n---------------------\n");
        }

        //GUARDAR
        Parser.escribir("RESULTADOS.txt");
    }
}
