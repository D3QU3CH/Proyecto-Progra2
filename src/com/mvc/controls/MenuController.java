package com.mvc.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mvc.view.MainView;

public class MenuController {

    private MainView varMainView;

    public MenuController(MainView pMainView) {
        this.varMainView = pMainView;
        setupMenuListeners();
    }

    private void setupMenuListeners() {
        varMainView.setupMenuListeners(
            // Listener for the University button
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    varMainView.showPanel("UNIVERSITY");
                }
            },
            // Listener for the Schools button
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    varMainView.showPanel("SCHOOLS");
                }
            },
            // Listener for the Courses button
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    varMainView.showPanel("COURSES");
                }
            },
            // Listener for the Search by School button
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    varMainView.showPanel("SEARCH");
                }
            }
        );
    }
}