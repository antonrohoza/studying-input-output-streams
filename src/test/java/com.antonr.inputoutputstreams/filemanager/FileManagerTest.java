package com.antonr.inputoutputstreams.filemanager;

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

  @Test
  void copy() {
    String src = "fileMD/fileSD";
    String to = "fileMD/fileSD3/fileTD3";
    FileManager.copy(src, to);
  }

  @Test
  void move() {
    String src = "fileMD/fileSD/fileTD";
    String to = "fileMD/fileSD3/fileTD";
    FileManager.move(to, src);
  }

}