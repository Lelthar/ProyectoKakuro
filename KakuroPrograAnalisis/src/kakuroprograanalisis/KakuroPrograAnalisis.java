/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuroprograanalisis;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author gerald
 */
public class KakuroPrograAnalisis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
    }
    public static ArrayList<int[]>  backtrackNumeros(int numero,int casillas){
        int[]lista = new int[casillas];
        for(int i = 0; i<lista.length;i++){
            lista[i] = 0;
        }
        ArrayList<int[]> listaFinal = new ArrayList<>();
        int contador = 0;
        //ArrayList<int[]> listaFinal = new ArrayList<>();
        generar(0,casillas,0,numero,1,lista,listaFinal,contador);
        return listaFinal;
    }
    public static void generar(int num,int cantidad,int k,int meta,int i,int[] lista,ArrayList<int[]> listaFinal,int contador){
        if(lista.length == cantidad & k == cantidad && num == meta){
            //System.out.println(Arrays.toString(lista));
            listaFinal.add((int[])lista.clone());
            lista[k-1] = 0;
            //System.out.println(Arrays.toString(listaFinal.get(contador)));
            contador++;
        }else if(lista.length == cantidad & k == cantidad){
            System.out.println("No hay");

        }else{
            for(int j = 1; j<10; j++){
                if(num+j < meta & k+1 != cantidad & estaNumero(lista,j) == false | num+j == meta & k+1 == cantidad & estaNumero(lista,j) == false){
                    /*System.out.println(lista);
                    System.out.println(k);*/
                    lista[k] = j;
                    generar(num+j,cantidad,k+1,meta,i+1,lista,listaFinal,contador);
                }
            }
        }
    }
    public static boolean estaNumero(int[] lista, int numero){
        for(int i = 0; i<lista.length ;i++){
            if(lista[i] == numero){
                return true;
            }
        }
        return false;
    }
  
}
