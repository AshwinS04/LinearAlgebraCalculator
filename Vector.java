package Assignment1;

public class Vector extends Matrix{
	//Initalizes a Vector object, a single row matrix and sets all values to 0 using Matrix intalizer
	public Vector (int c) {
		super(1,c);
	}
	
	//Initalizes a Vector object, a single row matrix and sets all values to the values of linArr using Matrix intalizer
	public Vector(int c, double[] linArr) {
		super(1, c, linArr);
	}
	
	//Returns the element in the index of parameter c
	public double getElement (int c) {
		return this.getElement(0,c);
	}
}
