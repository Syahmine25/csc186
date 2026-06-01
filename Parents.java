import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Parents
{
    public static void clearScreen() {
        System.out.print("\f");
        System.out.flush();
    } 
    
    Scanner input = new Scanner(System.in);
    public void ProvideDetails()
    {
        //constructor 
        int option;
        String Name;
        int ICnumber;
        String Occupation;
        int PhoneNum;
        String choice;
        
        System.out.println ("1) Parents Details \n2)Student Details \n3)Back ");
        option = input.nextInt();
        
        if (option==1)
        {
            do{
                clearScreen();
         System.out.println("====Parents Details====");
          System.out.println("Enter Parent name: ");
          Name = input.nextLine();
          
          System.out.println("Enter IC number number: ");
          ICnumber = input.nextInt();
          
          System.out.println("Enter occupation: ");
          Occupation = input.nextLine();
          
          System.out.println("Enter phone number: ");
          PhoneNum = input.nextInt();
          
          System.out.println("Fill another details ? (Y/N):");
          choice = input.nextLine();
          
        }while (choice!="N");
   } else if (option== 2){
       clearScreen();
       Student S = new Student();
       S.StudentDetails();
   }
}
   
    public void StatusApply()
    {
        
    }

    public void parentsMenu()
    {
        int option;

        do
        {
            System.out.println("\n===== PARENTS MENU =====");
            System.out.println("1) Provide Details");
            System.out.println("2) Status Application");
            System.out.println("3) Back");
            System.out.print("Choose option: ");
            option = input.nextInt();

            if(option == 1)
               ProvideDetails(); 
            else if(option == 2)
               ProvideDetails();
               
            else if(option == 3)
                System.out.println("Back to Main Menu...");
            else
                System.out.println("Invalid option!");

        } while(option != 3);
    }
}
