package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.School;
import com.mvc.models.University;
import com.mvc.view.MainView;

public class SchoolController { 
    
    private MainView mainView;
    private UniversityController universidadController;
    
    public SchoolController(MainView mainView, UniversityController universidadController) {
        this.mainView = mainView;
        this.universidadController = universidadController;
        setupEscuelasPanel();
    }
    
    public void setupEscuelasPanel() {
        mainView.btnRegisterSchool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEscuela();
            }
        });
    }
    
    private void agregarEscuela() {
        University universidad = universidadController.getUniversidad();
        if (universidad != null) {
            String nombreEscuela = mainView.txtNameSchool.getText().trim();

            if (!nombreEscuela.isEmpty()) {
                // Verificar si la escuela ya existe
                boolean escuelaExiste = false;
                for (School esc : universidad.getEscuelas()) {
                    if (esc.getVarName().equalsIgnoreCase(nombreEscuela)) { // Usar equals para comparar los nombres
                        escuelaExiste = true;
                        break; // Si se encuentra, no es necesario seguir buscando
                    }
                }

                if (escuelaExiste) {
                    JOptionPane.showMessageDialog(mainView, "¡La escuela ya existe!", "¡Error!", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Si no existe, agregar la nueva escuela
                    School nuevaEscuela = new School(nombreEscuela);
                    universidad.agregarEscuela(nuevaEscuela);
                    mainView.varBtnRegistrar.setEnabled(true);
                    
                    agregarEscuelasTxtArea();
                    mainView.txtNameSchool.setText(""); 
                }

            } else {
                JOptionPane.showMessageDialog(mainView, "¡El nombre de la escuela es obligatorio!", "¡Error!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Primero se debe registrar una universidad!", "¡Error!", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    private void agregarEscuelasTxtArea() {
        University universidad = universidadController.getUniversidad();
        if (universidad != null && universidad.getEscuelas() != null) {
            StringBuilder lista = new StringBuilder();
            for (School esc : universidad.getEscuelas()) {
                lista.append(" - ").append(esc.getVarName()).append("\n");
            }
            mainView.txtAreaEscuelas.setText(lista.toString());
        }
    }
}