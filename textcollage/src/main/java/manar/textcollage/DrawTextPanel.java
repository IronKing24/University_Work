package manar.textcollage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.plaf.ColorUIResource;

/**
 * A panel that contains a large drawing area where strings
 * can be drawn.  The strings are represented by objects of
 * type DrawTextItem.  An input box under the panel allows
 * the user to specify what string will be drawn when the
 * user clicks on the drawing area.
 */
public class DrawTextPanel extends JPanel  {
	
	// As it now stands, this class can only show one string at at
	// a time!  The data for that string is in the DrawTextItem object
	// named theString.  (If it's null, nothing is shown.  This
	// variable should be replaced by a variable of type
	// ArrayList<DrawStringItem> that can store multiple items.
	
	private ArrayList<DrawTextItem> theString = new ArrayList<DrawTextItem>() ;  
	
	private Color currentTextColor = Color.BLACK;  // Color applied to new strings.

	private Canvas canvas;  // the drawing area.
	private JTextField input;  // where the user inputs the string that will be added to the canvas
	private SimpleFileChooser fileChooser;  // for letting the user select files
	private JMenuBar menuBar; // a menu bar with command that affect this panel
	private MenuHandler menuHandler; // a listener that responds whenever the user selects a menu command
	private FocusListener foucsHandler; // a listener that responds as failsafe if the user forgets to confirm input on text fields
	private JMenuItem undoMenuItem;  // the "Remove Item" command from the edit menu
	
	//new
	final String[] fontList = {Font.SANS_SERIF, Font.DIALOG, Font.DIALOG_INPUT, Font.MONOSPACED, Font.SERIF }; //list of font types
	int bold = 0; // 1 or 0 for bold
	int italic = 0; // 1 or 0 for italics
	int fontSize = 12; //font size
	Font currentFont; // the starter font
	ColorUIResource backColor;
	boolean border = false; 
	double rotation = 0;
	double magnifcation = 1;
	double transparncy = 0.3;
	double bgTransparncy = 0.7;

	//check event source and global accsasability
	private JMenuItem saveItem;
	private JMenuItem openItem;
	private JMenuItem saveImageItem;
	private JMenuItem clearItem;
	private JMenuItem colorItem;
	private JMenuItem bgColorItem;
	private JComboBox<String> fontlist;
	private JCheckBoxMenuItem boldCheckbox;
	private JCheckBoxMenuItem ItalicCheckbox;
	private JTextField sizeTextField;
	private JCheckBoxMenuItem borderCheckbox;
	private JTextField rotationField;
	private JTextField magnifcationTextField;
	private JTextField transparncyTextField;
	private JTextField bgTransparncyField;
	
	
	
	/**
	 * An object of type Canvas is used for the drawing area.
	 * The canvas simply displays all the DrawTextItems that
	 * are stored in the ArrayList, strings.
	 */
	private class Canvas extends JPanel {
		Canvas() {
			setPreferredSize( new Dimension(800,600) );
			setBackground(Color.LIGHT_GRAY);
			setFont( new Font( "Serif", Font.BOLD, 24 ));
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
			if (theString.size() > 0){
				for (DrawTextItem drawTextItem : theString) {
					drawTextItem.draw(g);
				}
			}
		}
	}
	
	/**
	 * An object of type MenuHandler is registered as the ActionListener
	 * for all the commands in the menu bar.  The MenuHandler object
	 * simply calls doMenuCommand() when the user selects a command
	 * from the menu.
	 */
	private class MenuHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			doMenuCommand( evt.getSource());
		}
	}
	
	/**
	 * An object of type MenuFeildFoucsHandler is registered as the FocusListener
	 * for all the Jtextfeilds on the menubar.  The MenuFeildFoucsHandler object
	 * simply calls doMenuCommand() when the Jtext feild losses foucs.
	 */
	private class MenuFeildFoucsHandler implements FocusListener{
		public void focusGained(FocusEvent e){} //not acctually needed
		public void focusLost(FocusEvent e){
			doMenuCommand( e.getSource());
		}
	}

	/**
	 * Creates a DrawTextPanel.  The panel has a large drawing area and
	 * a text input box where the user can specify a string.  When the
	 * user clicks the drawing area, the string is added to the drawing
	 * area at the point where the user clicked.
	 */
	public DrawTextPanel() {
		fileChooser = new SimpleFileChooser();
		undoMenuItem = new JMenuItem("Remove Item");
		undoMenuItem.setEnabled(false);
		menuHandler = new MenuHandler();
		foucsHandler = new MenuFeildFoucsHandler();
		setLayout(new BorderLayout(3,3));
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.add(new JLabel("Text to add: "));
		input = new JTextField("Hello World!", 40);
		bottom.add(input);
		add(bottom, BorderLayout.SOUTH);
		canvas.addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				canvas.grabFocus();
				doMousePress( e );
			}
		} );
	}
		
	/**
	 * This method is called when the user clicks the drawing area.
	 * A new string is added to the drawing area.  The center of
	 * the string is at the point where the user clicked.
	 * @param e the mouse event that was generated when the user clicked
	 */
	public void doMousePress( MouseEvent e ) {
		String text = input.getText().trim();
		if (text.length() == 0) {
			input.setText("Hello World!");
			text = "Hello World!";
		}
		DrawTextItem s = new DrawTextItem( text, e.getX(), e.getY() );
		s.setTextColor(currentTextColor);  // Default is null, meaning default color of the canvas (black).
		
//   SOME OTHER OPTIONS THAT CAN BE APPLIED TO TEXT ITEMS:
		s.setFont(currentFont);  // Default is null, meaning font of canvas.
		s.setMagnification(magnifcation);  // Default is 1, meaning no magnification.
		s.setBorder(border);  // Default is false, meaning don't draw a border.
		s.setRotationAngle(rotation);  // Default is 0, meaning no rotation.
		s.setTextTransparency(transparncy); // Default is 0, meaning text is not at all transparent.
		s.setBackground(Color.BLUE);  // Default is null, meaning don't draw a background area.
		s.setBackgroundTransparency(bgTransparncy);  // Default is 0, meaning background is not transparent.
	
		theString.add(s);  // Set this string as the ONLY string to be drawn on the canvas!
		undoMenuItem.setEnabled(true);
		canvas.repaint();
	}
	
	/**
	 * Returns a menu bar containing commands that affect this panel.  The menu
	 * bar is meant to appear in the same window that contains this panel.
	 */
	public JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();

			String commandKey; // for making keyboard accelerators for menu commands
			if (System.getProperty("mrj.version") == null)
				commandKey = "control ";  // command key for non-Mac OS
			else
				commandKey = "meta ";  // command key for Mac OS
			
			JMenu fileMenu = new JMenu("File");
			menuBar.add(fileMenu);
			saveItem = new JMenuItem("Save...");
			saveItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "N"));
			saveItem.addActionListener(menuHandler);
			fileMenu.add(saveItem);
			openItem = new JMenuItem("Open...");
			openItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "O"));
			openItem.addActionListener(menuHandler);
			fileMenu.add(openItem);
			fileMenu.addSeparator();
			saveImageItem = new JMenuItem("Save Image...");
			saveImageItem.addActionListener(menuHandler);
			fileMenu.add(saveImageItem);
			
			JMenu editMenu = new JMenu("Edit");
			menuBar.add(editMenu);
			undoMenuItem.addActionListener(menuHandler); // undoItem was created in the constructor
			undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "Z"));
			editMenu.add(undoMenuItem);
			editMenu.addSeparator();
			clearItem = new JMenuItem("Clear");
			clearItem.addActionListener(menuHandler);
			editMenu.add(clearItem);
			
			JMenu optionsMenu = new JMenu("Options");
			menuBar.add(optionsMenu);
			colorItem = new JMenuItem("Set Text Color...");
			colorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "T"));
			colorItem.addActionListener(menuHandler);
			optionsMenu.add(colorItem);
			bgColorItem = new JMenuItem("Set Background Color...");
			bgColorItem.addActionListener(menuHandler);
			optionsMenu.add(bgColorItem);
			

			//additinal feature add the option to control DrawTextitem options
			//fonts menu UI 
			fontlist = new JComboBox<String>(fontList);
			fontlist.setSelectedIndex(0);
			fontlist.addActionListener(menuHandler);
			menuBar.add(fontlist);

			//bloding menu UI
			boldCheckbox = new JCheckBoxMenuItem("Bold", bold == 1 ? true : false);
			boldCheckbox.addActionListener(menuHandler);
			optionsMenu.add(boldCheckbox);

			//italics menu UI
			ItalicCheckbox = new JCheckBoxMenuItem("Italic", italic == 1 ? true : false);
			ItalicCheckbox.addActionListener(menuHandler);
			optionsMenu.add(ItalicCheckbox);

			//font size UI
			JTextPane sizeText = new JTextPane();
			sizeText.setText("size");
			sizeText.setEditable(false);
			sizeText.setSize(2,1);
			menuBar.add(sizeText);

			sizeTextField = new JTextField();
			sizeTextField.setText(""+ fontSize);
			sizeTextField.addFocusListener(foucsHandler);
			sizeTextField.addActionListener(menuHandler);
			menuBar.add(sizeTextField);

			//border menu UI
			borderCheckbox = new JCheckBoxMenuItem("Border", border);
			borderCheckbox.addActionListener(menuHandler);
			optionsMenu.add(borderCheckbox);

			//magnification UI
			JTextPane magnificationText = new JTextPane();
			magnificationText.setText("Magnification");
			magnificationText.setEditable(false);
			magnificationText.setSize(2,1);
			menuBar.add(magnificationText);

			magnifcationTextField = new JTextField();
			magnifcationTextField.setText(""+ magnifcation);
			magnifcationTextField.addActionListener(menuHandler);
			magnifcationTextField.addFocusListener(foucsHandler);
			menuBar.add(magnifcationTextField);

			//rotation UI
			JTextPane rotationText = new JTextPane();
			rotationText.setText("Rotation");
			rotationText.setEditable(false);
			rotationText.setSize(2,1);
			menuBar.add(rotationText);

			rotationField = new JTextField();
			rotationField.setText(""+ rotation);
			rotationField.addActionListener(menuHandler);
			rotationField.addFocusListener(foucsHandler);
			menuBar.add(rotationField);

			//transparacy UI
			JTextPane AlphaText = new JTextPane();
			AlphaText.setText("Text alpha");
			AlphaText.setEditable(false);
			AlphaText.setSize(2,1);
			menuBar.add(AlphaText);

			transparncyTextField = new JTextField();
			transparncyTextField.setText("" + transparncy);
			transparncyTextField.addActionListener(menuHandler);
			transparncyTextField.addFocusListener(foucsHandler);
			menuBar.add(transparncyTextField);

			//background transparancy UI
			JTextPane BGText = new JTextPane();
			BGText.setText("Text BG alpha");
			BGText.setEditable(false);
			BGText.setSize(2,1);
			menuBar.add(BGText);

			bgTransparncyField = new JTextField();
			bgTransparncyField.setText("" +  bgTransparncy);
			bgTransparncyField.addActionListener(menuHandler);
			bgTransparncyField.addFocusListener(foucsHandler);
			menuBar.add(bgTransparncyField);
		}

		currentFont = new Font(fontList[fontlist.getSelectedIndex()], Font.ITALIC * italic + Font.BOLD * bold, fontSize); //initilize font
		return menuBar;
	}
	
	/**
	 * Carry out one of the commands from the menu bar.
	 * @param command the text of the menu command.
	 */
	private void doMenuCommand(Object command) {
		if (command.equals(saveItem)) { // save all the string info to a .TC file as modified CSV style file
			FileWriter savefileWriter;
			try{
				File file = fileChooser.getOutputFile(this, "Select *.tc File Name", "textcollage.tc");
				if(file == null)
					return;
				savefileWriter = new FileWriter(file);
				savefileWriter.write(
					Integer.toHexString(canvas.getBackground().getRGB())
				 	+'\n');
				for(int i = 0; i < theString.size(); i++){
					savefileWriter.write(theString.get(i).getString() +','
					+ (theString.get(i).getFont() != null ? theString.get(i).getFont().getName() +','
					+ theString.get(i).getFont().getStyle() + ','
					+ theString.get(i).getFont().getSize() : ", , , ") + ','
					+ theString.get(i).getX() +','
					+ theString.get(i).getY() +','
					+ (theString.get(i).getTextColor() != null ? Integer.toHexString(theString.get(i).getTextColor().getRGB()): " ") +','
					+ (theString.get(i).getBackground() != null ? Integer.toHexString(theString.get(i).getBackground().getRGB()): " ")  +','
					+ theString.get(i).getBorder() +','
					+ theString.get(i).getRotationAngle() +','
					+ theString.get(i).getMagnification() +','
					+ theString.get(i).getTextTransparency() +','
					+ theString.get(i).getBackgroundTransparency()
					+'\n');
				}
				savefileWriter.flush();
				savefileWriter.close();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Somkthing went wrong during the while we tried to save, sorry");
			}
		}
		else if (command.equals(openItem)) { // read a previously saved file, and reconstruct the list of strings
			BufferedReader savefileReader;
			ArrayList<DrawTextItem> newString = new ArrayList<DrawTextItem>();
			try{
				File file = fileChooser.getInputFile(this, "Select *.tc File Name");
				if(file == null)
					return;
				savefileReader = new BufferedReader(
					new FileReader(file));
				canvas.setBackground(new ColorUIResource((int)Long.parseLong(savefileReader.readLine(), 16)));
				
				String lineHolder= savefileReader.readLine();
				while(lineHolder!= null || lineHolder == ""){
					String [] valuesStr = lineHolder.split(",");
					DrawTextItem text = new DrawTextItem(valuesStr[0]);
					text.setFont( new Font(valuesStr[1], Integer.parseInt(valuesStr[2]), Integer.parseInt(valuesStr[3])));
					text.setX(Integer.parseInt(valuesStr[4]));
					text.setY(Integer.parseInt(valuesStr[5]));
					text.setTextColor(new ColorUIResource((int)Long.parseLong(valuesStr[6], 16)));
					text.setBackground(valuesStr[7] == " " ? null :new ColorUIResource((int)Long.parseLong(valuesStr[7], 16)));
					text.setBorder(Boolean.parseBoolean(valuesStr[8]));
					text.setRotationAngle(Double.parseDouble(valuesStr[9]));
					text.setMagnification(Double.parseDouble(valuesStr[10]));
					text.setTextTransparency(Double.parseDouble(valuesStr[11]));
					text.setBackgroundTransparency(Double.parseDouble(valuesStr[12]));
					newString.add(text);
					lineHolder= savefileReader.readLine();
				}

				savefileReader.close();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "file is corrupted");
			}

			theString = newString;
			for (DrawTextItem drawTextItem : theString) {
				drawTextItem.draw(canvas.getGraphics());
			}
			canvas.repaint(); // (you'll need this to make the new list of strings take effect)
		}
		else if (command.equals(clearItem)) {  // remove all strings
			theString.clear();   // Remove the ONLY string from the canvas.
			undoMenuItem.setEnabled(false);
			canvas.repaint();
		}
		else if (command.equals(undoMenuItem)) { // remove the most recently added string
			theString.remove(theString.size()-1);   // Remove the ONLY string from the canvas.
			undoMenuItem.setEnabled(false);
			canvas.repaint();
		}
		else if (command.equals(colorItem)) {
			Color c = JColorChooser.showDialog(this, "Select Text Color", currentTextColor);
			if (c != null)
				currentTextColor = c;
		}
		else if (command.equals(bgColorItem)) {
			Color c = JColorChooser.showDialog(this, "Select Background Color", canvas.getBackground());
			if (c != null) {
				canvas.setBackground(c);
				canvas.repaint();
			}
		}
		else if (command.equals(saveImageItem)) {  // save a PNG image of the drawing area
			File imageFile = fileChooser.getOutputFile(this, "Select Image File Name", "textimage.png");
			if (imageFile == null)
				return;
			try {
				// Because the image is not available, I will make a new BufferedImage and
				// draw the same data to the BufferedImage as is shown in the panel.
				// A BufferedImage is an image that is stored in memory, not on the screen.
				// There is a convenient method for writing a BufferedImage to a file.
				BufferedImage image = new BufferedImage(canvas.getWidth(),canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
				Graphics g = image.getGraphics();
				g.setFont(canvas.getFont());
				canvas.paintComponent(g);  // draws the canvas onto the BufferedImage, not the screen!
				boolean ok = ImageIO.write(image, "PNG", imageFile); // write to the file
				if (ok == false)
					throw new Exception("PNG format not supported (this shouldn't happen!).");
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, 
						"Sorry, an error occurred while trying to save the image:\n" + e);
			}
		}
		else if(command.equals(fontlist) || command.equals(boldCheckbox) || command.equals(ItalicCheckbox) || command.equals(sizeTextField)){ // if any change happens to font, make a new one
			//bolden
			if( command.equals(boldCheckbox)){
				bold = boldCheckbox.isArmed() == false? 0:1;
			}
			//italicize
			if(command.equals(ItalicCheckbox)){
				italic = ItalicCheckbox.isArmed() == false? 0:1;
			}
			//size
			if(command.equals(sizeTextField)){
				int currentSize = fontSize;
				try{
					fontSize = Integer.parseInt(sizeTextField.getText());
				}
				catch(Exception e){
					fontSize = currentSize;
				}
			}
			currentFont = new Font(fontList[fontlist.getSelectedIndex()], Font.ITALIC * italic + Font.BOLD * bold, fontSize);
		}
		else if(command.equals(borderCheckbox)){//border logic on/off
			border = !border; 
		}
		else if(command.equals(magnifcationTextField)){//magnifcation logic
			double currentMagnifaction = magnifcation;
			try{
				double newmagnifcation = Double.parseDouble(magnifcationTextField.getText());
				if(newmagnifcation> 1){
					magnifcation = 1;
				}
				else if(newmagnifcation < 0){
					magnifcation = 0.001;
				}
				else{
					magnifcation = newmagnifcation;
				}
				magnifcationTextField.setText(""+ magnifcation);
			}
			catch(Exception e)
			{
				magnifcation = currentMagnifaction;
			}
		}
		else if(command.equals(rotationField)){//rotaition logic
			double currentRotaion = rotation;
			try{
				double newrotation = Double.parseDouble(rotationField.getText());
				rotation = newrotation < 0 ? newrotation* -1 : newrotation;
				rotationField.setText(""+ rotation);
			}
			catch(Exception e)
			{
				rotation = currentRotaion;
			}
		}
		else if(command.equals(transparncyTextField)){//font transparncy logic
			double currentTransparancy= transparncy;
			try{
				double newtransparncy = Double.parseDouble(transparncyTextField.getText());
				if(newtransparncy > 1){
					transparncy = 1;
				}
				else if(newtransparncy < 0){
					transparncy = 0; 
				}
				else{
					transparncy = newtransparncy;
				}
				transparncyTextField.setText(""+ transparncy);
			}
			catch(Exception e)
			{
				transparncy = currentTransparancy;
			}
		}
		else if(command.equals(bgTransparncyField)){//font background transparancy logic
			double currentbgTransparncy = bgTransparncy;
			try{
				double newbgTransparncy = Double.parseDouble(bgTransparncyField.getText());
				if(newbgTransparncy > 1){
					bgTransparncy = 1;
				}
				else if(newbgTransparncy < 0){
					bgTransparncy = 0 ;
				}
				else{
					bgTransparncy =  newbgTransparncy;
				}
				bgTransparncyField.setText(""+ bgTransparncy);
			}
			catch(Exception e)
			{
				bgTransparncy = currentbgTransparncy;
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"unrecognized command("+command.toString()+")");
		}
	}
}
