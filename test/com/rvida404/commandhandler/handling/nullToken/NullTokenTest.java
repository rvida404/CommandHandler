package com.rvida404.commandhandler.handling.nullToken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.rvida404.commandhandler.BaseTest;

public class NullTokenTest extends BaseTest {

	@Test
	public void noNullToken() throws Exception {
		assertNull(getHandler("noNullToken.xml").handle(null));
	}

	@Test
	public void nullTokenWithValue() throws Exception {
		assertEquals("nullValue", getHandler("nullTokenWithValue.xml").handle(null));
	}
}
