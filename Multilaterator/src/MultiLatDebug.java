import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiLatDebug {
	public static void main(String[] args) {
		double xA = .5;
		double yA = 0;
		double xB = -.5;
		double yB = 0;
		double xC = 0;
		double yC = 1;
		double speed = 1481;
		double delayA = 7.07023e-4; //delays in e notation
		double delayB = 2.63474e-4;
		
		
		MultiLatDebug program = new MultiLatDebug();
		
		
		double beginX = -5;
		double beginY = 0;
		double endX = 0;
		double endY = 5;
		double x = beginX;
		double y = beginY;
		double multiplier = .1;
//		double minimumClosenessA = program.range((program.distance(xC, yC, x, y) + (speed*delayA)), program.distance(xA, yA, x, y));
//		double minimumClosenessB = program.range((program.distance(xC, yC, x, y) + (speed*delayB)), program.distance(xB, yB, x, y));
		double minimumTotalCloseness = 100;
		List<Double> pointOfBestMatch = new ArrayList<Double>();
		pointOfBestMatch.add(0.0);
		pointOfBestMatch.add(0.0);
		
		while(y < endY) {
			while(x < endX) {
				double closenessA = program.doesPointFitHyperbola(x, y, xA, yA, xC, yC, speed, delayA);
				double closenessB = program.doesPointFitHyperbola(x, y, xB, yB, xC, yC, speed, delayB); //since we're using a non-infinite resolution, the distances have to be close enough
				double totalCloseness = program.range(closenessA, closenessB);
				if (totalCloseness < minimumTotalCloseness) {
					pointOfBestMatch.set(0, x);
					pointOfBestMatch.set(1, y);
//					minimumClosenessA = closenessA;
//					minimumClosenessB = closenessB;
					minimumTotalCloseness = totalCloseness;
				}
				
//				System.out.print(calculatedDistanceA + "\t" + distanceA +"\t");
//				System.out.print(calculatedDistanceB + "\t" + distanceB +"\t");
				System.out.print(closenessA);
				
				System.out.print("\t" + x);
				System.out.print("\t" + y);
				System.out.println("\t" + minimumTotalCloseness);
				x+=multiplier;
				}
			x=beginX;
			y+=multiplier;
		}
		System.out.println(pointOfBestMatch);
	}
		
	public double range (double a, double b) { //method that compares two numbers and returns range
		return (Math.max(a, b)- Math.min(a, b));
	}
	public double distance(double x1, double y1, double x2, double y2) {
		return (Math.sqrt((Math.pow((x2-x1), 2) + (Math.pow((y2-y1), 2))))); //Distance between three points (distance formula)
	}
	public double doesPointFitHyperbola (double x, double y, double xDelay, double yDelay, double xReference, double yReference, double speed, double delay) {
		double deltaA = (speed*delay);
		double distanceA = distance(xDelay, yDelay, x, y);
		double calculatedDistanceA = distance(xReference, yReference, x, y) + deltaA;
		double closenessA = range(calculatedDistanceA, distanceA);
		return closenessA;
	}

		
	
	
	
	
}