package org.example.probs.Services;

import javafx.scene.control.ComboBox;
import org.example.probs.objects.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceContract {

    Connection connection;
    ResultSet resSet = null;

    private final ServiceEmployee dbEmployee = new ServiceEmployee();
    private final ServiceClient dbHandler = new ServiceClient();
    private final ServiceProduct dbProduct = new ServiceProduct();
    public Connection getDBConnection() {
        String Connection = "jdbc:mysql://localhost:3306/it-company";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(Connection, "root", "Rtiop#iti567");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public ResultSet Show() {
        String getContract =  "SELECT * " +
                "FROM contract";
        PreparedStatement prST = null;
        try {
            prST = getDBConnection().prepareStatement(getContract);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            resSet = prST.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void Delete(int id_Contract) {
        String query = "DELETE FROM contract WHERE Number_contract = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id_Contract);
            prST.executeUpdate();
            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void Add(Contract contract) {
        String insertProduct = "INSERT INTO contract (employee_id,product_id,client_id,data_contract)" +
                "VALUES(?,?,?,?)";
        try {
            LocalDate localDate = contract.getDate();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
            PreparedStatement prST = getDBConnection().prepareStatement(insertProduct);
            prST.setInt(1, contract.getEmployee().getId_emloyee());
            prST.setInt(2, contract.getProduct().getId_product());
            prST.setInt(3, contract.getClient().getId());
            prST.setDate(4, sqlDate);
            prST.executeUpdate();
            prST.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contract> filterByName(int name) {
        try {
            List<Contract> Contracts = new ArrayList<>();
            String query = "SELECT * FROM contract";
            if (name!=0) {
                query += " WHERE Number_contract = ? OR employee_id = ? OR product_id = ? OR client_id = ?";
            }
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            if (name!=0) {
                prST.setInt(1, name);
                prST.setInt(2, name);
                prST.setInt(3, name);
                prST.setInt(4, name);
            }

            ResultSet reSt = prST.executeQuery();
            while (reSt.next()) {
                Contract contract =new Contract();
                contract.setNumber_contract(reSt.getInt(1));
                ResultSet resEmployee  = dbEmployee.SerachEmployee(reSt.getInt(2));
                while (resEmployee.next()) {
                    Employee developer = new Employee();
                    developer.setId_emloyee(reSt.getInt(2));
                    developer.setName(resEmployee.getString(1));
                    developer.setSurname(resEmployee.getString(2));
                    contract.setEmployee(developer);
                }
                resEmployee.close();

                ResultSet resClient = dbHandler.SearchClient(reSt.getInt(4));
                if (resClient.next()) {
                    Client client1 = new Client();
                    client1.setId(reSt.getInt(4));
                    client1.setFirstName(resClient.getString(1));
                    client1.setLastName(resClient.getString(2));
                    contract.setClient(client1);
                }
                resClient.close();



                ResultSet res = dbProduct.SearchProduct(reSt.getInt(3));
                if (res.next()) {
                    Product product = new Product();
                    product.setId_product(reSt.getInt(3));
                    product.setName(res.getString(1));
                    product.setPrice(res.getFloat(2));
                    contract.setProduct(product);
                }
                res.close();
            }
            return Contracts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






}
