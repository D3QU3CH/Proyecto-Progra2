package com.mvc.models;

public class Escuela {

    private String varName;

    public Escuela() {
    }

    public Escuela(String pName) {
        this.varName = pName;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String pName) {
        this.varName = pName;
    }

    public String toString() {
        return "School: " + varName;
    }
}