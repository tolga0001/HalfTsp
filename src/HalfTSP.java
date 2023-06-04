import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HalfTSP {

    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input: ");
        String inputName = scanner.next();
        ArrayList<City> cities = readCities(inputName);

        for (City city : cities) {
            System.out.printf("id is %d,x is %d, y is %d\n", city.id, city.x, city.y);

        }*/

        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(1,2,3));
        cities.add(new City(2,1,11));
        cities.add(new City(3,12,23));
        cities.add(new City(4,7,10));
        cities.add(new City(5,13,37));
        cities.add(new City(6,54,6));
        cities.add(new City(7,2,1));

        opt3(cities,0,2,4);
        for (City city : cities) {
            System.out.printf("id is %d,x is %d, y is %d\n", city.id, city.x, city.y);

        }
    }

    private static ArrayList<City> readCities(String inputName) {
        ArrayList<City> cities = new ArrayList<>();
        File file = new File(inputName);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int x, y, id;
        City city;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] cityInfo = line.split(" ");
            id = Integer.parseInt(cityInfo[0]);
            x = Integer.parseInt(cityInfo[1]);
            y = Integer.parseInt(cityInfo[2]);
            city = new City(id, x, y);
            cities.add(city);
        }
        scanner.close();
        return cities;
    }

    private static ArrayList<City> optimize(ArrayList<City> cities){
        // TODO
        return cities;
    }

    // Finds the most optimized path for given 6 cities
    private static ArrayList<City> opt3(ArrayList<City> cities, int a, int c, int e){
        int dist0 = distance(cities.get(a), cities.get(a+1)) +
                distance(cities.get(c), cities.get(c+1)) +
                distance(cities.get(e), cities.get(e+1));
        // Case 1: A→C→B→D→E→F
        int dist1 = distance(cities.get(a), cities.get(c)) +
                distance(cities.get(a+1), cities.get(c+1)) +
                distance(cities.get(e), cities.get(e+1));
        // Case 2: A→B→C→E→D→F
        int dist2 = distance(cities.get(a), cities.get(a+1)) +
                distance(cities.get(c), cities.get(e)) +
                distance(cities.get(c+1), cities.get(e+1));
        // Case 3: A→E→D→C→B→F
        int dist3 = distance(cities.get(a), cities.get(e)) +
                distance(cities.get(c+1), cities.get(c)) +
                distance(cities.get(a+1), cities.get(e+1));
        // Case 4: A→D→E→B→C→F
        int dist4 = distance(cities.get(a), cities.get(c+1)) +
                distance(cities.get(e), cities.get(a+1)) +
                distance(cities.get(c), cities.get(e+1));
        // Case 5: A→D→E→C→B→F
        int dist5 = distance(cities.get(a), cities.get(c+1)) +
                distance(cities.get(e), cities.get(c)) +
                distance(cities.get(a+1), cities.get(e+1));
        // Case 6: A→C→B→E→D→F
        int dist6 =  distance(cities.get(a), cities.get(c)) +
                distance(cities.get(a+1), cities.get(e)) +
                distance(cities.get(c+1), cities.get(e+1));
        // Case 7: A→E→D→B→C→F
        int dist7 =  distance(cities.get(a), cities.get(e)) +
                distance(cities.get(c+1), cities.get(a+1)) +
                distance(cities.get(c), cities.get(e+1));

        int minDist = dist0;
        int caseIndex = 0;

        if(dist1 < minDist){
            minDist = dist1;
            caseIndex = 1;
        }
        if(dist2 < minDist){
            minDist = dist2;
            caseIndex = 2;
        }
        if(dist3 < minDist){
            minDist = dist3;
            caseIndex = 3;
        }
        if(dist4 < minDist){
            minDist = dist4;
            caseIndex = 4;
        }
        if(dist5 < minDist){
            minDist = dist5;
            caseIndex = 5;
        }
        if(dist6 < minDist){
            minDist = dist5;
            caseIndex = 6;
        }
        if(dist7 < minDist){
            minDist = dist5;
            caseIndex = 7;
        }

        if(caseIndex == 0) return cities;
        else return change(cities, a, caseIndex);
    }

    // Changes the order of travelling for shorter route
    private static ArrayList<City> change(ArrayList<City> cities, int cityIndex, int caseIndex){
        City b = copyCity(cities.get(cityIndex+1));
        City c = copyCity(cities.get(cityIndex+2));
        City d = copyCity(cities.get(cityIndex+3));
        City e = copyCity(cities.get(cityIndex+4));
        // Case 1: A→C→B→D→E→F
        if(caseIndex==1){
            cities.set(cityIndex+1, c);
            cities.set(cityIndex+2, b);
        }// Case 2: A→B→C→E→D→F
        else if(caseIndex==2){
            cities.set(cityIndex+3, e);
            cities.set(cityIndex+4, d);
        }// Case 3: A→E→D→C→B→F
        else if(caseIndex==3){
            cities.set(cityIndex+1, e);
            cities.set(cityIndex+2, d);
            cities.set(cityIndex+3, c);
            cities.set(cityIndex+4, b);
        }// Case 4: A→D→E→B→C→F
        else if(caseIndex==4){
            cities.set(cityIndex+1, d);
            cities.set(cityIndex+2, e);
            cities.set(cityIndex+3, b);
            cities.set(cityIndex+4, c);
        }// Case 5: A→D→E→C→B→F
        else if(caseIndex==5){
            cities.set(cityIndex+1, d);
            cities.set(cityIndex+2, e);
            cities.set(cityIndex+3, c);
            cities.set(cityIndex+4, b);
        }// Case 6: A→C→B→E→D→F
        else if(caseIndex==6){
            cities.set(cityIndex+1, c);
            cities.set(cityIndex+2, b);
            cities.set(cityIndex+3, e);
            cities.set(cityIndex+4, d);
        }// Case 7: A→E→D→B→C→F
        else if(caseIndex==7){
            cities.set(cityIndex+1, e);
            cities.set(cityIndex+2, d);
            cities.set(cityIndex+3, b);
            cities.set(cityIndex+4, c);
        }

        return cities;
    }

    // Returns the city to copy from
    private static City copyCity(City city){
        City newCity = new City(city.id, city.x, city.y);
        return newCity;
    }

    // Calculates the distance between 2 given cities
    private static int distance(City city, City nextCity){
        return Math.round((float)Math.sqrt(Math.pow((nextCity.x - city.x), 2) + Math.pow((nextCity.y - city.y), 2)));
    }
