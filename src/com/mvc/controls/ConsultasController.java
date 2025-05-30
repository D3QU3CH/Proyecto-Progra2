package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mvc.models.School;
import com.mvc.models.University;
import com.mvc.view.MainView;

public class ConsultasController { 

    private MainView mainView;
    private UniversityController universidadController;

    // Paneles específicos que vienen desde MainView
    private ControllerPanelConsultas panelPorProfesor;
    private ControllerPanelConsultas panelPorCurso;
    private ControllerPanelConsultas panelPorCedula;
    private ControllerPanelConsultas panelPorEscuela;

    public ConsultasController(MainView mainView, UniversityController universidadController) {
        this.mainView = mainView;
        this.universidadController = universidadController;

   
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
                consultarCursosPorProfesor(panelPorProfesor);
            });
        } else {
            System.err.println("botonBuscar es null en panelPorProfesor");
        }

        // Botón buscar por curso
        if (panelPorCurso.botonBuscar != null) {
            panelPorCurso.botonBuscar.addActionListener(e -> {
                consultarProfesoresPorCurso(panelPorCurso);
            });
        } else {
            System.err.println("botonBuscar es null en panelPorCurso");
        }
        //configuara bonton por cedula
        if (panelPorCedula.botonBuscar != null) {
        	panelPorCedula.botonBuscar.addActionListener(e -> {
        		consultarProfesorPorCedula(panelPorCedula);
            });
        } else {
            System.err.println("botonBuscar es null en panelPorCurso");
        }
        //configurar el boton de buscar por escuela
        if (panelPorEscuela.botonBuscar != null) {
        	panelPorEscuela.botonBuscar.addActionListener(e -> {
        		consultarProfesorPorEscuela(panelPorEscuela);
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

    public void consultarProfesorPorCedula(ControllerPanelConsultas panel) {
        String varNumeroCedula = panel.campoBuscar.getText().trim();

        DefaultTableModel modeloTablaProf = (DefaultTableModel) mainView.tablaProfesores.getModel();
        
        boolean cedulaExiste = false;
        boolean coincidencia = false;

        panel.areaMostrar.setText("");

        // Validar si la cédula existe en la tabla
        for (int i = 0; i < modeloTablaProf.getRowCount(); i++) {
            String cedula = (String) modeloTablaProf.getValueAt(i, 3);
            if (varNumeroCedula.equalsIgnoreCase(cedula)) {
                cedulaExiste = true;
                break;
            }
        }

        if (!cedulaExiste) {
            JOptionPane.showMessageDialog(mainView,
                    "¡El profesor con la cédula '" + varNumeroCedula + "' no existe en el sistema!", "Cédula no encontrada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar profesor por cédula (si existe)
        for (int i = 0; i < modeloTablaProf.getRowCount(); i++) {
            String varTomarCedula = (String) modeloTablaProf.getValueAt(i, 3);

            if (varNumeroCedula.equalsIgnoreCase(varTomarCedula)) {
                coincidencia = true;

                String nombreProfesor = (String) modeloTablaProf.getValueAt(i, 0);
                String primeroApellido = (String) modeloTablaProf.getValueAt(i, 1);
                String segundoApellido = (String) modeloTablaProf.getValueAt(i, 2);
                String filaTexto =
                        "Profesor: " + nombreProfesor + " " + primeroApellido + " " + segundoApellido + "\n" +
                        "Cédula: " + varNumeroCedula + "\n" +
                        "----------------------------------------\n";

                panel.areaMostrar.append(filaTexto);
            }
        }

        if (coincidencia) {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontró coincidencia con el número de cédula: '" + varNumeroCedula + "'!", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void consultarProfesorPorEscuela(ControllerPanelConsultas panel) {
        String varNombreEscuela = panel.campoBuscar.getText().trim();
        DefaultTableModel modeloTablaProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloTablaAsignar = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
        boolean escuelaExiste = false;
        boolean coincidencia = false;
        panel.areaMostrar.setText("");
        
        // Validar si la escuela existe en la tabla de cursos
        University universidad = universidadController.getUniversidad();
        for (School esc : universidad.getEscuelas()) {
            if (varNombreEscuela.equalsIgnoreCase(esc.getVarName())) {
                escuelaExiste = true;
                break;
            }
        }
        
        if (!escuelaExiste) {
            JOptionPane.showMessageDialog(mainView,
                    "¡La escuela '" + varNombreEscuela + "' no existe en el sistema!", "Escuela no encontrada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Buscar profesores relacionados con la escuela
        for (int i = 0; i < modeloTablaAsignar.getRowCount(); i++) {
            String nombreEscuela = (String) modeloTablaAsignar.getValueAt(i, 0);
            String cedula = (String) modeloTablaAsignar.getValueAt(i, 1);
            
            if (nombreEscuela.equalsIgnoreCase(varNombreEscuela)) {
                // Validar si esta cédula ya fue mostrada para esta escuela
                boolean yaFueMostrado = false;
                String textoActual = panel.areaMostrar.getText();
                
                if (textoActual.contains("Cédula: " + cedula)) {
                    yaFueMostrado = true;
                }
                
                if (!yaFueMostrado) {
                    coincidencia = true;
                    String nombreProfesor = "";
                    String primerApellido = "";
                    String segundoApellido = "";
                    
                    for (int j = 0; j < modeloTablaProfesores.getRowCount(); j++) {
                        String cedulaRegistrada = (String) modeloTablaProfesores.getValueAt(j, 3); 
                        if (cedula.equalsIgnoreCase(cedulaRegistrada)) {
                            nombreProfesor = (String) modeloTablaProfesores.getValueAt(j, 0); 
                            primerApellido = (String) modeloTablaProfesores.getValueAt(j, 1);
                            segundoApellido = (String) modeloTablaProfesores.getValueAt(j, 2);
                            break;
                        }
                    }
                    
                    String filaTexto =
                            "Escuela: " + nombreEscuela + "\n" +
                            "Profesor: " + nombreProfesor + " " + primerApellido + " " + segundoApellido + "\n" +
                            "Cédula: " + cedula + "\n" +
                            "----------------------------------------\n";
                    panel.areaMostrar.append(filaTexto);
                }
            }
        }
        
        if (!coincidencia) {
            JOptionPane.showMessageDialog(mainView,
                    "No hay profesores asignados a la escuela: '" + varNombreEscuela + "'",
                    "Sin profesores", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron profesores en la escuela: '" + varNombreEscuela + "'!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void consultarCursosPorProfesor(ControllerPanelConsultas panel) {
        String varNombreProfesor = panel.campoBuscar.getText().trim(); 
        DefaultTableModel modeloTablaProf = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloTablaAsignar = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
        DefaultTableModel modeloTablaCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
        boolean profesorExiste = false;
        boolean cursosEncontrados = false;
        panel.areaMostrar.setText(""); 
        
        // Primero validamos si el profesor existe
        String cedulaProfesorExistente = "";
        String nombreProfesorTabla = "";
        for (int i = 0; i < modeloTablaProf.getRowCount(); i++) {
            nombreProfesorTabla = (String) modeloTablaProf.getValueAt(i, 0);
            if (varNombreProfesor.equalsIgnoreCase(nombreProfesorTabla)) {
                cedulaProfesorExistente = (String) modeloTablaProf.getValueAt(i, 3); 
                profesorExiste = true;
                break;
            }
        }
        
        if (!profesorExiste) {
            JOptionPane.showMessageDialog(mainView,
                    "¡El profesor '" + varNombreProfesor + "' no existe en el sistema!", "Profesor no encontrado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (int i = 0; i < modeloTablaAsignar.getRowCount(); i++) {
            String nombreEscuela = (String) modeloTablaAsignar.getValueAt(i, 0);
            String cedula = (String) modeloTablaAsignar.getValueAt(i, 1);
            
            if (cedula.equalsIgnoreCase(cedulaProfesorExistente)) {
                String siglaCurso = (String) modeloTablaAsignar.getValueAt(i, 2);
                
                for (int j = 0; j < modeloTablaCursos.getRowCount(); j++) {
                    String siglasCursoTabla = (String) modeloTablaCursos.getValueAt(j, 1);
                    
                    if (siglaCurso.equalsIgnoreCase(siglasCursoTabla)) {
                        cursosEncontrados = true;
                        String nombreCurso = (String) modeloTablaCursos.getValueAt(j, 2);
                        String filaTexto =
                                "Profesor: " + nombreProfesorTabla + "\n" +
                                "Escuela: " + nombreEscuela + "\n" +
                                "Siglas: " + siglasCursoTabla + "\n" +
                                "Nombre del curso: " + nombreCurso + "\n" +
                                "----------------------------------------\n";
                        panel.areaMostrar.append(filaTexto);
                    }
                }
            }	
        }
        
        if (!cursosEncontrados) {
            JOptionPane.showMessageDialog(mainView,
                    "No hay cursos asignados para el profesor: '" + varNombreProfesor + "'",
                    "Sin cursos", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron cursos asignados para el profesor '" + varNombreProfesor + "'!", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    
    public void consultarProfesoresPorCurso(ControllerPanelConsultas panel) {
        String varSiglasCurso = panel.campoBuscar.getText().trim();

        DefaultTableModel modeloTablaCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
        DefaultTableModel modeloTablaProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloTablaAsignar = (DefaultTableModel) mainView.tablaAsignaciones.getModel();

        boolean cursoExiste = false;
        boolean coincidencia = false;

        panel.areaMostrar.setText(""); 

        // Primero validamos si el curso existe en la tabla de cursos
        for (int i = 0; i < modeloTablaCursos.getRowCount(); i++) {
            String siglasCurso = (String) modeloTablaCursos.getValueAt(i, 1);
            if (varSiglasCurso.equalsIgnoreCase(siglasCurso)) {
                cursoExiste = true;
                break;
            }
        }

        if (!cursoExiste) {
            JOptionPane.showMessageDialog(mainView,
                    "¡El curso con siglas '" + varSiglasCurso + "' no existe en el sistema!", "Curso no encontrado",
                    JOptionPane.ERROR_MESSAGE);
            return; 
        }

        // Si el curso existe, buscamos profesores asignados a ese curso
        for (int i = 0; i < modeloTablaAsignar.getRowCount(); i++) {
            String varTomarSigla = (String) modeloTablaAsignar.getValueAt(i, 2); // columna de siglas del curso

            if (varSiglasCurso.equalsIgnoreCase(varTomarSigla)) {
                coincidencia = true;
                
                String cedula = (String) modeloTablaAsignar.getValueAt(i, 1);
                String grupo = (String) modeloTablaAsignar.getValueAt(i, 3);
                String nombreProfesor = "";
                String primeroApellido = "";
                String segundoApellido = "";
                
                for (int j = 0; j < modeloTablaProfesores.getRowCount(); j++) {
        	        String cedulaRegistrada = (String) modeloTablaProfesores.getValueAt(j, 3); 

                    
        	        if (cedula.equalsIgnoreCase(cedulaRegistrada)) {
        	        	nombreProfesor = (String) modeloTablaProfesores.getValueAt(j, 0); 
        	        	primeroApellido = (String) modeloTablaProfesores.getValueAt(j, 1);
        	        	segundoApellido = (String) modeloTablaProfesores.getValueAt(j, 2);
        	            break;
        	        }
        	    }
             
                

                String filaTexto =
                        "Profesor: " + nombreProfesor + " " + primeroApellido + " " + segundoApellido + "\n" +
                        "Cédula: " + cedula + "\n" +
                        "Grupo: " + grupo + "\n" +
                        "----------------------------------------\n";

                panel.areaMostrar.append(filaTexto);
            }
        }

        if (!coincidencia) {
            JOptionPane.showMessageDialog(mainView,
                    "¡El curso '" + varSiglasCurso + "' no tiene profesores asignados!", "Sin profesores",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron profesores asignados al curso '" + varSiglasCurso + "'!", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarInformacionEscuelas() {
        DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
        DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloAsignaciones = (DefaultTableModel) mainView.tablaAsignaciones.getModel();

        mainView.txtAreaConsultaEscuelas.setText(""); // Limpiar contenido anterior

        for (int i = 0; i < modeloCursos.getRowCount(); i++) {
            String nombreEscuela = (String) modeloCursos.getValueAt(i, 0);
            String siglasCurso = (String) modeloCursos.getValueAt(i, 1);
            String descripcionCurso = (String) modeloCursos.getValueAt(i, 2);

            mainView.txtAreaConsultaEscuelas.append("Escuela: " + nombreEscuela + "\n");
            mainView.txtAreaConsultaEscuelas.append("Curso: " + siglasCurso + " - " + descripcionCurso + " (Siglas - Descripcion)\n");

            boolean profesorEncontrado = false;
            
            // Buscar profesores asignados a este curso usando la tabla de asignaciones
            for (int j = 0; j < modeloAsignaciones.getRowCount(); j++) {
                String escuelaAsignacion = (String) modeloAsignaciones.getValueAt(j, 0);
                String cedulaAsignacion = (String) modeloAsignaciones.getValueAt(j, 1);
                String siglaAsignacion = (String) modeloAsignaciones.getValueAt(j, 2);
                String grupoAsignacion = (String) modeloAsignaciones.getValueAt(j, 3);
                
                // Si coincide la escuela y las siglas del curso
                if (nombreEscuela.equalsIgnoreCase(escuelaAsignacion) && 
                    siglasCurso.equalsIgnoreCase(siglaAsignacion)) {
                    
                    // Buscar los datos del profesor en la tabla de profesores
                    for (int k = 0; k < modeloProfesores.getRowCount(); k++) {
                        String cedulaProfesor = (String) modeloProfesores.getValueAt(k, 3);
                        
                        if (cedulaAsignacion.equalsIgnoreCase(cedulaProfesor)) {
                            profesorEncontrado = true;
                            String nombre = (String) modeloProfesores.getValueAt(k, 0);
                            String apellido1 = (String) modeloProfesores.getValueAt(k, 1);
                            String apellido2 = (String) modeloProfesores.getValueAt(k, 2);
                            
                            mainView.txtAreaConsultaEscuelas.append("Profesor: " + nombre + " " + apellido1 + " " + apellido2 
                                    + ", Cédula: " + cedulaAsignacion + ", Grupo: " + grupoAsignacion + "\n");
                            break;
                        }
                    }
                }
            }

            if (!profesorEncontrado) {
            	mainView.txtAreaConsultaEscuelas.append("Profesor: No asignado\n");
            }

            mainView.txtAreaConsultaEscuelas.append("-------------------------------------------\n");
        }
    }

    
}