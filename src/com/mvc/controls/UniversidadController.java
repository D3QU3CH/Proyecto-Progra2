package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.University;
import com.mvc.view.MainView;

public class UniversidadController {

    private MainView mainView;
    private University varRegisteredUniversity;

    public UniversidadController(MainView pMainView) {
        this.mainView = pMainView;
        registerUniversityActionListener();
        updateUniversityActionListener();
    }

    private void registerUniversityActionListener() {
        mainView.varRegisterUniversityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUniversity();
            }
        });
    }

    private void registerUniversity() {
        String varName = mainView.varNameField.getText().trim();
        String varAddress = mainView.varAddressField.getText().trim();
        String varPhone = mainView.varPhoneField.getText().trim();

        if (!varName.isEmpty() && !varAddress.isEmpty() && !varPhone.isEmpty()) {

            if (!varPhone.matches("\\d+")) {
                JOptionPane.showMessageDialog(mainView, "El número de teléfono solo debe contener números.", "¡Error!",
                        JOptionPane.ERROR_MESSAGE);
                return; // No continuar si no es válido
            }

            varRegisteredUniversity = new University(varName, varAddress, varPhone);

            JOptionPane.showMessageDialog(mainView, "Universidad registrada: " + varName, "¡Éxito!",
                    JOptionPane.INFORMATION_MESSAGE);

            // Actualizar la interfaz de usuario
            mainView.setUniversityName(varName);

            mainView.varUpdateUniversityButton.setEnabled(true);
            mainView.varRegisterSchoolButton.setEnabled(true);
            mainView.varRegisterUniversityButton.setEnabled(false);

            // Inicializar los campos para actualizar universidad
            mainView.varNewAddressField.setText(varAddress);
            mainView.varNewPhoneField.setText(varPhone);

            // Mostrar panel de escuelas después de registrar universidad
            mainView.showPanel("SCHOOLS");

            // Limpiar campos de registro
            clearFields();
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Error!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateUniversityActionListener() {
        mainView.varUpdateUniversityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUniversity();
            }
        });
    }

    private void updateUniversity() {
        if (varRegisteredUniversity != null) {
            String pNewAddress = mainView.varNewAddressField.getText().trim();
            String pNewPhone = mainView.varNewPhoneField.getText().trim();

            if (!pNewAddress.isEmpty() && !pNewPhone.isEmpty()) {
                varRegisteredUniversity.updateData(pNewAddress, pNewPhone);

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

    private void clearFields() {
        mainView.varNameField.setText("");
        mainView.varAddressField.setText("");
        mainView.varPhoneField.setText("");
    }

    public University getUniversity() {
        return varRegisteredUniversity;
    }
}