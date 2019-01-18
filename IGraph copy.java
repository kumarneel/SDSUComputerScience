package edu.sdsu.cs.datastructures;
import java.io.IOException;
import java.util.List;
/**
 * Identifies the functions required for a graph object.
 *
 * @param <V> The type to use for unique vertex names (e.g. String)
 */
interface IGraph<V> {
    /**
     * Inserts a vertex with the specified name into the Graph if it
     * is not already present.
     *
     * @param vertexName The label to associate with the vertex
     */
    void add(V vertexName);
    /**
     * Adds a connection between the named vertices if one does not
     * yet exist.
     *
     * @param start The first vertex for the edge
     * @param destination The second vertex
     * @throws java.util.NoSuchElementException if either vertex are
     * not present in the graph
     */
    void connect(V start, V destination) throws IOException;
    /**
     * Resets the graph to an empty state.
     */
    void clear();
    /**
     * Reports if a vertex with the specified label is stored within
     * the graph.
     *
     * @param label The vertex name to find
     * @return true if within the graph, false if not.
     */
    boolean contains(V label);
    /**
     * Removes the specified edge, if it exists, from the Graph.
     *
     * @param start The name of the origin vertex
     * @param destination The name of the terminal vertex
     * @throws java.util.NoSuchElementException if either vertex are
     * not present in the graph
     */
    void disconnect(V start, V destination);
    /**
     * Identifies if a path exists between the two vertices.
     *
     * When the start and destination node names are the same, this
     * method shall only return true if there exists a self-edge on
     * the specified vertex.
     *
     * @param start The initial Vertex
     * @param destination The terminal vertex
     * @return True if any path exists between them
     * @throws java.util.NoSuchElementException if either vertex are
     * not present in the graph
     */
    boolean isConnected(V start, V destination);
    /**
     * Provides a collection of vertex names directly connected
     * through a single outgoing edge to the target vertex.
     *
     * Changes to the returned Iterable object (e.g., .remove())
     * shall NOT impact or change the graph.
     *
     * @param vertexName The target vertex
     * @return An iterable, possibly empty, containing all
     * neighboring vertices.
     * @throws java.util.NoSuchElementException if the vertex is not
     * present in the graph
     */

    Iterable<V> neighbors(V vertexName);

    /**
     * Deletes all trace of the specified vertex from within the
     * graph.
     ** This method deletes the vertex from the graph as well as every
     * edge using the specified vertex as a start (out) or
     * destination (in) vertex.
     *
     * @param vertexName The vertex name to remove from the graph
     * @throws java.util.NoSuchElementException if the origin vertex
     * is not present in this graph
     */
    void remove(V vertexName);

    /**
     * Returns one shortest path through the graph from the starting
     * vertex and ending in the destination vertex.
     *
     * @param start The vertex from which to begin the search
     * @param destination The terminal vertex within the graph
     * @return A sequence of vertices to visit requiring the fewest
     * steps through the graph from its starting position
     * (at index 0 in the list) to its terminus at the list's end.
     * If no path exists between the nodes, this method
     * returns an empty list.
     * @throws java.util.NoSuchElementException if either vertex are
    not present in the graph
     */

    List<V> shortestPath(V start, V destination);

    /**
     * Reports the number of vertices in the Graph.
     *
     * @return a non-negative number.
     */
    int size();
    /**
     * Provides a collection of vertex names currently in the graph.
     *
     * @return The names of the vertices within the graph.
     */

    Iterable<V> vertices();
    /**
     * Produces a graph of only those vertices and edges reachable
     * from the origin vertex.
     *
     * @param origin The vertex to build the graph from
     * @return A new graph with only the Vertices and Edges
     * reachable from the parameter Vertex.
     * @throws java.util.NoSuchElementException if the origin vertex
     * is not present in this graph
     */
    IGraph<V> connectedGraph(V origin);
}