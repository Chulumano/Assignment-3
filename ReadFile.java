/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.za.cput.execution;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Chulumanco Buhle Nkwindana
 */
public class ReadFile {
    
ObjectInputStream input;
FileWriter fileWriter;
BufferedWriter bufferedWriter;
ArrayList<Customer> customers= new ArrayList<Customer>();
ArrayList<Supplier> suppliers= new ArrayList<Supplier>();
       public void openFile(){
        try{
            input = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("*** ser file created and opened for reading  ***");               
        }
        catch (IOException ioe){
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
           public void readFile(){
        try{
           while(true){
               Object line = input.readObject();
               String y ="Customer";
               String z = "Supplier";
               String name = line.getClass().getSimpleName();
               if (name.equals(y)){
                   customers.add((Customer)line);
               } else if (name.equals(z)){
                   suppliers.add((Supplier)line);
               } else {
                   System.out.println("Error storing objects in array lists");
               }
           
        }
        }
           catch (EOFException eofe) {
            System.out.println("End of file reached");
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Class error reading ser file: "+ ioe);
        }
        catch (IOException ioe) {
            System.out.println("Error reading ser file: "+ ioe);
        }
        
     
        }
       public void closeFile(){
        try{
            input.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("Error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
       public void sortCustomer(){
        String[] sortNo = new String[customers.size()];
        ArrayList<Customer> sortCus= new ArrayList<Customer>();
        int nodeCount = customers.size();
        for (int i = 0; i < nodeCount; i++) {
            sortNo[i] = customers.get(i).getStHolderId();
        }
        Arrays.sort(sortNo);
        
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (sortNo[i].equals(customers.get(j).getStHolderId())){
                    sortCus.add(customers.get(j));
                }
            }
        }
        customers.clear();
        customers= sortCus;
     
    }
       public int getAge(String birthDate){
        String[] interstice= birthDate.split("-");
        
        LocalDate birth = LocalDate.of(Integer.parseInt(interstice[0]), Integer.parseInt(interstice[1]), Integer.parseInt(interstice[2]));
        LocalDate current = LocalDate.now();
        Period difference = Period.between(birth, current);
        int age = difference.getYears();
        return age;
     }
       
       public String dobFormatter(String dob){
       DateTimeFormatter formatDob= DateTimeFormatter.ofPattern("dd MMM yyyy");
       LocalDate birth = LocalDate.parse(dob);
       String date = birth.format(formatDob);
       return date;
       }
       
       public void writingToCustomerTxt(){
        try{
            fileWriter = new FileWriter("customerOutFile.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.format("%s\n","===============================CUSTOMERS=============================="));
            bufferedWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", "ID","Name","Surname","Date of Birth","Age"));
            bufferedWriter.write(String.format("%s\n","======================================================================"));
            for (int i = 0; i < customers.size(); i++) {
                bufferedWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", customers.get(i).getStHolderId(), customers.get(i).getFirstName(), customers.get(i).getSurName(), dobFormatter(customers.get(i).getDateOfBirth()),getAge(customers.get(i).getDateOfBirth())));
            }
            bufferedWriter.write(String.format("%s\n"," "));
            bufferedWriter.write(String.format("%s",rent()));
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            bufferedWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
       }
        
      public String  rent (){
        int nodeCount = customers.size();
        int rentPermitted = 0;
        int rentNotPermitted = 0;
        for (int i = 0; i < nodeCount; i++) {
            if (customers.get(i).getCanRent()){
                rentPermitted++;
            }else {
                rentNotPermitted++;
            }
        }
        String output = String.format ("Number of customers who can rent: %s\nNumber of customers who cannot rent: %s\n",rentPermitted,rentNotPermitted );
        return output;
    }
   
       public void sortSupplier(){
        String[] sortName = new String[suppliers.size()];
        ArrayList<Supplier> sortSup= new ArrayList<Supplier>();
        int nodeCount = suppliers.size();
        for (int i = 0; i < nodeCount; i++) {
            sortName[i] = suppliers.get(i).getName();
        }
        Arrays.sort(sortName);
        
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (sortName[i].equals(suppliers.get(j).getName())){
                    sortSup.add(suppliers.get(j));
                }
            }
        }
        suppliers.clear();
        suppliers= sortSup;
  
    }
        
       public void writingToSupplierTxt(){
        try{
            fileWriter = new FileWriter("supplierOutFile.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.format("%s\n","===============================SUPPLIERS=============================="));
            bufferedWriter.write(String.format("%-15s %-20s %-18s %-15s \n", "ID","Name","Product Type","Description"));
            bufferedWriter.write(String.format("%s\n","======================================================================"));
            for (int i = 0; i < suppliers.size(); i++) {
            bufferedWriter.write(String.format("%-15s %-20s %-18s %-15s \n", suppliers.get(i).getStHolderId(), suppliers.get(i).getName(), suppliers.get(i).getProductType(), suppliers.get(i).getProductDescription()));
            }
            
    
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            bufferedWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
       }
    
       
    public static void main(String args[])  {
        ReadFile obj = new ReadFile();
        obj.openFile();
        obj.readFile();
        obj.closeFile();
        obj.sortCustomer();
        obj.writingToCustomerTxt();
        obj.sortSupplier();
        obj.writingToSupplierTxt();

 


        
      
        }
}