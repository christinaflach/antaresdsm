/*
 * GraphVisualizator.java
 *
 * Created on 21 de Abril de 2008, 01:08
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

import javax.swing.JInternalFrame;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.controls.*;
import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.demos.RadialGraphView;

/**
 *
 * @author Tom
 */
public class GraphVisualizator {
    
    private JInternalFrame frame;
    
    /** Creates a new instance of GraphVisualizator */
    public GraphVisualizator(String name) {
        
        // Creates the visualization
        final Visualization vis = new Visualization();
        Graph graph = openXML(name);
        vis.add("graph", graph);
        // Indicates what is the attribute of the XML file that will be writeen in the node
        LabelRenderer r = new LabelRenderer("name");
        // Round the borders. The closest from 0, the more square the node will be.
        r.setRoundedCorner(8, 8);
        vis.setRendererFactory(new DefaultRendererFactory(r));



        Display display = new Display(vis);
        display.setSize(600, 600);
        display.addControlListener(new DragControl()); // drag 
        display.addControlListener(new PanControl()); // pan 
        display.addControlListener(new ZoomControl()); // zoom righ click


        
        frame = new JInternalFrame(name);
        frame.setClosable(true);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setIconifiable(true);
        frame.setMaximizable(true);
        frame.setResizable(true);
        frame.setContentPane(RadialGraphView.demo(name, "name"));
        frame.setTitle("Dependency Graph");
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(0,0,600,600);
        
    }

    
    /**
    * Metodo responsavel por "abrir" o xml
    */
    private Graph openXML(String name) {
           Graph graph = null;
           try {
                   graph = new GraphMLReader()
                                  .readGraph(name);
           } catch (DataIOException e) {
                   System.err.println("Error reading the graph.");
           }
           return graph;
    }

    public JInternalFrame getFrame() {
        return frame;
    }

    public void setFrame(JInternalFrame frame) {
        this.frame = frame;
    }

}
