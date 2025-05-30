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

	private StudentView studentView;
	private ArrayList<Student> estudiantesList = new ArrayList<>();

	private MainView mainView;

	public StudentController(StudentView studentView, MainView mainView) {
		this.studentView = studentView;
		this.mainView = mainView;

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

		boolean esExtranjero = "Extranjero".equalsIgnoreCase(nacionalidad);

		// Todos los campos obligatorios llenos (excepto porcentaje si es extranjero)
		if (cedula.isEmpty() || carnet.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()
				|| (!esExtranjero && porcentajeBecaTexto.isEmpty())) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel,
					"¡Todos los campos obligatorios deben estar llenos!", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Cédula nacional solo números
		if (!esExtranjero && !cedula.matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel,
					"La cédula nacional debe contener solo números.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Carnet solo números
		if (!carnet.matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "El carnet debe contener solo números.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// No permitir cédula ni carnet duplicado entre estudiantes
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

		// No permitir cédula duplicada con un profesor
		DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
		for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
			String cedulaProfesor = modeloProfesores.getValueAt(i, 3).toString();
			if (cedula.equalsIgnoreCase(cedulaProfesor)) {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Ya existe un profesor con esa cédula.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Crear estudiante según nacionalidad
		Student estudiante;
		if (!esExtranjero) {
			double porcentajeBeca = 0.0;
			try {
				porcentajeBeca = Double.parseDouble(porcentajeBecaTexto);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Porcentaje de beca inválido", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
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
		JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Estudiante registrado exitosamente!", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
		limpiarFormularioEstudiante();
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
			boolean esExtranjero = "Extranjero".equalsIgnoreCase(nacionalidad);

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
							studentView.txtPorcentajeBeca.setText("No aplica");
							studentView.txtPorcentajeBeca.setEditable(false);

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
							studentView.txtPorcentajeBeca.setEditable(true);
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
		String cedula = studentView.txtBuscarEstudiante.getText().trim();

		if (cedula.isEmpty()) {
			JOptionPane.showMessageDialog(studentView.panelBusquedaEstudiante, "Debe ingresar una cédula.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (Student estudiante : estudiantesList) {
			if (estudiante.getVarId().equalsIgnoreCase(cedula)) {
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
		studentView.txtPorcentajeBeca.setText("");
		studentView.boxNacionalidad.setSelectedIndex(0);

		studentView.tablaEstudiantes.clearSelection();

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

		if (cedulaEst.isEmpty() || cedulaProf.isEmpty() || siglaCurso.isEmpty()) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "Todos los campos deben estar llenos.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar existencia del estudiante
		boolean estudianteExiste = false;
		String escuelaEst = "";
		for (int i = 0; i < studentView.tablaEstudiantes.getRowCount(); i++) {
			if (studentView.tablaEstudiantes.getValueAt(i, 2).toString().equals(cedulaEst)) {
				estudianteExiste = true;
				escuelaEst = mainView.tablaCursos.getValueAt(i, 0).toString();
				break;
			}
		}

		if (!estudianteExiste) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El estudiante no existe.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar existencia del profesor
		boolean profesorExiste = false;
		for (int i = 0; i < mainView.tablaProfesores.getRowCount(); i++) {
			if (mainView.tablaProfesores.getValueAt(i, 3).toString().equals(cedulaProf)) {
				profesorExiste = true;
				break;
			}
		}

		if (!profesorExiste) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El profesor no existe.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar existencia del curso
		boolean cursoExiste = false;
		for (int i = 0; i < mainView.tablaCursos.getRowCount(); i++) {
			if (mainView.tablaCursos.getValueAt(i, 1).toString().equalsIgnoreCase(siglaCurso)) {
				cursoExiste = true;
				break;
			}
		}

		if (!cursoExiste) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El curso no existe.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// validar de que el grupo existe
		boolean grupoExiste = false;
		for (int i = 0; i < mainView.tablaAsignaciones.getRowCount(); i++) {
			if (mainView.tablaAsignaciones.getValueAt(i, 3).toString().equalsIgnoreCase(grupo)) {
				grupoExiste = true;
				break;
			}
		}

		if (!grupoExiste) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "El grupo no existe.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar que el profesor esté asignado a ese curso
		boolean asignadoAlCurso = false;
		for (int i = 0; i < mainView.tablaAsignaciones.getRowCount(); i++) {
			String cedulaAsignada = mainView.tablaAsignaciones.getValueAt(i, 1).toString(); // Columna 0: cédula del
																							// profesor
			String siglaAsignada = mainView.tablaAsignaciones.getValueAt(i, 2).toString(); // Columna 2: sigla del curso
			if (cedulaAsignada.equals(cedulaProf) && siglaAsignada.equalsIgnoreCase(siglaCurso)) {
				asignadoAlCurso = true;
				break;
			}
		}

		if (!asignadoAlCurso) {
			JOptionPane.showMessageDialog(studentView.matriculaPanel, "Ese profesor no está asignado a ese curso.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar que no esté ya matriculado en ese curso
		for (int i = 0; i < studentView.tablaMatriculas.getRowCount(); i++) {
			String cedulaMatriculada = studentView.tablaMatriculas.getValueAt(i, 1).toString();
			String siglaMatriculada = studentView.tablaMatriculas.getValueAt(i, 3).toString();
			if (cedulaMatriculada.equals(cedulaEst) && siglaMatriculada.equalsIgnoreCase(siglaCurso)) {
				JOptionPane.showMessageDialog(studentView.matriculaPanel,
						"Este estudiante ya está matriculado en ese curso.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Generar número aleatorio entre 3 y 4 para los créditos
		int creditos = (Math.random() < 0.5) ? 3 : 4;

		DefaultTableModel modelo = (DefaultTableModel) studentView.tablaMatriculas.getModel();
		modelo.addRow(new Object[] { escuelaEst, cedulaEst, cedulaProf, grupo, siglaCurso, creditos });

		JOptionPane.showMessageDialog(studentView.matriculaPanel, "¡Estudiante matriculado exitosamente!", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);

		// Limpiar campos
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

			JOptionPane.showMessageDialog(studentView.matriculaPanel, "¡Matrícula eliminada exitosamente!", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

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

}
