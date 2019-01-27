package com.rvida404.commandhandler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class will keep string references to objects that can be used within the command schema (such as placeholders
 * and actions)
 * @author Robert
 */
class Context {

	public static final String	COMMAND			= "command";
	public static final String	TOKEN_FORMAT	= "token@%d";

	private Map<String, Object>						context	= new HashMap<>();
	private Map<String, Function<List<?>, String>>	actions	= new HashMap<>();

	private String	placeholderFormat;
	private Pattern	placeholderPattern;

	public Context() {
	}

	public Context(Context context) {
		this.context = new HashMap<>();
		this.context.putAll(context.context);
		this.actions = new HashMap<>();
		this.actions.putAll(context.actions);
		this.placeholderFormat = context.placeholderFormat;
		this.placeholderPattern = context.placeholderPattern;
	}

	/**
	 * Set the <i>command<i> variable
	 * @param command the value
	 */
	public void setCommand(String command) {
		put(COMMAND, command);
	}

	/**
	 * Set the <i>token@?<i> variable
	 * @param command the value
	 */
	public void setCommandTokens(String[] commandTokens) {
		for (int i = 0; i < commandTokens.length; i++) {
			put(String.format(TOKEN_FORMAT, i), commandTokens[i]);
		}
	}

	/**
	 * Set the placeholder symbols that mark the beginning and end of a placeholder.
	 * @param begin the begin string
	 * @param end the end string
	 */
	public void setPlaceholders(String begin, String end) {
		// We need to escape the placeholder begin & end symbols
		placeholderFormat = Pattern.quote(begin) + "%s" + Pattern.quote(end);
		// Non-greedy matcher so we can support multiple placeholders in the same message
		placeholderPattern = Pattern.compile(String.format(placeholderFormat, "(.*?)"));
	}

	/**
	 * Register a placeholder entry.
	 * @param key the string that will be used to reference the variable
	 * @param value the value that will be used instead of the placeholder
	 */
	public void put(String key, Object value) {
		context.put(key, value);
	}

	/**
	 * Get a placeholder value.
	 * @param key the string reference of the variable
	 * @return the object value stored (typically a string) or null
	 */
	public Object get(String key) {
		return context.get(key);
	}

	/**
	 * Check if there exists a placeholder with the given name
	 * @param key the string reference of the variable to check
	 * @return true or false
	 */
	public boolean contains(String key) {
		return context.containsKey(key);
	}

	/**
	 * Register a action entry.
	 * @param key the string that will be used to reference the action
	 * @param value the function that will be called when the name is used
	 */
	public void putAction(String key, Function<List<?>, String> value) {
		actions.put(key, value);
	}

	/**
	 * Get a action function.
	 * @param key the string that will be used to reference the action
	 * @return the function associated with the key or null
	 */
	public Function<List<?>, String> getAction(String key) {
		return actions.get(key);
	}

	/**
	 * Check if there exists a action with the given name
	 * @param key the string reference of the action to check
	 * @return true or false
	 */
	public boolean containsAction(String key) {
		return actions.containsKey(key);
	}

	/**
	 * Resolve the palceholders within a string.
	 * @param value the string value
	 * @return the resolved string
	 */
	public String resolvePlaceholders(String value) {
		String result = value;
		try {
			Matcher matcher = placeholderPattern.matcher(value);
			while (matcher.find()) {
				String found = matcher.group(1);
				if (contains(found)) {
					result = result.replaceAll(String.format(placeholderFormat, found), context.get(found).toString());
				}
			}
		} catch (Exception e) {
			System.out.println();
		}
		return result;
	}
}
