package com.team08.controller.admin;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.team08.dto.ItemVO;
import com.team08.service.ItemService;

@WebServlet("/admin/items/*")
public class AdminItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static ItemService service = ItemService.getInstance();
	private String url = "";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		if(path.equals("/list")) {
			this.list(request);
		} else if(path.equals("/writeForm")) {
			this.writeForm(request);
		}else if(path.equals("/detail")) {
			this.detail(request);
		} else if(path.equals("/updateForm")) {
			this.updateForm(request);
		} else if(path.equals("/write")) {
			this.write(request);
		}  else if(path.equals("/update")) {
			this.update(request);
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void list(HttpServletRequest request) {
		this.url = "/admin/product/productList.jsp";
		String key = request.getParameter("key");
		String tpage = request.getParameter("tpage");
		if(key == null) {
			key = "";
		}
		if (tpage == null) {
			tpage="1";
		} else if (tpage.equals("")) {
			tpage = "1";
		}
		request.setAttribute("key", key);
		request.setAttribute("tpage", tpage);
		
		List<ItemVO> itemList = service.listItem(Integer.parseInt(tpage), key);
		
		String paging = service.pageNumber(Integer.parseInt(tpage), key, request.getContextPath());
		
		request.setAttribute("productList", itemList);
		int n = itemList.size();
		request.setAttribute("productListSize", n);
		request.setAttribute("paging", paging);
	}
	
	private void writeForm(HttpServletRequest request) {
		this.url = "/admin/product/productWrite.jsp";
		String kindList[] = {"Heels", "Boots", "Sandals", "Slipers", "Sneakers" };
		
		request.setAttribute("kindList", kindList);
	}
	
	private void write(HttpServletRequest request) throws IOException{
		this.url = "/admin/items/list";
		
		HttpSession session = request.getSession();
		
		int sizeLimit = 5 * 1024 * 1024;
		String savePath = "product_images";
		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		
		MultipartRequest multi = new MultipartRequest(request,
				uploadFilePath,
				sizeLimit,
				"UTF-8",
				new DefaultFileRenamePolicy()
		);
		
		ItemVO item = new ItemVO();
		item.setKind(multi.getParameter("kind"));
		item.setName(multi.getParameter("name"));
		item.setPrice1(Integer.parseInt(multi.getParameter("price1")));
		item.setPrice2(Integer.parseInt(multi.getParameter("price2")));
		item.setPrice3(
				Integer.parseInt(multi.getParameter("price2")) - Integer.parseInt(multi.getParameter("price1")));
		item.setContent(multi.getParameter("content"));
		item.setImage(multi.getFilesystemName("image"));
		
		service.insertItem(item);
	}
	
	private void detail(HttpServletRequest request) {
		url = "/admin/product/productDetail.jsp";
		String pseq = request.getParameter("pseq").trim();
		
		ItemVO item = service.getItem(pseq);
		
		request.setAttribute("productVO", item);
		
		String tpage = "1";
		if (request.getParameter("tpage") != null) {
			tpage = request.getParameter("tpage");
		}
		String kindList[] = {"0", "Heels", "Boots", "Sandals", "Slipers", "Sneakers" };
		request.setAttribute("tpage", tpage);
		int index = Integer.parseInt(item.getKind().trim());
		request.setAttribute("kind", kindList[index]);
	}
	
	private void updateForm(HttpServletRequest request) {
		url = "/admin/product/productUpdate.jsp";

		String pseq = request.getParameter("pseq").trim();

		ItemVO item = service.getItem(pseq);
		System.out.println("AdminItemController.updateForm() item.getUseyn = "+ item.getUseyn());

		request.setAttribute("productVO", item);

		String tpage = "1";
		if (request.getParameter("tpage") != null) {
			tpage = request.getParameter("tpage");
		}
		request.setAttribute("tpage", tpage);

		String kindList[] = { "Heels", "Boots", "Sandals", "Slipers", "Sneakers" };
		request.setAttribute("kindList", kindList);
		System.out.println("UPDATEfORM 호출ㄴ");
	}
	
	private void update(HttpServletRequest request) throws IOException {
		url = "/admin/items/list";

		HttpSession session = request.getSession();

		int sizeLimit = 5 * 1024 * 1024;
		String savePath = "product_images";
		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath(savePath);

		MultipartRequest multi = new MultipartRequest(request,
				uploadFilePath, 
				sizeLimit, 
				"UTF-8",
				new DefaultFileRenamePolicy() 
		); 

		ItemVO item = new ItemVO();
		item.setPseq(Integer.parseInt(multi.getParameter("pseq")));
		item.setKind(multi.getParameter("kind"));
		item.setName(multi.getParameter("name"));
		item.setPrice1(Integer.parseInt(multi.getParameter("price1")));
		item.setPrice2(Integer.parseInt(multi.getParameter("price2")));
		item.setPrice3(
				Integer.parseInt(multi.getParameter("price2")) - Integer.parseInt(multi.getParameter("price1")));
		item.setContent(multi.getParameter("content"));
		if (multi.getFilesystemName("image") == null) {
			item.setImage(multi.getParameter("nonmakeImg"));
		} else {
			item.setImage(multi.getFilesystemName("image"));
		}
		item.setUseyn(multi.getParameter("useyn"));
		System.out.println("AdminItemController.update() getParameter('useyn')=" + multi.getParameter("useyn"));
		item.setBestyn(multi.getParameter("bestyn"));


		service.updateItem(item);

	}
}
