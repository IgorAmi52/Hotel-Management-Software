package com.models;

import com.google.gson.JsonObject;
import com.models.enums.Role;

public interface Person {
	 public Role getRole();
	 public String getUserName();
	 public String getFullName();
	 public JsonObject getJson();

}
