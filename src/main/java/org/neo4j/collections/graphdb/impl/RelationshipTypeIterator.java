/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.collections.graphdb.impl;

import java.util.Iterator;

import org.neo4j.collections.graphdb.EnhancedRelationshipType;
import org.neo4j.collections.graphdb.GraphDatabaseService;

class RelationshipTypeIterator implements Iterator<EnhancedRelationshipType>{

	private final Iterator<org.neo4j.graphdb.RelationshipType> relTypes;
	private final GraphDatabaseService graphDb;
	
	RelationshipTypeIterator(Iterator<org.neo4j.graphdb.RelationshipType> relTypes, GraphDatabaseService graphDb){
		this.relTypes = relTypes;
		this.graphDb = graphDb;
	}
	
	@Override
	public boolean hasNext() {
		return relTypes.hasNext();
	}

	@Override
	public EnhancedRelationshipType next() {
		return new RelationshipTypeImpl(relTypes.next(), graphDb);
	}

	@Override
	public void remove() {
		relTypes.remove();
	}
	
}