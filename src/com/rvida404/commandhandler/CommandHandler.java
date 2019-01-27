package com.rvida404.commandhandler;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.rvida404.commandhandler.logger.HandlerLogger;

/**
 * Interface exposing the command handling functionality.
 * @author Robert
 */
public interface CommandHandler {

	/**
	 * Handle the command provided and return the response.
	 * @param command the comamnd
	 * @return the response
	 */
	String handle(String command);

	/**
	 * Put a {@link Consumer} as a action with the specified name.
	 * @param key the name of the action
	 * @param value the consumer to be used as action
	 */
	default void putAction(String key, Consumer<List<?>> value) {
		putAction(key, (objects) -> {
			value.accept(objects);
			return "";
		});
	}

	/**
	 * Put a {@link Function} as a action with the specified name.
	 * @param key the name of the action
	 * @param value the function to be used as action
	 */
	void putAction(String key, Function<List<?>, String> value);

	/**
	 * Set the handler logger to be used.
	 * @param logger the logger
	 */
	void setLogger(HandlerLogger logger);
}
