package com.omahasteaks.data;

import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.omahasteaks.data.objects.Department;
import com.omahasteaks.utils.common.Constants;

public class ListDepartments extends Database {
    private List<Department> listDepartment;

    private String jsonFile = "src/test/resources/data/%s/department_data.json";

    public ListDepartments() {	
	listDepartment = new ArrayList<Department>();
    }

    @SuppressWarnings("serial")
    public void getListDepartments() {
	listDepartment = getData(new TypeToken<List<Department>>() {
	}.getType(), String.format(jsonFile,Constants.SITE));
    }

    public String[] getArray() {
	getListDepartments();
	List<String> result = new ArrayList<>();
	for (Department department : listDepartment) {
	    result.add(department.departmentName);
	}
	return result.toArray(new String[result.size()]);
    }
    
   
}
