import java.util.Scanner;
import java.io.*;

public class Teacher {
    private String adminID;
    private Scanner sc;

    public Teacher(String adminID, Scanner sc) {
        this.adminID = adminID;
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
        System.out.println("║  1. Process Registration              ║");
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
        String status = "Pending";
        String docVerified, studentVerified, decision;

        String parentName = "", parentIC = "", parentOccupation = "", parentPhone = "";
        // matches Student.java: Name , DOB , MykidNum , Address , Gender
        String studentName = "", studentDOB = "", mykidNum = "", studentAddress = "", studentGender = "";
        String documentType = "", fileName = "";

        // Read from parents.txt — format: Name , IC , Occupation , Phone
        try {
            BufferedReader parentsReader = new BufferedReader(new FileReader("parents.txt"));
            String line;
            while ((line = parentsReader.readLine()) != null) {
                String[] parts = line.split(" , ");
                if (parts.length == 4) {
                    parentName       = parts[0].trim();
                    parentIC         = parts[1].trim();
                    parentOccupation = parts[2].trim();
                    parentPhone      = parts[3].trim();
                }
            }
            parentsReader.close();
        } catch (IOException e) {
            System.out.println("⚠️ Error: parents.txt not found.");
            pause(); TeacherMenu(); return;
        }

        // Read from students.txt — format: Name , DOB , MykidNum , Address , Gender
        try {
            BufferedReader studentReader = new BufferedReader(new FileReader("students.txt"));
            String line;
            while ((line = studentReader.readLine()) != null) {
                String[] parts = line.split(" , ");
                if (parts.length == 5) {
                    studentName    = parts[0].trim();
                    studentDOB     = parts[1].trim();
                    mykidNum       = parts[2].trim();
                    studentAddress = parts[3].trim();
                    studentGender  = parts[4].trim();
                }
            }
            studentReader.close();
        } catch (IOException e) {
            System.out.println("⚠️ Error: students.txt not found.");
            pause(); TeacherMenu(); return;
        }

        // Read from document.txt — format: DocumentType , FileName
        try {
            BufferedReader docReader = new BufferedReader(new FileReader("document.txt"));
            String line;
            while ((line = docReader.readLine()) != null) {
                String[] parts = line.split(" , ");
                if (parts.length == 2) {
                    documentType = parts[0].trim();
                    fileName     = parts[1].trim();
                }
            }
            docReader.close();
        } catch (IOException e) {
            System.out.println("⚠️ Error: document.txt not found.");
            pause(); TeacherMenu(); return;
        }

        // Display all details
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║          REGISTRATION DETAILS         ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  STUDENT INFO                         ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Student Name  : " + studentName);
        System.out.println("  Date of Birth : " + studentDOB);
        System.out.println("  MyKid Number  : " + mykidNum);
        System.out.println("  Home Address  : " + studentAddress);
        System.out.println("  Gender        : " + studentGender);
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  PARENT INFO                          ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Parent Name      : " + parentName);
        System.out.println("  Parent IC Number : " + parentIC);
        System.out.println("  Parent Occupation: " + parentOccupation);
        System.out.println("  Parent Phone     : " + parentPhone);
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  DOCUMENT INFO                        ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Document Type : " + documentType);
        System.out.println("  File          : " + fileName);
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Current Status: " + status);
        System.out.println("╚═══════════════════════════════════════╝");

        // verifyDocument()
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            VERIFY DOCUMENT            ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("► Is the document valid? (yes/no): ");
        docVerified = sc.nextLine();

        if (docVerified.equalsIgnoreCase("yes")) {
            System.out.println("✅ Document verified.");

            // verifyStudent()
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║            VERIFY STUDENT             ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("► Are student details correct? (yes/no): ");
            studentVerified = sc.nextLine();

            if (studentVerified.equalsIgnoreCase("yes")) {
                System.out.println("✅ Student details verified.");

                // approveStudent() / rejectStudent()
                System.out.println("\n╔═══════════════════════════════════════╗");
                System.out.println("║               DECISION                ║");
                System.out.println("╚═══════════════════════════════════════╝");
                System.out.print("► Decision - approve or reject: ");
                decision = sc.nextLine();

                // updateStatus()
                if (decision.equalsIgnoreCase("approve")) {
                    status = "Approved";
                } else {
                    status = "Rejected";
                }
            } else {
                status = "Rejected - Invalid Student Details";
            }
        } else {
            status = "Rejected - Invalid Document";
        }

        // Save to student.txt so Parents StatusApply() can read it
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("student.txt", true));
            writer.write("Student Name: " + studentName);
            writer.newLine();
            writer.write("MyKid: " + mykidNum);
            writer.newLine();
            writer.write("Status: " + status);
            writer.newLine();
            writer.write("----------");
            writer.newLine();
            writer.close();
            System.out.println("\n✅ Registration status updated: " + status);
            System.out.println("📁 Status saved to student.txt");
        } catch (IOException e) {
            System.out.println("⚠️ Error saving status: " + e.getMessage());
        }

        System.out.println("\nProcess complete. Final Status: " + status);
        pause();
        TeacherMenu();
    }
}
