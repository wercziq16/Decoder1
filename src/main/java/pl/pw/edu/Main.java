package pl.pw.edu;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ReadFile reader = new ReadFile(selectedFile.getAbsolutePath());
            reader.read();
        } else {
            System.out.println("Nie wybrano pliku.");
        }
    }

}
