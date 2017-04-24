/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JTextField;
import modelo.Casilla;

/**
 *
 * @author gerald
 */
public class ventanaKakuro extends javax.swing.JFrame {

  
    public static int DIMENSIONES = 14;
    // Tablero con objetos JTextField
    JTextField[][] tableroLabels1 = new JTextField[DIMENSIONES][DIMENSIONES]; //Es el tablero de JTextField
    static Casilla[][] tableroLogico = new Casilla[DIMENSIONES][DIMENSIONES]; //Es el tablero logico de tipo Casilla
    ArrayList<Integer> listaHorizontales = new ArrayList<>(); //Contiene la lista de los numeros que van a ir horizontal
    ArrayList<Integer> listaVerticales = new ArrayList<>(); //Contiene la lista de los numeros que van a ir vertical
    ArrayList<ArrayList<int[]>> listaSolucionesHorizontales = new ArrayList<>();
    ArrayList<ArrayList<int[]>> listaSolucionesVerticales = new ArrayList<>();
    ArrayList<Integer> listaColocadosVerticales = new ArrayList<>();
    ArrayList<Integer> listaColocadosHorizontales = new ArrayList<>();
    
    public ventanaKakuro() {
        initComponents();
        //generarNumerosPrincipales(10); //Le indica cuantos numeros van a tener entre las dos listas de numeros principales
        //generarSolucionesNumeros(5); //Genera las soluciones posibles para los numeros en las listas
        inicializarTableroLogico();
       // generarTableroLogicoJuego();
       // insertarNegativos();
        //slimpiarTablero();
        generarTableroLogicoJuego();
        generarTableroLabelsJuego(10, 40);
        //imprimirMatriz();
        
    }
    public void insertarNegativos(){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                if(tableroLogico[i][j].getNumero() == 0){
                    tableroLogico[i][j].setNumero(-1);
                }
            }
        }
    }
    public void limpiarTablero(){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                if(tableroLogico[i][j].getNumero() != -1 && tableroLogico[i][j].principal == false){
                    tableroLogico[i][j].setNumero(0);
                }
            }
        }
    }
    public int[] solucionBuena(ArrayList<int[]> listaSoluciones, ArrayList<Integer> listaPosiciones,ArrayList<Integer> listaNumeros){
        ArrayList<int[]> listaSolucionesTemp = (ArrayList<int[]>)listaSoluciones.clone();
        ArrayList<int[]> lista = new ArrayList<>();
        for(int i = 0; i<listaPosiciones.size();i++){
            for(int j = 0; j <listaSolucionesTemp.size() ;j++){
                if(listaSolucionesTemp.get(j)[listaPosiciones.get(i)] == listaNumeros.get(i)){
                    lista.add(listaSolucionesTemp.get(j));
                }
            }
            listaSolucionesTemp = (ArrayList<int[]>)lista.clone();
            lista.clear();
        }
        if(listaSolucionesTemp.size() == 0){
            System.out.println("No sirvio");
            return listaSoluciones.get(0);
        }else
            return listaSolucionesTemp.get(0);
    }
    public void inicializarTableroLogico(){
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 14; j++){
                tableroLogico[i][j] = new Casilla(0);
            }
        }
    }
    public void imprimirMatriz(){
        for(int i = 0;i<14;i++){
            for(int j = 0; j<14 ;j++){
                System.out.print(Integer.toString(tableroLogico[i][j].getNumero())+" ");
            }
            System.out.println("");
        }
    }
    public boolean lineaSiguienteCeros(int y){
        for(int i = 0; i<14 ; i++){
            if(tableroLogico[i][y].getNumero() != 0){
                return false;
            }
        }
        return true;
    }
    public boolean ponerVertical(int cantidad,int x,int y){
        int contador = 0;
        while(contador != cantidad){
            if(tableroLogico[x][y].principal){
                return false;
            }else{
                x++;
                contador++;
            }
        }
        return true;
    }
    public void generarTableroLogicoJuego(){
        //Random rnd = new Random();
        //int horizontales =  (int) (rnd.nextDouble() * 4 + 8);
        int horizontales = 11;
        ArrayList<Integer> camposHorizontalesUsados = new ArrayList<>();
        for(int i = 0; i < horizontales; i++){
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
            int y =  (int)(numeroRandomY.nextDouble() * (14 - cantidadCasillas));
            //System.out.println("X: "+Integer.toString(x)+" Y: "+Integer.toString(y));
            tableroLogico[x][y].setNumero(numeroColocar);
            //System.out.println("Se arregĺó");
            tableroLogico[x][y].setPrincipal(true);
            tableroLogico[x][y].cantidadCasillas = cantidadCasillas;
            tableroLogico[x][y].setOrientacion("H");
            ArrayList<int[]> listaSoluciones = backtrackNumeros(numeroColocar,cantidadCasillas,x,y+1,0);
            Random solucionRandom = new Random();
            int posicionSolucion =  (int)(solucionRandom.nextDouble() * listaSoluciones.size());
            /*System.out.println("Numero: "+Integer.toString(numeroColocar));
            System.out.println("Casillas: "+Integer.toString(cantidadCasillas));
            System.out.println(listaSoluciones.size());**/
            int[] solucionUsar = listaSoluciones.get(posicionSolucion);
            int y2 = y+1;
            for(int j = 0; j < cantidadCasillas; j++){
                if(j+1 == cantidadCasillas){
                    tableroLogico[x][y2].setNumero(solucionUsar[j]);
                    tableroLogico[x][y2].setCasillaFinal(true);
                    tableroLogico[x][y2].orientacion = "H";
                }else{
                    tableroLogico[x][y2].setNumero(solucionUsar[j]);
                    y2++;
                }
                
            }
            
        }
        
        //Random randomVerticales = new Random();
        //int verticales =  (int) (randomVerticales.nextDouble() * 4 + 8);
        int verticales = 10;
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
                int x =  (int)(numeroRandomX.nextDouble() * (14 - cantidadCasillas));
                if(tableroLogico[x][y].getNumero() == 0 && ponerVertical(cantidadCasillas,x,y)){
                    posicionX = x;
                    posicionY = y;
                    casillasNumero = cantidadCasillas;
                    break;
                }
            }
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
               
                for(int j = 0; j < casillasNumero; j++){
                    if(j+1 == casillasNumero){
                        tableroLogico[posicionX2][posicionY].setNumero(solucionUsar[j]);
                        tableroLogico[posicionX2][posicionY].setCasillaFinal(true);
                        tableroLogico[posicionX2][posicionY].orientacion = "V";
                    }else{
                        tableroLogico[posicionX2][posicionY].setNumero(solucionUsar[j]);
                        posicionX2++;
                    }
                }
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
  
    
    public void generarNumerosPrincipales(int cantidad){ //Genera la lista principal de numeros que van a estar en el tablero
        for(int i = 0 ; i<cantidad;){
            if(i%2 == 0){
                Random rnd = new Random();
                int numero =  (int) (rnd.nextDouble() * (45-1) + 6);
                if(numero>=6 && numero <=45 && !listaHorizontales.contains(numero)){
                    listaHorizontales.add(numero);
                    i++;
                }
                
            }else{
                Random rnd = new Random();
                int numero =  (int) (rnd.nextDouble() * (45-1) + 6);
                if(numero>=6 && numero <=45 && !listaHorizontales.contains(numero)){
                    listaVerticales.add(numero);
                    i++;
                }
            }
        }
    }
    
    public void generarTableroLabelsJuego(int posicionx,int posiciony){
        for(int i = 0; i < DIMENSIONES; i++){
            for(int j = 0; j < DIMENSIONES; j++){
                //System.out.println("Aca me caigo");
                if(tableroLogico[i][j].getNumero() == 0){ // Arreglar, es para pruebas nada mas
                    if(tableroLogico[i][j].casillaFinal){
                        tableroLabels1[i][j] = new JTextField();
                        panelJuego.add(tableroLabels1[i][j]);
                        tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                        tableroLabels1[i][j].setBackground(Color.red);
                    }else{
                        tableroLabels1[i][j] = new JTextField();
                        panelJuego.add(tableroLabels1[i][j]);
                        tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                        tableroLabels1[i][j].setBackground(Color.LIGHT_GRAY);
                    }
                }else if(tableroLogico[i][j].getNumero() != 0 && !tableroLogico[i][j].principal){
                    if(tableroLogico[i][j].casillaFinal){
                        if(tableroLogico[i][j].getOrientacion() == "H"){
                            tableroLabels1[i][j] = new JTextField();
                            panelJuego.add(tableroLabels1[i][j]);
                            tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                            tableroLabels1[i][j].setBackground(Color.GRAY);
                            tableroLabels1[i][j].setText((Integer.toString(tableroLogico[i][j].getNumero())+"/"));
                        }else{
                            tableroLabels1[i][j] = new JTextField();
                            panelJuego.add(tableroLabels1[i][j]);
                            tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                            tableroLabels1[i][j].setBackground(Color.GRAY);
                            tableroLabels1[i][j].setText(Integer.toString(tableroLogico[i][j].getNumero())+"/");
                        }
                        
                    }else{
                        tableroLabels1[i][j] = new JTextField();
                        panelJuego.add(tableroLabels1[i][j]);
                        tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                        tableroLabels1[i][j].setBackground(Color.GRAY);
                        tableroLabels1[i][j].setText(Integer.toString(tableroLogico[i][j].getNumero()));
                    }
                    
                    //tableroLabels1[i][j].setEditable(false);
                }else{
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.LIGHT_GRAY);
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
     
    //------------------------Aqui termina la funcion de ver numeros que sumados dan un numero x-------------------------------
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kakuro\n");
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setPreferredSize(new java.awt.Dimension(1366, 768));

        panelJuego.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/luffyImagen.jpeg"))); // NOI18N

        javax.swing.GroupLayout panelJuegoLayout = new javax.swing.GroupLayout(panelJuego);
        panelJuego.setLayout(panelJuegoLayout);
        panelJuegoLayout.setHorizontalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuegoLayout.createSequentialGroup()
                .addGap(0, 666, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelJuegoLayout.setVerticalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJuegoLayout.createSequentialGroup()
                .addGap(98, 98, 98)
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelJuego;
    // End of variables declaration//GEN-END:variables
}
