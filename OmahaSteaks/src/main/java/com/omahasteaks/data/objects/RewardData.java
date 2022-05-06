package com.omahasteaks.data.objects;

import java.util.Arrays;

public class RewardData {
	private String keyWord;
	private String[] results;
	
	public String getKeyWord() {
		return this.keyWord;
	}

	public String[] getLstResults() {
		return results;
	}

	public void setLstResults(String[] lstResults) {
		this.results = lstResults;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Override
	public String toString() {
		return "RewardData [keyWord=" + keyWord + ", lstResults=" + Arrays.toString(results) + "]";
	}
}
