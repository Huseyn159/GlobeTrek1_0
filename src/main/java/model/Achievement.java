package model;

import java.time.LocalDate;

public class Achievement {

    private String name;
    private String description;
    private LocalDate unlockedDate;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
        this.unlockedDate = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getUnlockedDate() {
        return unlockedDate;
    }

    public void setUnlockedDate(LocalDate unlockedDate) {
        this.unlockedDate = unlockedDate;
    }

    @Override
    public String toString() {
        return name + " (" + description + ") - Unlocked: " + unlockedDate;
    }

    // Dublicate achivementin qarsisin almaq ucun
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
