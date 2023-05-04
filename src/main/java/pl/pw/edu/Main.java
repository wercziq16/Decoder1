package pl.pw.edu;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            FileReader8bit reader = new FileReader8bit(selectedFile.getAbsolutePath());
            reader.read();
            System.out.println(reader.getDictionary());
            System.out.println(reader.getEncoded());
            Decoder decoder = new Decoder();
            Map<Character, String> dictionary = reader.getDictionary();
            String encoded = reader.getEncoded();
            decoder.decode(dictionary, encoded);
        } else {
            System.out.println("Nie wybrano pliku.");
        }
    }

}
