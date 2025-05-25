package com.mvc.controls;

import com.mvc.models.Courses;
import com.mvc.models.School;
import com.mvc.models.Teacher;
import com.mvc.models.University;
import com.mvc.view.MainView;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
		asignarProfesorActionListener();
		desasignarProfesorActionListener();
		asignarDirectorActionListener();
		setupTableSelectionListener();
		setupBtnDeseleccionarTablaActionListener();
		setupVerCursosDisponiblesButtonListener();

	}

	// agregar Profesor action Listener
	private void asignarProfesorActionListener() {
		mainView.btnAsignarProfesor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				asignarProfesor();
			}
		});
	}

	// metodo de agregar y asignar profesor
	public void asignarProfesor() {
		String nombre = mainView.txtNombreProfesor.getText().trim();
		String apellido1 = mainView.txtPrimerApe.getText().trim();
		String apellido2 = mainView.txtSegundoApe.getText().trim();
		String cedula = mainView.txtCedula.getText().trim();
		String grupo = mainView.txtNombreGrupoProf.getText().trim();
		String siglasCurso = mainView.txtSiglasCurso.getText().trim();

		// Validar campos vacíos
		if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() || cedula.isEmpty() || grupo.isEmpty()
				|| siglasCurso.isEmpty()) {
			JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Validar que la cédula contenga solo números
		if (!cedula.matches("\\d+")) {
			JOptionPane.showMessageDialog(mainView, "¡La cédula debe contener solo números!", "¡Error!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar si existe el curso con esas siglas
		DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
		boolean cursoExiste = false;
		for (int i = 0; i < modeloCursos.getRowCount(); i++) {
			String siglasRegistradas = (String) modeloCursos.getValueAt(i, 1); // columna de siglas
			if (siglasCurso.equalsIgnoreCase(siglasRegistradas)) {
				cursoExiste = true;
				break;
			}
		}
		if (!cursoExiste) {
			JOptionPane.showMessageDialog(mainView, "¡El curso con las siglas ingresadas no existe!", "¡Advertencia!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Verificar si ya existe un profesor con esa cédula
		DefaultTableModel modeloProfesores = (DefaultTableModel) mainView.tablaProfesores.getModel();
		for (int i = 0; i < modeloProfesores.getRowCount(); i++) {
			String cedulaRegistrada = (String) modeloProfesores.getValueAt(i, 4);
			if (cedula.equalsIgnoreCase(cedulaRegistrada)) {
				JOptionPane.showMessageDialog(mainView, "¡Ya existe un profesor con esa cédula!", "¡Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Registrar profesor
		Object[] fila = { siglasCurso, nombre, apellido1, apellido2, cedula, grupo };
		modeloProfesores.addRow(fila);

		JOptionPane.showMessageDialog(mainView, "¡Profesor registrado exitosamente!", "¡Éxito!",
				JOptionPane.INFORMATION_MESSAGE);
		mainView.btnConsultas.setEnabled(true);
		limpiarCamposProfesor();
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

	// metodo de modificar profesor (excepto cédula)
	public void modificarProfesor() {
		int filaSeleccionada = mainView.tablaProfesores.getSelectedRow();

		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(mainView, "Seleccione un profesor de la tabla para modificar",
					"¡Advertencia!", JOptionPane.WARNING_MESSAGE);
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
		modelo.setValueAt(nuevoNombre, filaSeleccionada, 1);
		modelo.setValueAt(nuevoApellido1, filaSeleccionada, 2);
		modelo.setValueAt(nuevoApellido2, filaSeleccionada, 3);

		JOptionPane.showMessageDialog(mainView, "¡Profesor modificado exitosamente!", "¡Éxito!",
				JOptionPane.INFORMATION_MESSAGE);
		limpiarPanelProfesor();
	}

	private void desasignarProfesorActionListener() {
		mainView.btnDesasignar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desasignarProfesor();
			}
		});
	}

	public void desasignarProfesor() {
		String varCedulaProf = mainView.txtCedula.getText().trim();
		DefaultTableModel modeloTablaProf = (DefaultTableModel) mainView.tablaProfesores.getModel();
		boolean seleccionado = false;

		for (int i = 0; i < modeloTablaProf.getRowCount(); i++) {
			Object valorCelda = modeloTablaProf.getValueAt(i, 4);
			if (valorCelda != null && valorCelda.toString().equalsIgnoreCase(varCedulaProf)) {
				modeloTablaProf.removeRow(i);
				JOptionPane.showMessageDialog(mainView, "¡Se desasigno el profesor!", "¡Éxito!",
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

	}

	private void asignarDirectorActionListener() {
		mainView.btnAsignarDirector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				asignarDirector();
			}
		});
	}

	private void asignarDirector() {
		String varSiglasCursoProf = mainView.txtSiglasCurso.getText().trim();
		String nombreDirector = mainView.txtNombreProfesor.getText().trim();

		// Validar que los campos no estén vacíos
		if (varSiglasCursoProf.isEmpty() || nombreDirector.isEmpty()) {
			JOptionPane.showMessageDialog(mainView, "¡Debe ingresar las siglas del curso y el nombre del director!",
					"¡Advertencia!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Buscar el curso en la tabla de cursos para obtener el nombre de la escuela
		DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();
		String nombreEscuela = null;
		boolean cursoEncontrado = false;

		for (int i = 0; i < modeloCursos.getRowCount(); i++) {
			String siglasRegistradas = (String) modeloCursos.getValueAt(i, 1); // Columna 1: siglas
			if (varSiglasCursoProf.equalsIgnoreCase(siglasRegistradas)) {
				nombreEscuela = (String) modeloCursos.getValueAt(i, 0); // Columna 0: nombre de escuela
				cursoEncontrado = true;
				break;
			}
		}

		// Validar que se encontró el curso
		if (!cursoEncontrado) {
			JOptionPane.showMessageDialog(mainView, "¡No se encontró el curso con las siglas especificadas!", "¡Error!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Crear panel personalizado para solicitar el período
		JPanel panelPeriodo = new JPanel();
		panelPeriodo.setLayout(new FlowLayout());

		JLabel labelPeriodo = new JLabel("Período en años:");
		JTextField txtPeriodo = new JTextField(10);

		panelPeriodo.add(labelPeriodo);
		panelPeriodo.add(txtPeriodo);

		// Mostrar el JOptionPane con el panel personalizado
		int resultado = JOptionPane.showConfirmDialog(mainView, panelPeriodo, "Asignar Director - Período",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		// Verificar si el usuario presionó OK (O aceptar)
		if (resultado == JOptionPane.OK_OPTION) {
			String periodoTexto = txtPeriodo.getText().trim();

			// Validar que el período no esté vacío
			if (periodoTexto.isEmpty()) {
				JOptionPane.showMessageDialog(mainView, "¡Debe ingresar el período en años!", "¡Advertencia!",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Validar que el período sea un número válido
			try {
				int periodoAnios = Integer.parseInt(periodoTexto);

				if (periodoAnios <= 0) {
					JOptionPane.showMessageDialog(mainView, "¡El período debe ser un número mayor a 0!", "¡Error!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Proceder con la asignación del director
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
								return;
							}
						}
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
		// Si el usuario presiona Cancel, no hace nada y regresa
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
			String siglasCurso = (String) modelo.getValueAt(filaSeleccionada, 0);
			String nombre = (String) modelo.getValueAt(filaSeleccionada, 1);
			String apellido1 = (String) modelo.getValueAt(filaSeleccionada, 2);
			String apellido2 = (String) modelo.getValueAt(filaSeleccionada, 3);
			String cedula = (String) modelo.getValueAt(filaSeleccionada, 4);
			String grupo = (String) modelo.getValueAt(filaSeleccionada, 5);

			// Cargar los datos en los campos de texto
			mainView.txtSiglasCurso.setText(siglasCurso);
			mainView.txtNombreProfesor.setText(nombre);
			mainView.txtPrimerApe.setText(apellido1);
			mainView.txtSegundoApe.setText(apellido2);
			mainView.txtCedula.setText(cedula);
			mainView.txtNombreGrupoProf.setText(grupo);

			// Actualizar estado de los botones
			mainView.btnAsignarProfesor.setEnabled(false);
			mainView.txtCedula.setEnabled(false);
			mainView.txtSiglasCurso.setEnabled(false);
			mainView.txtNombreGrupoProf.setEnabled(false);

			mainView.btnModificarProfesor.setEnabled(true);
			mainView.btnDeseleccionarTablaProf.setEnabled(true);
			mainView.btnDesasignar.setEnabled(true);
			mainView.btnAsignarDirector.setEnabled(true);
		}
	}

	// Limpiar campos del formulario
	private void limpiarCamposProfesor() {
		mainView.txtNombreProfesor.setText("");
		mainView.txtPrimerApe.setText("");
		mainView.txtSegundoApe.setText("");
		mainView.txtCedula.setText("");
		mainView.txtNombreGrupoProf.setText("");
		mainView.txtSiglasCurso.setText("");
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
		mainView.txtNombreGrupoProf.setText("");
		mainView.txtSiglasCurso.setText("");

		mainView.tablaProfesores.clearSelection(); // Limpiar selección de la tabla

		// Restablecer estado de los botones
		mainView.btnAsignarProfesor.setEnabled(true);
		mainView.txtSiglasCurso.setEnabled(true);
		mainView.txtCedula.setEnabled(true);
		mainView.txtNombreGrupoProf.setEnabled(true);

		mainView.btnModificarProfesor.setEnabled(false);
		mainView.btnDeseleccionarTablaProf.setEnabled(false);
		mainView.btnDesasignar.setEnabled(false);
		mainView.btnAsignarDirector.setEnabled(false);

	}

	private void setupVerCursosDisponiblesButtonListener() {
		mainView.btnVerCursosDisponibles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verCursosDisponibles();
			}
		});
	}

	// metodo para que el profesor pueda ver los cursos disponibles
	public void verCursosDisponibles() {
		DefaultTableModel modelo = (DefaultTableModel) mainView.tablaCursos.getModel();

		if (modelo.getRowCount() == 0) {
			JOptionPane.showMessageDialog(mainView, "No hay cursos registrados aún.", "Cursos disponibles",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		StringBuilder cursosDisponibles = new StringBuilder("Cursos disponibles:\n\n");

		for (int i = 0; i < modelo.getRowCount(); i++) {
			String escuela = modelo.getValueAt(i, 0).toString();
			String sigla = modelo.getValueAt(i, 1).toString();
			String descripcion = modelo.getValueAt(i, 2).toString();

			cursosDisponibles.append("Escuela: ").append(escuela).append(", Sigla: ").append(sigla)
					.append(", Descripción: ").append(descripcion).append("\n");
		}

		JTextArea textArea = new JTextArea(cursosDisponibles.toString());
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(400, 250));

		JOptionPane.showMessageDialog(mainView, scrollPane, "Cursos disponibles", JOptionPane.INFORMATION_MESSAGE);
	}

}