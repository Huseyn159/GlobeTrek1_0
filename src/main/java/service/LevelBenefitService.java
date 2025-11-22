package service;

import model.User;

public class LevelBenefitService {


        public double applyDiscount(User user, double cost) {
            return switch (user.getLevel()) {
                case "silver" -> cost * 0.95;
                case "gold" -> cost * 0.90;
                case "platinum" -> cost * 0.80;
                case "diamond" -> cost * 0.70;
                default -> cost;
            };
        }

        public int extraXp(User user) {
            return switch (user.getLevel()) {
                case "silver" -> 10;
                case "gold" -> 25;
                case "platinum" -> 40;
                case "diamond" -> 60;
                default -> 0;
            };
        }
    }


