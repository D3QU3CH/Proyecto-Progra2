package com.mvc.controls;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.google.gson.Gson;
import com.mvc.models.School;
import com.mvc.models.University;
import com.mvc.view.MainView;
import java.io.File;
import java.io.FileReader;

public class UniversityController {
    
    private MainView mainView;
    private University varUniversidadRegistrada;
    private SchoolController schoolController;
    public UniversityController(MainView mainView) {
        this.mainView = mainView;
        registerUniversityActionListener();
        uptadeUniversityActionListener();
        cargarUniversidadSiExiste();
      
        
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

            //Inicializar los campos para actualizar universidad
            mainView.txtNewAdress.setText(varDireccion);
            mainView.txtNewPhone.setText(varTelefono);
            
            mainView.registerPanel.removeAll();
            TitledBorder border = (TitledBorder) mainView.registerPanel.getBorder();
            border.setTitle("");
            
            JLabel tituloUniversidad = new JLabel("Universidad: "+varUniversidadRegistrada.getName(), SwingConstants.CENTER);
            tituloUniversidad.setFont(new Font("Arial", Font.ITALIC, 40)); // Tamaño grande
            mainView.registerPanel.setLayout(new BorderLayout()); // Para centrar fácilmente
            mainView.registerPanel.add(tituloUniversidad, BorderLayout.CENTER);
            
            mainView.registerPanel.repaint();
            escribirDataEnJson();          
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
                // Guarda el objeto actualizado
                escribirDataEnJson();

                // Actualiza otros campos de la interfaz si es necesario
                mainView.setUniversityName(varUniversidadRegistrada.getName());
                mainView.txtNewAdress.setText(nuevaDireccion);
                mainView.txtNewPhone.setText(nuevoTelefono);

                // Muestra mensaje de éxito
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
    //metodos para lo json
    
    public void escribirDataEnJson() {
        Gson gson = new Gson();
        String nombreDelArchivoJson = "Universidad.json";

        if (varUniversidadRegistrada == null) {
            JOptionPane.showMessageDialog(mainView, "No hay una universidad registrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter(nombreDelArchivoJson)) {
            gson.toJson(varUniversidadRegistrada, writer);
            writer.flush();
            System.out.println("Objeto guardado en: " + nombreDelArchivoJson);
            JOptionPane.showMessageDialog(mainView, "¡Universidad guardada correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainView, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static University cargarUniversidadDesdeJson() {
    	
    	Gson gson = new Gson();
    	File varArchivoJson = new File("Universidad.json");
    	
    	if(varArchivoJson.exists()&&varArchivoJson.length()>0) {
    		try(FileReader reader = new FileReader(varArchivoJson)){
    			
    			return gson.fromJson(reader, University.class);
    			
    			
    			
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	
    	return null; // si el arcvhivo no existe o si esta vacio 
    }
    public void cargarUniversidadSiExiste() {
        varUniversidadRegistrada = cargarUniversidadDesdeJson();

        if (varUniversidadRegistrada != null) {
            // Simula lo que haría registerUniversity
            String varNombre = varUniversidadRegistrada.getName();
            String varDireccion = varUniversidadRegistrada.getAdress();
            String varTelefono = varUniversidadRegistrada.getPhoneNumber();

            JOptionPane.showMessageDialog(mainView, "Universidad cargada: " + varNombre, "Datos encontrados",
                    JOptionPane.INFORMATION_MESSAGE);

            mainView.setUniversityName(varNombre);
            mainView.btnUpdateUniversity.setEnabled(true);
            mainView.btnRegisterSchool.setEnabled(true);
            mainView.txtNewAdress.setText(varDireccion);
            mainView.txtNewPhone.setText(varTelefono);
            
            mainView.registerPanel.removeAll();
            TitledBorder border = (TitledBorder) mainView.registerPanel.getBorder();
            border.setTitle("");
            
            JLabel tituloUniversidad = new JLabel("Universidad: " + varNombre, SwingConstants.CENTER);
            tituloUniversidad.setFont(new Font("Arial", Font.ITALIC, 40));
            mainView.registerPanel.setLayout(new BorderLayout());
            mainView.registerPanel.add(tituloUniversidad, BorderLayout.CENTER);
            
            mainView.registerPanel.repaint();
            
         
         // Mostrar las escuelas en txtAreaEscuelas y habilitar botones si hay escuelas
            if (varUniversidadRegistrada.getEscuelas() != null && !varUniversidadRegistrada.getEscuelas().isEmpty()) {
                StringBuilder lista = new StringBuilder();
                for (School school : varUniversidadRegistrada.getEscuelas()) {
                    lista.append(" - ").append(school.getVarName()).append("\n");
                }
                mainView.txtAreaEscuelas.setText(lista.toString());

                // 👇 Habilitar botones u otros componentes dependientes de las escuelas
                mainView.btnRegisterSchool.setEnabled(true); // O btnNextStep, etc.
                mainView.varBtnRegistrar.setEnabled(true);
                
            } else {
                mainView.txtAreaEscuelas.setText(""); // Limpiar si no hay escuelas
                
                // 👇 Deshabilitar botones si no hay escuelas
                mainView.btnRegisterSchool.setEnabled(false);
                mainView.varBtnRegistrar.setEnabled(false);
            }
        }
    }
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    