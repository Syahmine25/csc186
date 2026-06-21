import java.util.Scanner;
import java.io.*;

public class Registration {

    public static void clearScreen() {
        System.out.print("\f");
        System.out.flush();
    }

    public static void pause(Scanner sc) {
        System.out.print("\nPress [ENTER] to continue...");
        sc.nextLine();
    }

    public static boolean checkFile(String filename, String user, String pass) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length == 2) {

                    String savedUser = parts[0].trim();
                    String savedPass = parts[1].trim();

                    if (user.equals(savedUser) && pass.equals(savedPass)) {
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("⚠ Error reading file: " + filename);
        }

        return false;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {

            clearScreen();

            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║        PRIMARY 1 REGISTRATION         ║");
            System.out.println("║              MAIN MENU                ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Login                             ║");
            System.out.println("║  2. Sign Up                           ║");
            System.out.println("║  3. Exit                              ║");
            System.out.println("╚═══════════════════════════════════════╝");

            System.out.print("► Choose option: ");

            if (sc.hasNextInt()) {

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {

                    clearScreen();
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║                LOGIN                  ║");
                    System.out.println("╚═══════════════════════════════════════╝");

                    System.out.print("► Username: ");
                    String loginUser = sc.nextLine();

                    System.out.print("► Password: ");
                    String loginPass = sc.nextLine();

                    boolean parentLogin = checkFile("parents.txt", loginUser, loginPass);
                    boolean teacherLogin = checkFile("teacher.txt", loginUser, loginPass);

                    if (parentLogin) {

                        System.out.println("\nLogin successful (Parent).");
                        Parents P = new Parents();
                        P.parentsMenu();

                    } else if (teacherLogin) {

                        System.out.println("\nLogin successful (Teacher).");
                        Teacher t = new Teacher(sc);
                        t.TeacherMenu();

                    } else {

                        System.out.println("\nLogin failed. Incorrect username or password.");
                    }

                    pause(sc);

                } else if (choice == 2) {

                    clearScreen();

                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║        PARENT REGISTRATION            ║");
                    System.out.println("╚═══════════════════════════════════════╝");

                    System.out.print("► Create username: ");
                    String username = sc.nextLine();

                    System.out.print("► Create password: ");
                    String password = sc.nextLine();

                    try (BufferedWriter bw = new BufferedWriter(
                            new FileWriter("parents.txt", true))) {
                                bw.write(username + "," + password + ",");
                        bw.newLine();

                        System.out.println("\nRegistration successful!");

                    } catch (IOException e) {
                        System.out.println("⚠ Error saving file.");
                    }

                    pause(sc);

                } else if (choice == 3) {

                    System.out.println("\nExiting system...");
                    running = false;

                } else {

                    System.out.println("Invalid option! Choose 1-3.");
                    pause(sc);
                }

            } else {

                System.out.println("Invalid input! Numbers only.");
                sc.next();
                pause(sc);
            }
        }

        sc.close();
    }
}
