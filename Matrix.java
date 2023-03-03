package Assignment1;

public class Matrix {
	
	private int numRows;
	private int numCols;
	private double[][] data;
	
	//Initializes the Matrix object and sets all elements to 0 if 
	public Matrix (int r, int c) {
		this.data = new double[r][c];
		
		//Sets all values in the array to 0
		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				this.data[i][j] =  0.0;
		
		this.numRows = r;
		this.numCols = c;
	}
	//Initializes a Matrix object and sets the elements from linArr in the Matrix
	public Matrix (int r, int c, double[] linArr) {
		//Initializes values for number of rows and columns respectively
		this.numRows = r;
		this.numCols = c;
		
		//Initializes a temporary array to store the contents of the array, then set to data
		double[][] hold = new double[numRows][numCols];
		
		//rowNum is -1, then goes to 0 on the first instance then goes till the 
		int rowNum = -1;
		//For each element in linArr, add element to 
		for (int j = 0; j < linArr.length; j++) {
			//If j gets to the end of the row,  then it should go to the next row
			if (j % numCols == 0) {
				rowNum++;
			}
			/*Sets element in temporary array to the value in linear array
			 *j%numCols tells us the column number, as j interates through the for loop,
			 *the modulus ensures that the columns are in the right spot*/
			hold[rowNum][j%numCols] = linArr[j];
		}
		//Set data = hold
		this.data = hold;
	}
	
	//Getter method, returns number of rows in the Matrix object
	public int getNumRows() {
		return numRows;
	}
	
	//Getter method, returns number of columns in the Matrix object
	public int getNumCols() {
		return numCols;
	}
	
	//Getter method, returns data array in the Matrix object
	public double[][] getData(){
		return data;
	}
	
	//Getter method, returns element given parameter of row and column location
	public double getElement(int r, int c) {
		return data[r][c];
	}
	
	//Setter method, sets value of array given row and column location
	public void setElement(int r, int c, double value) {
		data[r][c] = value;
	}
	
	//Transposes matrix; swaps column space with row space
	public void transpose() {
		//Sets a temporary matrix that will store the columns of this matrix in the rows, and the rows in the columns
		double [][] hold = new double [numCols][numRows] ;
		
		//Sets row space of matrix with colun space of vectors
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				hold[j][i] = this.getElement(i,j);
			}
		}
		//Sets data = hold
		this.data = hold;
		//Holds number of rows so when numRows = numCols, then numCols = numRows without duplicates
		int save = this.numRows;
		
		this.numRows = numCols;
		this.numCols = save;
	}
	
	//Multiplies matrix by scalar, only works when parameter is a double
	public Matrix multiply (double scalar) {
		//Creates a new matrix object to return after the multiplication
		Matrix object1 = new Matrix(numRows, numCols);
		
		//Interates through each element of the array, multiplies each element by the scalar
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				object1.setElement(i, j, getElement(i,j) * scalar);
			}
		}
		return object1;
	}
	
	/*Used for the matrix multiplication, takes two rows and finds the dotProduct, value of each element
	 *of the multiplied matrix  given the  rows of the first and the columns of the others*/
	private double dotProduct (double[] array1, double[] array2) {
		double value = 0;
		
		//Checks if the two arrays are equivilent in length, they must be but just in case
		if (array1.length != array2.length) {
			return 0;
		}
		/*For each element in the array, it will multiply the value at the index of the first array with the value 
		 * of the same index at the second array, then  takes the sum of all of those values and returns the value
		 */
		else {
			double sum = 0;
			for (int i = 0; i < array1.length; i++) {
				sum = array1[i] * array2[i];
				value += sum;
			}
		}
		
		return value;
	}
	
	//Matrix multiplication:
	public Matrix multiply (Matrix other) {
		//First this checks if the Matrix multiplication is defined. It is defined if the first matrix's columns is equal to the second matrix's rows,
		if(this.getNumCols() != other.getNumRows()) {
			return null;
		}
		else {
			//Creates two matrix objects, object1 which will be the multiplied matrix that will be returned, and otherTranposable which is used to ensure other
			//Is not changed; helps in the case where a matrix is multiplied by itself
			Matrix object1 = new Matrix(this.numRows, other.getNumCols());
			Matrix otherTransposable = new Matrix(other.getNumRows(), other.getNumCols());
			
			//This will take each element from other, and set this to each element of otherTransposable
			for (int u = 0; u < other.numRows; u++) {
				for (int v = 0; v < other.numCols; v++) {
					otherTransposable.setElement(u,v,other.getElement(u, v));
				}
			}
			
			//Now, transpose other transposable. This way, the matrix multiply row by rows and this allows for simplicity and three for loops
			otherTransposable.transpose();
			
			//Define two arrays; they are both the same length and the dot product of these two arrays is going to be the value put in the matrix multiplication element
			double [] array1 = new double [this.numCols];
			double [] array2 = new double [otherTransposable.numCols];
			
			/*Interate through each element in the rows of the first Matrix, then interate through each column of the second matrix; this is the size of the multiplied matrix
			 *Then, for each row in the fist matrix, iterate between each row in the second matrix and take the dot product of the two arrays
			 */
			for (int i = 0; i < this.numRows; i++) {
				for (int j = 0; j < otherTransposable.getNumRows(); j++) {
					for (int y = 0; y < this.numCols; y++) {
						array1[y] = this.getElement(i,y);
						array2[y] = otherTransposable.getElement(j,y);
					}
					//Set the element of the multiplied matrix as the dot product between the two arrays
					object1.setElement(i,j, dotProduct(array1, array2));
				}
			}
			return object1;
		}
	}
	
	//Will take the values of the Matrix and return it as one string
	public String toString() {
		//Set an overall string
		String overall = "";
		
		//If the matrix has no entries and is a matrix with no columns ofr rows, return the string "Empty matrix"
		if(this.getNumCols() == 0 &&  this.getNumRows() == 0) {
			overall = "Empty matrix";
		}
		
		/*If the matrix has values, interate through each element of the for loop and return the values
		 *ensuring that the values each have 8 total spaces and 3 decimal places. Then append this value to 
		 *overall string, append a new line for each time a row ends, and  return the overall string
		 */
		else{
			for (int i = 0; i < this.numRows; i++) {
				for (int j = 0; j < this.numCols; j++) {
					String line1 = String.format("% 8.3f", this.getElement(i, j));
					overall += line1;
				}
				overall += "\n";
			}
		}
		
		return overall;
	}
	public static void main (String[] args) {
		Matrix sv = new Matrix(3, 2, new double[] {0.3, 0.5, 0.2, 0, 0, 0});
		Matrix tm = new Matrix(2, 3, new double[] {0.5, 0.0, 0.5, 0.2, 0.7, 0.1});		
		
		
		System.out.print(sv.multiply(tm));
	}
}