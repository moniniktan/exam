import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		File file1 = new File("testData_Apartments.txt");

		Scanner input = null;

		Map<String, Integer> cityApCounter = new HashMap<>();
		Set<String> validCities = new HashSet<>();
		validCities.add("София"); validCities.add("Варна"); validCities.add("Бургас");
		int numberOfValidApartments = 0;
		Map<Apartment, Integer> cheapestApartments = new HashMap<>();

		try {
			input = new Scanner(file1);

			while (input.hasNext()) {
				String city = input.next();
				int numberOfRooms = input.nextInt();
				int area = input.nextInt();
				int price = input.nextInt();
				String number = input.next();

				if (area > 100 && numberOfRooms == 3 && validCities.contains(city)) {
					numberOfValidApartments++;
				} else continue;

				Apartment a = new Apartment(city, numberOfRooms, area, price, number);

				if(cityApCounter.containsKey(city)) {
					cityApCounter.put(city, cityApCounter.get(city) + 1);
				} else {
					cityApCounter.put(city, 1);
				}

				cheapestApartments.put(a, price);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			assert input != null;
			input.close();
		}

		try {
			if (numberOfValidApartments == 0) {
				throw new UnsuitableApartmentsException("No valid apartments found");
			}
		} catch (UnsuitableApartmentsException uae) {
			System.out.println(uae.getMessage());
		}

		List<Map.Entry<Apartment, Integer>> sortedCheapestApartments = new ArrayList<>(cheapestApartments.entrySet());
		sortedCheapestApartments.sort(Map.Entry.comparingByValue());

		List<Map.Entry<String, Integer>> sortedMap = new ArrayList<>(cityApCounter.entrySet());
		sortedMap.sort(Map.Entry.comparingByValue());

		File file2 = new File("output_Apartments.txt");
		if (file2.exists()) {
			System.out.println("File already exists");
			System.exit(1);
		}
		PrintWriter output = null;

		try {
			output = new PrintWriter(file2);

			int i = 0;
			for(Map.Entry<Apartment, Integer> entry : sortedCheapestApartments) {
				if (i == 5) break;
				i++;
				output.println(entry.getKey().getNumber());
			}
			output.println();

			i = 0;
			for (Map.Entry<String, Integer> entry : sortedMap) {
				if (i == 3) break;
				i++;
				output.print(entry.getKey() + " " + entry.getValue());
				output.println();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			assert output != null;
			output.close();
		}
	}
}
