package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

public class ViewRegistroDeCursos extends JFrame {

	private JLabel varSiglasNom;
	private JLabel varDescripcionNom;
	private JLabel varNombreEscuela;
	private JLabel varOpciones;
	public JTextField varSigla;
	public JTextField varDescripcion;
	public JTextField varEscuelaNombres;
	public JButton varBotonRegistrar;
	public JButton varBotonEliminar;
	public JButton varBotonModificar;
	public JButton varBotonBuscarPorEscuela;
	
	
	public ViewRegistroDeCursos() {
		
	    setTitle("Registrar Cursos");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Datos del Curso"));

        JLabel varNombreEscuela = new JLabel("Nombre de la Escuela:");
        varEscuelaNombres = new JTextField();
        JLabel varSiglasNom = new JLabel("Siglas del Curso:");
        varSigla = new JTextField();
        JLabel varDescripcionNom = new JLabel("Descripción del Curso:");
        varDescripcion = new JTextField();

        inputPanel.add(varNombreEscuela);
        inputPanel.add(varEscuelaNombres);
        inputPanel.add(varSiglasNom);
        inputPanel.add(varSigla);
        inputPanel.add(varDescripcionNom);
        inputPanel.add(varDescripcion);

        // Botón Registrar Curso
        varBotonRegistrar = new JButton("Registrar Curso");
        varBotonRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
       
        varBotonRegistrar.setFocusPainted(false);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(varBotonRegistrar);

        // Panel de opciones adicionales
        JPanel optionsPanel = new JPanel(new BorderLayout(5, 5));
        JLabel varOpciones = new JLabel("Más Opciones, Funcionan ingresando las Siglas");
        varOpciones.setFont(new Font("Segoe UI", Font.BOLD, 14));
        varOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        optionsPanel.add(varOpciones, BorderLayout.NORTH);

        JPanel additionalButtonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        varBotonEliminar = new JButton("Eliminar Curso");
        varBotonModificar = new JButton("Modificar Curso");
        varBotonBuscarPorEscuela = new JButton("Buscar Por Escuela");

        JButton[] botones = { varBotonEliminar, varBotonModificar, varBotonBuscarPorEscuela };
        for (JButton btn : botones) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
           
            btn.setFocusPainted(false);
            additionalButtonsPanel.add(btn);
        }

        optionsPanel.add(additionalButtonsPanel, BorderLayout.CENTER);

        // Agregar al panel principal
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(optionsPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(false);
	}
	
	

	
	
	
	
	
	
	
	
	
}
