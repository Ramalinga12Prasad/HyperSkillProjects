package flashcards;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
class FlashCards {
    public static Scanner in  = new Scanner(System.in);
    public static Map<String, String> flashCards = new LinkedHashMap<>();
    public static Map<String, Integer> mistakes = new LinkedHashMap<>();
    public static List<String> logs = new ArrayList<>();
    private static Random random = new Random();
    public static FlashCards getInstance() {
        return new FlashCards();
    }
    public  boolean checkKeys(String key) {
        return flashCards.containsKey(key);
    }
    public  boolean checkValues(String value) {
        return flashCards.containsValue(value);
    }
    public  boolean anotherDefinition(String definition) {
        Collection<String> definitions = flashCards.values();
        return definitions.contains(definition);
    }
    public  String getCardForDefinition(String definition) {
        Set<String> cards = flashCards.keySet();
        for (String card : cards) {
            String def = flashCards.get(card);
            if (def.equals(definition)) {
                return card;
            }
        }
        return "";
    }
    public Scanner getScanner(){
        return in;
    }
    public String logOnly(String message) {
        logs.add(message);
        return message;
    }
    public void logAndPrint(String message) {
        System.out.println(logOnly(message));
    }

    
    public void getCards() {
     
            logAndPrint("The card:");
            String name = logOnly(in.nextLine());
            if (checkKeys(name)) {
                logAndPrint("The card \"" + name + "\" already exists. Try again:");
                return;
            }
            logAndPrint("The definition of the card:");
            String definition = logOnly(in.nextLine());
            if (checkValues(definition)) {
                logAndPrint("The definition \"" + definition + "\" already exists. Try again:");
                return;
            }
            flashCards.put(name, definition);
            mistakes.put(name, 0);
            logAndPrint("The pair (\"" + name + "\":\"" + definition + "\") has been added.");

        
    }

    public void removeCard() {
        logAndPrint("The card:");
        String name = logOnly(in.nextLine());
        if (checkKeys(name)) {
            flashCards.remove(name);
            mistakes.remove(name);
            logAndPrint("The card has been removed.");
        } else {
            logAndPrint("Can't remove \"" + name + "\": there is no such card.");
        }
    }

    public void readFromFile() {
        logAndPrint("File name:");
        String fileName = logOnly(in.nextLine());
        try (FileReader fr = new FileReader(fileName)) {

            
            int character = fr.read();
            StringBuilder sb = new StringBuilder();
            while (character != -1) {
                sb.append(String.valueOf((char)character));
                character = fr.read();
            }
            String[] entries = sb.toString().split("\n");
            for (int i = 0; i < entries.length; i++) {
                String[] pair = entries[i].split(":");
                flashCards.put(pair[0], pair[1]);
                mistakes.put(pair[0],Integer.valueOf(pair[2]));
            }
            logAndPrint(entries.length + " cards have been loaded.");
        } catch (Exception e) {
            logAndPrint("File not found.");
        }

    }
    
    public void readFromFile(String fileName) {

        try (FileReader fr = new FileReader(fileName)) {

            
            int character = fr.read();
            StringBuilder sb = new StringBuilder();
            while (character != -1) {
                sb.append(String.valueOf((char)character));
                character = fr.read();
            }
            String[] entries = sb.toString().split("\n");
            for (int i = 0; i < entries.length; i++) {
                String[] pair = entries[i].split(":");
                flashCards.put(pair[0], pair[1]);
                mistakes.put(pair[0],Integer.valueOf(pair[2]));
            }
            logAndPrint(entries.length + " cards have been loaded.");
        } catch (Exception e) {
            logAndPrint("File not found.");
        }

    }
    
    public String formattedMap(){
        StringBuilder sb = new StringBuilder();
        Set<String> set = flashCards.keySet();
        for (String card : set) {
            String definition = flashCards.get(card);
            String mistake = String.valueOf(mistakes.get(card));
            sb.append(String.valueOf(card + ":" + definition + ":" + mistake +"\n"));
        }
        return sb.toString();
    }

    //method to write a file
    public void writeToFile() {
        logAndPrint("File name:");
        String fileName = logOnly(in.nextLine());
        try (FileWriter fw = new FileWriter(fileName)) {
            String text = formattedMap();
            fw.write(text);
            logAndPrint(flashCards.size() + " cards have been saved.");
        } catch (Exception e) {
            logAndPrint("Problem writing file");
        }
    }

    public void writeToFile(String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            String text = formattedMap();
            fw.write(text);
            logAndPrint(flashCards.size() + " cards have been saved.");
        } catch (Exception e) {
            logAndPrint("Problem writing file");
        }
    }

    public void askQuestions() {
        logAndPrint("How many times to ask?");
        int n =in.nextInt();
        logOnly(String.valueOf(n));
        in.nextLine();
        List<String>cards = new ArrayList<>(flashCards.keySet());
       
        while (n-- > 0) {
            int size = flashCards.size();
            if (size == 0) {
                logAndPrint("There is no flash cards");
                return;
            }
            int questionNo = random.nextInt(size);
            String card = cards.get(questionNo);
            logAndPrint("Print the definition of \"" +card+ "\":");
            String answer = logOnly(in.nextLine());
            Integer mistakeCount = mistakes.get(card);
            if (answer.equals(flashCards.get(card))) {
                logAndPrint("Correct answer.");
            } else if (anotherDefinition(answer)) {
                mistakes.put(card, ++mistakeCount);
                String correctCardName = getCardForDefinition(answer);
                logAndPrint("Wrong answer. The correct one is \"" + flashCards.get(card) + "\", you've just written the definition of \"" + correctCardName + "\".");
            } else {
                mistakes.put(card, ++mistakeCount);
                logAndPrint("Wrong answer. The correct one is \"" + flashCards.get(card)+ "\".");
            }
        }
    }

    public String formattedLog(){
        StringBuilder sb = new StringBuilder();
        for (String str : logs) {
            sb.append(str + "\n");
        }
        return sb.toString();
    }
    public void log() {

        logAndPrint("File name:");
        String fileName = logOnly(in.nextLine());
        logOnly("The log has been saved.");
        try (FileWriter fw = new FileWriter(fileName)) {
            String text = formattedLog();
            fw.write(text);
            System.out.println("The log has been saved.");
        } catch (Exception e) {
            logAndPrint("Problem writing file");
        }
    }

    public void hardest_card() {
        int max = 0;
        String hardest = "";
        Set<String> cards = mistakes.keySet();
        List<String>hardestCards = new ArrayList<String>();
        for (String card : cards) {
            int mistakeCount = mistakes.get(card);
            if(mistakeCount > max) {
                max = mistakeCount;
                hardest = card;
                hardestCards.clear();
                hardestCards.add(card);
            } else if(mistakeCount == max) {
                hardestCards.add(card);
            }
        }
        
        if(max == 0) {
            logAndPrint("There are no cards with errors.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("The hardest card ");
            if(hardestCards.size() > 1){
                sb.append("are ");
            } else {
                sb.append("is ");
            }
            for (int i = 0; i < hardestCards.size(); i++) {
                if (i == hardestCards.size() -1) {
                    sb.append("\"" + hardestCards.get(i) + "\".");
                } else {
                    sb.append("\"" + hardestCards.get(i) + "\",");
                }
            }
            sb.append(" You have " + max + " errors answering it.");
            logAndPrint(sb.toString());
        }
    }

    public void reset_stats() {
        mistakes.forEach( (k, v) -> mistakes.put(k, 0) );
        logAndPrint("Card statistics has been reset.");
    }
}

 enum FlashCardsActions {
    ADD("add"), REMOVE("remove"), IMPORT("import"), EXPORT("export"), ASK("ask"), EXIT("exit"), LOG("log"), HARDEST_CARD("hardest card"), RESET_STATS("reset stats"), INVALID("invalid");
    String actions;
    FlashCardsActions(String actions) {
        this.actions = actions;
    }
    public String getActions() {
        return this.actions;
    }

    public static FlashCardsActions findByActions(String str) {
        for (FlashCardsActions action : FlashCardsActions.values()) {
            if (action.getActions().equals(str)) {
                return action;
            }
        }
        return FlashCardsActions.INVALID;
    }
}

public class Main {

   

    public static void main(String[] args) {
        String importFile = "";
        String exportFile = "";
        for(int i = 0; i<args.length; i+=2) {
            switch(args[i]) {
                case "-import" :
                importFile = args[ i + 1];
                break;
                case "-export" :
                exportFile = args[ i + 1];
            }

        }
        FlashCards flashCards = FlashCards.getInstance();
        Scanner in  = flashCards.getScanner();
        if(importFile.length() != 0) {
            flashCards.readFromFile(importFile);
        }
        boolean isNotOver = true;
        while (isNotOver) {
            flashCards.logAndPrint("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            FlashCardsActions action = FlashCardsActions.findByActions(flashCards.logOnly(in.nextLine()));
            switch (action) {
                case ADD:
                    flashCards.getCards();
                    break;
                case REMOVE:
                    flashCards.removeCard();
                    break;
                case IMPORT:
                    flashCards.readFromFile();
                    break;
                case EXPORT:
                    flashCards.writeToFile();
                    break;
                case ASK :
                    flashCards.askQuestions();
                    break;
                case LOG :
                    flashCards.log();
                    break;
                case HARDEST_CARD :
                    flashCards.hardest_card();
                    break;
                case RESET_STATS :
                    flashCards.reset_stats();
                    break;
                case EXIT:
                    flashCards.logAndPrint("Bye bye!");
                    if (exportFile.length() != 0) {
                        flashCards.writeToFile(exportFile);
                    }
                    isNotOver = false;
                case INVALID:
            }
            flashCards.logAndPrint("");
        }
                

        
    }
}
