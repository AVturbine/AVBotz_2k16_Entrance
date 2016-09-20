
//IT WORKS IT WORKS MY CREATION FINALLY WORKS
//I SWEAR I WASTED 10HRS OF MY LIFE ON THIS
//HELL YEAH


import java.math.BigDecimal; // needed for the rounding
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; //more useful than Scanner for user input

public class MultiLatFinal_RUN_THIS {
	
	public static void main(String[] args) {
		MultiLatFinal_RUN_THIS mlat = new MultiLatFinal_RUN_THIS();
		
		double[] delays = mlat.initialize();
		double delayA = delays[0];
		double delayB = delays[1];
		int precision = (int)(delays[2]); // a lone int in the sea of doubles
		double xA = .5;
		double yA = 0;
		double xB = -.5;
		double yB = 0;
		double xC = 0;
		double yC = 1;
		double speed = 1481;
		double numberOfIterations = precision + 2; // Defining some constants here, this program may or may not work if you change them
		double initialMultiplier = 1;
		double initialStartX = -100;
		double initialStartY = -100;
		double initialEndX = 100;
		double initialEndY = 100;
		double finalX = 0;
		double finalY = 0;
		
		while (numberOfIterations > 0) {
			List<Double> point = mlat.loopThroughBox(initialStartX, initialEndX, initialStartY, initialEndY, initialMultiplier, xA, yA, xB, yB, xC, yC, speed, delayA, delayB);
			double x = point.get(0); 
			double y = point.get(1);
			initialMultiplier = initialMultiplier/10; // increases resolution by a factor of 10 every step through
			initialStartX = x-((initialEndX-initialStartX)*(initialMultiplier/2)); // decreases scope 20fold each step through (centered on best point)
			initialEndX = x+((initialEndX-initialStartX)*(initialMultiplier/2));
			initialStartY = y-((initialEndY-initialStartY)*(initialMultiplier/2));
			initialEndY = y+((initialEndY-initialStartY)*(initialMultiplier/2));
			numberOfIterations--;
			finalX = x;
			finalY = y;
		}
		finalX = mlat.roundAnswer(finalX, precision); // rounds x and y (noisy doubles) to the relevant parts
		finalY = mlat.roundAnswer(finalY, precision);
		JOptionPane.showMessageDialog(null, "The coordinates of the pinger are (" + finalX + "," + finalY + ")."); // shows the result (may be incorrect)
	}
	
	
	public double[] initialize() { // method that takes user input and passes them back in a double array
		String delayAString = JOptionPane.showInputDialog("What is the TDoA, from Receiver C to Receiver A?");
		double delayA = Double.parseDouble(delayAString);
		String delayBString = JOptionPane.showInputDialog("What is the TDoA, from Receiver C to Receiver B?");
		double delayB = Double.parseDouble(delayBString);
		String precisionString = JOptionPane.showInputDialog("How many decimal places do you want your answer to?");
		double precision = Double.parseDouble(precisionString);
		double[] returnString = {delayA, delayB, precision};
		return returnString;
	}
	
	public double range (double a, double b) { //method that compares two numbers and returns range
		return (Math.max(a, b)- Math.min(a, b));
	}
	
	public double distance(double x1, double y1, double x2, double y2) { //Distance between three points (distance formula)
		return (Math.sqrt((Math.pow((x2-x1), 2) + (Math.pow((y2-y1), 2))))); 
	}
	
	public double doesPointFitHyperbola (double x, double y, double xDelay, double yDelay, double xReference, double yReference, double speed, double delay) { // Basically, this method compares the actual distance (from check point to receiver) with the calculated distance (distance to closest receiver + delay*speed). Returns similarity, with 0 being perfect.
		double deltaA = (speed*delay);
		double distanceA = distance(xDelay, yDelay, x, y);
		double calculatedDistanceA = distance(xReference, yReference, x, y) + deltaA;
		double closenessA = range(calculatedDistanceA, distanceA);
		return closenessA;
	}
	
	
	public List<Double> loopThroughBox(double xStart, double xEnd, double yStart, double yEnd, double multiplier, double xA, double yA, double xB, double yB, double xC, double yC, double speed, double delayA, double delayB) { // holy doubles batman. 
		
		double x = xStart;
		double y = yStart;
		double minimumClosenessA = this.range((this.distance(xC, yC, x, y) + (speed*delayA)), this.distance(xA, yA, x, y));
		double minimumClosenessB = this.range((this.distance(xC, yC, x, y) + (speed*delayB)), this.distance(xB, yB, x, y));
		List<Double> pointOfBestMatch = new ArrayList<Double>();
		pointOfBestMatch.add(x); // puts some starting values in the list
		pointOfBestMatch.add(y);
		
		
		while(y < yEnd) { //Loops through a grid of x and y points at a specified resolution, and returns the point closest to the intersection of the two hyperbolas.
			while(x < yEnd) { 
				double closenessA = this.doesPointFitHyperbola(x, y, xA, yA, xC, yC, speed, delayA);
				double closenessB = this.doesPointFitHyperbola(x, y, xB, yB, xC, yC, speed, delayB); //since we're using a non-infinite resolution, the distances have to be close enough
				if (closenessA < minimumClosenessA && closenessB < minimumClosenessB) {
					pointOfBestMatch.set(0, x);
					pointOfBestMatch.set(1, y);
					minimumClosenessA = closenessA;
					minimumClosenessB = closenessB;
				}
				x+=multiplier;
				}
			x=xStart;
			y+=multiplier;
		}
		return pointOfBestMatch;
		
	}
	public double roundAnswer (double input, int numDP) { // some code that rounds a double to specified number of places
			BigDecimal bigDecimal = new BigDecimal(input);
	        bigDecimal = bigDecimal.setScale(numDP, BigDecimal.ROUND_HALF_UP);
	        return bigDecimal.doubleValue();
	}
}