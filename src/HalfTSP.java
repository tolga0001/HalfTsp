import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HalfTSP {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input: ");
        String inputName = scanner.next();
        ArrayList<City> cities = readCities(inputName);

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


}
