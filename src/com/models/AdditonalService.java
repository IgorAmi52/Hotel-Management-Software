package com.models;

import com.models.enums.AdditionalServiceType;

public class AdditonalService { //curently out of use
	private AdditionalServiceType type;

	public AdditonalService(AdditionalServiceType type) {
		this.type = type;
	}
	public AdditionalServiceType getType() {
		return this.type;
	}
}
