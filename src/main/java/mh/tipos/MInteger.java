package mh.tipos;

/**
 *
 * @author diego
 */
public class MInteger {

    public final int filas, columnas;
    public int[][] m;

    public MInteger(int a, int b) {
        filas = a;
        columnas = b;
        m = new int[a][b];
    }

    public void construir(Lista<Nodo> listaCiu) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i == j) {
                    m[i][j] = Integer.MAX_VALUE;
                } else {
                    Nodo ni = listaCiu.get(i);
                    Nodo nj = listaCiu.get(j);
                    double distX = Math.pow(ni.x - nj.x, 2);
                    double distY = Math.pow(ni.y - nj.y, 2);
                    m[i][j] = (int) Math.round(Math.sqrt(distX + distY));
                }
            }
        }
    }

    public int costeCamino(Lista<Nodo> solucion) {
        int coste = 0;
        int tam = solucion.size();
        Nodo inicial = solucion.get(0);
        Nodo siguiente = inicial;
        for (int i = 0; i < tam - 1; i++) {
            Nodo actual = solucion.get(i);
            siguiente = solucion.get(i + 1);
            coste = coste + m[actual.id][siguiente.id];
        }
        inicial = solucion.get(0);
        coste = coste + m[siguiente.id][inicial.id];
        return coste;
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
