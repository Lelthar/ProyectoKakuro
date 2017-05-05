/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;
import modelo.Casilla;
import modelo.CasillaPrincipal;
import modelo.HiloBacktrack;
import modelo.MatrizGuardar;

/**
 *
 * @author gerald
 */
public class ventanaKakuro extends javax.swing.JFrame {

    public VentanaGuardar ventana = new VentanaGuardar(this);
    public VetanaCargar ventanaCargar;
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
    public boolean generar = true;
    public boolean cargar = true;
    
    public ventanaKakuro() {
        initComponents();
        //read_file();
        //numero = (listaMatrices.size());
        //ventanaCargar = new VetanaCargar(this);
        /*//ventana.dispose();
        inicializarTableroLogico();
        generarTableroLogicoJuego();
        //imprimirMatriz(tableroLogico);
        insertarNegativos();
        limpiarTablero();
        limpiarNegativos();
        
        listaPrincipales = OrdenamientoBurbujaArrayList(listaPrincipales);
        generarTableroLabelsJuego(10, 40);
        //llenarPrincipales();
        //llenarPrincipales();
        //solucionesPrincipales();
        //System.out.println(listaMatrices.size());*/
        
    }
    public void generarTableroNuevo(){
        if(generar){
            inicializarTableroLogico();
            generarTableroLogicoJuego(26);
            insertarNegativos();
            generarTableroLlenar();
            //System.out.println("Pasó");
            //imprimirMatriz(tableroLogico);
            limpiarTablero();
            limpiarNegativos();
            //read_file();
            listaPrincipales = OrdenamientoBurbujaArrayList(listaPrincipales);
            generarTableroLabelsJuego(10, 40);
            generar = false;
            cargar = false;
            
        }
    }
    public void insertarNegativos(){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                if(tableroLogico[i][j].getNumero() == 0 && tableroLogico[i][j].getNumero2() == 0){
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
                if(tableroLogico[i][j].getNumero() != -1 && tableroLogico[i][j].principal2 == false && tableroLogico[i][j].principal == false){
                    tableroLogico[i][j].setNumero(0);
                    tableroLogico[i][j].setNumero2(0);
                }
            }
        }
    }
    public Casilla[][] clonarMatriz(Casilla[][] matriz){
        Casilla[][] nuevo = new Casilla[matriz.length][matriz.length];

        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz.length; j++){
                nuevo[i][j] = new Casilla();
                nuevo[i][j].numero2 = matriz[i][j].numero2;
                nuevo[i][j].numero = matriz[i][j].numero;
                nuevo[i][j].principal = matriz[i][j].principal;
                nuevo[i][j].principal2 = matriz[i][j].principal2;
                nuevo[i][j].cantidadCasillas = matriz[i][j].cantidadCasillas;
                nuevo[i][j].cantidadCasillas2 = matriz[i][j].cantidadCasillas2;
                nuevo[i][j].casillaFinal = matriz[i][j].casillaFinal;
                nuevo[i][j].negativo = matriz[i][j].negativo;
            }
        }
        return nuevo;
    }
    public void inicializarTableroLogico(){
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                tableroLogico[i][j] = new Casilla();
                tableroLogico[i][j].numero = 0; //Es el numero de las casillas en horizontal
                tableroLogico[i][j].numero2 = 0; //Es el numero de las casillas en vertical
                tableroLogico[i][j].principal = false; //Dice si se está usando un numero en horizontal
                tableroLogico[i][j].principal2 = false; //Dice si se está usando un numero en vertical 
                tableroLogico[i][j].cantidadCasillas = 0; // La cantidad de casillas horizontales
                tableroLogico[i][j].cantidadCasillas2 = 0; //La cantidad de casillas verticales
                tableroLogico[i][j].casillaFinal = false; // Indice si es la ultima casilla de la suma de un numero
                tableroLogico[i][j].negativo = false; // Es para limpiar la matriz, no es necesarios
            }
        }
    }
    public void imprimirMatriz(Casilla[][] matriz){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                if(matriz[i][j].getNumero() == 0 && matriz[i][j].getNumero2() == 0){
                    System.out.print(Integer.toString(0)+","+Integer.toString(0)+"|");
                }else if(matriz[i][j].getNumero() != 0 && matriz[i][j].getNumero2() != 0){
                    System.out.print(Integer.toString(matriz[i][j].getNumero())+","+Integer.toString(matriz[i][j].getNumero2())+"|");
                }else if(matriz[i][j].getNumero() == 0 && matriz[i][j].getNumero2() != 0){
                    System.out.print(Integer.toString(0)+","+Integer.toString(matriz[i][j].getNumero2())+"|");
                }else{
                    System.out.print(Integer.toString(matriz[i][j].getNumero())+","+Integer.toString(0)+"|");
                }
                    
                
            }
            System.out.println("");
        }
    }
    public void solucionesPrincipales(){
        for(int i = 0; i < listaPrincipales.size(); i++){
            listaSolucionesPrincipales.add(backtrackSoluciones(listaPrincipales.get(i).numero,listaPrincipales.get(i).casillas));
        }
    }
    
    /*
     *Arreglar este metodo por el atributio de horizontal que fue borrado
     *
    */
    public boolean libreColumna(int x, int y){
        while(y >= 0){
            if(tableroLogico[x][y].principal == true /*&& tableroLogico[x][y].orientacion == "H"*/){
                return false;
            }else if(tableroLogico[x][y].principal2 == true /* && tableroLogico[x][y].orientacion == "H" */){
                return false;
            }else{
                y--;
            }
        }
        return true;
    }
    /*Revisa si en vertical desde las coordenadas que le den, está libre para poner la casilla principal o principal2
     De entrada recibe la cantidad de casillas que tiene el numero a colocar, y X y Y son las coordenadas donde está el numero
    */
    public boolean ponerVertical(int cantidad,int x,int y){
        int contador = 0;
        x++;
        while(contador != cantidad){
            //System.out.println(contador);
            if(contador + 1 == cantidad && x != 13){
                if(tableroLogico[x+1][y].numero != 0 && !tableroLogico[x+1][y].principal && !tableroLogico[x+1][y].principal2){
                    return false;
                }else if(tableroLogico[x][y-1].casillaFinal == true){
                    return false;
                }else{
                    x++;
                    contador++;
                }
            }else if(tableroLogico[x][y].principal == true || tableroLogico[x][y].principal2 == true){
                return false;
            }else if(tableroLogico[x][y-1].casillaFinal == true){
                return false;
            }else{
                x++;
                contador++;
            }
        }
        return true;
    }
    /*Revisa si en horizontal desde las coordenadas que le den, está libre para poner la casilla principal o principal2
     De entrada recibe la cantidad de casillas que tiene el numero a colocar, y X y Y son las coordenadas donde está el numero
    */
    public boolean ponerHorizontal(int cantidad,int x,int y){
        int contador = 0;
        y++;
        while(contador != cantidad){
            if(tableroLogico[x][y].principal){
                return false;
            }else if(tableroLogico[x][y].principal2){
                return false;
            }else if(tableroLogico[x][y].numero != 0){
                return false;
            }
            else{
                y++;
                contador++;
            }
        }
        return true;
    }
    public void generarTableroLogicoJuego(int cantidadFilas){
        
        int horizontales = cantidadFilas;
        int contadorIteracionesHorizontales = 0;
        ArrayList<Integer> camposHorizontalesUsados1 = new ArrayList<>();
        ArrayList<Integer> camposVerticalesUsados1 = new ArrayList<>();
        boolean solucionEncontrada1 = false;
        for(int i = 0; i < horizontales;){
            int numeroColocar = 0;
            int cantidadCasillas = 0;
            int x = 0;
            int y = 0;
            
            while(true){
                contadorIteracionesHorizontales++;
                if(contadorIteracionesHorizontales == 100000){
                    //System.out.println("...1");
                    inicializarTableroLogico();
                    generarTableroLogicoJuego(cantidadFilas);
                    solucionEncontrada1 = true;
                    break;
                }
                Random rnd1 = new Random();
                cantidadCasillas =  (int) (rnd1.nextDouble() * 6 + 2);
                numeroColocar = generarNumeroRandom(cantidadCasillas);
                Random numeroRandomY = new Random();
                Random numeroRandom = new Random();
                int posicionY =  (int)(numeroRandomY.nextDouble() * (14 - cantidadCasillas ));
                int posicionX =  (int)(numeroRandom.nextDouble() * 13 + 1);
                //Compara si el X y Y ya no se pusieron, y tambien que si con esa posicion va a servir para llenar la matriz
                if(tableroLogico[posicionX][posicionY].numero == 0 && ponerHorizontal(cantidadCasillas,posicionX,posicionY)){ 
                    camposHorizontalesUsados1.add(posicionX);
                    camposVerticalesUsados1.add(posicionY);
                    x = posicionX;
                    y = posicionY;
                    break;
                }
            }
 
            
            //CasillaPrincipal nuevo = new CasillaPrincipal(x,y,cantidadCasillas,0,numeroColocar);
            //listaPrincipales.add(nuevo);
            if(solucionEncontrada1){
                break;
            }
            ArrayList<int[]> listaSoluciones = backtrackNumeros(numeroColocar,cantidadCasillas,x,y+1,0);
            Random solucionRandom = new Random();
            int posicionSolucion =  (int)(solucionRandom.nextDouble() * listaSoluciones.size());
            if(listaSoluciones.size() != 0){
                tableroLogico[x][y].setNumero(numeroColocar); //Settea el numero para las horizontales
                tableroLogico[x][y].setPrincipal(true); //Settea que hay un numero en horizontale en esa casilla
                tableroLogico[x][y].setCantidadCasillas(cantidadCasillas); //Settea la cantidad de casillas para el numero horizontal 
            
                int[] solucionUsar = listaSoluciones.get(posicionSolucion);
                int y2 = y+1;

                for(int j = 0; j < cantidadCasillas; j++){
                    if(j+1 == cantidadCasillas){
                        tableroLogico[x][y2].setNumero(solucionUsar[j]);
                        tableroLogico[x][y2].setCasillaFinal(true);
                    }else{
                        tableroLogico[x][y2].setNumero(solucionUsar[j]);
                        y2++; 
                    }
                }
                i++;
            }
        }
        
        int verticales = 12;
        int contadorIteraciones = 0;
        ArrayList<Integer> camposHorizontalesUsados2 = new ArrayList<>();
        ArrayList<Integer> camposVerticalesUsados2 = new ArrayList<>();
        int contadorIteraciones2 = 0;
        boolean solucionEncontrada = false;
        for(int i = 0; i < verticales;){
            int numeroColocar = 0;
            int cantidadCasillas = 0;
            int x = 0;
            int y = 0;
      
            while(true){
                contadorIteraciones2++;
                if(contadorIteraciones2 == 1000000){
                    //System.out.println("...");
                    inicializarTableroLogico();
                    generarTableroLogicoJuego(cantidadFilas);
                    solucionEncontrada = true;
                    break;
                }
                Random rnd1 = new Random();
                cantidadCasillas =  (int) (rnd1.nextDouble() * 8 + 2);
                numeroColocar = generarNumeroRandom(cantidadCasillas);
                Random numeroRandomY = new Random();
                Random numeroRandom = new Random();
                int posicionX =  (int)(numeroRandomY.nextDouble() * (14 - cantidadCasillas));
                int posicionY =  (int)(numeroRandom.nextDouble() * 13 + 1);

                //Compara si el X y Y ya no se pusieron, y tambien que si con esa posicion va a servir para llenar la matriz
                if(tableroLogico[posicionX][posicionY].numero == 0 && tableroLogico[posicionX][posicionY].principal2 == false && ponerVertical(cantidadCasillas,posicionX,posicionY)){ 
                    x = posicionX;
                    y = posicionY;
                    break;
                }else if(tableroLogico[posicionX][posicionY].principal2 == false && tableroLogico[posicionX][posicionY].principal == true && ponerVertical(cantidadCasillas,posicionX,posicionY)){
                    x = posicionX;
                    y = posicionY;
                    break;
                }
            }
            if(solucionEncontrada){
                break;
            }

            //CasillaPrincipal nuevo = new CasillaPrincipal(x,y,cantidadCasillas,0,numeroColocar);
            //listaPrincipales.add(nuevo);
           
            ArrayList<int[]> listaSoluciones = backtrackNumeros(numeroColocar,cantidadCasillas,x+1,y,1);
            Random solucionRandom = new Random();
            int posicionSolucion =  (int)(solucionRandom.nextDouble() * listaSoluciones.size());
            if(listaSoluciones.size() != 0){
                if(i+1 == verticales){
                    System.out.println(contadorIteraciones2);
                }
                tableroLogico[x][y].setNumero2(numeroColocar); //Settea el numero para las horizontales
                tableroLogico[x][y].setPrincipal2(true); //Settea que hay un numero en horizontale en esa casilla
                tableroLogico[x][y].setCantidadCasillas2(cantidadCasillas);//Settea la cantidad de casillas para el numero horizontal 
                int[] solucionUsar = listaSoluciones.get(posicionSolucion);
                int x2 = x+1;
                //System.out.println(y2);
                //System.out.println(cantidadCasillas);
                for(int j = 0; j < cantidadCasillas; j++){
                    if(j+1 == cantidadCasillas){
                        tableroLogico[x2][y].setNumero(solucionUsar[j]);
                        tableroLogico[x2][y].setCasillaFinal(true);
                    }else{
                        tableroLogico[x2][y].setNumero(solucionUsar[j]);
                        x2++; 
                    }
                }
                i++;
            }
        
        }
        
    
    }
    //Llena los cuadros en negro que puedan sumar como un numero
    public void generarTableroLlenar(){
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                if(tableroLogico[i][j].negativo){
                    if(revisarHorizontal(i,j)){
                        tableroLogico[i][j].setNumero(cantidadRellenarHorizontales(i,j));
                        tableroLogico[i][j].setPrincipal(true);
                        tableroLogico[i][j].negativo = false;
                        tableroLogico[i][j].cantidadCasillas = largoCasillasHorizontales(i,j);
                        if(tableroLogico[i][j].numero2 == -1){
                            tableroLogico[i][j].setNumero2(0);
                        }
                    }
                    if(revisarVerticales(i,j)){
                        tableroLogico[i][j].setNumero2(cantidadRellenarVerticales(i,j));
                        tableroLogico[i][j].setPrincipal2(true);
                        tableroLogico[i][j].negativo = false;
                        tableroLogico[i][j].cantidadCasillas2 = largoCasillasVerticales(i,j);
                        if(tableroLogico[i][j].numero == -1){
                            tableroLogico[i][j].setNumero(0);
                        }
                    }
                }
            }
        }
    
    }
    //Revisa si en la casilla negra en la que está, revisa si tiene para llenar 2 o mas cuadros en blanco y retorna true si eso se cumple
    public boolean  revisarHorizontal(int x, int y){
        y++;
        int contador = 0;
        while(y != 14){
            if(tableroLogico[x][y].principal || tableroLogico[x][y].principal2 || tableroLogico[x][y].negativo){ //Compara que sea un casilla principal o cuadro negro
                break;
            }else{
                contador++;
                y++;
            }
        }
        if(contador > 1){ //Revisa si hay mas de una casilla que llenar
            return true;
        }else{
            return false;
        }
        
    }
    /*
     *Retorna el numero que va horizontales
     *
     */
    public int cantidadRellenarHorizontales(int x, int y){
        y++;
        int contador = 0;
        while(y != 14){
            if(tableroLogico[x][y].principal || tableroLogico[x][y].principal2 || tableroLogico[x][y].negativo){ //Compara que sea un casilla principal o cuadro negro
                break;
            }else{
                contador += tableroLogico[x][y].getNumero();
                y++;
            }
        }
        return contador; 
    }
    //Retorna la cantidad de casillas que se van a relllenar
    public int largoCasillasHorizontales(int x, int y){
        y++;
        int contador = 0;
        while(y != 14){
            if(tableroLogico[x][y].principal || tableroLogico[x][y].principal2 || tableroLogico[x][y].negativo){ //Compara que sea un casilla principal o cuadro negro
                break;
            }else{
                contador++;
                y++;
            }
        }
        return contador; 
    }
    //Retorna la cantidad de casillas que se van a relllenar
    public int largoCasillasVerticales(int x, int y){
        x++;
        int contador = 0;
        while(x != 14){
            if(tableroLogico[x][y].principal || tableroLogico[x][y].principal2 || tableroLogico[x][y].negativo){ //Compara que sea un casilla principal o cuadro negro
                break;
            }else{
                contador++;
                x++;
            }
        }
        return contador; 
    }
    /*
     *Retorna el numero que va verticalmente
     *
     */
    public int cantidadRellenarVerticales(int x, int y){
        x++;
        int contador = 0;
        while(x != 14){
            if(tableroLogico[x][y].principal || tableroLogico[x][y].principal2 || tableroLogico[x][y].negativo){ //Compara que sea un casilla principal o cuadro negro
                break;
            }else{
                contador += tableroLogico[x][y].getNumero();
                x++;
            }
        }
        return contador; 
    }
    //Revisa si en la casilla negra en la que está, revisa si tiene para llenar 2 o mas cuadros en blanco y retorna true si eso se cumple
    public boolean  revisarVerticales(int x, int y){
        x++;
        int contador = 0;
        while(x != 14){
            if(tableroLogico[x][y].principal || tableroLogico[x][y].principal2 || tableroLogico[x][y].negativo){ //Compara que sea un casilla principal o cuadro negro
                break;
            }else{
                contador++;
                x++;
            }
        }
        if(contador > 1){ //Revisa si hay mas de una casilla que llenar
            return true;
        }else{
            return false;
        }
        
    }
    public int generarNumeroRandom(int largo){
        if(largo == 2){
            Random rnd = new Random();
            int numero =  (int) (rnd.nextDouble() * 3 + 3);
            return numero;
        }else if(largo == 3){
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
            //System.out.println(largo);
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
                if(tableroLogico[i][j].getNumero() == 0 && !tableroLogico[i][j].negativo && !tableroLogico[i][j].principal2){ //Muestra las casillas blancas para rellenar
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.LIGHT_GRAY);
                }else if(tableroLogico[i][j].negativo == true){ //Muestra las casillas en negro
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.BLACK);
                    tableroLabels1[i][j].setText((Integer.toString(tableroLogico[i][j].getNumero())));
                    //tableroLabels1[i][j].setEditable(false);
                }else if(tableroLogico[i][j].principal2 == true && tableroLogico[i][j].principal == true){ //Muestra las casillas principal y principal2
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.GRAY);
                    tableroLabels1[i][j].setText(Integer.toString(tableroLogico[i][j].getNumero())+" \\ "+(Integer.toString(tableroLogico[i][j].getNumero2())));
                    //tableroLabels1[i][j].setEditable(false);
                }else if(tableroLogico[i][j].principal2 == true){ //Muestra las casillas verticales principales
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.GRAY);
                    tableroLabels1[i][j].setText("\\"+Integer.toString(tableroLogico[i][j].getNumero2()));
                    
                }else if(tableroLogico[i][j].principal == true){ //Muestra las casillas horizontales principales
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.GRAY);
                    tableroLabels1[i][j].setText(Integer.toString(tableroLogico[i][j].getNumero())+"\\");
                    //tableroLabels1[i][j].setEditable(false);
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
    /*
        Arreglar este metodo, por la eliminacion del atributo de orientacion
    
    */
    public void llenarPrincipales(){ //Mete a la lista de principales que están en la matriz logica
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                if(tableroLogico[i][j].principal /*&& tableroLogico[i][j].orientacion == "H"*/){
                    int orientacion = 0;
                    CasillaPrincipal nuevo = new CasillaPrincipal(i,j,tableroLogico[i][j].cantidadCasillas,orientacion,tableroLogico[i][j].numero);
                    listaPrincipales.add(nuevo);
                }
            }
        }
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                if(tableroLogico[i][j].principal /*&& tableroLogico[i][j].orientacion == "V"*/){
                    int orientacion = 1;
                    CasillaPrincipal nuevo = new CasillaPrincipal(i,j,tableroLogico[i][j].cantidadCasillas,orientacion,tableroLogico[i][j].numero);
                    listaPrincipales.add(nuevo);
                }
            }
        }
    }
    /*
        No arregar, porque no sirve para nada
    */
    public Casilla[][] limpiarCeros(int x, int y, int orientacion, int largo, Casilla[][] matriz){
        if(orientacion == 0){
            y++;
            for(int i = 0; i < largo; i++){
                matriz[x][y].numero = 0;
                //matriz[x][y].orientacion = "";
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
                //matriz[x][y].orientacion = "";
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
            FileOutputStream fileOut =  new FileOutputStream("listaMatrices.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(listaMatrices);
            out.close();
            System.out.println("File listo");
        }catch (IOException ex) {
            Logger.getLogger(ventanaKakuro.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Hubo un error");
        }
    }
    public void read_file() {
        
        try {
            FileInputStream fileIn;
            fileIn = new FileInputStream("listaMatrices.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            listaMatrices = (ArrayList<MatrizGuardar>) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ventanaKakuro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ventanaKakuro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventanaKakuro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void cargarMatrizNumero(int pNumero){
        tableroLogico = listaMatrices.get(pNumero).getMatriz();
        listaPrincipales = OrdenamientoBurbujaArrayList(listaPrincipales);
        generarTableroLabelsJuego(10, 40);
        cargar = false;
        generar = false;
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
        btnGenerar = new javax.swing.JButton();
        btnCargar = new javax.swing.JButton();

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

        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        btnCargar.setText("Cargar");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelJuegoLayout = new javax.swing.GroupLayout(panelJuego);
        panelJuego.setLayout(panelJuegoLayout);
        panelJuegoLayout.setHorizontalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJuegoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuegoLayout.createSequentialGroup()
                        .addComponent(btnCargar)
                        .addGap(18, 18, 18)
                        .addComponent(btnGenerar)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnResolver)
                        .addGap(117, 117, 117))))
        );
        panelJuegoLayout.setVerticalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJuegoLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResolver)
                    .addComponent(btnGuardar)
                    .addComponent(btnGenerar)
                    .addComponent(btnCargar))
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

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        // TODO add your handling code here:
        generarTableroNuevo();
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        // TODO add your handling code here:
        ventanaCargar.setVisible(true);
    }//GEN-LAST:event_btnCargarActionPerformed

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
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnResolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelJuego;
    // End of variables declaration//GEN-END:variables
}
