package rdorado.flowcharts.core;

import java.awt.Graphics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import rdorado.flowcharts.gui.DocumentCanvas;

public class ProcessElement extends GraphicalTextualElement{

	public ProcessElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		text = "Process ...";
	}
	
	public ProcessElement(Node node, DocumentCanvas dc){
		super();
		id = Integer.parseInt( ((Element)node).getAttribute("id") );
		x = Integer.parseInt( ((Element)node).getAttribute("x") );
		y = Integer.parseInt( ((Element)node).getAttribute("y") );
		height = Integer.parseInt( ((Element)node).getAttribute("height") );
		width = Integer.parseInt( ((Element)node).getAttribute("width") );
		text = ((Element)node).getAttribute("text");
	}	
	
	public ProcessElement(int id, int x, int y, int height, int width, String text) {
		super(id, x, y, height, width, text);
	}

	public ProcessElement(Node node){
		super();
		x = Integer.parseInt( ((Element)node).getAttribute("x") );
		y = Integer.parseInt( ((Element)node).getAttribute("y") );
		height = Integer.parseInt( ((Element)node).getAttribute("height") );
		width = Integer.parseInt( ((Element)node).getAttribute("width") );
		text = ((Element)node).getAttribute("text");
	}	
	
	@Override
	public String getAsSVG() {
		String resp = "";
		resp+="<rect x=\""+x+"\" y=\""+y+"\" width=\""+width+"\" height=\""+height+"\" style=\"stroke:black;fill:none\" />";
		int ty = (height+SVG_FONT_SIZE-2)/2;
	    resp+="<text font-family=\"Verdana\" font-size=\""+SVG_FONT_SIZE+"\" text-anchor=\"middle\" x=\""+(x+(width/2))+"\" y=\""+(y+ty)+"\">"+text+"</text>";
		return resp;
		
	}

	@Override
	public int getMinX() {
		return x;
	}

	@Override
	public int getMinY() {
		return y;
	}

	@Override
	public int getMaxX() {
		return x+width;
	}

	@Override
	public int getMaxY() {
		return y+height;
	}
	
	
	@Override
	void paintElement(Graphics g) {
		//g.setColor(Color.black);
	    g.drawRect(x, y, width, height);
	    
	    paintCenteredText(g);
	}
	
	public String getAsXML() {
		return "<process id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" height=\""+height+"\" width=\""+width+"\" text=\""+text+"\" />";
	}


}
