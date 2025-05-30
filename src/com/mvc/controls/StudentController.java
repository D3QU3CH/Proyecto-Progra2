package com.mvc.controls;

import com.mvc.models.Student;
import com.mvc.models.StudentNational;
import com.mvc.models.StudentForeign;
import com.mvc.view.MainView;
import com.mvc.view.StudentView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentController {

	private MainView mainView;
	private StudentView studentView;
	private ArrayList<Student> estudiantesList = new ArrayList<>();

	public StudentController(StudentView studentView,MainView pMainview) {
		this.studentView = studentView;
		this.mainView=pMainview;
		nacionalidadComboBoxActionListener();
		limpiarFormularioEstudiante();
		agregarEstudianteActionListener();
		buscarEstudianteActionListener();
		modificarEstudianteActionListener();

		eliminarEstudianteActionListener();
		setupTableEstudiantesgSelectionListener();
		setupBtnDeseleccionarTablaActionListener();
	}
	
	private void nacionalidadComboBoxActionListener() {
	    studentView.boxNacionalidad.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String nacionalidadSeleccionada = (String) studentView.boxNacionalidad.getSelectedItem();
	            if ("Extranjero".equals(nacionalidadSeleccionada)) {
	                studentView.txtPorcentajeBeca.setEnabled(false);
	                studentView.txtPorcentajeBeca.setText(""); 
	            } else {
	                studentView.txtPorcentajeBeca.setEnabled(true);
	            }
	        }
	    });
	}
	
	// agregar estudiante action Listener
	private void agregarEstudianteActionListener() {
		studentView.btnAgregarEstudiante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarEstudiante();
				mostrarEstudiadesEnPanelMatricula();
			}
		});
	}
	
	
	
	public void agregarEstudiante() {
		String cedula = studentView.txtCedula.getText().trim();
		String carnet = studentView.txtCarnet.getText().trim();
		String nombre = studentView.txtNombre.getText().trim();
		String apellidos = studentView.txtApellidos.getText().trim();
		String nacionalidad = (String) studentView.boxNacionalidad.getSelectedItem();
		String porcentajeBecaTexto = studentView.txtPorcentajeBeca.getText().trim();
		
		if (!cedula.trim().matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "La cedula debe contener solo números.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!carnet.trim().matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "El carnet debe contener solo números.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		// Validaci�n: Todos los campos obligatorios llenos
		if (!cedula.isEmpty() && !carnet.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty()) {

			// Validaci�n: No permitir c�dula ni carnet repetido
			for (Student s : estudiantesList) {
				if (s.getVarId().equalsIgnoreCase(cedula)) {
					JOptionPane.showMessageDialog(studentView.estudiantesPanel,
							"Ya existe un estudiante con esa cedula.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (s.getVarCarnet().equalsIgnoreCase(carnet)) {
					JOptionPane.showMessageDialog(studentView.estudiantesPanel,
							"Ya existe un estudiante con ese carnet.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			Student estudiante;

			if ("Nacional".equalsIgnoreCase(nacionalidad)) {
				double porcentajeBeca = 0.0;
				if (!porcentajeBecaTexto.isEmpty()) {
					try {
						porcentajeBeca = Double.parseDouble(porcentajeBecaTexto);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Porcentaje de beca inv�lido",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				estudiante = new StudentNational(cedula, carnet, nombre, apellidos, nacionalidad, porcentajeBeca);
			} else {
				estudiante = new StudentForeign(cedula, carnet, nombre, apellidos, nacionalidad);
			}

			// Agregar a la tabla
			DefaultTableModel modelo = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
			modelo.addRow(new Object[] { estudiante.getVarName(), estudiante.getVarLastnames(), estudiante.getVarId(),
					estudiante.getVarCarnet(), estudiante.getVarNationality(),
					estudiante instanceof StudentNational
							? ((StudentNational) estudiante).getVarScholarshipPercentage() + "%"
							: "No aplica" });

			estudiantesList.add(estudiante);
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "!Estudiante registrado exitosamente!", "!Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarFormularioEstudiante();
		} else {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel,
					"!Todos los campos obligatorios deben estar llenos!", "Advertencia", JOptionPane.WARNING_MESSAGE);
		}
	}

	// modificar estudiante action Listener
	private void modificarEstudianteActionListener() {
		studentView.btnModificarEstudiante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificarEstudiante();
			}
		});
	}

	public void modificarEstudiante() {
		int fila = studentView.tablaEstudiantes.getSelectedRow();
		if (fila != -1) {
			String nombre = studentView.txtNombre.getText().trim();
			String apellidos = studentView.txtApellidos.getText().trim();
			String nacionalidad = (String) studentView.boxNacionalidad.getSelectedItem();

			if (!nombre.isEmpty() && !apellidos.isEmpty()) {
				String cedula = (String) studentView.tablaEstudiantes.getValueAt(fila, 2);

				for (Student estudiante : estudiantesList) {
					if (estudiante.getVarId().equalsIgnoreCase(cedula)) {
						estudiante.setVarName(nombre);
						estudiante.setVarLastnames(apellidos);
						estudiante.setVarNationality(nacionalidad);

						DefaultTableModel modelo = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
						modelo.setValueAt(nombre, fila, 0);
						modelo.setValueAt(apellidos, fila, 1);
						modelo.setValueAt(nacionalidad, fila, 4);

						JOptionPane.showMessageDialog(studentView.estudiantesPanel,
								"!Estudiante modificado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			} else {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel,
						"!Nombre y apellidos no pueden estar vac�os!", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "!Seleccione un estudiante para modificar!",
					"Advertencia", JOptionPane.WARNING_MESSAGE);
		}
	}

	// eliminar estudiante action Listener
	private void eliminarEstudianteActionListener() {
		studentView.btnEliminarEstudiante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarEstudiante();
			}
		});
	}

	public void eliminarEstudiante() {
		String cedulaEstudiante = studentView.txtCedula.getText().trim();
		DefaultTableModel modeloTabla = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
		boolean encontrado = false;

		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			String cedulaFila = (String) modeloTabla.getValueAt(i, 2);
			if (cedulaFila != null && cedulaFila.equalsIgnoreCase(cedulaEstudiante)) {
				modeloTabla.removeRow(i);
				encontrado = true;

				// Eliminar de la lista interna
				estudiantesList.removeIf(est -> est.getVarId().equalsIgnoreCase(cedulaEstudiante));

				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "!Estudiante eliminado!", "�xito",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}

		if (!encontrado) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "!Estudiante no encontrado!", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
		}

		limpiarFormularioEstudiante();
	}

	private void setupTableEstudiantesgSelectionListener() {
		studentView.tablaEstudiantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					llenarFormularioDesdeTabla();
				}
			}
		});

	}

	private void llenarFormularioDesdeTabla() {
		int fila = studentView.tablaEstudiantes.getSelectedRow();

		if (fila != -1) {
			DefaultTableModel modelo = (DefaultTableModel) studentView.tablaEstudiantes.getModel();

			String nombre = (String) modelo.getValueAt(fila, 0);
			String apellidos = (String) modelo.getValueAt(fila, 1);
			String cedula = (String) modelo.getValueAt(fila, 2);
			String carnet = (String) modelo.getValueAt(fila, 3);
			String nacionalidad = (String) modelo.getValueAt(fila, 4);
			String porcentaje = (String) modelo.getValueAt(fila, 5);

			studentView.txtNombre.setText(nombre);
			studentView.txtApellidos.setText(apellidos);
			studentView.txtCedula.setText(cedula);
			studentView.txtCarnet.setText(carnet);
			studentView.boxNacionalidad.setSelectedItem(nacionalidad);

			if (!porcentaje.equalsIgnoreCase("No aplica")) { 
				studentView.txtPorcentajeBeca.setText(porcentaje.replace("%", ""));
			} else {
				studentView.txtPorcentajeBeca.setText("");
			}

			// Activar/desactivar botones
			studentView.btnAgregarEstudiante.setEnabled(false);
			studentView.txtCarnet.setEnabled(false);
			studentView.txtCedula.setEnabled(false);
			studentView.txtPorcentajeBeca.setEnabled(false);
			studentView.btnModificarEstudiante.setEnabled(true);
			studentView.btnEliminarEstudiante.setEnabled(true);
			studentView.btnDeseleccionarTabla.setEnabled(true);
			studentView.btnBuscarEstudiantePorCedula.setEnabled(true);

		}
	}

	// buscar estudiante action Listener
	private void buscarEstudianteActionListener() {
		studentView.btnBuscarEstudiantePorCedula.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarEstudiante();
			}
		});
	}

	public void buscarEstudiante() {
		String cedula = studentView.txtCedula.getText().trim();
		String carnet = studentView.txtCarnet.getText().trim();

		for (Student estudiante : estudiantesList) {
			if (estudiante.getVarId().equalsIgnoreCase(cedula) || estudiante.getVarCarnet().equalsIgnoreCase(carnet)) {
				studentView.txtNombre.setText(estudiante.getVarName());
				studentView.txtApellidos.setText(estudiante.getVarLastnames());
				studentView.txtCedula.setText(estudiante.getVarId());
				studentView.txtCarnet.setText(estudiante.getVarCarnet());
				studentView.boxNacionalidad.setSelectedItem(estudiante.getVarNationality());

				if (estudiante instanceof StudentNational) {
					studentView.txtPorcentajeBeca
							.setText(String.valueOf(((StudentNational) estudiante).getVarScholarshipPercentage()));
				} else {
					studentView.txtPorcentajeBeca.setText("");
				}

				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "!Estudiante encontrado!", "�xito",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		JOptionPane.showMessageDialog(studentView.estudiantesPanel, "!Estudiante no encontrado!", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void setupBtnDeseleccionarTablaActionListener() {
		studentView.btnDeseleccionarTabla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarPanelEstudiante();
			}
		});
	}

	private void limpiarPanelEstudiante() {
		studentView.txtNombre.setText("");
		studentView.txtApellidos.setText("");
		studentView.txtCedula.setText("");
		studentView.txtCarnet.setText("");
		studentView.txtPorcentajeBeca.setText("");
		studentView.boxNacionalidad.setSelectedIndex(0);

		studentView.tablaEstudiantes.clearSelection(); // Limpiar selecci�n de tabla

		// Restaurar botones y campos
		studentView.btnAgregarEstudiante.setEnabled(true);
		studentView.txtCedula.setEnabled(true);
		studentView.txtCarnet.setEnabled(true);
		studentView.txtPorcentajeBeca.setEnabled(true);

		studentView.btnModificarEstudiante.setEnabled(false);
		studentView.btnEliminarEstudiante.setEnabled(false);
		studentView.btnDeseleccionarTabla.setEnabled(false);
		studentView.btnBuscarEstudiantePorCedula.setEnabled(false);

	}

	private void limpiarFormularioEstudiante() {
		studentView.txtNombre.setText("");
		studentView.txtApellidos.setText("");
		studentView.txtCedula.setText("");
		studentView.txtCarnet.setText("");
		studentView.txtPorcentajeBeca.setText("");
		studentView.boxNacionalidad.setSelectedIndex(0);
	}
	
	public void mostrarEstudiadesEnPanelMatricula() {
		
		
		studentView.txtAreaEstudiantes.setText("");
		DefaultTableModel modeloEstudiantes = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
		
		for(int i =0;i<modeloEstudiantes.getRowCount();i++) {
			
			String nombre = (String) modeloEstudiantes.getValueAt(i, 0);
			String apellidos = (String) modeloEstudiantes.getValueAt(i, 1);
			String cedula = (String) modeloEstudiantes.getValueAt(i, 2);
			String carnet = (String) modeloEstudiantes.getValueAt(i, 3);
			String nacionalidad = (String) modeloEstudiantes.getValueAt(i, 4);
			String porcentaje = (String) modeloEstudiantes.getValueAt(i, 5);
			
			
			String toString = "Nombre: " + nombre +
	                 " | Apellidos: " + apellidos +
	                 " | Cedula: " + cedula +
	                 " | Carnet: " + carnet +
	                 " | Nacionalidad: " + nacionalidad +
	                 " | Porcentaje: " + porcentaje + "\n";
			
			studentView.txtAreaEstudiantes.append(toString);
			
		}
		
	}
	
	public void mostrarEstudiadesEnPanelMatriculaCursosDisponibles() {
		studentView.txtAreaCursosDisponibles.setText("");
	    DefaultTableModel modeloProfesoresAsignados = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
	    DefaultTableModel modeloTablaCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
	    DefaultTableModel modeloTablaProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();

	    String resultado = ""; // Variable String en vez de StringBuilder

	    for (int i = 0; i < modeloProfesoresAsignados.getRowCount(); i++) {

	        String getSiglasCurso = (String) modeloProfesoresAsignados.getValueAt(i, 2);
	        String getCedulaProfesor = (String) modeloProfesoresAsignados.getValueAt(i, 1);

	        // Validar siglas
	        if (getSiglasCurso == null || getSiglasCurso.trim().isEmpty()) {
	            System.out.println("Siglas vacías en fila " + i);
	            continue;
	        }

	        // Buscar curso por siglas
	        boolean cursoEncontrado = false;
	        for (int j = 0; j < modeloTablaCursos.getRowCount(); j++) {
	            String siglasEnCursos = (String) modeloTablaCursos.getValueAt(j, 1);
	            if (siglasEnCursos != null && siglasEnCursos.equalsIgnoreCase(getSiglasCurso)) {
	                String escuela = (String) modeloTablaCursos.getValueAt(j, 0);
	                String nombreCurso = (String) modeloTablaCursos.getValueAt(j, 2);


	                // Concatenamos al String
	                resultado += "Curso: " + nombreCurso
	                           + " | Escuela: " + escuela
	                           + " | Siglas: " + siglasEnCursos + "\n";

	                cursoEncontrado = true;
	                break;
	            }
	        }

	        if (!cursoEncontrado) {
	            resultado += "Curso no encontrado para siglas: " + getSiglasCurso + "\n";
	        }

	        // Buscar profesor por cédula
	        boolean profesorEncontrado = false;
	        for (int k = 0; k < modeloTablaProfesores.getRowCount(); k++) {
	        	
	            String cedulaProfesor = (String) modeloTablaProfesores.getValueAt(k, 3);
	            if (cedulaProfesor != null && cedulaProfesor.equalsIgnoreCase(getCedulaProfesor)) {
	                String nombre = (String) modeloTablaProfesores.getValueAt(k, 0);
	                String apellido1 = (String) modeloTablaProfesores.getValueAt(k, 1);
	                String apellido2 = (String) modeloTablaProfesores.getValueAt(k, 2);

	                resultado += "Profesor: " + nombre + " " + apellido1 + " " + apellido2
	                           + " | Cédula: " + cedulaProfesor + "\n\n"; // Salto de línea al final

	                profesorEncontrado = true;
	                break;
	            }
	        }

	        if (!profesorEncontrado) {
	            resultado += "Profesor no encontrado para cedula: " + getCedulaProfesor + "\n\n";
	        }
	    }
		studentView.txtAreaCursosDisponibles.append(resultado);
	    // Mostrar el resultado final
	    System.out.print(resultado); // o usar .println si prefieres línea nueva automática
	}
	
	
	
	
	

}
