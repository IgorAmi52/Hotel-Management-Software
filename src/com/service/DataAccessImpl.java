package com.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.DataTypes;

public class DataAccessImpl implements DataAccessInterface {
	private Gson gson = new Gson();

	public JsonObject getData(DataTypes dataType) throws IOException {
		String filePath = "data/" + dataType.getValue() + ".json";

		FileReader reader = new FileReader(filePath);
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

		return jsonObject;
	}

	public void setData(DataTypes dataType, JsonObject jsonObject) throws IOException {
		String filePath = "data/" + dataType.getValue() + ".json";

		FileWriter writer = new FileWriter(filePath);
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
	}

}
