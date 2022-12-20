package Producte;
import java.util.HashSet;

class Producte{
    private int id=0;
    private HashSet<Producte> reactives=new HashSet<Producte>();
    
    public Producte(int id){this.id=id;}
    public Producte(int id,HashSet<Producte> ps){
        this.id=id;
        reactives=ps;
    }

    public boolean add_reactive(Producte r){
        if(r==null) return false; //||reactives.contains(r) necesary?
        reactives.add(r);
        return true;
    }
    // https://www.javatpoint.com/java-graph
}