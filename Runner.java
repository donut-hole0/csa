// Students I worked with: Shourya Pallachulla
// Reflection Questions: 
// 1. a. I think words that end with ing or ed or any of those kind of suffixes are not great for chainlinks
// b. I don't think there are any right now that I can think of to keep.
// 2. I'm not completely sure how to answer this question but I would try to find unique words in the text and take the word next to it
// 3. Small words (length 4 or less)
// 4. I'm having a lot of difficulty with the logic for creating the chain links

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Runner extends JPanel implements KeyListener{
	
    Font largerFont = new Font("Arial", Font.BOLD, 36); 
    private ArrayList<String> book = new ArrayList<String>();

    
    private HashMap<String, ArrayList<String>> bigram = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> link = new ArrayList<String>();

    private int solvedUntil = 0;
    private String currentGuess = ""; 
    private String feedback = "Type your guess and press Enter";

	public void paint(Graphics pen) {
		pen.setColor(Color.WHITE);
	    pen.fillRect(0, 0, getWidth(), getHeight());
	    int y = 100;
		if (link != null && link.size() > 0) {
	        pen.setColor(Color.black);
	        pen.setFont(largerFont);
	        
	        
	        pen.drawString(link.get(0), 100, y);
	        
	        y = 200;
	        for(int i = 1; i < link.size(); i++) {
	        	if (i <= solvedUntil) {
	                pen.setColor(new Color(0, 150, 0));
	                pen.drawString(link.get(i), 100, y);
	            } else {
	                pen.setColor(Color.BLACK);
	                String hidden = link.get(i).substring(0, 1);
	                for (int j = 0; j < link.get(i).length() - 1; j++) {
	                    hidden += " _";
	                }
	                pen.drawString(hidden, 100, y);
	            }
	            y += 100;
	        }
	        
	    }
		pen.drawString("Press the $ key for a hint", 100, 700);

		pen.drawString("Guess: " + currentGuess, 100, 800);
		pen.drawString(feedback, 100, 900);
	}
	

	public static void main(String[] arg) {
		Runner m = new Runner();
    	
	}
	
	public Runner(){
		JFrame f = new JFrame("Chain Link Game");
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000,1000);
		f.add(this);
		f.addKeyListener(this);
		readFile();

		boolean works = false;
		while(!works) {
			int rand =(int)( Math.random() * (book.size()-1));
			String word = book.get((int) rand);
			
			if(word.length() > 4) {
				if(chainlink(word)) {
					works = true;
				}
			}

		}
		f.setVisible(true);
		System.out.println(link);
		
		

	}

	// reading file
	public void readFile(){
		File file = new File("thegreatgatsby.txt");
		Scanner s;
		try {
			s = new Scanner(file);
			while(s.hasNext() ) {
				String line = s.next();
				book.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	// generate hash map for word
	public void hash(String word) {
	    ArrayList<String> unique = new ArrayList<String>();
		for(int i = 0; i<book.size()-1; i++) {
			if(book.get(i).equalsIgnoreCase(word)) {
				String next = book.get(i+1);
				if(!unique.contains(next)) {
					unique.add(next);
				}
			}
		}
		bigram.put(word.toLowerCase(), unique);
		
		

	}
	
	public boolean chainlink(String word) {
		link.clear();
		String current = word;
		link.add(word);
		for(int i =0; i<5; i++) {
			hash(current);
			ArrayList<String> possible = bigram.get(current.toLowerCase());
			if(possible != null) {
				String nextWord = "";
				for(String x : possible) {
					if (x.length()>4) {
						nextWord = x;
						break;
					}
				}
				
				if(!nextWord.equals("")) {
					link.add(nextWord);
					current = nextWord;
				} else {
					return false;
				}
			} else{
				return false;
			}
		}
		return true;
	}
	
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (currentGuess.length() > 0) {
	            currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
	        }
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			String target = link.get(solvedUntil + 1);
	        if (currentGuess.equalsIgnoreCase(target)) {
	            solvedUntil++;
	            feedback = "Correct!";
	            currentGuess = ""; // Clear for next word
	            if (solvedUntil == link.size() - 1) {
	                feedback = "You won!";
	            }
	        } else {
	            feedback = "Wrong! Try again.";
	            currentGuess = "";
	        }
		} else if (e.getKeyChar() == '$') {
	        // HINT LOGIC
	        if (solvedUntil + 1 < link.size()) {
	            String target = link.get(solvedUntil + 1);
	            // Show the first two letters as a hint
	            feedback = "HINT: The word starts with " + target.substring(0, 2).toUpperCase();
	        }
		} else {
			char c = e.getKeyChar();
	        if (Character.isLetterOrDigit(c)) {
	            currentGuess += c;
	        }
		}
		repaint();
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
