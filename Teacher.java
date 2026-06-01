import java.util.Scanner;

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
        } else {
            System.out.println("Invalid choice. Try again.");
            TeacherMenu();
        }
    }

    // ==================== PROCESS REGISTRATION ====================
    public void processRegistration() {
        String studentName, studentAge, studentDOB, studentGender, studentAddress, mykidNum;
        String parentName, parentIC, parentOccupation, parentPhone;
        String documentType, fileName;
        String status = "Pending";
        String docVerified, studentVerified, decision;

        // getDetails()
        System.out.println("=== REGISTRATION DETAILS ===");

        System.out.print("Student Name: ");
        studentName = scanner.nextLine();

        System.out.print("Age: ");
        studentAge = scanner.nextLine();

        System.out.print("Date of Birth (DD/MM/YYYY): ");
        studentDOB = scanner.nextLine();

        do {
            System.out.print("Gender (M/F): ");
            studentGender = scanner.nextLine();
            if (!studentGender.equalsIgnoreCase("M") && !studentGender.equalsIgnoreCase("F")) {
                System.out.println("Invalid input. Please enter M or F only.");
            }
        } while (!studentGender.equalsIgnoreCase("M") && !studentGender.equalsIgnoreCase("F"));

        System.out.print("Home Address: ");
        studentAddress = scanner.nextLine();

        System.out.print("MyKid Number: ");
        mykidNum = scanner.nextLine();

        System.out.print("Parent Name: ");
        parentName = scanner.nextLine();

        System.out.print("Parent IC Number: ");
        parentIC = scanner.nextLine();

        System.out.print("Parent Occupation: ");
        parentOccupation = scanner.nextLine();

        System.out.print("Parent Phone Number: ");
        parentPhone = scanner.nextLine();

        System.out.print("Document Type (e.g. Birth Certificate): ");
        documentType = scanner.nextLine();

        System.out.print("File Name (e.g. birthcert.pdf): ");
        fileName = scanner.nextLine();

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

        // verifyDocument()
        System.out.println("=== VERIFY DOCUMENT ===");
        System.out.print("Is the document valid? (yes/no): ");
        docVerified = scanner.nextLine();

        if (docVerified.equalsIgnoreCase("yes")) {
            System.out.println("Document verified.");

            // verifyStudent()
            System.out.println("=== VERIFY STUDENT ===");
            System.out.print("Are student details correct? (yes/no): ");
            studentVerified = scanner.nextLine();

            if (studentVerified.equalsIgnoreCase("yes")) {
                System.out.println("Student details verified.");

                // approveStudent() / rejectStudent()
                System.out.print("Decision - approve or reject: ");
                decision = scanner.nextLine();

                // updateStatus()
                if (decision.equalsIgnoreCase("approve")) {
                    status = "Approved";
                } else {
                    status = "Rejected";
                }
                System.out.println("Registration status updated: " + status);

            } else {
                // updateStatus()
                status = "Rejected - Invalid Student Details";
                System.out.println("Registration status updated: " + status);
            }

        } else {
            // updateStatus()
            status = "Rejected - Invalid Document";
            System.out.println("Registration status updated: " + status);
        }

        System.out.println("Process complete. Final Status: " + status);
        TeacherMenu();
    }
}
