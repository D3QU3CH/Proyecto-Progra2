package com.mvc.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ViewMenu extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JLabel lblTitulo;
    
    public JButton btnUniversidad;
    public JButton btnEscuela;
    public JButton btnCurso;
    
    public ViewMenu() {
        setTitle("Sistema de Gestión Universitaria");
        setSize(800, 128);
        //setLocationRelativeTo(null);
        setLocation(284,50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelSuperior.setBackground(new Color(240, 240, 240));

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(new Color(240, 240, 240));
        lblTitulo = new JLabel("Menú Principal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        panelTitulo.add(lblTitulo);
        
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(new Color(240, 240, 240));
        
        btnUniversidad = new JButton("Universidad");
        btnUniversidad.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnUniversidad.setPreferredSize(new Dimension(120, 30));
        
        btnEscuela = new JButton("Escuelas");
        btnEscuela.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEscuela.setPreferredSize(new Dimension(120, 30));
        
        btnCurso = new JButton("Cursos");
        btnCurso.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCurso.setPreferredSize(new Dimension(120, 30));
        
        panelBotones.add(btnUniversidad);
        panelBotones.add(btnEscuela);
        panelBotones.add(btnCurso);
        
        panelSuperior.add(panelTitulo);
        panelSuperior.add(panelBotones);
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        add(panelPrincipal);
        
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
        setVisible(true);
    }
}