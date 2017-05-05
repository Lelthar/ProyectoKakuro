/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuroprograanalisis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import modelo.Casilla;

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
        /*for(int i= 6; i < 46; i++){
            if(i >=6 && i<=9){
                ArrayList<int[]> nuevo = backtrackSoluciones(i,3);
                System.out.println("El numero: "+Integer.toString(i));
                System.out.println("Tiene numero de soluciones: "+Integer.toString(nuevo.size()));
                System.out.println("----------------------------------------------------");
            }else if(i >= 10 && i<=14){
                ArrayList<int[]> nuevo = backtrackSoluciones(i,4);
                System.out.println("El numero: "+Integer.toString(i));
                System.out.println("Tiene numero de soluciones: "+Integer.toString(nuevo.size()));
                System.out.println("----------------------------------------------------");
            }else if(i >= 15 && i<=20){
                ArrayList<int[]> nuevo = backtrackSoluciones(i,5);
                System.out.println("El numero: "+Integer.toString(i));
                System.out.println("Tiene numero de soluciones: "+Integer.toString(nuevo.size()));
                System.out.println("----------------------------------------------------");
            }else if(i >= 21 && i<=27){
                ArrayList<int[]> nuevo = backtrackSoluciones(i,6);
                System.out.println("El numero: "+Integer.toString(i));
                System.out.println("Tiene numero de soluciones: "+Integer.toString(nuevo.size()));
                System.out.println("----------------------------------------------------");
            }else if(i >= 28 && i<=35){
                ArrayList<int[]> nuevo = backtrackSoluciones(i,7);
                System.out.println("El numero: "+Integer.toString(i));
                System.out.println("Tiene numero de soluciones: "+Integer.toString(nuevo.size()));
                System.out.println("----------------------------------------------------");
            }else if(i >= 36 && i<=44){
                ArrayList<int[]> nuevo = backtrackSoluciones(i,8);
                System.out.println("El numero: "+Integer.toString(i));
                System.out.println("Tiene numero de soluciones: "+Integer.toString(nuevo.size()));
                System.out.println("----------------------------------------------------");
            }else if(i == 45){
                ArrayList<int[]> nuevo = backtrackSoluciones(i,9);
                System.out.println("El numero: "+Integer.toString(i));
                System.out.println("Tiene numero de soluciones: "+Integer.toString(nuevo.size()));
                System.out.println("----------------------------------------------------");
                
            }
        }*/
        //ArrayList<int[]> nuevo = backtrackNumeros(11,4);
       //int[][] numero = new int[3][3];
        //System.out.println(nuevo.size());
        /*Random rnd = new Random();
        int numero =  (int) (rnd.nextDouble() * 5 + 10);
        System.out.println(numero);*/
        /*ArrayList<int[]> nuevo = backtrackSoluciones(46,9);
        
        System.out.println(nuevo.size());
        
        */
    }
    public static ArrayList<int[]>  backtrackNumeros(int numero,int casillas){
        int[]lista = new int[casillas];
        ArrayList<int[]> listaFinal = new ArrayList<>();
        generar(0,casillas,0,numero,lista,listaFinal);
        return listaFinal;
    }
    public static void generar(int num,int cantidad,int k,int meta,int[] lista,ArrayList<int[]> listaFinal){
        if(k == cantidad){
            System.out.println(Arrays.toString(lista));
            listaFinal.add((int[])lista.clone());
            //System.out.println(Arrays.toString(listaFinal.get(contador)));
            
        }else{
            for(int j = 1; j<10; j++){
                lista[k] = 0;
                for(int p = k; p<lista.length; p++){
                    lista[p] = 0;
                }
                if(num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){    
                    lista[k] = j;
                    generar(num+j,cantidad,k+1,meta,lista,listaFinal);
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
    public static ArrayList<int[]>  backtrackSoluciones(int numero,int casillas){
        int[]lista = new int[casillas];
        ArrayList<int[]> listaFinal = new ArrayList<>();
        generarSoluciones(0,casillas,0,numero,lista,listaFinal);
        return listaFinal;
    }
    public static void generarSoluciones(int num,int cantidad,int k,int meta,int[] lista,ArrayList<int[]> listaFinal){
        if(k == cantidad ){
            //System.out.println(Arrays.toString(lista));
            listaFinal.add((int[])lista.clone());
            //System.out.println(Arrays.toString(listaFinal.get(contador)));
            
        }else{
            for(int j = 1; j<10; j++){
                lista[k] = 0;
                for(int p = k; p<lista.length; p++){
                    lista[p] = 0;
                }
                if(num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){    
                    lista[k] = j;
                    generarSoluciones(num+j,cantidad,k+1,meta,lista,listaFinal);
                }
            }
        }
    }
  
}
