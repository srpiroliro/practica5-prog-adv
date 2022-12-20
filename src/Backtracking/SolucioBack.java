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

class SolucioBack {
    private static final double CHANCES=0.4;
    public static void main(String[] args) {
        Producte[] productes;
        int num_productes;
        

       
        System.out.print("Indica quants productes diferents ha d'emmagatzemar l'empresa: ");
        num_productes=Integer.parseInt(System.console().readLine());

        productes=new Producte[num_productes];
        for(int i=0; i<num_productes; i++) productes[i]=new Producte(i+1);

        System.out.println("Ara es procedeix a gener les deades aleatòries:");
        
        /// CHECK
        for(int x=0; x<num_productes;x++){
            for(int y=x; y<num_productes-x;y++){
                if(Math.random()<CHANCES){
                    productes[y].add_reactive(productes[x]);
                    productes[x].add_reactive(productes[y]);
                }
            }
            System.out.print("Producte "+x+" amb incompatibilitats amb els prodcutes: ");
            productes[x].printReactives();
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

        int min_comp[]=new int[1];

        boolean[] marcatge=new boolean[num_productes];
        ArrayList<ArrayList<Producte>> solucio=new ArrayList<ArrayList<Producte>>(), 
                                tmp=new ArrayList<ArrayList<Producte>>(); 

                                
        for(int i=0; i<num_productes; i++){
            solucio.add(new ArrayList<Producte>());
            tmp.add(new ArrayList<Producte>());

            marcatge[i]=false;
        }

        Backtracking(min_comp,tmp,solucio,productes,marcatge);

        vis_solucio(solucio);


    }

    static void Backtracking(
        int min[],
        ArrayList<ArrayList<Producte>> current_solution, 
        ArrayList<ArrayList<Producte>> best_solution,
        Producte[] productes_posibles,
        boolean marcatge[]
    ){
        
        int j=0, compartment;
        while(j<productes_posibles.length){
            if(!marcatge[j]){
                compartment=0;
                while(
                    compartment<current_solution.size() &&
                    current_solution.get(compartment).contains(productes_posibles[j])
                )
                    compartment++;
                
                current_solution.get(compartment).add(productes_posibles[j]);
            }
            j++;
        }

    }


    static void spawn_matriu(boolean[][] matriu, Producte prs[]){
        for(int y=0;y<matriu.length;y++){
            for(int x=0;x<matriu.length;x++){
                matriu[y][x]=prs[y].es_reactiu(prs[x]);
            }
        }
    }

    static private void vis_matriu(boolean[][] m){
        for(int y=0;y<m.length;y++){
            for(int x=0;x<m.length;x++)
                System.out.print(((m[y][x])?"X":"O")+" ");
            System.out.println();
        }
    }

    static private void vis_solucio(ArrayList<ArrayList<Producte>> sol){
        // print each product (not each compartment)
        for(int y=0;y<sol.size();y++){
            System.out.print("compartment: "+y);
            for(Producte p:sol.get(y))
                System.out.print(" "+p.getId());
        }
    }
}
