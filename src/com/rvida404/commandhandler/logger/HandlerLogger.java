package com.rvida404.commandhandler.logger;

/**
 * Logger to be used for command parsers.
 * @author Robert
 */
public interface HandlerLogger {

	default void error(String message) {
		log(Level.ERROR, message, null);
	}

	default void error(String message, Throwable throwable) {
		log(Level.ERROR, message, throwable);
	}

	default void warning(String message) {
		log(Level.WARNING, message, null);
	}

	default void warning(String message, Throwable throwable) {
		log(Level.WARNING, message, throwable);
	}

	default void info(String message) {
		log(Level.INFO, message, null);
	}

	default void info(String message, Throwable throwable) {
		log(Level.INFO, message, throwable);
	}

	default void debug(String message) {
		log(Level.INFO, message, null);
	}

	default void debug(String message, Throwable throwable) {
		log(Level.INFO, message, throwable);
	}

	void log(Level level, String message, Throwable e);
}
