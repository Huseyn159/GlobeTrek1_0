package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private double balance;
    private int xp;
    private String level;
    private String nationality;
    private List<Achievement> achievements= new ArrayList<>();




    public String getNationality() {
        return nationality;
    }

    public User(String username, String nationality, String password) {
        this.username = username;
        this.password = password;
        this.nationality = nationality;
        this.balance = 1000;
        this.xp = 0;
        this.level = "bronze";
    }

    public void addAchievement(Achievement ach){

        for (Achievement a : achievements){
            if (a.getName().equalsIgnoreCase(ach.getName())) {

                return;
            }
        }

        ach.setUnlockedDate(LocalDate.now());

        achievements.add(ach);

        System.out.println("🏆 Achievement unlocked: " + ach.getName());

    }

    public void deductBalance(double amount){
      balance-=amount;
    }

    public void addXp(int amount){
        xp+=amount;
        // No console output, UI will handle notification
    }

    public String updateLevel() {
        String previousLevel = level;

        if (xp >= 5000) {
            level = "Platinum";
        } else if (xp >= 3000) {
            level = "Diamond";
        } else if (xp >= 1500) {
            level = "Gold";
        } else if (xp >= 1200) {
            level = "Silver";
        } else {
            level = "Bronze";
        }

        if (!previousLevel.equals(level)) {
            return level; // Return new level if changed
        }
        return null; // No level change
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }




    @Override
    public String toString() {
        return username + " | Balance: "  + balance + " | XP: " + xp + " | Level: " + level;
    }
}
