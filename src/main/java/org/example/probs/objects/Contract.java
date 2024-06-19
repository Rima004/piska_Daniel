package org.example.probs.objects;
import java.time.LocalDate;

public class Contract {

    private int Number_contract;
    private Employee employee;
    private Product product;
    private Client client;
    private LocalDate date;
    private float Summ;

   public Contract()
   {

   }

    public Contract(Employee employee, Product product, Client client, LocalDate date) {

        this.employee = employee;
        this.product = product;
        this.client = client;
        this.date = date;
    }



    public Contract(int number_contract, Employee employee, Product product, Client client, LocalDate date, float summ) {
        Number_contract = number_contract;
        this.employee = employee;
        this.product = product;
        this.client = client;
        this.date = date;
        Summ = summ;
    }

    public int getNumber_contract() {
        return Number_contract;
    }

    public void setNumber_contract(int number_contract) {
        Number_contract = number_contract;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getSumm() {
        return Summ;
    }

    public void setSumm(float summ) {
        Summ = summ;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "Number_contract=" + Number_contract +
                ", employee=" + employee +
                ", product=" + product +
                ", client=" + client +
                ", date=" + date +
                ", Summ=" + Summ +
                '}';
    }
}
