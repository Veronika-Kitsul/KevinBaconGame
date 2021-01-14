package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.soap.Node;


// potential edge cases that need to be fixed: 
// if user tries to do add the same info twice
// if there is no vertex for which you search

public class Graph<E> 
{
	HashMap<E, Vertex> vertices;
	
	public Graph()
	{
		vertices = new HashMap<E, Vertex>();
	}
	
// potential prevention from the edge case	of adding the same twice
	public void addVertex(E info)
	{
		if (vertices.get(info) != null) 
		{
			// do something to prevent adding
			// I am not sure if it's okay to just skip it or return or add a print statement
		}
		else 
		{
			vertices.put(info, new Vertex(info));
		}
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
	
	private ArrayList<E> search(E place, E destiny)
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
				
				leadsTo.put(neighbor, curr);
				
				if (neighbor.info.equals(destiny))
				{
					return backTrace(neighbor, leadsTo);
				}
				else
				{
					toVisit.add(neighbor);
					visited.add(neighbor);
				}
			}
		}
		return null;
	}
	
	private ArrayList<E> backTrace(Vertex destiny, HashMap<Vertex, Vertex> leadsTo)
	{
		Vertex curr = destiny;
		ArrayList<E> path = new ArrayList<E>();
		
		while (curr != null) {
			path.add(0, curr.info);
			curr = leadsTo.get(curr);
		}
		System.out.println(path);
		return path;
	}

	public static void main(String[] args)
	{
		Graph<String> g = new Graph<String>();
		g.addVertex("Reina");
		g.addVertex("Felicity");
		g.addVertex("Andria");
		g.addVertex("Elgin");
		g.addVertex("Veronika");
		g.addVertex("Tommy");
		g.addVertex("Carl");
		g.addVertex("Carl");
		
		g.connect("Reina", "Felicity");
		g.connect("Andria", "Felicity");
		g.connect("Elgin", "Felicity");
		g.connect("Veronika", "Reina");
		g.connect("Elgin", "Andria");
		g.connect("Tommy", "Carl");
		g.connect("Carl", "Veronika");
		g.connect("Andria", "Tommy");
		g.connect("Carl", "Elgin");
		g.connect("Andria", "Carl");
		
		g.search("Kat", "Carl");
	}
}
