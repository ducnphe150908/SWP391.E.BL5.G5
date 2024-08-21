/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Slider;

/**
 *
 * @author quanb
 */
public class SliderDAO extends DBContext {

    public List<Slider> getAllSliders() {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM Slider where SliderStatus = 1";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSliderId(rs.getInt("SliderId"));
                slider.setSliderName(rs.getString("SliderName"));
                slider.setSliderImg(rs.getString("SliderImg"));
                slider.setSliderDate(rs.getString("SliderDate"));
                slider.setSliderStatus(rs.getBoolean("SliderStatus"));
                sliders.add(slider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sliders;
    }

    public List<Slider> searchByText(int pageIndex, int pageSize, String search) {
    List<Slider> sliders = new ArrayList<>();
    String sql = "SELECT [SliderId],\n"
            + "      [SliderName],\n"
            + "      [SliderImg],\n"
            + "      [SliderDate],\n"
            + "      [SliderStatus]\n"
            + "  FROM [HL_Motel].[dbo].[Slider]\n where SliderName like ? "
            + "ORDER BY [SliderId]\n"
            + "OFFSET ? ROWS\n"
            + "FETCH NEXT ? ROWS ONLY";

    try {
        java.sql.Connection conn = connection;
        PreparedStatement ps = conn.prepareStatement(sql);
        int offset = (pageIndex - 1) * pageSize;
        ps.setString(1, "%" + search + "%");
        ps.setInt(2, offset);
        ps.setInt(3, pageSize);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Slider slider = new Slider();

            slider.setSliderId(rs.getInt("SliderId"));
            slider.setSliderName(rs.getString("SliderName"));
            slider.setSliderImg(rs.getString("SliderImg"));
            slider.setSliderDate(rs.getString("SliderDate"));  // Đã sửa lỗi
            slider.setSliderStatus(rs.getBoolean("SliderStatus"));
            sliders.add(slider);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return sliders;
}


    public List<Slider> getSliderDetails(int sliderId) {
        List<Slider> slider = new ArrayList<>();
        String sql = "SELECT [SliderId]\n"
                + "      ,[SliderName]\n"
                + "      ,[SliderImg]\n"
                + "      ,[SliderDate]\n"
                + "      ,[SliderStatus]\n"
                + "  FROM [HL_Motel].[dbo].[Slider] where SliderId = ?";
        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sliderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Slider Slider = new Slider();
                Slider.setSliderId(rs.getInt("SliderId"));
                Slider.setSliderName(rs.getString("SliderName"));
                Slider.setSliderImg(rs.getString("SliderImg"));
                Slider.setSliderDate(rs.getString("SliderDate"));
                Slider.setSliderStatus(rs.getBoolean("SliderStatus"));

                slider.add(Slider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slider;
    }

    public Slider getSliderById(int id) {
        Slider slider = null;
        String query = "SELECT * FROM [dbo].[Slider] WHERE SliderId = ?";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                slider = new Slider();
                slider.setSliderId(rs.getInt("SliderId"));
                slider.setSliderName(rs.getString("SliderName"));
                slider.setSliderImg(rs.getString("SliderImg"));
                slider.setSliderDate(rs.getString("SliderDate"));
                slider.setSliderStatus(rs.getBoolean("SliderStatus"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slider;
    }
    public int updateSlider(Slider slider) {
        int n = 0;
        String query = "UPDATE [dbo].[Slider] \n"
                + "SET [SliderName] = ?, \n"
                + "    [SliderImg] = ?, \n"
                + "    [SliderDate] = ?, \n"
                + "    [SliderStatus] = ? \n"
                + "WHERE [newID] = ?";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, slider.getSliderName());
            ps.setString(2, slider.getSliderImg());
            ps.setString(3, slider.getSliderDate());
            ps.setBoolean(4, slider.isSliderStatus());
            ps.setInt(5, slider.getSliderId());
            n = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();  // In thông báo lỗi ra console để dễ dàng gỡ lỗi
            System.out.println(e.getMessage());
        }
        return n;
    }
    public int insertSlider(Slider slider) {
        int n = 0;
        String query = "INSERT INTO [dbo].[Slider]\n"
                + "           ([SliderName]\n"
                + "           ,[SliderImg]\n"
                + "           ,[SliderDate]\n"
                + "           ,[SliderStatus])\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";
        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, slider.getSliderName());
            ps.setString(2, slider.getSliderImg());
            ps.setString(3, slider.getSliderDate());
            ps.setBoolean(4, slider.isSliderStatus());

            n = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
     public List<Slider> getSliderList(int pageIndex, int pageSize) {
        List<Slider> news = new ArrayList<>();
        String sql = "SELECT [SliderId],\n"
                + "      [SliderName],\n"
                + "      [SliderImg],\n"
                + "      [SliderDate],\n"
                + "      [SliderStatus]\n"
                + "  FROM [HL_Motel].[dbo].[Slider]\n"
                + "ORDER BY [SliderId]\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            int offset = (pageIndex - 1) * pageSize;
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSliderId(rs.getInt("SliderId"));
                slider.setSliderName(rs.getString("SliderName"));
                slider.setSliderImg(rs.getString("SliderImg"));
                slider.setSliderDate(rs.getString("SliderDate"));
                slider.setSliderStatus(rs.getBoolean("SliderStatus"));
                news.add(slider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }
     public int DeleteSlider(Slider slider) {
        int n = 0;
        String query = "DELETE FROM [dbo].[Slider] WHERE [SliderId] = ?";
        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, slider.getSliderId());
            n = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
     public boolean updateSliderStatus(int sliderId, boolean sliderStatus) {
        String sql = "UPDATE Slider SET SliderStatus = ? WHERE SliderId = ?";
        try  {
             java.sql.Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, sliderStatus);
            ps.setInt(2, sliderId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        SliderDAO d = new SliderDAO();
//        List<Slider> sliders = d.getSliderList(1, 2);
//        for (Slider slider : sliders) {
//            System.out.println("ID: " + slider.getSliderId());
//            System.out.println("Name: " + slider.getSliderName());
//            System.out.println("Image: " + slider.getSliderImg());
//            System.out.println("Date: " + slider.getSliderDate());
//            System.out.println("Status: " + slider.isSliderStatus());
//            System.out.println("---------------------------");
//        }
     
//     public static void main(String[] args) {
//    // Giả sử bạn đã tạo một instance của DAO chứa hàm searchByText
//    SliderDAO sliderDAO = new SliderDAO();
//
//    // Gọi hàm searchByText để kiểm tra
//    List<Slider> sliders = sliderDAO.searchByText(1, 2, "n");
//
//    // In ra kết quả
//    for (Slider slider : sliders) {
//        System.out.println("ID: " + slider.getSliderId());
//        System.out.println("Name: " + slider.getSliderName());
//        System.out.println("Image: " + slider.getSliderImg());
//        System.out.println("Date: " + slider.getSliderDate());
//        System.out.println("Status: " + slider.isSliderStatus());
//        System.out.println("-----------------------------");
//    }
//     }  
     
     public static void main(String[] args) {
        // Tạo một đối tượng Slider với các thuộc tính cần thiết
        Slider slider = new Slider();
        slider.setSliderName("Test");
        slider.setSliderImg("test_image.png");
        slider.setSliderDate("2024-08-21"); // Định dạng ngày tháng theo yêu cầu của bạn
        slider.setSliderStatus(true); // hoặc false, tùy thuộc vào trạng thái bạn muốn kiểm tra

        // Khởi tạo đối tượng của lớp chứa phương thức insertSlider
        SliderDAO sliderDAO = new SliderDAO();

        // Gọi phương thức insertSlider và lưu kết quả
        int result = sliderDAO.insertSlider(slider);

        // Kiểm tra kết quả trả về
        if (result > 0) {
            System.out.println("Slider inserted successfully!");
        } else {
            System.out.println("Failed to insert slider.");
        }
    }
}
