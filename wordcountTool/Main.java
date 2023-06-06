import java.io.*;
import java.util.Scanner;

class WordcountTools {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the content:");

        String content = scanner.nextLine();
        String filename = "wordcount.txt";

        // Save the content to a file
        saveContentToFile(content, filename);

        // Read the content from the file
        String fileContent = readContentFromFile(filename);

        // Calculate the word count
        int wordCount = calculateWordCount(fileContent);

        System.out.println("Total words: " + wordCount);
    }

    private static void saveContentToFile(String content, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(content);
            writer.close();
            System.out.println("Content saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the content to the file.");
            e.printStackTrace();
        }
    }

    private static String readContentFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return content.toString();
    }

    private static int calculateWordCount(String content) {
        String[] words = content.split("\\s+");
        return words.length;
    }
}
