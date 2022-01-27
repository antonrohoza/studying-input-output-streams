package com.antonr.inputoutputstreams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FileManagerTest {

  @Test
  void countFiles() {
    // file*D: M - main, S - secondary, T - third level
    String path = "fileMD/fileSD/fileTD";
    assertEquals(6, FileManager.countFiles(path));
  }

  @Test
  void countDirs() {
    String path = "fileMD/fileSD2/fileTD";
    assertEquals(5, FileManager.countDirs(path));
  }

}