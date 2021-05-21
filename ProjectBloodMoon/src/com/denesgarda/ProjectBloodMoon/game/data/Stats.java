package com.denesgarda.ProjectBloodMoon.game.data;

import com.denesgarda.ProjectBloodMoon.Main;

import java.io.IOException;
import java.sql.PreparedStatement;
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
    public void saveStats() throws SQLException {
        String query = "UPDATE pbm.accounts SET hp = \"" + this.hP + "\", inventory = \"" + Arrays.toString(this.inventory).substring(1, Arrays.toString(this.inventory).length() - 1) + "\"";
        PreparedStatement stmt = Main.conn.prepareStatement(query);
        stmt.executeUpdate();
    }

    public void printStats() {
        System.out.println("\nSTATS:\n" +
                "HP: " + this.hP + "\n");
    }
    public double getHP() {
        return this.hP;
    }
    public Stats getHP(double hP) {
        this.hP = hP;
        return this;
    }
    public boolean minusHP(double hP) throws IOException {
        System.out.println("HP - " + hP + "\n");
        if(this.hP - hP <= 0) {
            Strings.youDied();
            this.hP = 100;
            return true;
        }
        else {
            this.hP -= hP;
        }
        return false;
    }
    public Stats plusHP(double hP) {
        System.out.println("HP + " + hP + "\n");
        if(this.hP + hP >= 100) {
            this.hP = 100;
        }
        else {
            this.hP += hP;
        }
        return this;
    }

    public void printInventory() {
        System.out.println("\nINVENTORY:\n" + Arrays.toString(this.inventory) + "\n");
    }
    public String[] getInventory() {
        return this.inventory;
    }
    public Stats setInventory(String[] inventory) {
        this.inventory = inventory;
        return this;
    }
}
