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
        registerUniversityActionListener();
        uptadeUniversityActionListener();
    }
    
    private void registerUniversityActionListener() {
    	mainView.U_btnRegisterUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	registerUniversity();
            }
        });
    }
    
    private void registerUniversity() {
        String varNombre = mainView.U_txtName.getText().trim();
        String varDireccion = mainView.U_txtAdress.getText().trim();
        String varTelefono = mainView.U_txtPhoneNumber.getText().trim();

        if (!varNombre.isEmpty() && !varDireccion.isEmpty() && !varTelefono.isEmpty()) {
        	
        	  if (!varTelefono.matches("\\d+")) {
                  JOptionPane.showMessageDialog(mainView, "El número de teléfono solo debe contener números.", "¡Error!",
                          JOptionPane.ERROR_MESSAGE);
                  return; // No continuar si no es válido
              }
        	
            varUniversidadRegistrada = new University(varNombre, varDireccion, varTelefono);

            JOptionPane.showMessageDialog(mainView, "Universidad registrada: " + varNombre, "¡Éxito!",
                    JOptionPane.INFORMATION_MESSAGE);

            //Actualizar la interfaz de usuario
            mainView.setUniversityName(varNombre);
            
            mainView.U_btnUpdateUniversity.setEnabled(true);
            mainView.btnRegisterSchool.setEnabled(true);
            mainView.U_btnRegisterUniversity.setEnabled(false);

            //Inicializar los campos para actualizar universidad
            mainView.U_txtNewAdress.setText(varDireccion);
            mainView.U_txtNewPhone.setText(varTelefono);
            
            //Mostrar panel de escuelas después de registrar universidad
            mainView.showPanel("ESCUELAS");
            
            //Limpiar campos de registro
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Error!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void uptadeUniversityActionListener() {
    	mainView.U_btnUpdateUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	uptadeUniversity();
            }
        });
    }
    
    private void uptadeUniversity() {
        if (varUniversidadRegistrada != null) {
            String nuevaDireccion = mainView.U_txtNewAdress.getText().trim();
            String nuevoTelefono = mainView.U_txtNewPhone.getText().trim();

            if (!nuevaDireccion.isEmpty() && !nuevoTelefono.isEmpty()) {
                varUniversidadRegistrada.actualizarDatos(nuevaDireccion, nuevoTelefono);

                JOptionPane.showMessageDialog(mainView, "¡Datos actualizados correctamente!", "¡Éxito!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainView, "¡Todos los campos deben estar completos!", "¡Error!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Primero se debe registrar una universidad!", "¡Error!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        mainView.U_txtName.setText("");
        mainView.U_txtAdress.setText("");
        mainView.U_txtPhoneNumber.setText("");
    }
    
    public University getUniversidad() {
        return varUniversidadRegistrada;
    }
}