/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Usuario
 */
import java.util.Date;

public class Postulante {
    private String email; 
    private String nombres; 
    private String apellidos; 
    private String direccion; 
    private Date nacimiento; 
    private String clave; 
    private GradoEstudio grado;
    private Postulacion[] postulaciones;
    private int contadorPostulaciones;

    public Postulante(String email, String nombres, String apellidos, String direccion, Date nacimiento, String clave) {
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.nacimiento = nacimiento;
        this.clave = clave;
        this.postulaciones = new Postulacion[100]; 
        this.contadorPostulaciones = 0;
    }

    public boolean asignarGradoEstudio(GradoEstudio grado) { 
        this.grado = grado;
        return true;
    }

    public boolean postular(Oferta oferta) { 
        if (contadorPostulaciones < postulaciones.length) {
            postulaciones[contadorPostulaciones] = new Postulacion(oferta);
            contadorPostulaciones++;
            return true;
        }
        return false;
    }

    public boolean anularPostulacion(Postulacion postulacion) {
        for (int i = 0; i < contadorPostulaciones; i++) {
            if (postulaciones[i].equals(postulacion)) {
                postulaciones[i].anular();
                return true;
            }
        }
        return false;
    }

    public Postulacion[] getPostulaciones() { 
        Postulacion[] actuales = new Postulacion[contadorPostulaciones];
        System.arraycopy(postulaciones, 0, actuales, 0, contadorPostulaciones);
        return actuales;
    }
}
