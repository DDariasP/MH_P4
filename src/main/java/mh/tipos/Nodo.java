package mh.tipos;

/**
 *
 * @author diego
 */
public class Nodo {

    public final int id;
    public final double x, y;
    public int coste;

    public Nodo(int a, double b, double c) {
        id = a;
        x = b;
        y = c;
    }

    public Nodo(Nodo n, int c) {
        id = n.id;
        x = n.x;
        y = n.y;
        coste = c;
    }

    public static void sort(Lista<Nodo> lista) {
        if (lista == null || lista.isEmpty()) {
            return;
        }
        quickSort(lista, 0, lista.size() - 1);
    }

    private static void quickSort(Lista<Nodo> lista, int menor, int mayor) {
        if (menor < mayor) {
            int indexPivote = partition(lista, menor, mayor);
            quickSort(lista, menor, indexPivote - 1);
            quickSort(lista, indexPivote + 1, mayor);
        }
    }

    private static int partition(Lista<Nodo> lista, int menor, int mayor) {
        Nodo pivote = lista.get(mayor);
        int i = menor - 1;
        for (int j = menor; j < mayor; j++) {
            if (lista.get(j).coste - pivote.coste <= 0) {
                i++;
                swap(lista, i, j);
            }
        }
        swap(lista, i + 1, mayor);
        return (i + 1);
    }

    private static void swap(Lista<Nodo> lista, int i, int j) {
        Nodo tmp = lista.get(i);
        lista.replace(i, lista.get(j));
        lista.replace(j, tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Nodo)) {
            return false;
        }

        Nodo obj = (Nodo) o;

        return (id == obj.id);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + id;
        return hash;
    }

    @Override
    public String toString() {
        String output = (id + 1) + "(" + Math.round(x) + "," + Math.round(y) + ")";
        return output;
    }

}
