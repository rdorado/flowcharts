package rdorado.flowcharts.core;

import java.awt.Graphics;

public class FunctionElement extends GraphicalTextualElement{

	public FunctionElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		text = "function_name() to Z";
	}

	@Override
	void paintElement(Graphics g) {
		g.drawLine(x, y, x + width, y);
		g.drawLine(x, y+16, x + width, y+16);
		g.drawLine(x, y+height, x + width, y+height);
		
		g.drawLine(x, y, x, y+height);
		g.drawLine(x+ width, y, x+ width, y+height);
		
		g.drawString(text, x+4, y+12);
	}

	@Override
	public String getAsXML() {
		return "<function></function>";
	}

	@Override
	public String getAsSVG() {
		String resp = "";

		resp+="<line x1=\""+x+"\" y1=\""+(y)+"\" x2=\""+(x+width)+"\" y2=\""+(y)+"\" style=\"stroke:black\" />\n";
		resp+="<line x1=\""+x+"\" y1=\""+(y+16)+"\" x2=\""+(x+width)+"\" y2=\""+(y+16)+"\" style=\"stroke:black\" />\n";
		resp+="<line x1=\""+x+"\" y1=\""+(y+height)+"\" x2=\""+(x+width)+"\" y2=\""+(y+height)+"\" style=\"stroke:black\" />\n";		
		resp+="<line x1=\""+x+"\" y1=\""+(y)+"\" x2=\""+(x)+"\" y2=\""+(y+height)+"\" style=\"stroke:black\" />\n";		
		resp+="<line x1=\""+(x+width)+"\" y1=\""+(y)+"\" x2=\""+(x+width)+"\" y2=\""+(y+height)+"\" style=\"stroke:black\" />\n";
	    resp+="<text font-family=\"Verdana\" font-size=\""+SVG_FONT_SIZE+"\" x=\""+(x+4)+"\" y=\""+(y+12)+"\">Variables</text>\n";
	    
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
