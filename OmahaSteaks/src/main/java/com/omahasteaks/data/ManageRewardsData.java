package com.omahasteaks.data;

import java.util.List;

import com.google.common.reflect.TypeToken;
import com.omahasteaks.data.objects.RewardData;
import com.omahasteaks.utils.common.Constants;

public class ManageRewardsData extends Database {
	private List<RewardData> lstRewardSearchData;
	private static String jsonFile = "src/test/resources/data/omk/rewards_data.json";
	
	public ManageRewardsData() {
		getListRewardsSearchData();
	}
    
    @SuppressWarnings("serial")
    public void getListRewardsSearchData() {
    	lstRewardSearchData = getData(new TypeToken<List<RewardData>>() {
	}.getType(), String.format(jsonFile,Constants.SITE));
    }

	public List<RewardData> getLstRewardSearchData() {
		return lstRewardSearchData;
	}

	public void setLstRewardSearchData(List<RewardData> lstRewardSearchData) {
		this.lstRewardSearchData = lstRewardSearchData;
	}
	
	public String[] getResultsBySearchKeyword(String searchKeyword) {
		String[] lstResults = null;
		for( int i=0; i< lstRewardSearchData.size(); i++) {
			if( lstRewardSearchData.get(i).getKeyWord().equals(searchKeyword))
				lstResults = lstRewardSearchData.get(i).getLstResults();
		}
		return lstResults;
	}
}
