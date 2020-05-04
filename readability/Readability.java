package readability;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Readability {

    public static double roundToHund(double value) {
        return (double)Math.round(value * 100) / 100;
    }
    public static boolean isVowel(char x) {
        switch(x) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'y':return true;
        }
        return false;
    }
    public static String ageString(int n){
        if(n == 25)return "24+";
        return String.valueOf(n);
    }

    public static int syllableCount(String str) {
        boolean lastVowel = false;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char x = Character.toLowerCase(str.charAt(i));
            if(i != str.length() -1 ) {
                if (isVowel(x) && !lastVowel) {
                    count++;
                    lastVowel = true;
                } else {
                    lastVowel = false;
                }
            } else {
                if(x != 'e' && isVowel(x)) {
                        count++;
                }
            }
            
        }
        return count;
    }
  
    public static int charCount(String word) {
        String[] chars = word.split("");
        int count = 0;
        String regex = "[^\\s]";
        for(String curr : chars) {
            // System.out.println(curr);
            if (curr.matches(regex)) {
                count++;
            }
    }
        return count;
    }

    public static int ageLevel(double score) {
        if(score < 2.0) {
            return 6;
        } else if (score < 3.0) {
            return 7;
        } else if (score < 4.0) {
            return 9;
        } else if (score < 5.0) {
            return 10;
        } else if (score < 6.0) {
            return 11;
        } else if (score < 7.0) {
            return 12;
        } else if (score < 8.0) {
            return 13;
        } else if (score < 9.0) {
            return 14;
        } else if (score < 10.0) {
            return 15;
        } else if (score < 11.0) {
            return 16;
        } else if (score < 12.0) {
            return 17;
        } else if (score < 13.0) {
            return 18;
        } else if (score < 14.0) {
            return 24;
        } else {
            return 25;
        }
    }


    public static void main(String[] args) throws Exception{
        String fileName = args[0];
        String text = new String(Files.readAllBytes(Paths.get(fileName)));
        Scanner in = new Scanner(System.in);
        
        String[] sentences = text.split("[\\?\\.!\\n]");
        int wordsCount = 0;
        int charactersCount = 0;
        int syllableCount = 0;
        int polySyllableCount = 0;

        charactersCount = charCount(text);
        for (int i = 0; i < sentences.length; i++) {
            String[] words = sentences[i].split("\\s");
            // System.out.println(i + sentences[i]);
            for (String word : words) {
                String regex = "[^\\s]+";
                if (word.matches(regex)) {
                    wordsCount++;
                    // System.out.println(word + " " + syllableCount(word));
                    int syllable = syllableCount(word);
                    syllableCount += syllable;
                    if(syllable >= 3) {
                        polySyllableCount++;
                    }
                }
            }
        }
        double autoReadIndex = 4.71 *  charactersCount / wordsCount   + 0.5 * wordsCount / sentences.length - 21.43;
        double fkReadTest  = 0.39 * wordsCount / sentences.length + 11.8 * syllableCount / wordsCount - 15.59;
        double SMOGIndex = 1.043 * Math.sqrt(polySyllableCount * ((double)30 / sentences.length)) + 3.1291;
        double clIndex = 0.0588 * ( (double)charactersCount / wordsCount) * 100 - 0.296 * ((double)sentences.length / wordsCount) * 100 -15.8;
        System.out.println("Words: " + wordsCount);
        System.out.println("Sentences: " + sentences.length);
        System.out.println("Characters: " + charactersCount);
        System.out.println("Syllables: " + syllableCount);
        System.out.println("Polysyllables: "+polySyllableCount);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String scoreType = in.next();
        double average =(double) (ageLevel(autoReadIndex) + ageLevel(fkReadTest) + ageLevel(SMOGIndex) + ageLevel(clIndex)) / 4.0;
        List<String> al = new ArrayList<String>();
        al.add("Automated Readability Index: " + roundToHund(autoReadIndex)+" (about "+ageString(ageLevel(autoReadIndex))+" year olds).");
        al.add("Flesch–Kincaid readability tests: "+roundToHund(fkReadTest)+" (about "+ageString(ageLevel(fkReadTest))+" year olds).");
        al.add("Simple Measure of Gobbledygook: "+roundToHund(SMOGIndex)+" (about "+ageString(ageLevel(SMOGIndex))+" year olds).");
        al.add("Coleman–Liau index: "+roundToHund(clIndex)+" (about "+ageString(ageLevel(clIndex))+" year olds).");
        printCorrScore(scoreType , al);
        System.out.println("This text should be understood in average by "+roundToHund(average)+" year olds.");
     
    }
    public static void printCorrScore(String scoreType, List<String> al) {
        System.out.println();
        if ("all".equals(scoreType)) {
            al.stream().forEach(x -> System.out.println(x));
        } else if ("ARI".equals(scoreType)) {
            System.out.println(al.get(0));
        } else if ("FK".equals(scoreType)) {
            System.out.println(al.get(1));
        } else if ("SMOG".equals(scoreType)) {
            System.out.println(al.get(2));
        } else {
            System.out.println(al.get(3));
        }
        System.out.println();
    }
}
