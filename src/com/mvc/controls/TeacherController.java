package com.mvc.controls;

import com.google.gson.Gson;
import com.mvc.models.Teacher;
import com.mvc.view.MainView;
import com.mvc.models.University;
import com.mvc.models.School;
import com.mvc.controls.UniversityController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TeacherController {

    private MainView mainView;
    private UniversityController universityController;

    public TeacherController(MainView mainView, UniversityController universityController) {
        this.mainView = mainView;
        this.universityController = universityController;

        // Acciones normales
        agregarProfesorActionListener();
        modificarProfesorActionListener();
        eliminarProfesorActionListener();
        setupTableSelectionListener();
        setupBtnDeseleccionarTablaActionListener();

        // Asignaciones
        asignarProfesorActionListener();
        desasignarProfesorActionListener();
        setupTableAsigSelectionListener();
        asignarDirectorActionListener();  // Aquí está tu método importante
        deseleccionarTablaAsigActionListener();

        // Cargar automáticamente los datos al iniciar
        cargarDataProfesores();
        cargarDataProfesoresAsignados();
    }

    // ========== CRUD Profesor ==========

    private void agregarProfesorActionListener() {
        mainView.btnAgregarProfesor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProfesor();
            }
        });
    }

    public void agregarProfesor() {
        String nombre = mainView.txtNombreProfesor.getText().trim();
        String apellido1 = mainView.txtPrimerApe.getText().trim();
        String apellido2 = mainView.txtSegundoApe.getText().trim();
        String cedula = mainView.txtCedula.getText().trim();

        if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(mainView, "Todos los campos son obligatorios", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(mainView, "La cedula debe contener solo números", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 3).equals(cedula)) {
                JOptionPane.showMessageDialog(mainView, "Ya existe un profesor con esa cedula", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        modelo.addRow(new Object[]{nombre, apellido1, apellido2, cedula});
        JOptionPane.showMessageDialog(mainView, "Profesor agregado exitosamente", "Exito",
                JOptionPane.INFORMATION_MESSAGE);
        limpiarCamposProfesor();
        mostrarProfesoresEnTextArea();
        escribirDataProfesores();
    }

    private void modificarProfesorActionListener() {
        mainView.btnModificarProfesor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProfesor();
            }
        });
    }

    public void modificarProfesor() {
        int filaSeleccionada = mainView.tablaProfesores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(mainView, "Seleccione un profesor para modificar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nuevoNombre = mainView.txtNombreProfesor.getText().trim();
        String nuevoApellido1 = mainView.txtPrimerApe.getText().trim();
        String nuevoApellido2 = mainView.txtSegundoApe.getText().trim();

        if (nuevoNombre.isEmpty() || nuevoApellido1.isEmpty() || nuevoApellido2.isEmpty()) {
            JOptionPane.showMessageDialog(mainView, "Nombre y apellidos no pueden estar vacíos", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();
        modelo.setValueAt(nuevoNombre, filaSeleccionada, 0);
        modelo.setValueAt(nuevoApellido1, filaSeleccionada, 1);
        modelo.setValueAt(nuevoApellido2, filaSeleccionada, 2);

        JOptionPane.showMessageDialog(mainView, "Profesor modificado exitosamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        limpiarPanelProfesor();
        mostrarProfesoresEnTextArea();
        escribirDataProfesores();
    }

    private void eliminarProfesorActionListener() {
        mainView.btnEliminarProfesor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProfesor();
            }
        });
    }

    public void eliminarProfesor() {
        String varCedulaProf = mainView.txtCedula.getText().trim();
        DefaultTableModel modeloTablaProf = (DefaultTableModel) mainView.tablaProfesores.getModel();
        DefaultTableModel modeloTablaAsig = (DefaultTableModel) mainView.tablaAsignaciones.getModel();

        boolean seleccionado = false;

        // Eliminar de la tabla de profesores
        for (int i = 0; i < modeloTablaProf.getRowCount(); i++) {
            Object valorCedula = modeloTablaProf.getValueAt(i, 3);
            if (valorCedula != null && valorCedula.toString().equalsIgnoreCase(varCedulaProf)) {
                modeloTablaProf.removeRow(i);
                seleccionado = true;
                break;
            }
        }

        // Eliminar todas las asignaciones asociadas al profesor
        if (seleccionado) {
            for (int j = modeloTablaAsig.getRowCount() - 1; j >= 0; j--) {
                String cedulaAsig = (String) modeloTablaAsig.getValueAt(j, 1);
                if (cedulaAsig.equalsIgnoreCase(varCedulaProf)) {
                    modeloTablaAsig.removeRow(j);
                }
            }

            JOptionPane.showMessageDialog(mainView, "¡Profesor y sus asignaciones eliminados exitosamente!", "¡Éxito!",
                    JOptionPane.INFORMATION_MESSAGE);

            // Guardar cambios en JSON
            escribirDataProfesores();
            escribirDataProfesoresAsignados();
        } else {
            JOptionPane.showMessageDialog(mainView, "¡No se selecciono el profesor!", "¡Advertencia!",
                    JOptionPane.WARNING_MESSAGE);
        }

        limpiarPanelProfesor();
        mostrarProfesoresEnTextArea();
    }

    private void setupTableSelectionListener() {
        mainView.tablaProfesores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) llenarFormularioDesdeTabla();
            }
        });
    }

    private void llenarFormularioDesdeTabla() {
        int fila = mainView.tablaProfesores.getSelectedRow();
        if (fila != -1) {
            DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();
            mainView.txtNombreProfesor.setText((String) modelo.getValueAt(fila, 0));
            mainView.txtPrimerApe.setText((String) modelo.getValueAt(fila, 1));
            mainView.txtSegundoApe.setText((String) modelo.getValueAt(fila, 2));
            mainView.txtCedula.setText((String) modelo.getValueAt(fila, 3));

            mainView.btnAgregarProfesor.setEnabled(false);
            mainView.txtCedula.setEnabled(false);
            mainView.btnModificarProfesor.setEnabled(true);
            mainView.btnDeseleccionarTablaProf.setEnabled(true);
            mainView.btnEliminarProfesor.setEnabled(true);
        }
    }

    private void limpiarCamposProfesor() {
        mainView.txtNombreProfesor.setText("");
        mainView.txtPrimerApe.setText("");
        mainView.txtSegundoApe.setText("");
        mainView.txtCedula.setText("");
    }

    private void setupBtnDeseleccionarTablaActionListener() {
        mainView.btnDeseleccionarTablaProf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarPanelProfesor();
            }
        });
    }

    private void limpiarPanelProfesor() {
        limpiarCamposProfesor();
        mainView.tablaProfesores.clearSelection();
        mainView.btnAgregarProfesor.setEnabled(true);
        mainView.txtCedula.setEnabled(true);
        mainView.btnModificarProfesor.setEnabled(false);
        mainView.btnDeseleccionarTablaProf.setEnabled(false);
        mainView.btnEliminarProfesor.setEnabled(false);
    }

    // ========== Asignación de Profesores ==========

    private void setupTableAsigSelectionListener() {
        mainView.tablaAsignaciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) llenarFormularioDesdeTablaAsig();
            }
        });
    }

    private void llenarFormularioDesdeTablaAsig() {
        int fila = mainView.tablaAsignaciones.getSelectedRow();
        if (fila != -1) {
            DefaultTableModel modelo = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
            mainView.txtCedulaAsignar.setText((String) modelo.getValueAt(fila, 1));
            mainView.txtSiglasAsignar.setText((String) modelo.getValueAt(fila, 2));
            mainView.txtGrupoAsignar.setText((String) modelo.getValueAt(fila, 3));

            mainView.btnAsignarProfesorAsig.setEnabled(false);
            mainView.btnDesasignarProfesorAsig.setEnabled(true);
            mainView.btnAsignarDirectorAsig.setEnabled(true);
        }
    }

    private void asignarProfesorActionListener() {
        mainView.btnAsignarProfesorAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarProfesor();
            }
        });
    }

    public void asignarProfesor() {
        String cedula = mainView.txtCedulaAsignar.getText().trim();
        String sigla = mainView.txtSiglasAsignar.getText().trim();
        String grupo = mainView.txtGrupoAsignar.getText().trim();

        if (cedula.isEmpty() || sigla.isEmpty() || grupo.isEmpty()) {
            JOptionPane.showMessageDialog(mainView, "Todos los campos son obligatorios", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
        boolean profesorExiste = false;

        for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
            if (modeloProfesores.getValueAt(i, 3).equals(cedula)) {
                profesorExiste = true;
                break;
            }
        }

        if (!profesorExiste) {
            JOptionPane.showMessageDialog(mainView, "No existe un profesor con esa cedula", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
        boolean cursoExiste = false;
        String escuela = "";

        for (int i = 0; i < modeloCursos.getRowCount(); i++) {
            if (modeloCursos.getValueAt(i, 1).equals(sigla)) {
                escuela = (String) modeloCursos.getValueAt(i, 0);
                cursoExiste = true;
                break;
            }
        }

        if (!cursoExiste) {
            JOptionPane.showMessageDialog(mainView, "No existe un curso con esas siglas", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modeloAsignaciones = (DefaultTableModel) mainView.tablaAsignaciones.getModel();

        for (int i = 0; i < modeloAsignaciones.getRowCount(); i++) {
            String cedulaExistente = (String) modeloAsignaciones.getValueAt(i, 1);
            String escuelaExistente = (String) modeloAsignaciones.getValueAt(i, 0);
            if (cedula.equals(cedulaExistente) && !escuela.equals(escuelaExistente)) {
                JOptionPane.showMessageDialog(mainView,
                        "El profesor ya esta asignado a otra escuela. Solo puede pertenecer a una.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        for (int i = 0; i < modeloAsignaciones.getRowCount(); i++) {
            String cedulaExistente = (String) modeloAsignaciones.getValueAt(i, 1);
            String siglaExistente = (String) modeloAsignaciones.getValueAt(i, 2);
            String grupoExistente = (String) modeloAsignaciones.getValueAt(i, 3);
            if (cedula.equals(cedulaExistente) && sigla.equals(siglaExistente)
                    && grupo.equals(grupoExistente)) {
                JOptionPane.showMessageDialog(mainView, "Esta asignación ya existe", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        modeloAsignaciones.addRow(new Object[]{escuela, cedula, sigla, grupo});
        JOptionPane.showMessageDialog(mainView, "Asignación registrada exitosamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        limpiarCamposAsignacion();
        escribirDataProfesoresAsignados();
    }

    private void desasignarProfesorActionListener() {
        mainView.btnDesasignarProfesorAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarProfesor();
            }
        });
    }

    public void desasignarProfesor() {
        int fila = mainView.tablaAsignaciones.getSelectedRow();
        if (fila != -1) {
            ((DefaultTableModel) mainView.tablaAsignaciones.getModel()).removeRow(fila);
            JOptionPane.showMessageDialog(mainView, "Asignación eliminada exitosamente", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarPanelAsignacion();
            escribirDataProfesoresAsignados();
        } else {
            JOptionPane.showMessageDialog(mainView, "Seleccione una asignación para eliminar", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void asignarDirectorActionListener() {
        mainView.btnAsignarDirectorAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarDirector();
            }
        });
    }

    public void asignarDirector() {
        int fila = mainView.tablaAsignaciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainView, "Seleccione una asignación", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modeloAsignaciones = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
        DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();

        String escuela = (String) modeloAsignaciones.getValueAt(fila, 0);
        String cedula = (String) modeloAsignaciones.getValueAt(fila, 1);

        String nombre = "";
        String apellido1 = "";
        String apellido2 = "";

        for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
            if (modeloProfesores.getValueAt(i, 3).equals(cedula)) {
                nombre = (String) modeloProfesores.getValueAt(i, 0);
                apellido1 = (String) modeloProfesores.getValueAt(i, 1);
                apellido2 = (String) modeloProfesores.getValueAt(i, 2);
                break;
            }
        }

        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Período en años:");
        JTextField txtPeriodo = new JTextField(10);
        panel.add(label);
        panel.add(txtPeriodo);

        int resultado = JOptionPane.showConfirmDialog(mainView, panel, "Ingrese periodo del director",
                JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            String periodoTexto = txtPeriodo.getText().trim();
            if (periodoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(mainView, "Debe ingresar un periodo", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int periodo = Integer.parseInt(periodoTexto);
                if (periodo <= 0) {
                    JOptionPane.showMessageDialog(mainView, "El periodo debe ser mayor a 0", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                University universidad = universityController.getUniversidad();
                if (universidad != null && universidad.getEscuelas() != null) {
                    for (School esc : universidad.getEscuelas()) {
                        if (esc.getVarName().equalsIgnoreCase(escuela)) {
                            esc.setVarDirector(nombre + " " + apellido1 + " " + apellido2);
                            JOptionPane.showMessageDialog(mainView,
                                    "Director asignado exitosamente por " + periodo + " años",
                                    "Exito", JOptionPane.INFORMATION_MESSAGE);
                            String texto =
                                "Director: " + nombre + " " + apellido1 + " " + apellido2 + "\n" +
                                "Cedula: " + cedula + "\n" +
                                "Escuela: " + escuela + "\n" +
                                "Período: " + periodo + " años\n" +
                                "----------------------------\n";
                            mainView.txtAreaDirectores.append(texto);
                            break;
                        }
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainView, "Debe ingresar un numero valido", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deseleccionarTablaAsigActionListener() {
        mainView.btnDeseleccionarTablaAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarPanelAsignacion();
            }
        });
    }

    private void limpiarCamposAsignacion() {
        mainView.txtCedulaAsignar.setText("");
        mainView.txtSiglasAsignar.setText("");
        mainView.txtGrupoAsignar.setText("");
    }

    private void limpiarPanelAsignacion() {
        limpiarCamposAsignacion();
        mainView.tablaAsignaciones.clearSelection();
        mainView.btnAsignarProfesorAsig.setEnabled(true);
        mainView.btnDesasignarProfesorAsig.setEnabled(false);
        mainView.btnAsignarDirectorAsig.setEnabled(false);
    }

    public void mostrarProfesoresEnTextArea() {
        DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();
        mainView.txtAreaProfesores.setText("");
        for (int i = 0; i < modelo.getRowCount(); i++) {
            mainView.txtAreaProfesores.append("Nombre: " + modelo.getValueAt(i, 0) + " "
                    + modelo.getValueAt(i, 1) + " " + modelo.getValueAt(i, 2)
                    + " | Cédula: " + modelo.getValueAt(i, 3) + "\n");
        }
    }

    // ========== JSON ==========

    public void escribirDataProfesores() {
        DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();
        List<Teacher> profesores = new ArrayList<>();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            profesores.add(new Teacher(
                    modelo.getValueAt(i, 0).toString(),
                    modelo.getValueAt(i, 1).toString(),
                    modelo.getValueAt(i, 2).toString(),
                    modelo.getValueAt(i, 3).toString(), "", ""));
        }
        try {
            Gson gson = new Gson();
            String json = gson.toJson(profesores);
            Files.write(Paths.get("ProfesoresSinAsignar.json"), json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDataProfesores() {
        try {
            Path path = Paths.get("ProfesoresSinAsignar.json");
            if (!Files.exists(path)) return;

            String contenido = Files.readString(path, StandardCharsets.UTF_8);
            Teacher[] array = new Gson().fromJson(contenido, Teacher[].class);
            DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();
            modelo.setRowCount(0);
            if (array != null) {
                for (Teacher t : array) {
                    modelo.addRow(new Object[]{t.getVarName(), t.getVarFirstSurname(),
                            t.getVarSecondSurname(), t.getVarId()});
                }
                mostrarProfesoresEnTextArea();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribirDataProfesoresAsignados() {
        DefaultTableModel modeloAsig = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
        DefaultTableModel modeloProf = (DefaultTableModel) mainView.tablaProfesores.getModel();
        List<Teacher> lista = new ArrayList<>();

        for (int i = 0; i < modeloAsig.getRowCount(); i++) {
            String escuela = modeloAsig.getValueAt(i, 0).toString();
            String cedula = modeloAsig.getValueAt(i, 1).toString();
            String siglas = modeloAsig.getValueAt(i, 2).toString();
            String grupo = modeloAsig.getValueAt(i, 3).toString();

            String nombre = "";
            String apellido1 = "";
            String apellido2 = "";

            for (int j = 0; j < modeloProf.getRowCount(); j++) {
                if (modeloProf.getValueAt(j, 3).equals(cedula)) {
                    nombre = modeloProf.getValueAt(j, 0).toString();
                    apellido1 = modeloProf.getValueAt(j, 1).toString();
                    apellido2 = modeloProf.getValueAt(j, 2).toString();
                    break;
                }
            }

            lista.add(new Teacher(nombre, apellido1, apellido2, cedula, escuela, siglas + "-" + grupo));
        }

        try {
            Gson gson = new Gson();
            String json = gson.toJson(lista);
            Files.write(Paths.get("ProfesoresAsignados.json"), json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDataProfesoresAsignados() {
        try {
            Path path = Paths.get("ProfesoresAsignados.json");
            if (!Files.exists(path)) return;

            String contenido = Files.readString(path, StandardCharsets.UTF_8);
            Teacher[] array = new Gson().fromJson(contenido, Teacher[].class);
            DefaultTableModel modelo = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
            modelo.setRowCount(0);
            if (array != null) {
                for (Teacher t : array) {
                    String[] partes = t.getVarSiglasCourses().split("-");
                    modelo.addRow(new Object[]{
                            t.getVarGroup(),
                            t.getVarId(),
                            partes[0],
                            partes.length > 1 ? partes[1] : ""
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


