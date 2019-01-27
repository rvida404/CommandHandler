package com.rvida404.commandhandler.validation.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.rvida404.commandhandler.TestCommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.validation.BaseValidation;

public class ResponseValidation extends BaseValidation {

	@Test
	public void emptyElement() throws Exception {
		TestCommandHandler parser = getHandler("emptyElement.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertNull(schema.getAllTokens().get(0).getResponseValue());
	}

	@Test
	public void foreignAttribute() throws Exception {
		expectTofail("foreignAttribute.xml", invalidAttribute("response", "foreign"));
	}

	@Test
	public void foreignElement() throws Exception {
		expectTofail("foreignElement.xml", elementHasNoChildren("response"));
	}

	@Test
	public void full() throws Exception {
		TestCommandHandler parser = getHandler("full.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertEquals("asd", schema.getAllTokens().get(0).getResponseValue());
	}
}
