package mh;

import mh.tipos.*;
import java.io.File;
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
            while (!line.equals("EOF")) {
                tokens = line.split("\\s+");
                if (tokens.length == 3) {
                    int id = Integer.parseInt(tokens[0]);
                    double x = Double.parseDouble(tokens[1]);
                    double y = Double.parseDouble(tokens[2]);
                    listaCiu.add(new Nodo(id, x, y));
                }
                line = scanner.nextLine();
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("Error en File.");
        }
        return listaCiu;
    }

    public static Lista<Integer> leerTour(String filename) {
        Lista<Integer> solucion = new Lista<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String line = "";
            while (!line.equals("TOUR_SECTION")) {
                line = scanner.nextLine();
            }
            line = scanner.nextLine();
            while (scanner.hasNext()) {
                int id = Integer.parseInt(line);
                solucion.add(id);
                line = scanner.nextLine();
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("Error en File.");
        }
        return solucion;
    }

}
