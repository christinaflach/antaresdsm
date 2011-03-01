/*
 * Arquivo.java
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
/**
 *
 * @author Tom
 */
public class Arquivo {

    private ArrayList<String> arquivoLido = new ArrayList<String>();
    /** Creates a new instance of Arquivo */
    public Arquivo() {
    }
    
    public void leArquivo(String nomeArquivo) {
        String linha="";
        StringBuffer sb = new StringBuffer();

        try{
                FileReader reader = new FileReader(nomeArquivo);
                BufferedReader leitor = new BufferedReader(reader);

            while (linha != null ){
                        linha = leitor.readLine();
                        if (linha != null){
                                sb = new StringBuffer(linha);
                                arquivoLido.add(sb.toString());
                        }
            }

            leitor.close();
            reader.close();
        }
        catch (IOException e) {
            System.out.print("Erro ao ler arquivo " + e.getMessage());
        }
    }
    
    public void escreveArquivo(StringBuffer saida){
        try{
            FileWriter writer = new FileWriter("saida.txt");
            BufferedWriter bWriter = new BufferedWriter(writer);

            writer.write(saida.toString());

            bWriter.close();
            writer.close();
        }
        catch (IOException e) {
            System.out.print("Erro ao escrever no arquivo " + e.getMessage());
        }
    }
    
    public void escreveArquivo(ArrayList<String> saida, String nomeArq){

        try{
            FileWriter writer = new FileWriter(nomeArq);
            StringBuilder buffer = new StringBuilder();

            for (int i = 0; i < saida.size(); i++) {
                buffer.append(saida.get(i).concat("\r\n"));
            }

            writer.write(buffer.toString());

            writer.close();
        }
        catch (IOException e) {
            System.out.print("Erro ao escrever no arquivo " + e.getMessage());
        }
    }

    public ArrayList<String> getArquivoLido() {
        return arquivoLido;
    }

    public void setArquivoLido(ArrayList<String> arquivoLido) {
        this.arquivoLido = arquivoLido;
    }
    
    
    
}
