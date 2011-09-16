/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.collections;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import java.util.Iterator;

/**
 * A NodeCollection provides an interface to store a collection of nodes using some form of in graph data structure.
 */
public interface NodeCollection extends Iterable<Node>
{
    static final String NODE_COLLECTION_CLASS = "node_collection_class";

    static enum RelationshipTypes implements RelationshipType
    {
        VALUE
    }

    /**
     * Get the base node for the node collection, e.g. for a tree this would be the root, for a list it
     * would be the head.
     * 
     * @return the node used to base the node collection off
     */
    Node getBaseNode();

    /**
     * Add a node to the to a collection.
     *
     * @param node the node to add the the collection.
     *
     * @return the value relationship, this is the relationship from an internal node in the collection to the added node.
     */
    Relationship addNode( Node node );

    /**
     * Remove the given node form the collection.
     *
     * @param node the node to remove from the collection.
     *
     * @return <code>true</code> if the node was in the collection.
     */
    boolean removeNode( Node node );

    /**
     * Returns an iterator over the nodes within the collection.
     *
     * @return a node iterator.
     */
    Iterator<Node> iterator();

    /**
     * Returns an iterable over the value relationships. Value relationships are the relationships from the internal
     * data structure to the nodes added to the collection.
     *
     * @return a value relationship iterable.
     */
    Iterable<Relationship> getValueRelationships();
}