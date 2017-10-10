package rdorado.flowcharts.core;

import java.awt.Graphics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import rdorado.flowcharts.gui.DocumentCanvas;

public class InputElement extends GraphicalTextualElement{

	public InputElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		setText("Process ...");
	}
	
	public InputElement(int id, int x, int y, int height, int width, String text) {
		super(id, x, y, height, width, text);
	}
	public InputElement(Node node, DocumentCanvas dc){
		super();
		id = Integer.parseInt( ((Element)node).getAttribute("id") );
		x = Integer.parseInt( ((Element)node).getAttribute("x") );
		y = Integer.parseInt( ((Element)node).getAttribute("y") );
		height = Integer.parseInt( ((Element)node).getAttribute("height") );
		width = Integer.parseInt( ((Element)node).getAttribute("width") );
		setUnescapedText( ((Element)node).getAttribute("text") );
	}	
	@Override
	void paintElement(Graphics g) {
		//g.setColor(Color.black);
		g.drawLine(x, y, x+width-10, y);
		g.drawLine(x, y+height, x+width-10, y+height);
		
		g.drawLine(x, y, x+10, y+(height/2));
		g.drawLine(x, y+height, x+10, y+(height/2));
		
		g.drawLine(x+width-10, y, x+width, y+(height/2));
		g.drawLine(x+width-10, y+height, x+width, y+(height/2));
		
	    paintCenteredText(g);
	}
	
	public String getAsXML() {
		return "<input id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" text=\""+getScapedText()+"\" height=\""+height+"\" width=\""+width+"\" />";
	}

	@Override
	public String getAsSVG() {
		String resp = "";
		
		resp+="<line x1=\""+x+"\" y1=\""+y+"\" x2=\""+(x+width-10)+"\" y2=\""+y+"\" style=\"stroke:black\" />\n";
		resp+="<line x1=\""+x+"\" y1=\""+(y+height)+"\" x2=\""+(x+width-10)+"\" y2=\""+(y+height)+"\" style=\"stroke:black\" />\n";		
		resp+="<line x1=\""+x+"\" y1=\""+(y)+"\" x2=\""+(x+10)+"\" y2=\""+(y+(height/2))+"\" style=\"stroke:black\" />\n";	
		resp+="<line x1=\""+x+"\" y1=\""+(y+height)+"\" x2=\""+(x+10)+"\" y2=\""+(y+(height/2))+"\" style=\"stroke:black\" />\n";
		resp+="<line x1=\""+(x+width-10)+"\" y1=\""+(y)+"\" x2=\""+(x+width)+"\" y2=\""+(y+(height/2))+"\" style=\"stroke:black\" />\n";
		resp+="<line x1=\""+(x+width-10)+"\" y1=\""+(y+height)+"\" x2=\""+(x+width)+"\" y2=\""+(y+(height/2))+"\" style=\"stroke:black\" />\n";
		
		int ty = (height+SVG_FONT_SIZE-2)/2;
	    resp+="<text font-family=\"Verdana\" font-size=\""+SVG_FONT_SIZE+"\" text-anchor=\"middle\" x=\""+(x+(width/2))+"\" y=\""+(y+ty)+"\">"+getScapedText()+"</text>";
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
