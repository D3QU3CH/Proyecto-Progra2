package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mvc.models.Courses;
import com.mvc.models.University;
import com.mvc.view.MainView;

public class ConsultasController {

    private MainView mainView;
    private UniversityController universidadController;

    // Paneles específicos que vienen desde MainView
    private ControllerPanelConsultas panelPorProfesor;
    private ControllerPanelConsultas panelPorCurso;

    public ConsultasController(MainView mainView, UniversityController universidadController) {
        this.mainView = mainView;
        this.universidadController = universidadController;

        // Suponiendo que MainView tiene estos dos paneles ya creados
        this.panelPorProfesor = mainView.panelPorProfesor;
        this.panelPorCurso = mainView.panelPorCurso;

        // Conectar eventos
        configurarEventos();
    }

    private void configurarEventos() {
        // Botón buscar por profesor
        if (panelPorProfesor.botonBuscar != null) {
            panelPorProfesor.botonBuscar.addActionListener(e -> {
                consultasPorProfesor(panelPorProfesor);
            });
        } else {
            System.err.println("botonBuscar es null en panelPorProfesor");
        }

        // ✅ Botón buscar por curso
        if (panelPorCurso.botonBuscar != null) {
            panelPorCurso.botonBuscar.addActionListener(e -> {
                consultaDeProfesoresPorCurso(panelPorCurso);
            });
        } else {
            System.err.println("botonBuscar es null en panelPorCurso");
        }
        configurarBotonVolver(panelPorProfesor);
        configurarBotonVolver(panelPorCurso);
    }
    
    private void configurarBotonVolver(ControllerPanelConsultas panel) {
        if (panel.botonVolver != null) {
            panel.botonVolver.addActionListener(e -> {
                mainView.showPanel("CONSULTAS"); // O el panel al que quieras volver
            });
        }
    }


    
    
    public void consultasPorProfesor(ControllerPanelConsultas panel) {
        String varNombreProfesor = panel.campoBuscar.getText().trim(); // Campo del panel actual
        DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloTablaCursos = (DefaultTableModel) mainView.tablaCursos.getModel();

        boolean coincidenciaEncontrada = false;

        panel.areaMostrar.setText(""); // Limpiar área antes de mostrar resultados

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String nombreProfesorTabla = (String) modeloTabla.getValueAt(i, 1);

            if (varNombreProfesor.equalsIgnoreCase(nombreProfesorTabla)) {
                String siglasCurso = (String) modeloTabla.getValueAt(i, 0);
                coincidenciaEncontrada = true;

                if (siglasCurso != null && !siglasCurso.isEmpty()) {
                    for (int j = 0; j < modeloTablaCursos.getRowCount(); j++) {
                        String siglasCursoTabla = (String) modeloTablaCursos.getValueAt(j, 1);

                        if (siglasCurso.equalsIgnoreCase(siglasCursoTabla)) {
                            String nombreEscuela = (String) modeloTabla.getValueAt(i, 0);
                            String descripcionCurso = (String) modeloTabla.getValueAt(i, 2);

                            String filaTexto = "Escuela: " + nombreEscuela +
                                               ", Siglas: " + siglasCurso +
                                               ", Descripción: " + descripcionCurso;

                            panel.areaMostrar.append(filaTexto + "\n");
                        }
                    }
                }
            }
        }

        // Validación final
        if (!coincidenciaEncontrada) {
            JOptionPane.showMessageDialog(mainView,
                    "¡El profesor '" + varNombreProfesor + "' no tiene cursos asociados!", "Sin cursos",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron cursos para el profesor '" + varNombreProfesor + "'!", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    
    
    public void consultaDeProfesoresPorCurso(ControllerPanelConsultas panel) {
    	String varSiglasCurso = panel.campoBuscar.getText().trim();

    	DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaProfesores.getModel();
    	
    	boolean considencia = false;
    	 panel.areaMostrar.setText("");
    	 
    	for(int i =0; i<modeloTabla.getRowCount();i++) {
    		String varTomarSigla = (String) modeloTabla.getValueAt(i, 0);
    		
    		if(varSiglasCurso.equalsIgnoreCase(varTomarSigla)) {
    			considencia =true;
    			
    			String nombreProfesor = (String) modeloTabla.getValueAt(i, 1);
                String primeroApellido = (String) modeloTabla.getValueAt(i, 2);
                String segudoApellido = (String) modeloTabla.getValueAt(i, 3);
                String cedula = (String) modeloTabla.getValueAt(i, 4);
                String grupo = (String) modeloTabla.getValueAt(i, 5);
                
                String filaTexto = "siglas cusro: "+varSiglasCurso+" "+
                        "Profesor: " + nombreProfesor + " " + primeroApellido + " " + segudoApellido + "\n" +
                        "Cédula: " + cedula + "\n" +
                        "Grupo: " + grupo + "\n\n" +
                        "----------------------------------------\n\n";
              
                panel.areaMostrar.append(filaTexto + "\n");
              
    			
    		}
    		
    	}
    	if (!considencia) {
            JOptionPane.showMessageDialog(mainView,
                    "el curso con siglas '" + varSiglasCurso + "' no tiene profesores asignados!", "Sin cursos",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron cursos con las siglas:'" + varSiglasCurso + "'!", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    	
    	
    	
    }
    
    
    
}