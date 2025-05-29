package com.mvc.controls;

import com.mvc.models.School;
import com.mvc.models.University;
import com.mvc.view.MainView;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TeacherController {

	private MainView mainView;
	private UniversityController universityController;

	public TeacherController(MainView mainView, UniversityController universityController) {
		this.mainView = mainView;
		this.universityController = universityController;
		modificarProfesorActionListener();
		agregarProfesorActionListener();
		eliminarProfesorActionListener();
		setupTableSelectionListener();
		setupBtnDeseleccionarTablaActionListener();
		asignarProfesorActionListener();
		desasignarProfesorActionListener();
		asignarDirectorActionListener();
		deseleccionarTablaAsigActionListener();
		setupTableAsigSelectionListener();

	}

	// agregar Profesor action Listener
	private void agregarProfesorActionListener() {
		mainView.btnAgregarProfesor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarProfesor();
			}
		});
	}

	// metodo de agregar y asignar profesor
	public void agregarProfesor() {
		String nombre = mainView.txtNombreProfesor.getText().trim();
		String apellido1 = mainView.txtPrimerApe.getText().trim();
		String apellido2 = mainView.txtSegundoApe.getText().trim();
		String cedula = mainView.txtCedula.getText().trim();

		// Validar campos vac�os
		if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() || cedula.isEmpty()) {
			JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Validar que la c�dula contenga solo n�meros
		if (!cedula.matches("\\d+")) {
			JOptionPane.showMessageDialog(mainView, "¡La c�dula debe contener solo n�meros!", "¡Error!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Verificar si ya existe un profesor con esa c�dula
		DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();

		for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
			String cedulaRegistrada = (String) modeloProfesores.getValueAt(i, 3);
			if (cedula.equalsIgnoreCase(cedulaRegistrada)) {
				JOptionPane.showMessageDialog(mainView, "¡Ya existe un profesor con esa c�dula!", "¡Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Registrar profesor
		Object[] fila = { nombre, apellido1, apellido2, cedula };
		modeloProfesores.addRow(fila);

		JOptionPane.showMessageDialog(mainView, "¡Profesor agregado exitosamente!", "¡Éxito!",
				JOptionPane.INFORMATION_MESSAGE);
		limpiarCamposProfesor();
		mostrarProfesoresEnTextArea();
	}

	// modificar profesor action Listener
	private void modificarProfesorActionListener() {
		mainView.btnModificarProfesor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificarProfesor();
			}
		});
	}

	// metodo de modificar profesor (excepto c�dula)
	public void modificarProfesor() {
		int filaSeleccionada = mainView.tablaProfesores.getSelectedRow();

		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(mainView, "Seleccione un profesor de la tabla para modificar",
					"�Advertencia!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String nuevoNombre = mainView.txtNombreProfesor.getText().trim();
		String nuevoApellido1 = mainView.txtPrimerApe.getText().trim();
		String nuevoApellido2 = mainView.txtSegundoApe.getText().trim();

		if (nuevoNombre.isEmpty() || nuevoApellido1.isEmpty() || nuevoApellido2.isEmpty()) {
			JOptionPane.showMessageDialog(mainView, "¡Nombre y apellidos no pueden estar vacíos!", "¡Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();
		modelo.setValueAt(nuevoNombre, filaSeleccionada, 0);
		modelo.setValueAt(nuevoApellido1, filaSeleccionada, 1);
		modelo.setValueAt(nuevoApellido2, filaSeleccionada, 2);

		JOptionPane.showMessageDialog(mainView, "¡Profesor modificado exitosamente!", "¡Éxito!",
				JOptionPane.INFORMATION_MESSAGE);
		limpiarPanelProfesor();
		mostrarProfesoresEnTextArea();
	}

	private void eliminarProfesorActionListener() {
		mainView.btnEliminarProfesor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarProfesor();
			}
		});
	}

	public void eliminarProfesor() {
		String varCedulaProf = mainView.txtCedula.getText().trim();
		DefaultTableModel modeloTablaProf = (DefaultTableModel) mainView.tablaProfesores.getModel();
		boolean seleccionado = false;

		for (int i = 0; i < modeloTablaProf.getRowCount(); i++) {
			Object valorCedula = modeloTablaProf.getValueAt(i, 3);
			if (valorCedula != null && valorCedula.toString().equalsIgnoreCase(varCedulaProf)) {
				modeloTablaProf.removeRow(i);
				JOptionPane.showMessageDialog(mainView, "¡Se eliminó el profesor!", "¡Éxito!",
						JOptionPane.INFORMATION_MESSAGE);
				seleccionado = true;
				break;
			}
		}

		if (!seleccionado) {
			JOptionPane.showMessageDialog(mainView, "¡No se seleccionó el profesor!", "¡Advertencia!",
					JOptionPane.WARNING_MESSAGE);
		}

		limpiarPanelProfesor();
		mostrarProfesoresEnTextArea();

	}

	private void setupTableSelectionListener() {
		mainView.tablaProfesores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					llenarFormularioDesdeTabla();
				}
			}
		});

	}

	private void llenarFormularioDesdeTabla() {
		int filaSeleccionada = mainView.tablaProfesores.getSelectedRow();

		if (filaSeleccionada != -1) {
			DefaultTableModel modelo = (DefaultTableModel) mainView.tablaProfesores.getModel();

			// Obtener los valores de la fila seleccionada
			String nombre = (String) modelo.getValueAt(filaSeleccionada, 0);
			String apellido1 = (String) modelo.getValueAt(filaSeleccionada, 1);
			String apellido2 = (String) modelo.getValueAt(filaSeleccionada, 2);
			String cedula = (String) modelo.getValueAt(filaSeleccionada, 3);

			// Cargar los datos en los campos de texto
			mainView.txtNombreProfesor.setText(nombre);
			mainView.txtPrimerApe.setText(apellido1);
			mainView.txtSegundoApe.setText(apellido2);
			mainView.txtCedula.setText(cedula);

			// Actualizar estado de los botones
			mainView.btnAgregarProfesor.setEnabled(false);
			mainView.txtCedula.setEnabled(false);

			mainView.btnModificarProfesor.setEnabled(true);
			mainView.btnDeseleccionarTablaProf.setEnabled(true);
			mainView.btnEliminarProfesor.setEnabled(true);
		}
	}

	// Limpiar campos del formulario
	private void limpiarCamposProfesor() {
		mainView.txtNombreProfesor.setText("");
		mainView.txtPrimerApe.setText("");
		mainView.txtSegundoApe.setText("");
		mainView.txtCedula.setText("");

	}

	private void setupBtnDeseleccionarTablaActionListener() {
		mainView.btnDeseleccionarTablaProf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarPanelProfesor();
			}
		});
	}

	private void limpiarPanelProfesor() {
		mainView.txtNombreProfesor.setText("");
		mainView.txtPrimerApe.setText("");
		mainView.txtSegundoApe.setText("");
		mainView.txtCedula.setText("");

		mainView.tablaProfesores.clearSelection(); // Limpiar selecci�n de la tabla

		// Restablecer estado de los botones
		mainView.btnAgregarProfesor.setEnabled(true);
		mainView.txtCedula.setEnabled(true);

		mainView.btnModificarProfesor.setEnabled(false);
		mainView.btnDeseleccionarTablaProf.setEnabled(false);
		mainView.btnEliminarProfesor.setEnabled(false);

	}

	// logica de asignarle cursos a profesores

	private void setupTableAsigSelectionListener() {
		mainView.tablaAsignaciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					llenarFormularioDesdeTablaAsig();
				}
			}
		});
	}

	private void llenarFormularioDesdeTablaAsig() {
		int filaSeleccionada = mainView.tablaAsignaciones.getSelectedRow();

		if (filaSeleccionada != -1) {
			DefaultTableModel modelo = (DefaultTableModel) mainView.tablaAsignaciones.getModel();

			String cedula = (String) modelo.getValueAt(filaSeleccionada, 1);
			String siglas = (String) modelo.getValueAt(filaSeleccionada, 2);
			String grupo = (String) modelo.getValueAt(filaSeleccionada, 3);

			mainView.txtCedulaAsignar.setText(cedula);
			mainView.txtSiglasAsignar.setText(siglas);
			mainView.txtGrupoAsignar.setText(grupo);

			mainView.btnAsignarProfesorAsig.setEnabled(false);
			mainView.txtCedulaAsignar.setEnabled(false);
			mainView.txtSiglasAsignar.setEnabled(false);
			mainView.txtGrupoAsignar.setEnabled(false);

			mainView.btnDesasignarProfesorAsig.setEnabled(true);
			mainView.btnDeseleccionarTablaAsig.setEnabled(true);
			mainView.btnAsignarDirectorAsig.setEnabled(true);
		}

	}

	private void asignarProfesorActionListener() {
		mainView.btnAsignarProfesorAsig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				asignarProfesor();
			}
		});
	}

	public void asignarProfesor() {
		String cedula = mainView.txtCedulaAsignar.getText().trim();
		String sigla = mainView.txtSiglasAsignar.getText().trim();
		String grupo = mainView.txtGrupoAsignar.getText().trim();
		if (cedula.isEmpty() || sigla.isEmpty() || grupo.isEmpty()) {
			JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		// Validación de existencia del profesor
		boolean profesorExiste = false;
		DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
		for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
			String cedulaProfesor = modeloProfesores.getValueAt(i, 3).toString();
			if (cedula.equals(cedulaProfesor)) {
				profesorExiste = true;
				break;
			}
		}
		if (!profesorExiste) {
			JOptionPane.showMessageDialog(mainView, "¡No existe un profesor con la cédula: " + cedula + "!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Validación y obtención del nombre de la escuela
		boolean cursoExiste = false;
		String nombreEscuela = "";
		String siglasCurso = "";
		DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
		for (int i = 0; i < modeloCursos.getRowCount(); i++) {
			siglasCurso = modeloCursos.getValueAt(i, 1).toString();
			if (sigla.equalsIgnoreCase(siglasCurso)) {
				cursoExiste = true;
				nombreEscuela = modeloCursos.getValueAt(i, 0).toString();
				break;
			}
		}
		if (!cursoExiste) {
			JOptionPane.showMessageDialog(mainView, "¡No existe un curso con las siglas: " + sigla + "!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Validacion de verificar si el profesor ya está asignado a otra escuela
		DefaultTableModel modeloAsignaciones = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
		for (int i = 0; i < modeloAsignaciones.getRowCount(); i++) {
			String cedulaExistente = modeloAsignaciones.getValueAt(i, 1).toString();
			if (cedula.equalsIgnoreCase(cedulaExistente)) {
				String escuelaExistente = modeloAsignaciones.getValueAt(i, 0).toString();
				if (!nombreEscuela.equalsIgnoreCase(escuelaExistente)) {
					JOptionPane.showMessageDialog(mainView, 
						"¡El profesor con cédula " + cedula + " ya está asignado a la escuela: " + escuelaExistente + 
						"!\nUn profesor solo puede pertenecer a una escuela.", "Error de Asignación",
						JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		
		// Validar si ya existe la asignación específica (mismo profesor, curso y grupo)
		for (int i = 0; i < modeloAsignaciones.getRowCount(); i++) {
			String cedulaExistente = modeloAsignaciones.getValueAt(i, 1).toString();
			String siglaExistente = modeloAsignaciones.getValueAt(i, 2).toString();
			String grupoExistente = modeloAsignaciones.getValueAt(i, 3).toString();
			if (cedula.equalsIgnoreCase(cedulaExistente) && sigla.equalsIgnoreCase(siglaExistente)
					&& grupo.equalsIgnoreCase(grupoExistente)) {
				JOptionPane.showMessageDialog(mainView, "¡Esta asignación ya existe!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		modeloAsignaciones.addRow(new Object[] { nombreEscuela, cedula, siglasCurso, grupo });
		JOptionPane.showMessageDialog(mainView, "¡Asignación registrada exitosamente!", "¡Éxito!",
				JOptionPane.INFORMATION_MESSAGE);
		limpiarCamposAsignacion();
	}

	private void desasignarProfesorActionListener() {
		mainView.btnDesasignarProfesorAsig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desasignarProfesor();
			}
		});
	}

	public void desasignarProfesor() {
		int filaSeleccionada = mainView.tablaAsignaciones.getSelectedRow();

		if (filaSeleccionada != -1) {
			DefaultTableModel modelo = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
			modelo.removeRow(filaSeleccionada);
			JOptionPane.showMessageDialog(mainView, "¡Asignación eliminada exitosamente!", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

			// Limpiar y restablecer despu�s de eliminar
			limpiarPanelAsignacion();
		} else {
			JOptionPane.showMessageDialog(mainView, "¡Seleccione una asignación para eliminar!", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void asignarDirectorActionListener() {
		mainView.btnAsignarDirectorAsig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				asignarDirector();
			}
		});
	}

	private void asignarDirector() {
	    
	    int filaSeleccionada = mainView.tablaAsignaciones.getSelectedRow();
	    DefaultTableModel modeloAsignaciones = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
	    DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();

	    String nombreEscuela = (String) modeloAsignaciones.getValueAt(filaSeleccionada, 0);
	    String cedulaProf = (String) modeloAsignaciones.getValueAt(filaSeleccionada, 1);
	    
	    String nombreDirector = "";
	    String apellido1 = "";
	    String apellido2 = "";
	    for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
	        String cedulaRegistrada = (String) modeloProfesores.getValueAt(i, 3); 
	        if (cedulaProf.equalsIgnoreCase(cedulaRegistrada)) {
	        	nombreDirector = (String) modeloProfesores.getValueAt(i, 0); 
	        	apellido1 = (String) modeloProfesores.getValueAt(i, 1);
	        	apellido2 = (String) modeloProfesores.getValueAt(i, 2);
	            break;
	        }
	    }
	    
	    String contenidoActual = mainView.txtAreaDirectores.getText(); 
	    
	    if (contenidoActual.contains("Director de la Escuela " + nombreEscuela)) {
	        JOptionPane.showMessageDialog(mainView, 
	                "¡Ya existe un director para la escuela: " + nombreEscuela + "!", 
	                "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Verificar si ya existe un director con la misma c�dula
	    if (contenidoActual.contains("Cédula: " + cedulaProf)) {
	        JOptionPane.showMessageDialog(mainView, 
	                "¡Ya existe un director registrado con la cédula " + cedulaProf + "!", 
	                "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    // Crear panel personalizado para solicitar el per�odo
	    JPanel panelPeriodo = new JPanel();
	    panelPeriodo.setLayout(new FlowLayout());

	    JLabel labelPeriodo = new JLabel("Período en años:");
	    JTextField txtPeriodo = new JTextField(10);

	    panelPeriodo.add(labelPeriodo);
	    panelPeriodo.add(txtPeriodo);

	    // Mostrar el JOptionPane con el panel personalizado
	    int resultado = JOptionPane.showConfirmDialog(mainView, panelPeriodo, "Asignar Director - Período",
	            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	    
	    // Verificar si el usuario presion� OK (O aceptar)
	    if (resultado == JOptionPane.OK_OPTION) {
	        String periodoTexto = txtPeriodo.getText().trim();

	        // Validar que el per�odo no est� vac�o
	        if (periodoTexto.isEmpty()) {
	            JOptionPane.showMessageDialog(mainView, "¡Debe ingresar el período en años!", "¡Advertencia!",
	                    JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        // Validar que el per�odo sea un n�mero v�lido
	        try {
	        	int periodoAnios = Integer.parseInt(periodoTexto);

	            if (periodoAnios <= 0) {
	                JOptionPane.showMessageDialog(mainView, "¡El período debe ser un número mayor a 0!", "¡Error!",
	                        JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            // Proceder con la asignaci�n del director
	            try {
	                University universidad = universityController.getUniversidad();
	                if (universidad != null && universidad.getEscuelas() != null) {
	                    for (School escuela : universidad.getEscuelas()) {
	                        if (escuela.getVarName().equalsIgnoreCase(nombreEscuela)) {
	                            escuela.setVarDirector(nombreDirector);
	                            JOptionPane.showMessageDialog(mainView,
	                                    "¡Director " + nombreDirector + " asignado exitosamente a la escuela "
	                                            + nombreEscuela + " por un período de " + periodoAnios + " años!",
	                                    "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
	                            limpiarPanelProfesor();
	                            break; // ya lo asignaste, puedes salir del for
	                        }
	                    }
	                    
	                    
	                    String filaTexto =
                        		"Director de la Escuela " + nombreEscuela + "...\n" +
                                "Profesor: " + nombreDirector + " " + apellido1 + " " + apellido2 + "\n" +
                                "Cédula: " + cedulaProf + "\n" +
                                "Periodo de tiempo en el que será director: " + periodoAnios + " años\n" +
                                "----------------------------------------------------------------\n";
	                   
	                    // Agregar al textarea el nuevo contenido
	                    mainView.txtAreaDirectores.append(filaTexto);
	                }
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(mainView, "¡Error al asignar el director!", "¡Error!",
	                        JOptionPane.ERROR_MESSAGE);
	            }

	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(mainView, "¡El período debe ser un número válido!", "¡Error!",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	private void limpiarCamposAsignacion() {
		mainView.txtCedulaAsignar.setText("");
		mainView.txtSiglasAsignar.setText("");
		mainView.txtGrupoAsignar.setText("");
	}

	private void mostrarProfesoresEnTextArea() {
		DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
		mainView.txtAreaProfesores.setText(""); // Limpiar antes de cargar

		for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
			String nombre = modeloProfesores.getValueAt(i, 0).toString();
			String apellido1 = modeloProfesores.getValueAt(i, 1).toString();
			String apellido2 = modeloProfesores.getValueAt(i, 2).toString();
			String cedula = modeloProfesores.getValueAt(i, 3).toString();

			mainView.txtAreaProfesores.append("Nombre: " + nombre + " " + apellido1 + " "+ apellido2 + " | Cédula: " + cedula + "\n");
		}
	}

	private void deseleccionarTablaAsigActionListener() {
		mainView.btnDeseleccionarTablaAsig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarPanelAsignacion();
			}
		});
	}

	private void limpiarPanelAsignacion() {
		// Limpiar campos de texto
		mainView.txtCedulaAsignar.setText("");
		mainView.txtSiglasAsignar.setText("");
		mainView.txtGrupoAsignar.setText("");

		// Limpiar selecci�n de la tabla
		mainView.tablaAsignaciones.clearSelection();

		// Restablecer estado de los botones y campos
		mainView.btnAsignarProfesorAsig.setEnabled(true);
		mainView.txtCedulaAsignar.setEnabled(true);
		mainView.txtSiglasAsignar.setEnabled(true);
		mainView.txtGrupoAsignar.setEnabled(true);

		mainView.btnDesasignarProfesorAsig.setEnabled(false);
		mainView.btnAsignarDirectorAsig.setEnabled(false);
		mainView.btnDeseleccionarTablaAsig.setEnabled(false);
	}

}