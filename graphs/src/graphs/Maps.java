package graphs;

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
	HashMap<String, String> codeActor = new HashMap<String, String>();
	
	// a map of code = movie
	HashMap<String, String> codeMovie = new HashMap<String, String>();
	
	// a graph with actors as vertices
	// it gets mad at me because of the <E>, I probably did not employ Graph class correctly
	
	
	public Maps() throws IOException 
	{
		// creating file readers and file variable for all files
		File actors = new File("actors.txt");
		File movies = new File("movies.txt");
		File movieActors = new File("movie-actors.txt");
		FileReader actorsReader = new FileReader(actors);
		FileReader moviesReader = new FileReader(movies);
		FileReader moviesActors = new FileReader(movieActors);
		
		//creating a graph
		EdgeGraph<String, String> graph = new EdgeGraph<String, String>();
		
		// declaring variables needed to read files
		int i;
		boolean isSymbol = false;
		String key = "";
		String value = "";
		
		// reading through the file
		while((i = actorsReader.read()) != -1)
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
				codeActor.put(key, value);
				graph.addVertex(value);
				isSymbol = false;
				value = "";
				key = "";
			}
		}

		
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
		
		
		/*
		String prevKey = "";
		while((i = moviesActors.read()) != -1)
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
				graph.connect(info1, info2, codeMovie.get(key));
				isSymbol = false;
				prevKey = key;
				value = "";
				key = "";
			}
		}*/
		
	}

	public static void main(String[] args) throws IOException
	{
		new Maps();
	}
}
