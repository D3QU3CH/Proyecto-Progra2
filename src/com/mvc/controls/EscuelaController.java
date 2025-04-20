package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.Escuela;
import com.mvc.models.University;
import com.mvc.view.MainView;

public class EscuelaController {

    private MainView mainView;
    private UniversidadController universityController;

    public EscuelaController(MainView pMainView, UniversidadController pUniversityController) {
        this.mainView = pMainView;
        this.universityController = pUniversityController;
        setupSchoolsPanel();
    }

    public void setupSchoolsPanel() {
        mainView.varRegisterSchoolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSchool();
            }
        });
    }

    public void addSchool() {
        University university = universityController.getUniversity();
        if (university != null) {
            String schoolName = mainView.varSchoolNameField2.getText().trim();

            if (!schoolName.isEmpty()) {
                Escuela newSchool = new Escuela(schoolName);
                university.addSchool(newSchool);

                mainView.enableCourseControls(true); // Habilita los botones

                addSchoolsToTextArea();

                mainView.varSchoolNameField2.setText("");
            } else {
                JOptionPane.showMessageDialog(mainView, "¡El nombre de la escuela es obligatorio!", "¡Error!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Primero se debe registrar una universidad!", "¡Error!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addSchoolsToTextArea() {
        University university = universityController.getUniversity();
        if (university != null && university.getEscuelas() != null) {
            StringBuilder list = new StringBuilder();
            int counter = 0;
            for (Escuela school : university.getEscuelas()) {
                counter++;
                list.append(counter).append(":").append(school.getVarName()).append("\n");
            }
            mainView.varSchoolsTextArea.setText(list.toString());
        }
    }
}