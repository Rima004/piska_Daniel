package org.example.probs.Services;

import org.example.probs.objects.Finance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFinance {
    Connection connection;
    ResultSet resSet = null;


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
        String getContract = "SELECT * FROM finances";
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

    public List<Finance> filterByName(String name) {
        try {
            List<Finance> finances = new ArrayList<>();
            String query = "SELECT * FROM finances";
            if (!name.isEmpty()) {
                query += " WHERE Name_employee like ? OR Surname like ?";
            }
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            if (!name.isEmpty()) {
                prST.setString(1, "%" + name + "%");
                prST.setString(2, "%" + name + "%");
            }

            ResultSet reSt = prST.executeQuery();
            while (reSt.next()) {
                Finance finance = new Finance();
                finance.setName_employee(reSt.getString(1));
                finance.setSurname(reSt.getString(2));
                finance.setCommission(reSt.getFloat(3));
                finance.setContracts(reSt.getInt(4));
                finance.setIncome(reSt.getFloat(5));
                finance.setAverage_income(reSt.getFloat(6));
                finance.setMax_income(reSt.getFloat(7));
                finances.add(finance);
            }
            reSt.close();
            return finances;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
