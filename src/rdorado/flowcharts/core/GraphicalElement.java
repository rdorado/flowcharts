package rdorado.flowcharts.core;

import java.awt.Graphics;
import java.lang.reflect.Constructor;

import org.w3c.dom.Node;

import rdorado.flowcharts.gui.DocumentCanvas;
import rdorado.flowcharts.gui.DragType;

public abstract class GraphicalElement {
	
	boolean selected;
	String text;
	int id;
	
	abstract void paintElement(Graphics g);
	public abstract void paint(Graphics g);
	public abstract void move(int x, int y);
	public abstract boolean collide(int x, int y);
	public abstract Point getOffset(int x, int y);
	public abstract DragType getDragType(int x, int y);
	abstract public String getAsXML();
	abstract public String getAsSVG();
	public abstract void changeSize(int l, int u, int r, int d);
	
	abstract public int getMinX();	
	abstract public int getMinY();
	abstract public int getMaxX();
	abstract public int getMaxY();
	
	public String getText(){
		return text;
	}
	public void setText(String text){
		this.text=text;
	}	
	public void setSelected(boolean selected){
		this.selected = selected;		
	}
	public static GraphicalElement fromXMLNode(String nodeName) {		
		return null;
	}
	public static GraphicalElement fromXMLNode(Node node, DocumentCanvas dc) {
		String javaName = "rdorado.flowcharts.core."+node.getNodeName().substring(0,1).toUpperCase()+node.getNodeName().substring(1)+"Element";
		try {
			
			Class c = Class.forName(javaName);
			Constructor ctor = c.getConstructor( Class.forName("org.w3c.dom.Node"), Class.forName("rdorado.flowcharts.gui.DocumentCanvas") );
			return (GraphicalElement)ctor.newInstance(node, dc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int getId(){
		return id;
	}	
	
}
