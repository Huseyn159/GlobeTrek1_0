package service;

import model.Question;
import java.util.*;

public class VisaQuizService {

    private Map<String, List<Question>> questionBank = new HashMap<>();
    private Random random = new Random();
    private int reputation;
    private int totalCorrect;
    private int totalWrong;

    private List<String> startDialogs = new ArrayList<>();
    private List<String> correctDialogs = new ArrayList<>();
    private List<String> wrongDialogs = new ArrayList<>();
    private List<String> approveDialogs = new ArrayList<>();
    private List<String> deniedDialogs = new ArrayList<>();

    public VisaQuizService() {
        reputation = 50;
        totalCorrect = 0;
        totalWrong = 0;

        // --- Sual Bankı ---
        List<Question> shengenQs = new ArrayList<>(6);
        shengenQs.add(new Question("Which tower in Paris is one of the most visited monuments on Earth?", "Eifel"));
        shengenQs.add(new Question("Which country is known for pasta, pizza, and the Colosseum?", "Italy"));
        shengenQs.add(new Question("What is the currency shared by most European Union countries?", "Euro"));
        shengenQs.add(new Question("Which European city is built on canals and uses boats as taxis?", "Venice"));
        shengenQs.add(new Question("Which northern European country is famous for Vikings and the Northern Lights?", "Norway"));
        shengenQs.add(new Question("Which country built the Brandenburg Gate?", "Germany"));
        questionBank.put("Europe", shengenQs);

        List<Question> asiaQs = new ArrayList<>(6);
        asiaQs.add(new Question("This continent is home to the world’s highest mountain. What is its name?", "Everest"));
        asiaQs.add(new Question("Sushi comes from which Asian country?", "Japan"));
        asiaQs.add(new Question("The Great Wall can be seen in which country?", "China"));
        asiaQs.add(new Question("Which desert in Asia is known as the coldest desert on Earth?", "Gobi"));
        asiaQs.add(new Question("What is the world’s most populated country (as of recent years)?", "China"));
        asiaQs.add(new Question("Asian city is famous for its futuristic skyline and Marina Bay Sands?", "Singapore"));
        questionBank.put("Asia", asiaQs);

        List<Question> africaQs = new ArrayList<>(6);
        africaQs.add(new Question("The world’s longest river flows through Africa. What’s its name?", "Nile"));
        africaQs.add(new Question("Which African desert is the largest hot desert on Earth?", "The Sahara Desert"));
        africaQs.add(new Question("“Safari” originated from which continent?", "Africa"));
        africaQs.add(new Question("Which African country has the ancient pyramids?", "Egypt"));
        africaQs.add(new Question("What is Africa’s highest mountain?", "Klimanjaro"));
        africaQs.add(new Question("Which African animal is known as the fastest land animal?", "Cheetah"));
        questionBank.put("Africa", africaQs);

        List<Question> americaQs = new ArrayList<>(6);
        americaQs.add(new Question("The largest rainforest in the world is found in South America. What’s its name?", "Amazon Rainforest"));
        americaQs.add(new Question("Which famous waterfall lies between Canada and the USA?", "Niagara Falls"));
        americaQs.add(new Question("Which North American country is known for maple syrup?", "Canada"));
        americaQs.add(new Question("Which South American country has a giant statue called 'Christ the Redeemer'?", "Brazil"));
        americaQs.add(new Question("Which U.S. city is known as 'The Big Apple'?", "New York City"));
        americaQs.add(new Question("The Andes Mountains stretch across how many South American countries?", "Seven"));
        questionBank.put("America", americaQs);

        List<Question> oceaniaQs = new ArrayList<>(6);
        oceaniaQs.add(new Question("Which country is both a continent and a nation?", "Australia"));
        oceaniaQs.add(new Question("What’s the name of the world’s largest coral reef system found in Oceania?", "The Great Barrier Reef"));
        oceaniaQs.add(new Question("Which bird is a national symbol of New Zealand and cannot fly?", "Kiwi"));
        oceaniaQs.add(new Question("What sport is most popular in both Australia and New Zealand?", "Rugby"));
        oceaniaQs.add(new Question("Which island nation in the Pacific is famous for its Moai statues?", "Easter Island"));
        oceaniaQs.add(new Question("What is the capital of Fiji?", "Suva"));
        questionBank.put("Oceania", oceaniaQs);

        // --- Dialoqlar (sənin CLI-dakı) ---
        startDialogs.add("Put your passport on the table. No excuses today.");
        startDialogs.add("Ready? I hope your brain is too.");
        startDialogs.add("Let's see if you're actually prepared… or just hoping.");
        startDialogs.add("Your answers decide everything. No pressure… kinda.");
        startDialogs.add("Start the quiz. And try not to embarrass yourself.");
        startDialogs.add("Okay, let's begin. I already doubt your confidence.");

        correctDialogs.add("Correct. Don’t get too happy.");
        correctDialogs.add("Not bad. Even I’m surprised.");
        correctDialogs.add("Alright, you're not hopeless.");
        correctDialogs.add("Good job… by accident?");
        correctDialogs.add("Fine. You got this one right.");
        correctDialogs.add("This time you survived.");

        wrongDialogs.add("Wrong. Very wrong.");
        wrongDialogs.add("This answer… I have no words.");
        wrongDialogs.add("You sure you're ready to travel?");
        wrongDialogs.add("Incorrect. Border officers would laugh.");
        wrongDialogs.add("Bad answer. Your passport is crying.");
        wrongDialogs.add("Nope. Try again, but smarter.");

        approveDialogs.add("Visa approved. Don’t cause trouble abroad.");
        approveDialogs.add("You passed. Even I’m shocked.");
        approveDialogs.add("Approved. Now go. Just… go.");
        approveDialogs.add("Fine, you can travel. Don't make me regret it.");
        approveDialogs.add("Congratulations… somehow you made it.");

        deniedDialogs.add("Visa denied. Too many mistakes.");
        deniedDialogs.add("Nope. You're staying home.");
        deniedDialogs.add("Denied. Try again after using Google.");
        deniedDialogs.add("Your answers were… disappointing. Denied.");
        deniedDialogs.add("Visa denied. Maybe next lifetime.");
    }

    private String randomPick(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    public List<Question> getQuestions(String region) {
        List<Question> questions = questionBank.get(region);
        if (questions == null) {
            return new ArrayList<>();
        }
        Collections.shuffle(questions);
        return questions.subList(0, Math.min(3, questions.size()));
    }

    public String getRandomIntroMessage(String country) {
        return randomPick(startDialogs);
    }

    public String getCorrectAnswerMessage() {
        return randomPick(correctDialogs);
    }

    public String getWrongAnswerMessage() {
        return randomPick(wrongDialogs);
    }

    public String getVisaApprovedMessage(String country) {
        return randomPick(approveDialogs);
    }

    public String getVisaRejectedMessage(String country, int correctAnswers, int totalQuestions) {
        return randomPick(deniedDialogs) + " (" + correctAnswers + "/" + totalQuestions + ")";
    }
}
