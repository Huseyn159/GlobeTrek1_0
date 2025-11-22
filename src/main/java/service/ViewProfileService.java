package service;

import model.Travel;
import model.User;

import java.util.List;

public class ViewProfileService {

    private final User currentUser;
    private final List<Travel> history;
    public ViewProfileService(User currentUser, List<Travel> history) {
        this.currentUser = currentUser;
        this.history = history;
    }




    public void showProfile(){
        System.out.println("===== 👤 USER PROFILE =====");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Nationality: " + currentUser.getNationality());
        System.out.println("Level: " + currentUser.getLevel());
        System.out.println("XP: " + currentUser.getXp());
        System.out.println("Balance: " + currentUser.getBalance() + "\uD83D\uDCB5");

        System.out.println("\n----- 🌍 Travel History -----");

        if (history.isEmpty()) {
            System.out.println("No trips yet.");
        } else {
            for (Travel e : history) {
                System.out.println(e);
            }
        }
    }
}
