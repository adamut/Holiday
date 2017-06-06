package com.cosmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<Location> locations;

    public static void readFileInfo(Scanner read) {
        //reading each location data (name,country,district,city,price,activities,date intervals)
        while (read.hasNext()) {
            String locationName = read.nextLine();
            String country = read.nextLine();
            String district = read.nextLine();
            String city = read.nextLine();
            double price = read.nextDouble();
            read.nextLine();

            String activities = read.nextLine();
            String[] eachActivity = activities.split(",");
            List<String> locationActivities = new ArrayList<>();
            for (int j = 0; j < eachActivity.length; j++) {
                locationActivities.add(eachActivity[j]);
            }
            String dates = read.nextLine();
            String[] eachData = dates.split(" ");
            List<String> locationDates = new ArrayList<>();
            for (int j = 0; j < eachData.length; j++) {
                locationDates.add(eachData[j]);
            }
            if (read.hasNext())
                read.nextLine();
            locations.add(new Location(locationName, new City(country, district, city), price, locationActivities, locationDates));
        }
        try {
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //informations about locatie
    public static int locationInfo(String locatie) {
        List<Location> currentLocation = locations.stream()
                .filter(p -> locatie.equals(p.getLocationName()))
                .collect(Collectors.toList());
        if (currentLocation.size() == 0)
            return -1;
        else {
            currentLocation.forEach(System.out::println);
        }
        return 1;
    }

    public static int topFive(String startPeriod, String endPeriod, String inputCity) throws ParseException {
        //formatting the input dates
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start = sdf.parse(startPeriod);
        Date end = sdf.parse(endPeriod);
        //map for storing location and price for the locations which validate the requested requirements
        Map<String, Double> locationAndPrice = new HashMap<>();
        //flags used for retyping input if city or price informations not found
        int flag = -1, flagCriteria = -1;

        for (Location loc : locations) {

            //searching for inputCity in all our locations
            if (loc.getCityName().getCity().equals(inputCity) || loc.getCityName().getCountryName().equals(inputCity) ||
                    loc.getCityName().getDistrictName().equals(inputCity)) {
                flag = 1;

                for (String date : loc.getDates()) {
                    //splitting our date string in 2 dates(start1 and end1 - for each location)
                    String individualDates[] = date.split("-");
                    Date start1 = sdf.parse(individualDates[0]);
                    Date end1 = sdf.parse(individualDates[1]);

                    //comparing formatted dates for each location
                    if (start1.compareTo(start) <= 0 && end.compareTo(end1) <= 0) {
                        flagCriteria = 2;
                        String[] dayStart = startPeriod.split("/");
                        String[] dayEnd = endPeriod.split("/");
                        double price = ((Integer.parseInt(dayEnd[0]) - Integer.parseInt(dayStart[0]) + 1) * loc.getDayPrice());
                        locationAndPrice.put(String.valueOf(loc.getCityName()), price);
                    }
                }
            }

        }
        //processing and printing the first five locations
        Map<String, Double> result = new LinkedHashMap<>();

        locationAndPrice.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        result.forEach((k, v) -> System.out.println(k + " avand pretul de: " + v));

        if (flagCriteria == 2)
            return flagCriteria;
        else
            return flag;

    }

    public static int cheapestActivity(String activity) throws ParseException {
        int flag = -1;
        Map<String, Double> locationAndPrice = new HashMap<>();

        for (Location loc : locations) {
            for (String activitati : loc.getActivities()) {
                //removing all the blank spaces from both sides of string
                activitati = activitati.trim();

                if (activity.equals(activitati)) {
                    for (String date : loc.getDates()) {
                        flag = 1;
                        double price = loc.getDayPrice() * 10;
                        locationAndPrice.put(String.valueOf(loc.getCityName()), price);
                    }
                }
            }
        }
        Map<String, Double> result = new LinkedHashMap<>();

        locationAndPrice.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        //result.forEach((k, v) -> System.out.println(k + " avand pretul de: " + v));
        try {
            //if result has an item, we print it
            Map.Entry<String, Double> entry = result.entrySet().iterator().next();
            System.out.println(entry.getKey() + " Pret: " + entry.getValue());
        } catch (Exception e) {
            System.out.println("Activitatea nu a fost gasita!");
            return -1;
        }
        return flag;
    }

    public static void inputData() throws ParseException {
        Scanner read = null;
        try {
            read = new Scanner(new File("location.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        locations = new ArrayList<>();

        //reading all the data from file
        readFileInfo(read);
        int locationFound;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("Introduceti locatia dorita: ");
            String inputLocation = in.nextLine();
            locationFound = locationInfo(inputLocation);
            if (locationFound == -1)
                System.out.println("Locatia cautata nu a fost gasita.");
        } while (locationFound == -1);

        System.out.println("");

        int flag;   //used to validate if we found a city, or we don't have dates that match the given period

        do {
            System.out.println("");
            System.out.print("Introduceti orasul/judetul/tara dorita: ");
            String inputCity = in.nextLine();
            boolean startDay = true;
            boolean endDay = true;
            String startPeriod;
            String endPeriod;

            do {
                System.out.print("Introduceti perioada de start(ZZ/LL/AAAA) : ");
                startPeriod = in.nextLine();
                startDay = isThisDateValid(startPeriod, "dd/MM/yyyy");
            } while (!startDay);

            do {
                System.out.print("Introduceti perioada de final(ZZ/LL/AAAA) : ");
                endPeriod = in.nextLine();
                endDay = isThisDateValid(endPeriod, "dd/MM/yyyy");
            } while (!endDay);

            flag = topFive(startPeriod, endPeriod, inputCity);
            if (flag == -1)
                System.out.println("Locatia nu a fost gasita!");
            if (flag == 1)
                System.out.println("Nu exista locatii cu criteriile cerute. ");
        } while (flag == -1);

        int activityFlag;

        do {
            System.out.println("Introduceti activitatea pe care doriti sa o practicati:");
            String activity = in.nextLine();
            activityFlag = cheapestActivity(activity);
        } while (activityFlag == -1);

        read.close();
    }

    public static boolean isThisDateValid(String dateToValidate, String dateFromat) {

        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
        } catch (ParseException e) {
            System.out.println("Data invalida!");
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        inputData();

    }
}
