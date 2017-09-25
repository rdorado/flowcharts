package rdorado.flowcharts.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rdorado.flowcharts.gui.DocumentCanvas;
import rdorado.flowcharts.gui.DragType;
import util.MyMath;

public class ArrowElement extends GraphicalRelationElement{
	
	int arc;
	String from_side="";
	String to_side="";
	
	public ArrowElement(GraphicalTextualElement from, GraphicalTextualElement to) {
		super(from, to);
		this.text="";
	}
	
	
	
	public ArrowElement(Node node, DocumentCanvas dc){
		super();
		id = Integer.parseInt( ((Element)node).getAttribute("id") );
		text =  ((Element)node).getAttribute("text") ;
		int iFrom = Integer.parseInt( ((Element)node).getAttribute("from") );
		int iTo = Integer.parseInt( ((Element)node).getAttribute("to") );
		
		from = (GraphicalTextualElement)dc.getById(iFrom);
		to = (GraphicalTextualElement)dc.getById(iTo);
		
		from_side = ((Element)node).getAttribute("from_side");
		to_side = ((Element)node).getAttribute("to_side");
		
		NodeList list = node.getChildNodes();
		for (int i=0;i<list.getLength();i++) {
			if(list.item(i).getNodeName().equals("node")){
				int x = Integer.parseInt( ((Element)list.item(i)).getAttribute("x") );
				int y = Integer.parseInt( ((Element)list.item(i)).getAttribute("y") );
				int arc = Integer.parseInt( ((Element)list.item(i)).getAttribute("arc") );				
				
			
				nodes.add(new NodeElement(x, y, arc));
				
				
				//System.out.println("-->x="+x+", y="+y+", arc="+arc);
			}
			
		}
		
		
	}	
	
	public ArrowElement(GraphicalTextualElement from, GraphicalTextualElement to, String text) {
		//super(from, to);
		this.text=text;
	}	
	/*
	Point getInitialPoint(){
		int x1 = from.x + (from.width/2);
		int y1 = from.y + from.height;
		
		if(from_side.equals("R")){
			x1 = from.x + from.width;
			y1 = from.y + (from.height/2);
		}
		return new Point(x1,y1);
	}*/
	
	Point getComponentPoint(GraphicalTextualElement component, String direction){
		int x1 = component.x + (component.width/2);
		int y1 = component.y + (component.height/2);
		
		if(component instanceof NodeElement){
			return new Point(x1,y1);
		}
		
		if(direction.equals("U")){
			y1 = component.y;
		}
		else if(direction.equals("D")){
			y1 = component.y + component.height;
		}
		else if(direction.equals("L")){
			x1 = component.x;
		}
		else if(direction.equals("R")){
			x1 = component.x + component.width;
		}
		else{
			y1 = component.y;
		}
		return new Point(x1,y1);
	}
	
	ArrayList<Point> getPoints(){
		ArrayList<Point> points = new ArrayList<Point>();
		
		//points.add(new Point(x1, y1));
		points.add( getComponentPoint(from,from_side) );
		
		for (NodeElement node : nodes) {
			points.add(new Point(node.x, node.y, node.arc));
		}
		
		int x2 = to.x + (to.width/2);
		int y2 = to.y;	
		
		if(to_side.equals("L")){
			x2 = to.x;
			y2 = to.y + (to.height/2);
		}
		else if(to_side.equals("R")){
			x2 = to.x + to.width;
			y2 = to.y + (to.height/2);			
		}
		
		points.add( getComponentPoint(to,to_side) );
		
		return points;
	}
	
	@Override
	void paintElement(Graphics g) {
		/*int x1 = from.x + (from.width/2);
		int y1 = from.y + from.height;
		
		
	
		
		
		
		if(from_side.equals("R")){
			System.out.println(from_side);
			x1 = from.x + from.width;
			y1 = from.y + (from.height/2);
		}*/
		
		//ArrayList<Point> points = new ArrayList<Point>();
		
		/*
		points.add(new Point(x1, y1));
		
		for (NodeElement node : nodes) {
			points.add(new Point(node.x, node.y, node.arc));
			x1=node.x;
			y1=node.y;
		}
		
		int x2 = to.x + (to.width/2);
		int y2 = to.y;		
		
		if(to_side.equals("L")){
			x2 = to.x;
			y2 = to.y + (to.height/2);
		}
		else if(to_side.equals("R")){
			x2 = to.x + to.width;
			y2 = to.y + (to.height/2);			
		}
		
		points.add(new Point(x2,y2));*/
		
		/** Paint lines */
		ArrayList<Point> points = getPoints();
		drawPolyline(points, g);
		
		/** Paint text */
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(text, g);
		int textHeight = (int)(rect.getHeight()); 
		int textWidth  = (int)(rect.getWidth());
		
		int x1 = points.get(0).x;
		int y1 = points.get(0).y;
		
		int x2 = points.get(1).x;
		int y2 = points.get(1).y;
		
		double lineAngle = Math.atan2(y2-y1, x1-x2);
		int tx = x1 - (int)(Math.cos(lineAngle + 0.810)*12);
		int ty = y1 + (int)(Math.sin(lineAngle + 0.810)*12);
		//g.drawString(text, tx - (textWidth/2), ty + (textHeight/2));
		g.drawString(text, x1+6, ty + (textHeight/2));
		
		
		/** Paint arrow */
		if( !(to instanceof NodeElement)){
			x1 = points.get(points.size()-2).x;
			y1 = points.get(points.size()-2).y;
			
			x2 = points.get(points.size()-1).x;
			y2 = points.get(points.size()-1).y;			
			
			int l = 10;
			int a = 10;		
			
			double arrowAngle =  Math.atan((a/2.0)/l);
			lineAngle = Math.atan2(y2-y1, x1-x2);
			
			int xp1 = x2 + (int)(Math.cos(lineAngle-arrowAngle)*l);
			int yp1 = y2-(int)(Math.sin(lineAngle-arrowAngle)*l);
			
			int xp2 = x2 + (int)(Math.cos(lineAngle+arrowAngle)*l);
			int yp2 = y2 - (int)(Math.sin(lineAngle+arrowAngle)*l);
			
			//int tx = x1 - (int)(Math.cos(lineAngle + 0.700)*12);
			//int ty = y1 + (int)(Math.sin(lineAngle + 0.700)*12);
			
		
			g.fillPolygon(new int[]{x2, xp1, xp2},new int[]{y2, yp1, yp2}, 3);
			
		}

	}
	
	@Override
	public String getAsSVG() {
		
		String resp ="";
		
		/** Paint line*/
		ArrayList<Point> points = getPoints();
		String strPoints = "";
		for(Point p: points){
			strPoints+=p.x+","+p.y+" ";
		}
		resp+="<polyline points=\""+strPoints+"\" style=\"fill:none;stroke:black;stroke-width:1\" />\n";  //fill-opacity=\"0\"
		
		int x1 = points.get(0).x;
		int y1 = points.get(0).y;
		
		int x2 = points.get(1).x;
		int y2 = points.get(1).y;
		
		double lineAngle = Math.atan2(y2-y1, x1-x2);
		
		/** Paint text*/		
		if(text!=null && !text.equals("")){
			int textHeight = GraphicalTextualElement.SVG_FONT_SIZE; 
			int textWidth  = text.length();			
			
			int tx = x1 - (int)(Math.cos(lineAngle + 0.700)*12);
			int ty = y1 + (int)(Math.sin(lineAngle + 0.700)*12);
			resp+="<text font-family=\"Verdana\" font-size=\""+GraphicalTextualElement.SVG_FONT_SIZE+"\" x=\""+(x1+4)+"\" y=\""+(ty + 2)+"\">"+text+"</text>\n";
		}
				
		/** Paint arrow*/
		if( !(to instanceof NodeElement)){
			x1 = points.get(points.size()-2).x;
			y1 = points.get(points.size()-2).y;
			
			x2 = points.get(points.size()-1).x;
			y2 = points.get(points.size()-1).y;
			
			
			int l = 10;
			int a = 10;		
			
			double arrowAngle =  Math.atan((a/2.0)/l);
			lineAngle = Math.atan2(y2-y1, x1-x2);
			
			int xp1 = x2 + (int)(Math.cos(lineAngle-arrowAngle)*l);
			int yp1 = y2-(int)(Math.sin(lineAngle-arrowAngle)*l);
			
			int xp2 = x2 + (int)(Math.cos(lineAngle+arrowAngle)*l);
			int yp2 = y2 - (int)(Math.sin(lineAngle+arrowAngle)*l);
			
			resp+="<polyline points=\""+x2+","+y2+" "+xp1+","+yp1+" "+xp2+","+yp2+" "+x2+","+y2+"\" style=\"fill:black;stroke:black;stroke-width:1\" />\n";  //fill-opacity=\"0\"
		}
		return resp;
	}
	
	
	public boolean collide(int x, int y){
		
		ArrayList<Point> points = getPoints();
		Point p1 = points.get(0);

		for(int i=1;i<points.size();i++){
			Point p2 = points.get(i);
			
			double dy = p1.y-p2.y;
			double dx = p1.x-p2.x;
			
			if(dy==0){
				
				if(Geometry.isInRectangle(p1.x,p1.y-2,p2.x,p2.y+2, x,y)){
					return true;
				}
			}
			else if (dx==0){
				if(Geometry.isInRectangle(p1.x-2,p1.y,p2.x+2,p2.y, x,y)){
					return true;
				}
			}
			else{
				if(Geometry.isInCircle(p1.x-2,p1.y,p2.x+2,p2.y, x,y) && Geometry.distance(p1.x-2,p1.y,p2.x+2,p2.y, x,y)<3){
					return true;	
				}
			}

			p1=p2;
		}
		return false;
	};
	
	public void stopMoving(){
		for (NodeElement node:nodes) {
			node.setMoving(false);
		}
	}
	
	String getBestDirectionAsString(GraphicalTextualElement component, Point p1){
		Point p2 = component.getMiddlePoint();
		String resp="";
		double dx = p2.x-p1.x;
		double dy = p2.y-p1.y;
		if(dy>0){
			
			if(dx==0){
				resp="U";
			}
			else{
				double m = dy/dx;
				if(Math.abs(m)>1){
					resp="U";
				}
				else if(dx>0){
					resp="L";
				}
				else{
					resp="R";
				}
			}
			
			
		}
		else if (dy==0){
			if(dx>0){
				resp="L";
			}
			else{
				resp="R";
			}
		}
		else{
			if(dx==0){
				resp="D";
			}
			else{
				double m = dy/dx;
				if(Math.abs(m)>1){
					resp="D";
				}
				else if(dx>0){
					resp="L";
				}
				else{
					resp="R";
				}
			}
		}
		return resp;
	}
	
	public void move(int x, int y) {
		
		for (NodeElement node:nodes) {
			
			if(node.isMoving()){
				node.x=x;
				node.y=y;
			}
			else if(node.collide(x, y)){
				node.setMoving(true);
				node.x=x;
				node.y=y;
			}
		}
		
		
		updateDirections();
	}
	
	public void updateDirections() {
		ArrayList<Point> points = getPoints();
		to_side = getBestDirectionAsString(to,points.get(points.size()-2));
		from_side = getBestDirectionAsString(from, points.get(1));
	}
	
	private void drawPolyline(ArrayList<Point> points, Graphics g) {
		Point p1 = null;
		Point p2 = points.get(0);
		Point p3 = points.get(1);
		
		if(selected){
			g.drawOval(p2.x-3, p2.y-3, 6, 6);
			g.drawOval(p3.x-3, p3.y-3, 6, 6);
		}
		
		
		for (int i=2;i<points.size();i++) {
			p1=p2;
			p2=p3;
			p3=points.get(i);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
			
			if(selected) g.drawOval(p3.x-3, p3.y-3, 6, 6);
			
			/*double m1 = MyMath.slope(p1.x, p1.y, p2.x, p2.y);
			double m2 = MyMath.slope(p2.x, p2.y, p3.x, p3.y);			
			double alpha = MyMath.getAngle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);			
			g.drawArc(p2.x, p2.y, p2.arc, p2.arc, 0, (int)((alpha*180)/Math.PI) );*/
		}
		g.drawLine(p2.x, p2.y, p3.x, p3.y);
	}
	
	public void addNodeAt(int x, int y) {
		nodes.add(new NodeElement(x, y));
		
	}

	private void drawArc(int x1, int y1, int x2, int y2, int x3, int y3, int arc, Graphics g) {
		
		/*Graphics2D g2d = (Graphics2D)g;
		
		g2d.rotate(Math.toRadians(45));
		g2d.drawArc(0, -10, 150, 20, 180, 180);
		g2d.rotate(Math.toRadians(-45));
		g.drawArc(x, y, width, height, startAngle, arcAngle)*/
		
		
	}	
	
/*	private void drawArc(int x1, int y1, int x2, int y2, int arc, Graphics g) {
		
		/*Graphics2D g2d = (Graphics2D)g;
		
		g2d.rotate(Math.toRadians(45));
		g2d.drawArc(0, -10, 150, 20, 180, 180);
		g2d.rotate(Math.toRadians(-45));
		g.drawArc(x, y, width, height, startAngle, arcAngle)*
		g.drawLine(x1, y1, x2, y2);
		
	}*/

	public String getAsXML() {
		String ret = "<arrow id=\""+id+"\" from=\""+from.id+"\" to=\""+to.id+"\" text=\""+text+"\" from_side=\""+from_side+"\" to_side=\""+to_side+"\">\n"; 
		for (NodeElement node : nodes) {
			ret+="  <node x=\""+node.x+"\" y=\""+node.y+"\" arc=\""+node.arc+"\"/>\n";
		}
		ret+=" </arrow>";
		return ret;
	}



	@Override
	public int getMinX() {
		ArrayList<Point> points = getPoints();
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		return Math.min(p1.x, p2.x);
	}
	@Override
	public int getMinY() {
		ArrayList<Point> points = getPoints();
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		return Math.min(p1.y, p2.y);
	}
	@Override
	public int getMaxX() {
		ArrayList<Point> points = getPoints();
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		return Math.max(p1.x, p2.x);
	}
	@Override
	public int getMaxY() {
		ArrayList<Point> points = getPoints();
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		return Math.max(p1.y, p2.y);
	}





}
