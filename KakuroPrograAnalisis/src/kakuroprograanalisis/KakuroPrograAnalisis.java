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
        generar(0,3,0,8,1,lista);
    }
    public void generar(int num,int cantidad,int k,int meta,int i,ArrayList lista){
        if(k == cantidad && num == meta){
            System.out.println(lista);
        }else if(k == cantidad){
            System.out.println("No hay");
        }else{
            for(int j = 0; j<10; j++){
                if(num+j < meta & k+1 != cantidad | num+j == meta & k+1 == cantidad ){
                    lista.add(j);
                    generar(num+j,cantidad,k+1,meta,i+1,lista);
                }
            }
        }
    } 
}
