/*
 * VisualizadorGrafo.java
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
public class VisualizadorGrafo {
    
    private JInternalFrame frame;
    
    /** Creates a new instance of VisualizadorGrafo */
    public VisualizadorGrafo(String nome) {
        
        // Cria visualizacao
        final Visualization vis = new Visualization();
        Graph grafo = abrirXML(nome);
        vis.add("graph", grafo);
        // Essa linha indica qual atributo do XML irá ser escrito no nodo
        LabelRenderer r = new LabelRenderer("name");
        // Arredonda os cantos, quanto mais proximo de zero mais "quadrado" o
        // nodo ira ficar
        r.setRoundedCorner(8, 8);
        vis.setRendererFactory(new DefaultRendererFactory(r));



        Display display = new Display(vis);
        display.setSize(600, 600);
        display.addControlListener(new DragControl()); // drag 
        display.addControlListener(new PanControl()); // pan 
        display.addControlListener(new ZoomControl()); // zoom com o clique direito do mouse


        
        frame = new JInternalFrame(nome);
        frame.setClosable(true);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setIconifiable(true);
        frame.setMaximizable(true);
        frame.setResizable(true);
        frame.setContentPane(RadialGraphView.demo(nome, "name"));
        frame.setTitle("Grafo de dependências");
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(0,0,600,600);
        
    }

    
    /**
    * Metodo responsavel por "abrir" o xml
    */
    public Graph abrirXML(String nome) {
           Graph grafo = null;
           try {
                   grafo = new GraphMLReader()
                                  .readGraph(nome);
           } catch (DataIOException e) {
                   e.printStackTrace();
                   System.err.println("Erro ao ler grafo.");
           }
           return grafo;
    }

    public JInternalFrame getFrame() {
        return frame;
    }

    public void setFrame(JInternalFrame frame) {
        this.frame = frame;
    }

}
