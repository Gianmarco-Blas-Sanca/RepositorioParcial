/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import java.util.Date;
import java.lang.reflect.Field;

public class ReclutamientoController {
    private Cliente[] clientes;
    private int contadorClientes;
    private Postulante[] postulantes;
    private int contadorPostulantes;

    public ReclutamientoController() {
        this.clientes = new Cliente[100];
        this.contadorClientes = 0;
        this.postulantes = new Postulante[100];
        this.contadorPostulantes = 0;
    }

    public boolean registrarEmpresa(String ruc, String razonSocial, String email, String contacto, String telefono, Rubro rubro) {
        if (contadorClientes >= clientes.length) {
            return false;
        }
        String claveAutogenerada = "EMP" + (int)(Math.random() * 9000 + 1000);
        Cliente nuevoCliente = new Cliente(ruc, razonSocial, email, contacto, telefono, claveAutogenerada, rubro);
        clientes[contadorClientes] = nuevoCliente;
        contadorClientes++;
        javax.swing.JOptionPane.showMessageDialog(null, "Empresa registrada.\nClave de acceso generada: " + claveAutogenerada);
        return true;
    }

    public boolean registrarPostulante(String email, String nombres, String apellidos, String direccion, Date nacimiento, GradoEstudio grado) {
        if (contadorPostulantes >= postulantes.length) {
            return false;
        }
        String claveAutogenerada = "POST" + (int)(Math.random() * 9000 + 1000);
        Postulante nuevoPostulante = new Postulante(email, nombres, apellidos, direccion, nacimiento, claveAutogenerada);
        nuevoPostulante.asignarGradoEstudio(grado);
        postulantes[contadorPostulantes] = nuevoPostulante;
        contadorPostulantes++;
        javax.swing.JOptionPane.showMessageDialog(null, "Candidato registrado.\nClave enviada a su email: " + claveAutogenerada);
        return true;
    }

    public Cliente autenticarCliente(String email, String clave) {
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i] != null) {
                try {
                    Field fEmail = clientes[i].getClass().getDeclaredField("email");
                    Field fClave = clientes[i].getClass().getDeclaredField("clave");
                    fEmail.setAccessible(true);
                    fClave.setAccessible(true);
                    
                    String cEmail = (String) fEmail.get(clientes[i]);
                    String cClave = (String) fClave.get(clientes[i]);
                    
                    if (email.trim().equalsIgnoreCase(cEmail.trim()) && clave.trim().equals(cClave.trim())) {
                        return clientes[i];
                    }
                } catch (Exception e) {
                    System.out.println("Error de lectura en Cliente: " + e.getMessage());
                }
            }
        }
        return null;
    }

    public Postulante autenticarPostulante(String email, String clave) {
        for (int i = 0; i < contadorPostulantes; i++) {
            if (postulantes[i] != null) {
                try {
                    Field fEmail = postulantes[i].getClass().getDeclaredField("email");
                    Field fClave = postulantes[i].getClass().getDeclaredField("clave");
                    fEmail.setAccessible(true);
                    fClave.setAccessible(true);
                    
                    String pEmail = (String) fEmail.get(postulantes[i]);
                    String pClave = (String) fClave.get(postulantes[i]);
                    
                    if (email.trim().equalsIgnoreCase(pEmail.trim()) && clave.trim().equals(pClave.trim())) {
                        return postulantes[i];
                    }
                } catch (Exception e) {
                    System.out.println("Error de lectura en Postulante: " + e.getMessage());
                }
            }
        }
        return null;
    }

    public Oferta[] obtenerTodasLasOfertas() {
        int total = 0;
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i] != null) {
                total += clientes[i].getOfertas().length;
            }
        }
        
        Oferta[] todas = new Oferta[total];
        int idx = 0;
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i] != null) {
                Oferta[] empOfertas = clientes[i].getOfertas();
                for (Oferta o : empOfertas) {
                    if (o != null) {
                        todas[idx++] = o;
                    }
                }
            }
        }
        
        Oferta[] limpias = new Oferta[idx];
        System.arraycopy(todas, 0, limpias, 0, idx);
        return limpias;
    }
}
