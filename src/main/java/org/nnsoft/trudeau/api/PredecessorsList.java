package org.nnsoft.trudeau.api;

/*
 *   Copyright 2013 The Trudeau Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.nnsoft.trudeau.math.monoid.Monoid;

import com.google.common.graph.ValueGraph;

/**
 * The predecessor list is a list of vertex of a {@link org.apache.commons.graph.Graph}.
 * Each vertex' entry contains the index of its predecessor in a path through the graph.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class PredecessorsList<V, WE, W>
{

    private final ValueGraph<V, WE> graph;

    private final Monoid<W> weightOperations;

    private final Function<WE, W> weightedEdges;

    private final Map<V, V> predecessors = new HashMap<V, V>();

    public PredecessorsList( ValueGraph<V, WE> graph, Monoid<W> weightOperations, Function<WE, W> weightedEdges )
    {
        this.graph = graph;
        this.weightOperations = weightOperations;
        this.weightedEdges = weightedEdges;
    }

    /**
     * Add an edge in the predecessor list associated to the input vertex.
     *
     * @param tail the predecessor vertex
     * @param head the edge that succeeds to the input vertex
     */
    public void addPredecessor( V tail, V head )
    {
        predecessors.put( tail, head );
    }

    /**
     * Build a {@link WeightedPath} instance related to source-target path.
     *
     * @param source the path source vertex
     * @param target the path target vertex
     * @return the weighted path related to source to target
     */
    public WeightedPath<V, WE, W> buildPath( V source, V target )
    {
        InMemoryWeightedPath<V, WE, W> path = new InMemoryWeightedPath<V, WE, W>( source, target, weightOperations, weightedEdges );

        V vertex = target;
        while ( !source.equals( vertex ) )
        {
            V predecessor = predecessors.get( vertex );
            if ( predecessor == null )
            {
                throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
            }
            WE edge = graph.edgeValue( predecessor, vertex ).get();

            path.addConnectionInHead( predecessor, edge, vertex );

            vertex = predecessor;
        }

        return path;
    }

    /**
     * Build a {@link WeightedPath} instance related to source-target path.
     *
     * @param source the path source vertex
     * @param touch the node where search frontiers meet, producing the shortest path
     * @param target the path target vertex
     * @param backwardsList the predecessor list in backwards search space along reversed edges
     * @return the weighted path related to source to target
     */
    public WeightedPath<V, WE, W> buildPath( V source, V touch, V target, PredecessorsList<V, WE, W> backwardsList ) {
        InMemoryWeightedPath<V, WE, W> path = new InMemoryWeightedPath<V, WE, W>( source, target, weightOperations, weightedEdges );

        V vertex = touch;
        while ( !source.equals( vertex ) )
        {
            V predecessor = predecessors.get( vertex );
            if ( predecessor == null )
            {
                throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
            }
            WE edge = graph.edgeValue( predecessor, vertex ).get();

            path.addConnectionInHead(predecessor, edge, vertex);

            vertex = predecessor;
        }

        vertex = touch;

        while ( !target.equals( vertex ) )
        {
            // 'predecessor' is actually a successor.
            V predecessor = backwardsList.predecessors.get( vertex );
            if ( predecessor == null )
            {
                throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
            }
            WE edge = graph.edgeValue( vertex, predecessor ).get();

            path.addConnectionInTail( vertex, edge, predecessor );

            vertex = predecessor;
        }

        return path;
    }

    /**
     * Checks the predecessor list has no elements.
     *
     * @return true, if the predecessor list has no elements, false otherwise.
     */
    public boolean isEmpty()
    {
        return predecessors.isEmpty();
    }

}
