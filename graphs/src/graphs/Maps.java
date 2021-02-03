package graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Maps 
{
	//--------------need to use try catch but I do not know how to frame them------------------------------------------
	
	// a map of code = actor
	HashMap<String, String> codeActor = new HashMap<String, String>();
	
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
					for (int n = 1; n < codes.size(); n++)
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
		System.out.println(connections);
}

	public static void main(String[] args) throws IOException
	{
		new Maps();
	}
}
