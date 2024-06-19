package org.example.probs.Services;

import org.example.probs.objects.Client;
import org.example.probs.objects.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct  implements  Service<Product> {
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
        String getProduct = "SELECT * FROM products";
        PreparedStatement prST = null;
        try {
            prST = getDBConnection().prepareStatement(getProduct);
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

    public void Add(Product product) {
        String insertProduct = "INSERT INTO products (ProductName,Price)" +
                "VALUES(?,?)";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(insertProduct);
            prST.setString(1, product.getName());
            prST.setFloat(2, product.getPrice());
            prST.executeUpdate();
            prST.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void Delete (int id) {
        String query = "DELETE FROM products WHERE idProducts = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id);
            prST.executeUpdate();
            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editDate(int id, String name_colm, String newInfo) {
        String query = "UPDATE products SET " + name_colm + " = ? WHERE idProducts = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setString(1, newInfo);
            prST.setInt(2, id);

            prST.executeUpdate();

            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void editDatePrice(int id, String name_colm, Float newInfo) {
        String query = "UPDATE products SET " + name_colm + " = ? WHERE idProducts = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setFloat(1, newInfo);
            prST.setInt(2, id);

            prST.executeUpdate();

            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Product> filterByName(String name) {
        try {
            List<Product> Products = new ArrayList<>();
            String query = "SELECT * FROM products";
            if (name.length() > 0) {
                query += " WHERE ProductName LIKE ?";
            }
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            if (name.length() > 0) {
                prST.setString(1, "%"+name+"%");
            }

            ResultSet reSt = prST.executeQuery();
            while (reSt.next()) {
                Products.add(new Product(
                        reSt.getInt(1),
                        reSt.getString(2),
                        reSt.getFloat(3)));
            }
            return Products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet SearchProduct(int id) {
        String query = "SELECT ProductName, Price FROM products WHERE idProducts = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id);
            ResultSet resSet = prST.executeQuery();
            return resSet;
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        }
    }




}
