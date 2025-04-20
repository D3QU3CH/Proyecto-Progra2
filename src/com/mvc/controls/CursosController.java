package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.mvc.models.Cursos;
import com.mvc.view.MainView;

public class CursosController {

    private MainView varMainView;
    private Cursos varCourseToRegister;

    public CursosController(MainView pMainView) {
        this.varMainView = pMainView;
        addCourseActionListener();
        deleteCourseActionListener();
        setupTableSelectionListener(); // New method to configure selection listener
        modifyCourseActionListener(); // Modify course
        searchBySchoolActionListener(); // Search by school
    }

    // ADD COURSE
    private void addCourseActionListener() {
        varMainView.varRegisterCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });
    }

    public void addCourse() {
        String varCourseCode = varMainView.varCourseCodeField.getText().trim();
        String varCourseDescription = varMainView.varCourseDescriptionField.getText().trim();
        String varSchoolName = varMainView.varSchoolNameFieldCursos.getText().trim();

        if (!varCourseCode.isEmpty() && !varCourseDescription.isEmpty() && !varSchoolName.isEmpty()) {
            String content = varMainView.varSchoolsTextArea.getText().trim();
            String[] lines = content.split("\n");
            boolean schoolFound = false;
            for (String line : lines) {
                String schoolInLine = line.replaceAll("^[0-9]+:", "").trim();
                if (schoolInLine.equals(varSchoolName)) {
                    schoolFound = true;
                    break;
                }
            }

            if (!schoolFound) {
                JOptionPane.showMessageDialog(varMainView, "¡No se encontró la Escuela!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DefaultTableModel tableModel = (DefaultTableModel) varMainView.varCoursesTable.getModel();
            boolean codeExists = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String registeredCode = (String) tableModel.getValueAt(i, 1); // Column of codes
                if (registeredCode.equalsIgnoreCase(varCourseCode)) {
                    codeExists = true;
                    break;
                }
            }

            if (codeExists) {
                JOptionPane.showMessageDialog(varMainView, "¡Las siglas del curso ya están registradas!", "¡Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            varCourseToRegister = new Cursos(varCourseCode, varCourseDescription, varSchoolName);
            Object[][] data = {{varSchoolName, varCourseCode, varCourseDescription}};
            addDataToTable(data);
            varMainView.enableCourseControls(true);
            JOptionPane.showMessageDialog(varMainView, "¡Curso registrado exitosamente!", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
            clearCoursePanel();
        } else {
            JOptionPane.showMessageDialog(varMainView, "¡Todos los campos son obligatorios!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addDataToTable(Object[][] pData) {
        DefaultTableModel tableModel = (DefaultTableModel) varMainView.varCoursesTable.getModel();
        for (Object[] row : pData) {
            tableModel.addRow(row);
        }
    }

    // DELETE COURSE
    private void deleteCourseActionListener() {
        varMainView.varDeleteCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCourse();
            }
        });
    }

    public void deleteCourse() {
        String varCourseCode = varMainView.varCourseCodeField.getText().trim();
        DefaultTableModel tableModel = (DefaultTableModel) varMainView.varCoursesTable.getModel();
        boolean selected = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object cellValue = tableModel.getValueAt(i, 1);
            if (cellValue != null && cellValue.toString().equalsIgnoreCase(varCourseCode)) {
                tableModel.removeRow(i);
                JOptionPane.showMessageDialog(varMainView, "¡Se eliminó el curso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                selected = true;
                break;
            }
        }
        if (!selected) {
            JOptionPane.showMessageDialog(varMainView, "¡No se seleccionó el curso!", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        clearCoursePanel();
    }

    // SELECT COURSE
    private void setupTableSelectionListener() {
        varMainView.varCoursesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    fillFormFromTable();
                }
            }
        });
    }

    private void fillFormFromTable() {
        int row = varMainView.varCoursesTable.getSelectedRow();
        if (row != -1) {
            String school = varMainView.varCoursesTable.getValueAt(row, 0).toString();
            String code = varMainView.varCoursesTable.getValueAt(row, 1).toString();
            String description = varMainView.varCoursesTable.getValueAt(row, 2).toString();
            varMainView.varSchoolNameField2.setText(school);
            varMainView.varCourseCodeField.setText(code);
            varMainView.varCourseDescriptionField.setText(description);
            varMainView.varRegisterCourseButton.setEnabled(false);
            varMainView.varSchoolNameField2.setEnabled(false);
            varMainView.varCourseCodeField.setEnabled(false);
            varMainView.varModifyCourseButton.setEnabled(true);
        }
    }

    // MODIFY COURSE
    private void modifyCourseActionListener() {
        varMainView.varModifyCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyCourse();
            }
        });
    }

    public void modifyCourse() {
        int row = varMainView.varCoursesTable.getSelectedRow();
        if (row != -1) {
            String varCourseCode = varMainView.varCourseCodeField.getText().trim();
            String varCourseDescription = varMainView.varCourseDescriptionField.getText().trim();
            String varSchoolName = varMainView.varSchoolNameField2.getText().trim();
            if (!varCourseCode.isEmpty() && !varCourseDescription.isEmpty() && !varSchoolName.isEmpty()) {
                varMainView.varCoursesTable.setValueAt(varSchoolName, row, 0);
                varMainView.varCoursesTable.setValueAt(varCourseCode, row, 1);
                varMainView.varCoursesTable.setValueAt(varCourseDescription, row, 2);
                JOptionPane.showMessageDialog(varMainView, "¡Curso actualizado exitosamente!", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                clearCoursePanel();
            } else {
                JOptionPane.showMessageDialog(varMainView, "¡Todos los campos son obligatorios", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(varMainView, "¡Seleccione un curso para modificar", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }

    // SEARCH BY SCHOOL
    private void searchBySchoolActionListener() {
        varMainView.varSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBySchool();
            }
        });
    }

    public void searchBySchool() {
        String varSchoolName = varMainView.varSearchField.getText().trim();
        varMainView.varSearchTextArea.setText("");
        DefaultTableModel tableModel = (DefaultTableModel) varMainView.varCoursesTable.getModel();
        boolean schoolFound = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String schoolToCheck = (String) tableModel.getValueAt(i, 0);
            if (varSchoolName.equals(schoolToCheck)) {
                schoolFound = true;
                String school = (String) tableModel.getValueAt(i, 0); // Column 0: School Name
                String courseCode = (String) tableModel.getValueAt(i, 1); // Column 1: Course Code
                String courseDescription = (String) tableModel.getValueAt(i, 2); // Column 2: Course Description
                String rowText = "Escuela: " + school + " - Siglas: " + courseCode + " - Descripción: " + courseDescription;
                varMainView.varSearchTextArea.append(rowText + "\n");
            }
        }
        if (!schoolFound) {
            varMainView.varSearchField.setText("");
            varMainView.varSearchTextArea.setText("");
            JOptionPane.showMessageDialog(varMainView, "¡No se encontró la escuela", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearCoursePanel() {
        varMainView.varCourseCodeField.setText("");
        varMainView.varCourseDescriptionField.setText("");
        varMainView.varSchoolNameFieldCursos.setText("");
        varMainView.varCoursesTable.clearSelection();
        varMainView.varRegisterCourseButton.setEnabled(true);
        varMainView.varSchoolNameField2.setEnabled(true);
        varMainView.varCourseCodeField.setEnabled(true);
        varMainView.varModifyCourseButton.setEnabled(false);
    }
}