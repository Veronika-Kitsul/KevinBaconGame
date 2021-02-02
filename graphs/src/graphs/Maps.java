package graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Maps 
{
	// need to use try catch but I do not know how to frame them
	
	// a map of code = actor
	HashMap codeActor = new HashMap();
	
	// a map of code = movie
	HashMap<String, String> codeMovie = new HashMap<String, String>();
	
	
	public Maps() throws IOException 
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
		System.out.println(k);
		System.out.println(graph.vertices.size());

		
		int f = 0;
		for (String line = moviesReader.readLine(); line != null; line = moviesReader.readLine())
		{
			String[] array = line.split("~");
			codeMovie.put(array[0], array[1]);
			f++;
		}
		System.out.println(f);
		 
		
		
		/*
		
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
