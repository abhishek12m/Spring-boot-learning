package basics;

public class Outer {
	private int x=10;
	
	class Inner{
		void display() {
			System.out.println("Value of x: "+x);
		}
	}

}
