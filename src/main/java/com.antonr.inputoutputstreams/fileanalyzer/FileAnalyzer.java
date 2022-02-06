package com.antonr.inputoutputstreams.fileanalyzer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileAnalyzer {

  // took small on purpose to show that stream read message right
  private static final int INITIAL_BUFFER_SIZE = 3;

  private static final String WORD_SEPARATOR = " ";
  private static final String SENTENCE_SEPARATOR = "(?<=[.!?])";
  private static final String LINE_BREAK = "\n";
  private static final String FILE_ORDERS_CHECK_1_12 = "^[1-9]$|^1[012]$";

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("1.Mary had lamb");
    System.out.println("2.Twinkle little star");
    System.out.println("3.Jack and Jill");
    System.out.println("4.Baa black sheep");
    System.out.println("5.Bed in Summer");
    System.out.println("6.Little Jack Horner");
    System.out.println("7.McGallimagoo");
    System.out.println("8.My cat is fat");
    System.out.println("9.Topsy torvey world");
    System.out.println("10.A mouse in her room");
    System.out.println("11.My dog ate my homework");
    System.out.println("12.Wee Willie Winkie");
    String orderOfTheFile;
    do {
      System.out.println("Please choose just a number of file which you want to use [1-12]: ");
      orderOfTheFile = reader.readLine();
    } while (!orderOfTheFile.matches(FILE_ORDERS_CHECK_1_12));
    System.out.println(
        "Enter word you want to check(just recommend you to use word from \"file name\"):");
    String word = reader.readLine();
    String filePath = Files.getPathByOrder(Integer.parseInt(orderOfTheFile));
    System.out.println("Number of word: " + word + " in file with number: " + orderOfTheFile + " = "
        + numberOfOccurrencesOfCurrentWordInCurrentFile(getAllSentencesInFile(new File(filePath)),
        word));
    System.out.println("All sentences with current word: ");
    allSentencesWhichContainsCurrentWord(getAllSentencesInFile(new File(filePath)), word)
        .forEach(System.out::println);
  }

  private static List<String> getAllSentencesInFile(File file) throws IOException {
    List<String> sentences = new ArrayList<>();
    try (InputStream reader = new BufferedInputStream(new FileInputStream(file))) {
      StringBuilder currentSentence = new StringBuilder();
      byte[] buffer = new byte[INITIAL_BUFFER_SIZE];
      int lengthRead;
      while ((lengthRead = reader.read(buffer)) > 0) {
        byte[] currentBuffer = new byte[lengthRead];
        System.arraycopy(buffer, 0, currentBuffer, 0, lengthRead);
        currentSentence.append(new String(currentBuffer));
        if (containsSentenceSeparator(currentSentence.toString())) {
          sentences.addAll(getCurrentSentences(currentSentence.toString()));
          currentSentence = new StringBuilder(currentSentence
              .substring(getNextIndexAfterSentenceSeparator(currentSentence.toString())));
        }
      }
    }
    return sentences;
  }

  private static List<String> getCurrentSentences(String currentSentence) {
    return Arrays
        .stream(currentSentence.replace(LINE_BREAK, WORD_SEPARATOR).split(SENTENCE_SEPARATOR))
        .collect(Collectors.toList());
  }

  private static boolean containsSentenceSeparator(String currentMessage) {
    return currentMessage.contains(".") || currentMessage.contains("!") || currentMessage
        .contains("?");
  }

  private static int getNextIndexAfterSentenceSeparator(String message) {
    return message.split(SENTENCE_SEPARATOR)[0].length();
  }

  // adequate solution with scanner
  private static List<String> sentencesInFile(File file) throws FileNotFoundException {
    List<String> sentenceList;
    try (Scanner sentence = new Scanner(file)) {
      sentenceList = new ArrayList<>();
      while (sentence.hasNextLine()) {
        sentenceList.add(sentence.nextLine());
      }
    }
    String fileInOneSentence = sentenceList.stream()
        .map(lineSentence -> lineSentence.replace(LINE_BREAK, WORD_SEPARATOR))
        .collect(Collectors.joining(WORD_SEPARATOR));
    return getCurrentSentences(fileInOneSentence);
  }

  private static List<String> allSentencesWhichContainsCurrentWord(List<String> allSentences,
      String currentWord) {
    return allSentences.stream()
        .filter(sentence -> Arrays.asList(sentence.split(WORD_SEPARATOR)).contains(currentWord))
        .collect(Collectors.toList());
  }

  private static int numberOfOccurrencesOfCurrentWordInCurrentFile(List<String> allSentences,
      String currentWord) {
    return (int) allSentences.stream()
        .flatMap(sentence -> Arrays.stream(sentence.split(WORD_SEPARATOR)))
        .filter(currentWord::equals).count();
  }
}
