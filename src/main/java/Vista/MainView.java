/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Vista;

import Controlador.ReclutamientoController;
import Modelo.*;
import javax.swing.JOptionPane;
import java.util.Date;
import java.lang.reflect.Field;

public class MainView {
    public static void main(String[] args) {
        ReclutamientoController controller = new ReclutamientoController();
        String[] opcionesMenuPrincipal = {
            "Registrar Empresa", 
            "Registrar Candidato", 
            "Ingresar como Empresa", 
            "Ingresar como Candidato", 
            "Salir"
        };

        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(
                null, 
                "Seleccione una opción para interactuar con el sistema:", 
                "SISTEMA DE RECLUTAMIENTO TI - MVC", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opcionesMenuPrincipal, 
                opcionesMenuPrincipal[0]
            );

            switch (seleccion) {
                case 0:
                    String ruc = JOptionPane.showInputDialog("Ingrese el RUC de la Empresa:");
                    if (ruc == null) break;
                    String razonSocial = JOptionPane.showInputDialog("Ingrese la Razón Social:");
                    if (razonSocial == null) break;
                    String emailE = JOptionPane.showInputDialog("Ingrese el Email Institucional:");
                    if (emailE == null) break;
                    String contacto = JOptionPane.showInputDialog("Nombre del Contacto:");
                    if (contacto == null) break;
                    String telefono = JOptionPane.showInputDialog("Teléfono Corporativo:");
                    if (telefono == null) break;
                    String rubroNombre = JOptionPane.showInputDialog("Rubro o Sector Empresarial:");
                    if (rubroNombre == null) break;
                    
                    Rubro rubroObj = new Rubro(rubroNombre);
                    controller.registrarEmpresa(ruc, razonSocial, emailE, contacto, telefono, rubroObj);
                    break;

                case 1:
                    String emailP = JOptionPane.showInputDialog("Ingrese Email del Candidato:");
                    if (emailP == null) break;
                    String nombres = JOptionPane.showInputDialog("Nombres:");
                    if (nombres == null) break;
                    String apellidos = JOptionPane.showInputDialog("Apellidos:");
                    if (apellidos == null) break;
                    String direccion = JOptionPane.showInputDialog("Dirección Completa:");
                    if (direccion == null) break;
                    String gradoEstudioDesc = JOptionPane.showInputDialog("Grado de Estudios Actual:");
                    if (gradoEstudioDesc == null) break;
                    
                    GradoEstudio gradoObj = new GradoEstudio(gradoEstudioDesc);
                    controller.registrarPostulante(emailP, nombres, apellidos, direccion, new Date(), gradoObj);
                    break;

                case 2:
                    String loginEmailE = JOptionPane.showInputDialog("Email de la Empresa:");
                    if (loginEmailE == null) break;
                    String loginClaveE = JOptionPane.showInputDialog("Contraseña de Empresa:");
                    if (loginClaveE == null) break;
                    
                    Cliente empresaAutenticada = controller.autenticarCliente(loginEmailE, loginClaveE);
                    
                    if (empresaAutenticada != null) {
                        String puesto = JOptionPane.showInputDialog("Nombre o Título del Puesto:");
                        if (puesto == null) break;
                        String descripcion = JOptionPane.showInputDialog("Descripción de la Vacante:");
                        if (descripcion == null) break;
                        String area = JOptionPane.showInputDialog("Área de la Empresa:");
                        if (area == null) break;
                        
                        Oferta nuevaOferta = new Oferta(puesto, descripcion, area, new Date(), new Date());
                        
                        String cantidadRequisitosStr = JOptionPane.showInputDialog("¿Cuántos requisitos posee la oferta?");
                        if (cantidadRequisitosStr != null && !cantidadRequisitosStr.trim().isEmpty()) {
                            try {
                                int cantReq = Integer.parseInt(cantidadRequisitosStr.trim());
                                for (int i = 1; i <= cantReq; i++) {
                                    String reqDesc = JOptionPane.showInputDialog("Descripción del Requisito N° " + i + ":");
                                    if (reqDesc != null) {
                                        nuevaOferta.agregarRequisito(i, reqDesc);
                                    }
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Cantidad inválida. No se añadieron requisitos adicionales.");
                            }
                        }
                        empresaAutenticada.agregarOferta(nuevaOferta);
                        JOptionPane.showMessageDialog(null, "La oferta laboral ha sido publicada correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Credenciales de empresa incorrectas.", "Autenticación Fallida", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 3:
                    String loginEmailP = JOptionPane.showInputDialog("Email del Candidato:");
                    if (loginEmailP == null) break;
                    String loginClaveP = JOptionPane.showInputDialog("Contraseña enviada a su Email:");
                    if (loginClaveP == null) break;
                    
                    Postulante postulanteAutenticado = controller.autenticarPostulante(loginEmailP, loginClaveP);
                    
                    if (postulanteAutenticado != null) {
                        Oferta[] ofertasDisponibles = controller.obtenerTodasLasOfertas();
                        if (ofertasDisponibles.length == 0) {
                            JOptionPane.showMessageDialog(null, "No hay ofertas laborales activas en el portal actualmente.");
                        } else {
                            StringBuilder listaOfertasVisual = new StringBuilder("--- OFERTAS DISPONIBLES EN TI ---\n\n");
                            for (int i = 0; i < ofertasDisponibles.length; i++) {
                                if (ofertasDisponibles[i] != null) {
                                    try {
                                        Field fPuesto = ofertasDisponibles[i].getClass().getDeclaredField("puesto");
                                        fPuesto.setAccessible(true);
                                        String nombrePuesto = (String) fPuesto.get(ofertasDisponibles[i]);
                                        listaOfertasVisual.append("[").append(i + 1).append("] ").append(nombrePuesto).append("\n");
                                    } catch (Exception e) {
                                        listaOfertasVisual.append("[").append(i + 1).append("] Oferta Laboral Registrada\n");
                                    }
                                }
                            }
                            listaOfertasVisual.append("\nDigite el número correlativo para postular:");
                            String seleccionOfertaStr = JOptionPane.showInputDialog(listaOfertasVisual.toString());
                            
                            if (seleccionOfertaStr != null && !seleccionOfertaStr.trim().isEmpty()) {
                                try {
                                    int indiceSeleccionado = Integer.parseInt(seleccionOfertaStr.trim()) - 1;
                                    if (indiceSeleccionado >= 0 && indiceSeleccionado < ofertasDisponibles.length && ofertasDisponibles[indiceSeleccionado] != null) {
                                        postulanteAutenticado.postular(ofertasDisponibles[indiceSeleccionado]);
                                        JOptionPane.showMessageDialog(null, "¡Postulación exitosa! Se registró fecha y hora del sistema.");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Índice fuera de rango. Operación cancelada.");
                                    }
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(null, "Entrada inválida. Debe colocar un número entero.");
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Credenciales del candidato incorrectas.", "Autenticación Fallida", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } while (seleccion != 4 && seleccion != -1);
    }
}
