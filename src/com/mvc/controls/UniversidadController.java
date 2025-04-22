package com.mvc.controls;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

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
    	mainView.btnRegisterUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	registerUniversity();
            }
        });
    }
    
    private void registerUniversity() {
        String varNombre = mainView.txtName.getText().trim();
        String varDireccion = mainView.txtAdress.getText().trim();
        String varTelefono = mainView.txtPhoneNumber.getText().trim();

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
            
            mainView.btnUpdateUniversity.setEnabled(true);
            mainView.btnRegisterSchool.setEnabled(true);
            mainView.btnRegisterUniversity.setEnabled(false);

            //Inicializar los campos para actualizar universidad
            mainView.txtNewAdress.setText(varDireccion);
            mainView.txtNewPhone.setText(varTelefono);
            
            mainView.registerPanel.removeAll();
            //mainView.registerPanel.revalidate();
            TitledBorder border = (TitledBorder) mainView.registerPanel.getBorder();
            border.setTitle("");
            
            JLabel tituloUniversidad = new JLabel("Universidad: "+varUniversidadRegistrada.getName(), SwingConstants.CENTER);
            tituloUniversidad.setFont(new Font("Arial", Font.ITALIC, 40)); // Tamaño grande
            mainView.registerPanel.setLayout(new BorderLayout()); // Para centrar fácilmente
            mainView.registerPanel.add(tituloUniversidad, BorderLayout.CENTER);
            
            mainView.registerPanel.repaint();
                       
            //Limpiar campos de registro
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Error!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void uptadeUniversityActionListener() {
    	mainView.btnUpdateUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	uptadeUniversity();
            }
        });
    }
    
    private void uptadeUniversity() {
        if (varUniversidadRegistrada != null) {
            String nuevaDireccion = mainView.txtNewAdress.getText().trim();
            String nuevoTelefono = mainView.txtNewPhone.getText().trim();

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
        mainView.txtName.setText("");
        mainView.txtAdress.setText("");
        mainView.txtPhoneNumber.setText("");
    }
    
    public University getUniversidad() {
        return varUniversidadRegistrada;
    }
}