package rdorado.flowcharts.core;

import java.awt.Graphics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import rdorado.flowcharts.gui.DocumentCanvas;

public class DecisionElement extends GraphicalTextualElement{
	
	public DecisionElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		text = "Decision ...";
	}
	
	public DecisionElement(int id, int x, int y, int height, int width, String text) {
		super(id, x, y, height, width, text);
	}
	
	public DecisionElement(Node node, DocumentCanvas dc){
		super();
		id = Integer.parseInt( ((Element)node).getAttribute("id") );
		x = Integer.parseInt( ((Element)node).getAttribute("x") );
		y = Integer.parseInt( ((Element)node).getAttribute("y") );
		height = Integer.parseInt( ((Element)node).getAttribute("height") );
		width = Integer.parseInt( ((Element)node).getAttribute("width") );
		text = ((Element)node).getAttribute("text");
	}

	@Override
	void paintElement(Graphics g) {
		//g.setColor(Color.black);

		int mx = x + (width/2);
	    int my = y + (height/2);
	    
	    g.drawLine(x, my, mx, y);
	    g.drawLine(mx, y, x+width, my);	    
	    g.drawLine(x+width, my, mx, y+height);
	    g.drawLine(mx, y+height, x, my);
	     
	    //paintCenteredText(g);
	    paintMultilineText(g, "\\\\n");
	}
	
	public String getAsXML() {
		return "<decision id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" text=\""+text+"\" height=\""+height+"\" width=\""+width+"\" />";
	}

	@Override
	public String getAsSVG() {
		String resp="";
		
		
		
		int mx = x + (width/2);
	    int my = y + (height/2);
	    
	    resp+="<line x1=\""+(x)+"\" y1=\""+(my)+"\" x2=\""+(mx)+"\" y2=\""+(y)+"\" style=\"stroke:black\" />\n";	    
	    resp+="<line x1=\""+(mx)+"\" y1=\""+(y)+"\" x2=\""+(x+width)+"\" y2=\""+(my)+"\" style=\"stroke:black\" />\n";
	    resp+="<line x1=\""+(x+width)+"\" y1=\""+(my)+"\" x2=\""+(mx)+"\" y2=\""+(y+height)+"\" style=\"stroke:black\" />\n";
	    resp+="<line x1=\""+(mx)+"\" y1=\""+(y+height)+"\" x2=\""+(x)+"\" y2=\""+(my)+"\" style=\"stroke:black\" />\n";
	    
		int ty = (height+SVG_FONT_SIZE-2)/2;
	    //resp+="<text font-family=\"Verdana\" font-size=\""+SVG_FONT_SIZE+"\" text-anchor=\"middle\" x=\""+(x+(width/2))+"\" y=\""+(y+ty)+"\">"+text+"</text>";
	    String svgCenteredText = getMultilineTextAsSVG("\\\\n");
	    resp+=svgCenteredText;
	    	    
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
