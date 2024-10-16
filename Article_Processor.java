import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// Main class that handles the processing of the article
public class Article_Processor
{
    public static void main(String[] args)
    {
        // Directory paths for each topic (note that these are now directory paths, not individual file paths)
        String baseballDirectory = "/Users/krismarte/Documents/CPSC2231L/articles/baseballArticle";
        String basketballDirectory = "/Users/krismarte/Documents/CPSC2231L/articles/basketballArticle";
        String footballDirectory = "/Users/krismarte/Documents/CPSC2231L/articles/footballArticle";

        // Create Scanner for user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose a topic (Enter the number corresponding to the topic of the article:");
        System.out.println("1. Baseball");
        System.out.println("2. Basketball");
        System.out.println("3. Football");
        System.out.print("Enter the number corresponding to your topic: ");

        String choice = scanner.next();  // Get user's choice
        String chosenDirectory;

        // Determine the directory path based on user choice
        switch (choice)
        {
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
                System.out.println("Invalid choice! Please restart and choose a valid option.");
                return;  // Exit the program if invalid choice
        }

        // Create instances of helper classes
        String stopWordsFilePath = "/Users/krismarte/Documents/CPSC2231L/stopwords.txt";  // Stop words list
        StopWordsProcessor stopWordsProcessor = new StopWordsProcessor(stopWordsFilePath);
        TextStatistics textStatistics = new TextStatistics(stopWordsProcessor);
        FileReaderUtil fileReaderUtil = new FileReaderUtil(chosenDirectory);  // To fetch all files in directory

        // Get all articles in the chosen directory
        List<File> articles = fileReaderUtil.getArticles();

        // Process each article in the directory
        for (File article : articles)
        {
            System.out.println("Processing file: " + article.getName());
            textStatistics.processArticle(article);
            System.out.println();
        }

        scanner.close();  // Close the scanner
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
class TextStatistics
{
    private final StopWordsProcessor stopWordsProcessor;

    // Constructor that takes a StopWordsProcessor instance
    public TextStatistics(StopWordsProcessor stopWordsProcessor)
    {
        this.stopWordsProcessor = stopWordsProcessor;
    }

    // Method to process an article
    public void processArticle(File file)
    {
        List<String> wordsList = new ArrayList<>();      // List to store words
        List<Integer> frequencies = new ArrayList<>();   // List to store frequencies
        int totalWordCount = 0;      // Total number of words before removing stop words
        int filteredWordCount = 0;   // Number of words after removing stop words
        int sentenceCount = 0;
        List<String> originalWords = new ArrayList<>();   // Store original words
        List<String> filteredWords = new ArrayList<>();   // Store words after removing stop words

        // Try to read the article file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Process each line in the article
            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\W+"); // Split by non-word characters
                totalWordCount += words.length; // Count all words, including stop words
                sentenceCount += line.split("[.!?]").length;

                // Add all original words to the list
                for (String word : words) {
                    if (!word.isEmpty()) {
                        originalWords.add(word); // Store original words

                        // If it's not a stop word, process it further
                        if (!stopWordsProcessor.isStopWord(word)) {
                            filteredWords.add(word);  // Store filtered words
                            filteredWordCount++;  // Count only non-stop words

                            // Check if word already exists in wordsList
                            if (wordsList.contains(word)) {
                                // If word exists, increment its frequency
                                int index = wordsList.indexOf(word);
                                frequencies.set(index, frequencies.get(index) + 1);
                            } else {
                                // If word doesn't exist, add it to wordsList and set frequency to 1
                                wordsList.add(word);
                                frequencies.add(1);
                            }
                        }
                    }
                }
            }

            // Print the original article (before removing stop words)
            System.out.println("Original Article Words (before removing stop words): " + originalWords);
            System.out.println("Total Words (before removing stop words): " + totalWordCount);
            System.out.println();

            // Print the filtered article (after removing stop words)
            System.out.println("Filtered Article Words (after removing stop words): " + filteredWords);
            System.out.println("Total Words (after removing stop words): " + filteredWordCount);
            System.out.println();

            System.out.println("Total Sentences: " + sentenceCount);
            System.out.println();

            System.out.println("Word Frequencies:");

            // Implementing Bubble Sort to sort the words and frequencies in descending order of frequency
            for (int i = 0; i < frequencies.size() - 1; i++)
            {
                for (int j = 0; j < frequencies.size() - i - 1; j++)
                {
                    // Compare adjacent frequencies and swap if needed (descending order)
                    if (frequencies.get(j) < frequencies.get(j + 1))
                    {
                        // Swap frequencies
                        int tempFreq = frequencies.get(j);
                        frequencies.set(j, frequencies.get(j + 1));
                        frequencies.set(j + 1, tempFreq);

                        // Swap corresponding words
                        String tempWord = wordsList.get(j);
                        wordsList.set(j, wordsList.get(j + 1));
                        wordsList.set(j + 1, tempWord);
                    }
                }
            }

            // Display sorted word frequencies
            for (int i = 0; i < wordsList.size(); i++) {
                System.out.println(wordsList.get(i) + ": " + frequencies.get(i));
            }

        } catch (IOException e) {
            System.out.println("Error reading article: " + e.getMessage());
        }
    }
}
