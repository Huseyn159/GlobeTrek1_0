package service;

import model.Achievement;
import model.Travel;
import model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class AchievementManager {
    private final String FOLDER_PATH = "data/achievements/";
    private Map<String, List<Achievement>> achievementMap = new HashMap<>();
    private final TravelService travelService = TravelService.getInstance();

    private final List<Achievement> ALL_ACHIEVEMENTS = List.of(
            new Achievement("✈️ First Flight", "Your first ever flight!"),
            new Achievement("🛬 Frequent Flyer", "Completed 3 flights!"),
            new Achievement("🧭 Explorer", "5 destinations reached!"),
            new Achievement("🌍 Jetsetter", "10 flights completed!"),
            new Achievement("🛫 Sky Addict", "20 flights completed!"),
            new Achievement("🌐 World Traveller", "Travelled to 5 different countries!"),
            new Achievement("🌏 Globe Explorer", "Travelled to 10 different countries!"),
            new Achievement("🌎 Continent Hopper", "Visited 3 different continents!"),
            new Achievement("🌍 Earth Roamer", "Visited 5 continents!")
    );

    public AchievementManager() {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        achievementMap = new HashMap<>();
    }

    private void saveUserAchievements(String username, List<Achievement> list) {
        File file = new File(FOLDER_PATH + username + ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Achievement a : list) {
                writer.println(a.getName() + "|" + a.getDescription() + "|" + a.getUnlockedDate());
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error saving achievements: " + e.getMessage());
        }
    }

    public List<Achievement> loadUserAchievements(String username) {
        File file = new File(FOLDER_PATH + username + ".txt");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Achievement> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                Achievement a = new Achievement(parts[0], parts[1]);
                a.setUnlockedDate(LocalDate.parse(parts[2]));
                list.add(a);
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error loading achievements: " + e.getMessage());
        }
        return list;
    }

    public boolean unlock(User user, Achievement achievement) {
        List<Achievement> list = getUserAchievements(user.getUsername());
        if (list.stream().anyMatch(a -> a.getName().equals(achievement.getName()))) {
            return false; // Already unlocked
        }
        achievement.setUnlockedDate(LocalDate.now());
        list.add(achievement);
        saveUserAchievements(user.getUsername(), list);
        return true; // Newly unlocked
    }

    private List<Achievement> getUserAchievements(String username) {
        if (achievementMap.containsKey(username)) {
            return achievementMap.get(username);
        }
        List<Achievement> loaded = loadUserAchievements(username);
        achievementMap.put(username, loaded);
        return loaded;
    }

    public List<Achievement> checkTravelAchievements(User user) {
        List<Achievement> newlyUnlocked = new ArrayList<>();
        List<Travel> history = travelService.getTravelHistory(user);
        if (history.isEmpty()) return newlyUnlocked;

        // FLIGHT COUNT ACHIEVEMENTS
        if (history.size() >= 1 && unlock(user, new Achievement("✈️ First Flight", "Your first ever flight!")))
            newlyUnlocked.add(new Achievement("✈️ First Flight", "Your first ever flight!"));
        if (history.size() >= 3 && unlock(user, new Achievement("🛬 Frequent Flyer", "Completed 3 flights!")))
            newlyUnlocked.add(new Achievement("🛬 Frequent Flyer", "Completed 3 flights!"));
        if (history.size() >= 5 && unlock(user, new Achievement("🧭 Explorer", "5 destinations reached!")))
            newlyUnlocked.add(new Achievement("🧭 Explorer", "5 destinations reached!"));
        if (history.size() >= 10 && unlock(user, new Achievement("🌍 Jetsetter", "10 flights completed!")))
            newlyUnlocked.add(new Achievement("🌍 Jetsetter", "10 flights completed!"));
        if (history.size() >= 20 && unlock(user, new Achievement("🛫 Sky Addict", "20 flights completed!")))
            newlyUnlocked.add(new Achievement("🛫 Sky Addict", "20 flights completed!"));

        // CONTINENT & COUNTRY ACHIEVEMENTS
        Set<String> continents = new HashSet<>();
        Set<String> countries = new HashSet<>();

        for (Travel a : history) {
            String cont = travelService.getContinent(a.getTo());
            if (cont != null) continents.add(cont);
            countries.add(a.getTo());
        }

        if (continents.size() >= 3 && unlock(user, new Achievement("🌎 Continent Hopper", "Visited 3 different continents!")))
            newlyUnlocked.add(new Achievement("🌎 Continent Hopper", "Visited 3 different continents!"));
        if (continents.size() >= 5 && unlock(user, new Achievement("🌍 Earth Roamer", "Visited 5 continents!")))
            newlyUnlocked.add(new Achievement("🌍 Earth Roamer", "Visited 5 continents!"));
        if (countries.size() >= 5 && unlock(user, new Achievement("🌐 World Traveller", "Travelled to 5 different countries!")))
            newlyUnlocked.add(new Achievement("🌐 World Traveller", "Travelled to 5 different countries!"));
        if (countries.size() >= 10 && unlock(user, new Achievement("🌏 Globe Explorer", "Travelled to 10 different countries!")))
            newlyUnlocked.add(new Achievement("🌏 Globe Explorer", "Travelled to 10 different countries!"));

        return newlyUnlocked;
    }
    public List<Achievement> getAllAchievements() {
        return ALL_ACHIEVEMENTS;
    }
}