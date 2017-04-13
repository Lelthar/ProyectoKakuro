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
        int[]lista = new int[3];
        lista[0] = 0;
        lista[1] = 0;
        lista[2] = 0;
        ArrayList<int[]> listaFinal = new ArrayList<>();
        int contador = 0;
        //ArrayList<int[]> listaFinal = new ArrayList<>();
        generar(0,3,0,8,1,lista,listaFinal,contador);
        for(int i = 0; i< listaFinal.size();i++){
            System.out.println(Arrays.toString(listaFinal.get(i)));
        }
        
    }
    public static void generar(int num,int cantidad,int k,int meta,int i,int[] lista,ArrayList<int[]> listaFinal,int contador){
        if(lista.length == cantidad & k == cantidad && num == meta){
            //System.out.println(Arrays.toString(lista));
            listaFinal.add(vectorConversion(lista));
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
    public static int[] vectorConversion(int[] vector){
        int a,b;
        b = vector.length;
        int[] nuevoVector = new  int[b];
        for(int i = 0; i<vector.length; i++){
            a = vector[i];
            nuevoVector[i] = a;
        }
  
        return nuevoVector;
    }
}
