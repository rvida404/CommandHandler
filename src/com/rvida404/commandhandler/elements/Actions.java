package com.rvida404.commandhandler.elements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.rvida404.commandhandler.Constants;

public class Actions {

	public static class Action {

		private String	name;
		private String	param;
		private String	paramSeparator	= Constants.DEFAULT_SEPARATOR;
		private String	storeIn;
		private String	responseOnError;

		public String getName() {
			return name;
		}

		@XmlAttribute
		public void setName(String name) {
			this.name = name;
		}

		public String getParam() {
			return param;
		}

		@XmlAttribute
		public void setParam(String param) {
			this.param = param;
		}

		public String getParamSeparator() {
			return paramSeparator;
		}

		@XmlAttribute
		public void setParamSeparator(String paramSeparator) {
			this.paramSeparator = paramSeparator;
		}

		public String getStoreIn() {
			return storeIn;
		}

		@XmlAttribute
		public void setStoreIn(String storeIn) {
			this.storeIn = storeIn;
		}

		public String getResponseOnError() {
			return responseOnError;
		}

		@XmlAttribute
		public void setResponseOnError(String responseOnError) {
			this.responseOnError = responseOnError;
		}
	}

	private List<Action> action = new ArrayList<>();

	public List<Action> getAction() {
		return action;
	}

	@XmlElement
	public void setAction(List<Action> action) {
		this.action = action;
	}

}
