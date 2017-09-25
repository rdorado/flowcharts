package rdorado.flowcharts.core;

public class Point {
	int x;
	int y;
	int arc;
	public Point() {}
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Point(int x, int y, int arc) {
		this.x=x;
		this.y=y;
		this.arc=arc;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
