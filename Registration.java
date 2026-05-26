import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Registration 
{
    public static void main(String args[])
    {
      boolean running = true;
       
      while(running){
        System.out.println ("---------------------------------");
        System.out.println ("|   PRIMARY 1 REGISTRATION      |");
        System.out.println ("     | M A I N  M E N U |     ");
        
        System.out.println ("---------------------------------");
        System.out.println ("                            ");
        
        System.out.println ("---------------------------------");
        System.out.println ("|       LOGIN / SIGN UP:        |");
        System.out.println ("---------------------------------");
        System.out.println ("1)LOGIN   \n2)SIGN UP FOR PARENT ACCOUNT  \n3)EXIT ");
        System.out.println( "---------------------------------");
        Scanner sc=new Scanner (System.in);
        int choice = sc.nextInt();
        
        switch (choice) {
            case 1: 
                   System.out.println ("------LOGIN-----");
                   System.out.println ("Enter username: ");
                   
                   
                break;
            case 2: 
                 System.out.println("-----Parents registration -----");
                System.out.println("Enter username: ");
                String username = sc.nextLine();
                 
                System.out.println("create password: ");
                String password =sc.nextLine();
                
                try {
                    FileWriter fw= new FileWriter("parents.txt",true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    bw.write(username + " , "+password);
                    bw.newLine();
                    bw.flush();
                    bw.close();
                    
                    System.out.println ("registration successful ! Login now");
                   } catch (IOException e){
                       System.out.println (" An error occured while saving data.");
                       e.printStackTrace();
                    }
                   break;
                   default:
                       System.out.println("Invalid choice");
                    
                   
             case 3: 
                 System.out.println("Exitting program..");
                 running = false;
               break;
            }
                
                }
                
            
        }
    }
