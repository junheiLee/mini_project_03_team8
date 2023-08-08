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

	public List<ItemVO> listItem(int tpage, String key) {
		return dao.listProduct(tpage, key);
	}

	public String pageNumber(int tpage, String name, String path) {
		String str = "";

		int total_pages = dao.totalRecord(name);
		int page_count = total_pages / ItemDAO.counts + 1;

		if (total_pages % ItemDAO.counts == 0) {
			page_count--;
		}
		if (tpage < 1) {
			tpage = 1;
		}

		int start_page = tpage - (tpage % ItemDAO.view_rows) + 1;
		int end_page = start_page + (ItemDAO.counts - 1);

		if (end_page > page_count) {
			end_page = page_count;
		}
		if (start_page > ItemDAO.view_rows) {
			str += "<a href='" + path + "/admin/items/list?tpage=1&key=" + name + "'>&lt;&lt;</a>&nbsp;&nbsp;";
			str += "<a href='" + path + "/admin/items/list?tpage=" + (start_page - 1);
			str += "&key=<%=product_name%>'>&lt;</a>&nbsp;&nbsp;";
		}

		for (int i = start_page; i <= end_page; i++) {
			if (i == tpage) {
				str += "<font color=red>[" + i + "]&nbsp;&nbsp;</font>";
			} else {
				str += "<a href='" + path + "/admin/items/list?tpage=" + i + "&key=" + name + "'>[" + i
						+ "]</a>&nbsp;&nbsp;";
			}
		}

		if (page_count > end_page) {
			str += "<a href='" + path + "/admin/items/list?tpage=" + (end_page + 1) + "&key=" + name
					+ "'> &gt; </a>&nbsp;&nbsp;";
			str += "<a href='" + path + "/admin/items/list?tpage=" + page_count + "&key=" + name
					+ "'> &gt; &gt; </a>&nbsp;&nbsp;";
		}
		return str;
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
		if (item.getUseyn() == null) {
			item.setUseyn("n");
		}
		if (item.getBestyn() == null) {
			item.setBestyn("n");
		}
		dao.updateProduct(item);
	}
}
