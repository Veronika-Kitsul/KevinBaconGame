package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.soap.Node;


public class EdgeGraph<E, T> 
{
	HashMap<E, Vertex> vertices;
	
	public EdgeGraph()
	{
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info)
	{
		vertices.put(info, new Vertex(info));
	}
	
	public void connect(E info1, E info2, T label)
	{
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		Edge e = new Edge(label, v1, v2);
		
		v1.edges.add(e);
		v2.edges.add(e);
	}
	
	public class Edge
	{
		T label;
		Vertex v1, v2;
		
		public Edge(T label, Vertex v1, Vertex v2)
		{
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
		}
		
		public Vertex getNeighbor(Vertex v)
		{
			if (v.info.equals(v1.info))
			{
				return v2;
			}
			return v1;
		}
	}
	
	private class Vertex 
	{
		E info;
		HashSet<Edge> edges;
		
		public Vertex(E info)
		{
			this.info = info;
			edges = new HashSet<Edge>();
		}
		
		public boolean equals(Vertex other)
		{
			return info.equals(other.info);
		}
	}
	
	public ArrayList<Object> search(E place, E destiny)
	{
		if (vertices.get(destiny) == null)
		{
			System.out.println("There is no destination with this name. ");
			return null;
		}
		else if (vertices.get(place) == null)
		{
			System.out.println("There is no starting point with this name. ");
			return null;
		}
		
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(place));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(place));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		
		while (!toVisit.isEmpty())
		{
			Vertex curr = toVisit.remove(0);
			
			for (Edge e: curr.edges)
			{
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;
				
				leadsTo.put(neighbor, e);
				
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
	
	private ArrayList<Object> backTrace(Vertex destiny, HashMap<Vertex, Edge> leadsTo)
	{
		Vertex curr = destiny;
		ArrayList<Object> path = new ArrayList<Object>();
		
		while (leadsTo.get(curr) != null) 
		{
			path.add(0, curr.info);
			path.add(0, leadsTo.get(curr).label);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		path.add(0, curr.info);
		System.out.println(path);
		return path;
	}

	public static void main(String[] args)
	{
		EdgeGraph<String, String> g = new EdgeGraph<String, String>();
		g.addVertex("R");
		g.addVertex("A");
		g.addVertex("K");
		
		
		g.connect("K", "A", "food");
		g.connect("R", "A", "sports");
		g.search("K", "R");
	}
}
