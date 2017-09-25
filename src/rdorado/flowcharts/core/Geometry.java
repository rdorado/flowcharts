package rdorado.flowcharts.core;

public class Geometry {

	public static boolean inRectangle(Point p1, Point p2, int x, int y) {
		int minx = Math.min(p1.x,p2.x);
		int maxx = Math.max(p1.x,p2.x);
		int miny = Math.min(p1.y,p2.y);
		int maxy = Math.max(p1.y,p2.y);
		return (x>=minx&&x<=maxx&&y>=miny&&y<=maxy);
	}
	
	public static double distance(int p1x, int p1y, int p2x, int p2y, int x, int y){
		double num = Math.abs((p2y - p1y)*x - (p2x-p1x)*y + p2x*p1y - p2y*p1x);
		double den = Math.sqrt(Math.pow(p2y-p1y, 2) + Math.pow(p2x-p1x, 2));
	
		return num/den;
	}

	public static boolean isInRectangle(int p1x, int p1y, int p2x, int p2y, int x, int y) {
		int minx = Math.min(p1x,p2x);
		int maxx = Math.max(p1x,p2x);
		int miny = Math.min(p1y,p2y);
		int maxy = Math.max(p1y,p2y);
		return (x>=minx&&x<=maxx&&y>=miny&&y<=maxy);
	}
	
	public static boolean isInCircle(int p1x, int p1y, int p2x, int p2y, int x, int y){
		double mx = Math.abs((p1x+p2x)/2);
		double my = Math.abs((p1y+p2y)/2);
		double r = Math.sqrt( Math.pow(mx-p1x,2) + Math.pow(my-p1y,2) );
		double d = Math.sqrt( Math.pow(mx-x,2) + Math.pow(my-y,2) );
		return d<r+3;
	}

}
