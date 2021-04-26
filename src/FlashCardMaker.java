import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardMaker {
	
	//instance variables
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<FlashCard> cardList;
	private JFrame frame;
	
	//constructor
	public FlashCardMaker() {
		//building UI
		frame = new JFrame("Flash Card");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creates JPanel to hold everything
		JPanel mainPanel = new JPanel();
		
		//instantiate array list
		cardList = new ArrayList<FlashCard>();
		
		//create font for questions and create dimensions of text box
		Font font = new Font("Sans-seriff", Font.BOLD, 20);
		question = new JTextArea(6, 20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(font);
		
		
		//question area
		JScrollPane questionPane = new JScrollPane(question);
		//allows vertical scrolling only
		questionPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		questionPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//create font for answer and dimensions of text box
		answer = new JTextArea(6, 20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(font);
		
		//answer area
		JScrollPane answerPane = new JScrollPane(answer);
		//allows vertical scrolling only
		answerPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		answerPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//creates a button to go to next card
		JButton nextButton = new JButton("Next Card");
		
		//create labels
		JLabel questionLabel = new JLabel("Question");
		JLabel answerLabel = new JLabel("Answer");
	
		
		//adding components to mainPanel
		mainPanel.add(questionLabel);
		mainPanel.add(questionPane);
		
		mainPanel.add(answerLabel);
		mainPanel.add(answerPane);
		
		mainPanel.add(nextButton);
		//calling NextCardListener method
		nextButton.addActionListener(new NextCardListener());
		
		//add to frame and center the mainPanel within the frame
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		//adjusting size and making the frame visible
		frame.setSize(400, 500);
		frame.setVisible(true);
		
		//creating menu bar
		JMenuBar menuBar = new JMenuBar();
		//creating drop down option in our menu
		JMenu fileMenu = new JMenu("File");
		//creating options within the drop down menu
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		//adding options to our menu bar
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		//adding drop down menu to menu bar
		menuBar.add(fileMenu);
		//adding menu bar to frame
		frame.setJMenuBar(menuBar);
		
		//Event Listeners
		//calling methods for our event listeners
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		
		
		
		
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new FlashCardMaker();
			}

		});

	}
	//method allows us to perform actions on the Next card button
	class NextCardListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//testing to make sure the action is working
			System.out.println("Button Clicked!");
			
			//create a card that gets question and answer text
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			//adding card to our array list
			cardList.add(card);
			//calling method to clear card
			clearCard();
			
		}
		
	}
	//adding event listeners to our drop down menu options
	class NewMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			
			
		}
		
	}
	//adding event listeners to our drop down menu options
	class SaveMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		FlashCard card = new FlashCard(question.getText(), answer.getText());
		cardList.add(card);
		
		//create file dialog to choose were the file will be saved
		JFileChooser fileSave = new JFileChooser();
		fileSave.showSaveDialog(frame);
		//calling save file method to save the selected file 
		saveFile(fileSave.getSelectedFile());
			
		
		}
	}
	//method clears the card by setting the text to empty and resets cursor back in question box
	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
	private void saveFile(File selectedFile) {

		try {
			//passing through the file writer so the buffered writer can be instantiated
			BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			
			Iterator<FlashCard> cardIterator = cardList.iterator();
			//while the flash card has next = true, while flash card has nothing left = false
			while(cardIterator.hasNext()) {
				FlashCard card = (FlashCard)cardIterator.next();
				//using our getter to retrieve question and appending to include the answer afterwards
				writer.write(card.getQuestion() + " -> ");
				writer.write(card.getAnswer() + "\n");
				
				//format: Question -> Answer
			}
			writer.close();
			
		}catch (Exception e) {
			System.out.println("Couldn't save file :(");
			e.printStackTrace();
		}
		
	}
}
