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
	//--------------need to use try catch but I do not know how to frame them------------------------------------------
	
	// a map of code = actor
	HashMap<String, String> codeActor = new HashMap<String, String>();
	
	// a map of code = movie
	HashMap<String, String> codeMovie = new HashMap<String, String>();
	
	
	public KevinBaconGame() throws IOException 
	{
		// creating readers and file variables for all files
		File actors = new File("actors.txt");
		File movies = new File("movies.txt");
		File movieActors = new File("movie-actors.txt");
		BufferedReader actorsReader = new BufferedReader(new FileReader(actors));
		BufferedReader moviesReader = new BufferedReader(new FileReader(movies));
		BufferedReader moviesActors = new BufferedReader(new FileReader(movieActors));
		
		//FileReader moviesActors = new FileReader(movieActors);
		
		//creating a graph
		EdgeGraph<String, String> graph = new EdgeGraph<String, String>();
		
		int k = 0;
		for (String line = actorsReader.readLine(); line != null; line = actorsReader.readLine())
		{
			String[] array = line.split("~");
			codeActor.put(array[0], array[1]);
			graph.addVertex(array[1]);
			k++;
		}
		actorsReader.close();
		
		for (String line = moviesReader.readLine(); line != null; line = moviesReader.readLine())
		{
			String[] array = line.split("~");
			codeMovie.put(array[0], array[1]);
		}
		
		
		String prevKey = "";
		String key = "";
		int connections = 0;
		ArrayList<String> codes = new ArrayList<String>();
		for (String line = moviesActors.readLine(); line != null; line = moviesActors.readLine())
		{
			String[] array = line.split("~");
			key = array[0];
			
			if(prevKey.equals(key)|| prevKey == "")
			{
				codes.add(array[1]);
			}
			else if (prevKey.equals(key) == false && prevKey != "")
			{
				for (int j = 0; j < codes.size(); j++)
				{
					for (int n = j + 1; n < codes.size(); n++)
					{
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
				codes.clear();
				
			}
			prevKey = key;
			key = "";
		}
		
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
		
		// actor 1 text field
		JTextArea actor1 = new JTextArea();
		actor1.setEditable(true);
		actor1.setPreferredSize(new Dimension(100, 20));
		JLabel one = new JLabel("Enter the first actor: ");
		top.add(one);
		top.add(actor1);
		
		// actor 2 text field
		JTextArea actor2 = new JTextArea();
		actor2.setEditable(true);
		actor2.setPreferredSize(new Dimension(100, 20));
		JLabel two = new JLabel("Enter the second actor: ");
		top.add(two);
		top.add(actor2);
		
		// display area to display results
		JTextArea display = new JTextArea();
		display.setEditable(false);
		display.setPreferredSize(new Dimension(560, 400));
		display.setLineWrap(true);
        display.setWrapStyleWord(true);
		top.add(display);
		
		// search button to run BFS
		JButton shortest = new JButton("Shortest path");
		bottom.add(shortest);
		
		shortest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String first = actor1.getText().trim();
				String second = actor2.getText().trim();
				
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
				
				if (graph.search(first, second) == null)
				{
					display.setText("Sorry, there is no information you are looking for. You might want to check if actors' names are correct, or we might just not have them in our database. ");
				}
				else
				{
					display.setText((graph.search(first, second).toString()));
				}
			}
		});
		
		// farthest button to run farthest method
		JButton farthest = new JButton("Farthest actor");
		bottom.add(farthest);
		
		farthest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String first = actor1.getText().trim();
				
				if (first.equals(""))
				{
					display.setText("Please input the first actor and run again. ");
					return;
				}
				
				if (graph.farthest(first) == null)
				{
					display.setText("Sorry, there is no information you are looking for. You might want to check if actors' names are correct, or we might just not have them in our database. ");
				}
				else
				{
					display.setText((graph.farthest(first)).toString());
				}
			}
		});
		
		JButton ways = new JButton("3 Shortest Ways");
		bottom.add(ways);
		
		ways.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String first = actor1.getText().trim();
				String second = actor2.getText().trim();
				
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
				
				if (graph.threeWays(first, second) == null)
				{
					display.setText("Sorry, there is no information you are looking for. You might want to check if actors' names are correct, or we might just not have them in our database. ");
				}
				else 
				{
					graph.threeWays(first, second);
					String output = "1) ";
					
					ArrayList<ArrayList<Object>> ways = graph.threeWays(first, second);
					output = output + ways.get(0) + "\n\n" + "2) " + ways.get(1) + "\n\n" + "3) " + ways.get(2);
		
					display.setText(output);
				}
			}
		});
		
		
		// creating a button to switch between themes
		JButton theme = new JButton("Swtich theme");
		bottom.add(theme);
		
		// getting the original colors of panels to use it in light mode later
		Color panelcolor = panel.getBackground();
		Color bottomColor = bottom.getBackground();
				
		// if you click on the dark mode button, it will set backgrounds of buttons panel and overall panel to dark palette
		theme.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// if it's black -- go for white
				if (panel.getBackground().equals(new Color (90, 130, 100)))
				{
					bottom.setBackground(bottomColor);
					panel.setBackground(panelcolor);
					display.setBackground(Color.white);
					top.setBackground(panelcolor);
				}
				else 
				{
					bottom.setBackground(new Color (88, 110, 117));
					top.setBackground(new Color (90, 130, 100));
					display.setBackground(new Color(220, 220, 220));
					panel.setBackground(new Color (90, 130, 100));
				}
			}
		});
		
		JButton rules = new JButton("Rules and Instructions");
		bottom.add(rules);
		rules.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
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
		
		
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
	// another issue is how do I make it case sensitive?
}


	public static void main(String[] args) throws IOException
	{
		new KevinBaconGame();
	}
}
