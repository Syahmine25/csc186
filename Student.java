import java.util.Scanner;
import java.io.*;

public class Student {

    public void StudentDetails() {

        int option;
        String StudentName;
        String dateOfBirth;
        String MykidNum;
        String HomeAddress;
        String gender;

        Scanner sc = new Scanner(System.in);

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         STUDENT REGISTRATION          ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. Enter Student Details             ║");
        System.out.println("║  2. Back                              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Choice: ");

        while (!sc.hasNextInt()) {
            System.out.println("✗ Invalid option! Please enter 1 or 2.");
            sc.nextLine();
            System.out.print("Choice: ");
        }
        option = sc.nextInt();
        sc.nextLine(); 

        if (option == 1) {
            clearScreen();
            System.out.println();
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║          ENTER STUDENT DATA           ║");
            System.out.println("╚═══════════════════════════════════════╝");

            while (true) {
                System.out.print("► Student Name  : ");
                StudentName = sc.nextLine();

                if (StudentName.matches("[a-zA-Z ]+")) {
                    break;
                }
                System.out.println("✗ Invalid name! Letters only.");
            }

            while (true) {
                System.out.print("► Date of Birth (DD/MM/YYYY): ");
                dateOfBirth = sc.nextLine();

                if (dateOfBirth.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    break;
                }
                System.out.println("✗ Invalid date format! Use DD/MM/YYYY.");
            }

            while (true) {
            System.out.print("► MyKid Number  : ");
            MykidNum = sc.nextLine();
            if (MykidNum.matches("\\d{12}")) { 
                break;
                
              } else {
              System.out.println("✗ Invalid MyKid Number! Numbers only.");
            }
            }

            System.out.print("► Home Address  : ");
            HomeAddress = sc.nextLine();

            while (true) {
                System.out.print("► Gender (F/M)  : ");
                gender = sc.nextLine().toUpperCase();

                if (gender.equals("F") || gender.equals("M")) {
                    break;
                }
                System.out.println("✗ Invalid gender! Please enter F or M only.");
            }

            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter("student.txt", true))) {

                bw.write(StudentName + " , " +
                         dateOfBirth + " , " +
                         MykidNum + " , " +
                         HomeAddress + " , " +
                         gender);
                bw.newLine();

                System.out.println();
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║       RECORD SAVED SUCCESSFULLY       ║");
                System.out.println("╚═══════════════════════════════════════╝");

            } catch (IOException iox) {
                System.out.println("✗ Error: " + iox.getMessage());
            }System.out.print("\nPress [ENTER] to continue...");
             sc.nextLine();

        } else if (option == 2) {
            Parents P = new Parents();
            P.parentsMenu();
        } else {
            System.out.println();
            System.out.println("✗ Invalid option! Please choose 1 or 2.");
        }
    }
    public static void clearScreen() {
        System.out.print("\f");
        System.out.flush(); 
    }
    public static void pause(Scanner sc) {
        System.out.print("\nPress [ENTER] to continue...");
        sc.nextLine();
    }
}
