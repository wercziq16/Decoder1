package pl.pw.edu;

import java.io.FileInputStream;
import java.util.HashMap;
import java.io.*;
import java.util.Map;

public class FileReader8bit implements FileReader {
    private final String fileName;

    private int decimalCount=0;

    private String encoded;
    private Map<Character, String> dictionary;

    public String getEncoded() {
        return encoded;
    }

    public Map<Character, String> getDictionary() {
        return dictionary;
    }

    private static final int BIT_COUNTER = 256*8;
    public FileReader8bit(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void read() {
        try {
            StringBuilder binaryString = readBinaryStringFromFile();
            String trimmedText = trimTrailingZeros(binaryString);
            this.dictionary = addToDictionary(trimmedText);
            this.encoded = encodedText (trimmedText);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private StringBuilder readBinaryStringFromFile() throws IOException {
        FileInputStream fis = new FileInputStream(this.fileName);
        int byteRead;
        StringBuilder binaryString = new StringBuilder();
        while ((byteRead = fis.read()) != -1) {
            for (int i = 7; i >= 0; i--) {
                int bit = (byteRead >>> i) & 1;
                String bin = Integer.toBinaryString(bit);
                binaryString.append(bin);
            }
        }
        return binaryString;
    }

    private String trimTrailingZeros(StringBuilder binaryString) {
        char lastChar = binaryString.charAt(binaryString.length() - 1);
        while (lastChar != '1') {
            binaryString.deleteCharAt(binaryString.length() - 1); // usunięcie ostatniego znaku
            lastChar = binaryString.charAt(binaryString.length() - 1); // aktualizacja ostatniego znaku
        }
        return binaryString.substring(0, binaryString.length() - 1);
    }

    private HashMap<Character, String> addToDictionary(String trimmedText) {
        int startIndex = 0;
        HashMap<Character, String> dictionary = new HashMap<>();
        char keyChar;
        for (int i = 0; i < 255; i++) {
            String value;
            keyChar=(char)i;
            String eightChars = trimmedText.substring(startIndex, startIndex + 8);
            int decimal = Integer.parseInt(eightChars, 2);
            if (decimal != 0) {
                value = trimmedText.substring(startIndex + 8, startIndex + 8 + decimal);
                dictionary.put(keyChar,value);
                decimalCount+=decimal;
            }
            startIndex = startIndex + 8 + decimal;
        }
        return dictionary;
    }

    private String encodedText (String trimmedText){
        return trimmedText.substring(decimalCount+ BIT_COUNTER);
    }
}