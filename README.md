# Text Analysis Project - Milestone 3

### Created by Kristopher Marte
- **Major**: Computer Science
- **Year**: Sophomore at Fairfield University
- **Team Members**: 
  - Kristopher Marte
  - Michael Muscara
  - Philip Casey-Leonard

---

## Project Overview
This project is a Java-based text analysis tool designed to allow users to preprocess, analyze, and manage articles. The tool features an interactive, text-based user interface that enables folder management, article content addition, and advanced text analysis, including sentiment evaluation and vocabulary richness assessment.

---

### Features
1. **Folder Management**:
   - Create new folders dynamically within the base directory to organize articles.
   - Add `.txt` files to existing folders with custom content.

2. **Article Analysis**:
   - Analyze articles by selecting a topic (Baseball, Basketball, or Football).
   - Provides detailed output for each article, including:
     - Total words before and after removing stop words.
     - Count of positive and negative words.
     - Overall sentiment (Positive or Negative).
     - Vocabulary richness (unique word count).

3. **Sentiment Analysis**:
   - Uses `positive-words.txt` and `negative-words.txt` to assess the sentiment of articles.
   - Determines the overall sentiment as Positive, Negative, or Neutral based on word counts.

4. **Improved User Experience**:
   - Features a user-friendly menu-driven interface.
   - Includes robust input validation and error handling for seamless use.

---

### Milestone 3 Enhancements
1. **Folder Creation**:
   - Users can dynamically create folders to organize articles by topics or categories.

2. **File Addition**:
   - Users can create new `.txt` files and add custom content through the program.

3. **Streamlined Analysis**:
   - Improved output for article statistics, including detailed sentiment analysis and vocabulary richness evaluation.

4. **Enhanced Error Handling**:
   - Validates user inputs for directory paths, menu options, and text entry.
   - Notifies users of invalid actions or missing files.

---

### Prerequisites
- Java Development Kit (JDK) version 8 or higher installed.
- A text editor or IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code).
- Basic knowledge of running Java programs in the command line or IDE.

---

### Running the Program
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/kmarte29/TextAnalysisProject.git
   cd TextAnalysisProject

2. **Prepare the Environment**:
   - Ensure the base directory (/Users/krismarte/Documents/CPSC2231L/articles/) exists with subfolders:
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

### Menu Options

1. **Create a New Folder**:
   - Organize articles by creating a new folder in the base directory.
   - Example Workflow:
     - User is prompted to enter the name of the new folder.
     - The program validates if the folder already exists.
     - If the folder doesn’t exist, it creates the folder and notifies the user.

2. **Add a File**:
   - Add a `.txt` file to an existing folder and populate it with custom content.
   - Example Workflow:
     - User is prompted to enter the name of the folder where the file will be added.
     - The program validates the existence of the folder.
     - User enters the name of the new `.txt` file.
     - The program creates the file and allows the user to input content, ending with the keyword `DONE`.

3. **Analyze Articles**:
   - Select a topic (Baseball, Basketball, or Football) to analyze articles within the corresponding folder.
   - Outputs include:
     - **Total Words**:
       - Number of words before and after removing stop words.
     - **Sentiment Analysis**:
       - Positive word count.
       - Negative word count.
       - Overall sentiment classification (Positive/Negative/Neutral).
     - **Vocabulary Richness**:
       - Ratio of unique words to total words.
   - Example Workflow:
     - User selects a topic (e.g., Baseball).
     - The program processes all `.txt` files in the topic's folder.
     - For each article, statistics and analysis are displayed in a structured format.

4. **Exit**:
   - Exits the program gracefully.

---

### Example Output

**Sample Analysis for Baseball Articles**:
```text
Welcome to the Text Analysis Tool!
Please choose an option:
1. Create a new folder
2. Add a file to an existing folder
3. Analyze articles in a folder
4. Exit
Enter your choice: 1
Enter the name of the new folder: Test1
Folder 'Test1' created successfully.

Welcome to the Text Analysis Tool!
Please choose an option:
1. Create a new folder
2. Add a file to an existing folder
3. Analyze articles in a folder
4. Exit
Enter your choice: 2
Enter the name of the folder to add the file to: Test.txt
Folder 'Test.txt' does not exist.

Welcome to the Text Analysis Tool!
Please choose an option:
1. Create a new folder
2. Add a file to an existing folder
3. Analyze articles in a folder
4. Exit
Enter your choice: 3
Select a topic to analyze:
1. Baseball
2. Basketball
3. Football
Enter your choice: 1

Article 1: Article1a.txt
Total Words (Before Removing Stop Words): 2320
Total Words (After Removing Stop Words): 1358
Positive Count: 69
Negative Count: 54
Overall Sentiment: Positive
Vocabulary Richness: 729.0

Article 2: Article1b.txt
Total Words (Before Removing Stop Words): 1940
Total Words (After Removing Stop Words): 1087
Positive Count: 70
Negative Count: 64
Overall Sentiment: Positive
Vocabulary Richness: 546.0

Article 3: Article1c.txt
Total Words (Before Removing Stop Words): 762
Total Words (After Removing Stop Words): 487
Positive Count: 28
Negative Count: 27
Overall Sentiment: Positive
Vocabulary Richness: 295.0

Welcome to the Text Analysis Tool!
Please choose an option:
1. Create a new folder
2. Add a file to an existing folder
3. Analyze articles in a folder
4. Exit
Enter your choice: 4
Exiting the program. Goodbye!
```

### Class Descriptions

#### 1. **Article_Processor (Main Class)**:
   - Entry point of the program.
   - Manages user interaction through a menu-driven interface.
   - Handles folder creation, file addition, and article analysis.

#### 2. **FileReaderUtil**:
   - Retrieves `.txt` files from the specified folder.
   - Ensures only valid files are included in the analysis.

#### 3. **StopWordsProcessor**:
   - Filters out common stop words using a predefined list from `stopwords.txt`.

#### 4. **TextStatistics**:
   - Processes articles to compute detailed statistics:
     - Vocabulary Richness: Unique-to-total word ratio.
     - Positive and Negative Word Counts: Based on predefined sentiment lists.
     - Total Word Counts: Before and after removing stop words.

#### 5. **SentimentWordsProcessor**:
   - Loads and manages positive and negative word lists from external files.
   - Provides methods to classify words and compute sentiment scores.

---

### UML Diagram

![UML Diagram](https://github.com/kmarte29/TextAnalysisProject/blob/main/UML%20Diagram%20Milestone%203.png)

The UML diagram illustrates the relationships between classes:
- `Article_Processor` (Main Class)
- `FileReaderUtil` (File Management)
- `StopWordsProcessor` (Text Filtering)
- `TextStatistics` (Article Analysis)
- `SentimentWordsProcessor` (Sentiment Classification)
## UML Diagram
![UML Diagram](https://github.com/kmarte29/TextAnalysisProject/blob/main/UML%20Diagram%20Milestone%202.png)

The UML diagram illustrates the relationships between the main classes: `Article_Processor`, `FileReaderUtil`, `StopWordsProcessor`, `TextStatistics`, and `SentimentWordsProcessor`.

## Example Output

Below is an example of the program's output when processing articles from one of the topics (Baseball, Basketball, or Football). The output includes:

1. **Vocabulary Richness**: Shows the unique word count for each article.
2. **Word Frequencies**: Displays a list of words that meet the specified frequency threshold set by the user.
3. **Sentiment Analysis**: Indicates whether the overall sentiment of the article is positive or negative based on the count of positive and negative words.

---

### Conclusion

This project successfully achieves the objectives outlined for **Milestone 3**, introducing enhanced features and improved usability:

1. **Folder and File Management**:
   - Users can dynamically create folders for better organization of articles by topics or categories.
   - The program allows seamless addition and editing of text files directly through the interface.

2. **Comprehensive Article Analysis**:
   - Provides detailed text statistics for articles in selected topics, including:
     - Word counts before and after removing stop words.
     - Sentiment analysis with positive and negative word counts.
     - Overall sentiment classification (Positive, Negative, or Neutral).
     - Vocabulary richness for insights into unique word usage.

3. **Interactive User Experience**:
   - The menu-driven interface ensures ease of navigation and a straightforward workflow for users.
   - Robust error handling prevents invalid inputs and ensures smooth execution of commands.

4. **Sentiment Analysis and Vocabulary Richness**:
   - Sentiment analysis leverages predefined word lists (`positive-words.txt` and `negative-words.txt`) to determine article sentiment.
   - Vocabulary richness provides insights into the diversity of language used in each article.

---

This milestone solidifies the project as a comprehensive, user-friendly text analysis tool suitable for educational and practical applications. Future developments may include additional analytical features, graphical user interface (GUI) integration, and API support for real-time data processing.

For any questions, suggestions, or issues, feel free to contact **Kristopher Marte** at **kristopher.marte@student.fairfield.edu**.
