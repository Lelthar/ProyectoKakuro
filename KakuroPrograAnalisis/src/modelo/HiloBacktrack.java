/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import static vista.ventanaKakuro.bactrackFinal;

/**
 *
 * @author gerald
 */
public class HiloBacktrack extends Thread{
    Casilla[][] matriz;
    ArrayList<CasillaPrincipal> listaPrincipales;
    ArrayList<Casilla[][]> soluciones;
    boolean solucion = true;
    
    
    public HiloBacktrack(Casilla[][] pMatriz,ArrayList<CasillaPrincipal> lista) {
        this.matriz = pMatriz;
        this.listaPrincipales = lista;
     
    }
    
    public void run(){
        while(solucion){
            System.out.println("Iniciando hilo...");
            soluciones = backtrackResolver();
            System.out.println("Listo...");
            solucion = false;
        }
    }
    
    public Casilla[][] clonarMatriz(Casilla[][] matriz){
        Casilla[][] nuevo = new Casilla[matriz.length][matriz.length];
        /*for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz.length; j++){
                
                nuevo[i][j].orientacion = "";
                nuevo[i][j].principal = false;
                nuevo[i][j].principal2 = false;
                nuevo[i][j].negativo = false;
                nuevo[i][j].cantidadCasillas = 0;
                
            }
        }*/
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz.length; j++){
                nuevo[i][j] = new Casilla(matriz[i][j].numero);
                nuevo[i][j].orientacion = matriz[i][j].orientacion;
                nuevo[i][j].principal = matriz[i][j].principal;
                nuevo[i][j].principal2 = matriz[i][j].principal2;
                nuevo[i][j].cantidadCasillas = matriz[i][j].cantidadCasillas;
                nuevo[i][j].casillaFinal = matriz[i][j].casillaFinal;
                nuevo[i][j].negativo = matriz[i][j].negativo;
            }
        }
        return nuevo;
    }
    public void imprimirMatriz(Casilla[][] matriz){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                System.out.print(Integer.toString(matriz[i][j].getNumero())+" ");
            }
            System.out.println("");
        }
    }
    public ArrayList<Casilla[][]> backtrackResolver(){
        Casilla[][] matrizLogica = new Casilla[14][14];
        matrizLogica = clonarMatriz(matriz);
        ArrayList<Casilla[][]> solucionesMatriz = new ArrayList<>();
        int contador = 1;
        int largoIteraciones = listaPrincipales.size();
        generarSolucionKakuro(contador,matrizLogica,solucionesMatriz,largoIteraciones);
        return solucionesMatriz;
    }
    public void generarSolucionKakuro(int contador,Casilla[][] matriz, ArrayList<Casilla[][]> soluciones, int largo){
        /*System.out.println("---------------");
        System.out.println(soluciones.size());
        System.out.println(contador);*/
        if(contador == largo){
            soluciones.add(clonarMatriz(matriz));
            return;
        }else{
            /*for(int k = contador; k < listaPrincipales.size(); k++){
                limpiarCeros(listaPrincipales.get(k).x, listaPrincipales.get(k).y, listaPrincipales.get(k).orientacion, listaPrincipales.get(k).casillas, matriz);
            }*/
            ArrayList<int[]>  listaSoluciones = new ArrayList<>();
            if(listaPrincipales.get(contador).orientacion == 0){
                listaSoluciones = bactrackFinal(listaPrincipales.get(contador).numero,listaPrincipales.get(contador).casillas,listaPrincipales.get(contador).x,listaPrincipales.get(contador).y+1,listaPrincipales.get(contador).orientacion,matriz);
            }else{
                listaSoluciones = bactrackFinal(listaPrincipales.get(contador).numero,listaPrincipales.get(contador).casillas,listaPrincipales.get(contador).x+1,listaPrincipales.get(contador).y,listaPrincipales.get(contador).orientacion,matriz);
            }
            //System.out.println(largoSoluciones);
            if(listaSoluciones.size() != 0 ){
                //System.out.println("s");
                Casilla[][] nuevaMatriz = clonarMatriz(matriz);
                for(int i = 0; i < listaSoluciones.size(); i++){
                    //System.out.println("Entra");
                    matriz = (Casilla[][]) clonarMatriz(nuevaMatriz);
                    //System.out.println(Arrays.toString(listaSoluciones.get(i)));
                    matriz = llenarTableroMatriz(listaPrincipales.get(contador).x,listaPrincipales.get(contador).y,listaSoluciones.get(i),listaPrincipales.get(contador).orientacion,matriz);
                    //Casilla[][] nuevaMatriz = clonarMatriz(matriz);
                    //imprimirMatriz(matriz);
                    generarSolucionKakuro(contador+1,matriz,soluciones,largo);
                }
            }
            /*int largoSoluciones = listaSolucionesPrincipales.get(contador).size();
            Casilla[][] nuevaMatriz = clonarMatriz(matriz);
            for(int i = 0; i<largoSoluciones; i++){
                matriz = clonarMatriz(nuevaMatriz);
                if(sirveSolucion(matriz,listaSolucionesPrincipales.get(contador).get(i),listaPrincipales.get(contador).x,listaPrincipales.get(contador).y,listaPrincipales.get(contador).orientacion)){
                    matriz = llenarTableroMatriz(listaPrincipales.get(contador).x,listaPrincipales.get(contador).y,largoSoluciones.get(contador).get(i),listaPrincipales.get(contador).orientacion,matriz);
                    //Casilla[][] nuevaMatriz = clonarMatriz(matriz);
                    //imprimirMatriz(nuevaMatriz);
                    imprimirMatriz(matriz);
                    generarSolucionKakuro(contador+1,matriz,soluciones,largo);
                }
            }*/
            
            
        }
    }
    public Casilla[][] llenarTableroMatriz(int x,int y,int[] lista, int orientacion,Casilla[][] matriz){
        if(orientacion == 0){
            y++;
            for(int i = 0; i < lista.length; i++){
                matriz[x][y].setNumero(lista[i]);
                y++;
            }
            return matriz;
        }else{
            x++;
            for(int i = 0; i < lista.length; i++){
                matriz[x][y].setNumero(lista[i]);
                x++;
            }
            return matriz;
        }
    }
}
