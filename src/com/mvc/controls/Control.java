package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import com.mvc.models.Escuela;
import com.mvc.models.University;
import com.mvc.view.ViewActualizarUniversidad;
import com.mvc.view.ViewConsultarEscuelas;
import com.mvc.view.ViewRegistrarEscuelas;
import com.mvc.view.ViewRegistrarUniversidad;

public class Control {

	private ViewRegistrarUniversidad vRegistrarUniversidad;
	private ViewRegistrarEscuelas vRegistrarEscuela;
	private ViewActualizarUniversidad vActualizarUniversidad;
	private ViewConsultarEscuelas vConsultarEscuelas;
	private University varUniversidadRegistrada;

	public Control(ViewRegistrarUniversidad pRegistrarUniversidad) {
		this.vRegistrarUniversidad = pRegistrarUniversidad;
		vRegistrarUniversidad.setVisible(true);

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

	private void agregarUniversidad() {
		String varNombre = vRegistrarUniversidad.txtName.getText().trim();
		String varDireccion = vRegistrarUniversidad.txtAdress.getText().trim();
		String varTelefono = vRegistrarUniversidad.txtPhoneNumber.getText().trim();

		if (!varNombre.isEmpty() && !varDireccion.isEmpty() && !varTelefono.isEmpty()) {
			varUniversidadRegistrada = new University(varNombre, varDireccion, varTelefono);

			JOptionPane.showMessageDialog(vRegistrarUniversidad, "Universidad registrada: " + varNombre, "Éxito", JOptionPane.INFORMATION_MESSAGE);

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

				JOptionPane.showMessageDialog(vActualizarUniversidad, "Datos actualizados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

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
	}
}
