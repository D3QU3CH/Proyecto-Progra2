package com.mvc.init;
import com.mvc.controls.Control;
import com.mvc.view.MainView;

public class Main {

    public static void main(String[] args) {
        MainView mainView = new MainView();
        new Control(mainView);
    }
}