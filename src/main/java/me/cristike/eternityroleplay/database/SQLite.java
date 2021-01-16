package me.cristike.eternityroleplay.database;

import me.cristike.eternityroleplay.Main;
import me.cristike.eternityroleplay.objects.Character;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class SQLite {
    private Connection connection;
    private Statement statement;

    public SQLite() {
        connect();
        createTables();
        loadCharacters();
    }

    private void connect() {
        connection = null;

        try {
            File dbFile = new File(Main.instance.getDataFolder(), "eternity.db");
            if (!dbFile.exists()) {
                dbFile.createNewFile();
            }
            String url = "jdbc:sqlite:" + dbFile.getPath();

            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS characters (uuid VARCHAR(32) NOT NULL PRIMARY KEY," +
                    " name VARCHAR(32) NOT NULL," +
                    " age INT(11) NOT NULL," +
                    " gender VARCHAR(32) NOT NULL," +
                    " nation VARCHAR(32) NOT NULL," +
                    " religion VARCHAR(32) NOT NULL )");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveCharacters() {
        String sql = "INSERT OR REPLACE INTO characters (uuid, name, age, gender, nation, religion) VALUES(?, ?, ?, ?, ?, ?)";
        for (UUID uuid: Main.characters.keySet()) {
            Character c = Main.characters.get(uuid);
            try {
                Connection conn = this.connection;
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, uuid.toString());
                ps.setString(2, c.getName());
                ps.setInt(3, c.getAge());
                ps.setString(4, c.getGender());
                ps.setString(5, c.getNationName());
                ps.setString(6, c.getReligion());
                ps.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCharacters() {
        String sql = "SELECT uuid, name, age, gender, nation, religion FROM characters";
        try {
            Connection conn = this.connection;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String nation = rs.getString("nation");
                String religion = rs.getString("religion");
                Main.characters.put(uuid, new Character(name, age, gender, nation, religion));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCharacter(UUID uuid) {
        String sql = "DELETE FROM characters WHERE uuid = ?";
        try {
            Connection conn = this.connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}