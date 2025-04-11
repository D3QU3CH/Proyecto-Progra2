package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.Escuela;
import com.mvc.models.University;
import com.mvc.view.ViewEscuelas;

public class SchoolController {
    private ViewEscuelas vEscuelas;
    private University currentUniversity;
    
    public SchoolController() {
    }
    
    public void abrirVistaEscuelas(University universidad) {
        this.currentUniversity = universidad;
        
        if (vEscuelas == null) {
            vEscuelas = new ViewEscuelas(universidad.getName());
            agregarControladorEscuela();
        }
        
        vEscuelas.setVisible(true);
        
        agregarEscuelasTxtArea(universidad);
    }
    
    private void agregarControladorEscuela() {
        vEscuelas.btnRegisterSchool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEscuela();
            }
        });
    }
    
    private void agregarEscuela() {
        if (currentUniversity != null) {
            String nombreEscuela = vEscuelas.txtNameSchool.getText().trim();

            if (!nombreEscuela.isEmpty()) {
                Escuela nuevaEscuela = new Escuela(nombreEscuela);
                currentUniversity.agregarEscuela(nuevaEscuela);

                agregarEscuelasTxtArea(currentUniversity);

                vEscuelas.txtNameSchool.setText("");
            } else {
                JOptionPane.showMessageDialog(vEscuelas, "El nombre de la escuela es obligatorio", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void agregarEscuelasTxtArea(University universidad) {
        if (universidad != null && universidad.getEscuelas() != null) {
            StringBuilder lista = new StringBuilder();
            int contador = 0;
            for (Escuela esc : universidad.getEscuelas()) {
                contador++;
                lista.append(contador + ":").append(esc.getVarName()).append("\n");
            }
            vEscuelas.txtAreaEscuelas.setText(lista.toString());
        }
    }
    
    public ViewEscuelas getViewEscuelas() {
        return vEscuelas;
    }
}