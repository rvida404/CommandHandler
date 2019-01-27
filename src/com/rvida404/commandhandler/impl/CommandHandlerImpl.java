package com.rvida404.commandhandler.impl;

import java.util.List;
import java.util.function.Function;

import com.rvida404.commandhandler.CommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.logger.HandlerLogger;

public class CommandHandlerImpl implements CommandHandler {

	private static final HandlerLogger emptyLogger = (level, message, throwable) -> {
	};

	private final CommandSchema	schema;
	private final Context		context;
	private HandlerLogger		logger;

	public CommandHandlerImpl(CommandSchema schema) {
		this(schema, emptyLogger);
	}

	public CommandHandlerImpl(CommandSchema schema, HandlerLogger logger) {
		this.schema = schema;
		this.context = new Context();
		this.context.setPlaceholders(schema.getPlaceholderBegin(), schema.getPlaceholderEnd());
		this.logger = logger;
	}

	@Override
	public String handle(String command) {
		return command == null //
				? schema.getNullToken().getValue() //
				: new Session(schema, new Context(context), command, logger).getResponse();
	}

	@Override
	public void putAction(String key, Function<List<?>, String> value) {
		context.putAction(key, value);
	}

	@Override
	public void setLogger(HandlerLogger logger) {
		this.logger = logger != null //
				? logger //
				: emptyLogger;

	}
}
