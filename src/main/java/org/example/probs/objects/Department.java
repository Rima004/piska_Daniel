package org.example.probs.objects;


import javafx.scene.control.ComboBox;

public class Department {
    private int id;
    private String name;

    private Employee manager;

    private ComboBox <Employee>ComboxManager;


    public Department()
    {
        this.id=0;
        this.name="unknown";
        this.manager=null;
    }

    public Department(String name)
    {
        this.name=name;
    }


    public Department(String name,int id)
    {
        this.name=name;
        this.id=id;

    }

public Department(int id, String name, Employee manager)
    {
        this.id=id;
        this.name=name;
        this.manager=manager;
        this.ComboxManager = new ComboBox<>();

    }

    public ComboBox<Employee> getComboxManager() {
        return ComboxManager;
    }

    public void setComboxManager(ComboBox<Employee> comboxManager) {
        ComboxManager = comboxManager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return this.name;
    }

}

