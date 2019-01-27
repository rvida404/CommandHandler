package com.rvida404.commandhandler.validation.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.rvida404.commandhandler.Constants;
import com.rvida404.commandhandler.TestCommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.elements.Actions.Action;
import com.rvida404.commandhandler.validation.BaseValidation;

public class ActionValidation extends BaseValidation {

	@Test
	public void emptyElement() throws Exception {
		expectTofail("emptyElement.xml", missingAttribute("action", "name"));
	}

	@Test
	public void foreignAttribute() throws Exception {
		expectTofail("foreignAttribute.xml", invalidAttribute("action", "foreign"));
	}

	@Test
	public void foreignElement() throws Exception {
		expectTofail("foreignElement.xml", elementHasNoChildren("action"));
	}

	@Test
	public void justNameAttribute() throws Exception {
		TestCommandHandler parser = getHandler("justNameAttribute.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertEquals(1, schema.getAllTokens().get(0).getAllActions().size());
		Action action = schema.getAllTokens().get(0).getAllActions().get(0);
		assertEquals("a", action.getName());
		assertNull(action.getParam());
		assertNull(action.getStoreIn());
		assertEquals(Constants.DEFAULT_SEPARATOR, action.getParamSeparator());
		assertNull(action.getResponseOnError());
	}

	@Test
	public void full() throws Exception {
		TestCommandHandler parser = getHandler("full.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertEquals(2, schema.getAllTokens().get(0).getAllActions().size());
		Action action = schema.getAllTokens().get(0).getAllActions().get(0);
		assertEquals("a", action.getName());
		assertEquals("param", action.getParam());
		assertEquals("asd", action.getStoreIn());
		assertEquals("separator", action.getParamSeparator());
		assertEquals("errorResponse", action.getResponseOnError());
	}
}
