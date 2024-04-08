package basics;

public class MyClass {
	
//	void display() {
//		MyFunctionalInterface obj=()->System.out.println("Lambda Expression");
//		obj.myMethod();
//	}
	
	
	void display(MyFunctionalInterface obj) {
		obj.myMethod();
	}

}
