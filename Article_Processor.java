import java.io.*;
import java.util.*;

public class Article_Processor {
    public static void main(String[] args) {
        // Directory paths for each topic
        String baseDirectory = "/Users/krismarte/Documents/CPSC2231L/articles/";
        String baseballDirectory = baseDirectory + "baseballArticle";
        String basketballDirectory = baseDirectory + "basketballArticle";
        String footballDirectory = baseDirectory + "footballArticle";

        // Validate base directory
        File baseDir = new File(baseDirectory);
        if (!baseDir.exists()) {
            System.out.println("Base directory does not exist. Please check the path.");
            return;
        }

        // Paths to positive and negative words files
        String positiveWordsFilePath = "/Users/krismarte/Documents/CPSC2231L/positive-words.txt";
        String negativeWordsFilePath = "/Users/krismarte/Documents/CPSC2231L/negative-words.txt";

        // Instantiate SentimentWordsProcessor and StopWordsProcessor
        TextStatistics.SentimentWordsProcessor sentimentWordsProcessor = new TextStatistics.SentimentWordsProcessor(positiveWordsFilePath, negativeWordsFilePath);
        StopWordsProcessor stopWordsProcessor = new StopWordsProcessor("/Users/krismarte/Documents/CPSC2231L/stopwords.txt");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\nWelcome to the Text Analysis Tool!");
            System.out.println("Please choose an option:");
            System.out.println("1. Create a new folder");
            System.out.println("2. Add a file to an existing folder");
            System.out.println("3. Analyze articles in a folder");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // Validate numeric input for main menu
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                continue;
            }

            switch (choice) {
                case 1: // Create a new folder
                    System.out.print("Enter the name of the new folder: ");
                    String folderName = scanner.nextLine().trim();
                    File newFolder = new File(baseDirectory + folderName);
                    if (newFolder.mkdirs()) {
                        System.out.println("Folder '" + folderName + "' created successfully.");
                    } else {
                        System.out.println("Failed to create folder. It may already exist.");
                    }
                    break;

                case 2: // Add a file to an existing folder
                    System.out.print("Enter the name of the folder to add the file to: ");
                    String targetFolderName = scanner.nextLine().trim();
                    File targetFolder = new File(baseDirectory + targetFolderName);
                    if (targetFolder.exists() && targetFolder.isDirectory()) {
                        System.out.print("Enter the name of the new file (with .txt extension): ");
                        String fileName = scanner.nextLine().trim();
                        File newFile = new File(targetFolder, fileName);
                        try {
                            if (newFile.createNewFile()) {
                                System.out.println("File '" + fileName + "' created successfully in folder '" + targetFolderName + "'.");
                                System.out.println("You can now add content to the file.");
                                System.out.println("Enter the content (type 'DONE' on a new line to finish):");
                                try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
                                    String line;
                                    while (!(line = scanner.nextLine()).equalsIgnoreCase("DONE")) {
                                        writer.write(line);
                                        writer.newLine();
                                    }
                                    System.out.println("Content written to file successfully.");
                                }
                            } else {
                                System.out.println("File already exists in the folder.");
                            }
                        } catch (IOException e) {
                            System.out.println("Error creating file: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Folder '" + targetFolderName + "' does not exist.");
                    }
                    break;

                case 3: // Analyze articles in a folder
                    System.out.println("Select a topic to analyze:");
                    System.out.println("1. Baseball");
                    System.out.println("2. Basketball");
                    System.out.println("3. Football");
                    System.out.print("Enter your choice: ");
                    String topicChoice = scanner.nextLine().trim();
                    String chosenDirectory;

                    switch (topicChoice) {
                        case "1":
                            chosenDirectory = baseballDirectory;
                            break;
                        case "2":
                            chosenDirectory = basketballDirectory;
                            break;
                        case "3":
                            chosenDirectory = footballDirectory;
                            break;
                        default:
                            System.out.println("Invalid choice. Returning to main menu.");
                            continue;
                    }

                    FileReaderUtil fileReaderUtil = new FileReaderUtil(chosenDirectory);
                    TextStatistics textStatistics = new TextStatistics(stopWordsProcessor, sentimentWordsProcessor);
                    List<File> articles = fileReaderUtil.getArticles();

                    if (articles.isEmpty()) {
                        System.out.println("No articles found in the selected topic.");
                    } else {
                        int articleNumber = 1;
                        for (File article : articles) {
                            System.out.println("\nArticle " + articleNumber + ": " + article.getName());
                            articleNumber++;

                            // Process the article and display required data
                            int totalWordsBefore = textStatistics.getTotalWordsBeforeStopWords(article);
                            int totalWordsAfter = textStatistics.getTotalWordsAfterStopWords(article);
                            int positiveCount = textStatistics.getPositiveWordCount(article);
                            int negativeCount = textStatistics.getNegativeWordCount(article);
                            String overallSentiment = positiveCount > negativeCount ? "Positive" : "Negative";
                            double vocabularyRichness = textStatistics.processArticleForUniqueWordCount(article);

                            System.out.println("Total Words (Before Removing Stop Words): " + totalWordsBefore);
                            System.out.println("Total Words (After Removing Stop Words): " + totalWordsAfter);
                            System.out.println("Positive Count: " + positiveCount);
                            System.out.println("Negative Count: " + negativeCount);
                            System.out.println("Overall Sentiment: " + overallSentiment);
                            System.out.println("Vocabulary Richness: " + vocabularyRichness);
                        }
                    }
                    break;

                case 4: // Exit
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}


// Class 1 to read articles from a directory
class FileReaderUtil
{
    private final String directoryPath;

    // Constructor that takes the directory path
    public FileReaderUtil(String directoryPath)
    {
        this.directoryPath = directoryPath;
    }

    // Method to get a list of all article files in the directory
    public List<File> getArticles()
    {
        List<File> articleFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        // Check if the directory exists and is a directory
        if (directory.exists() && directory.isDirectory())
        {
            // Get all the files in the directory
            File[] files = directory.listFiles();
            if (files != null) {
                // Only include text files
                for (File file : files)
                {
                    if (file.isFile() && file.getName().endsWith(".txt"))
                    {
                        articleFiles.add(file);
                    }
                }
            }
        }
        else
        {
            System.out.println("Directory not found: " + directoryPath);
        }

        return articleFiles;  // Return the list of article files
    }
}

// Class 2 to handle the processing of stop words
class StopWordsProcessor
{
    private final Set<String> stopWords;

    // Constructor that loads stop words from a file
    public StopWordsProcessor(String stopWordsFilePath)
    {
        this.stopWords = new HashSet<>();
        loadStopWords(stopWordsFilePath);
    }

    // Method to load stop words from a file
    private void loadStopWords(String stopWordsFilePath)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFilePath)))
        {
            String word;
            while ((word = br.readLine()) != null)
            {
                stopWords.add(word.trim().toLowerCase());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading stop words file: " + e.getMessage());
        }
    }

    // Method to check if a word is a stop word
    public boolean isStopWord(String word)
    {
        return stopWords.contains(word.toLowerCase());
    }
}


// Class 3 to process the article text and calculate statistics
class TextStatistics {
    private final StopWordsProcessor stopWordsProcessor;
    private final SentimentWordsProcessor sentimentWordsProcessor;

    public TextStatistics(StopWordsProcessor stopWordsProcessor, SentimentWordsProcessor sentimentWordsProcessor) {
        this.stopWordsProcessor = stopWordsProcessor;
        this.sentimentWordsProcessor = sentimentWordsProcessor;

    }

    // Method to process an article and return the number of unique words using streams
    public int processArticleForUniqueWordCount(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read all lines, split into words, filter, and collect unique words
            long uniqueWordCount = br.lines() // Stream each line
                    .flatMap(line -> Arrays.stream(line.toLowerCase().split("\\W+"))) // Split each line into words
                    .filter(word -> !word.isEmpty() && !stopWordsProcessor.isStopWord(word)) // Filter out stop words and empty words
                    .distinct() // Keep only unique words
                    .count(); // Count unique words

            return (int) uniqueWordCount;

        } catch (IOException e) {
            System.out.println("Error reading article: " + e.getMessage());
        }
        return 0;
    }

    public int getTotalWordsBeforeStopWords(File file) {
        int totalWords = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalWords += line.split("\\W+").length;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return totalWords;
    }

    public int getTotalWordsAfterStopWords(File file) {
        int totalWords = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalWords += (int) Arrays.stream(line.split("\\W+"))
                        .filter(word -> !stopWordsProcessor.isStopWord(word))
                        .count();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return totalWords;
    }

    public int getPositiveWordCount(File file) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                count += (int) Arrays.stream(line.split("\\W+"))
                        .filter(sentimentWordsProcessor::isPositiveWord)
                        .count();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return count;
    }

    public int getNegativeWordCount(File file) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                count += (int) Arrays.stream(line.split("\\W+"))
                        .filter(sentimentWordsProcessor::isNegativeWord)
                        .count();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return count;
    }

    //class 3 for processing positive/negative words
    static class SentimentWordsProcessor
    {
        private final Set<String> positiveWords;
        private final Set<String> negativeWords;

        // Constructor that loads positive and negative words from files
        public SentimentWordsProcessor(String positiveWordsFilePath, String negativeWordsFilePath) {
            this.positiveWords = new HashSet<>();
            this.negativeWords = new HashSet<>();
            loadWords(positiveWordsFilePath, positiveWords);
            loadWords(negativeWordsFilePath, negativeWords);
        }

        // Method to load words from a file into a set
        private void loadWords(String filePath, Set<String> wordSet) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String word;
                while ((word = br.readLine()) != null) {
                    wordSet.add(word.trim().toLowerCase());
                }
            } catch (IOException e) {
                System.out.println("Error reading sentiment words file: " + e.getMessage());
            }
        }

        // Check if a word is positive
        public boolean isPositiveWord(String word) {
            return positiveWords.contains(word.toLowerCase());
        }

        // Check if a word is negative
        public boolean isNegativeWord(String word) {
            return negativeWords.contains(word.toLowerCase());
        }
    }

}
