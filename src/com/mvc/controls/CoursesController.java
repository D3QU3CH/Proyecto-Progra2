package com.mvc.controls;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mvc.models.Courses;
import com.mvc.models.School;
import com.mvc.models.University;
import com.mvc.view.MainView;

public class CoursesController {

	private MainView mainView;
	private Courses varCursosRegistrar;
	private University varUniversidad;
	private UniversityController universidadController;

	public CoursesController(MainView mainView, UniversityController universidadController) {
		this.mainView = mainView;
		this.universidadController = universidadController;
		agregarCursosActionListener();
		setupBtnDeseleccionarTablaActionListener();
		eliminarCursoActionListener();
		setupTableSelectionListener();
		modificarCursoActionListener();
		ActionListenerBusquedaPorEscuela();
		setupVerEscuelasButtonListener();
		setupVerBtnRegresarACursos();
	}

	// AGREGAR CURSO
	private void agregarCursosActionListener() {
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
			// Obtener la universidad y su lista de escuelas
			University universidad = universidadController.getUniversidad();
			if (universidad == null || universidad.getEscuelas() == null || universidad.getEscuelas().isEmpty()) {
				JOptionPane.showMessageDialog(mainView, "No hay escuelas registradas en la universidad", "¡Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Buscar la escuela exacta en la lista de escuelas
			String nombreEscuelaExacto = null;
			for (School escuela : universidad.getEscuelas()) {
				if (escuela.getVarName().equalsIgnoreCase(varNombreEscuelas)) {
					nombreEscuelaExacto = escuela.getVarName(); // Obtener el nombre exacto como está en la lista
					break;
				}
			}

			// Si no se encuentra la escuela, mostrar advertencia
			if (nombreEscuelaExacto == null) {
				JOptionPane.showMessageDialog(mainView, "¡No se encontró la Escuela!", "¡Advertencia!",
						JOptionPane.WARNING_MESSAGE);
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
				JOptionPane.showMessageDialog(mainView, "¡Las siglas del curso ya están registradas!", "¡Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Si pasa todas las validaciones, registrar el curso con el nombre exacto de la
			// escuela en la lista
			varCursosRegistrar = new Courses(varSiglasCurso, varDescipcionDeCursos, nombreEscuelaExacto);
			Object[][] datos = { { nombreEscuelaExacto, varSiglasCurso, varDescipcionDeCursos } };
			agregarDatosTabla(datos);

			mainView.enableCursosControls(true);

			JOptionPane.showMessageDialog(mainView, "¡Curso registrado exitosamente!", "¡Éxito!",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarPanelCurso();
		} else {
			// Si algún campo está vacío, mostrar advertencia
			JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Advertencia!",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void setupBtnDeseleccionarTablaActionListener() {
		mainView.varBtnDeseleccionarTabla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarPanelCurso();
			}
		});
	}
	
	public void agregarDatosTabla(Object[][] datos) {
		DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();

		for (Object[] fila : datos) {
			modeloTabla.addRow(fila);
		}
	}

	// ELIMINAR CURSO
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
				JOptionPane.showMessageDialog(mainView, "¡Se eliminó el curso!", "¡Éxito!",
						JOptionPane.INFORMATION_MESSAGE);
				seleccionado = true;
				break;
			}
		}

		if (!seleccionado) {
			JOptionPane.showMessageDialog(mainView, "¡No se seleccionó el curso!", "¡Advertencia!",
					JOptionPane.WARNING_MESSAGE);
		}

		limpiarPanelCurso(); 
	}

	// SELECCIONAR CURSO
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
			mainView.varTxtSigla.setEnabled(false);

			mainView.varBtnModificar.setEnabled(true);
			mainView.varBtnDeseleccionarTabla.setEnabled(true);
		}
	}

	// MODIFICAR CURSO
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
			String varDescipcionDeCursos = mainView.varTxtDescripcion.getText().trim();

			if (!varDescipcionDeCursos.isEmpty()) {
				// Actualizar datos en la tabla
				mainView.tablaCursos.setValueAt(varDescipcionDeCursos, fila, 2);

				JOptionPane.showMessageDialog(mainView, "¡Curso actualizado exitosamente!", "¡Éxito!",
						JOptionPane.INFORMATION_MESSAGE);
				limpiarPanelCurso();
			} else {
				JOptionPane.showMessageDialog(mainView, "¡Todos los campos son obligatorios!", "¡Advertencia!",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(mainView, "¡Seleccione un curso para modificar!", "¡Advertencia!",
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

		String varNombreEscuela = mainView.txtBuscar.getText().trim();
		String varContenidoTxtArea = mainView.txtAreaEscuelas.getText();
		mainView.showTextArea.setText("");

		// Validar si la escuela existe en el JTextArea
		if (!varContenidoTxtArea.toLowerCase().contains(varNombreEscuela.toLowerCase())) {
			JOptionPane.showMessageDialog(mainView, "¡La escuela '" + varNombreEscuela + "' no existe!",
					"¡Escuela no encontrada!", JOptionPane.WARNING_MESSAGE);
			return; // Terminar el método aquí
		}

		// Obtener el modelo de la tabla de cursos
		DefaultTableModel modeloTabla = (DefaultTableModel) mainView.tablaCursos.getModel();
		boolean coincidenciaEncontrada = false;

		// Buscar la escuela en la tabla de cursos
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			String varNombreEscuelaEvaluar = (String) modeloTabla.getValueAt(i, 0); // Columna 0: Nombre de la Escuela

			if (varNombreEscuela.equalsIgnoreCase(varNombreEscuelaEvaluar)) {
				coincidenciaEncontrada = true;

				// Obtener los datos del curso asociados a la tabla
				String nombreEscuela = (String) modeloTabla.getValueAt(i, 0); // Columna 0: Nombre de la Escuela
				String siglasCurso = (String) modeloTabla.getValueAt(i, 1); // Columna 1: Siglas del Curso
				String descripcionCurso = (String) modeloTabla.getValueAt(i, 2); // Columna 2: Descripción del Curso

				// Mostrar en el JTextArea la informacion del curso
				String filaTexto = "Escuela: " + nombreEscuela + ", Siglas: " + siglasCurso + ", Descripción: "
						+ descripcionCurso;

				// Agregar la fila al JTextArea
				mainView.showTextArea.append(filaTexto + "\n");
			}
		}

		// Validar los resultados de la búsqueda
		if (!coincidenciaEncontrada) {
			JOptionPane.showMessageDialog(mainView,
					"¡La escuela '" + varNombreEscuela + "' existe, pero no tiene cursos asociados!", "¡Sin cursos!",
					JOptionPane.WARNING_MESSAGE);
		} else {

			JOptionPane.showMessageDialog(mainView,
					"¡SÍ se encontraron cursos para la escuela '" + varNombreEscuela + "'!", "¡Búsqueda exitosa!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void limpiarPanelCurso() {
		mainView.varTxtSigla.setText("");
		mainView.varTxtDescripcion.setText("");
		mainView.varTxtEscuelaNombres.setText("");
		mainView.tablaCursos.clearSelection(); // Limpiar selección de la tabla

		// Restablecer estado de los botones
		mainView.varBtnRegistrar.setEnabled(true);
		mainView.varTxtEscuelaNombres.setEnabled(true);
		mainView.varTxtSigla.setEnabled(true);

		mainView.varBtnModificar.setEnabled(false);
		mainView.varBtnDeseleccionarTabla.setEnabled(false);

	}

	private void setupVerEscuelasButtonListener() {
		mainView.btnVerEscuelas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarEscuelasDisponibles();
			}
		});
	}

	private void mostrarEscuelasDisponibles() {
		String contenido = mainView.txtAreaEscuelas.getText().trim();
		if (contenido.isEmpty()) {
			JOptionPane.showMessageDialog(mainView, "¡No hay escuelas registradas todavía!", "Escuelas Disponibles",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] lineas = contenido.split("\n");
		StringBuilder escuelasInfo = new StringBuilder("Escuelas disponibles:\n");

		for (String linea : lineas) {
			escuelasInfo.append(linea).append("\n");
		}

		JTextArea textArea = new JTextArea(escuelasInfo.toString());
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(300, 200));

		JOptionPane.showMessageDialog(mainView, scrollPane, "Escuelas Disponibles", JOptionPane.INFORMATION_MESSAGE);
	}

	private void setupVerBtnRegresarACursos() {
		mainView.varBtnRegresar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.showPanel("CURSOS");
			}
		});
	}
	
	
	

}