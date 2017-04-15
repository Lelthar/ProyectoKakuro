/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.util.ArrayList;
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
    
    public ventanaKakuro() {
        initComponents();
        generarNumerosPrincipales(10); //Le indica cuantos numeros van a tener entre las dos listas de numeros principales
        for(int i = 0;i<listaVerticales.size();i++){
            System.out.println(listaVerticales.get(i));
        }
        generarTableroLabelsJuego(10, 40);
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
    public void generarTableroLogicoJuego(){
        for(int i = 0; i < DIMENSIONES; i++){
            for(int j = 0; j < DIMENSIONES; j++){
                tableroLogico[i][j] = new Casilla(3,'H',true);
            }
        }
    }
    public void generarTableroLabelsJuego(int posicionx,int posiciony){
        for(int i = 0; i < DIMENSIONES; i++){
            for(int j = 0; j < DIMENSIONES; j++){
                if(i == 0){
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.BLACK);
                    tableroLabels1[i][j].setEditable(false); //Hace que la casilla no sea editable
                }else{
                    tableroLabels1[i][j] = new JTextField();
                    panelJuego.add(tableroLabels1[i][j]);
                    tableroLabels1[i][j].setBounds(posicionx+40*i, posiciony+40*j, 40, 40);
                    tableroLabels1[i][j].setBackground(Color.LIGHT_GRAY);
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
