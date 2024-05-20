package mh.tipos;

/**
 *
 * @author diego
 */
public class Tabla {

    public final int filas, columnas;
    public double[][] s;

    public Tabla(int a, int b) {
        filas = a;
        columnas = b;
        s = new double[a][b];
    }

    public Tabla(int a, int b, double c) {
        filas = a;
        columnas = b;
        s = new double[a][b];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                s[i][j] = c;
            }
        }
    }

    public void construir(Matriz distancias) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i == j) {
                    s[i][j] = 0.0;
                } else {
                    s[i][j] = 1.0 / distancias.s[i][j];
                }
            }
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                output = output + s[i][j] + " ";
            }
            output = output + "\n";
        }
        return output;
    }

}
