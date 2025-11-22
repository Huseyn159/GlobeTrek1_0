package model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TravelService {

    private Scanner sc = new Scanner(System.in);
    Map<String,String> countryToContinent = new HashMap<>(){{
        //  Asia
        put("Japan", "Asia");
        put("China", "Asia");
        put("India", "Asia");
        put("Azerbaijan", "Asia");
        put("Georgia", "Asia");

        //  Europe
        put("France", "Europe");
        put("England", "Europe");
        put("Germany", "Europe");
        put("Turkey", "Europe");

        //  America
        put("USA", "America");
        put("Brazil", "America");
        put("Canada", "America");

        //  Africa
        put("Egypt", "Africa");
        put("South Africa", "Africa");
        put("Nigeria", "Africa");

        //  Oceania
        put("Australia", "Oceania");
        put("New Zealand", "Oceania");
        put("Fiji", "Oceania");

    }};
    Map<String, List<String>> visaFreeAccess = new HashMap<>();
    {
        //  Asia passports
        List<String> azeFree = List.of("Turkey", "Georgia","Azerbaijan");
        visaFreeAccess.put("Azerbaijan", azeFree);

        List<String> japanFree = List.of("China", "India", "Australia","Japan");
        visaFreeAccess.put("Japan", japanFree);

        List<String> chinaFree = List.of("India","China");
        visaFreeAccess.put("China", chinaFree);

        List<String> indiaFree = List.of("Japan", "China","India");
        visaFreeAccess.put("India", indiaFree);

        List<String> georgiaFree = List.of("Turkey", "Azerbaijan","Georgia");
        visaFreeAccess.put("Georgia", georgiaFree);


        //  Europe passports
        List<String> turkeyFree = List.of("Azerbaijan", "Georgia", "Germany","Turkey");
        visaFreeAccess.put("Turkey", turkeyFree);

        List<String> franceFree = List.of("Germany", "England","France");
        visaFreeAccess.put("France", franceFree);

        List<String> germanyFree = List.of("France", "England","Germany");
        visaFreeAccess.put("Germany", germanyFree);

        List<String> englandFree = List.of("France", "Germany", "USA","England");
        visaFreeAccess.put("England", englandFree);


        //  America passports
        List<String> usaFree = List.of("Canada", "Brazil", "England","USA","Brazil");
        visaFreeAccess.put("USA", usaFree);

        List<String> brazilFree = List.of("USA","Brazil");
        visaFreeAccess.put("Brazil", brazilFree);

        List<String> canadaFree = List.of("USA", "England","Canada");
        visaFreeAccess.put("Canada", canadaFree);


        //  Africa passports
        List<String> egyptFree = List.of("South Africa", "Nigeria","Egypt");
        visaFreeAccess.put("Egypt", egyptFree);

        List<String> southAfricaFree = List.of("Egypt", "Nigeria","South Africa");
        visaFreeAccess.put("South Africa", southAfricaFree);

        List<String> nigeriaFree = List.of("Egypt","Nigeria");
        visaFreeAccess.put("Nigeria", nigeriaFree);


        //  Oceania passports
        List<String> australiaFree = List.of("New Zealand", "Fiji","Australia");
        visaFreeAccess.put("Australia", australiaFree);

        List<String> newZealandFree = List.of("Australia", "Fiji","New Zealand");
        visaFreeAccess.put("New Zealand", newZealandFree);

        List<String> fijiFree = List.of("Australia", "New Zealand","Fiji");
        visaFreeAccess.put("Fiji", fijiFree);
    }

    public String getContinent(String country) {
        return countryToContinent.get(country);
    }
    public boolean isVisaRequired(String nationality,String destination){

        for (Map.Entry<String,List<String>> entry : visaFreeAccess.entrySet()){
            if (entry.getKey().equals(nationality)){
               for (String e : entry.getValue()){
                   if (e.equals(destination)) return false;

               }
            }

        }
    return true;
    }


    public double calculateContinentDistance(String from,String to){
      String fromContinent = null;
      String toCont = null;
      int distance = 0;
        for (Map.Entry<String,String> entry : countryToContinent.entrySet()){
          if (entry.getKey().equals(from)) fromContinent =entry.getValue();
          if (entry.getKey().equals(to)) toCont = entry.getValue();
      }
        if (fromContinent == null || toCont == null) return 1500;
        if (fromContinent.equals(toCont)) return 500;
        if ((fromContinent.equals("Europe") && toCont.equals("Asia")) ||
                (fromContinent.equals("Asia") && toCont.equals("Europe"))) return 1000;
        return 2000;
    }

     public double calculateCost(String from,String to,String flightType,boolean visaRequired){
     double distance = calculateContinentDistance(from, to);
     double totalCost = switch (flightType) {
         case "Economy" -> distance * 0.5;
         case "Business" -> distance * 1;
         case "First" -> distance * 1.5;
         default -> 0;
     };
        if (visaRequired) totalCost += 200;


     return totalCost ;
    }

}
