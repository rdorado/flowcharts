package rdorado.flowcharts.core;

import java.awt.Graphics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import rdorado.flowcharts.gui.DocumentCanvas;

public class ConnectorElement extends GraphicalTextualElement{

	public ConnectorElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		setText("");
	}
	public ConnectorElement(Node node, DocumentCanvas dc){
		super();
		id = Integer.parseInt( ((Element)node).getAttribute("id") );
		x = Integer.parseInt( ((Element)node).getAttribute("x") );
		y = Integer.parseInt( ((Element)node).getAttribute("y") );
		height = Integer.parseInt( ((Element)node).getAttribute("height") );
		width = Integer.parseInt( ((Element)node).getAttribute("width") );
		setText("");
	}

	@Override
	void paintElement(Graphics g) {
		//g.setColor(Color.black);
	    g.drawOval(x, y, width, height);
	    
	    paintCenteredText(g);
	}	

	@Override
	public String getAsXML() {
		return "<connector id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" height=\""+height+"\" width=\""+width+"\" text=\""+getScapedText()+"\"/>";
	}
	
	@Override
	public String getAsSVG() {
		String resp = "";
		resp+="<ellipse cx=\""+(x+(width/2))+"\" cy=\""+(y+(height/2))+"\" rx=\""+(width/2)+"\" ry=\""+(height/2)+"\" style=\"stroke:black;\" fill-opacity=\"0\"/>";
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
	
}
