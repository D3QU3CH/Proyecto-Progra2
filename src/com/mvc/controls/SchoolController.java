package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
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
                mainView.varBtnRegistrar.setEnabled(true);
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

                    // Actualizar interfaz
                    agregarEscuelasTxtArea();
                    mainView.txtNameSchool.setText("");

                    // Guardar cambios en Universidad.json
                    universidadController.escribirDataEnJson(); // <- Este método ya está listo

                    // Opcional: Guardar también en Escuelas.json
                    escribirDataEnJsonEscuelas();
                }

            } else {
                JOptionPane.showMessageDialog(mainView, "¡El nombre de la escuela es obligatorio!", "¡Error!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Primero se debe registrar una universidad!", "¡Error!", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    public void agregarEscuelasTxtArea() {
        University universidad = universidadController.getUniversidad();
        if (universidad != null && universidad.getEscuelas() != null) {
            StringBuilder lista = new StringBuilder();
            for (School esc : universidad.getEscuelas()) {
                lista.append(" - ").append(esc.getVarName()).append("\n");
            }
            mainView.txtAreaEscuelas.setText(lista.toString());
        }
    }
    
    //TOLO LOS METODOS DEL JSON
    public void escribirDataEnJsonEscuelas() {
    	
    	Gson gson = new Gson();
    	String nombreDelArchivoJson = "Escuelas.json";
    	
    	 University universidad = universidadController.getUniversidad();
    	  
    	if(universidad !=null || universidad.getEscuelas().isEmpty()) {
    		// JOptionPane.showMessageDialog(mainView, "No hay escuelas para guardar.", "Error", JOptionPane.WARNING_MESSAGE);
    		System.out.println("NO HAY ESCUELAS POR CARGAR");
    	}
    	
    	try(FileWriter writer = new FileWriter(nombreDelArchivoJson)){
    		gson.toJson(universidad.getEscuelas(),writer);
    		writer.flush();
    		System.out.println("Escuelas guardadas en: " + nombreDelArchivoJson);
    		
    		
    	}catch(IOException e) {
    		  e.printStackTrace();
    	        JOptionPane.showMessageDialog(mainView, "Error al guardar las escuelas.", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	
    	
    	
    }
    
  
}