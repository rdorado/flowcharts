package rdorado.flowcharts.core;

import java.awt.Color;
import java.awt.Graphics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import rdorado.flowcharts.gui.DocumentCanvas;

public class NodeElement extends GraphicalTextualElement {

	int arc;
	boolean moving=false;
	
	public NodeElement(Node node, DocumentCanvas dc){
		super();
		id = Integer.parseInt( ((Element)node).getAttribute("id") );
		x = Integer.parseInt( ((Element)node).getAttribute("x") );
		y = Integer.parseInt( ((Element)node).getAttribute("y") );
		height = Integer.parseInt( ((Element)node).getAttribute("height") );
		width = Integer.parseInt( ((Element)node).getAttribute("width") );
		setUnescapedText( ((Element)node).getAttribute("text") );
	}
	
	public NodeElement(int id, int x, int y, int height, int width) {
		this(x, y);
		this.id=id;		
	}	
	
	public NodeElement(int x, int y) {
		this.x=x;
		this.y=y;	
		this.width=8;
		this.height=8;
		setText("");
	}

	public NodeElement(int x, int y, int arc) {
		this(x,y);
		this.arc=arc;
	}

	@Override
	void paintElement(Graphics g) {
		  g.setColor(Color.LIGHT_GRAY);
		  g.drawOval(x, y, width, height);
		  g.setColor(Color.BLACK);  
		  paintCenteredText(g);
	}
/*
	@Override
	public boolean collide(int x, int y) {
		return Math.sqrt( Math.pow(this.x-x,2) + Math.pow(this.y-y,2) ) < 6;
	}

	@Override
	public Point getOffset(int x, int y) {
		return null;
	}
	public DragType getDragType(int x, int y) {
		return DragType.NONE;
	}
*/
	@Override
	public String getAsXML() {
		return "<node id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" height=\""+height+"\" width=\""+width+"\" text=\""+getScapedText()+"\"/>";
	}
	//public void changeSize(int l, int u, int r, int d){}

	public void setMoving(boolean value) {
		moving=value;
	}

	public boolean isMoving() {
		return moving;
	}

	@Override
	public String getAsSVG() {
		return "";
	}

	@Override
	public int getMinX() {
		return x+width/2;
	}

	@Override
	public int getMinY() {
		return y+height/2;
	}

	@Override
	public int getMaxX() {
		return x+width/2;
	}

	@Override
	public int getMaxY() {
		return y+height/2;
	}

}
