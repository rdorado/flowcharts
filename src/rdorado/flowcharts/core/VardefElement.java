package rdorado.flowcharts.core;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rdorado.flowcharts.gui.DocumentCanvas;

public class VardefElement extends GraphicalTextualElement{

	Hashtable<String, String> variables = new Hashtable<String, String>();
	
	ArrayList<String> vars = new ArrayList<String>();
	
	StartElement startElement;
	
	public VardefElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		text = "Variables:";
	}
	
	
	public VardefElement(int id, int x, int y, int height, int width, String text) {
		super(id, x, y, height, width, text);
	}

	public VardefElement(Node node, DocumentCanvas dc){
		super();
		id = Integer.parseInt( ((Element)node).getAttribute("id") );
		x = Integer.parseInt( ((Element)node).getAttribute("x") );
		y = Integer.parseInt( ((Element)node).getAttribute("y") );
		text= ((Element)node).getAttribute("text");
		width=80;
		height=20;
		
		/*NodeList list = node.getChildNodes();
		for (int i=0;i<list.getLength();i++) {
			if(list.item(i).getNodeName().equals("var")){
				vars.add( ((Element)list.item(i)).getAttribute("text") );
			}			
		}*/
		String[] lines = text.split(",");
		height+=lines.length*14;
		
	}		
	
	@Override
	void paintElement(Graphics g) {
		//g.setColor(Color.black);
	    //g.drawRect(x, y, width, height);
	    g.drawString("Variables", x+4, y+10);
	    g.drawLine(x, y+12, x + width, y+12);
	    
	    
	    String[] lines = text.split(",");
	    g.drawLine(x, y, x, y + height + lines.length*14);
	    
	    
	    for(int i=0;i<vars.size();i++) {
	    	g.drawString(vars.get(i), x+4, y+(i*14)+25);
		}
	    
	    paintVariableList(g);
	    
	    paintVardefLine(g);
	}
	
	public String getAsXML() {
		String ret = "<vardef id=\""+id+"\" x=\""+x+"\" y=\""+y+"\" text=\""+text+"\">\n"; 
		ret+="  <var text=\""+text+"\"/>\n";                                                                            
		ret+=" </vardef>";
		return ret;
	}


	public void paintVardefLine(Graphics g) {
		int x1 = startElement.x + startElement.width;
		int y1 = startElement.y + startElement.height/2;
		
		int x2 = x;
		int y2 = y + 10;
		
		
		Stroke drawingStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{6}, 0);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(drawingStroke);
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		g2d.draw(line);
		
		drawingStroke = new BasicStroke(1);
		g2d.setStroke(drawingStroke);
		
	}


	@Override
	public String getAsSVG() {
		String resp = "";
		
	    resp+="<text font-family=\"Verdana\" font-size=\""+SVG_FONT_SIZE+"\" x=\""+(x+4)+"\" y=\""+(y+10)+"\">Variables</text>\n";
	    resp+="<line x1=\""+x+"\" y1=\""+(y+12)+"\" x2=\""+(x+width)+"\" y2=\""+(y+12)+"\" style=\"stroke:black\" />\n";
	    
	    String[] lines = text.split(",");
		resp+="<line x1=\""+x+"\" y1=\""+y+"\" x2=\""+(x)+"\" y2=\""+(y+height+(lines.length*14))+"\" style=\"stroke:black\" />\n";

		int yoffset = 25; 
		int xoffset = 5;
		
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();			
			resp+="<text font-family=\"Verdana\" font-size=\""+SVG_FONT_SIZE+"\" x=\""+(x+xoffset)+"\" y=\""+(y+yoffset+(i*14))+"\">"+line+"</text>\n";
		}
		
		int x1 = startElement.x + startElement.width;
		int y1 = startElement.y + startElement.height/2;
		
		int x2 = x;
		int y2 = y + 10;
		resp+="<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+(x2)+"\" y2=\""+(y2)+"\" stroke-dasharray=\"5,5\" style=\"stroke:black\" />\n";
		
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


	public void setStartSymbol(StartElement startElement) {
		this.startElement=startElement;
	}
	
}
