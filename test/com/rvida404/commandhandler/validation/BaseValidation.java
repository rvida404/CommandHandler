package com.rvida404.commandhandler.validation;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.rvida404.commandhandler.BaseTest;

public abstract class BaseValidation extends BaseTest {

	protected void expectTofail(String file, String errorSubstring) {
		try {
			assertNull(getHandler(file));
			fail();
		} catch (Exception e) {
			boolean checksOut = errorSubstring != null && e.toString().contains(errorSubstring);
			if (!checksOut) {
				System.out.println(e.getMessage());
			}
			assertTrue(checksOut);
		}
	}

	protected String elementHasNoChildren(String element) {
		return String.format(
				"Element '%s' must have no character or element information item [children], because the type's content type is empty.",
				element);
	}

	protected String invalidAttribute(String element, String attribute) {
		return String.format("Attribute '%s' is not allowed to appear in element '%s'.", attribute, element);
	}

	protected String missingAttribute(String element, String attribute) {
		return String.format("Attribute '%s' must appear on element '%s'.", attribute, element);
	}

	protected String missingElement(String parent, String missing) {
		return String.format("The content of element '%s' is not complete. One of '{%s}' is expected.", parent,
				missing);
	}

	protected String invalidContent(String elementStart) {
		return invalidContent(elementStart, null);
	}

	protected String invalidContent(String elementStart, String expected) {
		return String.format("Invalid content was found starting with element '%s'. ", elementStart) + //
				(expected != null //
						? String.format("One of '{%s}' is expected.", expected) //
						: "No child element is expected at this point.");
	}
}
