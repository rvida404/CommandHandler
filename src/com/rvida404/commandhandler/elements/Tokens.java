package com.rvida404.commandhandler.elements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.rvida404.commandhandler.elements.Actions.Action;

public class Tokens {

	public static class Token {

		private String		value;
		private boolean		isRegexp;
		private Tokens		tokens;
		private Response	response;
		private Failure		failure;
		private Actions		actions;

		public String getValue() {
			return value;
		}

		@XmlAttribute
		public void setValue(String value) {
			this.value = value;
		}

		public boolean isRegexp() {
			return isRegexp;
		}

		@XmlAttribute
		public void setRegexp(boolean isRegexp) {
			this.isRegexp = isRegexp;
		}

		public Tokens getTokens() {
			return tokens;
		}

		@XmlElement
		public void setTokens(Tokens tokens) {
			this.tokens = tokens;
		}

		public Response getResponse() {
			return response;
		}

		@XmlElement
		public void setResponse(Response response) {
			this.response = response;
		}

		public Failure getFailure() {
			return failure;
		}

		@XmlElement
		public void setFailure(Failure failure) {
			this.failure = failure;
		}

		public Actions getActions() {
			return actions;
		}

		@XmlElement
		public void setActions(Actions actions) {
			this.actions = actions;
		}

		// ================================================================

		public boolean canHandle(String targetToken) {
			if (getValue() != null) {
				return isRegexp() //
						? targetToken.matches(getValue()) //
						: getValue().equals(targetToken);
			}
			return false;
		}

		public boolean hasTokens() {
			return getTokens() != null ? getTokens().getToken().size() > 0 : false;
		}
		
		public List<Token> getAllTokens() {
			return getTokens().getToken();
		}

		public List<Action> getAllActions() {
			return getActions().getAction();
		}

		public boolean hasResponse() {
			return getResponse() != null;
		}

		public String getResponseValue() {
			return getResponse().getValue();
		}
	}

	private List<Token> token = new ArrayList<>();

	public List<Token> getToken() {
		return token;
	}

	@XmlElement
	public void setToken(List<Token> token) {
		this.token = token;
	}
}
