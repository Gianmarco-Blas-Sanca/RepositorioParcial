/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class Cliente {
    private String RUC; 
    private String razonSocial; 
    private String email; 
    private String contacto; 
    private String telefono;
    private String clave; 
    
    private Rubro rubro; 
    private Oferta[] ofertas;
    private int contadorOfertas;

    public Cliente(String RUC, String razonSocial, String email, String contacto, String telefono, String clave, Rubro rubro) {
        this.RUC = RUC;
        this.razonSocial = razonSocial;
        this.email = email;
        this.contacto = contacto;
        this.telefono = telefono;
        this.clave = clave;
        this.rubro = rubro;
        this.ofertas = new Oferta[50];
        this.contadorOfertas = 0;
    }

    public boolean agregarOferta(Oferta oferta) { 
        if (contadorOfertas < ofertas.length) {
            ofertas[contadorOfertas] = oferta;
            contadorOfertas++;
            return true;
        }
        return false;
    }

    public boolean eliminarOferta(Oferta oferta) {
        for (int i = 0; i < contadorOfertas; i++) {
            if (ofertas[i].equals(oferta)) {
                for (int j = i; j < contadorOfertas - 1; j++) {
                    ofertas[j] = ofertas[j + 1];
                }
                ofertas[contadorOfertas - 1] = null;
                contadorOfertas--;
                return true;
            }
        }
        return false;
    }

    public Oferta[] getOfertas() { // 
        Oferta[] actuales = new Oferta[contadorOfertas];
        System.arraycopy(ofertas, 0, actuales, 0, contadorOfertas);
        return actuales;
    }
}
