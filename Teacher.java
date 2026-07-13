import java.util.*;
import java.io.*;
import java.awt.Desktop;

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
        System.out.println("║  1. Process Student Registration      ║");
        System.out.println("║  2. Review Student Application List   ║");
        System.out.println("║  3. Back                              ║");
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
            reviewApplicationList();
        } else if (choice == 3) {
            System.out.println("Returning to Main Menu...");
        } else {
            System.out.println("✗ Invalid choice. Try again.");
            pause();
            TeacherMenu();
        }
    }

    // ==================== DISPLAY STUDENT DETAILS ====================
    private void displayStudentDetails(String name, String dob, String mykid, String address,
                                        String gender, String status) {
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

    // ==================== APPLICATION RECORD ====================
    private static class Application {
        int no;
        String name = "N/A";
        String mykid = "N/A";
        String regDate = "N/A";
        String status = "N/A";
        String dob = "N/A";
        String address = "N/A";
        String gender = "N/A";
        String username = "N/A";
        String parentName = "N/A";
        String parentMykad = "N/A";
        String parentOccupation = "N/A";
        String parentPhone = "N/A";
        boolean hasParentInfo = false;
    }

    // ==================== REVIEW APPLICATION LIST ====================
    public void reviewApplicationList() {
        Map<String, Application> byMykid = new LinkedHashMap<>();

        // 1. Read student.txt
        try (BufferedReader br = new BufferedReader(new FileReader("student.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length == 5 || parts.length == 6 || parts.length == 7) {
                    Application a = new Application();
                    a.name = parts[0];
                    a.dob = parts[1];
                    a.mykid = parts[2];
                    a.address = parts[3];
                    a.gender = parts[4];
                    a.regDate = (parts.length >= 6) ? parts[5] : "N/A";
                    a.username = (parts.length == 7) ? parts[6] : "N/A";
                    a.status = "Pending";
                    byMykid.put(a.mykid, a);
                }
            }
        } catch (IOException e) {
            clearScreen();
            System.out.println("⚠️ Error: student.txt not found. No applications submitted yet.");
            pause();
            TeacherMenu();
            return;
        }

        // 2. Overlay status.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("status.txt"))) {
            String line;
            String curName = "N/A", curMykid = "N/A", curStatus = "N/A";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Student Name:")) {
                    curName = line.replace("Student Name:", "").trim();
                } else if (line.startsWith("MyKid:")) {
                    curMykid = line.replace("MyKid:", "").trim();
                } else if (line.startsWith("Status:")) {
                    curStatus = line.replace("Status:", "").trim();
                } else if (line.equals("----------")) {
                    Application existing = byMykid.get(curMykid);
                    if (existing != null) {
                        existing.status = curStatus;
                    } else if (!curName.equals("N/A")) {
                        Application orphan = new Application();
                        orphan.name = curName;
                        orphan.mykid = curMykid;
                        orphan.status = curStatus;
                        byMykid.put(curMykid, orphan);
                    }
                    curName = "N/A";
                    curMykid = "N/A";
                    curStatus = "N/A";
                }
            }
        } catch (IOException e) {
            // status.txt missing is fine
        }

        // 3. Overlay parent info from parents.txt
        try (BufferedReader pr = new BufferedReader(new FileReader("parents_details.txt"))) {
            String line;
            while ((line = pr.readLine()) != null) {
                String[] parts = line.split("\\s*,\\s*");
                
                //skip login records(username,password)
                if (parts.length != 5)
                continue;
                {
                    String pUsername   = parts[0].trim();
                    String pName       = parts[1].trim();
                    String pMykad      = parts[2].trim();
                    String pOccupation = parts[3].trim();
                    String pPhone      = parts[4].trim();

                    //Match parent username with the username stored in student.txt
                    for (Application app: byMykid.values()){
                    if (app.username.trim().equalsIgnoreCase(pUsername)) {
                       
                        app.parentName = pName;
                        app.parentMykad = pMykad;
                        app.parentOccupation = pOccupation;
                        app.parentPhone = pPhone;
                        app.hasParentInfo = true;
                            
                        break;
                            }
                        }
                    }
                }
        } catch (IOException e) {
            //parents.txt missing is fine
        }

        List<Application> applications = new ArrayList<>(byMykid.values());

        // 4. Assign numbering
        int n = 1;
        for (Application a : applications) {
            a.no = n++;
        }

        // 5. Render table
        clearScreen();
        printApplicationTable(applications);

        // 6. Interactive loop
        while (true) {
            System.out.print("\n► Enter No. to view full details (0 to go back): ");
            while (!sc.hasNextInt()) {
                System.out.println("✗ Invalid input! Please enter a number.");
                sc.nextLine();
                System.out.print("► Enter No. to view full details (0 to go back): ");
            }
            int selection = sc.nextInt();
            sc.nextLine();

            if (selection == 0) {
                TeacherMenu();
                return;
            }

            Application selected = null;
            for (Application a : applications) {
                if (a.no == selection) {
                    selected = a;
                    break;
                }
            }

            if (selected == null) {
                System.out.println("✗ Invalid No. Please try again.");
                continue;
            }

            showApplicationDetailsPage(selected);
            clearScreen();
            printApplicationTable(applications);
        }
    }


    // ==================== PRINT APPLICATION TABLE ====================
    private void printApplicationTable(List<Application> applications) {
        String border = "╠═════╬══════════════════════╬══════════════════╬══════════════════════════╬═════════╣";

        System.out.println("╔═════╦══════════════════════╦══════════════════╦══════════════════════════╦═════════╗");
        System.out.printf("║ %-3s ║ %-20s ║ %-16s ║ %-24s ║ %-7s ║%n",
                "No.", "Student Name", "Reg. Date", "Status", "Action");
        System.out.println(border);

        if (applications.isEmpty()) {
            System.out.println("║                     No applications have been reviewed yet.                                       ║");
        } else {
            for (Application a : applications) {
                System.out.printf("║ %-3d ║ %-20s ║ %-16s ║ %-24s ║ [%3d]   ║%n",
                        a.no,
                        truncate(a.name, 20),
                        truncate(a.regDate, 16),
                        truncate(a.status, 24),
                        a.no);
            }
        }

        System.out.println("╚═════╩══════════════════════╩══════════════════╩══════════════════════════╩═════════╝");
        System.out.println("Total applications: " + applications.size());
    }

    private String truncate(String s, int max) {
        if (s == null) return "N/A";
        return s.length() > max ? s.substring(0, max - 3) + "..." : s;
    }

    // ==================== APPLICATION DETAILS PAGE ====================
    private void showApplicationDetailsPage(Application a) {
        boolean viewingDetails = true;

        while (viewingDetails) {
            clearScreen();
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║          APPLICATION DETAILS          ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("  Student Name    : " + a.name);
            System.out.println("  Date of Birth   : " + a.dob);
            System.out.println("  MyKid Number    : " + a.mykid);
            System.out.println("  Home Address    : " + a.address);
            System.out.println("  Gender          : " + a.gender);
            System.out.println("  Registration Date: " + a.regDate);
            System.out.println("  Status          : " + a.status);
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("  [1] View Submitted PDF Document        ");
            System.out.println("  [2] View Parent Information            ");
            System.out.println("  [3] Back to Application List           ");
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
                viewSubmittedDocument(a);
            } else if (choice == 2) {
                showParentInfoPage(a);
            } else if (choice == 3) {
                viewingDetails = false;
            } else {
                System.out.println("✗ Invalid option. Please choose 1, 2 or 3.");
                pause();
            }
        }
    }

    // ==================== PARENT INFORMATION PAGE ====================
    private void showParentInfoPage(Application a) {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║          PARENT INFORMATION           ║");
        System.out.println("╠═══════════════════════════════════════╣");

        if (!a.hasParentInfo) {
            System.out.println("  No parent details have been submitted");
            System.out.println("  for this student yet.");
        } else {
            System.out.println("  Parent Name       : " + a.parentName);
            System.out.println("  Parent MyKad No.  : " + a.parentMykad);
            System.out.println("  Occupation        : " + a.parentOccupation);
            System.out.println("  Phone Number      : " + a.parentPhone);
        }

        System.out.println("╚═══════════════════════════════════════╝");
        pause();
    }

    // ==================== VIEW SUBMITTED PDF DOCUMENT ====================
    private static final String PDF_STORAGE_FOLDER = "uploaded_pdfs";

    private void viewSubmittedDocument(Application a) {
        clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         SUBMITTED PDF DOCUMENT        ║");
        System.out.println("╚═══════════════════════════════════════╝");

        File storageDir = new File(PDF_STORAGE_FOLDER);
        if (!storageDir.exists() || !storageDir.isDirectory()) {
            System.out.println("✗ No PDF document has been submitted for this student.");
            pause();
            return;
        }

        File[] pdfFiles = storageDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        if (pdfFiles == null || pdfFiles.length == 0) {
            System.out.println("✗ No PDF document has been submitted for this student.");
            pause();
            return;
        }

        // Auto-match by MyKid number appearing in the filename. If nothing
        // matches, this student simply hasn't submitted a document — don't
        // show other students' files.
        List<File> matches = new ArrayList<>();
        for (File f : pdfFiles) {
            if (a.mykid != null && !a.mykid.equals("N/A") && f.getName().contains(a.mykid)) {
                matches.add(f);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("✗ No PDF document has been submitted for this student.");
            pause();
            return;
        }

        File toOpen = (matches.size() == 1)
                ? matches.get(0)
                : chooseFromList(matches, "Multiple documents found for this student:");

        if (toOpen == null) {
            return; // cancelled or invalid selection
        }

        openPDF(toOpen);
    }

    private File chooseFromList(List<File> files, String header) {
        System.out.println("\n" + header);
        for (int i = 0; i < files.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, files.get(i).getName());
        }
        System.out.print("► Enter number to open (0 to cancel): ");

        while (!sc.hasNextInt()) {
            System.out.println("✗ Invalid input! Please enter a number.");
            sc.nextLine();
            System.out.print("► Enter number to open (0 to cancel): ");
        }
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return null;
        if (choice < 1 || choice > files.size()) {
            System.out.println("✗ Invalid selection.");
            pause();
            return null;
        }
        return files.get(choice - 1);
    }

    private void openPDF(File pdfFile) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
                System.out.println("\n✓ Opening: " + pdfFile.getName());
            } else {
                System.out.println("✗ Desktop operations not supported on this system.");
            }
        } catch (IOException e) {
            System.out.println("✗ Error opening PDF: " + e.getMessage());
        }
        pause();
    }

    // Automatically checks uploaded_pdfs for a document matching this student's
    // MyKid number, and offers to open it, before the teacher decides validity.
    private void checkSubmittedDocumentBeforeDecision(String mykidNum) {
        List<File> matches = new ArrayList<>();
        File storageDir = new File(PDF_STORAGE_FOLDER);

        if (storageDir.exists() && storageDir.isDirectory()) {
            File[] pdfFiles = storageDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
            if (pdfFiles != null) {
                for (File f : pdfFiles) {
                    if (f.getName().contains(mykidNum)) {
                        matches.add(f);
                    }
                }
            }
        }

        if (matches.isEmpty()) {
            System.out.println("\n⚠️ No PDF document attached for this student.");
            return;
        }

        if (matches.size() == 1) {
            System.out.println("\n📄 Submitted document found: " + matches.get(0).getName());
        } else {
            System.out.println("\n📄 " + matches.size() + " submitted documents found for this student.");
        }

        System.out.print("► View submitted document before deciding? (yes/no): ");
        String view = sc.nextLine();

        if (view.equalsIgnoreCase("yes")) {
            File toOpen = (matches.size() == 1)
                    ? matches.get(0)
                    : chooseFromList(matches, "Select a document to open:");
            if (toOpen != null) {
                openPDF(toOpen);
            }
        }
    }

    // ==================== PROCESS REGISTRATION ====================
    public void processRegistration() {
        List<String[]> students = new ArrayList<>();

        // Read from student.txt — format: Name , DOB , MykidNum , Address , Gender [, RegDate [, Username]]
        try (BufferedReader studentReader = new BufferedReader(new FileReader("student.txt"))) {
            String line;
            while ((line = studentReader.readLine()) != null) {
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length == 5 || parts.length == 6 || parts.length == 7) {
                    students.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error: student.txt not found.");
            pause();
            TeacherMenu();
            return;
        }

        // Skip students who have already been processed (their MyKid already
        // appears in status.txt) so the same applicant is never processed twice.
        Set<String> processedMykids = new HashSet<>();
        try (BufferedReader statusReader = new BufferedReader(new FileReader("status.txt"))) {
            String line;
            while ((line = statusReader.readLine()) != null) {
                if (line.startsWith("MyKid:")) {
                    processedMykids.add(line.replace("MyKid:", "").trim());
                }
            }
        } catch (IOException e) {
            // status.txt missing just means nothing has been processed yet
        }

        List<String[]> pendingStudents = new ArrayList<>();
        for (String[] s : students) {
            if (!processedMykids.contains(s[2])) {
                pendingStudents.add(s);
            }
        }

        if (pendingStudents.isEmpty()) {
            clearScreen();
            System.out.println("✅ No pending applications to process. Every submitted student has already been reviewed.");
            pause();
            TeacherMenu();
            return;
        }

        // Loop through each student that hasn't been processed yet
        for (String[] s : pendingStudents) {
            String studentName    = s[0];
            String studentDOB     = s[1];
            String mykidNum       = s[2];
            String studentAddress = s[3];
            String studentGender  = s[4];
            String status         = "Pending";

            // Show initial details with Pending status
            displayStudentDetails(studentName, studentDOB, mykidNum, studentAddress, studentGender, status);

            // Automatically check whether the parent uploaded a PDF for this student
            checkSubmittedDocumentBeforeDecision(mykidNum);

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

            // Save to status.txt (registration date now comes from student.txt,
            // captured automatically when the parent submits the form)
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
