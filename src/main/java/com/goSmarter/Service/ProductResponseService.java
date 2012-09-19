package com.goSmarter.Service;

import java.util.List;

import com.goSmarter.dto.Item;

public interface ProductResponseService {

	public List<Item> filterAsPerUserFriendsRelevance(List<Item> productListFilteredAsperUserTaste, int id) ;
}
