package util;

public class MyMath {

	public static double slope(int x1, int y1, int x2, int y2) {
		if(x1==x2) return Double.POSITIVE_INFINITY;
		return (y2 - y1)/(x2 - x1);
	}
	public static double getAngle(int x1, int y1, int x2, int y2, int x3, int y3) {
		double a = distance(x1,y1,x2,y2);
		double b = distance(x2,y2,x3,y3);
		double c = distance(x1,y1,x3,y3);
		
		return Math.acos(  (Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2))/(2*a*b) );
	}
	
	public static double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt( Math.pow(x2-x1,2) + Math.pow(y2-y1,2)); 
	}

}
