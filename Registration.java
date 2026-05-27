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
        System.out.println("\n====================================");
            System.out.println("       PRIMARY 1 REGISTRATION       ");
            System.out.println("             MAIN MENU              ");
            System.out.println("====================================");
            System.out.println("1) Login");
            System.out.println("2) Sign Up for Parent Account");
            System.out.println("3) Exit");
            System.out.println("====================================");
            System.out.print("👉 Please enter your choice (1-3): ");
            Scanner sc= new Scanner(System.in);
        if (sc.hasNextInt());
         {
            int choice = sc.nextInt();
            sc.nextLine();
        switch (choice) {
            case 1: 
                    System.out.println("------ LOGIN -----");
                        System.out.print("Enter username: ");
                        String loginUser = sc.nextLine();
                        System.out.print("Enter password: ");
                        String loginPass = sc.nextLine();
                        
                        boolean parentLogin = checkFile("parents.txt", loginUser, loginPass);
                        boolean teacherLogin = checkFile("teacher.txt", loginUser, loginPass);
                        
                        if (parentLogin)
                        {
                          parentMenu();
                        }
                        else if (teacherLogin){
                            teacherMenu();
                        }
                        else {
                            System.out.println("Login failed. Incorrect Username or Password");
                        }
                        
                        
                   
            case 2: 
                 System.out.println("-----Parents registration -----");
                System.out.println("create username: ");
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
                            }
                        
                    
