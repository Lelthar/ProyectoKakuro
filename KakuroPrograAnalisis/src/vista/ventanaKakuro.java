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
    Casilla[][] tableroLogico = new Casilla[DIMENSIONES][DIMENSIONES]; //Es el tablero logico de tipo Casilla
    ArrayList<Integer> listaHorizontales = new ArrayList<>(); //Contiene la lista de los numeros que van a ir horizontal
    ArrayList<Integer> listaVerticales = new ArrayList<>(); //Contiene la lista de los numeros que van a ir vertical
    ArrayList<ArrayList<int[]>> listaSolucionesHorizontales = new ArrayList<>();
    ArrayList<ArrayList<int[]>> listaSolucionesVerticales = new ArrayList<>();
    ArrayList<Integer> listaColocadosVerticales = new ArrayList<>();
    ArrayList<Integer> listaColocadosHorizontales = new ArrayList<>();
    
    public ventanaKakuro() {
        initComponents();
        generarNumerosPrincipales(10); //Le indica cuantos numeros van a tener entre las dos listas de numeros principales
        generarSolucionesNumeros(5); //Genera las soluciones posibles para los numeros en las listas
        inicializarTableroLogico();
        generarTableroLogicoJuego();
        insertarNegativos();
        limpiarTablero();
        generarTableroLabelsJuego(10, 40);
        
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
    public void generarTableroLogicoJuego(){
        for(int i = 0; i < listaHorizontales.size(); i++){
            int casillas = cantidadCasillas(listaHorizontales.get(i));
            int contador = 0;
            int largoMaximo = 13-(casillas+1);
            int numero = 0;
            boolean solucion = false;
                   
            for(int j = 1; j < 14;j++){
                for(int l = 0;l<largoMaximo;l++){
                    if(solucion){
                        break;
                    }else if(tableroLogico[j][l].getNumero() == 0 && tableroLogico[j-1][contador].getNumero() == 0 && !listaColocadosHorizontales.contains(j) && !solucion){
                        if(j != 13 && tableroLogico[j+1][l].numero == 0){
                            //System.out.println("Aqui");
                            tableroLogico[j][l].setNumero(listaHorizontales.get(i));
                            tableroLogico[j][l].setPrincipal(true);
                            tableroLogico[j][l].setOrientacion('H');
                            solucion = true;
                            listaColocadosHorizontales.add(j);
                            numero = j;
                            contador = l;
                            break;
                        }else{
                            //System.out.println("Aqui");
                            tableroLogico[j][l].setNumero(listaHorizontales.get(i));
                            tableroLogico[j][l].setPrincipal(true);
                            tableroLogico[j][l].setOrientacion('H');
                            solucion = true;
                            listaColocadosHorizontales.add(j);
                            //System.out.println("Aqui");
                            numero = j;
                            contador = l;
                            break;
                        }
                    }
                }
            }
            Random rnd = new Random();
            int numero2 =  (int) (rnd.nextDouble() * listaSolucionesHorizontales.get(i).size());
            int[] solucionTemp = (int[])listaSolucionesHorizontales.get(i).get(numero2).clone();
            //System.out.println("Solucion horizontales");
            //System.out.println(Arrays.toString(solucionTemp));
            //System.out.println("Largo: ");
            //System.out.println(solucionTemp.length);
            int numeroNuevo = contador+1;
            int contador2 = 0;
            for(int k = numeroNuevo; k < solucionTemp.length+1; k++){
                if(k+1 == solucionTemp.length+1){
                    tableroLogico[numero][k].setNumero(solucionTemp[contador2]);
                    tableroLogico[numero][k].setCasillaFinal(true);
                    contador2++;
                }else{
                    tableroLogico[numero][k].setNumero(solucionTemp[contador2]);
                    contador2++;
                }
            }
        }
        
        for(int i = 0; i < listaVerticales.size(); i++){
            int casillas = cantidadCasillas(listaVerticales.get(i));
            int contador = 0;
            int largoMaximo = 13-(casillas+1);
            int numero = 0;
            boolean solucion = false;
            int contadorTemp = 13;
                   
            for(int l = 13; l > 0;l--){
                    int j = 0;
                    if(tableroLogico[j][l].getNumero() == 0 && tableroLogico[j][l-1].getNumero() == 0 && !listaColocadosVerticales.contains(l) && !solucion){
                        if(l != 13 && tableroLogico[j][l+1].numero == 0 && tableroLogico[j][l-1].getNumero() == 0){
                            //System.out.println("Aqui");
                            tableroLogico[j][l].setNumero(listaVerticales.get(i));
                            tableroLogico[j][l].setPrincipal(true);
                            tableroLogico[j][l].setOrientacion('V');
                            solucion = true;
                            listaColocadosVerticales.add(l);
                            numero = j;
                            contador = l;
                            break;
                        }
                    }
                
            }
            
            Random rnd = new Random();
            int posicionSolucion =  (int) (rnd.nextDouble() * listaSolucionesVerticales.get(i).size());
            int[] solucionTemp = (int[])listaSolucionesVerticales.get(i).get(posicionSolucion).clone();
            
            int largoLista = listaSolucionesVerticales.get(i).get(0).length;
            int numero2 = numero+1;
            int contador2 = contador;
            if(verEspaciosLibres(numero2,contador2,largoLista)){
               // System.out.println("Solucion verticales");
                //System.out.println(Arrays.toString(solucionTemp));
                for(int k = 0;k<largoLista;k++){
                    //System.out.println("Aca1");
                    if(k+1 == largoLista){
                        tableroLogico[numero2][contador2].setNumero(solucionTemp[k]);
                        tableroLogico[numero2][contador2].setCasillaFinal(true);
                         numero2++;
                    }else{
                    tableroLogico[numero2][contador2].setNumero(solucionTemp[k]);
                    //System.out.println("Aca2");
                    numero2++;
                    }
                }
            }else{
                ArrayList<int[]> solucionesNumero = (ArrayList<int[]>)listaSolucionesVerticales.get(i).clone();
                ArrayList<Integer> listaPosiciones = new ArrayList<>();
                ArrayList<Integer> listaNumeros = new ArrayList<>();
                int numero3 = numero+1;
                int contador3 = contador;
                //System.out.println("Largo de las casillas"+Integer.toString(largoLista));
                //imprimirMatriz();
                for(int k = 0; k < largoLista; k++){
                    //System.out.println(tableroLogico[numero3][contador3].getNumero());
                    if(tableroLogico[numero3][contador3].getNumero() != 0){
                        listaPosiciones.add(k);
                        listaNumeros.add(tableroLogico[numero3][contador3].getNumero());
                        //System.out.println("Numeros que se repiten"+Integer.toString(k));
                        numero3++;
                    }else{
                        //System.out.println("No se repiten");
                        numero3++;
                    }
                }
                System.out.print("Lista de numeros que chocan: ");
                System.out.println((listaNumeros).toString());
                System.out.print("Lista de posiciones que chocan: ");
                System.out.println((listaPosiciones).toString());
                //System.out.println("Largo de la lista de numeros: "+Integer.toString(listaNumeros.size()));
                int[] solucionLista = solucionBuena(solucionesNumero, listaPosiciones, listaNumeros);
                int x = numero +1;
                int y = contador;
                //System.out.println("Solucion verticales");
                //System.out.println(Arrays.toString(solucionLista));
                for(int f = 0; f < largoLista; f++){
                    if( f+1 == largoLista){
                        tableroLogico[x][y].setNumero(solucionLista[f]);
                        tableroLogico[x][y].setCasillaFinal(true);
                       x++;
                    }else{
                        tableroLogico[x][y].setNumero(solucionLista[f]);
                        x++;
                    }
                }
            }
           
            
        }
    
    }
    public boolean verEspaciosLibres(int x,int y,int cantidad){
        for(int i = 0;i<cantidad;i++){
            if(tableroLogico[x][y].numero != 0){
                return false;
            }else
                x++;
        }
        return true;
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
  
    public void generarSolucionesNumeros(int largo){
        for(int i = 0 ; i<largo;i++){
            if(listaHorizontales.get(i)<=9 && listaHorizontales.get(i)>=6){
                listaSolucionesHorizontales.add(backtrackNumeros(listaHorizontales.get(i), 3));
            }else if(listaHorizontales.get(i)<=14 && listaHorizontales.get(i) >= 10){
                listaSolucionesHorizontales.add(backtrackNumeros(listaHorizontales.get(i), 4));
            }else if(listaHorizontales.get(i)<=20 && listaHorizontales.get(i) >= 15){
                listaSolucionesHorizontales.add(backtrackNumeros(listaHorizontales.get(i), 5));
            }else if(listaHorizontales.get(i)<=27 && listaHorizontales.get(i) >= 21){
                listaSolucionesHorizontales.add(backtrackNumeros(listaHorizontales.get(i), 6));
            }else if(listaHorizontales.get(i)<=35 && listaHorizontales.get(i) >= 28){
                listaSolucionesHorizontales.add(backtrackNumeros(listaHorizontales.get(i), 7));
            }else if(listaHorizontales.get(i)<=44 && listaHorizontales.get(i) >= 36){
                listaSolucionesHorizontales.add(backtrackNumeros(listaHorizontales.get(i), 8));
            }else if(listaHorizontales.get(i) >= 45){
                listaSolucionesHorizontales.add(backtrackNumeros(listaHorizontales.get(i), 9));
            }
            
        }
   
        for(int i = 0 ; i<largo;i++){
            if(listaVerticales.get(i)<=9 && listaVerticales.get(i)>=6){
                listaSolucionesVerticales.add(backtrackNumeros(listaVerticales.get(i), 3));
            }else if(listaVerticales.get(i)<=14 && listaVerticales.get(i) >= 10){
                listaSolucionesVerticales.add(backtrackNumeros(listaVerticales.get(i), 4));
            }else if(listaVerticales.get(i)<=20 && listaVerticales.get(i) >= 15){
                listaSolucionesVerticales.add(backtrackNumeros(listaVerticales.get(i), 5));
            }else if(listaVerticales.get(i)<=27 && listaVerticales.get(i) >= 21){
                listaSolucionesVerticales.add(backtrackNumeros(listaVerticales.get(i), 6));
            }else if(listaVerticales.get(i)<=35 && listaVerticales.get(i) >= 28){
                listaSolucionesVerticales.add(backtrackNumeros(listaVerticales.get(i), 7));
            }else if(listaVerticales.get(i)<=44 && listaVerticales.get(i) >= 36){
                listaSolucionesVerticales.add(backtrackNumeros(listaVerticales.get(i), 8));
            }else if(listaVerticales.get(i) >= 45){
                listaSolucionesVerticales.add(backtrackNumeros(listaVerticales.get(i), 9));
            }
            
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
                if(tableroLogico[i][j].getNumero() == 0){
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
                }else if(tableroLogico[i][j].getNumero() == -1){
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.BLACK);
                    tableroLabels1[i][j].setEditable(false);
                }else{
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.LIGHT_GRAY);
                    tableroLabels1[i][j].setText(Integer.toString(tableroLogico[i][j].getNumero()));
                    tableroLabels1[i][j].setEditable(false);
                }
                
            } 
        }
       
    }
    
    //------------------------Aqui empieza la funcion de ver numeros que sumados dan un numero x-------------------------------
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
