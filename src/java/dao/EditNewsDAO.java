/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import model.News;
import java.sql.ResultSet;

/**
 *
 * @author quan
 */
public class EditNewsDAO extends DBContext {

    public int updateNews(News news) {
        int n = 0;
        String query = "UPDATE [dbo].[news] \n"
                + "SET [newTitle] = ?, \n"
                + "    [description] = ?, \n"
                + "    [img] = ?, \n"
                + "    [creatAt] = ? \n"
                + "WHERE [newID] = ?";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, news.getNewTitle());
            ps.setString(2, news.getDescription());
            ps.setString(3, news.getImg());
            ps.setString(4, news.getCreateAt());
            ps.setInt(5, news.getNewId());

            n = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();  // In thông báo lỗi ra console để dễ dàng gỡ lỗi
            System.out.println(e.getMessage());
        }
        return n;
    }

    public News getNewsById(int id) {
        News news = null;
        String query = "SELECT * FROM [dbo].[news] WHERE newID = ?";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                news = new News();
                news.setNewId(rs.getInt("newID"));
                news.setNewTitle(rs.getString("newTitle"));
                news.setDescription(rs.getString("description"));
                news.setImg(rs.getString("img"));
                news.setCreateAt(rs.getString("creatAt"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public int DeleteNews(News news) {
        int n = 0;
        String query = "DELETE FROM [dbo].[news] WHERE [newID] = ?";
        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, news.getNewId());
            n = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

//    public static void main(String[] args) {
//        News news = new News();
//        news.setNewId(52); // ID của tin tức cần cập nhật
//        news.setNewTitle("Updated Title");
//        news.setDescription("Updated Description");
//        news.setImg("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"); // Đảm bảo giá trị này phù hợp
//        news.setCreateAt("2024-08-13"); // Ngày tạo
//        EditNewsDAO a = new EditNewsDAO();
//        // Cập nhật tin tức
//        int result = a.updateNews(news);
//
//        // Kiểm tra kết quả
//        if (result > 0) {
//            System.out.println("News updated successfully.");
//        } else {
//            System.out.println("Failed to update news.");
//        }
//    }
}
