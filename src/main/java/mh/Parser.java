package mh;

import mh.tipos.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author diego
 */
public class Parser {

    public static Lista<Nodo> leerCiu(String filename) {
        Lista<Nodo> listaCiu = new Lista<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String line = "";
            String[] tokens;
            while (!line.equals("NODE_COORD_SECTION")) {
                line = scanner.nextLine();
            }
            line = scanner.nextLine();
            line = line.trim();
            while (!line.equals("EOF")) {
                tokens = line.split("\\s+");
                if (tokens.length == 3) {
                    int id = Integer.parseInt(tokens[0]) - 1;
                    double x = Double.parseDouble(tokens[1]);
                    double y = Double.parseDouble(tokens[2]);
                    listaCiu.add(new Nodo(id, x, y));
                }
                line = scanner.nextLine();
                line = line.trim();
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("Error en File.");
        }
        return listaCiu;
    }

    public static Lista<Nodo> leerTour(String filename) {
        Lista<Nodo> solucion = new Lista<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String line = "";
            while (!line.equals("TOUR_SECTION")) {
                line = scanner.nextLine();
            }
            line = scanner.nextLine();
            while (scanner.hasNext()) {
                int id = Integer.parseInt(line) - 1;
                solucion.add(P4.listaCiu.get(id));
                line = scanner.nextLine();
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("Error en File.");
        }
        return solucion;
    }

    public static void escribir(String filename) {
        try {
            File resultados = new File(filename);
            if (resultados.exists()) {
                resultados.delete();
                System.out.println("\nArchivo " + resultados.getName() + " sobreescrito.\n");
            } else {
                System.out.println("\nArchivo " + resultados.getName() + " creado.\n");
            }
            resultados.createNewFile();
            FileWriter writer = new FileWriter(filename);

            for (int t = 0; t < P4.T.length; t++) {
                writer.write(P4.filename[t] + ".tsp");

                writer.write("\nOPTIMA");
                writer.write("\n" + P4.solOPT[t].coste + "\t" + P4.solOPT[t].eval);

                writer.write("\nGREEDY");
                writer.write("\n" + P4.solG[t].coste + "\t" + P4.solG[t].eval);

                writer.write("\nSH - " + P4.T[t] + " min");
                for (int i = 0; i < P4.SEED.length; i++) {
                    writer.write("\n" + P4.solSHE[t][i].elite.coste + "\t" + P4.solSH[t][i].eval);
                }

                writer.write("\nSHE - " + P4.T[t] + " min");
                for (int i = 0; i < P4.SEED.length; i++) {
                    writer.write("\n" + P4.solSHE[t][i].elite.coste + "\t" + P4.solSHE[t][i].eval);
                }

                writer.write("\n---------------------\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error en File.");
        }
    }

}
