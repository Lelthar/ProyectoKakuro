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
    public int numero;
    public char orientacion; //Puede ser H o V, de Horizontal y Vertical
    public boolean principal; //Dice si es la primer casilla que contiene el numero a formar
    public int cantidadCasillas;

    public Casilla() {
    }

    public Casilla(int numero) {
        this.numero = numero;
    }

    public Casilla(int pNumero, char pOrientacion,boolean pPrincipal) {
        this.numero = pNumero;
        this.orientacion = pOrientacion;
        this.principal = pPrincipal;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public char getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(char orientacion) {
        this.orientacion = orientacion;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
    
    
}
