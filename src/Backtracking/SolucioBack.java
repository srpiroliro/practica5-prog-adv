/*
1.- Per què l’esquema de backtracking és aplicable per a resoldre aquest enunciat. 
2.- Indica quina pregunta et fas en cada nivell de l’arbre. Quines són les possibles respostes d’aquesta pregunta (domini). 
3.- Quin és el criteri per determinar si una decisió és o no acceptable. 
4.- Quin és el criteri per determinar si un conjunt de decisions és o no completable. 
5.- Quin és el criteri per determinar si un conjunt de decisions són o no solució. 
6.- Dibuixeu l’espai de cerca del problema, és a dir l’arbre que ha de recórrer la tècnica del backtracking, indica quina és l’alçada i l’amplada i si són valors exactes o valors màxims. Usaràs marcatge?. 
7.- Quin és el criteri per determinar si una solució és o no millor a una altra ja trobada prèviament.
*/

package Backtracking;
import Producte.Producte;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

class SolucioBack {
    private static final double CHANCES=0.5;

    public static void main(String[] args){
        ArrayList<Producte> productes=new ArrayList<Producte>();
        int num_productes;
       
        System.out.print("Indica quants productes diferents ha d'emmagatzemar l'empresa: ");
        num_productes=Integer.parseInt(System.console().readLine());

        for(int i=0; i<num_productes; i++) productes.add(new Producte(i+1));

        System.out.println("Ara es procedeix a generar les deades aleatòries:");

        for(int x=0; x<num_productes;x++){
            for(int y=x; y<num_productes;y++){
                if(Math.random()<CHANCES && x!=y){
                    productes.get(x).add_reactive(productes.get(y));
                    productes.get(y).add_reactive(productes.get(x));
                }
            }
            System.out.print("Producte "+(x+1)+" amb incompatibilitats amb els prodcutes: ");
            productes.get(x).printReactives();
            System.out.println();
            //  o
            // System.out.println("Producte "+x+" amb incompatibilitats amb els prodcutes: ");            
            // productes[x].ini_iterator();
            // Producte p;
            // do{
            //     p = productes[x].seg_iterator();
            //     if(p!=null)
            //         System.out.print(p.getId()+" ");
            // }while(p!=null);
            // System.out.println();
        }


        boolean matriu[][]=new boolean[num_productes][num_productes];
        spawn_matriu(matriu, productes);
        vis_matriu(matriu);
        System.out.println();

        int min_comp[]=new int[1];
        min_comp[0]=num_productes;

        boolean[] marcatge=new boolean[num_productes]; 
        ArrayList<ArrayList<Producte>> solucio =new ArrayList<ArrayList<Producte>>(), 
                                            tmp=new ArrayList<ArrayList<Producte>>(); 

        for(int i=0; i<num_productes; i++){
            solucio.add(new ArrayList<Producte>());
            marcatge[i]=false;
        }
        solucio.add(new ArrayList<Producte>());

        ArrayList<Producte> copia_productes=new ArrayList<Producte>(productes);

        Backtracking(tmp,solucio, copia_productes, num_productes, matriu);
        make_solucio(solucio, productes);
        vis_solucio(productes);
    }

    static int elem_amount(ArrayList<ArrayList<Producte>> p){
        int total=0;
        for(ArrayList<Producte> a:p) total+=a.size();
        return total;
    }

    static void Backtracking(
        ArrayList<ArrayList<Producte>> current_solution, 
        ArrayList<ArrayList<Producte>> best_solution,
        ArrayList<Producte> productes_posibles, 
        int llargada,
        boolean[][] matriu
        ){
        int j=0, calaix;
        while(j<productes_posibles.size()){
            Producte tmp=productes_posibles.remove(j);

            calaix=0;
            while(calaix<current_solution.size()&&danger(tmp,current_solution.get(calaix),matriu)) calaix++;
            if(calaix==current_solution.size()) current_solution.add(new ArrayList<Producte>());

            current_solution.get(calaix).add(tmp);

            if(elem_amount(current_solution)==llargada){
                if(best_solution.size()>current_solution.size()){
                    best_solution.clear();
                    for(ArrayList<Producte> p:current_solution) best_solution.add(new ArrayList<Producte>(p));
                    
                    System.out.println("Solucio trobada: "+best_solution + " amb "+best_solution.size()+" calaixos\n");
                }
            }else if(best_solution.size()>current_solution.size())
                Backtracking(current_solution, best_solution, productes_posibles, llargada, matriu);

            del_last_elem(current_solution, calaix);

            productes_posibles.add(tmp);
            j++;
        }

    }

    static void del_last_elem(ArrayList<ArrayList<Producte>> current_solution, int calaix){
        current_solution.get(calaix).remove(current_solution.get(calaix).size()-1);
        if(current_solution.get(calaix).size()==0) current_solution.remove(calaix);
    }

    static boolean danger(Producte target, ArrayList<Producte> p, boolean[][] matriu){
        for(Producte producte:p) if(matriu[target.getId()-1][producte.getId()-1]) return true;
        return false;
    } 

    static void spawn_matriu(boolean[][] matriu, ArrayList<Producte> prs){
        for(int y=0;y<matriu.length;y++) for(int x=0;x<matriu.length;x++) matriu[y][x]=prs.get(y).es_reactiu(prs.get(x));
    }

    static private void vis_matriu(boolean[][] m){
        System.out.print("       ");
        for(int y=0;y<m.length+1;y++){
            for(int x=0;x<m.length;x++) {
                if(y==0) System.out.print("P-"+(x+1)+" ");
                else{
                    if(x==0){
                        if(y<10) System.out.print("P-"+(y)+"  | ");
                        else System.out.print("P-"+(y)+" | ");
                    }
                    if(x<9) System.out.print(" "+((m[y-1][x])?"-":"X")+"  ");
                    else System.out.print(" "+((m[y-1][x])?"-":"X")+"   ");
                }
            }
            if(y==0){
                System.out.println();
                System.out.print("      ");
                for(int x=0;x<m.length;x++){
                    System.out.print("----");
                    if(x>=9) System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    static private void vis_solucio(ArrayList<Producte> productes){
        for(Producte p:productes) System.out.println(p+" al compartiment: "+(p.getCalaix()+1));
    }

    private static void make_solucio(ArrayList<ArrayList<Producte>> solucio, ArrayList<Producte> productes) {
        for(ArrayList<Producte> p:solucio) for(Producte pr:p) pr.setCalaix(solucio.indexOf(p));
    }
}
