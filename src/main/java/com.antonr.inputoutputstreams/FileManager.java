package com.antonr.inputoutputstreams;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class FileManager {

  public static int countFiles(String path) {

    return getNumberOfObjectInDirectoryWithDependent(0, path, path.length(), File::isFile);
  }

  public static int countDirs(String path) {
    return getNumberOfObjectInDirectoryWithDependent(0, path, path.length(), File::isDirectory);
  }

  public static void copy(String from, String to) {

  }

  public static void move(String from, String to) {

  }

  private static int getNumberOfObjectInDirectoryWithDependent(int numberOfFiles, String path,
      int originalPathLength, Function<File, Boolean> operator) {
    if (path.isEmpty()) {
      return numberOfFiles;
    }
    if (path.charAt(path.length() - 1) != '/' && path.length() != originalPathLength) {
      return getNumberOfObjectInDirectoryWithDependent(numberOfFiles,
          path.substring(0, path.length() - 1), originalPathLength, operator);
    }
    String pathWithoutSlash = getPathWithoutSlash(path);
    int numberOfFilesInCurrentDir = (int) Arrays.stream(
        Objects.requireNonNull(new File(pathWithoutSlash).listFiles()))
        .filter(operator::apply)
        .count();
    return getNumberOfObjectInDirectoryWithDependent(numberOfFiles + numberOfFilesInCurrentDir,
        path.substring(0, path.length() - 1), originalPathLength, operator);
  }

  private static String getPathWithoutSlash(String path) {
    return path.charAt(path.length() - 1) == '/' ? path.substring(0, path.length() - 1)
        : path;
  }

}
