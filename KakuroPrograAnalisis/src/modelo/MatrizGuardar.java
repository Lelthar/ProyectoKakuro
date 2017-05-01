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
public class MatrizGuardar implements Serializable{
    private Casilla[][] matriz;
    private int numero;
    private String nombre;

    public MatrizGuardar(Casilla[][] matriz, int numero, String nombre) {
        this.matriz = matriz;
        this.numero = numero;
        this.nombre = nombre;
    }

    public Casilla[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(Casilla[][] matriz) {
        this.matriz = matriz;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
