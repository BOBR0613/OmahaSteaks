package com.omahasteaks.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import com.omahasteaks.data.objects.Item;

// additional category methods 
public class Category {
	private String categoryPath;
	private List<Item> listItems;

	public Category() {
		this.listItems = new ArrayList<Item>();
	}

	public Category(String catagoryName) {
		this.categoryPath = catagoryName;
		this.listItems = new ArrayList<Item>();
		
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String catagoryName) {
		this.categoryPath = catagoryName;
	}

	public void addItem(Item item) {
		listItems.add(item);
	}

	public List<Item> getListItems() {
		return listItems;
	}

	public Item getRandomItem() {
		Random rd = new Random();
		Item item = listItems.get(rd.nextInt(listItems.size()));
		return item;
	}
}
