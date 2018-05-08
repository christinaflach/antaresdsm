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


import javax.swing.JTable;  
import java.awt.Color;  
import java.awt.Component;  
import javax.swing.table.DefaultTableCellRenderer;  
   
public class ColorRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 2L;

        @Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ){
            
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);   
                 
            setBackground(Color.white);
            setForeground(Color.black);
            
            if(row==column){
                 setBackground(Color.black);
                 setForeground(Color.black);
            }

            if(row==0 || column ==0){
                setBackground(new Color(150,210,240));
            }
            
            if(table.getModel().getValueAt(row,column) != null && column > row && row > 0){
                if( table.getModel().getValueAt(row,column).toString().contains(" ") ){
                    setBackground(new Color(255,0,0));
                }
            }
            return this;   
	}  
} 
