package com.rvida404.commandhandler.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rvida404.commandhandler.elements.Actions.Action;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.elements.Failure;
import com.rvida404.commandhandler.elements.Tokens.Token;
import com.rvida404.commandhandler.logger.HandlerLogger;

/**
 * Class that will handle a command according to a given schema.
 * @author Robert
 */
class Session {

	private static class Error extends Exception {
		private static final long serialVersionUID = 8337538687688466033L;

		private final String message;

		public Error(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	private final HandlerLogger	logger;
	private final String		command;
	private final String[]		commandTokens;
	private final Context		context;
	private final Boolean		failOnNoResponse;

	private Integer		commandTokenIndex;
	private boolean		wasHandled	= false;
	private List<Token>	tokens;
	private Failure		failure;

	public Session(CommandSchema schema, Context context, String command, HandlerLogger logger) {
		this.failure = schema.getFailure();
		this.failOnNoResponse = schema.isFailOnNoResponse();
		this.tokens = new ArrayList<>(schema.getAllTokens());

		this.command = command;
		this.commandTokens = command.split(schema.getSeparator(), -1);
		this.commandTokenIndex = 0;

		this.context = context;
		this.context.setCommand(command);
		this.context.setCommandTokens(this.commandTokens);
		this.logger = logger;
	}

	/**
	 * Get the response for the given command.
	 * @return the response in string format or null
	 */
	public String getResponse() {
		String response = null;
		try {
			verifyData();
			response = parseTokens();
		} catch (Exception e) {
			logger.warning(String.format("Error processing '%s'", command), e);
		}
		return response;
	}

	/**
	 * Parse tokens to obtain the response.
	 * @return the response in string format or null
	 */
	private String parseTokens() {
		String response = null;

		try {
			while (!tokens.isEmpty() && !wasHandled) {
				Token token = tokens.remove(0);
				if (token.canHandle(commandTokens[commandTokenIndex])) {
					updateFailure(token);
					executeActions(token);
					if (isAtLastToken()) {
						response = handleLastCommandToken(token);
						break;
					} else if (!token.hasTokens()) {
						response = handleNoMoreSchemaTokens();
						break;
					} else {
						// Search for the next token
						tokens.clear();
						tokens.addAll(token.getAllTokens());
						commandTokenIndex++;
					}
				}
			}
		} catch (Error e) {
			response = e.getMessage();
			wasHandled = true;
		} catch (Exception e) {
			logger.warning(String.format("Error processing '%s'", command), e);
		}

		if (!wasHandled) {
			response = failure.getValue();
		}

		return response != null ? context.resolvePlaceholders(response) : response;
	}

	/**
	 * Handle the situation when we are at the last command token. This can be split
	 * into the following situations:
	 * <ul>
	 * <li>The schema token has a response: in this case we simply retrieve the
	 * response</li>
	 * <li>The schema token does not have a response: if failOnNoResponse is true
	 * then we return the topmost failure value, otherwise we just return null</li>
	 * </ul>
	 * 
	 * @param token the schema token
	 * @return the response
	 */
	private String handleLastCommandToken(Token token) {
		String response;
		if (token.hasResponse()) {
			response = token.getResponseValue();
		} else {
			response = failOnNoResponse ? failure.getValue() : null;
		}
		wasHandled = true;
		return response;
	}

	/**
	 * Handle the situation when we have reached the end of our schema tokens,
	 * however we still have command tokens.
	 * 
	 * @return
	 */
	private String handleNoMoreSchemaTokens() {
		String response;
		response = failure.getValue();
		wasHandled = true;
		return response;
	}

	/**
	 * Update the failure object with the schema token failure if it has any. This
	 * is to ensure that we always use the topmost failure object.
	 * 
	 * @param token the schema token
	 */
	private void updateFailure(Token token) {
		if (token.getFailure() != null) {
			failure = token.getFailure();
		}
	}

	/**
	 * Execute the actions of a given token.
	 * @param token the token in question
	 * @throws Exception
	 */
	private void executeActions(Token token) throws Exception {
		if (token.getActions() != null) {
			for (Action action : token.getAllActions()) {
				checkAction(action);
				List<?> paramList = extractParameters(action);
				execute(action, paramList);
			}
		}
	}

	/**
	 * Check that the action actually exists in the context.
	 * @param action the action name
	 * @throws Exception
	 */
	private void checkAction(Action action) throws Exception {
		if (!context.containsAction(action.getName())) {
			logger.warning(String.format("There is no action with the name '%s'", action.getName()));
			throw new Exception();
		}
	}

	/**
	 * Extract the action parameters and resolve their placehodlers.
	 * If it has parameters, we first need to split them and then resolve their placehoders. If we do this the other way
	 * around we might introduce symbols that look like separators, generating wrong parameters
	 * @param action the action
	 * @return
	 */
	private List<?> extractParameters(Action action) {
		List<?> paramList = null;
		if (action.getParam() != null) {
			String[] paramElements = action.getParam().split(action.getParamSeparator());
			for (int i = 0; i < paramElements.length; i++) {
				paramElements[i] = context.resolvePlaceholders(paramElements[i]);
			}
			paramList = Arrays.asList(paramElements);
		}
		return paramList;
	}

	/**
	 * Execute the action with the parameters processed. This takes palce after we called {@link #checkAction(Action)}
	 * so we know the action exists in the context.
	 * @param action the action
	 * @param paramList the parameter list
	 * @throws Error
	 */
	private void execute(Action action, List<?> paramList) throws Error, Exception {
		try {
			String result = context.getAction(action.getName()).apply(paramList);

			// If we need to store the result
			if (action.getStoreIn() != null) {
				context.put(action.getStoreIn(), result);
			}
		} catch (Exception e) {
			// If we have an exception but the responseOnError value is not null, we will resolve it as the response
			throw action.getResponseOnError() != null //
					? new Error(action.getResponseOnError()) //
					: e;

		}
	}

	/**
	 * Check if we are at the last command token.
	 * 
	 * @return true or false
	 */
	private boolean isAtLastToken() {
		return commandTokens.length == commandTokenIndex + 1;
	}

	/**
	 * Check that the session data is valid.
	 * @throws Exception
	 */
	private void verifyData() throws Exception {
		StringBuilder sb = new StringBuilder();
		if (context == null) {
			sb.append("context is null,");
		}
		if (commandTokens == null || commandTokenIndex == null) {
			sb.append("commandTokens is null,");
		}
		if (failOnNoResponse == null) {
			sb.append("failOnNoResponse is null,");
		}
		if (tokens == null) {
			sb.append("tokens is null,");
		}
		if (failure == null) {
			sb.append("failure is null");
		}

		int length = sb.length();
		if (length > 0) {
			sb.replace(length - 1, length, "!");
			throw new Exception(sb.toString());
		}
	}
}
