package com.team08.service;

import java.util.ArrayList;
import java.util.List;

import com.team08.dao.ItemDAO;
import com.team08.dto.ItemVO;

public class ItemService {

	private static ItemService instance = new ItemService();
	private ItemDAO dao;
	
	private ItemService() {
		this.dao = ItemDAO.getInstance();
	}
	
	public static ItemService getInstance() {
		return instance;
	}
	
	/**
	 * 상품 번호에 해당하는 ProductVO 가져오는 메서드
	 * 
	 * @param pseq 상품 번호
	 * @return ProductVO
	 */
	public ItemVO getProduct(String pseq) {
		return dao.getProduct(pseq);
	}

	/**
	 * 상품 목룍에 해당하는 상품 목록 가져오는 메서드	
	 * 
	 * @param kind 상품 종료
	 * @return ArrayList<ProductVO>
	 */
	public ArrayList<ItemVO> listKindProduct(String kind) {
		return dao.listKindProduct(kind);
	}
	
	public List<ItemVO> listItem(int tpage, String key){
		return dao.listProduct(tpage, key);
	}
	
	public String pageNumber(int tpage, String key, String path) {
		return dao.pageNumber(tpage, key, path);
	}
	
	public ArrayList<ItemVO> listNewProduct() {
		return dao.listNewProduct();
	}
	
	public ArrayList<ItemVO> listBestProduct() {
		return dao.listBestProduct();
	}
	
	public void insertItem(ItemVO item) {
		dao.insertProduct(item);
	}
	
	public ItemVO getItem(String pseq) {
		return dao.getProduct(pseq);
	}
	
	public void updateItem(ItemVO item) {
		dao.updateProduct(item);
	}
}
