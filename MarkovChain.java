package Assignment1;

public class MarkovChain {
	private Vector stateVector;
	private Matrix transitionMatrix;
	
	//Initalizes the MarkovChain object using stateVectorand transitionMatrix parameters
	public MarkovChain (Vector sVector, Matrix tMatrix) {
		this.stateVector = sVector;
		this.transitionMatrix = tMatrix;
	}
	
	//Checks if the transitionMatrixand stateVectors are valid
	public boolean isValid() {
		double sum = 0;
		//If the rows  of the  transition Matrix does  not equal to the columns, i.e. it is not a square matrix, not Valid
		if(this.transitionMatrix.getNumRows() != this.transitionMatrix.getNumCols()) {
			return false;
		}
		
		//Adds up the values of the statevector inputs and checks if sum is 1
		for (int i = 0; i < this.stateVector.getNumCols(); i++) {
			sum += this.stateVector.getElement(i);
		}
		
		//Adds up rows of each transition matrix and checks if rowsum = 1
		for (int x = 0; x < this.stateVector.getNumCols(); x++) {
			double rowsum = 0;
			for (int y = 0; y < this.stateVector.getNumCols(); y++) {
				rowsum += this.transitionMatrix.getElement(x,y);
			}
			//To ensure that is equal to one, accounting for rounding  errors
			if(!(rowsum > 0.99 && rowsum < 1.01)) {
				return false;
			}
		}
		//To ensure that is equal to one, accounting for rounding  errors
		if(!(sum > 0.99 && sum < 1.01)) {
			return false;
		}
		//If none of the checks pass, then the transitionMatrix and stateVector is valid
		return true;
	}
	
	//Computes the probabilityMatrix using parameter of number of steps
	public Matrix computeProbabilityMatrix(int numSteps) {
		//Defining the matrix for the Steady State
		int stateVectorColNum = stateVector.getNumCols();
		Matrix steadyStateVector = new Matrix(1, stateVectorColNum, this.stateVector.getData()[0]);
		
		//Checks if the transition matrix and state vectors are defined for markov chains
		if(isValid() == false) {
			return null;
		}
		
		//Multiplies the vector by the transition matrix until get to the steady state
		for(int i = 0; i < numSteps; i++) {
			steadyStateVector = steadyStateVector.multiply(this.transitionMatrix);
		}
		return steadyStateVector;
	}
	public static void main (String[] args) {
		Vector sv = new Vector(3, new double[] {0.3, 0.5, 0.2});
		Matrix tm = new Matrix(3, 2, new double[] {0.5, 0.0, 0.5, 0.2, 0.7, 0.1});		
		MarkovChain markov = new MarkovChain(sv, tm);
		
		System.out.print(markov.computeProbabilityMatrix(1));
	}
}
