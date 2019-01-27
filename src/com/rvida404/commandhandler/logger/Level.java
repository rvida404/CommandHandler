package com.rvida404.commandhandler.logger;

public enum Level {
	ERROR(1), WARNING(2), INFO(3), DEBUG(4);

	int value;

	private Level(int value) {
		this.value = value;
	}
}
