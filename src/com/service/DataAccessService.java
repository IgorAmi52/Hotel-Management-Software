package com.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.DataTypes;

public class DataAccessService {
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;
	
	public static JsonObject getData(DataTypes dataType) throws IOException {
		String filePath = "data/" + dataType.getValue() + ".json";
		
		reader = new FileReader(filePath);
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		return jsonObject;
	}
	
	public static void setData(DataTypes dataType, JsonObject jsonObject) throws IOException{
		String filePath ="data/" + dataType.getValue() + ".json";
		
	    FileWriter writer = new FileWriter(filePath);
	    writer.write(new Gson().toJson(jsonObject));
	    writer.close();
	}

}
