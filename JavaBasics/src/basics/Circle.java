package basics;

public class Circle implements Shape {

	private double radious;

	public Circle(double radious) {
		this.radious = radious;
	}

	@Override
	public double calArea() {
		// TODO Auto-generated method stub
		return Math.PI * radious * radious;
	}

}
