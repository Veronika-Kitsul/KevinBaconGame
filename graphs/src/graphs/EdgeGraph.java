package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


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
		return path;
	}
	
	public ArrayList<Object> farthest(E info) 
	{
		if (vertices.get(info) == null)
		{
			System.out.println("There is no destination with this name. ");
			return null;
		}

		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(info));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(info));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		Vertex curr = toVisit.get(0);
		while (!toVisit.isEmpty())
		{			
			curr = toVisit.remove(0);

			for (Edge e: curr.edges)
			{
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;
				
				leadsTo.put(neighbor, e);
				
				toVisit.add(neighbor);
				visited.add(neighbor);
			}
		}
		return backTrace(curr, leadsTo);
	}
	
// working on this now ------------------------	
	public ArrayList<ArrayList<Object>> threeWays(E start, E end) 
	{

		if (vertices.get(end) == null)
		{
			System.out.println("There is no destination with this name. ");
			return null;
		}
		else if (vertices.get(start) == null)
		{
			System.out.println("There is no starting point with this name. ");
			return null;
		}
		
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		ArrayList<ArrayList <Object>> ways = new ArrayList<ArrayList<Object>>();
		
		while (!toVisit.isEmpty())
		{
			Vertex curr = toVisit.remove(0);
			
			for (Edge e: curr.edges)
			{
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;
				
				leadsTo.put(neighbor, e);
				
				if (neighbor.info.equals(end))
				{
					 ways.add(backTrace(neighbor, leadsTo));
					 if (ways.size() == 3)
					 {
						 return ways;
					 }
				}
				else
				{
					toVisit.add(neighbor);
					visited.add(neighbor);
				}
			}
		}
		return ways;
	}

	public static void main(String[] args)
	{
		EdgeGraph<String, String> g = new EdgeGraph<String, String>();
		g.addVertex("R");
		g.addVertex("A");
		g.addVertex("K");
		g.addVertex("H");
		g.addVertex("B");
		g.addVertex("C");
		
		g.connect("K", "A", "food");
		g.connect("R", "A", "sports");
		g.connect("R", "H", "shops");
		g.connect("H", "C", "chocolate");
		g.connect("C", "B", "tennis");
		g.threeWays("R", "C");
	}
}
