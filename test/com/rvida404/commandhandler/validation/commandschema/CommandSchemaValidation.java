package com.rvida404.commandhandler.validation.commandschema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rvida404.commandhandler.Constants;
import com.rvida404.commandhandler.TestCommandHandler;
import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.validation.BaseValidation;

public class CommandSchemaValidation extends BaseValidation {

	@Test
	public void emptyFile() throws Exception {
		expectTofail("emptyFile.xml", "Premature end of file.");
	}

	@Test
	public void emptyElement() throws Exception {
		expectTofail("emptyElement.xml", missingElement("commandSchema", "tokens"));
	}

	@Test
	public void twoTokens() throws Exception {
		expectTofail("twoTokens.xml", invalidContent("tokens", "nullToken, failure"));
	}

	@Test
	public void twoFailures() throws Exception {
		expectTofail("twoFailures.xml", invalidContent("failure"));
	}

	@Test
	public void twoNullTokens() throws Exception {
		expectTofail("twoNullTokens.xml", invalidContent("nullToken", "failure"));
	}

	@Test
	public void wrongAttributeType() throws Exception {
		expectTofail("wrongAttributeType.xml", "'aaaa' is not a valid value for 'boolean'");
	}

	@Test
	public void justTokens() throws Exception {
		TestCommandHandler parser = getHandler("justTokens.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();

		assertNull(schema.getNullToken().getValue());
		assertNull(schema.getFailure().getValue());

		assertEquals(Constants.DEFAULT_SEPARATOR, schema.getSeparator());
		assertEquals(Constants.DEFAULT_PLACEHOLDER_BEGIN, schema.getPlaceholderBegin());
		assertEquals(Constants.DEFAULT_PLACEHOLDER_END, schema.getPlaceholderEnd());
		assertTrue(schema.isFailOnNoResponse());
	}

	@Test
	public void full() throws Exception {
		TestCommandHandler parser = getHandler("full.xml");
		assertNotNull(parser);
		CommandSchema schema = parser.getSchema();
		assertEquals("a", schema.getNullToken().getValue());
		assertEquals("a", schema.getFailure().getValue());

		assertEquals(";", schema.getSeparator());
		assertEquals("<", schema.getPlaceholderBegin());
		assertEquals(">", schema.getPlaceholderEnd());
		assertFalse(schema.isFailOnNoResponse());
	}

	@Test
	public void foreignElementBefore() throws Exception {
		expectTofail("foreignElementBefore.xml", invalidContent("foreign", "tokens"));
	}

	@Test
	public void foreignElementMiddle() throws Exception {
		expectTofail("foreignElementMiddle.xml", invalidContent("foreign", "nullToken, failure"));
	}

	@Test
	public void foreignElementEnd() throws Exception {
		expectTofail("foreignElementEnd.xml", invalidContent("foreign"));
	}

	@Test
	public void foreignAttribute() throws Exception {
		expectTofail("foreignAttribute.xml", invalidAttribute("commandSchema", "foreign"));
	}
}
