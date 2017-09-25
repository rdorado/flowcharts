package rdorado.flowcharts.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ToolBar extends JPanel {

	ButtonGrid grid = new ButtonGrid();
	
	public ToolBar(){
		
		setPreferredSize(new Dimension(64, 30));
		setMaximumSize(new Dimension(64, 30));
		setLayout(new BorderLayout());
		
		add(grid, BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.CENTER);
	}

	public String getSelectedTool() {
		return grid.getSelectedTool();
	}
	
}


@SuppressWarnings("serial")	
class ButtonGrid extends JPanel implements ActionListener{	
	
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	String selectedTool = "arrow";
	
	public ButtonGrid() {
		GridLayout grid = new GridLayout(0,2);
		setLayout(grid);
		Dimension dim = new Dimension(64,180);
		setSize(dim);
		setMinimumSize(new Dimension(dim));
		setMaximumSize(new Dimension(dim));
		setPreferredSize(new Dimension(dim));	
		//setBorder(new LineBorder(Color.BLACK, 1));
		setBounds(0,0,0,0);
		
		try {
			
			createButton("images/tb-arrow.png", "images/tb-arrow-act.png", "arrow", true);			
			createButton("images/tb-start.png", "images/tb-start-act.png", "start");
			createButton("images/tb-end.png", "images/tb-end-act.png", "end");
			createButton("images/tb-process.png", "images/tb-process-act.png", "process");
			createButton("images/tb-input.png", "images/tb-input-act.png", "input");
			createButton("images/tb-output.png", "images/tb-output-act.png", "output");
			createButton("images/tb-decision.png", "images/tb-decision-act.png", "decision");
			createButton("images/tb-relation.png", "images/tb-relation-act.png", "relation");
			createButton("images/tb-vars.png", "images/tb-vars-act.png", "vars");
			createButton("images/tb-connector.png", "images/tb-connector-act.png", "connector");
			createButton("images/tb-eraser.png", "images/tb-eraser-act.png", "eraser");
			createButton("images/tb-class.png", "images/tb-class.png", "class");
			createButton("images/tb-function.png", "images/tb-function.png", "function");
			
			createButton("images/tb-connector.png", "images/tb-connector-act.png", "node");
			createButton("images/tb-relation.png", "images/tb-relation-act.png", "relation");
			createButton("images/tb-relation.png", "images/tb-relation-act.png", "relation");
			
			/*createButton("images/tb-select.png", "images/tb-select-act.png", "select");*/
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String getSelectedTool() {
		return selectedTool;
	}

	void createButton(String imageFile, String imageActFile, String actionCommand) throws IOException{
		createButton(imageFile, imageActFile, actionCommand, false);
	}
	
	void createButton(String imageFile, String imageActFile, String actionCommand, boolean selected) throws IOException{
		BufferedImage img = ImageIO.read(new File(imageFile));
		ImageIcon icon = new ImageIcon(img);
		JButton button = new JButton(icon);
		button.setActionCommand(actionCommand);
		button.setSelectedIcon(new ImageIcon(ImageIO.read(new File(imageActFile))));
		button.setSelected(selected);
		button.setBorder(new LineBorder(Color.BLACK, 1));
		button.addActionListener(this);
		add(button);
		buttons.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (JButton button : buttons) {
			if(e.getActionCommand().equals(button.getActionCommand())){
				button.setSelected(true);
				selectedTool = button.getActionCommand();
			}
			else button.setSelected(false);
		}
	}
	
	
}