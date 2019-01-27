package com.rvida404.commandhandler.elements;

import javax.xml.bind.annotation.XmlAttribute;

public class Failure {
	private String value = null;

	public String getValue() {
		return value;
	}

	@XmlAttribute
	public void setValue(String value) {
		this.value = value;
	}
}
