package mh.tipos;

/**
 *
 * @author diego
 */
public class Matriz {

    public final int filas, columnas;
    public int[][] s;

    public Matriz(int a, int b) {
        filas = a;
        columnas = b;
        s = new int[a][b];
    }

    public void construir(Lista<Nodo> listaCiu) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i == j) {
                    s[i][j] = Integer.MAX_VALUE;
                } else {
                    Nodo ni = listaCiu.get(i);
                    Nodo nj = listaCiu.get(j);
                    double distX = Math.pow(ni.x - nj.x, 2);
                    double distY = Math.pow(ni.y - nj.y, 2);
                    s[i][j] = (int) Math.round(Math.sqrt(distX + distY));
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
            coste = coste + s[actual.id][siguiente.id];
        }
        inicial = solucion.get(0);
        coste = coste + s[siguiente.id][inicial.id];
        return coste;
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
