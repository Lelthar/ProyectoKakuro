/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuroprograanalisis;

import java.util.ArrayList;

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
        System.out.println("Que pasó?");
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(0);
        lista.add(0);
        lista.add(0);
        ArrayList<ArrayList> listaFinal = new ArrayList<>();
        generar(0,3,0,8,1,lista,listaFinal);
    }
    public static void generar(int num,int cantidad,int k,int meta,int i,ArrayList<Integer> lista,ArrayList<ArrayList> listaFinal){
        if(lista.size() == cantidad & k == cantidad && num == meta){
            System.out.println(lista);
            listaFinal.add(lista);
            lista.set(k-1,0);
            System.out.println(listaFinal);
        }else if(lista.size() == cantidad & k == cantidad){
            System.out.println("No hay");

        }else{
            for(int j = 1; j<10; j++){
                if(num+j < meta & k+1 != cantidad & estaNumero(lista,j) == false | num+j == meta & k+1 == cantidad & estaNumero(lista,j) == false){
                    /*System.out.println(lista);
                    System.out.println(k);*/
                    lista.set(k, j);
                    generar(num+j,cantidad,k+1,meta,i+1,lista,listaFinal);
                }
            }
        }
    }
    public static boolean estaNumero(ArrayList<Integer> lista, int numero){
        for(int i = 0; i<lista.size() ;i++){
            if(lista.get(i) == numero){
                return true;
            }
        }
        return false;
    }
}
