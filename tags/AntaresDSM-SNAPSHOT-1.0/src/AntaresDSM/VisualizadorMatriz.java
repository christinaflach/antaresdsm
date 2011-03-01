/*
 * MatrizDependenciasPre.java
 *
 * Created on 21 de Abril de 2008, 13:40
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

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author  Tom
 */
public class VisualizadorMatriz extends javax.swing.JInternalFrame {
    private JTable tableDSM ;
    private JScrollPane scrollPanel;
    private DSM dsm;
    private TableCellRenderer renderer;
    
    /** Creates new form MatrizDependenciasPre */
    public VisualizadorMatriz() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" C�digo Gerado ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("DSM inicial");
        setName("matrizPre");
        setNormalBounds(null);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-500)/2, (screenSize.height-500)/2, 500, 500);
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisualizadorMatriz().setVisible(true);
            }
        });
    }
    
    // Declara��o de vari�veis - n�o modifique//GEN-BEGIN:variables
    // Fim da declara��o de vari�veis//GEN-END:variables

    public DSM getDsm() {
        return dsm;
    }

    public void setDsm(DSM dsm) {
        this.dsm = dsm;
    }
    
    public void criaMatriz(){
        int tamX, tamY;
        

        tableDSM = new JTable(dsm.getNdeVertices(), dsm.getNdeVertices());
        tableDSM.setTableHeader(null);

        renderer = new ColorRenderer();
        
        tableDSM.setEnabled(false);
        
        //Preenche cabe�alhos
        for (int i = 0; i < dsm.getListaVertices().size(); i++) {
            tableDSM.setValueAt(Integer.toString(i + 1).concat(" ".concat(dsm.getListaVertices().get(i))), i+1 , 0);
            tableDSM.setValueAt(Integer.toString(i + 1), 0 , i+1);
        }
        
       
        //Preenche dependencias
        for (int i = 1; i < dsm.getNdeVertices(); i++) {
            for (int j = 1; j < dsm.getNdeVertices(); j++) {
                if(dsm.getMatrizDependencias()[i][j] > 0  && i!=j){
                    tableDSM.setValueAt(Integer.toString(dsm.getMatrizDependencias()[i][j]), i , j);
                }
            }
        }
        
        tableDSM.setRowHeight(20);
        
        //Calcula o tamanho que vai ter a matriz
        tamX = dsm.getNdeVertices()*20 + 200;
        tamY = dsm.getNdeVertices()*20 + 5;//dsm.getNdeVertices();
        
        tableDSM.setBounds(20,20,tamX,tamY);
        tableDSM.getColumnModel().getColumn(0).setPreferredWidth(200);
        
        for (int i = 1; i < dsm.getNdeVertices(); i++) {
            tableDSM.getColumnModel().getColumn(i).setPreferredWidth(20);
        }
        
        tableDSM.setDefaultRenderer(Object.class, renderer);
        tableDSM.setAutoResizeMode(tableDSM.AUTO_RESIZE_OFF);
        
        scrollPanel = new JScrollPane(tableDSM);
        
        scrollPanel.setVisible(true);  
        scrollPanel.getViewport().setBackground(Color.WHITE);
        
        scrollPanel.setBorder(null);
        this.setContentPane(scrollPanel);
        
        //Atributos da janela
        setTitle("DSM inicial");
        setBounds(600, 0, 600, 500);
    }
    
    public void criaMatrizCalculada(){
        int tamX, tamY;
        
        //teste
        //dsm.setmatrizDependenciasCalculada(dsm.getMatrizDependencias());
        
        dsm.QuickTriangularMatrix();

        tableDSM = new JTable(dsm.getNdeVertices(), dsm.getNdeVertices());
        tableDSM.setTableHeader(null);

        renderer = new ColorRenderer();
        
        tableDSM.setEnabled(true);
        
        //Preenche cabe�alhos
        for (int i = 0; i < dsm.getListaVertices().size(); i++) {
            tableDSM.setValueAt(Integer.toString(dsm.getmatrizDependenciasCalculada()[i+1][0]).
                    concat(" ".concat(dsm.getListaVertices().get(dsm.getmatrizDependenciasCalculada()[i+1][0] - 1))), i+1 , 0);
            tableDSM.setValueAt(Integer.toString(dsm.getmatrizDependenciasCalculada()[i+1][0]), 0 , i+1);
        }
        
       
        //Preenche dependencias
        for (int i = 1; i < dsm.getNdeVertices(); i++) {
            for (int j = 1; j < dsm.getNdeVertices(); j++) {
                if(dsm.getmatrizDependenciasCalculada()[i][j] > 0  && i!=j){
                    tableDSM.setValueAt(Integer.toString(dsm.getmatrizDependenciasCalculada()[i][j]), i , j);
                }
            }
        }
        
        tableDSM.setRowHeight(20);
        
        //Calcula o tamanho que vai ter a matriz
        tamX = dsm.getNdeVertices()*20 + 200;
        tamY = dsm.getNdeVertices()*20 + 5;//dsm.getNdeVertices();
        
        tableDSM.setBounds(20,20,tamX,tamY);
        tableDSM.getColumnModel().getColumn(0).setPreferredWidth(200);
        
        for (int i = 1; i < dsm.getNdeVertices(); i++) {
            tableDSM.getColumnModel().getColumn(i).setPreferredWidth(20);
        }
        
        tableDSM.setDefaultRenderer(Object.class, renderer);
        tableDSM.setAutoResizeMode(tableDSM.AUTO_RESIZE_OFF);
        
        scrollPanel = new JScrollPane(tableDSM);
        
        scrollPanel.setVisible(true);  
        scrollPanel.getViewport().setBackground(Color.WHITE);
        
        scrollPanel.setBorder(null);
        this.setContentPane(scrollPanel);
        
        //Atributos da janela
        setTitle("DSM calculada");
        setBounds(600, 500, 600, 500);
        
    }

    public JTable getTableDSM() {
        return tableDSM;
    }

    public void setTableDSM(JTable tableDSM) {
        this.tableDSM = tableDSM;
    }

    
}
