
package com.service;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.models.enums.DataTypes;

public interface DataAccess {
	JsonObject getData(DataTypes dataType) throws IOException;

	void setData(DataTypes dataType, JsonObject jsonObject) throws IOException;
}
