import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardRunner {
	
	private JTextArea display;
//	private JTextArea answer;
	private ArrayList<FlashCard> cardList;
	private Iterator cardIterator;
	private FlashCard currentCard;
//	private int currentCardIndex;
	private JFrame frame;
	private boolean isShowAnswer;
	private JButton showAnswer;


	
	public FlashCardRunner() {
		//building UI
		frame = new JFrame("Flash Card Player");
		JPanel mainPanel = new JPanel();
		Font font = new Font("Sans-seriff", Font.BOLD, 20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		display = new JTextArea(10, 30);
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setFont(font);
		
		JScrollPane questionPane = new JScrollPane(display);
		questionPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		questionPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		showAnswer = new JButton("Show Answer");
		
		mainPanel.add(questionPane);
		mainPanel.add(showAnswer);
		showAnswer.addActionListener(new NextCardListener());
	
	
		
		//add menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load Flash Cards");
		loadMenuItem.addActionListener(new OpenMenuListener());
		
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		
		
		//add to frame
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}
	
	
	
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new FlashCardRunner();
			}

		});
	}
	
	class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isShowAnswer) {
				display.setText(currentCard.getAnswer());
				showAnswer.setText("Next Card");
				isShowAnswer = false;
			} else {
				//show next question
				if (cardIterator.hasNext()) {
					
					showNextCard();
				} else {
					// no more cards
					display.setText("No More Cards :(");
					showAnswer.setEnabled(false);
				}
			}
		}
	
	}
	
	
	
	class OpenMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			//calling load file method
			loadFile(fileOpen.getSelectedFile());
		}

		
	}
	
	
	
	private void loadFile(File selectedFile) {
		
		cardList= new ArrayList<FlashCard>();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
			String line = null;
			//while there are lines to read = true, while there are no more lines = false
			while((line = reader.readLine()) != null) {
				makeCard(line);
			}
			reader.close();
			
		} catch (Exception e) {

		}
		//show first card
		cardIterator = cardList.iterator();
		showNextCard();
	}
	
	
	
	
	private void showNextCard() {
		currentCard = (FlashCard) cardIterator.next();
		
		display.setText(currentCard.getQuestion());
		showAnswer.setText("Show Answer");
		isShowAnswer = true;
	}

	
	
	
	//parsing the question from the answers in the saved file
	private void makeCard(String line) {
		String[] result = line.split(" -> ");
		
		FlashCard card = new FlashCard(result[0], result[1]);
		cardList.add(card);
		System.out.println("Card created!");
		
	}
}
