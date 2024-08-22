/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.DBContext;
import model.News;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NewDAO extends DBContext {

    public List<News> getNewsList() {
        List<News> news = new ArrayList<>();
        String sql = "SELECT [newID]\n"
                + "      ,[newTitle]\n"
                 + "      ,[description]\n"
                + "      ,[creatAt]\n"
                + "      ,[img]\n"
                + "  FROM [HL_Motel].[dbo].[news]";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                News News = new News();
                News.setNewId(rs.getInt("newId"));
                News.setCreateAt(rs.getString("creatAt"));
                News.setNewTitle(rs.getString("newTitle"));
                News.setDescription(rs.getString ("description"));
                News.setImg(rs.getString("img"));
                news.add(News);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }
    
    
     public int insertNews(News news) {
        int n = 0;
        String query = "INSERT INTO [dbo].[news]\n"
                + "           ([newTitle]\n"
                + "           ,[description]\n"
                + "           ,[img]\n"
                + "           ,[creatAt])\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";
         try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
             ps.setString(1, news.getNewTitle());
             ps.setString(2, news.getDescription());
             ps.setString(3, news.getImg());
             ps.setString(4, news.getCreateAt());
        
         n = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
     
     public int updateNews(News news) {
        int n = 0;
        String query = "String query = \"UPDATE [dbo].[news]\\n\"\n"
                + "                 + \"SET [newTitle] = ?,\\n\"\n"
                + "                 + \"    [description] = ?,\\n\"\n"
                + "                 + \"    [img] = ?,\\n\"\n"
                + "                 + \"    [creatAt] = ?\\n\"\n"
                + "                 + \"WHERE [newID] = ?\";\n";

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

    public List<News> getNewsDetails(int newId) {
        List<News> news = new ArrayList<>();
        String sql = "SELECT [newID]\n"
                + "      ,[creatAt]\n"
                + "      ,[newTitle]\n"
                + "      ,[description]\n"
                + "      ,[img]\n"
                + "  FROM [HL_Motel].[dbo].[news] where newID = ?";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                News News = new News();

                News.setNewId(rs.getInt("newId"));
                News.setCreateAt(rs.getString("creatAt"));
                News.setNewTitle(rs.getString("newTitle"));
                News.setDescription(rs.getString("description"));
                News.setImg(rs.getString("img"));

                news.add(News);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public static void main(String[] args) {
        NewDAO NewDAO = new NewDAO();
        List<News> news = NewDAO.getNewsList();
        
        for (News aNew : news) {
            System.out.println(aNew.getNewId());
        }
    }
}
//getNewsDetails("1");
