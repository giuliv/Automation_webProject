package com.applause.auto.common.enums;

public enum Direction {
	RIGHT("right"),
	LEFT("left"),
	UP("up"),
	DOWN("down");

	private String direction;

	Direction(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}
}
