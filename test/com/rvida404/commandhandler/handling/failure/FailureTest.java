package com.rvida404.commandhandler.handling.failure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.rvida404.commandhandler.BaseTest;
import com.rvida404.commandhandler.CommandHandler;

public class FailureTest extends BaseTest {

	@Test
	public void noFailure() throws Exception {
		assertNull(getHandler("noFailure.xml").handle("non-existing token"));
	}

	@Test
	public void failureWithNoValue() throws Exception {
		assertNull(getHandler("failureWithNoValue.xml").handle("non-existing token"));
	}

	@Test
	public void failureWithValue() throws Exception {
		assertEquals("failureValue", getHandler("failureWithValue.xml").handle("non-existing token"));
	}

	@Test
	public void inToken() throws Exception {
		CommandHandler handler = getHandler("inToken.xml");

		// failure in token with value
		assertEquals("failure 1,1", handler.handle("1,1"));
		// failure in the same token, but trying to go a token further even if there are none (fallback)
		assertEquals("failure 1,1", handler.handle("1,1,1"));

		// same as before, but now we give a empty last token
		assertEquals("response 1,1,2", handler.handle("1,1,2"));
		assertEquals("failure 1,1", handler.handle("1,1,2,"));

		// failure in token without value
		assertNull(handler.handle("2,1"));

		// no failure element
		assertNull(handler.handle("3,1"));
	}

	@Test
	public void fallback() throws Exception {
		CommandHandler handler = getHandler("fallback.xml");

		// failure token 6 does not exist and falls back to 4 failure
		assertEquals("failure 1,2,3,4", handler.handle("1,2,3,4,5,6"));
		// failure token 5 does have response and falls back to 4 failure
		assertEquals("failure 1,2,3,4", handler.handle("1,2,3,4,5"));
		// failure token 4 does have response and falls back to 4 failure
		assertEquals("failure 1,2,3,4", handler.handle("1,2,3,4"));
		// failure token 3 does have response and falls back to 2 failure
		assertEquals("failure 1,2", handler.handle("1,2,3"));
		// failure token 1 does have response and falls back to global failure
		assertEquals("failure global", handler.handle("1"));
	}
}
