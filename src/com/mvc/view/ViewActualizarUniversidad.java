package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewActualizarUniversidad extends JFrame {
	
	
	private static final long serialVersionUID = 1L;

	private JPanel pUpdateUniversity; 
	
	private JLabel lblNewAdrees;
	private JLabel lblNewPhone;
	
	private JTextField txtNewAdrees;
	private JTextField txtNewPhone;
	
	private JButton btnUpdateUniversity;
	
	public ViewActualizarUniversidad() {
		pUpdateUniversity = new JPanel();
		pUpdateUniversity.setLayout(new BoxLayout(pUpdateUniversity,BoxLayout.Y_AXIS));
		pUpdateUniversity.setPreferredSize(new Dimension(200,getHeight()));
		pUpdateUniversity.setBorder(BorderFactory.createTitledBorder("Actualizar Universidad"));
		
		lblNewAdrees = new JLabel ("Nueva Direccion");
		txtNewAdrees = new JTextField(20);
		
		lblNewPhone = new JLabel ("Nuevo celularo");
		txtNewPhone = new JTextField(20);
		
		btnUpdateUniversity = new JButton("Actualizar datos de la universidad");
		
		pUpdateUniversity.add(lblNewAdrees);
		pUpdateUniversity.add(txtNewAdrees);
		pUpdateUniversity.add(lblNewPhone);
		pUpdateUniversity.add(txtNewPhone);
		pUpdateUniversity.add(btnUpdateUniversity);
		
		// Agregar el panel al JFrame
		add(pUpdateUniversity, BorderLayout.WEST);
		
		// Ajustar configuración del JFrame
		setTitle("Actualizar Universidad");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		

	}
	
	

}
