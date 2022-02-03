package com.antonr.inputoutputstreams.filemanager;

import java.io.File;

public class FileManager {

  public static int countFiles(String path) {
    return FileManagerService.getNumberOfObjectInDirectoryWithDependentDirectories(0, path, path.length(),
        File::isFile);
  }

  public static int countDirs(String path) {
    return FileManagerService.getNumberOfObjectInDirectoryWithDependentDirectories(0, path, path.length(),
        File::isDirectory);
  }

  public static void copy(String from, String to) {
    FileManagerService.copyElements(new File(from), new File(to));
  }

  public static void move(String from, String to) {
    FileManagerService.moveElements(new File(from), new File(to));
  }

}
