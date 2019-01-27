package com.rvida404.commandhandler.elements;

import javax.xml.bind.annotation.XmlAttribute;

public class NullToken {

	private String value;

	public String getValue() {
		return value;
	}

	@XmlAttribute
	public void setValue(String value) {
		this.value = value;
	}
}
