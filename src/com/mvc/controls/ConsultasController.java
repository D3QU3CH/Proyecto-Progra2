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

        // Botón buscar por curso
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
        String varNombreProfesor = panel.campoBuscar.getText().trim(); 
        DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloTablaCursos = (DefaultTableModel) mainView.tablaCursos.getModel();

        boolean profesorExiste = false;
        boolean coincidenciaEncontrada = false;

        panel.areaMostrar.setText(""); 

        // Primero validamos si el profesor existe
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String nombreProfesorTabla = (String) modeloTabla.getValueAt(i, 1);
            if (varNombreProfesor.equalsIgnoreCase(nombreProfesorTabla)) {
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

        // Si el profesor existe, buscamos sus cursos
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String nombreProfesorTabla = (String) modeloTabla.getValueAt(i, 1);

            if (varNombreProfesor.equalsIgnoreCase(nombreProfesorTabla)) {
                String siglasCurso = (String) modeloTabla.getValueAt(i, 0);
                coincidenciaEncontrada = true;

                if (siglasCurso != null && !siglasCurso.isEmpty()) {
                    for (int j = 0; j < modeloTablaCursos.getRowCount(); j++) {
                        String siglasCursoTabla = (String) modeloTablaCursos.getValueAt(j, 1);

                        if (siglasCurso.equalsIgnoreCase(siglasCursoTabla)) {
                            String nombreEscuela = (String) modeloTablaCursos.getValueAt(i, 0);
                            String descripcionCurso = (String) modeloTablaCursos.getValueAt(i, 2);

                            String filaTexto =
                                    "Profesor: " + nombreProfesorTabla + "\n" +
                                    "Escuela: " + nombreEscuela + "\n" +
                                    "Siglas: " + siglasCurso + "\n" +
                                    "Descripción: " + descripcionCurso + "\n" +
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

        DefaultTableModel modeloTablaProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloTablaCursos = (DefaultTableModel) mainView.tablaCursos.getModel();

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
        for (int i = 0; i < modeloTablaProfesores.getRowCount(); i++) {
            String varTomarSigla = (String) modeloTablaProfesores.getValueAt(i, 0); // columna de siglas del curso

            if (varSiglasCurso.equalsIgnoreCase(varTomarSigla)) {
                coincidencia = true;

                String nombreProfesor = (String) modeloTablaProfesores.getValueAt(i, 1);
                String primeroApellido = (String) modeloTablaProfesores.getValueAt(i, 2);
                String segundoApellido = (String) modeloTablaProfesores.getValueAt(i, 3);
                String cedula = (String) modeloTablaProfesores.getValueAt(i, 4);
                String grupo = (String) modeloTablaProfesores.getValueAt(i, 5);

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
    
    
    public void busquedaPorNumeroDeCedula(ControllerPanelConsultas panel) {
        String varNumeroCedula = panel.campoBuscar.getText().trim();

        DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaProfesores.getModel();
        boolean cedulaExiste = false;
        boolean coincidencia = false;

        panel.areaMostrar.setText("");

        // Validar si la cédula existe en la tabla
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String cedula = (String) modeloTabla.getValueAt(i, 4);
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
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String varTomarCedula = (String) modeloTabla.getValueAt(i, 4);

            if (varNumeroCedula.equalsIgnoreCase(varTomarCedula)) {
                coincidencia = true;

                String nombreProfesor = (String) modeloTabla.getValueAt(i, 1);
                String primeroApellido = (String) modeloTabla.getValueAt(i, 2);
                String segundoApellido = (String) modeloTabla.getValueAt(i, 3);
                String grupo = (String) modeloTabla.getValueAt(i, 5);

                String filaTexto =
                        "Profesor: " + nombreProfesor + " " + primeroApellido + " " + segundoApellido + "\n" +
                        "Cédula: " + varNumeroCedula + "\n" +
                        "Grupo: " + grupo + "\n" +
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

    
    public void busquedaPorEscuela(ControllerPanelConsultas panel) {
        String varNombreEscuela = panel.campoBuscar.getText().trim();

        DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();

        boolean escuelaExiste = false;
        boolean coincidencia = false;

        panel.areaMostrar.setText("");

        // Validar si la escuela existe en la tabla de cursos
        for (int i = 0; i < modeloCursos.getRowCount(); i++) {
            String nombreEscuela = (String) modeloCursos.getValueAt(i, 0);
            if (varNombreEscuela.equalsIgnoreCase(nombreEscuela)) {
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
        for (int i = 0; i < modeloCursos.getRowCount(); i++) {
            String nombreEscuela = (String) modeloCursos.getValueAt(i, 0);
            String siglasCurso = (String) modeloCursos.getValueAt(i, 1);

            if (nombreEscuela.equalsIgnoreCase(varNombreEscuela)) {
                for (int j = 0; j < modeloProfesores.getRowCount(); j++) {
                    String siglasProfesor = (String) modeloProfesores.getValueAt(j, 0);

                    if (siglasCurso.equalsIgnoreCase(siglasProfesor)) {
                        coincidencia = true;

                        String cursoSiglas = (String) modeloProfesores.getValueAt(j, 0);
                        String nombreProfesor = (String) modeloProfesores.getValueAt(j, 1);
                        String primerApellido = (String) modeloProfesores.getValueAt(j, 2);
                        String segundoApellido = (String) modeloProfesores.getValueAt(j, 3);
                        String cedula = (String) modeloProfesores.getValueAt(j, 4);
                        String grupo = (String) modeloProfesores.getValueAt(j, 5);

                        String filaTexto =
                                "Escuela: " + nombreEscuela + "\n" +
                                "Curso: " + cursoSiglas + "\n" +
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
                    "No hay profesores asignados a la escuela: '" + varNombreEscuela + "'",
                    "Sin profesores", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView,
                    "¡Se encontraron profesores en la escuela: '" + varNombreEscuela + "'!",
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