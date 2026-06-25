import java.util.*;
import java.io.*;

public class Teacher {
    private Scanner sc;

    public Teacher(Scanner sc) {
        this.sc = sc;
    }

    public static void clearScreen() {
        System.out.print("\f");
        System.out.flush();
    }

    public void pause() {
        System.out.print("\nPress [ENTER] to continue...");
        sc.nextLine();
    }

    // ==================== TEACHER MENU ====================
    public void TeacherMenu() {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║              TEACHER MENU             ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. View Student Detail               ║");
        System.out.println("║  2. Back                              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("► Choose option: ");

        while (!sc.hasNextInt()) {
            System.out.println("✗ Invalid input! Please enter a number.");
            sc.nextLine();
            System.out.print("► Choose option: ");
        }

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            processRegistration();
        } else if (choice == 2) {
            System.out.println("Returning to Main Menu...");
        } else {
            System.out.println("✗ Invalid choice. Try again.");
            TeacherMenu();
        }
    }

    // ==================== DISPLAY STUDENT DETAILS ====================
    private void displayStudentDetails(String name, String dob, String mykid, String address, String gender, String status) {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║          REGISTRATION DETAILS         ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Student Name  : " + name);
        System.out.println("  Date of Birth : " + dob);
        System.out.println("  MyKid Number  : " + mykid);
        System.out.println("  Home Address  : " + address);
        System.out.println("  Gender        : " + gender);
        System.out.println("  Current Status: " + status);
        System.out.println("╚═══════════════════════════════════════╝");
    }

    // ==================== PROCESS REGISTRATION ====================
    public void processRegistration() {
        List<String[]> students = new ArrayList<>();

        // Read from student.txt — format: Name , DOB , MykidNum , Address , Gender
        try (BufferedReader studentReader = new BufferedReader(new FileReader("student.txt"))) {
            String line;
            while ((line = studentReader.readLine()) != null) {
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length == 5) {
                    students.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error: student.txt not found.");
            pause();
            TeacherMenu();
            return;
        }

        // Loop through each student
        for (String[] s : students) {
            String studentName    = s[0];
            String studentDOB     = s[1];
            String mykidNum       = s[2];
            String studentAddress = s[3];
            String studentGender  = s[4];
            String status         = "Pending";

            // Show initial details with Pending status
            displayStudentDetails(studentName, studentDOB, mykidNum, studentAddress, studentGender, status);

            // Document verification
            System.out.print("\n► Is the document valid? (yes/no): ");
            String docVerified = sc.nextLine();

            if (docVerified.equalsIgnoreCase("yes")) {
                System.out.print("► Are student details correct? (yes/no): ");
                String studentVerified = sc.nextLine();

                if (studentVerified.equalsIgnoreCase("yes")) {
                    System.out.print("► Decision - approve or reject: ");
                    String decision = sc.nextLine();
                    status = decision.equalsIgnoreCase("approve") ? "Approved" : "Rejected";
                } else {
                    status = "Rejected - Invalid Student Details";
                }
            } else {
                status = "Rejected - Invalid Document";
            }

            // Re-display with updated status
            displayStudentDetails(studentName, studentDOB, mykidNum, studentAddress, studentGender, status);
            System.out.println("\n✅ Registration status updated: " + status);

            // Save to status.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("status.txt", true))) {
                writer.write("Student Name: " + studentName);
                writer.newLine();
                writer.write("MyKid: " + mykidNum);
                writer.newLine();
                writer.write("Status: " + status);
                writer.newLine();
                writer.write("----------");
                writer.newLine();
            } catch (IOException e) {
                System.out.println("⚠️ Error saving status: " + e.getMessage());
            }

            // If approved, ask teacher to continue or stop
            if (status.equals("Approved")) {
                System.out.print("\n► Check next student applicant? (yes/no): ");
                String continueCheck = sc.nextLine();
                if (!continueCheck.equalsIgnoreCase("yes")) {
                    System.out.println("Returning to Teacher Menu...");
                    pause();
                    TeacherMenu();
                    return;
                }
            } else {
                pause();
            }
        }

        System.out.println("\n✅ All student applications have been reviewed.");
        pause();
        TeacherMenu();
    }
}
