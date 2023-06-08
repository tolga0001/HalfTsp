import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HalfTSP {
    // Tolga Fehmioğlu 150120022
    // Mehmet Toprak Balıkçı 150121032
    // Enes Torluoğlu 150121002
    // Muhammed Enes Gökdeniz 150121538

    /**
     * The aim of this homework is to find the best solution as much as possible to Half TSP which is basically a hard
     * NP problem in a reasonable time.
     */


    public static void main(String[] args) {
        //In this function we take input file’s name, then read the cities in the input file and call
        //getTour() function to get initial tour and optimize() function the tour.
        //Then it prints the optimized tour. Also, calculates the time passed.

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input: ");
        String inputName = scanner.next();
        ArrayList<City> cities = readCities(inputName);

        long startTime = System.currentTimeMillis();
        ArrayList<City> initialTour = getTour(cities);
        int TourDistance = getTourDistance(initialTour);

        ArrayList<City> optimizedTour = optimize(initialTour);
        int optimizedDistance = getTourDistance(optimizedTour);
        int currentDistance;
        while (true) {
            optimizedTour = optimize(optimizedTour);
            currentDistance = getTourDistance(optimizedTour);
            if (optimizedDistance == currentDistance) {
                break;
            }
            optimizedDistance = currentDistance;

        }


        System.out.println("normal distance : " + TourDistance);
        System.out.println("optimizedDistance: " + optimizedDistance);
        write_to_file(optimizedDistance, optimizedTour);

        long endTime = System.currentTimeMillis();

        System.out.println("Time : " + (endTime - startTime) / 1000 + "saniye");

    }

    // This function creates output.txt file and writes the tour to this file.
    private static void write_to_file(int tourDistance, ArrayList<City> initialTour) {
        try (PrintWriter writer = new PrintWriter("output.txt")) {
            writer.println(tourDistance);
            for (City city : initialTour) {
                writer.println(city.id);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    // This function calculates the given tour’s distance.
    private static int getTourDistance(ArrayList<City> initialTour) {
        int distance = 0;
        City firstCity = initialTour.get(0);
        City currentCity = firstCity;
        for (City city : initialTour) {
            if (city == firstCity) continue;
            distance += distance(currentCity, city);
            currentCity = city;
        }
        City lastCity = initialTour.get(initialTour.size() - 1);
        distance += distance(lastCity, firstCity);
        return distance;
    }

    // This function returns the initial tour that is found using the  nearest neighbor algorithm.
    private static ArrayList<City> getTour(ArrayList<City> cities) {

        ArrayList<City> initialTour = new ArrayList<>();
        City first = getFirstCity(cities);
        City currentCity = first;
        City nextCity;
        initialTour.add(first);
        int tour_size = (int) Math.ceil(cities.size() / 2.0);
        for (int currentSize = 1; currentSize < tour_size; currentSize++) {
            nextCity = getNeighbourCity(currentCity, cities, initialTour);
            initialTour.add(nextCity);
            currentCity = nextCity;

        }
        return initialTour;

    }
    // Finds closest city to the given currentCity.

    private static City getNeighbourCity(City currentCity, ArrayList<City> cities, ArrayList<City> initialTour) {
        int min = Integer.MAX_VALUE;
        int current_distance;
        City closestCity = null;
        for (City city : cities) {
            if (initialTour.contains(city) || city == currentCity) continue;
            current_distance = distance(currentCity, city);
            if (current_distance < min) {
                min = current_distance;
                closestCity = city;
            }
        }

        return closestCity;

    }
    // This function finds the first city to start the initial tour.

    private static City getFirstCity(ArrayList<City> cities) {
        int averageX = getAverageX(cities);
        int averageY = getAverageY(cities);
        return getMiddleCity(averageX, averageY, cities);
    }

    // This function finds the arithmetic mean of all cities’ coordinates.
    private static City getMiddleCity(int averageX, int averageY, ArrayList<City> cities) {
        City closerMiddle = null;
        int minDistance = Integer.MAX_VALUE;
        int currentDistance;
        for (City city : cities) {
            currentDistance = getDistance(averageX, averageY, city);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closerMiddle = city;
            }

        }
        return closerMiddle;

    }

    // Finds the distance between arithmetic mean and the given city.
    private static int getDistance(int averageX, int averageY, City firstCity) {
        int dx = firstCity.x - averageX;
        int dy = firstCity.y - averageY;
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }

    // Finds mean Y coordinate of all cities.
    private static int getAverageY(ArrayList<City> cities) {
        int size = cities.size();
        int totalY = 0;
        int y;
        for (City city : cities) {
            y = city.y;
            totalY += y;
        }
        return totalY / size;
    }

    // Finds mean X coordinate of all cities.
    private static int getAverageX(ArrayList<City> cities) {
        int size = cities.size();
        int totalX = 0;
        int x;
        for (City city : cities) {
            x = city.x;
            totalX += x;
        }
        return totalX / size;

    }

    // 	Reads all cities from input file and puts them in an array list.
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
            String[] lineElements = line.split(" ");
            int[] numbers = getNumbers(lineElements);
            id = numbers[0];
            x = numbers[1];
            y = numbers[2];
            city = new City(id, x, y);
            cities.add(city);
        }
        scanner.close();
        return cities;
    }

    //Finds cities’ ids, X and Y coordinates.

    private static int[] getNumbers(String[] lineElements) {
        int[] numbers = new int[3];
        int i = 0;

        for (String element : lineElements) {
            if (element.equals("")) continue;

            numbers[i] = Integer.parseInt(element);
            i++;
        }
        return numbers;

    }

    // This function takes the initial tour and optimizes it using opt3() function.
    private static ArrayList<City> optimize(ArrayList<City> cities) {
        // TODO

        int lengthOfCities = cities.size();

        int i = 0;
        // 1 2 3 4 5 6
        // 2 3 4 5 6 7

        while (i < lengthOfCities - 5) {


            cities = opt3(cities, i, i + 2, i + 4);
            i++;
        }

        return cities;
    }


    // Finds the most optimized path for given 6 cities
    private static ArrayList<City> opt3(ArrayList<City> cities, int a, int c, int e) {
        int dist0 = distance(cities.get(a), cities.get(a + 1)) +
                distance(cities.get(c), cities.get(c + 1)) +
                distance(cities.get(e), cities.get(e + 1));
        // Case 1: A→C→B→D→E→F
        int dist1 = distance(cities.get(a), cities.get(c)) +
                distance(cities.get(a + 1), cities.get(c + 1)) +
                distance(cities.get(e), cities.get(e + 1));
        // Case 2: A→B→C→E→D→F
        int dist2 = distance(cities.get(a), cities.get(a + 1)) +
                distance(cities.get(c), cities.get(e)) +
                distance(cities.get(c + 1), cities.get(e + 1));
        // Case 3: A→E→D→C→B→F
        int dist3 = distance(cities.get(a), cities.get(e)) +
                distance(cities.get(c + 1), cities.get(c)) +
                distance(cities.get(a + 1), cities.get(e + 1));
        // Case 4: A→D→E→B→C→F
        int dist4 = distance(cities.get(a), cities.get(c + 1)) +
                distance(cities.get(e), cities.get(a + 1)) +
                distance(cities.get(c), cities.get(e + 1));
        // Case 5: A→D→E→C→B→F
        int dist5 = distance(cities.get(a), cities.get(c + 1)) +
                distance(cities.get(e), cities.get(c)) +
                distance(cities.get(a + 1), cities.get(e + 1));
        // Case 6: A→C→B→E→D→F
        int dist6 = distance(cities.get(a), cities.get(c)) +
                distance(cities.get(a + 1), cities.get(e)) +
                distance(cities.get(c + 1), cities.get(e + 1));
        // Case 7: A→E→D→B→C→F
        int dist7 = distance(cities.get(a), cities.get(e)) +
                distance(cities.get(c + 1), cities.get(a + 1)) +
                distance(cities.get(c), cities.get(e + 1));

        int minDist = dist0;
        int caseIndex = 0;

        if (dist1 < minDist) {
            minDist = dist1;
            caseIndex = 1;
        }
        if (dist2 < minDist) {
            minDist = dist2;
            caseIndex = 2;
        }
        if (dist3 < minDist) {
            minDist = dist3;
            caseIndex = 3;
        }
        if (dist4 < minDist) {
            minDist = dist4;
            caseIndex = 4;
        }
        if (dist5 < minDist) {
            minDist = dist5;
            caseIndex = 5;
        }
        if (dist6 < minDist) {
            minDist = dist6;
            caseIndex = 6;
        }
        if (dist7 < minDist) {
            caseIndex = 7;
        }

        if (caseIndex == 0) return cities;
        else return change(cities, a, caseIndex);
    }

    // Called from opt3 to change the route for a shorter route.
    private static ArrayList<City> change(ArrayList<City> cities, int startingIndex, int caseIndex) {
        City b = copyCity(cities.get(startingIndex + 1));
        City c = copyCity(cities.get(startingIndex + 2));
        City d = copyCity(cities.get(startingIndex + 3));
        City e = copyCity(cities.get(startingIndex + 4));
        // Case 1: A→C→B→D→E→F
        if (caseIndex == 1) {
            cities.set(startingIndex + 1, c);
            cities.set(startingIndex + 2, b);
        }// Case 2: A→B→C→E→D→F
        else if (caseIndex == 2) {
            cities.set(startingIndex + 3, e);
            cities.set(startingIndex + 4, d);
        }// Case 3: A→E→D→C→B→F
        else if (caseIndex == 3) {
            cities.set(startingIndex + 1, e);
            cities.set(startingIndex + 2, d);
            cities.set(startingIndex + 3, c);
            cities.set(startingIndex + 4, b);
        }// Case 4: A→D→E→B→C→F
        else if (caseIndex == 4) {
            cities.set(startingIndex + 1, d);
            cities.set(startingIndex + 2, e);
            cities.set(startingIndex + 3, b);
            cities.set(startingIndex + 4, c);
        }// Case 5: A→D→E→C→B→F
        else if (caseIndex == 5) {
            cities.set(startingIndex + 1, d);
            cities.set(startingIndex + 2, e);
            cities.set(startingIndex + 3, c);
            cities.set(startingIndex + 4, b);
        }// Case 6: A→C→B→E→D→F
        else if (caseIndex == 6) {
            cities.set(startingIndex + 1, c);
            cities.set(startingIndex + 2, b);
            cities.set(startingIndex + 3, e);
            cities.set(startingIndex + 4, d);
        }// Case 7: A→E→D→B→C→F
        else if (caseIndex == 7) {
            cities.set(startingIndex + 1, e);
            cities.set(startingIndex + 2, d);
            cities.set(startingIndex + 3, b);
            cities.set(startingIndex + 4, c);
        }

        return cities;
    }

    // Returns the city to copy from
    private static City copyCity(City city) {
        return new City(city.id, city.x, city.y);
    }

    // Calculates the distance between 2 given cities
    public static int distance(City city1, City city2) {
        double dx = city1.x - city2.x;
        double dy = city1.y - city2.y;
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }
}