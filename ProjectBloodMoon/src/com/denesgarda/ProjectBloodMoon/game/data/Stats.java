package com.denesgarda.ProjectBloodMoon.game.data;

import com.denesgarda.ProjectBloodMoon.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Stats {
    private double hP;
    private String[] inventory;

    public Stats(String username) throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT hp FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs = stmt.executeQuery(query);

        rs.next();

        String hP = rs.getString("hp");
        this.hP = Double.parseDouble(hP);

        Statement stmt2 = Main.conn.createStatement();
        String query2 = "SELECT inventory FROM pbm.accounts WHERE username = \"" + username + "\"";
        ResultSet rs2 = stmt2.executeQuery(query2);

        rs2.next();

        String inventoryString = rs2.getString("inventory");
        this.inventory = Strings.stringToArray(inventoryString);
    }

    public void printStats() {
        System.out.println("\nSTATS:\n" +
                "HP: " + this.hP + "\n");
    }

    public void printInventory() {
        System.out.println("\nINVENTORY:\n" + Arrays.toString(this.inventory) + "\n");
    }
}
