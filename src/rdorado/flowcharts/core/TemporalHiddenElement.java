package rdorado.flowcharts.core;

import java.awt.Graphics;

public class TemporalHiddenElement extends GraphicalTextualElement{

	public TemporalHiddenElement(int id, int x, int y, int height, int width) {
		super(id, x, y, height, width);
		setText("");
	}

	@Override
	void paintElement(Graphics g) {
	}
	
	public String getAsXML() {
		return "<hidden />";
	}

	@Override
	public String getAsSVG() {
		return "";
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
		return x;
	}

	@Override
	public int getMaxY() {
		return y;
	}
	
}
