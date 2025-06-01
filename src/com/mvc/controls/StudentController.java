package com.mvc.controls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mvc.models.Student;
import com.mvc.models.StudentNational;
import com.mvc.models.Teacher;
import com.mvc.models.StudentForeign;
import com.mvc.view.MainView;
import com.mvc.view.StudentView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
///
import java.nio.file.Path; // Para TypeToken
import java.lang.reflect.Type;           // Para Type

public class StudentController {

	private MainView mainView;
	private StudentView studentView;
	private ArrayList<Student> estudiantesList = new ArrayList<>();
	//private StudentNational studentNational;
	//private StudentForeign studentFregin;
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
		
		buscarEstudiantesMatriculadosPorSiglaActionListener();
		buscarCursosPorEstudianteActionListener();
		
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
		// Validación: Porcentaje de beca debe estar entre 0 y 100
		if (!esExtranjero) {
		    try {
		        double porcentajeBeca = Double.parseDouble(porcentajeBecaTexto);
		        if (porcentajeBeca < 0 || porcentajeBeca > 100) {
		            JOptionPane.showMessageDialog(studentView.estudiantesPanel, 
		                "El porcentaje de beca debe estar entre 0 y 100.", 
		                "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		    } catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(studentView.estudiantesPanel, 
		            "El porcentaje de beca debe ser un número válido.", 
		            "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
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
			estudiante = new StudentForeign(nombre, apellidos,cedula, carnet,  nacionalidad);
		} else {
			double porcentajeBeca;
			try {
				porcentajeBeca = Double.parseDouble(porcentajeBecaTexto);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Porcentaje de beca inválido", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			estudiante = new StudentNational(nombre, apellidos,cedula, carnet,  nacionalidad, porcentajeBeca);
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
		escribirDataEstudiantesSinMatricular();
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
							estudiante = new StudentForeign( nombre,
									apellidos,estudiante.getVarId(), estudiante.getVarCarnet(), nacionalidad);
							estudiantesList.set(i, estudiante);

						} else if (!esExtranjero && estudiante instanceof StudentForeign) {
							// Convertir de extranjero a nacional con beca
							double porcentaje;
							try {
								porcentaje = Double.parseDouble(porcentajeBecaTexto);
								if (porcentaje < 0 || porcentaje > 100) {
						            JOptionPane.showMessageDialog(studentView.estudiantesPanel, 
						                "El porcentaje de beca debe estar entre 0 y 100.", 
						                "Error", JOptionPane.ERROR_MESSAGE);
						            return;
						        }	
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(studentView.estudiantesPanel,
										"Porcentaje de beca inválido.", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							estudiante = new StudentNational(nombre,
									apellidos,estudiante.getVarId(), estudiante.getVarCarnet(),  nacionalidad, porcentaje);
							estudiantesList.set(i, estudiante);
						} else if (estudiante instanceof StudentNational) {
							// Si sigue siendo nacional, solo actualizar porcentaje
							try {
								double porcentaje = Double.parseDouble(porcentajeBecaTexto);
								((StudentNational) estudiante).setVarScholarshipPercentage(porcentaje);
								if (porcentaje < 0 || porcentaje > 100) {
						            JOptionPane.showMessageDialog(studentView.estudiantesPanel, 
						                "El porcentaje de beca debe estar entre 0 y 100.", 
						                "Error", JOptionPane.ERROR_MESSAGE);
						            return;
						        }	
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
						escribirDataEstudiantesSinMatricular();
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
	            escribirDataEstudiantesSinMatricular();
	            eliminarMatriculaPorCedula(cedulaEstudiante);

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
	    limpiarPanelEstudiante();
	}
	private void eliminarMatriculaPorCedula(String cedula) {
	    DefaultTableModel modeloMatricula = (DefaultTableModel) studentView.tablaMatriculas.getModel();
	    List<Integer> filasAEliminar = new ArrayList<>();

	 
	    for (int i = 0; i < modeloMatricula.getRowCount(); i++) {
	        String cedulaEnTabla = (String) modeloMatricula.getValueAt(i, 1);
	        if (cedulaEnTabla != null && cedulaEnTabla.equalsIgnoreCase(cedula)) {
	            filasAEliminar.add(i);
	        }
	    }

	  
	    for (int i = filasAEliminar.size() - 1; i >= 0; i--) {
	        modeloMatricula.removeRow(filasAEliminar.get(i));
	    }

	    // Guardar cambios en el JSON
	    guardarMatriculasEnJSON();
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
				guardarMatriculasEnJSON();
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
		String cedulaRegistrada = "";
		for (int i = 0; i < studentView.tablaEstudiantes.getRowCount(); i++) {
			cedulaRegistrada = studentView.tablaEstudiantes.getValueAt(i, 2).toString();
			if (cedulaRegistrada.equalsIgnoreCase(cedulaEst)) {
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
			if (cedulaProfAsig.equalsIgnoreCase(cedulaProf)) {
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
			if (cedulaMatriculada.equalsIgnoreCase(cedulaEst) && !escuelaYaMatriculada.equalsIgnoreCase(escuelaCurso)) {
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
		    
		    if (cedulaAsignada.equalsIgnoreCase(cedulaProf) && siglaAsignada.equalsIgnoreCase(siglaCurso)) {
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
		String cedulaMatriculada = "";
		for (int i = 0; i < studentView.tablaMatriculas.getRowCount(); i++) {
			cedulaMatriculada = studentView.tablaMatriculas.getValueAt(i, 1).toString();
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
		modelo.addRow(new Object[] { escuelaEst, cedulaRegistrada, cedulaProf, grupoAsignado, siglaAsignadas, creditos });

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
			guardarMatriculasEnJSON();
			
			
			limpiarPanelMatricula();
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
	    
	    boolean[] estudiantesProcesados = new boolean[modeloMatricula.getRowCount()];
	    
	    for (int i = 0; i < modeloMatricula.getRowCount(); i++) {
	        if (estudiantesProcesados[i]) {
	            continue; // Ya procesamos este estudiante
	        }
	        
	        String cedulaEstudiante = (String) modeloMatricula.getValueAt(i, 1);
	        
	        // Buscar información del estudiante
	        String nombreEstudiante = "";
	        String nacionalidad = "";
	        String porcentajeBeca = "";
	        for (int f = 0; f < modeloEstudiantes.getRowCount(); f++) {
	            String cedulaTablaEstudiantes = (String) modeloEstudiantes.getValueAt(f, 2);
	            if (cedulaEstudiante.equalsIgnoreCase(cedulaTablaEstudiantes)) {
	                nombreEstudiante = (String) modeloEstudiantes.getValueAt(f, 0);
	                nacionalidad = (String) modeloEstudiantes.getValueAt(f, 4);
	                porcentajeBeca = (String) modeloEstudiantes.getValueAt(f, 5);
	                break;
	            }
	        }
	        
	        boolean esExtranjero = nacionalidad.equalsIgnoreCase("Extranjero");
	        
	        resultado.append("ESTUDIANTE: ").append(nombreEstudiante).append(" (").append(nacionalidad).append(")\n");
	        resultado.append("Cédula: ").append(cedulaEstudiante).append("\n");
	        resultado.append("──────────────────────────────\n");
	        resultado.append("CURSOS MATRICULADOS:\n\n");
	        
	        int totalCreditos = 0;
	        double costoTotalCreditos = 0;
	        
	        for (int j = 0; j < modeloMatricula.getRowCount(); j++) {
	            String cedulaComparar = (String) modeloMatricula.getValueAt(j, 1);
	            
	            if (cedulaEstudiante.equalsIgnoreCase(cedulaComparar)) {
	                estudiantesProcesados[j] = true; 
	                
	                String siglasCurso = (String) modeloMatricula.getValueAt(j, 4);
	                Object valorCelda = modeloMatricula.getValueAt(j, 5);
	                int creditos;
	                ///hubo cambiossss
	                if (valorCelda instanceof Number) {
	                    creditos = ((Number) valorCelda).intValue();
	                } else if (valorCelda != null) {
	                    try {
	                        creditos = Integer.parseInt(valorCelda.toString().split("\\.")[0]);
	                    } catch (NumberFormatException e) {
	                        creditos = 0;
	                    }
	                } else {
	                    creditos = 0;
	                }
	                
	                String nombreCurso = "";
	                for (int k = 0; k < modeloCursos.getRowCount(); k++) {
	                    String siglasTablaCursos = (String) modeloCursos.getValueAt(k, 1);
	                    if (siglasCurso.equalsIgnoreCase(siglasTablaCursos)) {
	                        nombreCurso = (String) modeloCursos.getValueAt(k, 2);
	                        break;
	                    }
	                }
	                
	                double costoCurso = creditos * 10000;
	                totalCreditos += creditos;
	                costoTotalCreditos += costoCurso;
	                
	                resultado.append("• Curso: ").append(nombreCurso).append(" (").append(siglasCurso).append(")\n");
	                resultado.append("  Créditos: ").append(creditos).append("\n");
	                resultado.append("  Costo por créditos: ¢").append((int)costoCurso).append("\n\n");
	            }
	        }
	        
	        // Cálculos finales
	        double subtotal = costoTotalCreditos + 15000;
	        double totalConRecargo = esExtranjero ? subtotal * 1.4 : subtotal;
	        
	        // Aplicar porcentaje de beca solo a estudiantes nacionales
	        double descuentoBeca = 0;
	        double totalFinal = totalConRecargo;
	        
	        if (!esExtranjero) {
	            try {
	                String porcentajeLimpio = porcentajeBeca.replace("%", "");
	                double porcentaje = Double.parseDouble(porcentajeLimpio) / 100.0;
	                descuentoBeca = totalConRecargo * porcentaje;
	                totalFinal = totalConRecargo - descuentoBeca;
	            } catch (NumberFormatException e) {
	                // Si no se puede convertir, no aplicar descuento
	            }
	        }
	        
	        resultado.append("RESUMEN DE PAGO:\n");
	        resultado.append("Total de créditos: ").append(totalCreditos).append("\n");
	        resultado.append("Costo total por créditos: ¢").append((int)costoTotalCreditos).append("\n");
	        resultado.append("Cargos administrativos: ¢15,000\n");
	        
	        if (esExtranjero) {
	            resultado.append("Recargo extranjero (40%): ¢").append((double)(subtotal * 0.4)).append("\n");
	            resultado.append("Porcentaje de beca: No aplica\n");
	            resultado.append("TOTAL A PAGAR: ¢").append((double)totalFinal).append("\n");
	        } else {
	        	resultado.append("Porcentaje de beca: ").append(porcentajeBeca).append("\n");
                resultado.append("Descuento por beca: ¢").append((double)descuentoBeca).append("\n");
                resultado.append("TOTAL A PAGAR: ¢").append((double)totalFinal).append("\n");
	        }
	        
	        resultado.append("══════════════════════════════\n\n");
	    }
	    
	    studentView.txtAreaPagoCreditos.setText(resultado.toString());
	}
	
	

	private void buscarCursosPorEstudianteActionListener() {
		studentView.btnBuscarCursosPorCedula.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarCursosPorEstudiante();
			}
		});
	}

	public void buscarCursosPorEstudiante() {
		String cedula = studentView.txtBuscarCursos.getText().trim();

		if (cedula.isEmpty()) {
			JOptionPane.showMessageDialog(studentView.panelBusquedaCursos, "Debe ingresar la cédula del estudiante.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		DefaultTableModel modeloMatriculas = (DefaultTableModel) studentView.tablaMatriculas.getModel();
		DefaultTableModel modeloCursos = (DefaultTableModel) mainView.tablaCursos.getModel();

		StringBuilder resultado = new StringBuilder();
		boolean encontrados = false;

		for (int i = 0; i < modeloMatriculas.getRowCount(); i++) {
			String cedulaMatriculada = modeloMatriculas.getValueAt(i, 1).toString();

			if (cedula.equals(cedulaMatriculada)) {
				String grupo = modeloMatriculas.getValueAt(i, 3).toString();
				String sigla = modeloMatriculas.getValueAt(i, 4).toString();

				String nombreCurso = "Desconocido";
				String escuela = "Desconocida";

				for (int j = 0; j < modeloCursos.getRowCount(); j++) {
					if (modeloCursos.getValueAt(j, 1).toString().equalsIgnoreCase(sigla)) {
						escuela = modeloCursos.getValueAt(j, 0).toString();
						nombreCurso = modeloCursos.getValueAt(j, 2).toString();
						break;
					}
				}

				resultado.append("Curso: ").append(nombreCurso).append(" | Sigla: ").append(sigla).append(" | Grupo: ")
						.append(grupo).append(" | Escuela: ").append(escuela)
						.append("\n------------------------------------------------------------------------------------------------------------------------------\n");
				encontrados = true;
			}
		}

		if (encontrados) {
			JOptionPane.showMessageDialog(studentView.panelBusquedaCursos, "Cursos encontrados.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			resultado.append("Este estudiante no está matriculado en ningún curso.\n");
			JOptionPane.showMessageDialog(studentView.panelBusquedaCursos, "No se encontraron cursos para esta cédula.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		studentView.showTextAreaCursos.setText(resultado.toString());
	}

	private void buscarEstudiantesMatriculadosPorSiglaActionListener() {
		studentView.btnBuscarEstudiantesMatriculados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarEstudiantesMatriculadosPorSigla();
			}
		});

	} 

	public void buscarEstudiantesMatriculadosPorSigla() {
		String siglaCurso = studentView.txtBuscarEstudiantesMatriculados.getText().trim();

		if (siglaCurso.isEmpty()) {
			JOptionPane.showMessageDialog(studentView.panelConsultaEstudiantesMatriculados,
					"Debe ingresar la sigla del curso.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		DefaultTableModel modeloMatriculas = (DefaultTableModel) studentView.tablaMatriculas.getModel();
		DefaultTableModel modeloEstudiantes = (DefaultTableModel) studentView.tablaEstudiantes.getModel();

		StringBuilder resultado = new StringBuilder();
		boolean encontrados = false;

		for (int i = 0; i < modeloMatriculas.getRowCount(); i++) {
			String siglaMatriculada = modeloMatriculas.getValueAt(i, 4).toString();

			if (siglaMatriculada.equalsIgnoreCase(siglaCurso)) {
				String cedula = modeloMatriculas.getValueAt(i, 1).toString();

				for (int j = 0; j < modeloEstudiantes.getRowCount(); j++) {
					String cedulaEst = modeloEstudiantes.getValueAt(j, 2).toString();
					if (cedulaEst.equals(cedula)) {
						String nombre = modeloEstudiantes.getValueAt(j, 0).toString();
						String apellidos = modeloEstudiantes.getValueAt(j, 1).toString();

						resultado.append("Nombre: ").append(nombre).append(" ").append(apellidos).append(" | Cédula: ")
								.append(cedula).append("\n").append("--------------------------------------------------------------------------------\n");
						encontrados = true;
						break;
					}
				}
			}
		}

		if (encontrados) {
			JOptionPane.showMessageDialog(studentView.panelConsultaEstudiantesMatriculados, "Estudiantes encontrados.",
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
		} else {
			resultado.append("No hay estudiantes matriculados en ese curso.\n");
			JOptionPane.showMessageDialog(studentView.panelConsultaEstudiantesMatriculados,
					"No se encontraron estudiantes.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		studentView.txtAreaEstudiantesMatriculados.setText(resultado.toString());
	}
	///METODOS JSON
	
	public void escribirDataEstudiantesSinMatricular() {
	    DefaultTableModel modelo = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
	    List<Map<String, Object>> estudiantes = new ArrayList<>();

	    for (int i = 0; i < modelo.getRowCount(); i++) {
	        try {
	            String nombre = modelo.getValueAt(i, 0).toString();
	            String apellidos = modelo.getValueAt(i, 1).toString();
	            String id = modelo.getValueAt(i, 2).toString(); // Cédula
	            String carnet = modelo.getValueAt(i, 3).toString();
	            String nacionalidad = modelo.getValueAt(i, 4).toString();
	            String porcentajeRaw = modelo.getValueAt(i, 5).toString();

	            Map<String, Object> estudiante = new LinkedHashMap<>();
	            estudiante.put("varName", nombre);
	            estudiante.put("varLastnames", apellidos);
	            estudiante.put("varId", id);
	            estudiante.put("varCarnet", carnet);
	            estudiante.put("varNationality", nacionalidad);

	            if (nacionalidad.equalsIgnoreCase("Nacional")) {
	                double porcentaje = Double.parseDouble(porcentajeRaw.replace("%", "").trim());
	                estudiante.put("varScholarshipPercentage", porcentaje + "%");
	            } else {
	                estudiante.put("varScholarshipPercentage", "No aplica");
	            }

	            estudiantes.add(estudiante);

	        } catch (Exception e) {
	            System.err.println("Error procesando fila " + i + ": " + e.getMessage());
	        }
	    }

	    Gson gson = new GsonBuilder().setPrettyPrinting().create();

	    try {
	        String json = gson.toJson(estudiantes);
	        Files.write(Paths.get("EstudiantesSinMatricula.json"), json.getBytes(StandardCharsets.UTF_8));
	        System.out.println("Datos guardados correctamente en EstudiantesSinMatricula.json");
	    } catch (IOException e) {
	        System.err.println("Error al guardar el archivo JSON:");
	        e.printStackTrace();
	    }
	}
	

	public void cargarEstudiantesDesdeJson() {
	    try {
	        Path path = Paths.get("EstudiantesSinMatricula.json");
	        if (!Files.exists(path)) return;

	        String contenido = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	        Gson gson = new Gson();

	        // Crear lista genérica con Map<String, String> para evitar usar clases DTO
	        Type tipoLista = new TypeToken<List<Map<String, String>>>() {}.getType();
	        List<Map<String, String>> lista = gson.fromJson(contenido, tipoLista);

	        DefaultTableModel modelo = (DefaultTableModel) studentView.tablaEstudiantes.getModel();
	        modelo.setRowCount(0);
	        estudiantesList.clear();

	        for (Map<String, String> mapa : lista) {
	            String nombre = mapa.get("varName");
	            String apellidos = mapa.get("varLastnames");
	            String cedula = mapa.get("varId");
	            String carnet = mapa.get("varCarnet");
	            String nacionalidad = mapa.get("varNationality");
	            String porcentajeBeca = mapa.get("varScholarshipPercentage");

	            if (nacionalidad.equalsIgnoreCase("Nacional")) {
	                double porcentaje = Double.parseDouble(porcentajeBeca.replace("%", "").trim());
	                StudentNational estudiante = new StudentNational(nombre, apellidos, cedula, carnet, nacionalidad, porcentaje);
	                estudiantesList.add(estudiante);
	                modelo.addRow(new Object[]{nombre, apellidos, cedula, carnet, nacionalidad, porcentaje + "%"});
	            } else {
	                StudentForeign estudiante = new StudentForeign(nombre, apellidos, cedula, carnet, nacionalidad);
	                estudiantesList.add(estudiante);
	                modelo.addRow(new Object[]{nombre, apellidos, cedula, carnet, nacionalidad, "No aplica"});
	            }
	        }

	        // Activar botones como si hubieras agregado estudiantes manualmente
	        studentView.btnModificarEstudiante.setEnabled(true);
	        studentView.btnEliminarEstudiante.setEnabled(true);
	        studentView.btnDeseleccionarTabla.setEnabled(true);

	        JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Estudiantes cargados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(studentView.estudiantesPanel, "Error al cargar estudiantes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	public void guardarMatriculasEnJSON() {
	    // Obtener el modelo de la tabla
	    DefaultTableModel modelo = (DefaultTableModel) studentView.tablaMatriculas.getModel();
	    
	    // Lista donde guardaremos los datos como mapas
	    List<Map<String, Object>> estudiantesMatriculados = new ArrayList<>();

	    // Recorrer filas de la tabla
	    for (int i = 0; i < modelo.getRowCount(); i++) {
	        Map<String, Object> fila = new HashMap<>();
	        
	        fila.put("varNombreEscuela", modelo.getValueAt(i, 0));
	        fila.put("varCedulaEstudiante", modelo.getValueAt(i, 1));
	        fila.put("varCedulaProfesor", modelo.getValueAt(i, 2));
	        fila.put("varGrupo", modelo.getValueAt(i, 3));
	        fila.put("varSiglasCurso", modelo.getValueAt(i, 4));
	        fila.put("varCreditos", modelo.getValueAt(i, 5));

	        estudiantesMatriculados.add(fila);
	    }

	    // Convertir a JSON
	    Gson gson = new Gson();
	    String json = gson.toJson(estudiantesMatriculados);

	    // Guardar en archivo
	    try {
	        Files.write(Paths.get("Matriculas.json"), json.getBytes());
	        System.out.println("Archivo JSON guardado correctamente.");
	    } catch (IOException e) {
	        System.err.println("Error al guardar el archivo JSON:");
	        e.printStackTrace();
	    }
	}
	
	public void cargarDatosMatriculados() {
	    String rutaArchivo = "Matriculas.json";

	    try {
	        // Leer el archivo JSON
	        String json = new String(Files.readAllBytes(Paths.get(rutaArchivo)));

	        // Usamos Gson para convertir el JSON a una lista de mapas
	        Gson gson = new Gson();
	        List<Map<String, Object>> datosJSON = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>(){}.getType());

	        if (datosJSON == null) {
	            System.err.println("El archivo JSON está vacío o con formato incorrecto.");
	            return;
	        }

	        // Obtener el modelo de la tabla
	        DefaultTableModel modelo = (DefaultTableModel) studentView.tablaMatriculas.getModel();

	        // Limpiar filas existentes (opcional)
	        modelo.setRowCount(0);

	        // Agregar las filas al modelo de la tabla
	        for (Map<String, Object> fila : datosJSON) {
	        	Object[] filaTabla = new Object[] {
        		    fila.get("varNombreEscuela"),
        		    fila.get("varCedulaEstudiante"),
        		    fila.get("varCedulaProfesor"),
        		    fila.get("varGrupo"),
        		    fila.get("varSiglasCurso"), 
        		    ((Number) fila.get("varCreditos")).intValue()  // Subirlo a la tabla como entero
        		};;
	            modelo.addRow(filaTabla);
	        }

	        System.out.println("Datos cargados correctamente desde " + rutaArchivo);

	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo JSON:");
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.err.println("Error al procesar el archivo JSON:");
	        e.printStackTrace();
	    }
	}
	


}
