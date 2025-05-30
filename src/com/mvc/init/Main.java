package com.mvc.init;

import com.mvc.controls.Controller;
import com.mvc.view.MainView;
import com.mvc.view.StudentView;

public class Main {

    public static void main(String[] args) {
        MainView mainView = new MainView();
        StudentView studentView = new StudentView();
        new Controller(mainView, studentView);
    }
}