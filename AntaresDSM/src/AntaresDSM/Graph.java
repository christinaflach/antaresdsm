/*
 * Graph.java
 *
 * Created on 21 de Abril de 2008, 01:20
 *
 * Copyright (C) 2008 Source Forge, Inc.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA.
 *
 * Comments only in english, portuguese, spanish or french
 * 
 * Authors: Antonio Neto <tomsalvador at users.sourceforge.net>
 */

package AntaresDSM;

/**
 *
 * @author Tom
 */
public class Graph {
    
    DSM dsm;
    
    public Graph(String graphName, DSM dsm){
        this.dsm = dsm;
        GenerateXML.initializeXML();
        createNodes();
        createEdges();
        createFile(graphName);
    }

    /**
     * Finalizes and creates the XML
     */
    private void createFile(String graphName) {
           GenerateXML.closeFile();
           GenerateXML.saveXML(graphName);
    }

    private void createNodes(){
        
        for (int i = 0; i < dsm.getVertexList().size() ; i++) {
            GenerateXML.generateNodes(i+1, dsm.getVertexList().get(i));
        }
           
    }

    private void createEdges(){
          
        for (int i = 1; i < dsm.getVertexNumber(); i++) {
            for (int j = 1; j < dsm.getVertexNumber(); j++) {
                if(dsm.getDependencyMatrix()[i][j]>0){
                    GenerateXML.generateEdges(i, j);
                }
            }
        }
        
    }

}
