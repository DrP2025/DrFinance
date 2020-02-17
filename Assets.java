package com.dylan.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Assets {
	@JsonProperty("Asset")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Asset> assets = new ArrayList<>();

	public List<Asset> getAssets() {
		return assets;
	}

	public void setLoans(List<Asset> assets) {
		this.assets = assets;
	}
}
