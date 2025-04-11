package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.University;
import com.mvc.view.ViewUniversidad;

public class UniversityController {
    private ViewUniversidad vViewUniversidad;
    private University varUniversidadRegistrada;
    
    public UniversityController() {
        this.vViewUniversidad = new ViewUniversidad();
        
        agregarControladores();
    }
    
    public void abrirVistaUniversidad() {
        if (vViewUniversidad == null) {
        	vViewUniversidad = new ViewUniversidad();
            agregarControladores();
        }
        
        vViewUniversidad.setVisible(true);
    }
    
    private void agregarControladores() {
    	vViewUniversidad.btnRegisterUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUniversidad();
            }
        });
        
    	vViewUniversidad.btnUpdateUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUniversidad();
            }
        });
    }
    
    private void agregarUniversidad() {
        String varNombre = vViewUniversidad.txtName.getText().trim();
        String varDireccion = vViewUniversidad.txtAdress.getText().trim();
        String varTelefono = vViewUniversidad.txtPhoneNumber.getText().trim();

        if (!varNombre.isEmpty() && !varDireccion.isEmpty() && !varTelefono.isEmpty()) {
            varUniversidadRegistrada = new University(varNombre, varDireccion, varTelefono);

            JOptionPane.showMessageDialog(vViewUniversidad, "Universidad registrada: " + varNombre, "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            
            vViewUniversidad.actualizarNombreUniversidad(varNombre);

            vViewUniversidad.txtName.setText("");
            vViewUniversidad.txtAdress.setText("");
            vViewUniversidad.txtPhoneNumber.setText("");
        } else {
            JOptionPane.showMessageDialog(vViewUniversidad, "Todos los campos son obligatorios", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void actualizarUniversidad() {
        if (varUniversidadRegistrada != null) {
            String nuevaDireccion = vViewUniversidad.txtNewAdress.getText().trim();
            String nuevoTelefono = vViewUniversidad.txtNewPhone.getText().trim();

            if (!nuevaDireccion.isEmpty() && !nuevoTelefono.isEmpty()) {
                varUniversidadRegistrada.actualizarDatos(nuevaDireccion, nuevoTelefono);

                JOptionPane.showMessageDialog(vViewUniversidad, "Datos actualizados correctamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                vViewUniversidad.txtNewAdress.setText("");
                vViewUniversidad.txtNewPhone.setText("");
            } else {
                JOptionPane.showMessageDialog(vViewUniversidad, "Todos los campos deben estar completos", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vViewUniversidad, "Primero se debe registrar una universidad", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public University getUniversidadRegistrada() {
        return varUniversidadRegistrada;
    }
}