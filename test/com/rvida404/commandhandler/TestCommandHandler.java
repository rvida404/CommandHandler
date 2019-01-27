package com.rvida404.commandhandler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.impl.CommandHandlerImpl;
import com.rvida404.commandhandler.logger.HandlerLogger;

public class TestCommandHandler implements CommandHandler {

	private final CommandHandler	handler;
	private Field					schema;

	public TestCommandHandler(CommandHandlerImpl handler) {
		this.handler = handler;

		try {
			schema = handler.getClass().getDeclaredField("schema");
			schema.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public String handle(String command) {
		return handler.handle(command);
	}

	@Override
	public void putAction(String key, Consumer<List<?>> value) {
		handler.putAction(key, value);
	}

	@Override
	public void putAction(String key, Function<List<?>, String> value) {
		handler.putAction(key, value);
	}

	@Override
	public void setLogger(HandlerLogger logger) {
		handler.setLogger(logger);
	}

	public CommandSchema getSchema() {
		try {
			return (CommandSchema) schema.get(handler);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
