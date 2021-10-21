package org.nnsoft.trudeau.api;

import static java.lang.String.format;

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

import static java.util.Objects.hash;

import java.util.Objects;
import java.util.function.Function;

import org.nnsoft.trudeau.math.monoid.Monoid;

/**
 * Support {@link WeightedPath} implementation, optimized for algorithms (such Dijkstra's) that need to rebuild the path
 * traversing the predecessor list bottom-up.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class InMemoryWeightedPath<V, WE, W>
    extends InMemoryPath<V, WE>
    implements WeightedPath<V, WE, W>
{

    private final Monoid<W> weightOperations;

    private final Function<WE, W> weightedEdges;

    private W weight;

    /**
     * Creates a new instance of {@link InMemoryWeightedPath}.
     *
     * @param start the start vertex
     * @param target the target vertex
     * @param weightOperations
     * @param weightedEdges
     */
    public InMemoryWeightedPath( V start, V target, Monoid<W> weightOperations, Function<WE, W> weightedEdges )
    {
        super( start, target );
        this.weightOperations = weightOperations;
        this.weightedEdges = weightedEdges;

        this.weight = weightOperations.identity();
    }

    /**
     * {@inheritDoc}
     */
    public void addConnectionInHead( V head, WE edge, V tail )
    {
        super.addConnectionInHead( head, edge, tail );
        increaseWeight( edge );
    }

    /**
     * {@inheritDoc}
     */
    public void addConnectionInTail( V head, WE edge, V tail )
    {
        super.addConnectionInTail( head, edge, tail );
        increaseWeight( edge );
    }

    /**
     * Increase the path weight with the weight of the input weighted edge.
     *
     * @param edge the edge whose weight is used to increase the path weight
     */
    private void increaseWeight( WE edge )
    {
        weight = weightOperations.append( weightedEdges.apply( edge ), weight );
    }

    /**
     * {@inheritDoc}
     */
    public W getWeight()
    {
        return weight;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode()
    {
        return hash( weight );
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( !super.equals( obj ) )
        {
            return false;
        }

        if ( getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" ) // test against any WeightedPath typed instance
        InMemoryWeightedPath<Object, Object, W> other = (InMemoryWeightedPath<Object, Object, W>) obj;
        return Objects.equals( weight, other.getWeight() );
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return format( "InMemoryPath [weigth=%s, vertices=%s, edges=%s]", weight, getVertices(), getEdges() );
    }

}
