package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mvc.view.MainView;
import com.mvc.view.StudentView;

public class Controller {

	private MainView mainView;
	private StudentView studentView;
	private MenuController menuController;
	private UniversityController universidadController;
	private SchoolController escuelaController;
	private CoursesController cursosController;
	private TeacherController teacherController;
	private ConsultasController consultasController;
	private StudentController studentController;

	public Controller(MainView mainView, StudentView studentView) {
		this.mainView = mainView;
		this.studentView = studentView;
		
		// Inicializar los controladores espec�ficos

		studentController = new StudentController(studentView, mainView);
		menuController = new MenuController(mainView, studentView, studentController);
		universidadController = new UniversityController(mainView);
		escuelaController = new SchoolController(mainView, universidadController);
		teacherController = new TeacherController(mainView, universidadController,studentView );
		cursosController = new CoursesController(mainView, studentView ,universidadController, teacherController, studentController);
		
		
		
		consultasController = new ConsultasController(mainView, universidadController);
		
		mainView.btnEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	int confirmacion = JOptionPane.showConfirmDialog(studentView.matriculaPanel,
        				"¿Está seguro que desea acceder a la ventana de Gestion de ESTUDIANTES?", "Confirmar", JOptionPane.YES_NO_OPTION);

        		if (confirmacion == JOptionPane.YES_OPTION) {
                	mainView.setVisible(false);
                	studentView.setVisible(true);
                	studentController.cargarEstudiantesDesdeJson();
        		}
            	
            }
        });
		
		studentView.btnRegresarGestionUniversidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int confirmacion = JOptionPane.showConfirmDialog(studentView.matriculaPanel,
        				"¿Está seguro que desea regresar a la ventana de Gestion de UNIVERSIDAD?", "Confirmar", JOptionPane.YES_NO_OPTION);

        		if (confirmacion == JOptionPane.YES_OPTION) {
        			mainView.setVisible(true);
                	studentView.setVisible(false);
        		}
            	
            }
        });
        

		// Mostrar la vista
		mainView.setVisible(true);
		studentView.setVisible(false);
	}
}