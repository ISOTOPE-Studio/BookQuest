package cc.isotopestudio.bookquest.sql;
/*
 * Created by Mars Tan on 3/30/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static cc.isotopestudio.bookquest.BookQuest.plugin;

public abstract class SqlManager {

    private static Connection c;
    private static Statement s;

    public static boolean init() {
        String host = plugin.config.getString("mySQL.host");
        String port = plugin.config.getString("mySQL.port");
        String user = plugin.config.getString("mySQL.user");
        String pw = plugin.config.getString("mySQL.password");
        String db = plugin.config.getString("mySQL.database");
        MySQL mySQL = new MySQL(host, port, db, user, pw);
        try {
            c = mySQL.openConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            plugin.getLogger().info("数据库出错 Error1");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.getLogger().info("数据库出错 Error2");
            return false;
        }
        try {
            s = c.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
            plugin.getLogger().info("数据库出错 Error3");
            return false;
        }
        try {
            s.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS record(" +
                            " id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            " time TIMESTAMP NOT NULL," +
                            " player TEXT NOT NULL," +
                            " task TEXT NOT NULL" +
                            " );");
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.getLogger().info("数据库出错 Error4");
            return false;
        }
        return true;
    }

    public static void addRecord(Player player, Task task) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO record (player, task) VALUES (?, ?)");
            ps.setString(1, player.getName());
            ps.setString(2, task.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Long> getRecord(Player player, Task task) {
        List<Long> result = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM record WHERE player=? AND task=?");
            ps.setString(1, player.getName());
            ps.setString(2, task.getName());
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result.add(res.getTimestamp("time").getTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result.sort(Comparator.comparingLong(l -> l));
        return result;
    }

}
