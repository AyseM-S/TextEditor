import java.util.Scanner;
import java.util.Stack;

public class TextEditor {

    static Stack<String> undo = new Stack<>(); 
    static Stack<String> redo = new Stack<>();
    
    static StringBuffer text = new StringBuffer();   
    static Scanner yourCommand = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            String command = readCommand();
            commandHandling(command);
        }
    }

    public static void commandHandling(String command) {
        switch (command) {
            case "TYPE":
                char inputChar = yourCommand.next().charAt(0);
                type(inputChar);
                break;
            case "DELETE":
                delete();
                break;
            case "UNDO":
                undoText();
                break;
            case "REDO":
                redoText();
                break;
            case "SHOW":
                show();
                break;
            default:
                System.out.println("Your command is invalid. Valid commands are: TYPE, DELETE, UNDO, REDO, SHOW.");
        }
    }

    private static void type(char inputChar) {
        text.append(inputChar);
        undo.push("TYPE:" + inputChar); 
        redo.clear();
    }
    
    private static void delete() {
        if (text.length() > 0) {
            char lastChar = text.charAt(text.length() - 1);
            text.deleteCharAt(text.length() - 1);
            undo.push("DELETE:" + lastChar); 
            redo.clear();
        }
    }

    private static void undoText() {
        if (!undo.isEmpty()) {
            String lastAction = undo.pop();
            String[] actionParts = lastAction.split(":");
            String actionType = actionParts[0];
            char character = actionParts[1].charAt(0);

            if (actionType.equals("TYPE")) {
                text.deleteCharAt(text.length() - 1);
                redo.push(lastAction); 
            }
            else if (actionType.equals("DELETE")) {
                text.append(character);
                redo.push(lastAction); 
            }
        }
        else {
            System.out.println("Nothing to undo.");
        }
    }

    private static void redoText() {
        if (!redo.isEmpty()) {
            String lastAction = redo.pop();
            String[] actionParts = lastAction.split(":");
            String actionType = actionParts[0];
            char character = actionParts[1].charAt(0);

            if (actionType.equals("TYPE")) {
                text.append(character);
                undo.push(lastAction); 
            }
            else if (actionType.equals("DELETE")) {
                text.deleteCharAt(text.length() - 1);
                undo.push(lastAction); 
            }
        }
        else {
            System.out.println("Nothing to redo.");
        }
    }

    private static void show() {
        System.out.println("Current Text: " + text);
    }

    private static String readCommand() {
        System.out.print("> ");
        return yourCommand.next().toUpperCase();
    }
}
