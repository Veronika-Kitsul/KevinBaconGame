package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.soap.Node;

public class Graph<E> 
{
	HashMap<E, Vertex> vertices;
	
	public Graph()
	{
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info)
	{
		vertices.put(info, new Vertex(info));
	}
	
	public void connect(E info1, E info2)
	{
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		//check if the info is in the map already

		v1.neighbors.add(v2);
		v2.neighbors.add(v1);
	}
	
	
	private class Vertex 
	{
		E info;
		HashSet<Vertex> neighbors;
		
		public Vertex(E info)
		{
			this.info = info;
			neighbors = new HashSet<Vertex>();
		}
		
		public boolean equals(Vertex other)
		{
			return info.equals(other.info);
		}
	}
	
	private void search(E place, E destiny)
	{
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(place));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(place));
		
		HashMap<Vertex, Vertex> leadsTo = new HashMap<Vertex, Vertex>();
		
		
		while (!toVisit.isEmpty())
		{
			Vertex curr = toVisit.remove(0);
			
			for (Vertex neighbor : curr.neighbors)
			{
				if (visited.contains(neighbor)) continue;
				
				if (neighbor.info.equals(destiny))
				{
					System.out.println("found");
					
					backTrace(destiny, place, leadsTo);
					return;
				}
				else
				{
					toVisit.add(neighbor);
					visited.add(neighbor);
					leadsTo.put(neighbor, curr);
				}
			}
		}
	}
	
	private void backTrace(E place, E destiny, HashMap leadsTo)
	{
		Vertex curr = vertices.get(destiny);
		String path = "" + destiny;
		System.out.println(vertices.get(destiny));
		
		
		while (! curr.info.equals(destiny))
		{
			System.out.println(curr);
			path = curr + path;
			curr = (Graph<E>.Vertex) leadsTo.get(curr);
		}
		System.out.println(path);
	}

	public static void main(String[] args)
	{
		Graph<String> g = new Graph<String>();
		g.addVertex("Reina");
		g.addVertex("Felicity");
		g.addVertex("Andria");
		g.addVertex("Elgin");
		
		g.connect("Reina", "Felicity");
		g.connect("Andria", "Felicity");
		g.connect("Elgin", "Felicity");
		
		g.search("Reina", "Elgin");
	}
}
