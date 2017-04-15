/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuroprograanalisis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
        for(int i = 0; i<20;i++){
            Random rnd = new Random();
            int n = 45;
            int numero =  (int) (rnd.nextDouble() * (n-1) + 6);
            System.out.println(numero);
        }
        
    }
    
  
}
