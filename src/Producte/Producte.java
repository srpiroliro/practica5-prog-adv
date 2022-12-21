package Producte;
import java.util.HashSet;
import java.util.Iterator;

public class Producte{
    private int id=0;
    private String nom;
    
    private HashSet<Producte> reactives=new HashSet<Producte>();
    private Iterator<Producte> iter;
    private int calaix;
    
    public Producte(int id){this.id=id;this.nom="P"+id;}
    public Producte(int id,HashSet<Producte> ps){
        // this(id); // inefficient?
        this.id=id;
        this.nom="P"+id;
        reactives=ps;
    }

    public boolean add_reactive(Producte r){return reactives.add(r);}

    public int getId(){return id;}
    public String getName(){return nom;}

    public int getCalaix(){return calaix;}
    public void setCalaix(int c){calaix = c;}


    public void printReactives(){for(Producte p:reactives) System.out.print(p.getId()+" ");}

    public void ini_iterator(){iter=reactives.iterator();}
    public Producte seg_iterator(){return (iter==null||!iter.hasNext())?null:iter.next();}

    public boolean es_reactiu(Producte p){return reactives.contains(p);}

    @Override
    public String toString(){return "Producte "+id;}

    // o 
    // public int length(){return reactives.size();}
    // public Producte get_reactive(int i){return reactives.get(i);}
}