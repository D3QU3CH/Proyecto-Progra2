package com.mvc.init;


import com.mvc.controls.MenuController;
import com.mvc.controls.UniversityController;
import com.mvc.controls.SchoolController;
import com.mvc.controls.CourseController;
import com.mvc.view.ViewMenu;

public class Main {
    public static void main(String[] args) {
        ViewMenu viewMenu = new ViewMenu();
        
        UniversityController universityController = new UniversityController();
        SchoolController schoolController = new SchoolController();
        CourseController courseController = new CourseController();
        
        MenuController menuController = new MenuController(viewMenu);
        menuController.setUniversityController(universityController);
        menuController.setSchoolController(schoolController);
        menuController.setCourseController(courseController);
        
        viewMenu.setVisible(true);
    }
}