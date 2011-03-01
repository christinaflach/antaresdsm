/*
 * DSM.java
 *
 * Created on 20 de Abril de 2008, 23:11
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Tom
 */
public class DSM {
    
    private int NdeVertices; //Número de vertices contando com o cabeçalho
    
    private ArrayList<String> listaVertices = new ArrayList<String>();
    
    
    private int matrizDependencias[][];
    private int matrizDependenciasCalculada[][];
    
    /** Creates a new instance of DSM */
    public DSM(ArrayList<String> descricaoArquivo) {
    int i;
    int indiceEspaco1, indiceEspaco2;
    int dependente, fornecedor, valorDependencia;
        
        
        //Carrega lista de elementos
        for (i = 0; ((i < descricaoArquivo.size()) && (descricaoArquivo.get(i).compareTo("dep") != 0) ); i++) {
            String elemento;
            int indexEspaco;

            elemento = descricaoArquivo.get(i);
            indexEspaco = elemento.indexOf(" ");
            if(i + 1 ==  Integer.parseInt(elemento.substring(0, indexEspaco)) ){
                listaVertices.add(elemento.substring(indexEspaco + 1));
            } else {
                JOptionPane.showMessageDialog(null,"O arquivo de entrada é inválido!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            }

        }

        // A primeira linha/coluna servirá de cabeçalho
        NdeVertices = i + 1;
        
        matrizDependencias = new int[NdeVertices][NdeVertices];
        
        for (int j = 0; j < NdeVertices; j++) {
            matrizDependencias[0][j] = j;
            matrizDependencias[j][0] = j;
        }
        
        
        for (int j = 1; j < NdeVertices; j++) {
            for (int k = 1; k < NdeVertices; k++) {
                matrizDependencias[j][k] = 0;
            }
        }
        
        //Carrega dependências
        for (i = i+1; i < descricaoArquivo.size(); i++) {
        String linhaRestante;    
            
            indiceEspaco1 = descricaoArquivo.get(i).indexOf(' ');
            
            linhaRestante = descricaoArquivo.get(i).substring(indiceEspaco1 + 1);
            
            indiceEspaco2 = linhaRestante.indexOf(' ');
            dependente = Integer.parseInt(descricaoArquivo.get(i).substring(0, indiceEspaco1));

            //O valor da dependência é opcional, caso não coloque, o padrão é 1
            if(indiceEspaco2 > 0){
                fornecedor = Integer.parseInt(linhaRestante.substring(0, indiceEspaco2));
                valorDependencia = Integer.parseInt(linhaRestante.substring(indiceEspaco2 + 1));
            }else{
                fornecedor = Integer.parseInt(linhaRestante);
                valorDependencia = 1;
            }
                
            
            
            matrizDependencias[dependente][fornecedor] = valorDependencia;
        }
    
        
        
    }
    
    /** Creates a new instance of DSM*/
    public DSM(int nDeVertices, int populacao, boolean dependenciasMultiplas) {
        Random rand = new Random();
        double popLimite = 0;
        
        rand.setSeed(System.currentTimeMillis());
        
        if(nDeVertices == 0){ //randomico
            this.NdeVertices = rand.nextInt(100) + 1;
        }else{
            this.NdeVertices = nDeVertices;
        }
        
        // A primeira linha/coluna servirá de cabeçalho
        this.NdeVertices = this.NdeVertices + 1;
        
        matrizDependencias = new int[this.NdeVertices][this.NdeVertices];
        
        for (int j = 0; j < NdeVertices; j++) {
            matrizDependencias[0][j] = j;
            matrizDependencias[j][0] = j;
            if(j > 0){
                listaVertices.add(String.valueOf(j));
            }
        }

        
        
        for (int j = 1; j < NdeVertices; j++) {
            for (int k = 1; k < NdeVertices; k++) {
                matrizDependencias[j][k] = 0;
            }
        }
        
        
        if (populacao  != 1) {
            if(populacao == 0){ //randomico
                popLimite = rand.nextDouble();
            }else{
                popLimite = (populacao - 1) * 0.10;
            }
           
        }
        
        
        for (int j = 1; j < NdeVertices; j++) {
            for (int k = 1; k < NdeVertices; k++) {
                if(rand.nextDouble() < popLimite){ //Marca dependência
                    if(dependenciasMultiplas){
                        matrizDependencias[j][k] = rand.nextInt(100) + 1;
                    }else{
                        matrizDependencias[j][k] = 1;
                    }
                }
            }
        }
        
        
    }
    

    public int getNdeVertices() {
        return NdeVertices;
    }

    public void setNdeVertices(int NdeVertices) {
        this.NdeVertices = NdeVertices;
    }

    public ArrayList<String> getListaVertices() {
        return listaVertices;
    }

    public void setListaVertices(ArrayList<String> listaVertices) {
        this.listaVertices = listaVertices;
    }

    public int[][] getMatrizDependencias() {
        return matrizDependencias;
    }

    public void setMatrizDependencias(int[][] matrizDependencias) {
        this.matrizDependencias = matrizDependencias;
    }
    
    public int[][] getmatrizDependenciasCalculada() {
        return matrizDependenciasCalculada;
    }

    public void setmatrizDependenciasCalculada(int[][] matrizDependenciasCalculada) {
        this.matrizDependenciasCalculada = matrizDependenciasCalculada;
    }
    
    public ArrayList<ElementoMatriz> OrdenaLista(ArrayList<ElementoMatriz> listaElementos){
        
        Collections.sort (listaElementos, new Comparator() {   
            public int compare(Object o1, Object o2) {   
                ElementoMatriz e1 = (ElementoMatriz)o1;   
                ElementoMatriz e2 = (ElementoMatriz)o2;   
                return e1.somaElemento < e2.somaElemento ? -1 : (e1.somaElemento > e2.somaElemento ? +1 : 0);   
            }   
        }); 
        
        
        return listaElementos;
    }
    
    public void atualizaMatriz(){
        //Popula dependências da matriz calculada
        for (int i = 1; i < NdeVertices; i++) {
            for (int j = 1; j < NdeVertices; j++) {
                matrizDependenciasCalculada[i][j] = 
                        matrizDependencias[ matrizDependenciasCalculada[i][0] ][ matrizDependenciasCalculada[0][j] ];
            }
        }
    }
    
    public void QuickTriangularMatrix(){
        ArrayList<ElementoMatriz> listaFornecedores = new ArrayList<ElementoMatriz>();
        ArrayList<ElementoMatriz> listaDependentes = new ArrayList<ElementoMatriz>();
        ElementoMatriz elemento;
        int soma, somaInferior, somaSuperior;
        
        //----------------------------------------------------------------------
        // Parte I do algoritmo:
        //
        // 1- Monta duas listas em ordem crescente, uma com a soma dos valores dos fornecedores (Colunas)
        //      e outra com a soma dos valores dos dependendentes (Linhas)
        // 2- Pega na ordem crescente, um item da lista de fornecedores e coloca no final
        //      e um item da lista de dependentes e coloca no início
        // 3- Monta matriz calculada com essa ordem
        //----------------------------------------------------------------------

        
        //Monta as listas para serem ordenadas
        //Lista de fornecedores
        for (int i = 1; i < NdeVertices; i++) {
            soma = 0;
            for (int j = 1; j < NdeVertices; j++) {
                soma = soma + matrizDependencias[j][i];
            }
            elemento = new ElementoMatriz(i,soma);
            listaFornecedores.add(elemento);
        }
        
        //Lista de dependentes
        for (int i = 1; i < NdeVertices; i++) {
            soma = 0;
            for (int j = 1; j < NdeVertices; j++) {
                soma = soma + matrizDependencias[i][j];
            }
            elemento = new ElementoMatriz(i,soma);
            listaDependentes.add(elemento);
        }
        
        listaFornecedores = OrdenaLista(listaFornecedores);
        listaDependentes = OrdenaLista(listaDependentes);
        
        matrizDependenciasCalculada = new int[NdeVertices][NdeVertices];
        
        //Monta nova matriz, pegando na ordem um elemento da lista de fornecedores e colocando no final 
        //e um da lista de dependentes e colocando no início alternadamente
        for (int i = 0; listaFornecedores.size() > 0; i++) {
            int idAtual;
            
            idAtual = listaFornecedores.get(0).id;
            matrizDependenciasCalculada[NdeVertices - 1 - i][0] = idAtual;
            matrizDependenciasCalculada[0][NdeVertices - 1 - i] = idAtual;
            
            listaFornecedores.remove(0);
            //Procura mesmo elemento na outra lista para remover
            for (int j = 0; j < listaDependentes.size(); j++) {
                if(listaDependentes.get(j).id == idAtual){
                    listaDependentes.remove(j);
                    break;
                }
            }
            
            
            if(listaDependentes.size() > 0 ){
                idAtual = listaDependentes.get(0).id;
                matrizDependenciasCalculada[i + 1][0] = idAtual;
                matrizDependenciasCalculada[0][i + 1] = idAtual;

                listaDependentes.remove(0);
                //Procura mesmo elemento na outra lista para remover
                for (int j = 0; j < listaFornecedores.size(); j++) {
                    if(listaFornecedores.get(j).id == idAtual){
                        listaFornecedores.remove(j);
                        break;
                    }
                }
            }
        }
        
        atualizaMatriz();
        
        
        //----------------------------------------------------------------------
        // Parte II do algoritmo:
        //
        // 1- Varrendo as colunas de trás para frente e as linhas de baixo 
        //      para cima, procurar dependências
        //      - Se achar dependência em (i,j), formar quadrado com (j,i) e somar
        //          bordas do triangulo inferior e superior e comparar
        //      - Se a soma do triângulo superior for maior que a soma do triângulo
        //          inferior, trocar (i,j)
        //----------------------------------------------------------------------
        
        for (int j = NdeVertices - 1; j > 0; j--) {
            for (int i = j - 1; i > 0; i--) {
                if( matrizDependenciasCalculada[i][j]>0 ){ //Tem dependência
                    somaInferior = 0;
                    somaSuperior = 0;
                    
                    for (int k = i; k <= j; k++) {
                        somaSuperior = somaSuperior + matrizDependenciasCalculada[i][k];
                        somaInferior = somaInferior + matrizDependenciasCalculada[k][i];

                        if(k != j && k != i){
                            somaSuperior = somaSuperior + matrizDependenciasCalculada[k][j];
                            somaInferior = somaInferior + matrizDependenciasCalculada[j][k];
                        }
                    }
                    
                    if(somaSuperior > somaInferior){
                        //Troca
                        matrizDependenciasCalculada[i][0] = matrizDependenciasCalculada[0][j];
                        matrizDependenciasCalculada[j][0] = matrizDependenciasCalculada[0][i];
                        
                        matrizDependenciasCalculada[0][i] = matrizDependenciasCalculada[i][0];
                        matrizDependenciasCalculada[0][j] = matrizDependenciasCalculada[j][0];
                        
                        atualizaMatriz();
                        
                        j++; // Para analisar o novo elemento que ficou na posição
                        break;
                    }
                }
            }
        }
    }
    
   

    
    
    public boolean identificarCiclosElem(int elementoBusca, int elementoAtual, ArrayList<String> elemPassados){
        
        elemPassados.add(String.valueOf(elementoAtual));
        
        for (int i = 1; i < NdeVertices; i++) {
            if(matrizDependenciasCalculada[elementoAtual][i] > 0){
                if(elementoBusca == i){
                    return true; // É ciclo
                }else{
                    if( !elemPassados.contains(String.valueOf(i))){
                        if( identificarCiclosElem(elementoBusca, i, elemPassados) == true){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    
}
