package makeMyTrip;

public class GenericClassesLearning <T> {

		T obj;
		
		public void set(T obj) {
		this.obj = obj;	
		}
		
		public T get() {
			return obj;
		}

}

