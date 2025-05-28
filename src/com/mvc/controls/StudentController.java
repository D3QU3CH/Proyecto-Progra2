package com.mvc.controls;

import com.mvc.models.Student;
import com.mvc.models.StudentNational;
import com.mvc.models.StudentForeign;
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

	public StudentController(StudentView studentView) {
		this.studentView = studentView;

		limpiarFormularioEstudiante();
		agregarEstudianteActionListener();
		buscarEstudianteActionListener();
		modificarEstudianteActionListener();

		eliminarEstudianteActionListener();
		setupTableEstudiantesgSelectionListener();
		setupBtnDeseleccionarTablaActionListener();
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

		// Validación: Solo números en cédula y carnet
		if (!cedula.matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "La cédula debe contener solo números.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!carnet.matches("\\d+")) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "El carnet debe contener solo números.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validación: Todos los campos obligatorios llenos
		if (!cedula.isEmpty() && !carnet.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty()) {

			// Validación: No permitir cédula ni carnet repetido
			for (Student s : estudiantesList) {
				if (s.getVarId().equalsIgnoreCase(cedula)) {
					JOptionPane.showMessageDialog(studentView.estudiantesPanel,
							"Ya existe un estudiante con esa cédula.", "Error", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Porcentaje de beca inválido",
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
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Estudiante registrado exitosamente!", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarFormularioEstudiante();
		} else {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel,
					"¡Todos los campos obligatorios deben estar llenos!", "Advertencia", JOptionPane.WARNING_MESSAGE);
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

				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Estudiante eliminado!", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}

		if (!encontrado) {
			JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Estudiante no encontrado!", "Advertencia",
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
			studentView.btnBuscarEstudiante.setEnabled(true);

		}
	}

	// buscar estudiante action Listener
	private void buscarEstudianteActionListener() {
		studentView.btnBuscarEstudiante.addActionListener(new ActionListener() {
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

				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Estudiante encontrado!", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		JOptionPane.showMessageDialog(studentView.estudiantesPanel, "¡Estudiante no encontrado!", "Error",
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

		studentView.tablaEstudiantes.clearSelection(); // Limpiar selección de tabla

		// Restaurar botones y campos
		studentView.btnAgregarEstudiante.setEnabled(true);
		studentView.txtCedula.setEnabled(true);
		studentView.txtCarnet.setEnabled(true);
		studentView.txtPorcentajeBeca.setEnabled(true);

		studentView.btnModificarEstudiante.setEnabled(false);
		studentView.btnEliminarEstudiante.setEnabled(false);
		studentView.btnDeseleccionarTabla.setEnabled(false);
		studentView.btnBuscarEstudiante.setEnabled(false);

	}

	private void limpiarFormularioEstudiante() {
		studentView.txtNombre.setText("");
		studentView.txtApellidos.setText("");
		studentView.txtCedula.setText("");
		studentView.txtCarnet.setText("");
		studentView.txtPorcentajeBeca.setText("");
		studentView.boxNacionalidad.setSelectedIndex(0);
	}

}
