package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.mvc.models.Cursos;
import com.mvc.models.Escuela;
import com.mvc.models.University;
import com.mvc.view.ViewActualizarUniversidad;
import com.mvc.view.ViewConsultarCursosDeEscuelas;
import com.mvc.view.ViewConsultarEscuelas;
import com.mvc.view.ViewRegistrarEscuelas;
import com.mvc.view.ViewRegistrarUniversidad;
import com.mvc.view.ViewRegistroDeCursos;

public class Control {

	private ViewRegistrarUniversidad vRegistrarUniversidad;
	private ViewRegistrarEscuelas vRegistrarEscuela;
	private ViewActualizarUniversidad vActualizarUniversidad;
	private ViewConsultarEscuelas vConsultarEscuelas;
	private University varUniversidadRegistrada;
	private Cursos varCursosRegistrar;

	// lo hecho por jeferson 
	private ViewConsultarCursosDeEscuelas varConsultasCursos;
	private ViewRegistroDeCursos varRegistroCursos;
	
	public Control(ViewRegistrarUniversidad pRegistrarUniversidad) {
		this.vRegistrarUniversidad = pRegistrarUniversidad;
		vRegistrarUniversidad.setVisible(true);
		
		 this.varRegistroCursos= new ViewRegistroDeCursos();
		 this.varConsultasCursos = new ViewConsultarCursosDeEscuelas(); // Una sola instancia
		 varConsultasCursos.setVisible(false); // No visible al inicio
		 agregarCursosActionListener();
		agregarControladorUniversidad();
	}

	private void agregarControladorUniversidad() {
		vRegistrarUniversidad.btnRegisterUniversity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarUniversidad();
			}
		});
	}
	private void agregarCursosActionListener() {
		//reinicio de listeners
		varRegistroCursos.varBotonRegistrar.removeActionListener(null);
		
		varRegistroCursos.varBotonRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarCursos();
			}
		});
	}
	public void agregarCursos() {
		
		String varSiglasCurso = varRegistroCursos.varSigla.getText().trim();
		String varDescipcionDeCursos = varRegistroCursos.varDescripcion.getText().trim();
		String varNombreEscuelas =varRegistroCursos.varEscuelaNombres.getText().trim();
		
		if(!varSiglasCurso.isEmpty()&&!varDescipcionDeCursos.isEmpty()&&!varNombreEscuelas.isEmpty()) {
			
			String contenido = varNombreEscuelas.toLowerCase().trim();
			System.out.println("Contenido del JTextArea: " + contenido);
		    String[] lineas = contenido.split("\n");

		  
		    for (String linea : lineas) {
		        // Eliminamos el n�mero y los dos puntos al inicio de la l�nea (por ejemplo, "1:")
		        String nombreEscuelaEnLinea = linea.replaceAll("^[0-9]+:", "").trim();

		        if (nombreEscuelaEnLinea.equalsIgnoreCase(varNombreEscuelas)) {
		        	varCursosRegistrar = new Cursos(varSiglasCurso,varDescipcionDeCursos,varNombreEscuelas);
		        	Object[][] datos = {
		                    {varNombreEscuelas, varSiglasCurso,varDescipcionDeCursos }
		                
		                };
		        	varConsultasCursos.setVisible(true);
		        	varConsultasCursos.agregarDatosTabla(datos);
		        	
		         JOptionPane.showMessageDialog(varRegistroCursos, "Curso registrado exitosamente", "�xito",  JOptionPane.INFORMATION_MESSAGE);
		         break;
		        
		        }else {
		        	JOptionPane.showMessageDialog(varRegistroCursos, "Nose encontro la Escuela",  "Advertencia", JOptionPane.WARNING_MESSAGE);
		        	return;
		        
		        }
		        
		    }
		    
		}else {
		
			JOptionPane.showMessageDialog( varRegistroCursos, "Todos los campos son obligatorios",  "Advertencia", JOptionPane.WARNING_MESSAGE);
		}
		

	  
		
		
	}
	void limpiarPanelCurso() {
		if (varRegistroCursos != null) {
			varRegistroCursos.varSigla.setText("");
			varRegistroCursos.varDescripcion.setText("");
			varRegistroCursos.varEscuelaNombres.setText("");
		}

	}
	private void agregarUniversidad() {
		String varNombre = vRegistrarUniversidad.txtName.getText().trim();
		String varDireccion = vRegistrarUniversidad.txtAdress.getText().trim();
		String varTelefono = vRegistrarUniversidad.txtPhoneNumber.getText().trim();

		if (!varNombre.isEmpty() && !varDireccion.isEmpty() && !varTelefono.isEmpty()) {
			varUniversidadRegistrada = new University(varNombre, varDireccion, varTelefono);

			JOptionPane.showMessageDialog(vRegistrarUniversidad, "Universidad registrada: " + varNombre, "�xito", JOptionPane.INFORMATION_MESSAGE);

			vRegistrarUniversidad.dispose();

			vActualizarUniversidad = new ViewActualizarUniversidad(varNombre, varDireccion, varTelefono);
			vRegistrarEscuela = new ViewRegistrarEscuelas(varNombre);
			vConsultarEscuelas = new ViewConsultarEscuelas(varNombre);

			agregarControladorActualizar();
			agregarControladorEscuela();

		} else {
			JOptionPane.showMessageDialog(vRegistrarUniversidad, "Todos los campos son obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void agregarControladorActualizar() {
		vActualizarUniversidad.btnUpdateUniversity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarUniversidad();
			}
		});
	}

	private void actualizarUniversidad() {
		if (varUniversidadRegistrada != null) {
			String nuevaDireccion = vActualizarUniversidad.txtNewAdress.getText().trim();
			String nuevoTelefono = vActualizarUniversidad.txtNewPhone.getText().trim();

			if (!nuevaDireccion.isEmpty() && !nuevoTelefono.isEmpty()) {
				varUniversidadRegistrada.actualizarDatos(nuevaDireccion, nuevoTelefono);

				JOptionPane.showMessageDialog(vActualizarUniversidad, "Datos actualizados correctamente", "�xito", JOptionPane.INFORMATION_MESSAGE);

				limpiarCampos();

			} else {
				JOptionPane.showMessageDialog(vActualizarUniversidad, "Todos los campos deben estar completos", "Error", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(vActualizarUniversidad, "Primero se debe registrar una universidad", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void agregarControladorEscuela() {
		vRegistrarEscuela.btnRegisterSchool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarEscuela();
				
	            varRegistroCursos.setVisible(true);
	            // Agregar el controlador para el bot�n de registro de cursos
	            
			}
		});
	}

	private void agregarEscuela() {
		if (varUniversidadRegistrada != null) {
			String nombreEscuela = vRegistrarEscuela.txtNameSchool.getText().trim();

			if (!nombreEscuela.isEmpty()) {
				Escuela nuevaEscuela = new Escuela(nombreEscuela);
				varUniversidadRegistrada.agregarEscuela(nuevaEscuela);

				if (vConsultarEscuelas != null) {
					agregarEscuelasTxtArea();
				}

				limpiarCampos();

			} else {
				JOptionPane.showMessageDialog(vRegistrarEscuela, "El nombre de la escuela es obligatorio", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void agregarEscuelasTxtArea() {
		if (varUniversidadRegistrada != null && varUniversidadRegistrada.getEscuelas() != null) {
			
			StringBuilder lista = new StringBuilder();
			int contador = 0;
			for (Escuela esc : varUniversidadRegistrada.getEscuelas()) {
				contador++;
				lista.append(contador+":").append(esc.getVarName()).append("\n");
			}
			vConsultarEscuelas.txtAreaEscuelas.setText(lista.toString());
		}
	}

	private void limpiarCampos() {
		if (vRegistrarUniversidad != null) {
			vRegistrarUniversidad.txtName.setText("");
			vRegistrarUniversidad.txtAdress.setText("");
			vRegistrarUniversidad.txtPhoneNumber.setText("");
		}

		if (vActualizarUniversidad != null) {
			vActualizarUniversidad.txtNewAdress.setText("");
			vActualizarUniversidad.txtNewPhone.setText("");
		}

		if (vRegistrarEscuela != null) {
			vRegistrarEscuela.txtNameSchool.setText("");
		}
		 limpiarPanelCurso();
		
	}
}
