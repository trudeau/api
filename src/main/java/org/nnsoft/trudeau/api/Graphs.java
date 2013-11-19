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

import static org.nnsoft.trudeau.utils.Assertions.checkNotNull;

/**
 * The Apache Commons Graph package is a toolkit for managing graphs and graph based data structures.
 */
public final class Graphs
{

    /**
     * Returns a synchronized (thread-safe) {@link Graph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the syncronyzed graph
     */
    public static <V, E> Graph<V, E> synchronize( final Graph<V, E> graph )
    {
        Graph<V, E> checkedGraph = checkNotNull( graph, "Impossible to synchronize null Graph." );
        return new SynchronizedGraph<V, E>( checkedGraph );
    }

    /**
     * Returns a synchronized (thread-safe) {@link DirectedGraph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the syncronyzed graph
     */
    public static <V, E> DirectedGraph<V, E> synchronize( final DirectedGraph<V, E> graph )
    {
        return new SynchronizedDirectedGraph<V, E>( graph );
    }

    /**
     * Returns a synchronized (thread-safe) {@link UndirectedGraph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the syncronyzed graph
     */
    public static <V, E> Graph<V, E> synchronize( final UndirectedGraph<V, E> graph )
    {
        UndirectedGraph<V, E> checkedGraph = checkNotNull( graph, "Impossible to synchronize null Graph." );
        return new SynchronizedUndirectedGraph<V, E>( checkedGraph );
    }

    /**
     * Returns a synchronized (thread-safe) {@link MutableGraph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the synchronized graph
     */
    public static <V, E> Graph<V, E> synchronize( final MutableGraph<V, E> graph )
    {
        MutableGraph<V, E> checkedGraph = checkNotNull( graph, "Impossible to synchronize null Graph." );
        return new SynchronizedMutableGraph<V, E>( checkedGraph );
    }

    /**
     * Hidden constructor, this class cannot be instantiated.
     */
    private Graphs()
    {
        // do nothing
    }

}
