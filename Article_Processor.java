import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Article_Processor
{
    public static void main(String[] args)
    {
        // Directory paths for each topic (note that these are now directory paths, not individual file paths)
        String baseballDirectory = "/Users/krismarte/Documents/CPSC2231L/articles/baseballArticle";
        String basketballDirectory = "/Users/krismarte/Documents/CPSC2231L/articles/basketballArticle";
        String footballDirectory = "/Users/krismarte/Documents/CPSC2231L/articles/footballArticle";

        // Paths to positive and negative words files
        String positiveWordsFilePath = "/Users/krismarte/Documents/CPSC2231L/positive-words.txt";
        String negativeWordsFilePath = "/Users/krismarte/Documents/CPSC2231L/negative-words.txt";

        // Instantiate SentimentWordsProcessor
        TextStatistics.SentimentWordsProcessor sentimentWordsProcessor = new TextStatistics.SentimentWordsProcessor(positiveWordsFilePath, negativeWordsFilePath);


        // Create Scanner for user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose a topic (Enter the number corresponding to the topic of the article):");
        System.out.println("1. Baseball");
        System.out.println("2. Basketball");
        System.out.println("3. Football");
        System.out.print("Enter the number corresponding to your topic: ");

        String choice = scanner.next();
        String chosenDirectory;

        // Determine the directory path based on user choice
        switch (choice) {
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

        // Ask the user for the minimum frequency threshold
        System.out.print("Enter the minimum frequency of words you want to see: ");
        int frequencyThreshold = scanner.nextInt();

        // Create instances of helper classes
        String stopWordsFilePath = "/Users/krismarte/Documents/CPSC2231L/stopwords.txt";  // Stop words list
        StopWordsProcessor stopWordsProcessor = new StopWordsProcessor(stopWordsFilePath);
        TextStatistics textStatistics = new TextStatistics(stopWordsProcessor, sentimentWordsProcessor);
        FileReaderUtil fileReaderUtil = new FileReaderUtil(chosenDirectory);  // To fetch all files in directory

        // Get all articles in the chosen directory
        List<File> articles = fileReaderUtil.getArticles();

        // Variable to store which article has the richest vocabulary
        File richestVocabularyArticle = null;
        double highestRichness = 0;

        // Process each article in the directory
        for (File article : articles) {
            System.out.println("Processing file: " + article.getName());

            // Calculate vocabulary richness for each article
            double richness = textStatistics.processArticleForUniqueWordCount(article);
            System.out.println("Vocabulary Richness by Unique Word Count for " + article.getName() + ": " + richness);

            // Compare and keep track of the article with the highest richness
            if (richness > highestRichness) {
                richestVocabularyArticle = article;
                highestRichness = richness;
            }

            // Process the article with the frequency threshold
            textStatistics.processArticle(article, frequencyThreshold);
            System.out.println();
        }

        // Display the article with the richest vocabulary
        if (richestVocabularyArticle != null) {
            System.out.println("The article with the richest vocabulary is: " + richestVocabularyArticle.getName() +
                    " with a number of " + highestRichness + " unique words!");
        } else {
            System.out.println("No articles processed.");
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

    // Method to process an article and display words above a frequency threshold
    public void processArticle(File file, int frequencyThreshold) {
        List<String> wordsList = new ArrayList<>();
        List<Integer> frequencies = new ArrayList<>();
        int totalWordCount = 0;
        int filteredWordCount = 0;
        int sentenceCount = 0;
        int positiveCount = 0; // Count positive words
        int negativeCount = 0; // Count negative words

        List<String> originalWords = new ArrayList<>();
        List<String> filteredWords = new ArrayList<>();

        // Try to read the article file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Process each line in the article
            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\W+");
                totalWordCount += words.length; // Count all words, including stop words
                sentenceCount += line.split("[.!?]").length;

                for (String word : words) {
                    if (!word.isEmpty()) {
                        originalWords.add(word);

                        if (!stopWordsProcessor.isStopWord(word)) {
                            filteredWords.add(word);
                            filteredWordCount++;

                            // Check if the word is positive or negative
                            if (sentimentWordsProcessor.isPositiveWord(word)) {
                                positiveCount++;
                            } else if (sentimentWordsProcessor.isNegativeWord(word)) {
                                negativeCount++;
                            }

                            // Check if word already exists in wordsList
                            if (wordsList.contains(word)) {
                                int index = wordsList.indexOf(word);
                                frequencies.set(index, frequencies.get(index) + 1);
                            } else {
                                wordsList.add(word);
                                frequencies.add(1);
                            }
                        }
                    }
                }
            }

            // Print basic statistics
            System.out.println("Total Words (before removing stop words): " + totalWordCount);
            System.out.println("Total Words (after removing stop words): " + filteredWordCount);
            System.out.println("Total Sentences: " + sentenceCount);
            System.out.println("Filtered Article Words (after removing stop words): " + filteredWords);
            System.out.println();

            // Print sentiment analysis
            System.out.println("Positive Words Count: " + positiveCount);
            System.out.println("Negative Words Count: " + negativeCount);
            String sentiment;
            if (positiveCount > negativeCount) {
                sentiment = "Positive";
            } else {
                sentiment = "Negative";
            }            System.out.println("Overall Sentiment: " + sentiment);

            // Sort words by frequency using Bubble Sort
            for (int i = 0; i < frequencies.size() - 1; i++) {
                for (int j = 0; j < frequencies.size() - i - 1; j++) {
                    if (frequencies.get(j) < frequencies.get(j + 1)) {
                        int tempFreq = frequencies.get(j);
                        frequencies.set(j, frequencies.get(j + 1));
                        frequencies.set(j + 1, tempFreq);

                        String tempWord = wordsList.get(j);
                        wordsList.set(j, wordsList.get(j + 1));
                        wordsList.set(j + 1, tempWord);
                    }
                }
            }

            // Display words that meet the frequency threshold
            System.out.println("Word Frequencies (above threshold of " + frequencyThreshold + "):");
            for (int i = 0; i < wordsList.size(); i++)
            {
                if (frequencies.get(i) >= frequencyThreshold)
                {
                    System.out.println(wordsList.get(i) + ": " + frequencies.get(i));
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading article: " + e.getMessage());
        }
    }

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

