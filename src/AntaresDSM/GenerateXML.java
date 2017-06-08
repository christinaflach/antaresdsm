/*
 * GenerateXML.java
 *
 * Created on 21 de Abril de 2008, 00:48
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


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

 


public class GenerateXML {

        

        //Here it will be defined the header of the xml file
        public static StringBuilder file;
        

        /**
         * Method used in order to generate nodes,
         * will be passed an id for the edges correlation.
         * Through the id, it will be created an edge bewtween two nodes.
         * @param id
         * @param name
         * @return
         */
        public static void generateNodes(long userId, String name){
               file.append("\n\t\t<node id=\"").append(userId).append("\">\n\t\t\t").append(
               "<data key=\"name\">").append(name).append("</data>\n\t\t</node>");
        }

        
        /**
         * Builds the edges that correspond to the id of the source user (idSource)
         * to the id of the target user (idTarget).
         * @param idSource
         * @param idTarget
         * @return
         */
        public static void generateEdges(int idSource, int idTarget){
               file.append("\n\t\t<edge source=\"").append(idSource).append("\" target=\"").append(idTarget).append("\"></edge>\n\t\t\t");
        }

        /**
         * Closes the file
         *
         */
        public static void closeFile(){
               file.append("\n\n\t</graph>\n</graphml>");
        }
        
        /**
         * Saves the file
         * @param path
         */
        public static void saveXML(String path)
        {
               try {
                       BufferedWriter out = new BufferedWriter(new FileWriter(path));
                       out.write(file.toString());
                       out.flush();
                       out.close();
               } catch (IOException e) {
                       System.err.println("Error saving the file... Exiting");
                       System.exit(0);
               }
        }
        
        public static void initializeXML(){
            file = new StringBuilder(
                       "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!--  Graph of classes  -->\n<graphml xmlns=\"").
                append("http://graphml.graphdrawing.org/xmlns\">\n\t<graph edgedefault=\"directed\">\n\n\t\t<!-- data schema -->\n\t\t<key id=\"name\" for=\"node\"").
                append(" attr.name=\"name\" attr.type=\"string\"/>\n\n\t\t<!-- nodes -->  ");
        }
}
