package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.University;
import com.mvc.view.ViewMenu;

public class MenuController {
    private ViewMenu vMenu;
    private UniversityController universityController;
    private SchoolController schoolController;
    private CourseController courseController;
    //private University currentUniversity;
    
    public MenuController(ViewMenu pMenu) {
        this.vMenu = pMenu;
        
        this.universityController = new UniversityController();
        this.schoolController = new SchoolController();
        this.courseController = new CourseController();
        
        configurarMenuListeners();
    }
    
    private void configurarMenuListeners() {
        vMenu.btnUniversidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                universityController.abrirVistaUniversidad();
            }
        });
        
        vMenu.btnEscuela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (universityController.getUniversidadRegistrada() == null) {
                    JOptionPane.showMessageDialog(vMenu, 
                        "Primero debe registrar una universidad", 
                        "Advertencia", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                schoolController.abrirVistaEscuelas(universityController.getUniversidadRegistrada());
            }
        });
        
        vMenu.btnCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                University universidad = universityController.getUniversidadRegistrada();
                if (universidad == null || 
                    universidad.getEscuelas() == null || 
                    universidad.getEscuelas().isEmpty()) {
                    
                    JOptionPane.showMessageDialog(vMenu, 
                        "Primero debe registrar una universidad y al menos una escuela", 
                        "Advertencia", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                courseController.abrirVistaCursos(schoolController.getViewEscuelas());
            }
        });
    }
    
    public void setUniversityController(UniversityController controller) {
        this.universityController = controller;
    }
    
    public void setSchoolController(SchoolController controller) {
        this.schoolController = controller;
    }
    
    public void setCourseController(CourseController controller) {
        this.courseController = controller;
    }
}