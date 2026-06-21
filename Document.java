import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.awt.*;
import java.awt.Desktop;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PDFManager {

    private static final String STORAGE_FOLDER = "uploaded_pdfs";

    public void uploadPDF() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            savePDFFile(selectedFile);
        }
    }

    private void savePDFFile(File sourceFile) {
        try {
            File storageDir = new File(STORAGE_FOLDER);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }

            File destinationFile = new File(STORAGE_FOLDER + File.separator + sourceFile.getName());

            Files.copy(sourceFile.toPath(), destinationFile.toPath(),
                      StandardCopyOption.REPLACE_EXISTING);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("document.txt", true))) {
                bw.write("PDF Document , " + sourceFile.getName());
                bw.newLine();
            } catch (IOException e) {
                showError("Error saving document info: " + e.getMessage());
            }

            showInfo("PDF uploaded successfully!\nFile: " + destinationFile.getName() +
                    "\nLocation: " + destinationFile.getAbsolutePath());

            int openNow = JOptionPane.showConfirmDialog(null,
                "Do you want to open this PDF now?",
                "Open PDF", JOptionPane.YES_NO_OPTION);

            if (openNow == JOptionPane.YES_OPTION) {
                openStoredPDF(destinationFile.getName());
            }

        } catch (IOException e) {
            showError("Error uploading PDF: " + e.getMessage());
        }
    }

    public void openStoredPDF(String fileName) {
        try {
            File pdfFile = new File(STORAGE_FOLDER + File.separator + fileName);

            if (!pdfFile.exists()) {
                showError("File not found: " + fileName);
                return;
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
                showInfo("Opening PDF: " + fileName);
            } else {
                showError("Desktop operations not supported");
            }

        } catch (IOException e) {
            showError("Error opening PDF: " + e.getMessage());
        }
    }

    public void listUploadedPDFs() {
        File storageDir = new File(STORAGE_FOLDER);

        if (!storageDir.exists() || !storageDir.isDirectory()) {
            showInfo("No uploaded PDFs yet");
            return;
        }

        File[] pdfFiles = storageDir.listFiles((dir, name) -> name.endsWith(".pdf"));

        if (pdfFiles == null || pdfFiles.length == 0) {
            showInfo("No PDF files found in storage");
            return;
        }

        StringBuilder fileList = new StringBuilder("Uploaded PDFs:\n\n");
        for (int i = 0; i < pdfFiles.length; i++) {
            fileList.append((i + 1)).append(". ").append(pdfFiles[i].getName())
                    .append(" (").append(formatFileSize(pdfFiles[i].length())).append(")\n");
        }

        showInfo(fileList.toString());
    }

    public void deletePDF() {
        File storageDir = new File(STORAGE_FOLDER);

        if (!storageDir.exists() || !storageDir.isDirectory()) {
            showError("No uploaded PDFs yet");
            return;
        }

        File[] pdfFiles = storageDir.listFiles((dir, name) -> name.endsWith(".pdf"));

        if (pdfFiles == null || pdfFiles.length == 0) {
            showError("No PDF files found in storage");
            return;
        }

        // Create array of file names for the dialog
        String[] fileNames = new String[pdfFiles.length];
        for (int i = 0; i < pdfFiles.length; i++) {
            fileNames[i] = pdfFiles[i].getName();
        }

        // Show selection dialog
        String selectedFile = (String) JOptionPane.showInputDialog(
            null,
            "Select a PDF to delete:",
            "Delete PDF",
            JOptionPane.QUESTION_MESSAGE,
            null,
            fileNames,
            fileNames[0]
        );

        if (selectedFile != null) {
            File fileToDelete = new File(STORAGE_FOLDER + File.separator + selectedFile);
            if (fileToDelete.delete()) {
                showInfo("PDF deleted: " + selectedFile);
            } else {
                showError("Could not delete PDF: " + selectedFile);
            }
        }
    }

    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int unit = 1024;
        if (bytes < unit * 1024) return String.format("%.2f KB", bytes / (double) unit);
        unit *= 1024;
        if (bytes < unit * 1024) return String.format("%.2f MB", bytes / (double) unit);
        unit *= 1024;
        return String.format("%.2f GB", bytes / (double) unit);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "PDF Manager",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "PDF Manager Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    public void pdfManagerMenu() {
        while (true) {
            String[] options = {"Upload PDF", "List PDFs", "Delete PDF", "Back"};
            int choice = JOptionPane.showOptionDialog(null,
                "PDF Manager - What would you like to do?",
                "PDF Manager Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

            switch (choice) {
                case 0:
                    uploadPDF();
                    break;
                case 1:
                    listUploadedPDFs();
                    break;
                case 2:
                    deletePDF();
                    break;
                case 3:
                    return;
                default:
                    return;
            }
        }
    }
}
