/*
 * FileManipulator.java
 *
 * Created on 25 de Agosto de 2007, 17:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Tom
 */
public class FileManipulator {

    private List<String> alreadyReadFile = new ArrayList<String>();
    
    /** Creates a new instance of FileManipulator */
    public FileManipulator() {
    }
    
    public void readFile(String fileName) {
        String line="";
        StringBuffer sb = new StringBuffer();

        try{
                FileReader reader = new FileReader(fileName);
                BufferedReader bReader = new BufferedReader(reader);

            while (line != null ){
                        line = bReader.readLine();
                        if (line != null){
                                sb = new StringBuffer(line);
                                this.alreadyReadFile.add(sb.toString());
                        }
            }

            bReader.close();
            reader.close();
        }
        catch (IOException e) {
            System.out.print("Error reading file " + e.getMessage());
        }
    }
    
    public void writeFile(StringBuffer output){
        try{
            FileWriter writer = new FileWriter("output.txt");
            BufferedWriter bWriter = new BufferedWriter(writer);

            writer.write(output.toString());

            bWriter.close();
            writer.close();
        }
        catch (IOException e) {
            System.out.print("Error writing file " + e.getMessage());
        }
    }
    
    public void writeFile(List<String> output, String fileName){

        try{
            FileWriter writer = new FileWriter(fileName);
            StringBuilder builder = new StringBuilder();

            for (String outputString :output) {
                builder.append(outputString.concat("\r\n"));
            }

            writer.write(builder.toString());
            writer.close();
        }
        catch (IOException e) {
            System.out.print("Error writing file " + e.getMessage());
        }
    }

    public List<String> getAlreadyReadFile() {
        return this.alreadyReadFile;
    }

    public void setAlreadyReadFile(List<String> alreadyReadFile) {
        this.alreadyReadFile = alreadyReadFile;
    }
      
}
