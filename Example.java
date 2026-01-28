import java.util.ArrayList;
import java.util.HashMap;

public class Example {

	public static void main(String[] args) {
		
		
		//key, value pair
		//first String is a unique String that MAPS to a value
		
		// HashMap<Object, Object> myMap = new HashMap(<Object, Object>();
		HashMap<String, String> myMap = new HashMap<String, String>();
		ArrayList<String> myList = new ArrayList<String>();
		
		String key = "the";
		
		//Capitals
		myMap.put("England", "London");
		myMap.put("India", "New Delhi");
		myMap.put("Norway", "Oslo");
		myMap.put("Philippines", "Manila");
		
		
		myList.add("person");
		myList.add("student");
		myList.add("teacher");
		
		HashMap<String, ArrayList<String>> words = new HashMap<String, ArrayList<String>>();
		
		words.put(key, myList);
		
		System.out.println(words);
		
		//check if something exists
		System.out.println(words.containsKey("the"));
		System.out.println(words.containsKey("person"));

		
		}

}
