package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.Escuela;
import com.mvc.models.University;
import com.mvc.view.MainView;

public class EscuelaController { 
    
    private MainView mainView;
    private UniversidadController universidadController;
    
    public EscuelaController(MainView mainView, UniversidadController universidadController) {
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
                Escuela nuevaEscuela = new Escuela(nombreEscuela);
                universidad.agregarEscuela(nuevaEscuela);
                
                mainView.varBtnRegistrar.setEnabled(true);
                
                agregarEscuelasTxtArea();

                mainView.txtNameSchool.setText("");
            } else {
                JOptionPane.showMessageDialog(mainView, "�El nombre de la escuela es obligatorio!", "�Error!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "�Primero se debe registrar una universidad!", "�Error!",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void agregarEscuelasTxtArea() {
        University universidad = universidadController.getUniversidad();
        if (universidad != null && universidad.getEscuelas() != null) {
            StringBuilder lista = new StringBuilder();
            int contador = 0;
            for (Escuela esc : universidad.getEscuelas()) {
                contador++;
                lista.append(contador + ":").append(esc.getVarName()).append("\n");
            }
            mainView.txtAreaEscuelas.setText(lista.toString());
        }
    }
}