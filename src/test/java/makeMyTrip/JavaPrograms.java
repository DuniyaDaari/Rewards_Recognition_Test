package makeMyTrip;

import java.util.HashMap;
import java.util.Map;

public class JavaPrograms {

	public static void main(String[] args) {
		JavaPrograms j = new JavaPrograms();

		String reversStr = j.reverseStringUsingRecursion("Guru99");
		System.out.println(reversStr);

		int[] reverseArr = j.reverseArrayWithoutThirdArray(new int[] { 1, 2, 43, 47, 92, 67, 14, 6, 2 });
		for (int i : reverseArr) {
			System.out.print(i + " ");
		}

		System.out.println();
		Map<String, Integer> map1 = new HashMap<>();
		map1.put("Alok", 1);
		map1.put("Abhinav", 2);
		map1.put("Abhishek", 3);
		map1.put("Aashirwad", 4);

		Map<String, Integer> map2 = new HashMap<>();
		map2.put("Mayank", 4);
		map2.put("Alok", 1);
		map2.put("Sarvesh", 6);
		map2.put("Manu", 7);

		j.compareHashMap(map1, map2);
	}

	// Reverse a String using recursion
	public String reverseStringUsingRecursion(String str) {
		while (!str.isEmpty()) {
			String str1 = str.substring(1);// lok
			return reverseStringUsingRecursion(str1) + str.charAt(0);
		}
		return str;
	}

	// Array Reverse without using third array
	public int[] reverseArrayWithoutThirdArray(int[] arr) {
		int j = 0;
		int temp;
		for (int i = arr.length - 1; i > arr.length / 2; i--, j++) {
			temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
		return arr;
	}

	// Comparing two Hashmap

	public boolean compareHashMap(Map<String, Integer> map1, Map<String, Integer> map2) {

		if (map1.size() != map2.size()) {

			System.out.println("Given maps size are not equal");
			return false;
		} else {
			int status = 0;
			for (Map.Entry<String, Integer> entry : map1.entrySet()) {
				String key1 = entry.getKey();
				int value1 = entry.getValue();
				if (map2.containsKey(key1)) {
					int value2 = map2.get(key1);
					if (value1 == value2) {
						System.out.println(key1 + "," + value1 + " this pair is available in other map");
						status = 1;
					} else {
						System.out.println(key1 + " key is available but correspondent " + value1
								+ " value is not available in other map");
						status = 0;
					}
				} else {
					System.out.println(key1 + " key is not available in map2");
					status = 0;
				}
			}

			if (status == 1) {
				System.out.println(" Given maps are  equal");
				return true;
			} else {
				System.out.println(" Given maps are not equal");
				return false;
			}

		}

	}
}
