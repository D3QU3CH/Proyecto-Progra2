package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class ViewConsultarEscuelas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    public JTextArea txtAreaEscuelas;
    private JScrollPane scrollPane;

    public ViewConsultarEscuelas(String universityName) {
        setTitle("Universidad: "+universityName);
        setSize(300, 300);
        setLocation(500,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal = new JPanel(new BorderLayout(10, 10));

        lblTitulo = new JLabel("Lista de las Escuelas:", JLabel.CENTER); 
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        txtAreaEscuelas = new JTextArea();
        txtAreaEscuelas.setEditable(false);
        txtAreaEscuelas.setFont(new Font("Courier New", Font.PLAIN, 14));
        txtAreaEscuelas.setBorder(BorderFactory.createEtchedBorder());

        scrollPane = new JScrollPane(txtAreaEscuelas);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        add(panelPrincipal);
        
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
        
        setVisible(true);
    }
}
