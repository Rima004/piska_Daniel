package org.example.probs.objects;


public class Department {
    private int id;
    private String name;

    private Employee manager;


    public Department()
    {
        this.id=0;
        this.name="unknown";
        this.manager=null;
    }

    Department(int id, String name, Employee manager)
    {
        this.id=id;
        this.name=name;
        this.manager=manager;
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
        return "Name department" + name;
    }

}

