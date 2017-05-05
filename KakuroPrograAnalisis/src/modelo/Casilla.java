/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author gerald
 */
public class Casilla implements Serializable{
    public int numero = 0; //Es el numero de las casillas en horizontal
    public int numero2 = 0; //Es el numero de las casillas en vertical
    //public String orientacion = ""; //Puede ser H o V, de Horizontal y Vertical (No se necesita)
    public boolean principal = false; //Dice si se está usando un numero en horizontal
    public boolean principal2 = false; //Dice si se está usando un numero en vertical 
    public int cantidadCasillas = 0; // La cantidad de casillas horizontales
    public int cantidadCasillas2 = 0; //La cantidad de casillas verticales
    public boolean casillaFinal = false; // Indice si es la ultima casilla de la suma de un numero
    public boolean negativo = false; // Es para limpiar la matriz, no es necesarios

    public Casilla() {
    }

    public Casilla(int numero) {
        this.numero = numero;
    }

    public Casilla(int pNumero,boolean pPrincipal) {
        this.numero = pNumero;
        //this.orientacion = pOrientacion;
        this.principal = pPrincipal;
    }

    public int getNumero2() {
        return numero2;
    }

    public void setNumero2(int numero2) {
        this.numero2 = numero2;
    }

    public boolean isPrincipal2() {
        return principal2;
    }

    public void setPrincipal2(boolean principal2) {
        this.principal2 = principal2;
    }

    public int getCantidadCasillas() {
        return cantidadCasillas;
    }

    public void setCantidadCasillas(int cantidadCasillas) {
        this.cantidadCasillas = cantidadCasillas;
    }

    public int getCantidadCasillas2() {
        return cantidadCasillas2;
    }

    public void setCantidadCasillas2(int cantidadCasillas2) {
        this.cantidadCasillas2 = cantidadCasillas2;
    }

    public boolean isNegativo() {
        return negativo;
    }

    public void setNegativo(boolean negativo) {
        this.negativo = negativo;
    }

    public boolean isCasillaFinal() {
        return casillaFinal;
    }

    public void setCasillaFinal(boolean casillaFinal) {
        this.casillaFinal = casillaFinal;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
    
    
}
