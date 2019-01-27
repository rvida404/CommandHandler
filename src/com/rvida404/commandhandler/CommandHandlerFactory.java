package com.rvida404.commandhandler;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.rvida404.commandhandler.elements.CommandSchema;
import com.rvida404.commandhandler.impl.CommandHandlerImpl;

/**
 * Command handler factory
 * @author Robert
 */
public abstract class CommandHandlerFactory {

	static File xsdFile = new File(CommandHandlerFactory.class.getResource("commandSchema.xsd").getPath());

	/**
	 * Make a command handler out of a file. The file should be an xml, and it will be validated against an xsd.
	 * @param file the file
	 * @return the command handler for the provided file or null
	 */
	public static CommandHandler makeHandler(File file) {
		CommandHandlerImpl commandHandler = null;
		try {
			commandHandler = new CommandHandlerImpl(makeSchema(file));
		} catch (Exception e) {
		}
		return commandHandler;
	}

	/**
	 * Create the command schema from a file. The file should be an xml, and it will be validated against an xsd.
	 * @param file the file
	 * @return the command schema for the provided file
	 * @throws Exception
	 */
	public static CommandSchema makeSchema(File file) throws Exception {
		validateXML(file);
		CommandSchema parser = null;
		JAXBContext jaxbContext = JAXBContext.newInstance(CommandSchema.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		parser = (CommandSchema) jaxbUnmarshaller.unmarshal(file);
		return parser;
	}

	/**
	 * Validates the xml file provided against an xsd.
	 * @param file the file
	 * @throws Exception
	 */
	private static void validateXML(File file) throws Exception {
		Source xmlFile = new StreamSource(file);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = schemaFactory.newSchema(new StreamSource(xsdFile));
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}
}
