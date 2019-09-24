import org.apache.poi.ss.formula.functions.T;

import makeMyTrip.GenericClassesLearning;

public class GenericClassRunner {

	public static void main(String[] args) {

		GenericClassesLearning<Student> obj1 = new GenericClassesLearning<Student>();

		obj1.set(new Student(10484475, "Alok Shrivastava", 9035888848L));

		Student returnValue = obj1.get();

		System.out.println("Student Id " + returnValue.getsId() + "Student Name " + returnValue.getsName()
				+ "Student Contact No. " + returnValue.getsContact());

	}

}
