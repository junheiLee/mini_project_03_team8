package com.team08.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team08.dto.ItemVO;

public class ItemDAO {
	public static int view_rows = 5;// 페이지의 개수
	public static int counts = 5; // 한 페이지에 나타낼 상품의 개수
	
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;

	private static ItemDAO instance = new ItemDAO();

	private ItemDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("MemberDAO() ERR : " + e.getMessage());
		}
	}

	public static ItemDAO getInstance() {
		return instance;
	}

	// 신상품
	public ArrayList<ItemVO> listNewProduct() {
		ArrayList<ItemVO> itemList = new ArrayList<ItemVO>();
		String sql = "select *  from new_pro_view";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ItemVO item = new ItemVO();
				item.setPseq(rs.getInt("pseq"));
				item.setName(rs.getString("name"));
				item.setPrice2(rs.getInt("price2"));
				item.setImage(rs.getString("image"));
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return itemList;
	}

	// 베스트 상품
	public ArrayList<ItemVO> listBestProduct() {
		ArrayList<ItemVO> productList = new ArrayList<ItemVO>();
		String sql = "select *  from best_pro_view";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ItemVO item = new ItemVO();
				item.setPseq(rs.getInt("pseq"));
				item.setName(rs.getString("name"));
				item.setPrice2(rs.getInt("price2"));
				item.setImage(rs.getString("image"));
				productList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return productList;
	}

	public ItemVO getProduct(String pseq) {
		ItemVO item = null;
		String sql = "select * from product where pseq=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pseq);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				item = new ItemVO();
				item.setPseq(rs.getInt("pseq"));
				item.setName(rs.getString("name"));
				item.setKind(rs.getString("kind"));
				item.setPrice1(rs.getInt("price1"));
				item.setPrice2(rs.getInt("price2"));
				item.setPrice3(rs.getInt("price3"));
				item.setContent(rs.getString("content"));
				item.setImage(rs.getString("image"));
				item.setUseyn(rs.getString("useyn"));
				item.setBestyn(rs.getString("bestyn"));
				item.setIndate(rs.getTimestamp("indate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return item;
	}

	public ArrayList<ItemVO> listKindProduct(String kind) {
		ArrayList<ItemVO> itemList = new ArrayList<ItemVO>();
		String sql = "select * from product where kind=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ItemVO item = new ItemVO();
				item.setPseq(rs.getInt("pseq"));
				item.setName(rs.getString("name"));
				item.setPrice2(rs.getInt("price2"));
				item.setImage(rs.getString("image"));
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return itemList;
	}

	/*
	 * * 관리자 모드에서 사용되는 메소드 *
	 */
	public int totalRecord(String product_name) {
		int total_pages = 0;
		String sql = "select count(*) from product where name like '%'||?||'%'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet pageset = null;

		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);

			if (product_name.equals("")) {
				pstmt.setString(1, "%");
			} else {
				pstmt.setString(1, product_name);
			}
			pageset = pstmt.executeQuery();

			if (pageset.next()) {
				total_pages = pageset.getInt(1); // 레코드의 개수
				pageset.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, null);
		}
		return total_pages;
	}

	public ArrayList<ItemVO> listProduct(int tpage, String product_name) {
		ArrayList<ItemVO> productList = new ArrayList<ItemVO>();

		String str = "select pseq, indate, name, price1, price2, useyn, bestyn "
				+ " from product where name like '%'||?||'%' order by useyn desc, pseq desc";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int absolutepage = 1;

		try {
			con = dataFactory.getConnection();
			absolutepage = (tpage - 1) * counts + 1;
			pstmt = con.prepareStatement(str, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			if (product_name.equals("")) {
				pstmt.setString(1, "%");
			} else {
				pstmt.setString(1, product_name);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rs.absolute(absolutepage);
				int count = 0;

				while (count < counts) {
					ItemVO item = new ItemVO();
					item.setPseq(rs.getInt(1));
					item.setIndate(rs.getTimestamp(2));
					item.setName(rs.getString(3));
					item.setPrice1(rs.getInt(4));
					item.setPrice2(rs.getInt(5));
					item.setUseyn(rs.getString(6));
					item.setBestyn(rs.getString(7));
					productList.add(item);
					if (rs.isLast()) {
						break;
					}
					rs.next();
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return productList;
	}

	public int insertProduct(ItemVO item) {
		int result = 0;

		String sql = "insert into product (" + "pseq, kind, name, price1, price2, price3, content, image) "
				+ "values(product_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item.getKind());
			pstmt.setString(2, item.getName());
			pstmt.setInt(3, item.getPrice1());
			pstmt.setInt(4, item.getPrice2());
			pstmt.setInt(5, item.getPrice3());
			pstmt.setString(6, item.getContent());
			pstmt.setString(7, item.getImage());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("추가 실패");
			e.printStackTrace();
		} finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public int updateProduct(ItemVO item) {
		int result = -1;
		String sql = "update product set kind=?, useyn=?, name=?"
				+ ", price1=?, price2=?, price3=?, content=?, image=?, bestyn=? " + "where pseq=?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item.getKind());
			pstmt.setString(2, item.getUseyn());
			System.out.println("ItemDAO.updateProduct() item.getUseyn= " + item.getUseyn());
			pstmt.setString(3, item.getName());
			pstmt.setInt(4, item.getPrice1());
			pstmt.setInt(5, item.getPrice2());
			pstmt.setInt(6, item.getPrice3());
			pstmt.setString(7, item.getContent());
			pstmt.setString(8, item.getImage());
			pstmt.setString(9, item.getBestyn());
			pstmt.setInt(10, item.getPseq());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, null);
		}
		return result;
	}
	
	private void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
	    if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    if (stmt != null) {
	        try {
	            stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    if (conn != null) {
	        try {
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
