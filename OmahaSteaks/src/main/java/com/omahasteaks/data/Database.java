package com.omahasteaks.data;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.logigear.helper.JsonHelper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class Database {

	// private String jsonFile;

	// public Database(String jsonFile) {
	// this.jsonFile = jsonFile;
	// }

	protected <T> T getData(Type classType, String jsonFile) {
		return JsonHelper.getData(jsonFile, classType);
	}

	protected <T> List<T> getListData(Type classType, String jsonFile) {
		return JsonHelper.getListData(jsonFile, classType);
	}

	protected <T> List<T> getListFromJsonObj(JsonElement jsonElement, Type classType) {
		Gson gson = new Gson();
		List<T> lst = new ArrayList<T>();
		lst = gson.fromJson(jsonElement, classType);
		return lst;
	}

	protected <T> List<T> getListFromCSVFile(String csvFile, Class<T> clazz) {
		ArrayList<T> lstResults = new ArrayList<T>();
		try {
			Reader reader = Files.newBufferedReader(Paths.get(csvFile));
			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader).withType(clazz).withIgnoreLeadingWhiteSpace(true).build();
			Iterator<T> csvIterator = csvToBean.iterator();
			csvIterator.forEachRemaining(lstResults::add);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (lstResults.size() > 0) ? lstResults : null;
	}
}
