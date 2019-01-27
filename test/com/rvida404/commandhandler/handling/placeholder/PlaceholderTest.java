package com.rvida404.commandhandler.handling.placeholder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.rvida404.commandhandler.BaseTest;
import com.rvida404.commandhandler.CommandHandler;

public class PlaceholderTest extends BaseTest {

	@Test
	public void value() throws Exception {
		CommandHandler handler = getHandler("value.xml");

		// response {$command} placeholder
		assertEquals("response 1,1 '1,1'", handler.handle("1,1"));
		// response {$token@1} placeholder
		assertEquals("response 2,1, '1'", handler.handle("2,1,"));
		// response {$token@5} placeholder does not exist, so we don't replace it
		assertEquals("response 2,1, '{$token@5}'", handler.handle("2,1"));
	}

	@Test
	public void failureValue() throws Exception {
		CommandHandler handler = getHandler("value.xml");
		// failure {$command} placeholder
		assertEquals("failure global '99'", handler.handle("99"));
	}

	@Test
	public void nullTokenValue() throws Exception {
		CommandHandler handler = getHandler("value.xml");
		// nullToken {$command} placeholder does not work. if there is no command, there is nothing in the context
		assertEquals("nullToken '{$command}'", handler.handle(null));
	}

	@Test
	public void multipleValue() throws Exception {
		CommandHandler handler = getHandler("value.xml");
		// multiple placeholders
		assertEquals("response '3,1' 1-3", handler.handle("3,1"));
	}

	@Test
	public void customPlaceholderSymbols() throws Exception {
		CommandHandler handler = getHandler("customPlaceholderSymbols.xml");
		// custom placehodler symbols
		assertEquals("response '1'", handler.handle("1"));

	}
}
