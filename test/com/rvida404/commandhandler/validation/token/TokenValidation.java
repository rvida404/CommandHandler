package com.rvida404.commandhandler.validation.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rvida404.commandhandler.TestCommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.elements.Tokens.Token;
import com.rvida404.commandhandler.validation.BaseValidation;

public class TokenValidation extends BaseValidation {

	@Test
	public void emptyElement() throws Exception {
		expectTofail("emptyElement.xml", missingAttribute("token", "value"));
	}

	@Test
	public void foreignAttribute() throws Exception {
		expectTofail("foreignAttribute.xml", invalidAttribute("token", "foreign"));
	}

	@Test
	public void foreignElementBefore() throws Exception {
		expectTofail("foreignElementBefore.xml", invalidContent("foreign", "actions, tokens, response, failure"));
	}

	@Test
	public void foreignElementMiddle() throws Exception {
		expectTofail("foreignElementMiddle.xml", invalidContent("foreign", "failure"));
	}

	@Test
	public void foreignElementEnd() throws Exception {
		expectTofail("foreignElementEnd.xml", invalidContent("foreign"));
	}

	@Test
	public void twoActions() throws Exception {
		expectTofail("twoActions.xml", invalidContent("actions", "tokens, response, failure"));
	}

	@Test
	public void twoTokens() throws Exception {
		expectTofail("twoTokens.xml", invalidContent("tokens", "response, failure"));
	}

	@Test
	public void twoResponses() throws Exception {
		expectTofail("twoResponses.xml", invalidContent("response", "failure"));
	}

	@Test
	public void twoFailures() throws Exception {
		expectTofail("twoFailures.xml", invalidContent("failure"));
	}

	@Test
	public void wrongAttributeType() throws Exception {
		expectTofail("wrongAttributeType.xml", "'aaaa' is not a valid value for 'boolean'");
	}

	@Test
	public void justValueAttribute() throws Exception {
		TestCommandHandler parser = getHandler("justValueAttribute.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertNotNull(schema.getTokens());
		assertEquals(1, schema.getAllTokens().size());

		Token token = schema.getAllTokens().get(0);
		assertEquals("a", token.getValue());
		assertFalse(token.isRegexp());
		assertNull(token.getTokens());
		assertNull(token.getActions());
		assertNull(token.getResponse());
		assertNull(token.getFailure());
	}

	@Test
	public void full() throws Exception {
		TestCommandHandler parser = getHandler("full.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertNotNull(schema.getTokens());
		assertEquals(2, schema.getAllTokens().size());
		assertEquals("b", schema.getAllTokens().get(1).getValue());

		Token token = schema.getAllTokens().get(0);
		assertEquals("a", token.getValue());
		assertTrue(token.isRegexp());
		assertEquals(1, token.getAllActions().size());
		assertEquals(1, token.getAllTokens().size());
		assertEquals("r", token.getResponseValue());
		assertEquals("f", token.getFailure().getValue());
		assertEquals("b", schema.getAllTokens().get(0).getAllTokens().get(0).getValue());
	}
}
