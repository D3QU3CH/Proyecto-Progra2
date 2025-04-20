package com.mvc.models;

public class Cursos {

    private String varCode;
    private String varDescription;
    private String varSchoolName;

    public Cursos(String pCode, String pDescription, String pSchoolName) {
        this.varCode = pCode;
        this.varDescription = pDescription;
        this.varSchoolName = pSchoolName;
    }

    public String getVarCode() {
        return varCode;
    }

    public void setVarCode(String pCode) {
        this.varCode = pCode;
    }

    public String getVarDescription() {
        return varDescription;
    }

    public void setVarDescription(String pDescription) {
        this.varDescription = pDescription;
    }

    public String getVarSchoolName() {
        return varSchoolName;
    }

    public void setVarSchoolName(String pSchoolName) {
        this.varSchoolName = pSchoolName;
    }
}
