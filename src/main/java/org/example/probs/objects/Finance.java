package org.example.probs.objects;

import java.time.LocalDate;

public class Finance {
    private String Name_employee;
    private String Surname;
    private float Commission;
    private int contracts;
    private float income;
    private float average_income;
    private float max_income;

    public Finance() {

    }

    public Finance(String Name_employee, String Surname, float Commission, int contracts, float income,
                   float average_income, float max_income) {

        this.Name_employee = Name_employee;
        this.Surname = Surname;
        this.Commission = Commission;
        this.contracts = contracts;
        this.income = income;
        this.average_income = average_income;
        this.max_income = max_income;
    }


    public String getName_employee() {
        return Name_employee;
    }

    public void setName_employee(String name_employee) {
        Name_employee = name_employee;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public float getCommission() {
        return Commission;
    }

    public void setCommission(float commission) {
        Commission = commission;
    }

    public int getContracts() {
        return contracts;
    }

    public void setContracts(int contracts) {
        this.contracts = contracts;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getAverage_income() {
        return average_income;
    }

    public void setAverage_income(float average_income) {
        this.average_income = average_income;
    }

    public float getMax_income() {
        return max_income;
    }

    public void setMax_income(float max_income) {
        this.max_income = max_income;
    }
}
