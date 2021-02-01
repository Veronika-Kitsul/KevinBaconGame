package graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Maps 
{
	// a map of code = actor
	HashMap codeActor = new HashMap();
	
	// a map of code = movie
	HashMap<String, String> codeMovie = new HashMap<String, String>();
	
	
	public Maps() throws IOException 
	{
		// creating file readers and file variable for all files
		File actors = new File("actors.txt");
		File movies = new File("movies.txt");
		File movieActors = new File("movie-actors.txt");
		//FileReader actorsReader = new FileReader(actors);
		FileReader moviesReader = new FileReader(movies);
		FileReader moviesActors = new FileReader(movieActors);
		
		//creating a graph
		EdgeGraph<String, String> graph = new EdgeGraph<String, String>();
		
		//trying to read files with buffered reader, probably do not use split well
		BufferedReader actorsReader = new BufferedReader(new FileReader(actors));
		
		for (String line = actorsReader.readLine(); line != null; line = actorsReader.readLine())
		{
			ArrayList string = new ArrayList();
			//string.add(line.split("~"));
			//codeActor.put(string.get(0), string.get(1));
			//graph.addVertex(value);
			System.out.println((line.split("~")));
		}
		
		//in.close();
		
		/*// declaring variables needed to read files
		int i;
		boolean isSymbol = false;
		String key = "";
		String value = "";
		
		int count = 0;
		// reading through the file
		while((i = actorsReader.read()) != -1)
		{
			char character = (char) i;
			
			// if you met ~ that means we go from key to value, change isSymbol to true
			if (character == '~')
			{
				isSymbol = true;
			}
			
			if (isSymbol == false)
			{
				// if you have not met ~ yet, then everything you write belongs to the key
				key = key + character;
			}
			else if (isSymbol == true && character != '\n')
			{
				value = value + character;
			}
			else if (character == '\n')
			{
				count++;
				codeActor.put(key, value);
				graph.addVertex(value);
				isSymbol = false;
				value = "";
				key = "";
			}
		}
		System.out.println(count);
		System.out.println(graph.vertices.size());

		
		// reading through the file
		while((i = moviesReader.read()) != -1)
		{
			char character = (char) i;
			
			// if you met ~ that means we go from key to value, change isSymbol to true
			if (character == '~')
			{
				isSymbol = true;
			}
			else if (isSymbol == false)
			{
				// if you have not met ~ yet, then everything you write belongs to the key
				key = key + character;
			}
			else if (isSymbol == true && character != '\n')
			{
				value = value + character;
			}
			else if (character == '\n')
			{
				codeMovie.put(key, value);
				isSymbol = false;
				value = "";
				key = "";
			}
		}
		
		//if they share a movie = connect them 	
		String prevKey = "";
		
		// list of all the codes of actors in one movie
		ArrayList<String> codes = new ArrayList<String>();
		
		// reading through the file
		int counter = 0;
		while((i = moviesActors.read()) != -1)
		{
			char character = (char) i;
			// if you met ~ that means we go from key to value, change isSymbol to true
			if (character == '~')
			{
				isSymbol = true;
			}
			
			// i do not add the first element
			if (isSymbol == false)
			{
				// if you have not met ~ yet, then everything you write belongs to the key
				key = key + character;
			}
			else if (isSymbol == true && character != '\n')
			{
				value = value + character;
			}
			
			
			if(prevKey == key && character == '\n' || prevKey == "")
			{
				// this does not add anything
				codes.add(value);
			}
			else if (prevKey != key && character == '\n')
			{
				//System.out.println(counter);
				System.out.println(codes.size());
				// it does not run this loop
				for (int j = 0; j < codes.size(); j++)
				{
					for (int k = 1; k < codes.size(); k++)
					{
						graph.connect(codeMovie.get(key), codes.get(j), codes.get(k));
						counter++;
					}
				}
			}
			
			isSymbol = false;
			prevKey = key;
			value = "";
			key = "";
		}
		System.out.println(counter);*/
	}

	public static void main(String[] args) throws IOException
	{
		new Maps();
	}
}
