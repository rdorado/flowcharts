package rdorado.flowcharts.core;

import java.awt.Graphics;

public class _CommentRelationElement extends GraphicalRelationElement{
	
	public void changeSize(int l, int u, int r, int d){}
	
	public _CommentRelationElement(GraphicalTextualElement from, GraphicalTextualElement to) {
	//	super(from, to);
		this.setText("");
	}

	public _CommentRelationElement(GraphicalTextualElement from, GraphicalTextualElement to, String text) {
	//	super(from, to);
		this.setText(text);
	}	
	
	@Override
	void paintElement(Graphics g) {
		/*int x1 = from.x + from.width;
		int y1 = from.y + from.height/2;
		
		int x2 = to.x;
		int y2 = to.y + 10;
		
		
		Stroke drawingStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{6}, 0);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(drawingStroke);
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		g2d.draw(line);
		
		drawingStroke = new BasicStroke(1);
		g2d.setStroke(drawingStroke);
		/*while(){
			
		}*/
		//g.drawLine(x1, y1, x2, y2);
		
		
		
	}
	
	public void move(int x, int y) {
		
	}
	
	public String getAsXML() {
		return "<comment>"+getScapedText()+"</comment>";
	}

	@Override
	public String getAsSVG() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMinX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
