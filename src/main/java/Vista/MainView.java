/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Vista;

import Controlador.ReclutamientoController;
import Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.lang.reflect.Field;

public class MainView extends JFrame {
    private ReclutamientoController controller;

    public MainView() {
        controller = new ReclutamientoController();
        
        setTitle("Sistema de Reclutamiento TI");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(5, 1, 10, 15));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("MENÚ PRINCIPAL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelCentral.add(lblTitulo);

        JButton btnRegistroEmpresa = new JButton("1. Registro de Empresas");
        JButton btnOfertasTrabajo = new JButton("2. Ofertas de Trabajo");
        JButton btnCandidatos = new JButton("3. Candidatos y Postulación");
        JButton btnSalir = new JButton("0. Salir");

        panelCentral.add(btnRegistroEmpresa);
        panelCentral.add(btnOfertasTrabajo);
        panelCentral.add(btnCandidatos);
        panelCentral.add(btnSalir);

        add(panelCentral, BorderLayout.CENTER);

        btnRegistroEmpresa.addActionListener(e -> menuRegistroEmpresa());
        btnOfertasTrabajo.addActionListener(e -> menuOfertasTrabajo());
        btnCandidatos.addActionListener(e -> menuCandidatos());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void menuRegistroEmpresa() {
        String ruc = JOptionPane.showInputDialog(this, "Ingrese el RUC de la Empresa:");
        if (ruc == null) return;
        String razonSocial = JOptionPane.showInputDialog(this, "Ingrese la Razón Social:");
        if (razonSocial == null) return;
        String emailE = JOptionPane.showInputDialog(this, "Ingrese el Email Institucional:");
        if (emailE == null) return;
        String contacto = JOptionPane.showInputDialog(this, "Nombre del Contacto:");
        if (contacto == null) return;
        String telefono = JOptionPane.showInputDialog(this, "Teléfono Corporativo:");
        if (telefono == null) return;
        String rubroNombre = JOptionPane.showInputDialog(this, "Rubro o Sector Empresarial:");
        if (rubroNombre == null) return;
        
        Rubro rubroObj = new Rubro(rubroNombre);
        controller.registrarEmpresa(ruc, razonSocial, emailE, contacto, telefono, rubroObj);
    }

    private void menuOfertasTrabajo() {
        String loginEmailE = JOptionPane.showInputDialog(this, "Email de la Empresa:");
        if (loginEmailE == null) return;
        String loginClaveE = JOptionPane.showInputDialog(this, "Contraseña de Empresa:");
        if (loginClaveE == null) return;
        
        Cliente empresaAutenticada = controller.autenticarCliente(loginEmailE, loginClaveE);
        
        if (empresaAutenticada != null) {
            String puesto = JOptionPane.showInputDialog(this, "Nombre o Título del Puesto:");
            if (puesto == null) return;
            String descripcion = JOptionPane.showInputDialog(this, "Descripción de la Vacante:");
            if (descripcion == null) return;
            String area = JOptionPane.showInputDialog(this, "Área de la Empresa:");
            if (area == null) return;
            
            Oferta nuevaOferta = new Oferta(puesto, descripcion, area, new Date(), new Date());
            
            String cantidadRequisitosStr = JOptionPane.showInputDialog(this, "¿Cuántos requisitos posee la oferta?");
            if (cantidadRequisitosStr != null && !cantidadRequisitosStr.trim().isEmpty()) {
                try {
                    int cantReq = Integer.parseInt(cantidadRequisitosStr.trim());
                    for (int i = 1; i <= cantReq; i++) {
                        String reqDesc = JOptionPane.showInputDialog(this, "Descripción del Requisito N° " + i + ":");
                        if (reqDesc != null) {
                            nuevaOferta.agregarRequisito(i, reqDesc);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Cantidad inválida. No se añadieron requisitos adicionales.");
                }
            }
            empresaAutenticada.agregarOferta(nuevaOferta);
            JOptionPane.showMessageDialog(this, "La oferta laboral ha sido publicada correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Credenciales de empresa incorrectas.", "Autenticación Fallida", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void menuCandidatos() {
        String[] opciones = {"Registrar Nuevo Candidato", "Ingresar y Postular"};
        int seleccion = JOptionPane.showOptionDialog(this, "Seleccione una acción:", "Candidatos", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        
        if (seleccion == 0) {
            String emailP = JOptionPane.showInputDialog(this, "Ingrese Email del Candidato:");
            if (emailP == null) return;
            String nombres = JOptionPane.showInputDialog(this, "Nombres:");
            if (nombres == null) return;
            String apellidos = JOptionPane.showInputDialog(this, "Apellidos:");
            if (apellidos == null) return;
            String direccion = JOptionPane.showInputDialog(this, "Dirección Completa:");
            if (direccion == null) return;
            String gradoEstudioDesc = JOptionPane.showInputDialog(this, "Grado de Estudios Actual:");
            if (gradoEstudioDesc == null) return;
            
            GradoEstudio gradoObj = new GradoEstudio(gradoEstudioDesc);
            controller.registrarPostulante(emailP, nombres, apellidos, direccion, new Date(), gradoObj);
        } else if (seleccion == 1) {
            String loginEmailP = JOptionPane.showInputDialog(this, "Email del Candidato:");
            if (loginEmailP == null) return;
            String loginClaveP = JOptionPane.showInputDialog(this, "Contraseña enviada a su Email:");
            if (loginClaveP == null) return;
            
            Postulante postulanteAutenticado = controller.autenticarPostulante(loginEmailP, loginClaveP);
            
            if (postulanteAutenticado != null) {
                Oferta[] ofertasDisponibles = controller.obtenerTodasLasOfertas();
                if (ofertasDisponibles.length == 0) {
                    JOptionPane.showMessageDialog(this, "No hay ofertas laborales activas en el portal actualmente.");
                } else {
                    StringBuilder listaOfertasVisual = new StringBuilder("--- OFERTAS DISPONIBLES EN TI ---\n\n");
                    for (int i = 0; i < ofertasDisponibles.length; i++) {
                        if (ofertasDisponibles[i] != null) {
                            try {
                                Field fPuesto = ofertasDisponibles[i].getClass().getDeclaredField("puesto");
                                fPuesto.setAccessible(true);
                                String nombrePuesto = (String) fPuesto.get(ofertasDisponibles[i]);
                                listaOfertasVisual.append("[").append(i + 1).append("] ").append(nombrePuesto).append("\n");
                            } catch (Exception ex) {
                                listaOfertasVisual.append("[").append(i + 1).append("] Oferta Laboral Registrada\n");
                            }
                        }
                    }
                    listaOfertasVisual.append("\nDigite el número correlativo para postular:");
                    String seleccionOfertaStr = JOptionPane.showInputDialog(this, listaOfertasVisual.toString());
                    
                    if (seleccionOfertaStr != null && !seleccionOfertaStr.trim().isEmpty()) {
                        try {
                            int indiceSeleccionado = Integer.parseInt(seleccionOfertaStr.trim()) - 1;
                            if (indiceSeleccionado >= 0 && indiceSeleccionado < ofertasDisponibles.length && ofertasDisponibles[indiceSeleccionado] != null) {
                                postulanteAutenticado.postular(ofertasDisponibles[indiceSeleccionado]);
                                JOptionPane.showMessageDialog(this, "¡Postulación exitosa! Se registró fecha y hora del sistema.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Índice fuera de rango. Operación cancelada.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Entrada inválida. Debe colocar un número entero.");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: Credenciales del candidato incorrectas.", "Autenticación Fallida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        
        SwingUtilities.invokeLater(() -> {
            new MainView().setVisible(true);
        });
    }
}