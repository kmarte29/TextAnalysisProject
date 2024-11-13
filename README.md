# Text Analysis Project - Updated Milestone 2

### Created by Kristopher Marte
- **Major**: Computer Science
- **Year**: Sophomore at Fairfield University
- **Team Members**: 
  - Kristopher Marte
  - Michael Muscara
  - Philip Casey-Leonard

## Project Overview
This project is a Java-based text analysis tool designed to preprocess and analyze articles on a given topic. The application allows users to select a topic (Baseball, Basketball, or Football) and processes multiple articles in the corresponding directory. It removes stop words, calculates basic text statistics, ranks words by frequency, and performs sentiment analysis.

### Features:
1. **Stop Words Removal**: The tool removes common stop words such as "and," "the," "is," etc.
2. **Basic Text Statistics**: The program calculates the total number of words, number of sentences, and word frequencies.
3. **Word Frequency Ranking**: The tool ranks and sorts words by their frequency, displaying the results in descending order.
4. **Vocabulary Richness Analysis**: Determines the article with the richest vocabulary based on the count of unique words.
5. **Frequent Words Filtering**: Allows the user to specify a minimum frequency threshold to display the most repeated words.
6. **Sentiment Analysis**: Analyzes the sentiment of each article using positive and negative word lists, and provides an overall sentiment rating.

## How to Run the Program

### Prerequisites:
- Java Development Kit (JDK) version 8 or higher installed.
- A text editor or IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code).
- Basic knowledge of running Java programs in the command line or IDE.

### Running the Program:
1. **Download the Code**:
   - Clone or download the GitHub repository containing the project files.
     ```bash
     git clone https://github.com/your-username/TextAnalysisProject.git
     ```

2. **Article Files**:
   - The project already includes all necessary `.txt` article files in the repository. You can find the articles in the following directories:
     - `baseballArticle/` contains `.txt` files for Baseball-related articles.
     - `basketballArticle/` contains `.txt` files for Basketball-related articles.
     - `footballArticle/` contains `.txt` files for Football-related articles.

   The articles are automatically processed when you run the program.

3. **Stop Words and Sentiment Files**:
   - The stop words text file (`stopwords.txt`) is included in the repository.
   - Sentiment analysis uses two files: `positive-words.txt` and `negative-words.txt`, which are also included.

4. **Run the Program**:
   - Compile and run the `Article_Processor` class, either in your IDE or using the command line:
     ```bash
     javac Article_Processor.java
     java Article_Processor
     ```

5. **Select a Topic**:
   - Upon running the program, you will be prompted to select a topic (Baseball, Basketball, or Football) by entering a number (1, 2, or 3).
   - The program will then process all articles in the selected directory and display statistics, filtered words, word frequencies, and sentiment analysis.

6. **Specify Frequency Threshold**:
   - You will be prompted to enter a minimum frequency threshold. The program will display words that appear at least this many times in the articles.

## Class Descriptions

### 1. **Article_Processor (Main Class)**:
   - This is the entry point for the program. It allows the user to select a topic, specify a frequency threshold, and process all `.txt` files in the chosen directory.
   - It handles vocabulary richness analysis and sentiment analysis for each article.

### 2. **FileReaderUtil**:
   - This class reads all `.txt` files from the selected directory and returns a list of files for further processing.

### 3. **StopWordsProcessor**:
   - This class removes common stop words from the text using a predefined list from `stopwords.txt`.

### 4. **TextStatistics**:
   - This class processes each article to calculate statistics, rank word frequencies, and perform sentiment analysis.
   - It includes methods for:
     - **Vocabulary Richness Analysis**: Identifying the article with the richest vocabulary.
     - **Frequent Words Filtering**: Displaying words based on a specified frequency threshold.
     - **Sentiment Analysis**: Analyzing positive and negative sentiment using `positive-words.txt` and `negative-words.txt`.

### 5. **SentimentWordsProcessor**:
   - This inner class handles loading and checking positive and negative words.
   - It provides methods to determine if a word is positive or negative based on the sentiment word lists.

## UML Diagram
![UML Diagram](https://github.com/kmarte29/TextAnalysisProject/blob/main/UML%20Diagram%20Milestone%201.png)

The UML diagram illustrates the relationships between the main classes: `Article_Processor`, `FileReaderUtil`, `StopWordsProcessor`, `TextStatistics`, and `SentimentWordsProcessor`.

## Milestone 2 Additions
The following enhancements were implemented as part of Milestone 2:
1. **Vocabulary Richness Analysis**:
   - Determines which article has the richest vocabulary by counting unique words.
   - The article with the highest unique word count is reported as the one with the richest vocabulary.

2. **Frequent Words Filtering**:
   - The user can specify a minimum frequency threshold, and the program will display words that meet or exceed this threshold.

3. **Sentiment Analysis**:
   - The program analyzes the sentiment of each article using `positive-words.txt` and `negative-words.txt`.
   - It counts the occurrences of positive and negative words and provides an overall sentiment rating (Positive or Negative) based on the counts.

## Conclusion
This project successfully implements a comprehensive text analysis tool, meeting the requirements for Milestones 1 and 2 by:
- Preprocessing and analyzing articles on different topics.
- Removing stop words using `stopwords.txt`.
- Ranking word frequencies and performing vocabulary richness analysis.
- Analyzing sentiment using positive and negative word lists.

For any questions or issues with running the program, please reach out to kristopher.marte@student.fairfield.edu
