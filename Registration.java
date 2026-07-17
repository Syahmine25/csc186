import java.util.Scanner;
import java.io.*;

public class Registration {

    static final int WIDTH = 180;

    public static void clearScreen() {
        System.out.print("\f");
        System.out.flush();
    }

    public static void blankLines(int n) {
        for (int i = 0; i < n; i++)
            System.out.println();
    }

    public static void centerPrint(String text) {
        int padding = Math.max(0, (WIDTH - text.length()) / 2);
        for (int i = 0; i < padding; i++)
            System.out.print(" ");
        System.out.println(text);
    }

    public static void centerInput(String text) {
        for (int i = 0; i < 70; i++)
            System.out.print(" ");
        System.out.print(text);
    }

    public static void messagePrint(String text) {
        for (int i = 0; i < 70; i++)
            System.out.print(" ");
        System.out.println(text);
    }

    public static void pause(Scanner sc) {
        System.out.println();
        centerInput("Press [ENTER] to continue...");
        sc.nextLine();
    }

    public static boolean checkFile(String filename, String user, String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String savedUser = parts[0].trim();
                    String savedPass = parts[1].trim();
                    if (user.equals(savedUser) && pass.equals(savedPass)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            messagePrint("Error reading file: " + filename);
        }
        return false;
    }

    // ✅ New helper: check if username already exists
    public static boolean usernameExists(String filename, String user) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    String savedUser = parts[0].trim();
                    if (user.equals(savedUser)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            // Ignore if file not found (means no accounts yet)
        }
        return false;
    }

    public static void showMainMenu() {
        clearScreen();
        blankLines(8);
        centerPrint("╔═══════════════════════════════════════╗");
        centerPrint("║        PRIMARY 1 REGISTRATION         ║");
        centerPrint("║              MAIN MENU                ║");
        centerPrint("╠═══════════════════════════════════════╣");
        centerPrint("║  1. Login                             ║");
        centerPrint("║  2. Create account for Parents        ║");
        centerPrint("║  3. Exit                              ║");
        centerPrint("╚═══════════════════════════════════════╝");
        System.out.println();
    }

    public static void showLogin() {
        clearScreen();
        blankLines(8);
        centerPrint("╔═══════════════════════════════════════╗");
        centerPrint("║                LOGIN                  ║");
        centerPrint("╚═══════════════════════════════════════╝");
        System.out.println();
    }

    public static void showRegister() {
        clearScreen();
        blankLines(8);
        centerPrint("╔═══════════════════════════════════════╗");
        centerPrint("║        PARENT REGISTRATION            ║");
        centerPrint("╚═══════════════════════════════════════╝");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            showMainMenu();
            centerInput("► Choose option: ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    showLogin();
                    centerInput("► Username: ");
                    String loginUser = sc.nextLine();
                    centerInput("► Password: ");
                    String loginPass = sc.nextLine();

                    boolean parentLogin = checkFile("parents_accounts.txt", loginUser, loginPass);
                    boolean teacherLogin = checkFile("teacher.txt", loginUser, loginPass);

                    if (parentLogin) {
                        System.out.println();
                        messagePrint("Login successful (Parent).");
                        pause(sc);
                        Parents P = new Parents(loginUser);
                        P.parentsMenu();
                    } else if (teacherLogin) {
                        System.out.println();
                        messagePrint("Login successful (Teacher).");
                        pause(sc);
                        Teacher t = new Teacher(sc);
                        t.TeacherMenu();
                    } else {
                        System.out.println();
                        messagePrint("Login failed. Incorrect username or password.");
                        pause(sc);
                    }

                } else if (choice == 2) {
                    showRegister();
                    centerInput("► Create username: ");
                    String username = sc.nextLine().trim();
                    centerInput("► Create password: ");
                    String password = sc.nextLine().trim();

                    // ✅ Validation: empty input
                    if (username.isEmpty() || password.isEmpty()) {
                        System.out.println();
                        messagePrint("Error: Username and password cannot be empty!");
                        pause(sc);
                    }
                    // ✅ Validation: duplicate username
                    else if (usernameExists("parents_accounts.txt", username)) {
                        System.out.println();
                        messagePrint("Error: Username already exists. Please choose another.");
                        pause(sc);
                    }
                    else {
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("parents_accounts.txt", true))) {
                            bw.write(username + "," + password);
                            bw.newLine();
                            System.out.println();
                            messagePrint("Registration successful!");
                        } catch (IOException e) {
                            messagePrint("Error saving file.");
                        }
                        pause(sc);
                    }

                } else if (choice == 3) {
                    clearScreen();
                    blankLines(10);
                    centerPrint("╔══════════════════════════════════════╗");
                    centerPrint("║          THANK YOU FOR USING         ║");
                    centerPrint("║         PRIMARY 1 REGISTRATION       ║");
                    centerPrint("║          HAVE A NICE DAY!            ║");
                    centerPrint("╚══════════════════════════════════════╝");
                    running = false;
                } else {
                    System.out.println();
                    messagePrint("Invalid option! Choose 1 - 3.");
                    pause(sc);
                }

            } else {
                System.out.println();
                messagePrint("Invalid input! Numbers only.");
                sc.nextLine();
                pause(sc);
            }
        }

        sc.close();
    }
}
