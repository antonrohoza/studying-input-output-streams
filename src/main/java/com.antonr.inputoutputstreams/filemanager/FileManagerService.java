package com.antonr.inputoutputstreams.filemanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FileManagerService {

  private static final Logger LOGGER;

  static {
    LOGGER = Logger.getLogger("com.antonr.inputoutputstreams.filemanager.FileManagerService");
    Handler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(Level.ALL);
    LOGGER.addHandler(consoleHandler);
    LOGGER.setLevel(Level.ALL);
    LOGGER.setUseParentHandlers(false);
  }

  private static final int INITIAL_BUFFER_SIZE = 1024;

  static int getNumberOfObjectInDirectoryWithDependentDirectories(int numberOfFiles, String path,
      int originalPathLength, Function<File, Boolean> countFilesOrDirs) {
    if (path.isEmpty()) {
      return numberOfFiles;
    }
    if (path.charAt(path.length() - 1) != '/' && path.length() != originalPathLength) {
      return getNumberOfObjectInDirectoryWithDependentDirectories(numberOfFiles,
          path.substring(0, path.length() - 1), originalPathLength, countFilesOrDirs);
    }
    String pathWithoutSlash = getPathWithoutSlash(path);
    int numberOfFilesInCurrentDir = (int) Arrays.stream(
        Objects.requireNonNull(new File(pathWithoutSlash).listFiles()))
        .filter(countFilesOrDirs::apply)
        .count();
    return getNumberOfObjectInDirectoryWithDependentDirectories(
        numberOfFiles + numberOfFilesInCurrentDir,
        path.substring(0, path.length() - 1), originalPathLength, countFilesOrDirs);
  }

  static void copyElements(File src, File dest) {
    if (src.isDirectory()) {
      creteDirectory(src, dest);
    } else {
      createFile(src, dest);
    }
  }

  static void moveElements(File src, File dest) {
    moveOrCopyAllElements(src, dest, FileManagerService::moveElement);
  }

  private static void moveOrCopyAllElements(File src, File dest,
      BiConsumer<File, File> moveOrCopy) {
    Arrays.stream(Objects.requireNonNull(src.list()))
        .forEach(file -> findNewPathOfElements(src.getPath(), dest.getPath(), file, moveOrCopy));
  }

  private static void moveElement(File src, File dest) {
    if (src.renameTo(dest)) {
      LOGGER.log(Level.FINE,
          "File: " + src.getAbsolutePath() + " was successfully renamed to " + dest
              .getAbsolutePath());
    } else {
      LOGGER.log(Level.WARNING, "Cannot rename file " + src.getAbsolutePath());
    }
  }

  private static void creteDirectory(File src, File dest) {
    if (dest.mkdirs()) {
      LOGGER.log(Level.FINE, "File: " + dest.getAbsolutePath() + " was successfully created!");
    } else {
      LOGGER.log(Level.WARNING, "Cannot create file: " + src.getAbsolutePath());
    }
    moveOrCopyAllElements(src, dest, FileManagerService::copyElements);
  }

  private static void createFile(File src, File dest) {
    try (
        InputStream in = new BufferedInputStream(new FileInputStream(src));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {
      byte[] buffer = new byte[INITIAL_BUFFER_SIZE];
      int lengthRead;
      while ((lengthRead = in.read(buffer)) > 0) {
        out.write(buffer, 0, lengthRead);
      }
    } catch (IOException e) {
      throw new RuntimeException("File cannot be created");
    }
  }

  private static void findNewPathOfElements(String from, String to, String file,
      BiConsumer<File, File> moveOrCopy) {
    File srcFile = new File(from, file);
    File destFile = new File(to, file);
    moveOrCopy.accept(srcFile, destFile);
  }

  private static String getPathWithoutSlash(String path) {
    return path.charAt(path.length() - 1) == '/' ? path.substring(0, path.length() - 1)
        : path;
  }

}
