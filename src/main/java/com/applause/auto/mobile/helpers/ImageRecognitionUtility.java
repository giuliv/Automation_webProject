package com.applause.auto.mobile.helpers;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sikuli.basics.Settings;
import org.sikuli.script.Finder;
import org.sikuli.script.Match;

public class ImageRecognitionUtility {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  private static final double DEFAULT_MIN_SIMILARITY = 0.7;

  static {
    Settings.OcrTextSearch = true;
    Settings.OcrTextRead = true;
  }

  /**
   * getCoords returns the coordinates of the FIRST element that matches the specified
   *
   * @param actual is the screenshot of the device
   * @param expectedImagePath is the image of the element that you want to find
   * @return coordinates of the centre of the element found as Point2D
   */
  public static Point2D getCoords(
      BufferedImage actual, String expectedImagePath, int findOrder, double similarity) {
    Settings.MinSimilarity = similarity;
    Point2D coords = new Point2D.Double(-1, -1);
    try {
      Finder finder = new Finder(actual);

      finder.find(expectedImagePath);
      int currentFindOrder = 0;
      boolean hasNext = finder.hasNext();
      logger.info("Image recognition hasNext: [" + hasNext + "]");
      while (finder.hasNext()) {
        currentFindOrder++;
        Match m = finder.next();
        String currentMatch = finder.toString();
        logger.info("Current match for image recognition [" + currentMatch + "]");
        if (currentFindOrder == findOrder) {
          coords.setLocation(m.getTarget().getX(), m.getTarget().getY());
          break;
        }
        // m = f.next();
      }

      double foundXCoords = coords.getX();
      double foundYCoords = coords.getY();
      logger.info("Found similar image: " + expectedImagePath + " coordinate X: " + foundXCoords);
      logger.info("Found similar image: " + expectedImagePath + " coordinate Y: " + foundYCoords);

      return coords;
    } catch (Throwable e) {
      e.getCause().printStackTrace();
      logger.error("After printing e.getCause().printStackTrace()");
    } finally {
      Settings.MinSimilarity = DEFAULT_MIN_SIMILARITY;
    }

    return coords;
  }
}
