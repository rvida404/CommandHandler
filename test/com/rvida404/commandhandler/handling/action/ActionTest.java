package com.rvida404.commandhandler.handling.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.Test;

import com.rvida404.commandhandler.BaseTest;
import com.rvida404.commandhandler.CommandHandler;
import com.rvida404.commandhandler.logger.Level;

public class ActionTest extends BaseTest {

	@Test
	public void testCall() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		boolean[] called = { false };
		Consumer<List<?>> consumer = (objects) -> called[0] = true;
		handler.putAction("testCall", consumer);

		assertEquals("Top failure fired", handler.handle("1"));
		assertTrue(called[0]);
	}

	@Test
	public void testCallWithParam() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		boolean[] recieved = { false };
		Consumer<List<?>> consumer = (objects) -> recieved[0] = "2,1".equals(objects.get(0));
		handler.putAction("testCallWithParam", consumer);

		assertEquals("Top failure fired", handler.handle("2,1"));
		assertTrue(recieved[0]);
	}

	@Test
	public void testCallWithParamBadSeparator() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		boolean[] recieved = { false };
		Consumer<List<?>> consumer = (objects) -> recieved[0] = "2,4;4".equals(objects.get(0));
		handler.putAction("testCallWithParam", consumer);

		assertEquals("Top failure fired", handler.handle("2,4"));
		assertTrue(recieved[0]);
	}

	@Test
	public void testCallWithMultipleParam() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		boolean[] recieved = { false };
		Consumer<List<?>> consumer = (objects) -> recieved[0] = "2,2".equals(objects.get(0)) //
				&& "2".equals(objects.get(1)) //
				&& "test".equals(objects.get(2));
		handler.putAction("testCallWithParam", consumer);

		assertEquals("Top failure fired", handler.handle("2,2"));
		assertTrue(recieved[0]);
	}

	@Test
	public void testCallWithMultipleAndSeparatorParam() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		boolean[] recieved = { false };
		Consumer<List<?>> consumer = (objects) -> recieved[0] = "2,3".equals(objects.get(0)) //
				&& "3".equals(objects.get(1)) //
				&& "test".equals(objects.get(2));
		handler.putAction("testCallWithParam", consumer);

		assertEquals("Top failure fired", handler.handle("2,3"));
		assertTrue(recieved[0]);
	}

	@Test
	public void testCallWithParamAndReturn() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		Function<List<?>, String> function = (objects) -> "First token: " + objects.get(0);
		handler.putAction("testCallWithParamAndReturn", function);

		assertEquals("Got 'First token: 3' from action'testCallWithParamAndReturn", handler.handle("3"));
	}

	@Test
	public void testChainedCallsWithParamAndReturn() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		Function<List<?>, String> function = (objects) -> "First token: " + objects.get(0);
		handler.putAction("testCallWithParamAndReturn", function);

		assertEquals("Got 'First token: 4' then 'First token: First token: 4' from action'testCallWithParamAndReturn",
				handler.handle("4"));
	}

	@Test
	public void testChainedCallsOnDifferentLevelsWithParamAndReturn() throws Exception {
		CommandHandler handler = getHandler("action.xml");
		Function<List<?>, String> function1 = (objects) -> "First Function: here";
		Function<List<?>, String> function2 = (objects) -> "Second Function: " + objects.get(0);
		handler.putAction("testCall1", function1);
		handler.putAction("testCall2", function2);

		assertEquals("Got 'First Function: here' then 'Second Function: First Function: here'", handler.handle("5,1"));
	}

	@Test
	public void errorNotExists() throws Exception {
		CommandHandler handler = getHandler("error.xml");

		assertEquals("failure global", handler.handle("1"));
	}

	@Test
	public void actionThrowsException() throws Exception {
		CommandHandler handler = getHandler("error.xml");
		String[] message = { "" };
		 handler.setLogger((l, m, t) -> message[0] = m);

		// action throws exception
		Consumer<List<?>> consumer = objects -> {
			throw new RuntimeException("asd");
		};
		handler.putAction("testCall2", consumer);
		handler.putAction("testCall1", objects -> {
		});

		assertEquals("failure 2,1", handler.handle("2,1"));
		assertEquals("Error processing '2,1'", message[0]);
	}

	@Test
	public void actionThrowsExceptionWithResponse() throws Exception {
		CommandHandler handler = getHandler("error.xml");
		String[] message = { "" };
		 handler.setLogger((l, m, t) -> message[0] = m);

		// action throws exception
		Consumer<List<?>> consumer = objects -> {
			throw new RuntimeException("asd");
		};
		handler.putAction("testCall2", consumer);
		handler.putAction("testCall1", objects -> {
		});

		assertEquals("Error '3,1'", handler.handle("3,1"));
		assertEquals("", message[0]);
	}
}
