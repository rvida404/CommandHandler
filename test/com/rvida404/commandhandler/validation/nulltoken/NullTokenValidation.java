package com.rvida404.commandhandler.validation.nulltoken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.rvida404.commandhandler.TestCommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.validation.BaseValidation;

public class NullTokenValidation extends BaseValidation {

	@Test
	public void emptyElement() throws Exception {
		expectTofail("emptyElement.xml", missingAttribute("nullToken", "value"));
	}

	@Test
	public void foreignAttribute() throws Exception {
		expectTofail("foreignAttribute.xml", invalidAttribute("nullToken", "foreign"));
	}

	@Test
	public void foreignElement() throws Exception {
		expectTofail("foreignElement.xml", elementHasNoChildren("nullToken"));
	}

	@Test
	public void full() throws Exception {
		TestCommandHandler parser = getHandler("full.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertEquals("asd", schema.getNullToken().getValue());
	}
}
