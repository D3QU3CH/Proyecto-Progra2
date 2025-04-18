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
    
    private MainView mainView;
    private Cursos varCursosRegistrar;
    
    public CursosController(MainView mainView) {
        this.mainView = mainView;
        agregarCursosActionListener();
        eliminarCursoActionListener();
        setupTableSelectionListener(); // Nuevo método para configurar el listener de selección
        modificarCursoActionListener();//MODIFICAR CURSO
        ActionListenerBusquedaPorEscuela();//Para la busqueda por esceula
       
    }
    
    //AGREGAR CURSO
    private void agregarCursosActionListener() {
        // Añadir el ActionListener al botón de registrar curso
        mainView.varBtnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCursos();
            }
        });
    }
    
    public void agregarCursos() {
        // Obtener los datos ingresados por el usuario
        String varSiglasCurso = mainView.varTxtSigla.getText().trim();
        String varDescipcionDeCursos = mainView.varTxtDescripcion.getText().trim();
        String varNombreEscuelas = mainView.varTxtEscuelaNombres.getText().trim();
        // Validar que todos los campos estén llenos
        if (!varSiglasCurso.isEmpty() && !varDescipcionDeCursos.isEmpty() && !varNombreEscuelas.isEmpty()) {
            // Verificar si la escuela existe en el área de texto de escuelas
            String contenido = mainView.txtAreaEscuelas.getText().toLowerCase().trim();
            String[] lineas = contenido.split("\n");

            boolean escuelaEncontrada = false;
            for (String linea : lineas) {
                String nombreEscuelaEnLinea = linea.replaceAll("^[0-9]+:", "").trim();

                if (nombreEscuelaEnLinea.equalsIgnoreCase(varNombreEscuelas)) {
                    escuelaEncontrada = true;
                    break;
                }
            }

            // Si la escuela no existe, mostrar advertencia
            if (!escuelaEncontrada) {
                JOptionPane.showMessageDialog(mainView, "¡No se encontró la Escuela!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar si las siglas del curso ya existen en la tabla
            DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();
            boolean siglasExisten = false;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String siglasRegistradas = (String) modeloTabla.getValueAt(i, 1); // Columna de siglas

                if (siglasRegistradas.equalsIgnoreCase(varSiglasCurso)) {
                    siglasExisten = true;
                    break;
                }
            }

            // Si las siglas ya existen, mostrar advertencia
            if (siglasExisten) {
                JOptionPane.showMessageDialog(mainView, "¡Las siglas del curso ya están registradas!", "¡Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si pasa todas las validaciones, registrar el curso
            varCursosRegistrar = new Cursos(varSiglasCurso, varDescipcionDeCursos, varNombreEscuelas);
            Object[][] datos = { { varNombreEscuelas, varSiglasCurso, varDescipcionDeCursos } };
            agregarDatosTabla(datos);
            
            mainView.enableCursosControls(true);

            JOptionPane.showMessageDialog(mainView, "¡Curso registrado exitosamente!", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
            limpiarPanelCurso();
        } else {
            // Si algún campo está vacío, mostrar advertencia
            JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void agregarDatosTabla(Object[][] datos) {
        DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();
        
        // Agregar los nuevos datos
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }
    //ELIMINAR CURSO
    private void eliminarCursoActionListener() {
        mainView.varBtnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCurso();
            }
        });
    }
    
    public void eliminarCurso() {
        String varSiglasCurso = mainView.varTxtSigla.getText().trim();
        DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();
        boolean seleccionado = false;
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object valorCelda = modeloTabla.getValueAt(i, 1);
            if (valorCelda != null && valorCelda.toString().equalsIgnoreCase(varSiglasCurso)) {
                modeloTabla.removeRow(i);
                JOptionPane.showMessageDialog(mainView, "¡Se eliminó el curso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                seleccionado = true;
                break;
            }
        }
        
        if (!seleccionado) {
            JOptionPane.showMessageDialog(mainView, "¡No se seleccionó el curso!", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
        limpiarPanelCurso(); // Limpiar los campos después de eliminar
    }
    
    
    //SELECCIONAR CURSO
    private void setupTableSelectionListener() {
        mainView.tablaCursos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    llenarFormularioDesdeTabla();
                }
            }
        });
        
    }
    
    //Metodo para llenar los campos de texto con los datos de la fila seleccionada
    private void llenarFormularioDesdeTabla() {
        int fila = mainView.tablaCursos.getSelectedRow();
        if (fila != -1) {
            // Obtener los valores de la fila seleccionada
            String escuela = mainView.tablaCursos.getValueAt(fila, 0).toString();
            String siglas = mainView.tablaCursos.getValueAt(fila, 1).toString();
            String descripcion = mainView.tablaCursos.getValueAt(fila, 2).toString();
            
            // Llenar los campos de texto
            mainView.varTxtEscuelaNombres.setText(escuela);
            mainView.varTxtSigla.setText(siglas);
            mainView.varTxtDescripcion.setText(descripcion);
            
            // Actualizar estado de los botones (opcional)
            mainView.varBtnRegistrar.setEnabled(false);
            mainView.varTxtEscuelaNombres.setEnabled(false);
            mainView.varTxtSigla .setEnabled(false);
            
            mainView.varBtnModificar.setEnabled(true);
        }
    }
    
    //MODIFICAR CURSO
    private void modificarCursoActionListener() {
        mainView.varBtnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarCurso();
            }
        });
    }

    public void modificarCurso() {
        int fila = mainView.tablaCursos.getSelectedRow();
        
        if (fila != -1) {
            String varSiglasCurso = mainView.varTxtSigla.getText().trim();
            String varDescipcionDeCursos = mainView.varTxtDescripcion.getText().trim();
            String varNombreEscuelas = mainView.varTxtEscuelaNombres.getText().trim();

            if (!varSiglasCurso.isEmpty() && !varDescipcionDeCursos.isEmpty() && !varNombreEscuelas.isEmpty()) {
                // Actualizar datos en la tabla
                mainView.tablaCursos.setValueAt(varNombreEscuelas, fila, 0);
                mainView.tablaCursos.setValueAt(varSiglasCurso, fila, 1);
                mainView.tablaCursos.setValueAt(varDescipcionDeCursos, fila, 2);

                JOptionPane.showMessageDialog(mainView, "¡Curso actualizado exitosamente!", "¡Éxito!",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarPanelCurso();
            } else {
                JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios", "¡Advertencia!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainView, "¡Seleccione un curso para modificar", "¡Advertencia!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
   
    private void ActionListenerBusquedaPorEscuela() {
    	mainView.btnBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buscarPorEscuela();
				
			}
    		
    	});
    }
  
    public void buscarPorEscuela() {
    	 String varNombreEscuela=mainView.txtBuscar.getText().trim();
    	 mainView.showTextArea.setText("");
    	 DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();
    	 boolean coincidenciaEncontrada = false;
    	 for(int i =0;i<modeloTabla.getRowCount();i++) {
    		 String varNombreEscuelaEvaluar = (String) modeloTabla.getValueAt(i, 0);
    		 
    		 if(varNombreEscuela.equalsIgnoreCase(varNombreEscuelaEvaluar)) {
    			 coincidenciaEncontrada= true;
    			 String nombreEscuela = (String) modeloTabla.getValueAt(i, 0); // Columna 0: Nombre de la Escuela
    	         String siglasCurso = (String) modeloTabla.getValueAt(i, 1);   // Columna 1: Siglas del Curso
    	         String descripcionCurso = (String) modeloTabla.getValueAt(i, 2);
    	         String filaTexto = "Escuela: " + nombreEscuela + ", Siglas: " + siglasCurso + ", Descripción: " + descripcionCurso;

    	            // Agregar la fila al JTextArea
    	         mainView.showTextArea.append(filaTexto + "\n");
    		 }
    	 }
    	 if(!coincidenciaEncontrada) {
    		 mainView.txtBuscar.setText("");
    		 mainView.showTextArea.setText("");
    		 JOptionPane.showMessageDialog(mainView, "¡No se encontraron considencias", "¡Advertencia!",
                     JOptionPane.WARNING_MESSAGE);
    	 }
    }
    
    void limpiarPanelCurso() {
        mainView.varTxtSigla.setText("");
        mainView.varTxtDescripcion.setText("");
        mainView.varTxtEscuelaNombres.setText("");
        mainView.tablaCursos.clearSelection(); // Limpiar selección de la tabla
        
        // Restablecer estado de los botones
        mainView.varBtnRegistrar.setEnabled(true);
        mainView.varTxtEscuelaNombres.setEnabled(true);
        mainView.varTxtSigla .setEnabled(true);
        
        mainView.varBtnModificar.setEnabled(false);

    }
    
}