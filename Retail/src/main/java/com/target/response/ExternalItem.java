package com.target.response;

public class ExternalItem {

	String dpci;
    int departmentId;
    int classId;
    int itemId;
    String itemDescription;

   
    public String getDpci() {
        return dpci;
    }

    public void setItemDescription(String desc) {
        this.itemDescription = desc;
    }
    
    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setDpci(String dpci) {
        this.dpci = dpci;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int id) {
        this.departmentId = id;
    }
    public int getClassId() {
        return classId;
    }

    public void setClassId(int id) {
        this.classId = id;
    }
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int id) {
        this.itemId = id;
    }

   
}