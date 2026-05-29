/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.Date;

public class Oferta {
    private String puesto; // [cite: 62]
    private String descripcion; // [cite: 63]
    private String area; // [cite: 63]
    private Date fechaInicio; // [cite: 64]
    private Date fechaTermino; // [cite: 65]
    
    // Clase de tipo arreglo interna
    private Requisito[] requisitos;
    private int contadorRequisitos;

    public Oferta(String puesto, String descripcion, String area, Date fechaInicio, Date fechaTermino) {
        this.puesto = puesto;
        this.descripcion = descripcion;
        this.area = area;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.requisitos = new Requisito[100]; // Capacidad inicial
        this.contadorRequisitos = 0;
    }

    public boolean agregarRequisito(int orden, String descripcion) { // [cite: 66]
        if (contadorRequisitos < requisitos.length) {
            requisitos[contadorRequisitos] = new Requisito(orden, descripcion);
            contadorRequisitos++;
            return true;
        }
        return false;
    }

    public boolean eliminarRequisito(int orden) { // [cite: 68]
        for (int i = 0; i < contadorRequisitos; i++) {
            if (requisitos[i].getOrden() == orden) {
                // Desplazar elementos para "eliminar"
                for (int j = i; j < contadorRequisitos - 1; j++) {
                    requisitos[j] = requisitos[j + 1];
                }
                requisitos[contadorRequisitos - 1] = null;
                contadorRequisitos--;
                return true;
            }
        }
        return false;
    }

    public Requisito[] getRequisitos() { // [cite: 67]
        // Retorna solo los elementos válidos
        Requisito[] actuales = new Requisito[contadorRequisitos];
        System.arraycopy(requisitos, 0, actuales, 0, contadorRequisitos);
        return actuales;
    }
}
