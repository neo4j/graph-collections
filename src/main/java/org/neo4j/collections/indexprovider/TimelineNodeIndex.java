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
package org.neo4j.collections.indexprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.collections.timeline.Timeline;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;

public class TimelineNodeIndex implements Index<Node>
{


    private static final String TIMESTAMP = "timestamp";
    private Timeline timeline;
    private final String indexName;

    public TimelineNodeIndex( String indexName, GraphDatabaseService db,
            Map<String, String> config )
    {
        this.indexName = indexName;
        Transaction tx = db.beginTx();
        Node node = db.createNode();
        timeline = new Timeline( indexName, node, true, db );
        tx.success();
        tx.finish();
    }

    @Override
    public boolean isWriteable()
    {
        return true;
    }

    @Override
    public String getName()
    {
        // TODO Auto-generated method stub
        return indexName;
    }

    @Override
    public Class<Node> getEntityType()
    {
        return Node.class;
    }

    @Override
    public IndexHits<Node> get( String key, Object value )
    {
        return new NodeIndexHits( toList(timeline.getNodes( (Long) value )) );
    }

    private List<Node> toList( Iterable<Node> nodes )
    {
        List<Node> results = new ArrayList<Node>();
        for(Node node : nodes) {
            results.add( node );
        }
        return results;
    }

    @Override
    public IndexHits<Node> query( String key, Object queryOrQueryObject )
    {
        return null;
    }

    @Override
    public IndexHits<Node> query( Object queryString )
    {
        String query = (String)queryString;
        Long from = Long.parseLong( query.substring( 1, query.indexOf( "TO" ) ).trim());
        Long to = Long.parseLong( query.substring( query.indexOf( "TO" )+2,query.indexOf( "]" ) ).trim());
        return new NodeIndexHits( toList(timeline.getAllNodesBetween( from, to )));
    }

    @Override
    public void add( Node entity, String key, Object value )
    {
        if(key.equals( TIMESTAMP ))
        {
        timeline.addNode( entity, (Long) value );
        } else
        {
            throw new RuntimeException( "need a timestamp key and a LONG value" );
        }
    }

    @Override
    public void remove( Node entity, String key, Object value )
    {
        timeline.removeNode( entity );
        
    }

    @Override
    public void remove( Node entity, String key )
    {
        this.remove( entity );
        
    }

    @Override
    public void remove( Node entity )
    {
        timeline.removeNode( entity );
        
    }

    @Override
    public void delete()
    {
        timeline.delete();
        
    }
}