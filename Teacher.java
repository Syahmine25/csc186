import java.util.Scanner;
import java.io.*;

public class Teacher {
    private String adminID;
    private Scanner scanner = new Scanner(System.in);

    public Teacher(String adminID) {
        this.adminID = adminID;
    }

    // ==================== TEACHER MENU ====================
    public void TeacherMenu() {
        int choice;

        System.out.println("===== TEACHER MENU =====");
        System.out.println("1) Process Registration");
        System.out.println("2) Back");
        System.out.print("Read choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            processRegistration();
        } else if (choice == 2) {
            System.out.println("Returning to Main Menu...");
            // RegistrationMainMenu() called by leader's main class
        } else {
            System.out.println("Invalid choice. Try again.");
            TeacherMenu();
        }
    }

    // ==================== PROCESS REGISTRATION ====================
    public void processRegistration() {
        String status = "Pending";
        String docVerified, studentVerified, decision;

        // ---------- getDetails() - read from txt files ----------
        System.out.println("=== REGISTRATION DETAILS ===");

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

        // Read from parents.txt
        try {
            BufferedReader parentsReader = new BufferedReader(new FileReader("parents.txt"));
            String line;
            while ((line = parentsReader.readLine()) != null) {
                if (line.startsWith("Student Name:"))      studentName = line.substring(13).trim();
                else if (line.startsWith("Age:"))          studentAge = line.substring(4).trim();
                else if (line.startsWith("Date of Birth:")) studentDOB = line.substring(13).trim();
                else if (line.startsWith("Gender:"))       studentGender = line.substring(7).trim();
                else if (line.startsWith("Address:"))      studentAddress = line.substring(8).trim();
                else if (line.startsWith("MyKid Number:")) mykidNum = line.substring(13).trim();
                else if (line.startsWith("Parent Name:"))  parentName = line.substring(12).trim();
                else if (line.startsWith("Parent IC:"))    parentIC = line.substring(10).trim();
                else if (line.startsWith("Occupation:"))   parentOccupation = line.substring(11).trim();
                else if (line.startsWith("Phone:"))        parentPhone = line.substring(6).trim();
            }
            parentsReader.close();
            System.out.println("Data loaded from parents.txt");
        } catch (IOException e) {
            System.out.println("Error: parents.txt not found. Please make sure parent has submitted their details.");
            TeacherMenu();
            return;
        }

        // Read from document.txt
        try {
            BufferedReader docReader = new BufferedReader(new FileReader("document.txt"));
            String line;
            while ((line = docReader.readLine()) != null) {
                if (line.startsWith("Document Type:")) documentType = line.substring(14).trim();
                else if (line.startsWith("File Name:"))  fileName = line.substring(10).trim();
            }
            docReader.close();
            System.out.println("Data loaded from document.txt");
        } catch (IOException e) {
            System.out.println("Error: document.txt not found. Please make sure parent has uploaded their document.");
            TeacherMenu();
            return;
        }

        // Display all details
        System.out.println("----------------------------------");
        System.out.println("Student Name     : " + studentName);
        System.out.println("Age              : " + studentAge);
        System.out.println("Date of Birth    : " + studentDOB);
        System.out.println("Gender           : " + studentGender);
        System.out.println("Address          : " + studentAddress);
        System.out.println("MyKid Number     : " + mykidNum);
        System.out.println("Parent Name      : " + parentName);
        System.out.println("Parent IC Number : " + parentIC);
        System.out.println("Parent Occupation: " + parentOccupation);
        System.out.println("Parent Phone     : " + parentPhone);
        System.out.println("Document Type    : " + documentType);
        System.out.println("File             : " + fileName);
        System.out.println("Current Status   : " + status);
        System.out.println("----------------------------------");

        // ---------- verifyDocument() ----------
        System.out.println("=== VERIFY DOCUMENT ===");
        System.out.print("Is the document valid? (yes/no): ");
        docVerified = scanner.nextLine();

        if (docVerified.equalsIgnoreCase("yes")) {
            System.out.println("Document verified.");

            // ---------- verifyStudent() ----------
            System.out.println("=== VERIFY STUDENT ===");
            System.out.print("Are student details correct? (yes/no): ");
            studentVerified = scanner.nextLine();

            if (studentVerified.equalsIgnoreCase("yes")) {
                System.out.println("Student details verified.");

                // ---------- approveStudent() / rejectStudent() ----------
                System.out.print("Decision - approve or reject: ");
                decision = scanner.nextLine();

                // ---------- updateStatus() ----------
                if (decision.equalsIgnoreCase("approve")) {
                    status = "Approved";
                } else {
                    status = "Rejected";
                }

            } else {
                // ---------- updateStatus() ----------
                status = "Rejected - Invalid Student Details";
            }

        } else {
            // ---------- updateStatus() ----------
            status = "Rejected - Invalid Document";
        }

        // Save updated status to student.txt
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("student.txt"));
            writer.write("Student Name: " + studentName);
            writer.newLine();
            writer.write("MyKid Number: " + mykidNum);
            writer.newLine();
            writer.write("Status: " + status);
            writer.newLine();
            writer.close();
            System.out.println("Registration status updated: " + status);
            System.out.println("Status saved to student.txt");
        } catch (IOException e) {
            System.out.println("Error saving status: " + e.getMessage());
        }

        System.out.println("Process complete. Final Status: " + status);
        TeacherMenu();
    }
}
