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
public class CasillaPrincipal {
    public int x;
    public int y;
    public int casillas;
    public int orientacion;
    public int numero;
    public CasillaPrincipal() {
    }

    public CasillaPrincipal(int x, int y, int casillas, int orientacion, int numero) {
        this.x = x;
        this.y = y;
        this.casillas = casillas;
        this.orientacion = orientacion;
        this.numero = numero;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCasillas() {
        return casillas;
    }

    public void setCasillas(int casillas) {
        this.casillas = casillas;
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }
    
}
