
package org.example.probs.Services;

import javafx.collections.ObservableList;
import org.example.probs.objects.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient implements Service<Client> {

    Connection connection;
    ResultSet resSet = null;

    //Подключения
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

    //SELECT (Show info)
    @Override
    public ResultSet Show() {
        String getClinet = "SELECT * FROM clients";
        PreparedStatement prST = null;
        try {
            prST = getDBConnection().prepareStatement(getClinet);
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

    //ISERT CLIENTS
    public void Add(Client client) {

        String insertClinet = "INSERT INTO clients ( Name, Surname, Phnoe, Address)" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(insertClinet);
            prST.setString(1, client.getFirstName());
            prST.setString(2, client.getLastName());
            prST.setString(3, client.getPhone());
            prST.setString(4, client.getAddress());
            prST.executeUpdate();
            prST.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //delete
    public void Delete(int id_clinet) {
        String query = "DELETE FROM Clients WHERE idClients = ?";
        try {
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            prST.setInt(1, id_clinet);
            prST.executeUpdate();
            prST.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editDate(int id, String name_colm, String newInfo) {
        String query = "UPDATE Clients SET " + name_colm + " = ? WHERE idClients = ?";
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
    public List<Client> filterByName(String name) {
        try {
            List<Client> Clients = new ArrayList<>();
            String query = "SELECT * FROM Clients";
            if (name.length() > 0) {
                query += " WHERE name LIKE ? OR surname LIKE ? OR phnoe LIKE ? OR address LIKE ?";
            }
            PreparedStatement prST = getDBConnection().prepareStatement(query);
            if (name.length() > 0) {
                prST.setString(1, "%"+name+"%");
                prST.setString(2, "%"+name+"%");
                prST.setString(3, "%"+name+"%");
                prST.setString(4, "%"+name+"%");
            }

            ResultSet reSt = prST.executeQuery();
            while (reSt.next()) {
                Clients.add(new Client(
                        reSt.getInt(1),
                        reSt.getString(2),
                        reSt.getString(3),
                        reSt.getString(4),
                        reSt.getString(5)));
            }
            return Clients;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
