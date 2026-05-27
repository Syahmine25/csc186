import java.util.Scanner;
import java.io.*;

public class Registration {
    public static void main(String[] args) {
        boolean running = true;
        Scanner sc = new Scanner(System.in);

        while (running) {
            System.out.println("\n====================================");
            System.out.println("       PRIMARY 1 REGISTRATION       ");
            System.out.println("             MAIN MENU              ");
            System.out.println("====================================");
            System.out.println("1) Login");
            System.out.println("2) Sign Up for Parent Account");
            System.out.println("3) Exit");
            System.out.println("====================================");
            System.out.print("👉 Please enter your choice (1-3): ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.println("------ LOGIN -----");
                        System.out.print("Enter username: ");
                        String loginUser = sc.nextLine();
                        System.out.print("Enter password: ");
                        String loginPass = sc.nextLine();

                        boolean parentLogin = checkFile("parents.txt", loginUser, loginPass);
                        boolean teacherLogin = checkFile("teacher.txt", loginUser, loginPass);

                        if (parentLogin) {
                            System.out.println("✅ Parent login successful!");
                            Parents P = new Parents();
                            P.parentsMenu();
                        } else if (teacherLogin) {
                            System.out.println("✅ Teacher login successful!");
                        } else {
                            System.out.println("❌ Login failed. Incorrect Username or Password");
                        }
                        break;

                    case 2:
                        System.out.println("----- Parents Registration -----");
                        System.out.print("Create username: ");
                        String username = sc.nextLine();
                        System.out.print("Create password: ");
                        String password = sc.nextLine();

                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("parents.txt", true))) {
                            bw.write(username + "," + password);
                            bw.newLine();
                            System.out.println("🎉 Registration successful! Login now.");
                        } catch (IOException e) {
                            System.out.println("⚠️ Error saving data.");
                            e.printStackTrace();
                        }
                        break;

                    case 3:
                        System.out.println("👋 Exiting program...");
                        running = false;
                        break;

                    default:
                        System.out.println("⚠️ Invalid choice. Please try again.");
                }
            } else {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                sc.next(); 
            }
        }
        sc.close();
    }

    
    public static boolean checkFile(String filename, String loginUser, String loginPass) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String savedUser = parts[0].trim();
                    String savedPass = parts[1].trim();
                    if (loginUser.equals(savedUser) && loginPass.equals(savedPass)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error reading " + filename);
        }
        return false;
    }
}
