package rdorado.flowcharts.core;

import java.awt.Graphics;
import java.util.ArrayList;

import rdorado.flowcharts.gui.DragType;

public abstract class GraphicalRelationElement extends GraphicalElement{
	
	
	ArrayList<NodeElement> nodes = new ArrayList<NodeElement>();
	
	GraphicalTextualElement from;
	GraphicalTextualElement to;
	
	public GraphicalRelationElement(GraphicalTextualElement from, GraphicalTextualElement to){
		this.from = from;
		this.to = to;
	}

	public GraphicalRelationElement(){
		this.from=null;
		this.to=null;
	}
	
	@Override
	public void paint(Graphics g) {
		paintElement(g);		
	}
	
	
	public boolean collide(int x, int y) {		
		return false;
	}
	

	
	public Point getOffset(int x, int y) {		
		return null;
	}
	
	public DragType getDragType(int x, int y) {
		return DragType.NONE;
	}


	public GraphicalTextualElement getFrom() {
		return from;
	}


	public void setFrom(GraphicalTextualElement from) {
		this.from = from;
	}


	public GraphicalTextualElement getTo() {
		return to;
	}


	public void setTo(GraphicalTextualElement to) {
		this.to = to;
	}
	
	public void changeSize(int l, int u, int r, int d){}


}
