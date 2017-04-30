/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author gerald
 */
public class Casilla {
    public int numero = 0;
    public String orientacion = ""; //Puede ser H o V, de Horizontal y Vertical
    public boolean principal = false; //Dice si es la primer casilla que contiene el numero a formar
    public boolean principal2 = false;
    public int cantidadCasillas = 0;
    public boolean casillaFinal = false;
    public boolean negativo = false;

    public Casilla() {
    }

    public Casilla(int numero) {
        this.numero = numero;
    }

    public Casilla(int pNumero, String pOrientacion,boolean pPrincipal) {
        this.numero = pNumero;
        this.orientacion = pOrientacion;
        this.principal = pPrincipal;
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

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
    
    
}
