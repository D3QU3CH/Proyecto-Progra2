package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.Cursos;
import com.mvc.view.ViewCursos;
import com.mvc.view.ViewEscuelas;

public class CourseController {
    private ViewCursos vCursos;
    private Cursos varCursosRegistrar;
    private ViewEscuelas vEscuelas;
    
    public CourseController() {
        this.vCursos = new ViewCursos();
        vCursos.setVisible(false);
        
        agregarCursosActionListener();
        eliminarCursoActionListener();
    }
    
    public void abrirVistaCursos(ViewEscuelas viewEscuelas) {
        this.vEscuelas = viewEscuelas;
        vCursos.setVisible(true);
    }
    
    private void agregarCursosActionListener() {
        vCursos.varBtnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCursos();
            }
        });
    }
    
    private void eliminarCursoActionListener() {
        vCursos.varBtnEliminar.setEnabled(true);
        vCursos.varBtnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCurso();
                System.out.println("se dio click");
            }
        });
    }
    
    public void eliminarCurso() {
        String varSiglasCurso = vCursos.varTxtSigla.getText().trim();
        vCursos.eliminarCurso(varSiglasCurso);
    }
    
    public void agregarCursos() {
        if (vEscuelas == null) {
            JOptionPane.showMessageDialog(vCursos, "Error: No hay información de escuelas disponible", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String varSiglasCurso = vCursos.varTxtSigla.getText().trim();
        String varDescipcionDeCursos = vCursos.varTxtDescripcion.getText().trim();
        String varNombreEscuelas = vCursos.varTxtEscuelaNombres.getText().trim();

        if (!varSiglasCurso.isEmpty() && !varDescipcionDeCursos.isEmpty() && !varNombreEscuelas.isEmpty()) {
            String contenido = vEscuelas.txtAreaEscuelas.getText().toLowerCase().trim();
            System.out.println("Contenido del JTextArea: " + contenido);
            String[] lineas = contenido.split("\n");

            for (String linea : lineas) {
                String nombreEscuelaEnLinea = linea.replaceAll("^[0-9]+:", "").trim();

                if (nombreEscuelaEnLinea.equalsIgnoreCase(varNombreEscuelas)) {
                    varCursosRegistrar = new Cursos(varSiglasCurso, varDescipcionDeCursos, varNombreEscuelas);
                    Object[][] datos = { { varNombreEscuelas, varSiglasCurso, varDescipcionDeCursos } };
                    vCursos.agregarDatosTabla(datos);

                    JOptionPane.showMessageDialog(vCursos, "Curso registrado exitosamente", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarPanelCurso();
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(vCursos, "No se encontró la Escuela", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vCursos, "Todos los campos son obligatorios", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    void limpiarPanelCurso() {
        if (vCursos != null) {
            vCursos.varTxtSigla.setText("");
            vCursos.varTxtDescripcion.setText("");
            vCursos.varTxtEscuelaNombres.setText("");
        }
    }
}