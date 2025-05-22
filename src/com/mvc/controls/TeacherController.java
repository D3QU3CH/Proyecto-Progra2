package com.mvc.controls;

import com.mvc.models.Courses;
import com.mvc.models.School;
import com.mvc.models.Teacher;
import com.mvc.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TeacherController {

	private MainView mainView;

	public TeacherController(MainView mainView) {
		this.mainView = mainView;
		modificarProfesorActionListener();
		agregarProfesorActionListener();
		setupTableSelectionListener();
		setupBtnDeseleccionarTablaActionListener();
	}

	// agregar Profesor action Listener
	private void agregarProfesorActionListener() {
		mainView.btnAsignarProfesor.addActionListener(new ActionListener() {
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
		String grupo = mainView.txtNombreCursoProf.getText().trim();
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
		limpiarCamposProfesor();
	}

	// SELECCIONAR CURSO
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
			mainView.txtNombreCursoProf.setText(grupo);

			// Actualizar estado de los botones (opcional)
			mainView.btnAsignarProfesor.setEnabled(false);
			mainView.txtCedula.setEnabled(false);
			mainView.txtSiglasCurso.setEnabled(false);
			mainView.txtNombreCursoProf.setEnabled(false);

			mainView.btnModificarProfesor.setEnabled(true);
			mainView.btnDeseleccionarTablaProf.setEnabled(true);
		}
	}

	// Limpiar campos del formulario
	private void limpiarCamposProfesor() {
		mainView.txtNombreProfesor.setText("");
		mainView.txtPrimerApe.setText("");
		mainView.txtSegundoApe.setText("");
		mainView.txtCedula.setText("");
		mainView.txtNombreCursoProf.setText("");
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
		mainView.txtNombreCursoProf.setText("");
		mainView.txtSiglasCurso.setText("");

		mainView.tablaCursos.clearSelection(); // Limpiar selección de la tabla

		// Restablecer estado de los botones
		mainView.btnAsignarProfesor.setEnabled(true);
		mainView.txtSiglasCurso.setEnabled(true);
		mainView.txtCedula.setEnabled(true);
		mainView.txtNombreCursoProf.setEnabled(true);

		mainView.btnModificarProfesor.setEnabled(false);
		mainView.btnDeseleccionarTablaProf.setEnabled(false);

	}

}