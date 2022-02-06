package com.antonr.inputoutputstreams.fileanalyzer;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Files {
  ONE(1, "src/main/resources/fileMD/fileSD/fileTD/maryHadLamb.txt"),
  TWO(2, "src/main/resources/fileMD/fileSD/fileTD/twinkleLittleStar.txt"),
  THREE(3, "src/main/resources/fileMD/fileSD/fileTD2/jackAndJill.txt"),
  FOUR(4, "src/main/resources/fileMD/fileSD/fileTD3/baaBlackSheep.txt"),
  FIVE(5, "src/main/resources/fileMD/fileSD/bedInSummer.txt"),
  SIX(6, "src/main/resources/fileMD/fileSD/littleJackHorner.txt"),
  SEVEN(7, "src/main/resources/fileMD/fileSD2/fileTD/mcGallimagoo.txt"),
  EIGHT(8, "src/main/resources/fileMD/fileSD2/fileTD2/MyCatIsFat.txt"),
  NINE(9, "src/main/resources/fileMD/fileSD2/topsyTorveyWorld.txt"),
  TEN(10, "src/main/resources/fileMD/fileSD3/fileTD2/aMouseInHerRoom.txt"),
  ELEVEN(11, "src/main/resources/fileMD/myDogAteMyHomework.txt"),
  TWELVE(12, "src/main/resources/fileMD/WeeWillieWinkie.txt");

  private final int order;
  private final String path;

  public static String getPathByOrder(int order) {
    return Arrays.stream(Files.values())
        .filter(file -> file.getOrder() == order)
        .map(Files::getPath).findFirst()
        .orElseThrow(() -> new RuntimeException("There is no file with order: " + order));
  }
}
