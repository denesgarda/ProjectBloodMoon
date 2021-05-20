package com.denesgarda.ProjectBloodMoon.game.data;

import com.denesgarda.ProjectBloodMoon.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stats {
    private double hP;

    public Stats(String username) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT hp FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);

        rs.next();

        String hP = rs.getString("hp");
        this.hP = Double.parseDouble(hP);
    }

    public void printStats() {
        System.out.println("\nSTATS:\n" +
                "HP: " + this.hP);
    }
}
