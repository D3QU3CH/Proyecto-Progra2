package com.mvc.init;
import com.mvc.controls.Controller;
import com.mvc.view.MainView;

public class Main {

    public static void main(String[] args) {
        MainView mainView = new MainView();
        new Controller(mainView);
    }
}