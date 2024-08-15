/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;
import model.Guideline;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DAT
 */
public class GuideLineDAO extends DBContext {

    public ArrayList<Guideline> findAll() {
        ArrayList<Guideline> guideLines = new ArrayList<>();
        try {
            String sql = "select * from guideline";
            PreparedStatement ps;
            ResultSet rs;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                guideLines.add(toGuideLine(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuideLineDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(guideLines.size());
        return guideLines;
    }

    private Guideline toGuideLine(ResultSet rs) throws SQLException {
        Guideline guideLine = new Guideline();
        guideLine.setGuideID(rs.getInt("guideID"));
        guideLine.setGuideName(rs.getString("guideName"));
        guideLine.setImg(rs.getString("img"));
        return guideLine;
    }
}
