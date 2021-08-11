package graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class KevinBaconGame 
{
	// EdgeGraph class is the only class that go with it, and also three text files
	
	// a map of code = actor
	HashMap<String, String> codeActor = new HashMap<String, String>();
	
	// a map of code = movie
	HashMap<String, String> codeMovie = new HashMap<String, String>();
	
	
	public KevinBaconGame() 
	{
		// creating readers and file variables for all files
		File actors = new File("actors.txt");
		File movies = new File("movies.txt");
		File movieActors = new File("movie-actors.txt");
		BufferedReader actorsReader;
		try {
			actorsReader = new BufferedReader(new FileReader(actors));
		
		BufferedReader moviesReader = new BufferedReader(new FileReader(movies));
		BufferedReader moviesActors = new BufferedReader(new FileReader(movieActors));
		
		//creating a graph
		EdgeGraph<String, String> graph = new EdgeGraph<String, String>();
		
		// looping through the file of actors and putting them into a hash map, adding them to the graph as vertices
		int k = 0;
		for (String line = actorsReader.readLine(); line != null; line = actorsReader.readLine())
		{
			String[] array = line.split("~");
			codeActor.put(array[0], array[1]);
			graph.addVertex(array[1]);
			k++;
		}
		actorsReader.close();
		
		// looping through the file of movies and putting them into the hash map
		for (String line = moviesReader.readLine(); line != null; line = moviesReader.readLine())
		{
			String[] array = line.split("~");
			codeMovie.put(array[0], array[1]);
		}
		
		// looping through the file of movies to actors codes
		// setting keys so I know what is the previous key 
		String prevKey = "";
		String key = "";
		int connections = 0;
		
		// an array list to keep all codes of actors from the same movie
		ArrayList<String> codes = new ArrayList<String>();
		for (String line = moviesActors.readLine(); line != null; line = moviesActors.readLine())
		{
			String[] array = line.split("~");
			key = array[0];
			
			// if previous key is the same as current or if this is a first time looping add actor to the array list
			if(prevKey.equals(key)|| prevKey == "")
			{
				codes.add(array[1]);
			}
			// if they are not equal and this is not the first line
			else if (prevKey.equals(key) == false && prevKey != "")
			{
				// loop through the array list and connect each element in it with another elements through nested loops
				for (int j = 0; j < codes.size(); j++)
				{
					for (int n = j + 1; n < codes.size(); n++)
					{
						// do not make another connection between the object itself
						if (j == n)
						{
							
						}
						else
						{
							graph.connect(codeActor.get(codes.get(j)), codeActor.get(codes.get(n)), codeMovie.get(array[0]));
							connections++;
						}
					}
			    }
				// clear the array list for the next movie
				codes.clear();
			}
			// update keys for the net movie
			prevKey = key;
			key = "";
		}
		
	// GUI ----------
		
		// create frames and panels, add panels to the frame, and set a title
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		frame.add(panel);
		panel.add(top);
		panel.add(bottom);
		panel.setBorder(BorderFactory.createTitledBorder("Kevin Bacon Game"));
		
		// laying out by y-axis
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		// actor 1 text field and label 
		JTextArea actor1 = new JTextArea();
		actor1.setEditable(true);
		actor1.setPreferredSize(new Dimension(100, 20));
		JLabel one = new JLabel("Enter the first actor: ");
		top.add(one);
		top.add(actor1);
		
		// actor 2 text field and label
		JTextArea actor2 = new JTextArea();
		actor2.setEditable(true);
		actor2.setPreferredSize(new Dimension(100, 20));
		JLabel two = new JLabel("Enter the second actor: ");
		top.add(two);
		top.add(actor2);
		
		// display area to display results of operations
			JTextArea display = new JTextArea();
			display.setEditable(false);
			display.setPreferredSize(new Dimension(560, 400));
			display.setLineWrap(true);
		
			// if the text is too much to fit, wrap it and to the top panel
	        display.setWrapStyleWord(true);
			top.add(display);
		
		// search button to run BFS
			JButton shortest = new JButton("Shortest path");
			bottom.add(shortest);
			
			// action listener to search button to run BFS
			shortest.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					// take the input from the user
					String first = actor1.getText().trim();
					String second = actor2.getText().trim();
					
					// if there is nothing in the input -- give a message
					if (first.equals(""))
					{
						display.setText("Please input the first actor and run again. ");
						return;
					}
					else if (second.equals(""))
					{
						display.setText("Please input the second actor and run again. ");
						return;
					}
					
					// if the user is trying to input two same names
					if (first.equals(second))
					{
						display.setText("You search a path from the actor to oneself, are you sure that this is what you meant? You are already in your destined place.");
					}
					// if there is no path or no information found
					else if (graph.search(first, second) == null)
					{
						display.setText("Sorry, there is no information you are looking for. You might want to check if actors' names are correct, or we might just not have them in our database. ");
					}
					// else just return a result of the operation
					else
					{
						display.setText((graph.search(first, second).toString()));
					}
				}
			});
		
		// farthest button to run farthest method that returns farthest actor to actor1
			JButton farthest = new JButton("Farthest actor");
			bottom.add(farthest);
			
			farthest.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					// get the first actor because we work with it only
					String first = actor1.getText().trim();
					
					// if there is no input
					if (first.equals(""))
					{
						display.setText("Please input the first actor and run again. ");
						return;
					}
					
					// if there is no path (handle null pointers)
					if (graph.farthest(first) == null)
					{
						display.setText("Sorry, there is no information you are looking for. You might want to check if actors' names are correct, or we might just not have them in our database. ");
					}
					// else return the result of the operation
					else
					{
						display.setText((graph.farthest(first)).toString());
					}
				}
			});
			
		// ways button to display 3 shortest ways from one actor to another
			JButton ways = new JButton("3 Shortest Ways");
			bottom.add(ways);
			
			ways.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					// take the input from the user
					String first = actor1.getText().trim();
					String second = actor2.getText().trim();
					
					// if there is no input for any of the fields
					if (first.equals(""))
					{
						display.setText("Please input the first actor and run again. ");
						return;
					}
					else if (second.equals(""))
					{
						display.setText("Please input the second actor and run again. ");
						return;
					}
					
					// if actors are equal there is no sense to run search
					if (first.equals(second))
					{
						display.setText("You search a path from the actor to oneself, are you sure that this is what you meant? You are already in your destined place.");
					}
					// handle null pointer if there is no path
					else if (graph.threeWays(first, second) == null)
					{
						display.setText("Sorry, there is no information you are looking for. You might want to check if actors' names are correct, or we might just not have them in our database. ");
					}
					// else return the result of the operation
					else 
					{
						// nice formatting of the output
						graph.threeWays(first, second);
						String output = "1) ";
						
						ArrayList<ArrayList<Object>> ways = graph.threeWays(first, second);
						output = output + ways.get(0) + "\n\n" + "2) " + ways.get(1) + "\n\n" + "3) " + ways.get(2);
			
						display.setText(output);
					}
				}
			});
		
		
		// creating a button to switch between themes
			JButton theme = new JButton("Switch theme");
			bottom.add(theme);
			
			// getting the original colors of panels to use it in the light mode later
			Color panelcolor = panel.getBackground();
			Color bottomColor = bottom.getBackground();
			
			// action listener for when you press a button
			theme.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					// if it's black -- go for white
					if (panel.getBackground().equals(new Color (255, 166, 180)))
					{
						bottom.setBackground(bottomColor);
						panel.setBackground(panelcolor);
						display.setBackground(Color.white);
						top.setBackground(panelcolor);
					}
					// if it's not black -- set it to black
					else 
					{
						// play around with colors and elements
						bottom.setBackground(new Color (255, 199, 204));
						top.setBackground(new Color (255, 166, 180));
						display.setBackground(new Color(255, 243, 243));
						panel.setBackground(new Color (255, 166, 180));
					}
				}
			});
		
		// a button to display how to use the game and what are the limitations
			JButton rules = new JButton("Rules and Instructions");
			bottom.add(rules);
			
			// action listener
			rules.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					// call a pop up informational window 
					// i needed to split the text because everything i found online about changing the size of the popup 
					// window did not work here
					JOptionPane message = new JOptionPane();
					message.showMessageDialog(frame, "Hello and welcome to the Kevin Bacon Game!\n" +
					                                  "You have two text fields to enter two actors,\n" + 
					                                  "and you can perform three operations on them.\n" + 
					                                  "First,  you can find the shortest path from one\n" +
					                                  "actor to another through movies they stage in.\n" +
					                                  "For the second one, you only need one actor and\n" +
					                                  "even if you input two, we will run the search for\n" +
					                                  "for the farthest actor in the map for just the\n" +
					                                  "first one. The third feature is to show three\n" +
					                                  "shortest paths how to get from the first actor\n" +
					                                  "to the second one. Names of actors have to be\n" +
					                                  "their real names inputted correctly. The program\n" +
					                                  "will ask you to try again if you inputted the name\n" +
					                                  "incorrectly.", "Rules and Instructions", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		
		// frame settings
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
}

	// run a game!
	public static void main(String[] args) throws IOException
	{
		new KevinBaconGame();
	}
}
