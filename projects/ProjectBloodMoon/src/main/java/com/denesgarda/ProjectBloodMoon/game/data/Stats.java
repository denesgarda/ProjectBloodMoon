package com.denesgarda.ProjectBloodMoon.game.data;

import com.denesgarda.ProjectBloodMoon.Main;
import com.denesgarda.ProjectBloodMoon.game.Game;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Stats implements Serializable {
    private double hP;
    private String[] inventory;
    private int progress;

    public Stats(double hP, String[] inventory, int progress) throws SQLException {
        this.hP = hP;
        this.inventory = inventory;
        this.progress = progress;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
    public Stats resetHP() {
        System.out.println("HP fully regenerated!\n");
        this.hP = 100;
        return this;
    }
    public Stats setHP(double hP) {
        this.hP = hP;
        return this;
    }
    public Stats retrieveAndSetHp() throws SQLException {
        Statement stmt = Main.conn.createStatement();
        String query = "SELECT hp FROM pbm.accounts WHERE username = \"" + Game.username + "\"";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        this.hP = Double.parseDouble(rs.getString("hp"));
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