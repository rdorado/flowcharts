package rdorado.flowcharts.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame implements ActionListener{
	
	JTabbedPane tabbedPane;
	int ncondoc = 1;
	ToolBar toolBar;
	Font mathFont;
		
	/*Element observableElement;
	PropertiesTableModel propertiesTableModel;
	Plugins plugins;
	*/
	public MainFrame() {
		try { 	
			Font f = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Asana-Math.ttf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/** Frame Setup  */
		setLayout(new BorderLayout());
		setTitle("RD Flowchart Editor");		
		setMinimumSize(new Dimension(200,200));
		
		
		/** Create and set menu bar*/
		setJMenuBar(createMenuBar());
		
		
		/** Tool Bar*/
		toolBar = new ToolBar();
		add(toolBar, BorderLayout.LINE_START);
		
		
		/** Document panel*/
		tabbedPane = new JTabbedPane();		
		tabbedPane.setPreferredSize(new Dimension(600,600));
		tabbedPane.setSize(300, 300);
		add(tabbedPane, BorderLayout.CENTER);
		
		/** Properties table panel*/		
		/*propertiesTableModel = new PropertiesTableModel(tabbedPane);		
		JTable table = new JTable(propertiesTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(150,150));
		add(scrollPane, BorderLayout.SOUTH);*/
		
		/** Display the window*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
	
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		JMenuItem mItemNew = new JMenuItem("New");
		mItemNew.addActionListener(this);
		menuFile.add(mItemNew);
		
		JMenuItem mItemOpen = new JMenuItem("Open");
		mItemOpen.addActionListener(this);
		menuFile.add(mItemOpen);
		
		JMenuItem mItemSave = new JMenuItem("Save");
		mItemSave.addActionListener(this);
		menuFile.add(mItemSave);
		
		JMenuItem mItemSaveAs = new JMenuItem("Save as...");
		mItemSaveAs.addActionListener(this);
		menuFile.add(mItemSaveAs);		

		JMenuItem mItemExport = new JMenuItem("Export...");
		mItemExport.addActionListener(this);
		menuFile.add(mItemExport);		
		
		JMenuItem mItemExit = new JMenuItem("Exit");
		mItemExit.addActionListener(this);
		menuFile.add(mItemExit);
		
		JMenu menuTools = new JMenu("Tools");
		menuBar.add(menuTools);
		
		JMenu menuImport = new JMenu("Import plugin");
		menuTools.add(menuImport);

		

		JMenuItem mItemPlugin = new JMenuItem("Load plugin");
		menuTools.add(mItemPlugin);
		
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		JMenuItem mItemAbout = new JMenuItem("About");
		mItemAbout.addActionListener(this);
		menuHelp.add(mItemAbout);

		return menuBar;
	}
	
	public static void main(String[] args) {
		MainFrame le = new MainFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("New")){
			DocumentCanvas dp = new DocumentCanvas(this);
			tabbedPane.addTab("*New Document "+(ncondoc++), dp);
		}				
		else if(command.equals("Save")){
			int selIndex = tabbedPane.getSelectedIndex();
			if(selIndex!=-1){
				
				DocumentCanvas dc = (DocumentCanvas)tabbedPane.getComponent(selIndex);
				
				if(!dc.inFile){
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("Flowchart Editor", "dfe"));				
					int returnVal = fc.showSaveDialog(this);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {	
						String filename = fc.getSelectedFile().getAbsolutePath();
						try {
							
							if(!filename.endsWith(".dfe")) filename=filename+".dfe";
							dc.saveAsXMLTo(filename);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						
						tabbedPane.setTitleAt(selIndex, fc.getSelectedFile().getName());
					}
				}
				else{
					if(!dc.saved){
						try {
							dc.saveAsXMLTo(dc.filename);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
						
				}
				
				
				
			}
		}
		else if(command.startsWith("Save as")){
			int selIndex = tabbedPane.getSelectedIndex();
			if(selIndex!=-1){
				DocumentCanvas dc = (DocumentCanvas)tabbedPane.getComponent(selIndex);
				
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Flowchart Editor", "dfe"));				
				int returnVal = fc.showSaveDialog(this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {	
					String filename = fc.getSelectedFile().getAbsolutePath();
					try {
						
						if(!filename.endsWith(".dfe")) filename=filename+".dfe";
						dc.saveAsXMLTo(filename);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
					tabbedPane.setTitleAt(selIndex, fc.getSelectedFile().getName());
				}
			}
		}
		else if(command.equals("Open")){
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Flowchart Editor", "dfe"));
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					DocumentCanvas dp = new DocumentCanvas(this, fc.getSelectedFile().getAbsolutePath());
					tabbedPane.addTab(fc.getSelectedFile().getName(), dp);
					tabbedPane.setSelectedComponent(dp);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		}
		else if(command.startsWith("Exit")){
			System.exit(NORMAL);
		}
		else if(command.startsWith("Export")){
			int selIndex = tabbedPane.getSelectedIndex();
			if(selIndex!=-1){
				
				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false);
				String writerNames[] = ImageIO.getWriterFormatNames();
				String writerNamesTmp[] = new String[writerNames.length+1];
				for(int i=0;i<writerNames.length;i++) writerNamesTmp[i]=writerNames[i];
				writerNamesTmp[writerNames.length] = "svg";
				writerNames=writerNamesTmp;
				for (int i = 1; i < writerNames.length; i++) {
					fc.setFileFilter(new FileNameExtensionFilter(writerNames[i], writerNames[i]));
				}
				//
				
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					DocumentCanvas dc = (DocumentCanvas)tabbedPane.getComponent(selIndex);
					File f = fc.getSelectedFile();				
					String ext = fc.getFileFilter().getDescription();					
					
					if(f.getName().endsWith("."+ext)) dc.saveImage(f.getAbsolutePath(), ext);
					else dc.saveImage(f.getAbsolutePath()+"."+ext, ext);
				}
			}
			
		}
		/*else if(command.startsWith("About")){
			JDialog about = new AboutDialog(this);
			about.pack();
			about.setVisible(true);
		}
		else if(command.startsWith("output.")){
			int selIndex = tabbedPane.getSelectedIndex();
			if(selIndex!=-1){
				DocumentPanel dp = (DocumentPanel)tabbedPane.getComponent(selIndex);
				dp.render(command.replaceAll("output.", ""));
			}			
		}*/
		
				
	}

	public String getSelectedTool() {
		return toolBar.getSelectedTool();
	}
	
}
