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

	public StudentController(StudentView studentView, MainView pMainview) {
		this.studentView = studentView;
		this.mainView = pMainview;

		nacionalidadComboBoxActionListener();
		limpiarFormularioEstudiante();
		agregarEstudianteActionListener();
		buscarEstudianteActionListener();
		modificarEstudianteActionListener();

		eliminarEstudianteActionListener();
		setupTableEstudiantesgSelectionListener();
		setupBtnDeseleccionarTablaActionListener();

		matricularEstudianteActionListener();
		setupBtnDeseleccionarTablaMatriculaActionListener();
		setupTableMatriculaSelectionListener();
		desmatricularEstudianteActionListener();
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
		
		
		boolean esExtranjero = nacionalidad.equalsIgnoreCase("Extranjero");
		// Validación de campos obligatorios
		if (cedula.isEmpty() || carnet.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()
				|| (!esExtranjero && porcentajeBecaTexto.isEmpty())) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel,
					"¡Todos los campos obligatorios deben estar llenos!", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}
		// Validación: Solo números para cédula (excepto extranjeros) y carnet
		if (!esExtranjero && !cedula.matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "La cédula debe contener solo números.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!carnet.matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "El carnet debe contener solo números.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Validación: No permitir cédula ni carnet repetido
		for (Student s : estudiantesList) {
			if (s.getVarId().equalsIgnoreCase(cedula)) {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Ya existe un estudiante con esa cédula.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (s.getVarCarnet().equalsIgnoreCase(carnet)) {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Ya existe un estudiante con ese carnet.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		// Validación: No permitir que la cédula coincida con un profesor
		DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
		for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
			String cedulaProfesor = modeloProfesores.getValueAt(i, 3).toString();
			if (cedula.equalsIgnoreCase(cedulaProfesor)) {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Ya existe un profesor con esa cédula.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		// Crear estudiante
		Student estudiante;
		if (esExtranjero) {
			estudiante = new StudentForeign(cedula, carnet, nombre, apellidos, nacionalidad);
		} else {
			double porcentajeBeca;
			try {
				porcentajeBeca = Double.parseDouble(porcentajeBecaTexto);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Porcentaje de beca inválido", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			estudiante = new StudentNational(cedula, carnet, nombre, apellidos, nacionalidad, porcentajeBeca);
		}
		// Agregar a tabla y lista
		DefaultTableModel modelo = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
		modelo.addRow(new Object[] { 
				estudiante.getVarName(), 
				estudiante.getVarLastnames(), 
				estudiante.getVarId(),
				estudiante.getVarCarnet(), 
				estudiante.getVarNationality(),
				(estudiante instanceof StudentNational)
						? ((StudentNational) estudiante).getVarScholarshipPercentage() + "%"
						: "No aplica" });
		estudiantesList.add(estudiante);
		JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Estudiante registrado exitosamente!", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
		
		limpiarFormularioEstudiante();
		limpiarPanelEstudiante();
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
			String porcentajeBecaTexto = studentView.txtPorcentajeBeca.getText().trim();
			boolean esExtranjero = nacionalidad.equalsIgnoreCase("Extranjero");

			if (!nombre.isEmpty() && !apellidos.isEmpty()) {
				String cedula = (String) studentView.tablaEstudiantes.getValueAt(fila, 2);

				for (int i = 0; i < estudiantesList.size(); i++) {
					Student estudiante = estudiantesList.get(i);
					if (estudiante.getVarId().equalsIgnoreCase(cedula)) {
						estudiante.setVarName(nombre);
						estudiante.setVarLastnames(apellidos);
						estudiante.setVarNationality(nacionalidad);

						// Si antes era nacional y ahora es extranjero, convertirlo
						if (esExtranjero && estudiante instanceof StudentNational) {
							estudiante = new StudentForeign(estudiante.getVarId(), estudiante.getVarCarnet(), nombre,
									apellidos, nacionalidad);
							estudiantesList.set(i, estudiante);

						} else if (!esExtranjero && estudiante instanceof StudentForeign) {
							// Convertir de extranjero a nacional con beca
							double porcentaje;
							try {
								porcentaje = Double.parseDouble(porcentajeBecaTexto);
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(studentView.estudiantesPanel,
										"Porcentaje de beca inválido.", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							estudiante = new StudentNational(estudiante.getVarId(), estudiante.getVarCarnet(), nombre,
									apellidos, nacionalidad, porcentaje);
							estudiantesList.set(i, estudiante);
						} else if (estudiante instanceof StudentNational) {
							// Si sigue siendo nacional, solo actualizar porcentaje
							try {
								double porcentaje = Double.parseDouble(porcentajeBecaTexto);
								((StudentNational) estudiante).setVarScholarshipPercentage(porcentaje);
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(studentView.estudiantesPanel,
										"Porcentaje de beca inválido.", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}

						// Actualizar tabla visual
						DefaultTableModel modelo = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
						modelo.setValueAt(nombre, fila, 0);
						modelo.setValueAt(apellidos, fila, 1);
						modelo.setValueAt(nacionalidad, fila, 4);
						modelo.setValueAt(estudiante instanceof StudentNational
								? ((StudentNational) estudiante).getVarScholarshipPercentage() + "%"
								: "No aplica", fila, 5);
						
						JOptionPane.showMessageDialog(studentView.estudiantesPanel,
								"¡Estudiante modificado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						limpiarPanelEstudiante();
						return;
						
						
					}
				}
			} else {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel,
						"¡Nombre y apellidos no pueden estar vacíos!", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Seleccione un estudiante para modificar!",
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
		limpiarPanelEstudiante();
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
			studentView.btnModificarEstudiante.setEnabled(true);
			studentView.btnEliminarEstudiante.setEnabled(true);
			studentView.btnDeseleccionarTabla.setEnabled(true);

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
		String cedulaOCarnet = studentView.txtBuscarEstudiante.getText().trim();

		if (cedulaOCarnet.isEmpty()) {
			JOptionPane.showMessageDialog(studentView.panelBusquedaEstudiante, "Debe ingresar una cédula.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (Student estudiante : estudiantesList) {
			if (estudiante.getVarId().equalsIgnoreCase(cedulaOCarnet) || estudiante.getVarCarnet().equalsIgnoreCase(cedulaOCarnet)) {
				StringBuilder datos = new StringBuilder();
				datos.append("Nombre: ").append(estudiante.getVarName()).append(" ")
						.append(estudiante.getVarLastnames()).append("\n");
				datos.append("Cédula: ").append(estudiante.getVarId()).append("\n");
				datos.append("Carnet: ").append(estudiante.getVarCarnet()).append("\n");
				datos.append("Nacionalidad: ").append(estudiante.getVarNationality()).append("\n");

				if (estudiante instanceof StudentNational) {
					datos.append("Porcentaje de beca: ")
							.append(((StudentNational) estudiante).getVarScholarshipPercentage()).append("%\n");
				} else {
					datos.append("Porcentaje de beca: No aplica\n");
				}

				datos.append("----------------------------------------\n");
				studentView.showTextAreaEstudiante.setText(datos.toString());

				JOptionPane.showMessageDialog(studentView.panelBusquedaEstudiante, "¡Estudiante encontrado!", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		// Si no se encontró el estudiante
		JOptionPane.showMessageDialog(studentView.panelBusquedaEstudiante, "Estudiante no encontrado.", "Error",
				JOptionPane.ERROR_MESSAGE);
		studentView.showTextAreaEstudiante.setText("");
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
		studentView.boxNacionalidad.setSelectedIndex(0);
		studentView.txtPorcentajeBeca.setText("");

		studentView.tablaEstudiantes.clearSelection();

		// Restaurar botones y campos
		studentView.btnAgregarEstudiante.setEnabled(true);
		studentView.txtCedula.setEnabled(true);
		studentView.txtCarnet.setEnabled(true);

		studentView.btnModificarEstudiante.setEnabled(false);
		studentView.btnEliminarEstudiante.setEnabled(false);
		studentView.btnDeseleccionarTabla.setEnabled(false);

	}

	private void limpiarFormularioEstudiante() {
		studentView.txtNombre.setText("");
		studentView.txtApellidos.setText("");
		studentView.txtCedula.setText("");
		studentView.txtCarnet.setText("");
		studentView.boxNacionalidad.setSelectedIndex(0);
		studentView.txtPorcentajeBeca.setText("");
	}

	public void mostrarEstudiadesEnPanelMatricula() {

		studentView.txtAreaEstudiantes.setText("");
		DefaultTableModel modeloEstudiantes = (DefaultTableModel) studentView.tablaEstudiantes.getModel();

		for (int i = 0; i < modeloEstudiantes.getRowCount(); i++) {

			String nombre = (String) modeloEstudiantes.getValueAt(i, 0);
			String apellidos = (String) modeloEstudiantes.getValueAt(i, 1);
			String cedula = (String) modeloEstudiantes.getValueAt(i, 2);
			String carnet = (String) modeloEstudiantes.getValueAt(i, 3);
			String nacionalidad = (String) modeloEstudiantes.getValueAt(i, 4);
			String porcentaje = (String) modeloEstudiantes.getValueAt(i, 5);

			String toString = "Nombre: " + nombre + " | Apellidos: " + apellidos + " | Cedula: " + cedula + " | Carnet: " + carnet  
							+ "\nNacionalidad: " + nacionalidad + " | Porcentaje: " + porcentaje + "\n" 
							+ "------------------------------------------------------------------------------------------------------------------------\n";
					

			studentView.txtAreaEstudiantes.append(toString);

		}

	}

	public void mostrarEstudiadesEnPanelMatriculaCursosDisponibles() {
	    studentView.txtAreaCursosDisponibles.setText("");
	    DefaultTableModel modeloProfesoresAsignados = (DefaultTableModel) mainView.tablaAsignaciones.getModel();
	    DefaultTableModel modeloTablaCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
	    DefaultTableModel modeloTablaProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
	    
	    String resultado = ""; 
	    
	    for (int i = 0; i < modeloProfesoresAsignados.getRowCount(); i++) {
	        String getCedulaProfesor = (String) modeloProfesoresAsignados.getValueAt(i, 1);
	        String getSiglasCurso = (String) modeloProfesoresAsignados.getValueAt(i, 2);
	        String getGrupo = (String) modeloProfesoresAsignados.getValueAt(i, 3);
	        
	        String escuela = "";
	        String nombreCurso = "";
	        for (int j = 0; j < modeloTablaCursos.getRowCount(); j++) {
	            String siglasEnCursos = (String) modeloTablaCursos.getValueAt(j, 1);
	            if (siglasEnCursos != null && siglasEnCursos.equalsIgnoreCase(getSiglasCurso)) {
	                escuela = (String) modeloTablaCursos.getValueAt(j, 0);
	                nombreCurso = (String) modeloTablaCursos.getValueAt(j, 2);
	                break;
	            }
	        }
	        
	        String nombreCompleto = "";
	        for (int k = 0; k < modeloTablaProfesores.getRowCount(); k++) {
	            String cedulaProfesor = (String) modeloTablaProfesores.getValueAt(k, 3);
	            if (cedulaProfesor != null && cedulaProfesor.equalsIgnoreCase(getCedulaProfesor)) {
	                String nombre = (String) modeloTablaProfesores.getValueAt(k, 0);
	                String apellido1 = (String) modeloTablaProfesores.getValueAt(k, 1);
	                String apellido2 = (String) modeloTablaProfesores.getValueAt(k, 2);
	                nombreCompleto = nombre + " " + apellido1 + " " + apellido2;
	                break;
	            }
	        }
	        
	        // Concatenar la información completa del curso y profesor
	        resultado += "Curso: " + nombreCurso + " | Siglas: " + getSiglasCurso + " | Escuela: " + escuela + "\n" +
	        			 "Profesor: " + nombreCompleto + " | Cédula: " + getCedulaProfesor + " | Grupo: " + getGrupo + "\n"+
	        			 "------------------------------------------------------------------------------------------------------------------------\n";
	    }
	    
	    studentView.txtAreaCursosDisponibles.append(resultado);
	    System.out.print(resultado);
	}

	// metodos de matricula de estudiante

	private void matricularEstudianteActionListener() {
		studentView.btnMatricularEstudiante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matricularEstudiante();
			}
		});
	}

	public void matricularEstudiante() {
		String cedulaEst = studentView.txtCedulaEstudianteMatricula.getText().trim();
		String cedulaProf = studentView.txtCedulaProfesorMatricula.getText().trim();
		String siglaCurso = studentView.txtSiglaCursoMatricula.getText().trim();
		String grupo = studentView.txtGrupoMatricula.getText().trim();

		// Validar campos vacíos
		if (cedulaEst.isEmpty() || cedulaProf.isEmpty() || siglaCurso.isEmpty() || grupo.isEmpty()) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "Todos los campos deben estar llenos.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar existencia del estudiante
		boolean estudianteExiste = false;
		for (int i = 0; i < studentView.tablaEstudiantes.getRowCount(); i++) {
			String cedulaRegistrada = studentView.tablaEstudiantes.getValueAt(i, 2).toString();
			if (cedulaRegistrada.equals(cedulaEst)) {
				estudianteExiste = true;
				break;
			}
		}

		if (!estudianteExiste) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El estudiante no existe.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean profesorAsignado = false;
		String escuelaEst = "";
		for (int i = 0; i < mainView.tablaAsignaciones.getRowCount(); i++) {
			String cedulaProfAsig = mainView.tablaAsignaciones.getValueAt(i, 1).toString();
			if (cedulaProfAsig.equals(cedulaProf)) {
				escuelaEst = mainView.tablaAsignaciones.getValueAt(i, 0).toString();
				profesorAsignado = true;
				break;
			}
		}

		if (!profesorAsignado) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El profesor no existe o no esta asignado.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		boolean cursoExiste = false;
		String escuelaCurso = "";
		for (int i = 0; i < mainView.tablaAsignaciones.getRowCount(); i++) {
			String siglasCursoAsig = mainView.tablaAsignaciones.getValueAt(i, 2).toString();
			if (siglasCursoAsig.equalsIgnoreCase(siglaCurso)) {
				cursoExiste = true;
				escuelaCurso = mainView.tablaAsignaciones.getValueAt(i, 0).toString(); 
				break;
			}
		}

		if (!cursoExiste) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El curso no existe o no tiene profesores asignados.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar si el estudiante ya está matriculado en otra escuela
		for (int i = 0; i < studentView.tablaMatriculas.getRowCount(); i++) {
			String cedulaMatriculada = studentView.tablaMatriculas.getValueAt(i, 1).toString();
			String escuelaYaMatriculada = studentView.tablaMatriculas.getValueAt(i, 0).toString();
			if (cedulaMatriculada.equals(cedulaEst) && !escuelaYaMatriculada.equals(escuelaCurso)) {
				JOptionPane.showMessageDialog(studentView.matriculaPanel,
						"Este estudiante ya está matriculado en otra escuela.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		boolean profesorAsignadoAlCurso = false;
		boolean grupoYaAsignado = false;

		// Primero verificar si el profesor está asignado al curso
		for (int i = 0; i < mainView.tablaAsignaciones.getRowCount(); i++) {
		    String cedulaAsignada = mainView.tablaAsignaciones.getValueAt(i, 1).toString();
		    String siglaAsignada = mainView.tablaAsignaciones.getValueAt(i, 2).toString();
		    
		    if (cedulaAsignada.equals(cedulaProf) && siglaAsignada.equalsIgnoreCase(siglaCurso)) {
		        profesorAsignadoAlCurso = true;
		        break;
		    }
		}
		
		if (!profesorAsignadoAlCurso) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "Ese profesor no está asignado a ese curso.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Luego verificar si el grupo específico ya está asignado a ese profesor en ese curso
		
		String grupoAsignado = "";
		String siglaAsignadas = "";
		for (int i = 0; i < mainView.tablaAsignaciones.getRowCount(); i++) {
		    String cedulaAsignada = mainView.tablaAsignaciones.getValueAt(i, 1).toString();
		    siglaAsignadas = mainView.tablaAsignaciones.getValueAt(i, 2).toString();
		    grupoAsignado = mainView.tablaAsignaciones.getValueAt(i, 3).toString();
		    
		    if (cedulaAsignada.equals(cedulaProf) && 
		        siglaAsignadas.equalsIgnoreCase(siglaCurso) && 
		        grupoAsignado.equalsIgnoreCase(grupo)) {
		        grupoYaAsignado = true;
		        break;
		    }
		}
		
		if (!grupoYaAsignado) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El grupo no existe o no esta asignado al profesor.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		

		// Validar que no esté ya matriculado en ese curso
		for (int i = 0; i < studentView.tablaMatriculas.getRowCount(); i++) {
			String cedulaMatriculada = studentView.tablaMatriculas.getValueAt(i, 1).toString();
			String siglaMatriculada = studentView.tablaMatriculas.getValueAt(i, 4).toString();
			if (cedulaMatriculada.equals(cedulaEst) && siglaMatriculada.equalsIgnoreCase(siglaCurso)) {
				JOptionPane.showMessageDialog(studentView.matriculaPanel,
						"Este estudiante ya está matriculado en ese curso.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Asignar créditos aleatorios 3 o 4
		int creditos = (Math.random() < 0.5) ? 3 : 4;

		DefaultTableModel modelo = (DefaultTableModel) studentView.tablaMatriculas.getModel();
		modelo.addRow(new Object[] { escuelaEst, cedulaEst, cedulaProf, grupoAsignado, siglaAsignadas, creditos });

		JOptionPane.showMessageDialog(studentView.matriculaPanel, "¡Estudiante matriculado exitosamente!", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);

		// Limpiar campos de matricula
		studentView.txtCedulaEstudianteMatricula.setText("");
		studentView.txtCedulaProfesorMatricula.setText("");
		studentView.txtSiglaCursoMatricula.setText("");
		studentView.txtGrupoMatricula.setText("");
	}

	// desmatricular estudiante action Listener
	private void desmatricularEstudianteActionListener() {
		studentView.btnDesmatricularEstudiante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desmatricularEstudiante();
			}
		});
	}

	public void desmatricularEstudiante() {
		int filaSeleccionada = studentView.tablaMatriculas.getSelectedRow();

		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "Debe seleccionar una matrícula para eliminar.",
					"Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirmacion = JOptionPane.showConfirmDialog(studentView.matriculaPanel,
				"¿Está seguro que desea eliminar esta matrícula?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

		if (confirmacion == JOptionPane.YES_OPTION) {
			DefaultTableModel modelo = (DefaultTableModel) studentView.tablaMatriculas.getModel();
			modelo.removeRow(filaSeleccionada);
			
			// Limpiar campos
			studentView.txtCedulaEstudianteMatricula.setText("");
			studentView.txtCedulaProfesorMatricula.setText("");
			studentView.txtSiglaCursoMatricula.setText("");
			studentView.txtGrupoMatricula.setText("");

			// Reactivar campos y botones
			studentView.txtCedulaEstudianteMatricula.setEnabled(true);
			studentView.txtCedulaProfesorMatricula.setEnabled(true);
			studentView.txtSiglaCursoMatricula.setEnabled(true);
			studentView.txtGrupoMatricula.setEnabled(true);
			studentView.btnMatricularEstudiante.setEnabled(true);
			studentView.btnDesmatricularEstudiante.setEnabled(false);
			studentView.btnDeseleccionarTablaMatricula.setEnabled(false);
		}
	}

	private void setupTableMatriculaSelectionListener() {
		studentView.tablaMatriculas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					llenarFormularioDesdeTablaMatricula();
				}
			}
		});

	}

	private void llenarFormularioDesdeTablaMatricula() {
		int fila = studentView.tablaMatriculas.getSelectedRow();

		if (fila != -1) {
			DefaultTableModel modelo = (DefaultTableModel) studentView.tablaMatriculas.getModel();

			String cedulaEstudiante = (String) modelo.getValueAt(fila, 1);
			String cedulaProfesor = (String) modelo.getValueAt(fila, 2);
			String grupoCurso = (String) modelo.getValueAt(fila, 3);
			String siglaCurso = (String) modelo.getValueAt(fila, 4);

			studentView.txtCedulaEstudianteMatricula.setText(cedulaEstudiante);
			studentView.txtCedulaProfesorMatricula.setText(cedulaProfesor);
			studentView.txtSiglaCursoMatricula.setText(siglaCurso);
			studentView.txtGrupoMatricula.setText(grupoCurso);

			studentView.txtCedulaEstudianteMatricula.setEnabled(false);
			studentView.txtCedulaProfesorMatricula.setEnabled(false);
			studentView.txtSiglaCursoMatricula.setEnabled(false);
			studentView.txtGrupoMatricula.setEnabled(false);

			studentView.btnMatricularEstudiante.setEnabled(false);
			studentView.btnDesmatricularEstudiante.setEnabled(true);
			studentView.btnDeseleccionarTablaMatricula.setEnabled(true);
		}
	}

	private void setupBtnDeseleccionarTablaMatriculaActionListener() {
		studentView.btnDeseleccionarTablaMatricula.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarPanelMatricula();
			}
		});
	}

	private void limpiarPanelMatricula() {
		studentView.txtCedulaEstudianteMatricula.setText("");
		studentView.txtCedulaProfesorMatricula.setText("");
		studentView.txtSiglaCursoMatricula.setText("");
		studentView.txtGrupoMatricula.setText("");

		studentView.tablaMatriculas.clearSelection();

		studentView.btnMatricularEstudiante.setEnabled(true);

		studentView.txtCedulaEstudianteMatricula.setEnabled(true);
		studentView.txtCedulaProfesorMatricula.setEnabled(true);
		studentView.txtSiglaCursoMatricula.setEnabled(true);
		studentView.txtGrupoMatricula.setEnabled(true);

		studentView.btnDesmatricularEstudiante.setEnabled(false);
		studentView.btnDeseleccionarTablaMatricula.setEnabled(false);

	}
	
	public void pagoDeCreditos() {
	    studentView.txtAreaPagoCreditos.setText(""); 

	    DefaultTableModel modeloMatricula = (DefaultTableModel) studentView.tablaMatriculas.getModel();
	    DefaultTableModel modeloEstudiantes = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
	    DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();

	    StringBuilder resultado = new StringBuilder();

	    for (int i = 0; i < modeloMatricula.getRowCount(); i++) {
	        String cedulaEstudiante = (String) modeloMatricula.getValueAt(i, 1);
	        String siglasCurso = (String) modeloMatricula.getValueAt(i, 4);
	        int creditos = (int) modeloMatricula.getValueAt(i, 5); 
	        String creditosStr = String.valueOf(creditos);

	        String nombreEstudiante = "";
	        String nacionalidad = "";
	        String nombreCurso = "";


	        for (int f = 0; f < modeloEstudiantes.getRowCount(); f++) {
	            String cedulaTablaEstudiantes = (String) modeloEstudiantes.getValueAt(f, 2);
	            if (cedulaEstudiante.equalsIgnoreCase(cedulaTablaEstudiantes)) {
	                nombreEstudiante = (String) modeloEstudiantes.getValueAt(f, 0);
	                nacionalidad = (String) modeloEstudiantes.getValueAt(f, 4);
	                break;
	            }
	        }

	        for (int j = 0; j < modeloCursos.getRowCount(); j++) {
	            String siglasTablaCursos = (String) modeloCursos.getValueAt(j, 1);
	            if (siglasCurso.equalsIgnoreCase(siglasTablaCursos)) {
	                nombreCurso = (String) modeloCursos.getValueAt(j, 2);
	                break;
	            }
	        }

	        // Cálculo de aranceles
	        double creditosDouble = Double.parseDouble(creditosStr); 
	        double costoPorCreditos = creditosDouble * 10000;
	        double subtotal = costoPorCreditos + 15000;
	        boolean esExtranjero = nacionalidad.equalsIgnoreCase("Extranjero");
	        double total = esExtranjero ? subtotal * 1.4 : subtotal;

	        
	        resultado.append("──────────────────────────────\n");
	        resultado.append("Estudiante: ").append(nombreEstudiante).append(" (").append(nacionalidad).append(")\n");
	        resultado.append("Curso: ").append(nombreCurso).append(" (").append(siglasCurso).append(")\n");
	        resultado.append("Créditos: ").append(creditos).append("\n");
	        resultado.append("Costo por créditos: ¢").append((int) costoPorCreditos).append("\n");
	        resultado.append("Cargos administrativos: ¢15,000\n");

	        if (esExtranjero) {
	            resultado.append("Recargo extranjero (40%): ¢").append((int)(subtotal * 0.4)).append("\n");
	        }

	        resultado.append("Total a pagar: ¢").append((int) total).append("\n");
	        resultado.append("──────────────────────────────\n\n");
	    }

	    studentView.txtAreaPagoCreditos.setText(resultado.toString());
	}

}
