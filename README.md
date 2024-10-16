# Text Analysis Project - Milestone 1

### Created by Kristopher Marte
- **Major**: Computer Science
- **Year**: Sophomore at Fairfield University
- **Team Members**: 
  - Kristopher Marte
  - Michael Muscara
  - Philip Casey-Leonard

## Project Overview
This project is a Java-based text analysis tool designed to preprocess and analyze articles on a given topic. The application allows users to select a topic (Baseball, Basketball, or Football) and processes multiple articles in the corresponding directory. It removes stop words, calculates basic text statistics, and ranks words by frequency.

### Features:
1. **Stop Words Removal**: The tool removes common stop words such as "and," "the," "is," etc.
2. **Basic Text Statistics**: The program calculates the total number of words, number of sentences, and word frequencies.
3. **Word Frequency Ranking**: The tool ranks and sorts words by their frequency, displaying the results in descending order.

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

2. **Open the Project**:
   - Open the project in your preferred Java IDE (e.g., IntelliJ IDEA or Eclipse) or in a text editor.
   
3. **Add Articles**:
   - Add text articles (`.txt` files) to the corresponding directories: 
     - `baseballArticle` for Baseball articles
     - `basketballArticle` for Basketball articles
     - `footballArticle` for Football articles
     
4. **Run the Program**:
   - Compile and run the `Article_Processor` class, either in your IDE or using the command line:
     ```bash
     javac Article_Processor.java
     java Article_Processor
     ```
     
5. **Select a Topic**:
   - Upon running the program, you will be prompted to select a topic (Baseball, Basketball, or Football) by entering a number corresponding to the topic (1, 2, or 3).
   - The program will then process all articles in the selected directory and display statistics, filtered words, and word frequencies.




## Class Descriptions

### 1. **Article_Processor (Main Class)**:
   - This is the entry point for the program. It allows the user to select a topic (Baseball, Basketball, or Football) using a `Scanner`.
   - Based on the user's selection, it processes all `.txt` files in the corresponding directory by utilizing the `TextStatistics` and `StopWordsProcessor` classes.

### 2. **FileReaderUtil**:
   - This class handles reading all files from the selected directory.
   - It retrieves all `.txt` files from the directory for processing.

### 3. **StopWordsProcessor**:
   - This class handles the removal of stop words from the text.
   - It loads a list of common stop words from a file and filters them out during text processing.

### 4. **TextStatistics**:
   - This class processes each article, removes stop words, calculates basic statistics (word count, sentence count), and ranks words by frequency.
   - It stores original words and filtered words separately and uses Bubble Sort to sort the words by frequency.

## UML Diagram
![UML Diagram](uml-diagram.png)

The UML diagram illustrates the relationships between the main classes in this project: `Article_Processor`, `FileReaderUtil`, `StopWordsProcessor`, and `TextStatistics`. Each class is responsible for a specific task in the article preprocessing workflow.

## Conclusion
This project successfully implements a basic text analysis tool, hitting the Milestone 1 requirements by:
- Preprocessing articles from different topics.
- Removing stop words.
- Calculating basic statistics and ranking word frequencies.

For any questions or issues with running the program, please reach out to Kristopher Marte.
