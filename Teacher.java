import java.util.Scanner;
import java.io.*;

public class Teacher
{
    private String adminID;
    private Scanner scanner;

    public Teacher(String adminID)
    {
        this.adminID = adminID;
        scanner = new Scanner(System.in);
    }

    public void TeacherMenu()
    {
        int choice;

        do
        {
            System.out.println("\n================================");
            System.out.println("          TEACHER MENU");
            System.out.println("================================");
            System.out.println("Teacher ID: " + adminID);
            System.out.println("1) Process Registration");
            System.out.println("2) Logout");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt())
            {
                System.out.print("Invalid input. Enter a number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice)
            {
                case 1:
                    processRegistration();
                    break;

                case 2:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while(choice != 2);
    }

    public void processRegistration()
    {
        String status = "Pending";

        String studentName = "";
        String studentAge = "";
        String studentDOB = "";
        String studentGender = "";
        String studentAddress = "";
        String mykidNum = "";

        String parentName = "";
        String parentIC = "";
        String parentOccupation = "";
        String parentPhone = "";

        String documentType = "";
        String fileName = "";

        System.out.println("\n===== REGISTRATION DETAILS =====");

        try
        {
            BufferedReader br =
                new BufferedReader(new FileReader("parents.txt"));

            String line;

            while((line = br.readLine()) != null)
            {
                if(line.startsWith("Student Name:"))
                    studentName = line.substring(13).trim();

                else if(line.startsWith("Age:"))
                    studentAge = line.substring(4).trim();

                else if(line.startsWith("Date of Birth:"))
                    studentDOB = line.substring(14).trim();

                else if(line.startsWith("Gender:"))
                    studentGender = line.substring(7).trim();

                else if(line.startsWith("Address:"))
                    studentAddress = line.substring(8).trim();

                else if(line.startsWith("MyKid Number:"))
                    mykidNum = line.substring(13).trim();

                else if(line.startsWith("Parent Name:"))
                    parentName = line.substring(12).trim();

                else if(line.startsWith("Parent IC:"))
                    parentIC = line.substring(10).trim();

                else if(line.startsWith("Occupation:"))
                    parentOccupation = line.substring(11).trim();

                else if(line.startsWith("Phone:"))
                    parentPhone = line.substring(6).trim();
            }

            br.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: parents.txt not found.");
            return;
        }

        try
        {
            BufferedReader br =
                new BufferedReader(new FileReader("document.txt"));

            String line;

            while((line = br.readLine()) != null)
            {
                if(line.startsWith("Document Type:"))
                    documentType = line.substring(14).trim();

                else if(line.startsWith("File Name:"))
                    fileName = line.substring(10).trim();
            }

            br.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: document.txt not found.");
            return;
        }

        System.out.println("--------------------------------");
        System.out.println("Student Name      : " + studentName);
        System.out.println("Age               : " + studentAge);
        System.out.println("Date of Birth     : " + studentDOB);
        System.out.println("Gender            : " + studentGender);
        System.out.println("Address           : " + studentAddress);
        System.out.println("MyKid Number      : " + mykidNum);
        System.out.println("Parent Name       : " + parentName);
        System.out.println("Parent IC         : " + parentIC);
        System.out.println("Parent Occupation : " + parentOccupation);
        System.out.println("Parent Phone      : " + parentPhone);
        System.out.println("Document Type     : " + documentType);
        System.out.println("File Name         : " + fileName);
        System.out.println("Current Status    : " + status);
        System.out.println("--------------------------------");

        System.out.print("Is the document valid? (yes/no): ");
        String docVerified = scanner.nextLine();

        if(docVerified.equalsIgnoreCase("yes"))
        {
            System.out.println("Document verified.");

            System.out.print("Are student details correct? (yes/no): ");
            String studentVerified = scanner.nextLine();

            if(studentVerified.equalsIgnoreCase("yes"))
            {
                System.out.println("Student details verified.");

                System.out.print("Decision (approve/reject): ");
                String decision = scanner.nextLine();

                if(decision.equalsIgnoreCase("approve"))
                {
                    status = "Approved";
                }
                else
                {
                    status = "Rejected";
                }
            }
            else
            {
                status = "Rejected - Invalid Student Details";
            }
        }
        else
        {
            status = "Rejected - Invalid Document";
        }

        try
        {
            BufferedWriter bw =
                new BufferedWriter(
                new FileWriter("student.txt", true));

            bw.write("Student Name: " + studentName);
            bw.newLine();

            bw.write("MyKid Number: " + mykidNum);
            bw.newLine();

            bw.write("Status: " + status);
            bw.newLine();

            bw.write("--------------------------------");
            bw.newLine();

            bw.close();

            System.out.println("Registration status saved.");
        }
        catch(IOException e)
        {
            System.out.println("Error saving status.");
        }

        System.out.println("Final Status: " + status);
    }
}
