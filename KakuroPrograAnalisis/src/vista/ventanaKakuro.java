/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import modelo.Casilla;
import modelo.CasillaPrincipal;
import modelo.HiloBacktrack;
import modelo.MatrizGuardar;

/**
 *
 * @author gerald
 */
public class ventanaKakuro extends javax.swing.JFrame implements Serializable{

    public VentanaGuardar ventana = new VentanaGuardar(this);
    public static int DIMENSIONES = 14;
    // Tablero con objetos JTextField
    public JTextField[][] tableroLabels1 = new JTextField[DIMENSIONES][DIMENSIONES]; //Es el tablero de JTextField
    public static Casilla[][] tableroLogico = new Casilla[DIMENSIONES][DIMENSIONES]; //Es el tablero logico de tipo Casilla
    public ArrayList<Integer> listaHorizontales = new ArrayList<>(); //Contiene la lista de los numeros que van a ir horizontal
    public ArrayList<Integer> listaVerticales = new ArrayList<>(); //Contiene la lista de los numeros que van a ir vertical
    public ArrayList<ArrayList<int[]>> listaSolucionesPrincipales = new ArrayList<>();
    public ArrayList<CasillaPrincipal> listaPrincipales = new ArrayList<CasillaPrincipal>();
    public ArrayList<MatrizGuardar> listaMatrices = new ArrayList<>();
    public int pruebaIteraciones = 0;
    public int numero = 0;
    
    public ventanaKakuro() {
        initComponents();
        //ventana.dispose();
        inicializarTableroLogico();
        generarTableroLogicoJuego();
        imprimirMatriz(tableroLogico);
        insertarNegativos();
        limpiarTablero();
        limpiarNegativos();
        listaPrincipales = OrdenamientoBurbujaArrayList(listaPrincipales);
        generarTableroLabelsJuego(10, 40);
        //llenarPrincipales();
        //llenarPrincipales();
        //solucionesPrincipales();
        
    }
    public void insertarNegativos(){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                if(tableroLogico[i][j].getNumero() == 0){
                    tableroLogico[i][j].setNumero(-1);
                    tableroLogico[i][j].negativo = true;
                }
            }
        }
    }

   
   public ArrayList<CasillaPrincipal> OrdenamientoBurbujaArrayList(ArrayList<CasillaPrincipal> n){
       CasillaPrincipal temp;
       int t = n.size();
       for (int i = 1; i < t; i++) {
           for (int k = t- 1; k >= i; k--) {
               if(n.get(k).numero < n.get(k-1).numero){
                   temp = n.get(k);
                   n.set(k, n.get(k-1));
                   n.set(k-1,temp);
               }
           }
       }
       return n;
   }
    
    public void limpiarNegativos(){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                if(tableroLogico[i][j].getNumero() == -1){
                    tableroLogico[i][j].setNumero(0);
                }
            }
        }
    }
    public void limpiarTablero(){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                if(tableroLogico[i][j].getNumero() != -1 && tableroLogico[i][j].principal2 == false){
                    if(tableroLogico[i][j].principal == false){
                        tableroLogico[i][j].setNumero(0);
                    }
                }
            }
        }
    }
    public Casilla[][] clonarMatriz(Casilla[][] matriz){
        Casilla[][] nuevo = new Casilla[matriz.length][matriz.length];
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                nuevo[i][j] = new Casilla(0);
                nuevo[i][j].orientacion = "";
                nuevo[i][j].principal = false;
                nuevo[i][j].principal2 = false;
                nuevo[i][j].negativo = false;
                nuevo[i][j].cantidadCasillas = 0;
                
            }
        }
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz.length; j++){
                nuevo[i][j].numero = matriz[i][j].numero;
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
    public void inicializarTableroLogico(){
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                tableroLogico[i][j] = new Casilla(0);
                tableroLogico[i][j].orientacion = "";
                tableroLogico[i][j].principal = false;
                tableroLogico[i][j].principal2 = false;
                tableroLogico[i][j].negativo = false;
                tableroLogico[i][j].cantidadCasillas = 0;
                
            }
        }
    }
    public void imprimirMatriz(Casilla[][] matriz){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                System.out.print(Integer.toString(matriz[i][j].getNumero())+" ");
            }
            System.out.println("");
        }
    }
    public void solucionesPrincipales(){
        for(int i = 0; i < listaPrincipales.size(); i++){
            listaSolucionesPrincipales.add(backtrackSoluciones(listaPrincipales.get(i).numero,listaPrincipales.get(i).casillas));
        }
    }
    public boolean ponerVertical(int cantidad,int x,int y){
        int contador = 0;
        while(contador != cantidad){
            if(tableroLogico[x][y].principal || tableroLogico[x][y].principal2){
                return false;
            }else{
                x++;
                contador++;
            }
        }
        return true;
    }
    public boolean libreColumna(int x, int y){
        while(y >= 0){
            if(tableroLogico[x][y].principal == true && tableroLogico[x][y].orientacion == "H"){
                return false;
            }else if(tableroLogico[x][y].principal2 == true && tableroLogico[x][y].orientacion == "H"){
                return true;
            }else{
                y--;
            }
        }
        return true;
    }
    public void generarTableroLogicoJuego(){
        
        //Random rnd = new Random();
        //int horizontales =  (int) (rnd.nextDouble() * 4 + 8);
        int horizontales = 10;
        int contadorIteracionesHorizontales = 0;
        ArrayList<Integer> camposHorizontalesUsados = new ArrayList<>();
        for(int i = 0; i < horizontales;){
            Random rnd1 = new Random();
            int cantidadCasillas =  (int) (rnd1.nextDouble() * 7 + 3);
            int numeroColocar = generarNumeroRandom(cantidadCasillas);
            int x = 0;
            while(true){
                Random numeroRandom = new Random();
                int posicionX =  (int)(numeroRandom.nextDouble() * 13 + 1);
                if(!camposHorizontalesUsados.contains(posicionX)){
                    camposHorizontalesUsados.add(posicionX);
                    x = posicionX;
                    break;
                }
            }
            Random numeroRandomY = new Random();
            int y =  (int)(numeroRandomY.nextDouble() * (14 - cantidadCasillas - 1));
            //System.out.println("X: "+Integer.toString(x)+" Y: "+Integer.toString(y));
            tableroLogico[x][y].setNumero(numeroColocar);
            //System.out.println("Se arregĺó");
            tableroLogico[x][y].setPrincipal(true);
            tableroLogico[x][y].cantidadCasillas = cantidadCasillas;
            tableroLogico[x][y].setOrientacion("H");
            CasillaPrincipal nuevo = new CasillaPrincipal(x,y,cantidadCasillas,0,numeroColocar);
            listaPrincipales.add(nuevo);
            if(contadorIteracionesHorizontales == 25000){
                System.out.println("....");
                contadorIteracionesHorizontales = 0;
                inicializarTableroLogico();
                listaPrincipales.clear();
                generarTableroLogicoJuego();
                break;
            }
            contadorIteracionesHorizontales++;
            ArrayList<int[]> listaSoluciones = backtrackNumeros(numeroColocar,cantidadCasillas,x,y+1,0);
            Random solucionRandom = new Random();
            int posicionSolucion =  (int)(solucionRandom.nextDouble() * listaSoluciones.size());
            if(listaSoluciones.size() == 0){
                
            }else{
            int[] solucionUsar = listaSoluciones.get(posicionSolucion);
            int y2 = y+1;
            //System.out.println(y2);
            //System.out.println(cantidadCasillas);
            for(int j = 0; j < cantidadCasillas; j++){
                tableroLogico[x][y2].setNumero(solucionUsar[j]);
                y2++;   
            }
            //System.out.println(y2);
            tableroLogico[x][y2].setNumero(numeroColocar);
            tableroLogico[x][y2].principal2 = true;
            tableroLogico[x][y2].orientacion = "H";
            i++;
            }
        }
        //imprimirMatriz();
        //Random randomVerticales = new Random();
        //int verticales =  (int) (randomVerticales.nextDouble() * 4 + 8);
        int verticales = 10;
        int contadorIteraciones = 0;
        ArrayList<Integer> camposVerticalesUsados = new ArrayList<>();
        for(int i = 0; i < verticales;){
            int posicionX;
            int posicionY;
            int casillasNumero;
            while(true){
                Random rnd1 = new Random();
                int cantidadCasillas =  (int) (rnd1.nextDouble() * 7 + 3);
                int y = 0;
                while(true){
                    Random numeroRandom = new Random();
                    int y2 =  (int)(numeroRandom.nextDouble() * 13 + 1);
                    if(!camposVerticalesUsados.contains(y2)){
                        //camposHorizontalesUsados.add(posicionX);
                        y = y2;
                        break;
                    }
                }
                Random numeroRandomX = new Random();
                int x =  (int)(numeroRandomX.nextDouble() * (14 - cantidadCasillas -1));
                if(tableroLogico[x][y].getNumero() == 0 && ponerVertical(cantidadCasillas+1,x+1,y) && libreColumna(x+cantidadCasillas+1, y)){
                    posicionX = x;
                    posicionY = y;
                    casillasNumero = cantidadCasillas;
                    break;
                }
            }
            //System.out.println(contadorIteraciones);
            if(contadorIteraciones == 25000){
                System.out.println("...");
                contadorIteraciones = 0;
                inicializarTableroLogico();
                listaPrincipales.clear();
                generarTableroLogicoJuego();
                break;
            }
            contadorIteraciones++;
            int numeroColocar = generarNumeroRandom(casillasNumero);
            ArrayList<int[]> listaSoluciones = backtrackNumeros(numeroColocar,casillasNumero,posicionX+1,posicionY,1);
            if(!listaSoluciones.isEmpty()){
                Random solucionRandom = new Random();
                int posicionSolucion =  (int)(solucionRandom.nextDouble() * listaSoluciones.size());
                int[] solucionUsar = listaSoluciones.get(posicionSolucion);
                int posicionX2 = posicionX+1;
                camposVerticalesUsados.add(posicionY);
                tableroLogico[posicionX][posicionY].setNumero(numeroColocar);
                tableroLogico[posicionX][posicionY].cantidadCasillas = casillasNumero;
                tableroLogico[posicionX][posicionY].setPrincipal(true);
                tableroLogico[posicionX][posicionY].orientacion = "V";
                CasillaPrincipal nuevo = new CasillaPrincipal(posicionX,posicionY,casillasNumero,1,numeroColocar);
                listaPrincipales.add(nuevo);
               
                for(int j = 0; j < casillasNumero; j++){
                    tableroLogico[posicionX2][posicionY].setNumero(solucionUsar[j]);
                    posicionX2++;
                    
                }
                tableroLogico[posicionX2][posicionY].setNumero(numeroColocar);
                tableroLogico[posicionX2][posicionY].principal2 = true;
                tableroLogico[posicionX2][posicionY].orientacion = "V";
                i++;
            }
        }
        
    
    }
   
    public int generarNumeroRandom(int largo){
        if(largo == 3){
            Random rnd = new Random();
            int numero =  (int) (rnd.nextDouble() * 4 + 6);
            return numero;
        }else if(largo == 4){
            Random rnd = new Random();
            int numero =  (int) (rnd.nextDouble() * 5 + 10);
            return numero;
        }else if(largo == 5){
            Random rnd = new Random();
            int numero =  (int) (rnd.nextDouble() * 6 + 15);
            return numero;
        }else if(largo == 6){
            Random rnd = new Random();
            int numero =  (int) (rnd.nextDouble() * 7 + 21);
            return numero;
        }else if(largo == 7){
            Random rnd = new Random();
            int numero =  (int) (rnd.nextDouble() * 8 + 28);
            return numero;
        }else if(largo == 8){
            Random rnd = new Random();
            int numero =  (int) (rnd.nextDouble() * 8 + 36);
            return numero;
        }else{
            return 45;
        }
    }
      
            
            
        
    public int cantidadCasillas(int numero){
        if(numero <= 9 && numero >=6 ){
            return 3;
        }else if(numero <= 14 && numero >= 10){
            return 4;
        }else if(numero <= 20 && numero >= 15){
            return 5;
        }else if(numero <= 27 && numero >= 21){
            return 6;
        }else if(numero <= 35 && numero >= 28){
            return 7;
        }else if(numero <=44 && numero >= 36){
            return 8;
        }else{
            return 9;
        }
    }
    
    public void generarTableroLabelsJuego(int posicionx,int posiciony){
        for(int i = 0; i < DIMENSIONES; i++){
            for(int j = 0; j < DIMENSIONES; j++){
                //System.out.println("Aca me caigo");
                if(tableroLogico[i][j].getNumero() == 0 && !tableroLogico[i][j].negativo){ // Arreglar, es para pruebas nada mas
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.LIGHT_GRAY);
                }else if(tableroLogico[i][j].getNumero() == 0 && tableroLogico[i][j].negativo ){
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.BLACK);
                    tableroLabels1[i][j].setText((Integer.toString(tableroLogico[i][j].getNumero())));
                    tableroLabels1[i][j].setEditable(false);
                }else if(tableroLogico[i][j].getNumero() != 0 && tableroLogico[i][j].principal2 == true){
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.GRAY);
                    tableroLabels1[i][j].setText((Integer.toString(tableroLogico[i][j].getNumero())+"/"));
                    tableroLabels1[i][j].setEditable(false);
                }else{
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.GRAY);
                    tableroLabels1[i][j].setText("/"+Integer.toString(tableroLogico[i][j].getNumero()));
                    tableroLabels1[i][j].setEditable(false);
                }
                
            } 
        }
       
    }
    public static boolean verEspaciosLibres(int x,int y,int cantidad){
        int x1 = x-1; 
            int y1 = y;
            while(x1 != 0){
                if(tableroLogico[x1][y1].getNumero() == 0){
                    break;
                }else if(tableroLogico[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    x1--;
            }
            x1 = x+1;
            y1 = y;
            
            while(x1 != 14 ){
                //System.out.println("X: "+Integer.toString(x1)+" Y: "+Integer.toString(y));
                if(tableroLogico[x1][y1].getNumero() == 0){
                    break;
                }else if(tableroLogico[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    x1++;
            }
            x1 = x;
            y1 = y-1;
            while(y1 != 0){
                if(tableroLogico[x1][y1].getNumero() == 0){
                    break;
                }else if(tableroLogico[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    y1--;
            }
            x1 = x;
            y1 = y+1;
            while(y1 != 14 ){
                if(tableroLogico[x1][y1].getNumero() == 0){
                    break;
                }else if(tableroLogico[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    y1++;
            }
            return true;
    }
    
    //------------------------Aqui empieza la funcion de ver numeros que sumados dan un numero x-------------------------------
    public static ArrayList<int[]>  backtrackNumeros(int numero,int casillas,int x, int y,int orientacion){
        int[]lista = new int[casillas];
        ArrayList<int[]> listaFinal = new ArrayList<>();
        generar(0,casillas,0,numero,lista,listaFinal,x,y,orientacion);
        return listaFinal;
    }
    public static void generar(int num,int cantidad,int k,int meta,int[] lista,ArrayList<int[]> listaFinal,int x, int y,int orientacion){
        if(k == cantidad){
            //System.out.println("Entre");
            listaFinal.add((int[])lista.clone());
        }else{
            for(int j = 1; j<10; j++){
                for(int p = k; p<lista.length; p++){
                    lista[p] = 0;
                }
                if(tableroLogico[x][y].getNumero() != 0 && tableroLogico[x][y].getNumero() == j){
                    if(verEspaciosLibres(x,y,j)){
                        if(orientacion == 0 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 0 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generar(num+j,cantidad,k+1,meta,lista,listaFinal,x,y+1,orientacion);
                        }else if(orientacion == 1 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 1 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generar(num+j,cantidad,k+1,meta,lista,listaFinal,x+1,y,orientacion);
                        }
                    }
                    
                }else if(tableroLogico[x][y].getNumero() == 0){
                    if(verEspaciosLibres(x,y,j)){
                        if(orientacion == 0 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 0 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generar(num+j,cantidad,k+1,meta,lista,listaFinal,x,y+1,orientacion);
                        }else if(orientacion == 1 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 1 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generar(num+j,cantidad,k+1,meta,lista,listaFinal,x+1,y,orientacion);
                        }
                    }
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
    public void llenarTablero(int x,int y,int[] lista, int orientacion){
        if(orientacion == 0){
            y++;
            for(int i = 0; i < lista.length; i++){
                tableroLogico[x][y].setNumero(lista[i]);
                tableroLabels1[x][y].setText(Integer.toString(lista[i]));
                y++;
            }
        }else{
            x++;
            for(int i = 0; i < lista.length; i++){
                tableroLogico[x][y].setNumero(lista[i]);
                tableroLabels1[x][y].setText(Integer.toString(lista[i]));
                x++;
            }
        }
    }
    
    public void llenarPrincipales(){ //Mete a la lista de principales que están en la matriz logica
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                if(tableroLogico[i][j].principal && tableroLogico[i][j].orientacion == "H"){
                    int orientacion = 0;
                    CasillaPrincipal nuevo = new CasillaPrincipal(i,j,tableroLogico[i][j].cantidadCasillas,orientacion,tableroLogico[i][j].numero);
                    listaPrincipales.add(nuevo);
                }
            }
        }
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                if(tableroLogico[i][j].principal && tableroLogico[i][j].orientacion == "V"){
                    int orientacion = 1;
                    CasillaPrincipal nuevo = new CasillaPrincipal(i,j,tableroLogico[i][j].cantidadCasillas,orientacion,tableroLogico[i][j].numero);
                    listaPrincipales.add(nuevo);
                }
            }
        }
    }
    public Casilla[][] limpiarCeros(int x, int y, int orientacion, int largo, Casilla[][] matriz){
        if(orientacion == 0){
            y++;
            for(int i = 0; i < largo; i++){
                matriz[x][y].numero = 0;
                matriz[x][y].orientacion = "";
                matriz[x][y].principal = false;
                matriz[x][y].principal2 = false;
                matriz[x][y].negativo = false;
                matriz[x][y].cantidadCasillas = 0;
                y++;
            }
            return matriz;
        }else{
            x++;
            for(int i = 0; i < largo; i++){
                matriz[x][y].numero = 0;
                matriz[x][y].orientacion = "";
                matriz[x][y].principal = false;
                matriz[x][y].principal2 = false;
                matriz[x][y].negativo = false;
                matriz[x][y].cantidadCasillas = 0;
                x++;
            }
            return matriz;
            
        }
    }
    //Terminar el backtrack luego ...
    public ArrayList<Casilla[][]> backtrackResolver(){
        Casilla[][] matrizLogica = new Casilla[14][14];
        matrizLogica = clonarMatriz(tableroLogico);
        ArrayList<Casilla[][]> solucionesMatriz = new ArrayList<>();
        int contador = 0;
        int largoIteraciones = listaPrincipales.size();
        //System.out.println(largoIteraciones);
        //imprimirMatriz(matrizLogica);
        //System.out.println(listaSolucionesPrincipales.get(0).size());
        generarSolucionKakuro(contador,matrizLogica,solucionesMatriz,largoIteraciones);
        return solucionesMatriz;
    }
    public void generarSolucionKakuro(int contador,Casilla[][] matriz, ArrayList<Casilla[][]> soluciones, int largo){
        //System.out.println(pruebaIteraciones);
        //System.out.println(contador);
        //pruebaIteraciones++;
        if(contador == largo){
            soluciones.add(clonarMatriz(matriz));
            imprimirMatriz(matriz);
            //System.out.println("----------------------------------------------");
            //System.out.println("Entré cabrones");
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
                    matriz = llenarTableroMatriz(listaPrincipales.get(contador).x,listaPrincipales.get(contador).y,listaSolucionesPrincipales.get(contador).get(i),listaPrincipales.get(contador).orientacion,matriz);
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
    //------------------------------------------------------------------------------------------------------------------------
    public static ArrayList<int[]>  backtrackSoluciones(int numero,int casillas){
        int[]lista = new int[casillas];
        ArrayList<int[]> listaFinal = new ArrayList<>();
        generarSoluciones(0,casillas,0,numero,lista,listaFinal);
        return listaFinal;
    }
    public static void generarSoluciones(int num,int cantidad,int k,int meta,int[] lista,ArrayList<int[]> listaFinal){
        if(k == cantidad){
            //System.out.println(Arrays.toString(lista));
            listaFinal.add((int[])lista.clone());
            //System.out.println(Arrays.toString(listaFinal.get(contador)));
            
        }else{
            for(int j = 1; j<10; j++){
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

    public boolean sirveSolucion(Casilla[][] matriz,int[] lista,int x, int y, int orientacion){
        if(orientacion == 0){
            y++;
            for(int i = 0; i < lista.length; i++){
                if(matriz[x][y].getNumero() != 0 && matriz[x][y].getNumero() != lista[i]){
                    return false;
                }else if(!verEspaciosLibresMatriz(x, y, lista[i], matriz)){
                    return false;
                }else
                    y++;
            }
            return true;
        }else{
            x++;
            for(int i = 0; i < lista.length; i++){
                if(matriz[x][y].getNumero() != 0 && matriz[x][y].getNumero() != lista[i]){
                    return false;
                }else if(!verEspaciosLibresMatriz(x, y, lista[i], matriz)){
                    return false;
                }else
                    x++;
            }
            return true;
        }
        
    }
    public static boolean verEspaciosLibresMatriz(int x,int y,int cantidad,Casilla[][] matriz){
        int x1 = x-1; 
            int y1 = y;
            while(x1 != 0){
                if(matriz[x1][y1].getNumero() == 0){
                    break;
                }else if(matriz[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    x1--;
            }
            x1 = x+1;
            y1 = y;
            
            while(x1 != 14 ){
                //System.out.println("X: "+Integer.toString(x1)+" Y: "+Integer.toString(y));
                if(matriz[x1][y1].getNumero() == 0){
                    break;
                }else if(matriz[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    x1++;
            }
            x1 = x;
            y1 = y-1;
            while(y1 != 0){
                if(matriz[x1][y1].getNumero() == 0){
                    break;
                }else if(matriz[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    y1--;
            }
            x1 = x;
            y1 = y+1;
            while(y1 != 14 ){
                if(matriz[x1][y1].getNumero() == 0){
                    break;
                }else if(matriz[x1][y1].getNumero() == cantidad){
                    return false;
                }else
                    y1++;
            }
            return true;
    }
    //------------------------Aqui termina la funcion de ver numeros que sumados dan un numero x-------------------------------
    public static ArrayList<int[]>  bactrackFinal(int numero,int casillas,int x, int y,int orientacion,Casilla[][] matriz){
        int[]lista = new int[casillas];
        ArrayList<int[]> listaFinal = new ArrayList<>();
        generarBacktrack(0,casillas,0,numero,lista,listaFinal,x,y,orientacion,matriz);
        return listaFinal;
    }
    public static void generarBacktrack(int num,int cantidad,int k,int meta,int[] lista,ArrayList<int[]> listaFinal,int x, int y,int orientacion,Casilla[][] matriz){
        if(k == cantidad){
            //System.out.println("Entre");
            listaFinal.add((int[])lista.clone());
        }else{
            for(int j = 1; j<10; j++){
                for(int p = k; p<lista.length; p++){
                    lista[p] = 0;
                }
                if(matriz[x][y].getNumero() != 0 && matriz[x][y].getNumero() == j){
                    if(verEspaciosLibresMatriz(x,y,j,matriz)){
                        if(orientacion == 0 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 0 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generarBacktrack(num+j,cantidad,k+1,meta,lista,listaFinal,x,y+1,orientacion,matriz);
                        }else if(orientacion == 1 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 1 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generarBacktrack(num+j,cantidad,k+1,meta,lista,listaFinal,x+1,y,orientacion,matriz);
                        }
                    }
                    
                }else if(matriz[x][y].getNumero() == 0){
                    if(verEspaciosLibresMatriz(x,y,j,matriz)){
                        if(orientacion == 0 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 0 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generarBacktrack(num+j,cantidad,k+1,meta,lista,listaFinal,x,y+1,orientacion,matriz);
                        }else if(orientacion == 1 && num+j < meta && k+1 != cantidad && estaNumero(lista,j) == false || orientacion == 1 && num+j == meta && k+1 == cantidad && estaNumero(lista,j) == false){
                            lista[k] = j;
                            generarBacktrack(num+j,cantidad,k+1,meta,lista,listaFinal,x+1,y,orientacion,matriz);
                        }
                    }
                }
                
            }
        }
    }
    public void guardarFile(){
        try{
            FileOutputStream fos = new FileOutputStream("listaMatrices.ser");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(listaMatrices);
            out.close();
            System.out.println("File listo");
        }catch (IOException ex) {
            Logger.getLogger(ventanaKakuro.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Hubo un error");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelJuego = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnResolver = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kakuro\n");
        setMinimumSize(new java.awt.Dimension(1366, 768));

        panelJuego.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/luffyImagen.jpeg"))); // NOI18N

        btnResolver.setText("Resolver");
        btnResolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResolverActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelJuegoLayout = new javax.swing.GroupLayout(panelJuego);
        panelJuego.setLayout(panelJuegoLayout);
        panelJuegoLayout.setHorizontalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuegoLayout.createSequentialGroup()
                .addGap(182, 666, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuegoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnResolver)
                .addGap(117, 117, 117))
        );
        panelJuegoLayout.setVerticalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJuegoLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResolver)
                    .addComponent(btnGuardar))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResolverActionPerformed
        // TODO add your handling code here:
        /*ArrayList<Casilla[][]> solucionesTablero = backtrackResolver();
        if(solucionesTablero.size() != 0){
            System.out.println("Sirve");
            imprimirMatriz(solucionesTablero.get(solucionesTablero.size()-1));
        }else{
            System.out.println("Ni verga");
        }*/
        Casilla[][] matrizLogica = clonarMatriz(tableroLogico);;
        ArrayList<int[]> listaSoluciones1 = new ArrayList<>();
        if(listaPrincipales.get(0).orientacion == 0){
            listaSoluciones1 = bactrackFinal(listaPrincipales.get(0).numero, listaPrincipales.get(0).casillas, listaPrincipales.get(0).x, listaPrincipales.get(0).y+1,listaPrincipales.get(0).orientacion, matrizLogica);
        }else{
            listaSoluciones1 = bactrackFinal(listaPrincipales.get(0).numero, listaPrincipales.get(0).casillas, listaPrincipales.get(0).x+1, listaPrincipales.get(0).y,listaPrincipales.get(0).orientacion, matrizLogica);
        }
        Casilla[][] matrizLogica2 = new Casilla[14][14];
        System.out.println(listaPrincipales.get(0).numero);
        //for(int i = 0; i < listaSoluciones1.size(); i++){
            matrizLogica2 = clonarMatriz(matrizLogica);
            matrizLogica2 = llenarTableroMatriz(listaPrincipales.get(0).x,listaPrincipales.get(0).y,listaSoluciones1.get(0),listaPrincipales.get(0).orientacion,matrizLogica2);
            HiloBacktrack hilo1 = new HiloBacktrack(matrizLogica2,listaPrincipales);
            hilo1.start();
        //}
    }//GEN-LAST:event_btnResolverActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        ventana.setVisible(true);
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventanaKakuro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaKakuro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaKakuro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaKakuro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaKakuro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnResolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelJuego;
    // End of variables declaration//GEN-END:variables
}
