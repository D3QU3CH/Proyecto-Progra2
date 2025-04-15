package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mvc.models.Cursos;
import com.mvc.view.MainView;

public class CursosController { 
    
    private MainView mainView;
    private Cursos varCursosRegistrar;
    
    public CursosController(MainView mainView) {
        this.mainView = mainView;
        agregarCursosActionListener();
        eliminarCursoActionListener();
        setupTableSelectionListener(); // Nuevo método para configurar el listener de selección
        modificarCursoActionListener(); //MODIFICAR CURSO
    }
    
    //AGREGAR CURSO
    private void agregarCursosActionListener() {
        // Añadir el ActionListener al botón de registrar curso
        mainView.varBtnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCursos();
            }
        });
    }
    
    public void agregarCursos() {
        String varSiglasCurso = mainView.varTxtSigla.getText().trim();
        String varDescipcionDeCursos = mainView.varTxtDescripcion.getText().trim();
        String varNombreEscuelas = mainView.varTxtEscuelaNombres.getText().trim();

        if (!varSiglasCurso.isEmpty() && !varDescipcionDeCursos.isEmpty() && !varNombreEscuelas.isEmpty()) {
            String contenido = mainView.txtAreaEscuelas.getText().toLowerCase().trim();
            String[] lineas = contenido.split("\n");

            for (String linea : lineas) {
                String nombreEscuelaEnLinea = linea.replaceAll("^[0-9]+:", "").trim();

                if (nombreEscuelaEnLinea.equalsIgnoreCase(varNombreEscuelas)) {
                    varCursosRegistrar = new Cursos(varSiglasCurso, varDescipcionDeCursos, varNombreEscuelas);
                    Object[][] datos = { { varNombreEscuelas, varSiglasCurso, varDescipcionDeCursos } };
                    agregarDatosTabla(datos);

                    JOptionPane.showMessageDialog(mainView, "¡Curso registrado exitosamente!", "¡Éxito!",JOptionPane.INFORMATION_MESSAGE);
                    limpiarPanelCurso();
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(mainView, "¡No se encontró la Escuela!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "!Advertencia¡",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void agregarDatosTabla(Object[][] datos) {
        DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();
        
        // Agregar los nuevos datos
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }
    //ELIMINAR CURSO
    private void eliminarCursoActionListener() {
        mainView.varBtnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCurso();
            }
        });
    }
    
    public void eliminarCurso() {
        String varSiglasCurso = mainView.varTxtSigla.getText().trim();
        DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();
        boolean seleccionado = false;
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object valorCelda = modeloTabla.getValueAt(i, 1);
            if (valorCelda != null && valorCelda.toString().equalsIgnoreCase(varSiglasCurso)) {
                modeloTabla.removeRow(i);
                JOptionPane.showMessageDialog(mainView, "¡Se eliminó el curso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                seleccionado = true;
                break;
            }
        }
        
        if (!seleccionado) {
            JOptionPane.showMessageDialog(mainView, "¡No se seleccionó el curso!", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
        limpiarPanelCurso(); // Limpiar los campos después de eliminar
    }
    
    
    //SELECCIONAR CURSO
    private void setupTableSelectionListener() {
        mainView.tablaCursos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    llenarFormularioDesdeTabla();
                }
            }
        });
        
    }
    
    //Metodo para llenar los campos de texto con los datos de la fila seleccionada
    private void llenarFormularioDesdeTabla() {
        int fila = mainView.tablaCursos.getSelectedRow();
        if (fila != -1) {
            // Obtener los valores de la fila seleccionada
            String escuela = mainView.tablaCursos.getValueAt(fila, 0).toString();
            String siglas = mainView.tablaCursos.getValueAt(fila, 1).toString();
            String descripcion = mainView.tablaCursos.getValueAt(fila, 2).toString();
            
            // Llenar los campos de texto
            mainView.varTxtEscuelaNombres.setText(escuela);
            mainView.varTxtSigla.setText(siglas);
            mainView.varTxtDescripcion.setText(descripcion);
            
            // Actualizar estado de los botones (opcional)
            mainView.varBtnRegistrar.setEnabled(false);
            mainView.varTxtEscuelaNombres.setEnabled(false);
            mainView.varTxtSigla .setEnabled(false);
            
            mainView.varBtnModificar.setEnabled(true);
        }
    }
    
    //MODIFICAR CURSO
    private void modificarCursoActionListener() {
        mainView.varBtnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarCurso();
            }
        });
    }

    public void modificarCurso() {
        int fila = mainView.tablaCursos.getSelectedRow();
        
        if (fila != -1) {
            String varSiglasCurso = mainView.varTxtSigla.getText().trim();
            String varDescipcionDeCursos = mainView.varTxtDescripcion.getText().trim();
            String varNombreEscuelas = mainView.varTxtEscuelaNombres.getText().trim();

            if (!varSiglasCurso.isEmpty() && !varDescipcionDeCursos.isEmpty() && !varNombreEscuelas.isEmpty()) {
                // Actualizar datos en la tabla
                mainView.tablaCursos.setValueAt(varNombreEscuelas, fila, 0);
                mainView.tablaCursos.setValueAt(varSiglasCurso, fila, 1);
                mainView.tablaCursos.setValueAt(varDescipcionDeCursos, fila, 2);

                JOptionPane.showMessageDialog(mainView, "¡Curso actualizado exitosamente!", "¡Éxito!",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarPanelCurso();
            } else {
                JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios", "¡Advertencia!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Seleccione un curso para modificar", "¡Advertencia!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    void limpiarPanelCurso() {
        mainView.varTxtSigla.setText("");
        mainView.varTxtDescripcion.setText("");
        mainView.varTxtEscuelaNombres.setText("");
        mainView.tablaCursos.clearSelection(); // Limpiar selección de la tabla
        
        // Restablecer estado de los botones
        mainView.varBtnRegistrar.setEnabled(true);
        mainView.varTxtEscuelaNombres.setEnabled(true);
        mainView.varTxtSigla .setEnabled(true);
        
        mainView.varBtnModificar.setEnabled(false);

    }
}