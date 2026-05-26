import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Parents
{
    Scanner input = new Scanner(System.in);

    public void registerForm()
{
    input.nextLine(); // consume newline
    System.out.println("\n--- Student Registration ---");
    System.out.print("Enter student name: ");
    String name = input.nextLine();

    System.out.print("Enter student age: ");
    int age = input.nextInt();

    // Save to file
    try {
        FileWriter fw = new FileWriter("students.txt", true); // append mode
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(name + " , " + age);
        bw.newLine();
        bw.flush();
        bw.close();

        System.out.println("Student registered successfully! Data saved to students.txt");
    } catch (IOException e) {
        System.out.println("An error occurred while saving student data.");
        e.printStackTrace();
    }
}


    public void updateForm()
    {
        input.nextLine();
        System.out.println("\n--- Profile Management ---");
        System.out.print("Enter student name to update: ");
        String name = input.nextLine();

        System.out.println("Profile updated successfully!");
    }

    public void parentsMenu()
    {
        int option;

        do
        {
            System.out.println("\n===== PARENTS MENU =====");
            System.out.println("1) Student Registration");
            System.out.println("2) Profile Management");
            System.out.println("3) Back");
            System.out.print("Choose option: ");
            option = input.nextInt();

            if(option == 1)
                registerForm();
            else if(option == 2)
                updateForm();
            else if(option == 3)
                System.out.println("Back to Main Menu...");
            else
                System.out.println("Invalid option!");

        } while(option != 3);
    }
}