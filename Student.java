import java.util.Scanner;
import java.io.*;

public class Student
{
    public void StudentDetails(){
        boolean running = true;
        int option;
        String StudentName;
        int MykidNum;
        String HomeAddress;
        String gender;
        
    Scanner sc = new Scanner(System.in);
    System.out.println("1)Enter details \n2)Back ");
    option = sc.nextInt();
    
    if (option==1){
        System.out.println("Enter student name: ");
        StudentName = sc.nextLine();
        
        System.out.println("enter Mykid number: ");
        MykidNum = sc.nextInt();
        sc.nextLine();
        
        System.out.println("Home Address: ");
        HomeAddress = sc.nextLine();
        
        System.out.println("Enter Gender (F/M):");
        gender = sc.nextLine();
        sc.nextLine();
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("student.txt" , true ))) {
            bw.write(StudentName + " , " + MykidNum + " , " + HomeAddress + " , " + gender);
            bw.newLine();
            System.out.println("Infomation entered successfully!");
        } catch (IOException iox) {
            System.out.println(iox.getMessage());
        }
        
    } else if (option==2){
       Parents P = new Parents();
       P.parentsMenu();
       
    }
    } 
    
    
    
}
