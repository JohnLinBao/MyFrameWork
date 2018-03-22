package com.alfred.framework.utils;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;



public class UtilDateSerializer implements com.google.gson.JsonSerializer<Date>{

	@Override
	public JsonElement serialize(Date arg0, Type arg1,
			JsonSerializationContext arg2) {
		return new JsonPrimitive(arg0.getTime());
	}

}
