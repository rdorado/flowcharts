package rdorado.flowcharts.core;

import java.awt.Graphics;

public class ClassElement extends GraphicalTextualElement{

	public ClassElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		text = "class_name";
	}
	
	@Override
	void paintElement(Graphics g) {
		g.drawLine(x, y+12, x + width, y+12);
		g.drawLine(x, y+12, x + width, y+12);
		
	}

	@Override
	public String getAsXML() {	
		return "<class id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" height=\""+height+"\" width=\""+width+"\" text=\""+text+"\" />";
	}

	@Override
	public String getAsSVG() {
		String resp = "";
		
		//resp+="<line x1=\""+x+"\" y1=\""+(y+12)+"\" x2=\""+(x+width)+"\" y2=\""+(y+12)+"\" style=\"stroke:black\" />\n";
		
	    //resp+="<text font-family=\"Verdana\" font-size=\""+SVG_FONT_SIZE+"\" x=\""+(x+4)+"\" y=\""+(y+10)+"\">Variables</text>\n";
	    
	    
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
