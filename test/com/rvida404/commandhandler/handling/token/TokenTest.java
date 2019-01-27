package com.rvida404.commandhandler.handling.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.rvida404.commandhandler.BaseTest;
import com.rvida404.commandhandler.CommandHandler;

public class TokenTest extends BaseTest {

	@Test
	public void value() throws Exception {
		CommandHandler handler = getHandler("value.xml");

		// response 1,1
		assertEquals("response 1,1", handler.handle("1,1"));
		// test that handler does not fallback on 1,1 response
		assertEquals("failure global", handler.handle("1,1,2"));

		// there is no token handling the empty string
		assertEquals("failure global", handler.handle("2,1,"));
		// there is a token handling the empty string
		assertEquals("response 2,2,", handler.handle("2,2,"));

		// regexp start
		assertEquals("response 3,start.*", handler.handle("3,startAAAAAAAAA"));
		// regexp middle
		assertEquals("response 3,.*middle.*", handler.handle("3,AAAAAmiddleAAAA"));
		// regexp end
		assertEquals("response 3,.*end", handler.handle("3,AAAAAAAAAend"));
	}

	@Test
	public void separator() throws Exception {
		CommandHandler handler = getHandler("separator.xml");

		// failure, separator is now ;
		assertEquals("failure global", handler.handle("1,1"));
		// response 1,1
		assertEquals("response 1;1", handler.handle("1;1"));
	}

	@Test
	public void passOnNoResponse() throws Exception {
		CommandHandler handler = getHandler("passOnNoResponse.xml");

		// token 1,1 has response
		assertEquals("response 1,1", handler.handle("1,1"));
		// there is no token 2
		assertEquals("failure global", handler.handle("2"));
		// token 1 does not have a response, however it does not fail since the flag failOnNoResponse is set to false
		assertNull(handler.handle("1"));
	}

	@Test
	public void passOnEmptyResponse() throws Exception {
		CommandHandler handler = getHandler("passOnEmptyResponse.xml");

		// token 1,1 has response
		assertEquals("response 1,1", handler.handle("1,1"));
		// there is no token 2
		assertEquals("failure global", handler.handle("2"));
		// token 1 has a response with no value, thus null
		assertNull(handler.handle("1"));
	}

}
