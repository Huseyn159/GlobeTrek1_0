package service;

import model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserService {

    private static UserService instance;
    private Map<String, User> users = new HashMap<>();
    private final String FILE_NAME = "data/users.txt";

    private  UserService() {
        loadUsers();
    }
    public User getUserByUsername(String username) {
        return users.get(username);
    }
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean register(String username,String nationality,String password){
        if(users.containsKey(username)){
            return false;
        }else{
            User user = new User(username,nationality,password);
            users.put(username,user);
            saveUsers();
            return true;
        }
    }

    public User login(String username, String password) {
        if (!users.containsKey(username)) {
            return null;
        }

        User user = users.get(username);
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    private void saveUsers() {
        File file = new File(FILE_NAME);


        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (User u : users.values()) {
                writer.println(
                        u.getUsername() + "|" +
                                u.getNationality() + "|" +      // 🔹 burda 1 dənə '|' var
                                u.getPassword() + "|" +
                                u.getBalance() + "|" +
                                u.getXp() + "|" +
                                u.getLevel()
                );
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error saving users: " + e.getMessage());
        }
    }
    public void save() {
        saveUsers();
    }
    private void loadUsers() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split("\\|");
                User u = new User(parts[0],parts[1],parts[2]);
                u.setBalance(Double.parseDouble(parts[3]));
                u.setXp(Integer.parseInt(parts[4]));
                u.setLevel(parts[5]);
                users.put(u.getUsername(),u);
            }
        }catch (IOException e){
            System.out.println("Error loading users: " + e.getMessage());
        }






    }

}
