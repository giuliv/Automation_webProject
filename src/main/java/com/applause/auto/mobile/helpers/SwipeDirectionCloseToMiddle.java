package com.applause.auto.mobile.helpers;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.Point;

public enum SwipeDirectionCloseToMiddle {
  UP,
  DOWN,
  LEFT,
  RIGHT;

  private SwipeDirectionCloseToMiddle() {}

  /**
   * Gets swipe vector.
   *
   * @param width the width
   * @param height the height
   * @return the swipe vector
   */
  public Pair<Point, Point> getSwipeVector(int width, int height) {
    Point start;
    Point end;
    switch (this) {
      case UP:
        start = new Point(width / 2, (int) ((double) height * 0.7D));
        end = new Point(width / 2, (int) ((double) height * 0.3D));
        break;
      case DOWN:
        start = new Point(width / 2, (int) ((double) height * 0.3D));
        end = new Point(width / 2, (int) ((double) height * 0.7D));
        break;
      case LEFT:
        start = new Point((int) ((double) width * 0.7D), height / 2);
        end = new Point((int) ((double) width * 0.3D), height / 2);
        break;
      case RIGHT:
        start = new Point((int) ((double) width * 0.3D), height / 2);
        end = new Point((int) ((double) width * 0.7D), height / 2);
        break;
      default:
        throw new IllegalArgumentException("Invalid SwipeDirection value specified, somehow.");
    }

    return Pair.of(start, end);
  }
}
