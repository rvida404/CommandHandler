package com.rvida404.commandhandler.validation.tokens;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.rvida404.commandhandler.TestCommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.validation.BaseValidation;

public class TokensValidation extends BaseValidation {

	@Test
	public void emptyElement() throws Exception {
		expectTofail("emptyElement.xml", missingElement("tokens", "token"));
	}

	@Test
	public void foreignAttribute() throws Exception {
		expectTofail("foreignAttribute.xml", invalidAttribute("tokens", "foreign"));
	}

	@Test
	public void foreignElementBefore() throws Exception {
		expectTofail("foreignElementBefore.xml", invalidContent("foreign", "token"));
	}

	@Test
	public void foreignElementMiddle() throws Exception {
		expectTofail("foreignElementMiddle.xml", invalidContent("foreign", "token"));
	}

	@Test
	public void foreignElementEnd() throws Exception {
		expectTofail("foreignElementEnd.xml", invalidContent("foreign", "token"));
	}

	@Test
	public void full() throws Exception {
		TestCommandHandler parser = getHandler("full.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertNotNull(schema.getTokens());
		assertEquals(3, schema.getAllTokens().size());
	}
}
