package Greedy;

import Producte.Producte;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

class SolucioVorac {
    private static final double CHANCES=0.5;

    public static void main(String[] args){
        ArrayList<Producte> productes=new ArrayList<Producte>();
        int num_productes;
        
        System.out.print("Indica quants productes diferents ha d'emmagatzemar l'empresa: ");
        num_productes=Integer.parseInt(System.console().readLine());
        for(int i=0; i<num_productes; i++) productes.add(new Producte(i+1));
        
        System.out.println("\nAra es procedeix a generar les deades aleatòries:");
        generate_reactives(productes, num_productes);
        
        boolean matriu[][]=new boolean[num_productes][num_productes];
        do_matriu(matriu, productes);

        ArrayList<ArrayList<Producte>> solucio =new ArrayList<ArrayList<Producte>>();
        // for(int i=0; i<num_productes+1; i++) solucio.add(new ArrayList<Producte>());
        
        ArrayList<Producte> copia_productes=new ArrayList<Producte>(productes);

        Collections.sort(copia_productes,Collections.reverseOrder());

        long startTime = System.nanoTime();
        Greedy(solucio, copia_productes, num_productes, matriu);
        long timeElapsed = System.nanoTime() - startTime;
        check_solucio(productes);

        System.out.println("\nTotal calaixos: "+solucio.size());
        System.out.println("Temps d'execució: "+(timeElapsed/Math.pow(10, 9))+" segons");
    }

    static void generate_reactives(ArrayList<Producte> productes, int num_productes){
        for(int x=0; x<num_productes;x++){
            for(int y=x; y<num_productes;y++){
                if(Math.random()<CHANCES && x!=y){
                    productes.get(x).add_reactive(productes.get(y));
                    productes.get(y).add_reactive(productes.get(x));
                }
            }
            System.out.print(productes.get(x)+" amb incompatibilitats amb els prodcutes: ");
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
        System.out.println();
    }

    static void Greedy(
        ArrayList<ArrayList<Producte>> best_solution,
        ArrayList<Producte> productes_posibles,
        int llargada, boolean[][] matriu
    ){
        int j=0, calaix;
        while(j<productes_posibles.size()){

            calaix=get_calaix(best_solution, productes_posibles.get(j), matriu);
            productes_posibles.get(j).setCalaix(calaix);

            best_solution.get(calaix).add(productes_posibles.get(j));

            j++;
        }
    }

    static int get_calaix(ArrayList<ArrayList<Producte>> best_solution, Producte producte, boolean[][] matriu){
        int calaix=0;
        while(calaix<best_solution.size()&&danger(producte,best_solution.get(calaix),matriu)) 
            calaix++;

        if(calaix==best_solution.size()) 
            best_solution.add(new ArrayList<Producte>());

        return calaix;
    }

    static void del_last_elem(ArrayList<ArrayList<Producte>> best_solution, int calaix){
        best_solution.get(calaix).remove(best_solution.get(calaix).size()-1);
        if(best_solution.get(calaix).size()==0) best_solution.remove(calaix);
    }

    static boolean danger(Producte target, ArrayList<Producte> p, boolean[][] matriu){
        for(Producte producte:p) 
            if(matriu[target.getId()-1][producte.getId()-1]) return true; // O(n)
            // if(producte.es_reactiu(target)) return true; // O(n), pero sevita passar matriu com a parametre.
        return false;
    } 

    static void do_matriu(boolean[][] matriu, ArrayList<Producte> productes){
        System.out.print("\n       ");

        for(int y=0;y<matriu.length+1;y++){
            for(int x=0;x<matriu.length;x++){
                if(y==0) System.out.print(productes.get(x).getName()+" ");
                else{
                    matriu[y-1][x]=productes.get(y-1).es_reactiu(productes.get(x));
                    if(x==0) System.out.print(productes.get(y-1).getName()+((y<10)?"  | ":" | "));
                    System.out.print(" "+((matriu[y-1][x])?"-":"X")+((x<9)?" ":"  "));
                }
            }

            if(y==0){
                System.out.print("\n      ");
                for(int x=0;x<matriu.length;x++)
                    System.out.print((x<9)?"___":"____");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    static private void check_solucio(ArrayList<Producte> productes){
        for(Producte p:productes) System.out.println(p+" al compartiment: "+(p.getCalaix()+1));
    }
}