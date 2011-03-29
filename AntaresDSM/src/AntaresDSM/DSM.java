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
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Tom
 */
public class DSM {

    /**
     * Vertex number including the header.
     */
    private int vertexNumber;
    
    private List<String> vertexList = new ArrayList<String>();
    
    
    private int dependencyMatrix[][];
    private int calculatedDependecyMatrix[][];
    
    /** Creates a new instance of DSM */
    public DSM(List<String> fileDescription) {
    int i;
    int spaceIndex1, spaceIndex2;
    int dependent, provider, dependecyValor;
        
        
        //Load element list
        for (i = 0; ((i < fileDescription.size()) && (fileDescription.get(i).compareTo("dep") != 0) ); i++) {
            String element;
            int spaceIndex;

            element = fileDescription.get(i);
            spaceIndex = element.indexOf(" ");
            if(i + 1 ==  Integer.parseInt(element.substring(0, spaceIndex)) ){
                vertexList.add(element.substring(spaceIndex + 1));
            } else {
                JOptionPane.showMessageDialog(null,"Input file is not valid!","Error", JOptionPane.ERROR_MESSAGE);
                break;
            }

        }

        // The first line/column will serve as header
        vertexNumber = i + 1;
        
        dependencyMatrix = new int[vertexNumber][vertexNumber];
        
        for (int j = 0; j < vertexNumber; j++) {
            dependencyMatrix[0][j] = j;
            dependencyMatrix[j][0] = j;
        }
        
        
        for (int j = 1; j < vertexNumber; j++) {
            for (int k = 1; k < vertexNumber; k++) {
                dependencyMatrix[j][k] = 0;
            }
        }
        
       
        //Load dependecies
        for (i = i+1; i < fileDescription.size(); i++) {
        String residualLine;

            spaceIndex1 = fileDescription.get(i).indexOf(' ');

            residualLine = fileDescription.get(i).substring(spaceIndex1 + 1);

            spaceIndex2 = residualLine.indexOf(' ');
            dependent = Integer.parseInt(fileDescription.get(i).substring(0, spaceIndex1));

            //The dependency valor is optional, in the case that it is not set, the default valor will be 1.
            if(spaceIndex2 > 0){
                provider = Integer.parseInt(residualLine.substring(0, spaceIndex2));
                dependecyValor = Integer.parseInt(residualLine.substring(spaceIndex2 + 1));
            }else{
                provider = Integer.parseInt(residualLine);
                dependecyValor = 1;
            }

            dependencyMatrix[dependent][provider] = dependecyValor;
        }
    
        
        
    }
    
    /** Creates a new instance of DSM*/
    public DSM(int vertexAmount, int population, boolean multipleDependencies) {
        Random rand = new Random();
        double popLimit = 0;
        
        rand.setSeed(System.currentTimeMillis());
        
        if(vertexAmount == 0){ //random
            this.vertexNumber = rand.nextInt(100) + 1;
        }else{
            this.vertexNumber = vertexAmount;
        }
        
        // The first line/column will serve as header
        this.vertexNumber = this.vertexNumber + 1;
        
        dependencyMatrix = new int[this.vertexNumber][this.vertexNumber];
        
        for (int j = 0; j < vertexNumber; j++) {
            dependencyMatrix[0][j] = j;
            dependencyMatrix[j][0] = j;
            if(j > 0){
                vertexList.add(String.valueOf(j));
            }
        }

        
        
        for (int j = 1; j < vertexNumber; j++) {
            for (int k = 1; k < vertexNumber; k++) {
                dependencyMatrix[j][k] = 0;
            }
        }
        
        
        if (population  != 1) {
            if(population == 0){ //random
                popLimit = rand.nextDouble();
            }else{
                popLimit = (population - 1) * 0.10;
            }
           
        }
        
        
        for (int j = 1; j < vertexNumber; j++) {
            for (int k = 1; k < vertexNumber; k++) {
                if(rand.nextDouble() < popLimit){ // Mark dependency
                    if(multipleDependencies){
                        dependencyMatrix[j][k] = rand.nextInt(100) + 1;
                    }else{
                        dependencyMatrix[j][k] = 1;
                    }
                }
            }
        }
        
        
    }
    

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void setVertexNumber(int vertexNumber) {
        this.vertexNumber = vertexNumber;
    }

    public List<String> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<String> vertexList) {
        this.vertexList = vertexList;
    }

    public int[][] getDependencyMatrix() {
        return dependencyMatrix;
    }

    public void setDependencyMatrix(int[][] dependencyMatrix) {
        this.dependencyMatrix = dependencyMatrix;
    }
    
    public int[][] getCalculatedDependencyMatrix() {
        return calculatedDependecyMatrix;
    }

    public void setCalculatedDependencyMatrix(int[][] calculatedDependencyMatrix) {
        this.calculatedDependecyMatrix = calculatedDependencyMatrix;
    }
    
    public List<MatrixElement> sortList(List<MatrixElement> elementList){
        
        Collections.sort (elementList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {   
                MatrixElement e1 = (MatrixElement)o1;
                MatrixElement e2 = (MatrixElement)o2;
                return e1.sumElement < e2.sumElement ? -1 : (e1.sumElement > e2.sumElement ? +1 : 0);
            }   
        }); 
        
        
        return elementList;
    }
    
    public void updateMatrix(){
        //Populate the dependencies of the calculated matrix
        for (int i = 1; i < vertexNumber; i++) {
            for (int j = 1; j < vertexNumber; j++) {
                calculatedDependecyMatrix[i][j] =
                        dependencyMatrix[ calculatedDependecyMatrix[i][0] ][ calculatedDependecyMatrix[0][j] ];
            }
        }
    }
    
    public void QuickTriangularMatrix(){
        List<MatrixElement> suplierList = new ArrayList<MatrixElement>();
        List<MatrixElement> dependentList = new ArrayList<MatrixElement>();
        MatrixElement element;
        int sum, inferiorSum, superiorSum;
        
        //----------------------------------------------------------------------
        // Part 1 of the algorithm:
        //
        // 1- Build two lists in ascending order, one with the sum of the suplier's valors (Columns)
        //      and another one with the sum of the dependent's valors (Lines)
        // 2- Gets an element in the ascending order, from the list of supliers e put it in the end of the matrix
        //      and gets an element from the list of dependents and puts in the beginning of the matrix.
        // 3- Build the calculated matrix with this order
        //----------------------------------------------------------------------

        
        //Build the lists to be ordered
        //Supliers list
        for (int i = 1; i < vertexNumber; i++) {
            sum = 0;
            for (int j = 1; j < vertexNumber; j++) {
                sum = sum + dependencyMatrix[j][i];
            }
            element = new MatrixElement(i,sum);
            suplierList.add(element);
        }
        
        //Dependents list
        for (int i = 1; i < vertexNumber; i++) {
            sum = 0;
            for (int j = 1; j < vertexNumber; j++) {
                sum = sum + dependencyMatrix[i][j];
            }
            element = new MatrixElement(i,sum);
            dependentList.add(element);
        }
        
        suplierList = sortList(suplierList);
        dependentList = sortList(dependentList);
        
        calculatedDependecyMatrix = new int[vertexNumber][vertexNumber];
        
        //Build the new matrix, getting in the ascending order an element from the supliers list and place it in the end of the matrix
        //and another one from the dependents list and place it in the beginning of the matrix alternatively
        for (int i = 0; suplierList.size() > 0; i++) {
            int idAtual;
            
            idAtual = suplierList.get(0).id;
            calculatedDependecyMatrix[vertexNumber - 1 - i][0] = idAtual;
            calculatedDependecyMatrix[0][vertexNumber - 1 - i] = idAtual;
            
            suplierList.remove(0);
            //Search the same element in the other list in order to remove it
            for (int j = 0; j < dependentList.size(); j++) {
                if(dependentList.get(j).id == idAtual){
                    dependentList.remove(j);
                    break;
                }
            }
            
            
            if(dependentList.size() > 0 ){
                idAtual = dependentList.get(0).id;
                calculatedDependecyMatrix[i + 1][0] = idAtual;
                calculatedDependecyMatrix[0][i + 1] = idAtual;

                dependentList.remove(0);
                //Search the same element in the other list in order to remove it
                for (int j = 0; j < suplierList.size(); j++) {
                    if(suplierList.get(j).id == idAtual){
                        suplierList.remove(j);
                        break;
                    }
                }
            }
        }
        
        updateMatrix();
        
        
        //----------------------------------------------------------------------
        // Part II of the algorithm:
        //
        // 1- Iterate the columns backwards and the lines upwards searching dependencies
        //      - If a dependecy is found in (i,j), form a square with (ui,j) and sum
        //          borders of the inferior and superior triangle and compares
        //      - If the sum of the superior triangle is greater than the sum of the inferior triangle, change (i,j)
        //----------------------------------------------------------------------
        
        for (int j = vertexNumber - 1; j > 0; j--) {
            for (int i = j - 1; i > 0; i--) {
                if( calculatedDependecyMatrix[i][j]>0 ){ //Has dependency
                    inferiorSum = 0;
                    superiorSum = 0;
                    
                    for (int k = i; k <= j; k++) {
                        superiorSum = superiorSum + calculatedDependecyMatrix[i][k];
                        inferiorSum = inferiorSum + calculatedDependecyMatrix[k][i];

                        if(k != j && k != i){
                            superiorSum = superiorSum + calculatedDependecyMatrix[k][j];
                            inferiorSum = inferiorSum + calculatedDependecyMatrix[j][k];
                        }
                    }
                    
                    if(superiorSum > inferiorSum){
                        //Change
                        calculatedDependecyMatrix[i][0] = calculatedDependecyMatrix[0][j];
                        calculatedDependecyMatrix[j][0] = calculatedDependecyMatrix[0][i];
                        
                        calculatedDependecyMatrix[0][i] = calculatedDependecyMatrix[i][0];
                        calculatedDependecyMatrix[0][j] = calculatedDependecyMatrix[j][0];
                        
                        updateMatrix();
                        
                        j++; // In order to analyze the new element that stayed in the position
                        break;
                    }
                }
            }
        }
    }
    
   

    
    
    public boolean indetifyCycles(int searchElement, int currentElement, List<String> pastElements){
        
        pastElements.add(String.valueOf(currentElement));
        
        for (int i = 1; i < vertexNumber; i++) {
            if(calculatedDependecyMatrix[currentElement][i] > 0){
                if(searchElement == i){
                    return true; // It is a cycle
                }else{
                    if( !pastElements.contains(String.valueOf(i))){
                        if( indetifyCycles(searchElement, i, pastElements) == true){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    
}
