import java.io.*;
import java.util.*;

public class Parents {
    Scanner input = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\f");
        System.out.flush();
    }

    public void parentsMenu() {
        int option;
        do {
            clearScreen();
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║            PARENTS DASHBOARD          ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Provide Details                   ║");
            System.out.println("║  2. Status Application                ║");
            System.out.println("║  3. Back                              ║");
            System.out.println("╚═══════════════════════════════════════╝");

            System.out.print("► Choose option: ");
            while (!input.hasNextInt()) {
                System.out.println("✗ Invalid input! Please enter a number.");
                input.nextLine();
                System.out.print("► Choose option: ");
            }

            option = input.nextInt();
            input.nextLine();

            if (option == 1) {
                ProvideDetails();
            } else if (option == 2) {
                StatusApply();
            } else if (option == 3) {
                System.out.println("Returning to Main Menu...");
            } else {
                System.out.println("✗ Invalid option! Please choose 1–3.");
            }

        } while (option != 3);
    }

    public void ProvideDetails() {
        int option;
        String Name;
        String ICnumber;
        String Occupation;
        String PhoneNum;
        String choice;

        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         SELECT INFORMATION TYPE       ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. Parents Details                   ║");
        System.out.println("║  2. Student Details                   ║");
        System.out.println("║  3. Document Upload                   ║");
        System.out.println("║  4. Back                              ║");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.print("► Choice: ");

        while (!input.hasNextInt()) {
            System.out.println("✗ Invalid option! Please enter a number.");
            input.nextLine();
            System.out.print("► Choice: ");
        }

        option = input.nextInt();
        input.nextLine();

        if (option == 1) {

            do {
                clearScreen();

                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║           PARENTS DETAILS             ║");
                System.out.println("╚═══════════════════════════════════════╝");

                System.out.print("► Parent Name  : ");
                Name = input.nextLine();

                System.out.print("► IC Number    : ");
                ICnumber = input.nextLine();

                System.out.print("► Occupation   : ");
                Occupation = input.nextLine();

                System.out.print("► Phone Number : ");
                PhoneNum = input.nextLine();

                try (BufferedWriter bw = new BufferedWriter(
                        new FileWriter("parents.txt", true))) {

                    bw.write(Name + " , " +
                             ICnumber + " , " +
                             Occupation + " , " +
                             PhoneNum);
                    bw.newLine();
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║      RECORD SAVED SUCCESSFULLY        ║");
                    System.out.println("╚═══════════════════════════════════════╝");

                } catch (IOException e) {
                    System.out.println("✗ Error saving record: " + e.getMessage());
                }
                System.out.print("\nFill another details? (Y/N): ");
                choice = input.nextLine();

            } while (choice.equalsIgnoreCase("Y"));

    }else if (option == 2) {

            clearScreen();
            Student S = new Student();
            S.StudentDetails();

        } else if (option == 3) {

            clearScreen();
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║            DOCUMENT UPLOAD            ║");
            System.out.println("╚═══════════════════════════════════════╝");

            System.out.println("Opening PDF Manager...");
            PDFManager manager = new PDFManager();
            manager.pdfManagerMenu();

        } else if (option == 4) {

            System.out.println("Returning...");

        } else {

            System.out.println("✗ Invalid option! Please choose 1–4.");
        }

}
    // ==================== STATUS APPLY ====================
    public void StatusApply() {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║          APPLICATION STATUS           ║");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.print("► Enter your child's MyKid Number: ");
        String mykidInput = input.nextLine();

        boolean found = false;
        String studentName = "";
        String status = "";

        try (BufferedReader br = new BufferedReader(new FileReader("status.txt"))) {
            String line;
            String currentName = "";
            String currentMykid = "";
            String currentStatus = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Student Name:")) {
                    currentName = line.substring(13).trim();
                } else if (line.startsWith("MyKid:")) {
                    currentMykid = line.substring(6).trim();
                } else if (line.startsWith("Status:")) {
                    currentStatus = line.substring(7).trim();
                    if (currentMykid.equals(mykidInput)) {
                        found = true;
                        studentName = currentName;
                        status = currentStatus;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ No application found. Please submit registration first.");
            System.out.print("\nPress [ENTER] to continue...");
            input.nextLine();
            return;
        }

        System.out.println();
        if (found) {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║           APPLICATION RESULT          ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("  Student Name : " + studentName);
            System.out.println("  MyKid Number : " + mykidInput);
            System.out.println("  Status       : " + status);
            System.out.println("╚═══════════════════════════════════════╝");

            if (status.equalsIgnoreCase("Approved")) {
                System.out.println("🎉 Congratulations! Your child's registration has been APPROVED!");
            } else if (status.toLowerCase().startsWith("rejected")) {
                System.out.println("❌ Sorry, your child's registration has been REJECTED.");
                System.out.println("   Reason: " + status);
            } else {
                System.out.println("⏳ Your application is still PENDING. Please check back later.");
            }
        } else {
            System.out.println("⚠️ No application found for MyKid Number: " + mykidInput);
            System.out.println("   Please make sure you entered the correct MyKid Number.");
        }

        System.out.print("\nPress [ENTER] to continue...");
        input.nextLine();
    }
}
