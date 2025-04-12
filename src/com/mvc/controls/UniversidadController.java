package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.University;
import com.mvc.view.MainView;

public class UniversidadController {
    
    private MainView mainView;
    private University varUniversidadRegistrada;
    
    public UniversidadController(MainView mainView) {
        this.mainView = mainView;
        agregarControladorUniversidad();
    }
    
    private void agregarControladorUniversidad() {
        mainView.btnRegisterUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUniversidad();
            }
        });
        
        mainView.btnUpdateUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUniversidad();
            }
        });
    }
    
    private void agregarUniversidad() {
        String varNombre = mainView.txtName.getText().trim();
        String varDireccion = mainView.txtAdress.getText().trim();
        String varTelefono = mainView.txtPhoneNumber.getText().trim();

        if (!varNombre.isEmpty() && !varDireccion.isEmpty() && !varTelefono.isEmpty()) {
            varUniversidadRegistrada = new University(varNombre, varDireccion, varTelefono);

            JOptionPane.showMessageDialog(mainView, "Universidad registrada: " + varNombre, "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Actualizar la interfaz de usuario
            mainView.setUniversityName(varNombre);
            mainView.enableUpdateControls(true);
            mainView.enableEscuelaControls(true);
            mainView.enableRegisterUniversity(false);
            
            // Inicializar los campos para actualizar universidad
            mainView.txtNewAdress.setText(varDireccion);
            mainView.txtNewPhone.setText(varTelefono);
            
            // Mostrar panel de escuelas después de registrar universidad
            mainView.showPanel("ESCUELAS");
            
            // Limpiar campos de registro
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(mainView, "Todos los campos son obligatorios", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void actualizarUniversidad() {
        if (varUniversidadRegistrada != null) {
            String nuevaDireccion = mainView.txtNewAdress.getText().trim();
            String nuevoTelefono = mainView.txtNewPhone.getText().trim();

            if (!nuevaDireccion.isEmpty() && !nuevoTelefono.isEmpty()) {
                varUniversidadRegistrada.actualizarDatos(nuevaDireccion, nuevoTelefono);

                JOptionPane.showMessageDialog(mainView, "Datos actualizados correctamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainView, "Todos los campos deben estar completos", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "Primero se debe registrar una universidad", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        mainView.txtName.setText("");
        mainView.txtAdress.setText("");
        mainView.txtPhoneNumber.setText("");
    }
    
    public University getUniversidad() {
        return varUniversidadRegistrada;
    }
}