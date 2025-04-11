package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.Cursos;
import com.mvc.models.Escuela;
import com.mvc.models.University;
import com.mvc.view.ViewActualizarUniversidad;
import com.mvc.view.ViewCursos;  // Nueva clase combinada
import com.mvc.view.ViewEscuelas;
import com.mvc.view.ViewRegistrarUniversidad;

public class Control {

    private ViewRegistrarUniversidad vRegistrarUniversidad;
    private ViewEscuelas vEscuelas;
    private ViewActualizarUniversidad vActualizarUniversidad;
    private ViewCursos vCursos; 
    private University varUniversidadRegistrada;
    private Cursos varCursosRegistrar;

    public Control(ViewRegistrarUniversidad pRegistrarUniversidad) {
        this.vRegistrarUniversidad = pRegistrarUniversidad;
        vRegistrarUniversidad.setVisible(true);

        this.vCursos = new ViewCursos();  // Inicializa la vista combinada
        vCursos.setVisible(false);  // No visible al inicio
        
        agregarCursosActionListener();
        agregarControladorUniversidad();
        eliminarCursoActionListener();
    }

    private void agregarControladorUniversidad() {
        vRegistrarUniversidad.btnRegisterUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUniversidad();
            }
        });
    }

    private void agregarCursosActionListener() {
        // Añadir el ActionListener al botón de registrar curso
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
        String varSiglasCurso = vCursos.varTxtSigla.getText().trim();
        String varDescipcionDeCursos = vCursos.varTxtDescripcion.getText().trim();
        String varNombreEscuelas = vCursos.varTxtEscuelaNombres.getText().trim();

        if (!varSiglasCurso.isEmpty() && !varDescipcionDeCursos.isEmpty() && !varNombreEscuelas.isEmpty()) {
            String contenido = varNombreEscuelas.toLowerCase().trim();
            System.out.println("Contenido del JTextArea: " + contenido);
            String[] lineas = contenido.split("\n");

            for (String linea : lineas) {
                // Eliminamos el número y los dos puntos al inicio de la línea (por ejemplo,
                // "1:")
                String nombreEscuelaEnLinea = linea.replaceAll("^[0-9]+:", "").trim();

                if (nombreEscuelaEnLinea.equalsIgnoreCase(varNombreEscuelas)) {
                    varCursosRegistrar = new Cursos(varSiglasCurso, varDescipcionDeCursos, varNombreEscuelas);
                    Object[][] datos = { { varNombreEscuelas, varSiglasCurso, varDescipcionDeCursos } };
                    vCursos.agregarDatosTabla(datos);

                    JOptionPane.showMessageDialog(vCursos, "Curso registrado exitosamente", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarPanelCurso();
                    break;
                } else {
                    JOptionPane.showMessageDialog(vCursos, "No se encontró la Escuela", "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
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

    private void agregarUniversidad() {
        String varNombre = vRegistrarUniversidad.txtName.getText().trim();
        String varDireccion = vRegistrarUniversidad.txtAdress.getText().trim();
        String varTelefono = vRegistrarUniversidad.txtPhoneNumber.getText().trim();

        if (!varNombre.isEmpty() && !varDireccion.isEmpty() && !varTelefono.isEmpty()) {
            varUniversidadRegistrada = new University(varNombre, varDireccion, varTelefono);

            JOptionPane.showMessageDialog(vRegistrarUniversidad, "Universidad registrada: " + varNombre, "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            vRegistrarUniversidad.dispose();

            vActualizarUniversidad = new ViewActualizarUniversidad(varNombre, varDireccion, varTelefono);
            vEscuelas = new ViewEscuelas(varNombre);

            agregarControladorActualizar();
            agregarControladorEscuela();
        } else {
            JOptionPane.showMessageDialog(vRegistrarUniversidad, "Todos los campos son obligatorios", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void agregarControladorActualizar() {
        vActualizarUniversidad.btnUpdateUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUniversidad();
            }
        });
    }

    private void actualizarUniversidad() {
        if (varUniversidadRegistrada != null) {
            String nuevaDireccion = vActualizarUniversidad.txtNewAdress.getText().trim();
            String nuevoTelefono = vActualizarUniversidad.txtNewPhone.getText().trim();

            if (!nuevaDireccion.isEmpty() && !nuevoTelefono.isEmpty()) {
                varUniversidadRegistrada.actualizarDatos(nuevaDireccion, nuevoTelefono);

                JOptionPane.showMessageDialog(vActualizarUniversidad, "Datos actualizados correctamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vActualizarUniversidad, "Todos los campos deben estar completos", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vActualizarUniversidad, "Primero se debe registrar una universidad", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void agregarControladorEscuela() {
        vEscuelas.btnRegisterSchool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEscuela();

                vCursos.setVisible(true);  // Mostramos la vista de cursos
            }
        });
    }

    private void agregarEscuela() {
        if (varUniversidadRegistrada != null) {
            String nombreEscuela = vEscuelas.txtNameSchool.getText().trim();

            if (!nombreEscuela.isEmpty()) {
                Escuela nuevaEscuela = new Escuela(nombreEscuela);
                varUniversidadRegistrada.agregarEscuela(nuevaEscuela);

                agregarEscuelasTxtArea();

                vEscuelas.txtNameSchool.setText("");
            } else {
                JOptionPane.showMessageDialog(vEscuelas, "El nombre de la escuela es obligatorio", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void agregarEscuelasTxtArea() {
        if (varUniversidadRegistrada != null && varUniversidadRegistrada.getEscuelas() != null) {
            StringBuilder lista = new StringBuilder();
            int contador = 0;
            for (Escuela esc : varUniversidadRegistrada.getEscuelas()) {
                contador++;
                lista.append(contador + ":").append(esc.getVarName()).append("\n");
            }
            vEscuelas.txtAreaEscuelas.setText(lista.toString());
        }
    }

    private void limpiarCampos() {
        if (vRegistrarUniversidad != null) {
            vRegistrarUniversidad.txtName.setText("");
            vRegistrarUniversidad.txtAdress.setText("");
            vRegistrarUniversidad.txtPhoneNumber.setText("");
        }

        if (vActualizarUniversidad != null) {
            vActualizarUniversidad.txtNewAdress.setText("");
            vActualizarUniversidad.txtNewPhone.setText("");
        }

        if (vEscuelas != null) {
            vEscuelas.txtNameSchool.setText("");
        }
        
        limpiarPanelCurso();
    }
}