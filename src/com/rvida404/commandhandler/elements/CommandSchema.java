package com.rvida404.commandhandler.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.rvida404.commandhandler.Constants;
import com.rvida404.commandhandler.elements.Tokens.Token;

@XmlRootElement
public class CommandSchema {

	private String	separator			= Constants.DEFAULT_SEPARATOR;
	private String	placeholderBegin	= Constants.DEFAULT_PLACEHOLDER_BEGIN;
	private String	placeholderEnd		= Constants.DEFAULT_PLACEHOLDER_END;

	private boolean		failOnNoResponse	= true;
	private Tokens		tokens				= new Tokens();
	private Failure		failure				= new Failure();
	private NullToken	nullToken			= new NullToken();

	public String getSeparator() {
		return separator;
	}

	@XmlAttribute
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getPlaceholderBegin() {
		return placeholderBegin;
	}

	@XmlAttribute
	public void setPlaceholderBegin(String placeholderBegin) {
		this.placeholderBegin = placeholderBegin;
	}

	public String getPlaceholderEnd() {
		return placeholderEnd;
	}

	@XmlAttribute
	public void setPlaceholderEnd(String placeholderEnd) {
		this.placeholderEnd = placeholderEnd;
	}

	public boolean isFailOnNoResponse() {
		return failOnNoResponse;
	}

	@XmlAttribute
	public void setFailOnNoResponse(boolean failOnNoResponse) {
		this.failOnNoResponse = failOnNoResponse;
	}

	public Tokens getTokens() {
		return tokens;
	}

	@XmlElement
	public void setTokens(Tokens tokens) {
		this.tokens = tokens;
	}

	public Failure getFailure() {
		return failure;
	}

	@XmlElement
	public void setFailure(Failure failure) {
		this.failure = failure;
	}

	public NullToken getNullToken() {
		return nullToken;
	}

	@XmlElement
	public void setNullToken(NullToken nullToken) {
		this.nullToken = nullToken;
	}

	// ================================================================

	public List<Token> getAllTokens() {
		return getTokens().getToken();
	}
}
