package org.example.probs.objects;

public class Client {

    private int id;
    private String FirstName;
    private String LastName;
    private String phone;
    private String address;

    public Client()
    {

    }

   public Client( String name, String surname, String phone,String address) {
        this.FirstName = name;
        this.LastName = surname;
        this.phone=phone;
        this.address=address;
    }

    public Client(int id_client, String name, String surname, String phone, String address) {
        this.id = id_client;
        this.FirstName = name;
        this.LastName = surname;
        this.phone = phone;
        this.address = address;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return getFirstName() +" "+ getLastName();
    }


}
