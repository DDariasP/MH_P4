package mh.tipos;

/**
 *
 * @author diego
 */
public class Nodo {

    public final int id;
    public final double x, y;

    public Nodo(int a, double b, double c) {
        id = a;
        x = b;
        y = c;
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

        return (id == obj.id && x == obj.x && y == obj.y);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + id;
        hash = 13 * hash + (int) (Double.doubleToLongBits(x) ^ (Double.doubleToLongBits(x) >>> 32));
        hash = 13 * hash + (int) (Double.doubleToLongBits(y) ^ (Double.doubleToLongBits(y) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        String output = id + "[" + Math.round(x) + "," + Math.round(y) + "]";
        return output;
    }

}
