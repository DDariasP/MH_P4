package mh.tipos;

/**
 *
 * @author diego
 */
public class Hormiga {
    
    public final String id;
    public Lista<Nodo> cerrados;
    
    public Hormiga(int a, Nodo b) {
        id = "H" + a;
        cerrados = new Lista<>();
        cerrados.add(b);
    }
    
    public Nodo siguiente() {
        Nodo siguiente = actual();
        
        return siguiente;
    }
    
    public Nodo actual() {
        int pos = cerrados.size() - 1;
        return cerrados.get(pos);
    }
    
}
