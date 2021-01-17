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
	//ArrayList<Graph<E>> graph = new ArrayList<Graph<E>>();
	
	public Maps() throws IOException 
	{
		// creating file reader and file variable for both files
		File actors = new File("actors.txt");
		File movies = new File("movies.txt");
		FileReader actorsReader = new FileReader(actors);
		FileReader moviesReader = new FileReader(movies);
		
		// putting actors to the map of actors, and movies to the map of movies
		reading(actorsReader, codeActor);
		reading(moviesReader, codeMovie);
		System.out.println(codeActor);
		System.out.println(codeMovie);
	}
	
	// method for reading text file databases
	public void reading(FileReader input,  HashMap map) throws IOException
	{
		int i;
		boolean isSymbol = false;
		String key = "";
		String value = "";
		
		// reading through the file
		while((i = input.read()) != -1)
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
				map.put(key, value);
				isSymbol = false;
				value = "";
				key = "";
			}
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		new Maps();
	}
}
