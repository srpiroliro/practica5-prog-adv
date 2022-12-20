package Backtracking;

public class Teoria {
    static final int OBJECTIVE=23;

    private static final int d[]={1,2,3,5,7,8,9,11,13};
    public static void main(String args[]){
        int x[]={0,0,0,0};
        int Xmillor[]={0,0,0,0};
        int min[]=new int[1];
        min[0]=5;

        boolean marcats[]=new boolean[d.length];
        for (int i=0;i<marcats.length; i++) marcats[i]=false;
        
        System.out.println("Repetits:");
        BackMillorSolucio(x, Xmillor, 0, min, marcats);
        vis(min,Xmillor);
        
        System.out.println("uniques:");
        BackMillorSolucio(x, Xmillor, 0, min);
        vis(min,Xmillor);

        
    }

    private static void vis(int min[], int Xmillor[]){
        if(min[0]==5) System.out.println("NONE");
        else{
            System.out.println("min: "+min[0]);

            // for (int b : Xmillor) {
            //     System.out.println(b);
            // }

            for (int i=0; i<=min[0]; i++){
                System.out.println(Xmillor[i]);
            }
        }
        System.out.println("+ + +\n\n");
    }

    private static void BackMillorSolucio(int x[], int Xmillor[], int k, int min[]){
        int j=0;
        while(j<d.length){
            x[k]=d[j]; // 0 1 2 3 

            if(x[0]+x[1]+x[2]+x[3]==OBJECTIVE){
                if(min[0]>k){
                    for(int i=0;i<4;i++) Xmillor[i]=x[i];
                    min[0]=k;
                }
            } else if(x[0]+x[1]+x[2]+x[3]<OBJECTIVE && k<(4-1) && k<min[0]-1)
                BackMillorSolucio(x, Xmillor, k+1, min);
            x[k]=0;
            j++;
        }
    }

    private static void BackMillorSolucio(int x[], int Xmillor[], int k, int min[], boolean marcats[]){
        int j=0;
        while(j<d.length){
            if(marcats[j]==false){
                x[k]=d[j];
                marcats[j]=true;

                if(x[0]+x[1]+x[2]+x[3]==OBJECTIVE){
                    if(min[0]>k){
                        for(int i=0;i<4;i++) Xmillor[i]=x[i];
                        min[0]=k;
                    }
                } else
                    if(x[0]+x[1]+x[2]+x[3]<OBJECTIVE && k<3 && k<min[0]-1) 
                        BackMillorSolucio(x, Xmillor, k+1, min, marcats);
                x[k]=0;
                marcats[j]=false;
            }
            j++;
        }
    }
}
