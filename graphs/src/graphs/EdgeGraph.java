package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// run Kevin Bacon Game, this is just a constructor class

public class EdgeGraph<E, T> 
{
	HashMap<E, Vertex> vertices;
	
	// add vertices to a hash map that matches their input to the actual vertex
	public EdgeGraph()
	{
		vertices = new HashMap<E, Vertex>();
	}
	
	// constructor for adding vertices
	public void addVertex(E info)
	{
		vertices.put(info, new Vertex(info));
	}
	
	// connecting vertices with edges
	public void connect(E info1, E info2, T label)
	{
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		Edge e = new Edge(label, v1, v2);
		
		v1.edges.add(e);
		v2.edges.add(e);
	}
	
	// constructor of labeled edges
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
	
	// constructor of vertices
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
	
	// BFS algorithm
	public ArrayList<Object> search(E place, E destiny)
	{
		// handle null pointers and give details as to what went wrong
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
		
		// create a list of places to visit
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(place));
		
		// create a list of visited places so we don't waste time visiting them twice
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(place));
		
		// create a map to remember which vertex leads to which vertex
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		// while we have nothing to visit == we searched the whole graph
		while (!toVisit.isEmpty())
		{
			// we don't need to visit current vertex anymore because we are here
			Vertex curr = toVisit.remove(0);
			
			// for all of the edges to the current vertex
			for (Edge e: curr.edges)
			{
				Vertex neighbor = e.getNeighbor(curr);
				
				// if you have already visited then just continue
				if (visited.contains(neighbor)) continue;
				
				// put the path to the map
				leadsTo.put(neighbor, e);
				
				// if we found what we are looking for then call backTrace to trace all the path back to the start
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
	
	// back trace method
	private ArrayList<Object> backTrace(Vertex destiny, HashMap<Vertex, Edge> leadsTo)
	{
		// start at the final position
		Vertex curr = destiny;
		// array list to hold the path
		ArrayList<Object> path = new ArrayList<Object>();
		
		// while there is a vertex to lead us to another vertex
		while (leadsTo.get(curr) != null) 
		{
			// add it to the path
			path.add(0, curr.info);
			path.add(0, leadsTo.get(curr).label);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		path.add(0, curr.info);
		return path;
	}
	
	// farthest method to find farthest connection
	public ArrayList<Object> farthest(E info) 
	{
		// handle null pointer
		if (vertices.get(info) == null)
		{
			System.out.println("There is no destination with this name. ");
			return null;
		}

		// similar procedure to the BFS -- refer to the search method above
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(info));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(info));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		Vertex curr = toVisit.get(0);
		
		// search while searched the whole graph
		while (!toVisit.isEmpty())
		{			
			curr = toVisit.remove(0);

			for (Edge e: curr.edges)
			{
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;
				
				leadsTo.put(neighbor, e);
				
				// here we don't have a condition to check if it's the destination yet because we don't know the destination
				// just search the whole graph and make connections
				
				toVisit.add(neighbor);
				visited.add(neighbor);
			}
		}
		return backTrace(curr, leadsTo);
	}
	
	// method to find three shortest ways to get to the destination
	public ArrayList<ArrayList<Object>> threeWays(E start, E end) 
	{
		// handle null pointers
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
		
		// similar code to search and farthest -- see details on it above
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
				
				// search for ways the way you searched in the search method
				if (neighbor.info.equals(end))
				{
					 ways.add(backTrace(neighbor, leadsTo));
					 
					 // if you reached the needed number of paths -- return them
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
	}
}
