package rdorado.flowcharts.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.w3c.dom.Node;

import rdorado.flowcharts.gui.DragType;

public abstract class GraphicalTextualElement extends GraphicalElement{

	public static final int SVG_FONT_SIZE=12;
	
	int x;
	int y;
	int height;
	int width;
	
	public GraphicalTextualElement(int id, int x, int y, int height, int width) {
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.id=id;
	}
	
	public GraphicalTextualElement(int id, int x, int y, int height, int width, String text) {
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.text=text;
		this.id=id;
	}

	public GraphicalTextualElement() {
		this.x=0;
		this.y=0;
		this.height=10;
		this.width=10;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Point getMiddlePoint() {
		int mx = x+(width/2);
		int my = y+(height/2);
		return new Point(mx,my);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}	
	
	public void setSelected(boolean selected) {
		this.selected=selected;		
	}
	
	public void paint(Graphics g){
		g.setColor(Color.black);
		
		if(selected){			
			int selSize=5;
			
			g.drawRect(x, y, selSize, selSize);
			g.drawRect(x+(width-selSize), y, selSize, selSize);
			g.drawRect(x, y+(height-selSize), selSize, selSize);
			g.drawRect(x+(width-selSize), y+(height-selSize), selSize, selSize);
			
			int mHeight = (height-selSize)/2;
			int mWidth = (width-selSize)/2;
			g.drawRect(x+mWidth, y, selSize, selSize);
			g.drawRect(x, y+mHeight, selSize, selSize);
			g.drawRect(x+mWidth, y+(height-selSize), selSize, selSize);
			g.drawRect(x+(width-selSize), y+mHeight, selSize, selSize);
			
		}			
		
		paintElement(g);
				
		
	}
	
	
	protected void paintCenteredText(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(text, g);
		
		int textHeight = (int)(rect.getHeight()); 
		int textWidth  = (int)(rect.getWidth());
		
		int tx = (width  - textWidth)  / 2;
		int ty = (height - textHeight) / 2  + fm.getAscent();
		
		g.drawString(text, x + tx, y + ty);

	}
	
	protected void paintVariableList(Graphics g){
		
		
		int yoffset = 25; 
		int xoffset = 5;
		
		String[] lines = text.split(",");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			
			g.drawString(line, x + xoffset, y + yoffset + i*14);
		}
		
		
	}
	
	public boolean collide(int px, int py) {
		if(px >= x && px <= x + width && py >= y && py <= y+height) return true;
		return false;
	}
	
	public void move(int x, int y) {
		this.x=x;
		this.y=y;		
	}
	
	public Point getOffset(int x, int y) {
		return new Point(x - this.x, y - this.y);
	}
	public DragType getDragType(int cx, int cy) {
		
		if(this.x<cx&&cx-this.x<8){
			if(this.y<cy&&cy-this.y<8){
				return DragType.STRETCH_LU;
			}
			else if(cy<(this.y+this.height)&&cy>(this.y+this.height-8)){
				return DragType.STRETCH_LD;
			}
			else if(cy<(this.y+(this.height/2)+4)&&cy>(this.y+(this.height/2)-4)){
				return DragType.STRETCH_L;
			}
		}
		else if(cx<(this.x+this.width)&&cx>(this.x+this.width-8)){
			if(this.y<cy&&cy-this.y<8){
				return DragType.STRETCH_RU;
			}
			else if(cy<(this.y+this.height)&&cy>(this.y+this.height-8)){
				return DragType.STRETCH_RD;
			}
			else if(cy<(this.y+(this.height/2)+4)&&cy>(this.y+(this.height/2)-4)){
				return DragType.STRETCH_R;
			}
		}
		else if(cx<(this.x+(this.width/2)+4)&&cx>(this.x+(this.width/2)-4)){
			if(this.y<cy&&cy-this.y<8){
				return DragType.STRETCH_U;
			}
			else if(cy<(this.y+this.height)&&cy>(this.y+this.height-8)){
				return DragType.STRETCH_D;
			}
		}
		return DragType.MOVE;
	}
	
	public void changeSize(int l, int u, int r, int d){
		if(l!=0){
			x-=l;
			width+=l;
		}
		if(r!=0){
			width-=r;
		}
		if(u!=0){
			y-=u;
			height+=u;
		}
		if(d!=0){
			height-=d;
		}
	}


	
}
