package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.mvc.models.Courses;
import com.mvc.models.School;
import com.mvc.models.University;
import com.mvc.view.MainView;

public class ConsultasController { 

    private MainView mainView;
    private UniversityController universidadController;
    private TeacherController profesorController;

    // Paneles específicos que vienen desde MainView
    private ControllerPanelConsultas panelPorProfesor;
    private ControllerPanelConsultas panelPorCurso;
    private ControllerPanelConsultas panelPorCedula;
    private ControllerPanelConsultas panelPorEscuela;

    public ConsultasController(MainView mainView, UniversityController universidadController, TeacherController profesorController) {
        this.mainView = mainView;
        this.universidadController = universidadController;
        this.profesorController = profesorController;

        // Suponiendo que MainView tiene estos dos paneles ya creados
        this.panelPorProfesor = mainView.panelPorProfesor;
        this.panelPorCurso = mainView.panelPorCurso;
        this.panelPorCedula= mainView.panelPorCedula;
        this.panelPorEscuela= mainView.panelPorEscuela;
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
        //configuara bonton por cedula
        if (panelPorCedula.botonBuscar != null) {
        	panelPorCedula.botonBuscar.addActionListener(e -> {
        		busquedaPorNumeroDeCedula(panelPorCedula);
            });
        } else {
            System.err.println("botonBuscar es null en panelPorCurso");
        }
        //configurar el boton de buscar por escuela
        if (panelPorEscuela.botonBuscar != null) {
        	panelPorEscuela.botonBuscar.addActionListener(e -> {
        		busquedaPorEscuela(panelPorEscuela);
            });
        } else {
            System.err.println("botonBuscar es null en panelPorCurso");
        }
        
        mainView.btnConsultaDirectores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mainView.showPanel("CONSULTA DIRECTORES");
            }
        });
        
        mainView.btnConsultaEscuelas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mostrarInformacionEscuelas();
            	mainView.showPanel("CONSULTA ESCUELAS");
            }
        });
        
        mainView.btnRegresarDirectores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showPanel("CONSULTAS");
            }
        });

        mainView.btnRegresarEscuelas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showPanel("CONSULTAS");
            }
        });
        
        configurarBotonVolver(panelPorProfesor);
        configurarBotonVolver(panelPorCurso);
        configurarBotonVolver(panelPorCedula);
        configurarBotonVolver(panelPorEscuela);
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
                            
                            String filaTexto = 
                            		"Profesor: " + nombreProfesorTabla + "\n" +
                            		"Escuela: " + nombreEscuela + "\n" + 
                                    "Siglas: " + siglasCurso + "\n" + 
                                    "Descripción: " + descripcionCurso+"\n"+
                                    "----------------------------------------\n";

                            panel.areaMostrar.append(filaTexto);
                            
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
    	
    	boolean coincidencia = false;
    	 panel.areaMostrar.setText("");
    	 
    	for(int i =0; i<modeloTabla.getRowCount();i++) {
    		String varTomarSigla = (String) modeloTabla.getValueAt(i, 0);
    		
    		if(varSiglasCurso.equalsIgnoreCase(varTomarSigla)) {
    			coincidencia = true;
    			
    			String nombreProfesor = (String) modeloTabla.getValueAt(i, 1);
                String primeroApellido = (String) modeloTabla.getValueAt(i, 2);
                String segudoApellido = (String) modeloTabla.getValueAt(i, 3);
                String cedula = (String) modeloTabla.getValueAt(i, 4);
                String grupo = (String) modeloTabla.getValueAt(i, 5);
                
                String filaTexto = 
                		"Profesor: " + nombreProfesor + " " + primeroApellido + " " + segudoApellido + "\n" +
                        "Cédula: " + cedula + "\n" +
                        "Grupo: " + grupo + "\n" +
                        "----------------------------------------\n";
              
                panel.areaMostrar.append(filaTexto);
              
    			
    		}
    		
    	}
    	if (!coincidencia) {
            JOptionPane.showMessageDialog(mainView,
                    "el curso con siglas '" + varSiglasCurso + "' no tiene profesores asignados!", "Sin cursos",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron cursos con las siglas:'" + varSiglasCurso + "'!", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    	
    	
    	
    }
    public void busquedaPorNumeroDeCedula(ControllerPanelConsultas panel) {
    	String varNumeroCedula = panel.campoBuscar.getText().trim();
    	
    	DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaProfesores.getModel();
    	boolean coincidencia = false;
    	panel.areaMostrar.setText("");
    	for(int i=0;i<modeloTabla.getRowCount();i++) {
    		String varTomarCedula = (String) modeloTabla.getValueAt(i, 4);
    		
    		if(varNumeroCedula.equalsIgnoreCase(varTomarCedula)) {
    			coincidencia=true;
    			String nombreProfesor = (String) modeloTabla.getValueAt(i, 1);
                String primeroApellido = (String) modeloTabla.getValueAt(i, 2);
                String segudoApellido = (String) modeloTabla.getValueAt(i, 3);
                String grupo = (String) modeloTabla.getValueAt(i, 5);
                
                String filaTexto = 
                		"Profesor: " + nombreProfesor + " " + primeroApellido + " " + segudoApellido + "\n" +
                        "Cédula: " + varNumeroCedula + "\n" +
                        "Grupo: " + grupo + "\n" +
                        "----------------------------------------\n";
              
                panel.areaMostrar.append(filaTexto);
    			
    		}
    		
    		
    	}
    	if (!coincidencia) {
            JOptionPane.showMessageDialog(mainView,
                    "No se encontraron profesores con el numero de cedula: '" + varNumeroCedula + "' error!", "",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron considencia en el numero de cedula:'" + varNumeroCedula + "'!", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    	
    	
    }
    public void busquedaPorEscuela(ControllerPanelConsultas panel) {
        String varNombreEscuela = panel.campoBuscar.getText().trim();
        DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();

        boolean coincidencia = false;
        panel.areaMostrar.setText(""); // Limpiar el área de texto antes de mostrar resultados

        for (int i = 0; i < modeloCursos.getRowCount(); i++) {
            String nombreEscuela = (String) modeloCursos.getValueAt(i, 0); // Columna 0: nombre de la escuela
            String siglasCurso = (String) modeloCursos.getValueAt(i, 1);   // Columna 1: siglas del curso

            if (nombreEscuela.equalsIgnoreCase(varNombreEscuela)) {
                // Buscar profesores que tengan estas siglas de curso
                for (int j = 0; j < modeloProfesores.getRowCount(); j++) {
                    String siglasProfesor = (String) modeloProfesores.getValueAt(j, 0); // Columna 0: siglas del curso del profesor

                    if (siglasCurso.equalsIgnoreCase(siglasProfesor)) {
                        coincidencia = true;
                        String cursoSiglas =(String) modeloProfesores.getValueAt(j, 0);
                        String nombreProfesor = (String) modeloProfesores.getValueAt(j, 1);
                        String primerApellido = (String) modeloProfesores.getValueAt(j, 2);
                        String segundoApellido = (String) modeloProfesores.getValueAt(j, 3);
                        String cedula = (String) modeloProfesores.getValueAt(j, 4);
                        String grupo = (String) modeloProfesores.getValueAt(j, 5);

                        String filaTexto =
                        		"Escuela: " + nombreEscuela + "\n" +
                        		"Curso: "+cursoSiglas+"\n"+
                                "Profesor: " + nombreProfesor + " " + primerApellido + " " + segundoApellido + "\n" +
                                "Cédula: " + cedula + "\n" +
                                "Grupo: " + grupo + "\n" +
                                "----------------------------------------\n";

                        panel.areaMostrar.append(filaTexto);
                    }
                }
            }
        }

        if (!coincidencia) {
            JOptionPane.showMessageDialog(mainView,
                    "No se encontraron profesores con la escuela: '" + varNombreEscuela + "'",
                    "Sin coincidencias", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron coincidencias en la escuela: '" + varNombreEscuela + "'!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mostrarInformacionEscuelas() {
        DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
        DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        JTextArea areaTexto = mainView.txtAreaConsultaEscuelas;

        areaTexto.setText(""); // Limpiar contenido anterior

        for (int i = 0; i < modeloCursos.getRowCount(); i++) {
            String nombreEscuela = (String) modeloCursos.getValueAt(i, 0);
            String siglasCurso = (String) modeloCursos.getValueAt(i, 1);
            String descripcionCurso = (String) modeloCursos.getValueAt(i, 2);

            areaTexto.append("Escuela: " + nombreEscuela + "\n");
            areaTexto.append("Curso: " + siglasCurso + " - " + descripcionCurso + " (Siglas - Descripcion)\n");

            boolean profesorEncontrado = false;
            for (int j = 0; j < modeloProfesores.getRowCount(); j++) {
                String siglasProfesor = (String) modeloProfesores.getValueAt(j, 0);
                if (siglasCurso.equalsIgnoreCase(siglasProfesor)) {
                    profesorEncontrado = true;
                    String nombre = (String) modeloProfesores.getValueAt(j, 1);
                    String apellido1 = (String) modeloProfesores.getValueAt(j, 2);
                    String apellido2 = (String) modeloProfesores.getValueAt(j, 3);
                    String cedula = (String) modeloProfesores.getValueAt(j, 4);
                    String grupo = (String) modeloProfesores.getValueAt(j, 5);

                    areaTexto.append("Profesor: " + nombre + " " + apellido1 + " " + apellido2 
                    		+ ", Cédula: " + cedula + ", Grupo: " + grupo + "\n");
                }
            }

            if (!profesorEncontrado) {
                areaTexto.append("Profesor: No asignado\n");
            }

            areaTexto.append("-------------------------------------------\n");
        }
    }

    
}