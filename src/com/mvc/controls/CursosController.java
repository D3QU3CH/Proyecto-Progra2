package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.Cursos;
import com.mvc.view.MainView;

public class CursosController { 
    
    private MainView mainView;
    private Cursos varCursosRegistrar;
    
    public CursosController(MainView mainView) {
        this.mainView = mainView;
        agregarCursosActionListener();
        eliminarCursoActionListener();
    }
    
    private void agregarCursosActionListener() {
        // Añadir el ActionListener al botón de registrar curso
        mainView.varBtnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCursos();
            }
        });
    }
    
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
        mainView.eliminarCurso(varSiglasCurso);
    }
    
    public void agregarCursos() {
        String varSiglasCurso = mainView.varTxtSigla.getText().trim();
        String varDescipcionDeCursos = mainView.varTxtDescripcion.getText().trim();
        String varNombreEscuelas = mainView.varTxtEscuelaNombres.getText().trim();

        if (!varSiglasCurso.isEmpty() && !varDescipcionDeCursos.isEmpty() && !varNombreEscuelas.isEmpty()) {
            String contenido = mainView.txtAreaEscuelas.getText().toLowerCase().trim();
            //System.out.println("Contenido del JTextArea: " + contenido);
            String[] lineas = contenido.split("\n");

            for (String linea : lineas) {
                // Eliminamos el número y los dos puntos al inicio de la línea (por ejemplo, "1:")
                String nombreEscuelaEnLinea = linea.replaceAll("^[0-9]+:", "").trim();

                if (nombreEscuelaEnLinea.equalsIgnoreCase(varNombreEscuelas)) {
                    varCursosRegistrar = new Cursos(varSiglasCurso, varDescipcionDeCursos, varNombreEscuelas);
                    Object[][] datos = { { varNombreEscuelas, varSiglasCurso, varDescipcionDeCursos } };
                    mainView.agregarDatosTabla(datos);

                    JOptionPane.showMessageDialog(mainView, "Curso registrado exitosamente", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarPanelCurso();
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(mainView, "No se encontró la Escuela", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainView, "Todos los campos son obligatorios", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    void limpiarPanelCurso() {
        mainView.varTxtSigla.setText("");
        mainView.varTxtDescripcion.setText("");
        mainView.varTxtEscuelaNombres.setText("");
    }
}