package com.rvida404.commandhandler;

import java.io.File;

import com.rvida404.commandhandler.impl.CommandHandlerImpl;

public abstract class BaseTest {

	protected TestCommandHandler getHandler(String xmlFile) throws Exception {
		return new TestCommandHandler(new CommandHandlerImpl(
				CommandHandlerFactory.makeSchema(new File(getClass().getResource(xmlFile).getPath()))));
	}
}
