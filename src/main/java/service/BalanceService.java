package service;

import exception.InvalidAmountException;
import exception.InvalidCardNumberException;
import model.User;

public class BalanceService {
    UserService sr = UserService.getInstance();

    public boolean validateCard(String cardNumber) {
        cardNumber = cardNumber.replace(" ", "");
        boolean onlyDigits = cardNumber.chars().allMatch(Character::isDigit);
        if (!onlyDigits) return false;
        if (cardNumber.length() < 13 || cardNumber.length() > 19) return false;

        int sum = 0;
        boolean doubleDigit = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }

        return sum % 10 == 0;
    }

    public boolean addBalance(User user, String cardNumber, double amount) throws InvalidCardNumberException, InvalidAmountException {
        if (!validateCard(cardNumber)) {
            throw new InvalidCardNumberException("Invalid card number!");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Balance must be greater than zero!");
        }

        user.setBalance(user.getBalance() + amount);
        sr.save();
        return true;
    }
}