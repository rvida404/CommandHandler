package com.rvida404.commandhandler.validation.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.rvida404.commandhandler.TestCommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.elements.Tokens.Token;
import com.rvida404.commandhandler.validation.BaseValidation;

public class ActionsValidation extends BaseValidation {

	@Test
	public void emptyElement() throws Exception {
		expectTofail("emptyElement.xml", missingElement("actions", "action"));
	}

	@Test
	public void foreignAttribute() throws Exception {
		expectTofail("foreignAttribute.xml", invalidAttribute("actions", "foreign"));
	}

	@Test
	public void foreignElementBefore() throws Exception {
		expectTofail("foreignElementBefore.xml", invalidContent("foreign", "action"));
	}

	@Test
	public void foreignElementMiddle() throws Exception {
		expectTofail("foreignElementMiddle.xml", invalidContent("foreign", "action"));
	}

	@Test
	public void foreignElementEnd() throws Exception {
		expectTofail("foreignElementEnd.xml", invalidContent("foreign", "action"));
	}

	@Test
	public void full() throws Exception {
		TestCommandHandler parser = getHandler("full.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertNotNull(schema.getTokens());
		assertEquals(1, schema.getAllTokens().size());
		Token token = schema.getAllTokens().get(0);
		assertEquals(2, token.getAllActions().size());
		assertEquals("a", token.getAllActions().get(0).getName());
		assertEquals("b", token.getAllActions().get(1).getName());
	}
}
