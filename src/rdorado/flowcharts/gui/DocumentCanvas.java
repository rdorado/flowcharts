package rdorado.flowcharts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import rdorado.flowcharts.core.ArrowElement;
import rdorado.flowcharts.core.ClassElement;
import rdorado.flowcharts.core.ConnectorElement;
import rdorado.flowcharts.core.DecisionElement;
import rdorado.flowcharts.core.EndElement;
import rdorado.flowcharts.core.FunctionElement;
import rdorado.flowcharts.core.GraphicalElement;
import rdorado.flowcharts.core.GraphicalRelationElement;
import rdorado.flowcharts.core.GraphicalTextualElement;
import rdorado.flowcharts.core.InputElement;
import rdorado.flowcharts.core.NodeElement;
import rdorado.flowcharts.core.OutputElement;
import rdorado.flowcharts.core.Point;
import rdorado.flowcharts.core.ProcessElement;
import rdorado.flowcharts.core.StartElement;
import rdorado.flowcharts.core.TemporalHiddenElement;
import rdorado.flowcharts.core.VardefElement;


public class DocumentCanvas extends JPanel implements MouseListener, MouseMotionListener{


	ArrayList<GraphicalElement> elements = new ArrayList<GraphicalElement>();
	//ArrayList<GraphicalRelationElement> relations = new ArrayList<GraphicalRelationElement>();

	int ncons=0;
	GraphicalElement selected;
	TemporalHiddenElement temp;
	GraphicalRelationElement tempRelation;
	StartElement startSymbol;
	boolean movingElement=false;
	boolean settingRelation=false;
	Point moveOffset = new Point(0,0);
	Point delta;
	MainFrame mainFrameRef;
	String filename;
	boolean hasStart=false;
	boolean hasVardef=false;
	boolean inFile=false;
	boolean saved=false;
	DragType dragType = DragType.NONE;

	DocumentCanvas(){
	}

	DocumentCanvas(MainFrame mainFrameRef){
		this.mainFrameRef=mainFrameRef;
		/*StartElement startSymbol = new StartElement(30, 5, 40, 70);
		startSymbol.setSelected(true);
		elements.add(startSymbol);

		VardefElement variables = new VardefElement(150, 10, 30, 80); 
		elements.add(variables);

		CommentRelationElement commentLine = new CommentRelationElement(startSymbol, variables);
		elements.add(commentLine);

		OutputElement output = new OutputElement(5, 60, 30, 190, "Hello world. Insert new var x: ");
		elements.add(output);

		InputElement input = new InputElement(5, 105, 30, 40, "x");
		elements.add(input);

		ProcessElement process = new ProcessElement(5, 190, 30, 120, "x <- x + 1");
		elements.add(process);

		ArrowElement arrow = new ArrowElement(startSymbol, output);
		elements.add(arrow);

		DecisionElement decision = new DecisionElement(5, 150, 30, 120, "?");
		elements.add(decision);

		ReturnArrowElement returnArrow = new ReturnArrowElement(process, decision);
		elements.add(returnArrow);		

		EndElement endSymbol = new EndElement(30, 250, 40, 70);
		elements.add(endSymbol);

		arrow = new ArrowElement(decision, process, "F");
		elements.add(arrow);
		 */

		setPreferredSize(new Dimension(300, 300));
		setMinimumSize(new Dimension(300, 300));

		// pack();


		selected=null;
		addMouseListener(this);
		addMouseMotionListener(this);
		setFont(mainFrameRef.mathFont);
	}


	public DocumentCanvas(MainFrame mainFrame, String filename) throws ParserConfigurationException, SAXException, IOException {
		this(mainFrame);

		File xmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		inFile=true;
		this.filename=filename;
		//System.out.println( doc.getFirstChild().getNodeName() );

		Node node = doc.getFirstChild();
		ncons = Integer.parseInt( ((Element)node).getAttribute("ncons") );
		NodeList list = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			node = list.item(i);
			if(!node.getNodeName().equals("#text")){
				GraphicalElement el = GraphicalElement.fromXMLNode( node , this);
				elements.add(el);

				if(el instanceof StartElement){
					startSymbol= (StartElement)el;
					hasStart=true; 	
				}
			}

		}

		//System.out.println( ((Element)node).getAttribute("x") );
	}

	public void saveImage(String filename, String type) {
	//	System.out.println(getSize().width+" "+getHeight());
		//BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
		if(type.equals("svg")){
			try{
				saveAsSVGTo(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		else{
			Point start = getMinPoint();
			Point end = getMaxPoint();
			int width = end.getX() + start.getX();
			int height = end.getY() + start.getY();
			
			
			BufferedImage image = (BufferedImage) createImage(width, height);
			Graphics g = image.createGraphics();
			paintComponent(g);
			g.dispose();
			File imageFile = new File(filename);
			try {
				ImageIO.write(image, type, imageFile);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}

	}



	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (GraphicalElement element : elements) {
			System.out.println(element);
			element.paint(g);

			/*if(element instanceof VardefElement){
				((VardefElement)element).paintVardefLine(g, getStartElement());	    			    		
			}*/
		}

	}
	/*			
	public static void main(String[] args) {
		 JFrame mainFrame = new JFrame("Graphics demo");
		 DocumentCanvas dc = new DocumentCanvas();
		 mainFrame.getContentPane().add(dc);

		 mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 mainFrame.pack();
		 mainFrame.setVisible(true);

		 dc.saveImage();
	}*/


	private StartElement getStartElement() {
		for (GraphicalElement element: elements) {
			if(element instanceof StartElement) return (StartElement)element;
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
		if(e.getClickCount()>=2 && selected!=null){
			if(SwingUtilities.isRightMouseButton(e)){
				if(selected instanceof ArrowElement){
					((ArrowElement)selected).addNodeAt(e.getX(),e.getY());
					repaint();
				}
			}
			else{
					String newText = JOptionPane.showInputDialog(this, "Nuevo texto:",selected.getText());
					if(newText!=null){
						selected.setText(newText);
						repaint();
					}

			}			
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void unselectAll(){
		for (GraphicalElement el : elements) {
			el.setSelected(false);
			selected=null;
		}		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		movingElement=false;

		String stool = mainFrameRef.getSelectedTool();
		if(stool.equals("arrow")){
			boolean nonselected=true;
			for (GraphicalElement element : elements) {
			//	System.out.println(element+" "+element.collide(e.getX(),e.getY()));
				if(element.collide(e.getX(),e.getY())){
					nonselected=false;
					moveOffset = element.getOffset(e.getX(),e.getY());
					delta = new Point(e.getX(),e.getY());
					if(selected==null || !selected.equals(element)){
						unselectAll();
						element.setSelected(true);
						selected=element;
						movingElement=true;
					} 
					else{
						dragType = element.getDragType(e.getX(),e.getY());
					}
					break;
				}

			}
			if(nonselected) unselectAll();
		}
		
		
		
		if(stool.equals("start")){
			if(!hasStart){
				startSymbol = new StartElement(++ncons, e.getX(), e.getY(), 40, 70);
				elements.add(startSymbol);
				hasStart=true;
			}
			else{
				JOptionPane.showMessageDialog(this, "This diagram has already a start element.");
			}
		}		
		else if(stool.equals("end")){
			EndElement endSymbol = new EndElement(++ncons, e.getX(), e.getY(), 40, 70);
			elements.add(endSymbol);
		}
		else if(stool.equals("function")){
			FunctionElement functionSymbol = new FunctionElement(++ncons, e.getX(), e.getY(), 40, 70);
			elements.add(functionSymbol);
		}
		else if(stool.equals("class")){
			ClassElement classSymbol = new ClassElement(++ncons, e.getX(), e.getY(), 40, 70);
			elements.add(classSymbol);
		}
		else if(stool.equals("process")){
			ProcessElement process = new ProcessElement(++ncons, e.getX(), e.getY(), 30, 120, "x : \u2124, x \u2190 4, y : \u211D");
			elements.add(process);
		}
		else if(stool.equals("output")){
			OutputElement process = new OutputElement(++ncons, e.getX(), e.getY(), 30, 190, "Set output text..."); 
			elements.add(process);
		}
		else if(stool.equals("input")){
			InputElement process = new InputElement(++ncons, e.getX(), e.getY(), 30, 80, "varname"); 
			elements.add(process);
		}
		else if(stool.equals("input")){
			InputElement process = new InputElement(++ncons, e.getX(), e.getY(), 30, 80, "varname"); 
			elements.add(process);
		}
		else if(stool.equals("decision")){
			DecisionElement decision = new DecisionElement(++ncons, e.getX(), e.getY(), 30, 120, "?");
			elements.add(decision);
		}
		else if(stool.equals("vars")){
			if(startSymbol==null){
				JOptionPane.showMessageDialog(this, "There is not Start element. Start element must be defined first.");
			}
			else if(hasVardef){
				JOptionPane.showMessageDialog(this, "This diagram has already a variable definition element.");
			}
			else{
				VardefElement variables = new VardefElement(++ncons, e.getX(), e.getY(), 30, 80); 
				elements.add(variables);
				variables.setStartSymbol(startSymbol);
				//CommentRelationElement commentLine = new CommentRelationElement(startSymbol, variables);
				//elements.add(commentLine);

				hasVardef= true;
			}

		}
		else if(stool.equals("relation")){
			for (GraphicalElement element : elements) {
				element.setSelected(false);
				if(element.collide(e.getX(),e.getY())){
					element.setSelected(true);
					selected=element;
					settingRelation=true;

					temp = new TemporalHiddenElement(++ncons, e.getX(),e.getY(),1,1);
					tempRelation = new ArrowElement((GraphicalTextualElement) selected, temp);
					elements.add(tempRelation);
					break;
				}

			}
		}
		else if(stool.equals("connector")){			
			ConnectorElement connectorElement = new ConnectorElement(++ncons, e.getX(), e.getY(), 8, 8);
			elements.add(connectorElement);
		}
		else if(stool.equals("node")){		
			System.out.println("-->"+e.getX()+" "+e.getY());
			NodeElement nodeElement = new NodeElement(++ncons, e.getX(), e.getY(), 8, 8);
			elements.add(nodeElement);
		}
		else if(stool.equals("eraser")){
			ArrayList<GraphicalElement> toErase = new ArrayList<GraphicalElement>();
			GraphicalElement remElement=null;
			for (GraphicalElement element : elements) {
				if(element.collide(e.getX(),e.getY())){
					remElement = element;					
					break;
				}
			}
			
			if(remElement!=null){
				for (GraphicalElement element : elements) {
					if(element instanceof ArrowElement){
						ArrowElement arrow = (ArrowElement)element;
						if(remElement.equals(arrow.getFrom()) || remElement.equals(arrow.getTo())){
							toErase.add(arrow);
						}
					}
					
				}
				elements.remove(remElement);
				elements.removeAll(toErase);
			}
			
		}



		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		movingElement=false;	
		dragType=DragType.NONE;
		if(selected instanceof ArrowElement){
			((ArrowElement)selected).stopMoving();
		}
		
		if(settingRelation){
			for (GraphicalElement element : elements) {
				element.setSelected(false);
				if(element.collide(e.getX(),e.getY())){
					if(element.equals(tempRelation.getFrom())){
						elements.remove(tempRelation);
						settingRelation=false;
						repaint();
						return;
					}
					else{
						tempRelation.setTo((GraphicalTextualElement) element);
						if(tempRelation instanceof ArrowElement){
							((ArrowElement)tempRelation).updateDirections();
						}
						settingRelation=false;
						repaint();
						return;
					}
				}
			}
			elements.remove(tempRelation);
			settingRelation=false;
			repaint();
		}
		System.out.println("\n\n\n\n");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(dragType.equals(DragType.STRETCH_L)){
			selected.changeSize(delta.getX() - e.getX(),0,0,0);
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(dragType.equals(DragType.STRETCH_R)){
			selected.changeSize(0,0,delta.getX() - e.getX(),0);
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(dragType.equals(DragType.STRETCH_U)){
			selected.changeSize(0,delta.getY() - e.getY(),0,0);
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(dragType.equals(DragType.STRETCH_D)){
			selected.changeSize(0,0,0,delta.getY() - e.getY());
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(dragType.equals(DragType.STRETCH_LD)){
			selected.changeSize(delta.getX() - e.getX(),0,0,delta.getY() - e.getY());
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(dragType.equals(DragType.STRETCH_RD)){
			selected.changeSize(0,0,delta.getX() - e.getX(),delta.getY() - e.getY());
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(dragType.equals(DragType.STRETCH_LU)){
			selected.changeSize(delta.getX() - e.getX(),delta.getY() - e.getY(),0,0);
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(dragType.equals(DragType.STRETCH_RU)){
			selected.changeSize(0,delta.getY() - e.getY(),delta.getX() - e.getX(),0);
			delta = new Point(e.getX(),e.getY());
			repaint();
			saved=false;
		}
		else if(movingElement){
			if(selected instanceof ArrowElement){
				selected.move(e.getX(), e.getY());
			}
			else{
				selected.move(e.getX() - moveOffset.getX(), e.getY() - moveOffset.getY());
			}
			
			repaint();
			saved=false;
		}
		else if(settingRelation){
			temp.setX(e.getX());
			temp.setY(e.getY());
			repaint();
		}
		
		updateArrows();
	}

	private void updateArrows() {
		for (GraphicalElement element : elements) {
			if(element instanceof ArrowElement) ((ArrowElement)element).updateDirections();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		/*if(movingElement){
			selected.move(e.getX(), e.getY());
			repaint();
		}
		else if(settingRelation){
			temp.setX(e.getX());
			temp.setY(e.getY());
			repaint();
		}*/
	}

	public void saveAsXMLTo(String fileName) throws IOException {

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
		writer.write("<?xml version=\"1.0\"?>");
		writer.newLine();
		writer.write("<diagram ncons=\""+ncons+"\">");
		writer.newLine();
		for (GraphicalElement ge : elements) {
			writer.write(" "+ge.getAsXML());
			writer.newLine();
		}
		writer.write("</diagram>");
		writer.newLine();
		writer.close();
		inFile=true;
		saved=true;
		this.filename=filename;
	}

	public Point getMinPoint(){
		int x=Integer.MAX_VALUE,y=Integer.MAX_VALUE, nx,ny;
		for (GraphicalElement ge : elements) {
			nx=ge.getMinX();
			ny=ge.getMinY();
			
			if(nx<x) x = nx;
			if(ny<y) y = ny;
		}
		return new Point(x, y);
	}
	
	public Point getMaxPoint(){
		int x=0,y=0, nx,ny;
		for (GraphicalElement ge : elements) {
			nx=ge.getMaxX();
			ny=ge.getMaxY();
			
			if(nx>x) x = nx;
			if(ny>y) y = ny;
		}
		return new Point(x, y);
	}
	
	public void saveAsSVGTo(String fileName) throws IOException {

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		writer.newLine();
		
		Point start = getMinPoint();
		Point end = getMaxPoint();
		int width = end.getX() + start.getX();
		int height = end.getY() + start.getY();
		
		writer.write("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" height=\""+height+"\" width=\""+width+"\" >");
		writer.newLine();
		for (GraphicalElement ge : elements) {
			writer.write(" "+ge.getAsSVG());
			writer.newLine();
		}
		writer.write("</svg>");
		writer.newLine();
		writer.close();
		inFile=true;
		saved=true;
		this.filename=filename;
	}
	
	
	public GraphicalElement getById(int id) {
		for (GraphicalElement el : elements) {
			if(el.getId()==id) return el;
		}
		return null;
	}


}
