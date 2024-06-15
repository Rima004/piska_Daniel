package org.example.probs.objects;

import java.io.Serializable;

public class Employee implements Serializable {


    private int id_emloyee;
    private String name;
    private String Surname;
    private String address;
    private String phone;
    private Department department;

   public Employee() {
        id_emloyee = 0;
        name = "unknown";
        Surname="unknown";
        address = "unknown";
        phone = "unknown";
        department = null;
    }

    Employee(int id_emloyee, String name, Department department) {
        this.id_emloyee = id_emloyee;
        this.name = name;
        this.department = department;
    }

 public    Employee(String name, String Surname ,String address, String phone, Department department) {
        this.name = name;
        this.Surname=Surname;
        this.address = address;
        this.phone = phone;
        this.department = department;
    }

    public void setId_emloyee(int id_emloyee) {
        this.id_emloyee = id_emloyee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getSurname()
    { return this.Surname;}

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getId_emloyee() {
        return id_emloyee;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return getName();
    }

}

