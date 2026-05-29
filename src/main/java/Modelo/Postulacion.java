/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.Date;

public class Postulacion {
    private Date fecha; 
    private boolean anulado; 
    private Date fechaAnulacion; 
    private Oferta oferta; 

    public Postulacion(Oferta oferta) {
        this.oferta = oferta;
        this.fecha = new Date(); 
        this.anulado = false;
    }
    
    public void anular() {
        this.anulado = true;
        this.fechaAnulacion = new Date();
    }
    
    public Oferta getOferta() {
        return oferta;
    }
}
