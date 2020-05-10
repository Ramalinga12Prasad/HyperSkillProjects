package encryptdecrypt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

interface EncryptionAlgorithm {
    String encrypt(String data, int key);

    String decrypt(String data, int key);
}

class ShiftAlgorithm implements EncryptionAlgorithm {

   
    @Override
    public String encrypt(String data, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char character : data.toCharArray()) {
            
            if (Character.isAlphabetic(character)) {
                int a = character - 'a' + key;
                a %= 26;
                char encChar = (char) (a + 'a');
                encryptedText.append(String.valueOf(encChar));
            } else {
                encryptedText.append(String.valueOf(character));
            }
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(String data, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char character : data.toCharArray()) {
            
            if (Character.isAlphabetic(character)) {
                int a = character - 'a' - key + 26;
                a %= 26;
                char encChar = (char) (a + 'a');
                encryptedText.append(String.valueOf(encChar));
            } else {
                encryptedText.append(String.valueOf(character));
            }
        }
        return encryptedText.toString();
    }


}


class UnicodeAlgorithm implements EncryptionAlgorithm {

    @Override
    public String encrypt(String data, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char character : data.toCharArray()) {
            char encChar = (char) (character + key);
            encryptedText.append(String.valueOf(encChar));
        }
        return encryptedText.toString();
        
    }

    @Override
    public String decrypt(String data, int key) {
        StringBuilder decryptedText = new StringBuilder();
        for (char character : data.toCharArray()) {
            char encChar = (char) (character - key);
            decryptedText.append(String.valueOf(encChar));
        }
        return decryptedText.toString();
    }
    
}


class AlgorithmFactory {
    public static EncryptionAlgorithm getAlgorithmInstance(String type) {
        switch(type) {
            case "shift" : return new ShiftAlgorithm();
            case "unicode" : return new UnicodeAlgorithm();
            default : return null;
        }

        
    }
}


public class Main {
    


    //method for reading a file
    public static String readFile(String fileName)throws IOException {
        FileReader fr = new FileReader(fileName);
        int character = fr.read();
        StringBuilder sb = new StringBuilder();
        while (character != -1) {
            sb.append(String.valueOf((char)character));
            character = fr.read();
        }
        fr.close();
        return sb.toString();

    }

    //method to write a file
    public static void writeFile(String fileName, String text) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        fw.write(text);
        fw.close();
    }
    public static void main(String[] args) {
        String choice = "enc";
        String data = "";
        int key = 0;
        String inFile = "";
        String outFile = "";
        String alg = "";
        for (int i = 0; i < args.length; i+=2) {
            switch (args[i]) {
                case "-mode":
                    choice = args[i + 1];
                    break;
                case "-key" :
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    inFile = args[i + 1];
                    break;
                case "-out":
                    outFile = args[i + 1];
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
            }
        }

        if(inFile.length() != 0) {
            if(data.length() == 0) {
                try{
                    data = readFile(inFile);
                } catch (Exception e) {
                    System.out.println("Error : problem while reading a file");
                }
            }
        }

        EncryptionAlgorithm algorithm = AlgorithmFactory.getAlgorithmInstance(alg);
        String encDecMessage = "";
        switch (choice) {
            case "enc" :
                encDecMessage = algorithm.encrypt(data, key);
                break;
            case "dec" :
                encDecMessage = algorithm.decrypt(data, key);
                break;
        }
        if(outFile.length() == 0) {
            System.out.println(encDecMessage);
        } else {
            try{
                writeFile(outFile, encDecMessage);
            } catch (Exception e) {
                System.out.println("Error : problem while writing a file");
            }
        }

    }
}
