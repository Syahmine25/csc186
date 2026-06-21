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

    // ==================== PROCESS REGISTRATION ====================
    public void processRegistration() {
        List<String[]> students = new ArrayList<>();

        // Read from student.txt — format: Name , DOB , MykidNum , Address , Gender
        try (BufferedReader studentReader = new BufferedReader(new FileReader("student.txt"))) {
            String line;
            while ((line = studentReader.readLine()) != null) {
                String[] parts = line.split("\\s*,\\s*"); // handles spaces around commas
                if (parts.length == 5) {
                    students.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error: student.txt not found.");
            pause(); TeacherMenu(); return;
        }

        // Loop through each student
        for (String[] s : students) {
            String studentName    = s[0];
            String studentDOB     = s[1];
            String mykidNum       = s[2];
            String studentAddress = s[3];
            String studentGender  = s[4];
            String status         = "Pending";

            clearScreen();
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║          REGISTRATION DETAILS         ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("  Student Name  : " + studentName);
            System.out.println("  Date of Birth : " + studentDOB);
            System.out.println("  MyKid Number  : " + mykidNum);
            System.out.println("  Home Address  : " + studentAddress);
            System.out.println("  Gender        : " + studentGender);
            System.out.println("  Current Status: " + status);
            System.out.println("╚═══════════════════════════════════════╝");

            // verifyDocument()
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

            System.out.println("\n✅ Registration status updated: " + status);
            pause();
        }

        TeacherMenu();
    }
}
