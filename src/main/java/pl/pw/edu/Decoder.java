package pl.pw.edu;

import java.io.File;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
public class Decoder {
     public void decode(Map<Character, String> dictionary, String encoded) {
         StringBuilder decoded = new StringBuilder();
         int i = 0;
         while (i < encoded.length()) {
             boolean foundMatch = false;
             for (Character key : dictionary.keySet()) {
                 String value = dictionary.get(key);
                 if (encoded.startsWith(value, i)) {
                     decoded.append(key);
                     i += value.length();
                     foundMatch = true;
                     break;
                 }
             }
             if (!foundMatch) {
                 // No match found, so just append the current character
                 decoded.append(encoded.charAt(i));
                 i++;
             }
         }
         writeToFile(decoded.toString());
     }

    private void writeToFile(String text) {
        try {
            File outputFile = new File("output.txt");
            outputFile.createNewFile();
            FileWriter writer = new FileWriter(outputFile);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

