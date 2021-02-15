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
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		System.out.println(graph.farthest("Tom Hanks"));
		
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
		JButton search = new JButton("Search");
		bottom.add(search);
		
		search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// how to get the actual text it has??????????????????/
				graph.search(actor1.getText().trim(), actor2.getText().trim());
				display.setText((graph.search(actor1.getText().trim(), actor2.getText().trim()).toString()));
			}
		});
		
		JButton farthest = new JButton("Farthest actor");
		bottom.add(farthest);
		
		farthest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				graph.farthest(actor1.getText());
				display.setText((graph.farthest(actor1.getText())).toString());
			}
		});
		
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
}


	public static void main(String[] args) throws IOException
	{
		new KevinBaconGame();
	}
}
