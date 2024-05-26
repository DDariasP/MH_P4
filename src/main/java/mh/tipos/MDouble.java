package mh.tipos;

/**
 *
 * @author diego
 */
public class MDouble {

    public final int filas, columnas;
    public double[][] m;

    public MDouble(int a, int b) {
        filas = a;
        columnas = b;
        m = new double[a][b];
    }

    public MDouble(int a, int b, double c) {
        filas = a;
        columnas = b;
        m = new double[a][b];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                m[i][j] = c;
            }
        }
    }

    public void construir(MInteger distancias) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i == j) {
                    m[i][j] = 0.0;
                } else {
                    m[i][j] = 1.0 / distancias.m[i][j];
                }
            }
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                output = output + m[i][j] + " ";
            }
            output = output + "\n";
        }
        return output;
    }

}
