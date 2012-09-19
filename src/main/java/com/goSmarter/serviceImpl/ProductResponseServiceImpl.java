package com.goSmarter.serviceImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.goSmarter.Service.ProductResponseService;
import com.goSmarter.dto.Item;
import com.goSmarter.dto.UserItemPurchaseHistory;

public class ProductResponseServiceImpl implements ProductResponseService {

	SqlMapClientTemplate ibatisTemplate;

	public List<Item> filterAsPerUserFriendsRelevance(
			List<Item> productListFilteredAsperUserTaste,
			int id) {

		// TODO: Weka or Mahout implementation of recommendations of his friends
		// and his past data

		// get all the user item liked list from the database, get the brand
		// details
		// from that and check if the ProductFromApi list belongs to the same
		// brand and subbrand and filter out only those items
		List<Item> recommendedProducts = getListFromRecommendationEngine(id);

		ArrayList<Item> list3 = new ArrayList<Item>();

		for (Item productFromApi : productListFilteredAsperUserTaste) {
			for (Item item : recommendedProducts) {
				if (productFromApi.getBrand().toLowerCase()
						.equals(item.getBrand().toLowerCase())) {
					list3.add(productFromApi);
				}
			}
		}
		return list3;
	}

	/**
	 * 
	 * @param list1
				
	 * This is where Mahout is used
	 * @return
	 */
	private List<Item> getListFromRecommendationEngine(int id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", id);

		List<UserItemPurchaseHistory> list2 = ibatisTemplate.queryForList(
				"GoSmarter.userItemList", map);

		assertEquals(1, list2.size());
		assertEquals(list2.get(0).getItemId(), 1);

		map = new HashMap<String, Object>();
		map.put("itemId", list2.get(0).getItemId());
		Item item = (Item) ibatisTemplate.queryForObject(
				"GoSmarter.itemDetails", map);
		assertEquals(item.getName(), "FERRARI ENZO");

		List<Item> list3 = new ArrayList<Item>();
		list3.add(item);
		return list3;
	}
}
