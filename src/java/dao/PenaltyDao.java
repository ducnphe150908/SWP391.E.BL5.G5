package dao;

import model.PenaltyList;
import model.Penalty;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.Connection;
import java.sql.SQLException;
import dao.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;
import model.Rule;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class PenaltyDao extends DBContext {

    public List<PenaltyList> getPenList() {
        List<PenaltyList> lpen = new ArrayList<>();
        String sql = "SELECT [penID]\n" +
                            "      ,v.[roomNumber]\n" +
                            "      ,[description]\n" +
                            "      ,[penDate]\n" +
                            "      ,r.[ruleName]\n" +
                            "      ,[penStatus]\n" +
                            "  FROM [HL_Motel].[dbo].[penalty]\n" +
                            "  join [rule] r on r.ruleID = penalty.ruleID\n" +
                            "  join [room] v on v.roomID = penalty.roomID";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PenaltyList PenaltyList = new PenaltyList();

                PenaltyList.setPenId(rs.getInt("penId"));
                PenaltyList.setRoomNumber(rs.getInt("roomNumber"));
                PenaltyList.setDescription(rs.getString("description"));
                PenaltyList.setPenDate(rs.getDate("penDate"));
                PenaltyList.setRuleName(rs.getString("ruleName"));
                PenaltyList.setPenStatus(rs.getBoolean("penStatus"));
                lpen.add(PenaltyList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lpen;
    }

    public PenaltyList selectUpdateByPenID(String id) {
        String sql = "select penID, reportID,accuseID,roomID,description,penDate,penalty.ruleID,[rule].ruleName,penStatus\n"
                + "from penalty join [rule] \n"
                + "on penalty.ruleID = [rule].ruleID\n"
                + "where penID = ?";
        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PenaltyList(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getBoolean(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void addPenalty(String roomID, String description, String penDate, String ruleID) {
        String sql = "INSERT INTO HL_Motel.dbo.penalty (roomID, reportID, accuseID, description, penDate, ruleID, penStatus) VALUES (?,1,1, ?, ?, ?, 'false')";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomID);
            ps.setString(2, description);
            ps.setString(3, penDate);
            ps.setString(4, ruleID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePenalty(String penStatus, String penId) {
        String sql = "UPDATE HL_Motel.dbo.penalty SET  penStatus =? WHERE penID =?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, penStatus);
            ps.setString(2, penId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deletePenalty(String penId) {
        String sql = "DELETE FROM HL_Motel.dbo.penalty WHERE penID = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, penId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Penalty findById(int id) {
        try {
            String sql = "select * from penalty where penID = ?";
            PreparedStatement ps;
            ResultSet rs;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return toPenalty(rs);
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public int insert(Penalty model) {
        try {
            String sql = "insert into penalty(roomID, description, penDate, ruleID, penStatus, evidenceImg) values (?,?,?,?,?,?)";
            PreparedStatement ps;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, model.getRoomID().getRoomID());
            ps.setString(2, model.getDescription());
            ps.setDate(3, model.getPenDate());
            ps.setInt(4, model.getRuleID().getRuleID());
            ps.setInt(5, model.getPenStatus());
            ps.setString(6, model.getEvidenceImg());
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {

        }
        return -1;
    }

    public int update(Penalty model) {
        try {
            String sql = "update penalty set roomID = ?, description = ?, penDate = ?, ruleID = ?, penStatus = ?, evidenceImg = ? where penID = ?";
            PreparedStatement ps;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, model.getRoomID().getRoomID());
            ps.setString(2, model.getDescription());
            ps.setDate(3, model.getPenDate());
            ps.setInt(4, model.getRuleID().getRuleID());
            ps.setInt(5, model.getPenStatus());
            ps.setString(6, model.getEvidenceImg());
            ps.setInt(7, model.getPenID());
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {

        }
        return -1;
    }

    public int remove(int id) {
        try {
            String sql = "delete from penalty where penID = ?";
            PreparedStatement ps;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {

        }
        return -1;
    }

    public int updateStatus(Penalty model) {
        try {
            String sql = "update penalty set penStatus = ? where penID = ?";
            PreparedStatement ps;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, model.getPenStatus());
            ps.setInt(2, model.getPenID());
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {

        }
        return -1;
    }

    private Penalty toPenalty(ResultSet rs) throws SQLException {
        Penalty penalty = new Penalty();
        penalty.setPenID(rs.getInt("penID"));

        RoomDAO dbRoom = new RoomDAO();
        Room room = dbRoom.findById(rs.getInt("roomID"));
        penalty.setRoomID(room);

        penalty.setDescription(rs.getString("description"));
        penalty.setPenDate(rs.getDate("penDate"));

        RuleDAO dbRule = new RuleDAO();
        Rule rule = dbRule.findById(rs.getInt("ruleID"));
        penalty.setRuleID(rule);

        penalty.setPenStatus(rs.getInt("penStatus"));
        penalty.setEvidenceImg(rs.getString("evidenceImg"));

        return penalty;
    }

    public ArrayList<Penalty> findByRuleId(int ruleId) {
        ArrayList<Penalty> penaltys = new ArrayList<>();
        try {
            String sql = "select * from penalty where ruleID = ?";
            PreparedStatement ps;
            ResultSet rs;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, ruleId);
            rs = ps.executeQuery();

            while (rs.next()) {
                penaltys.add(toPenalty(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PenaltyDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return penaltys;
    }

    public ArrayList<Penalty> findAll() {
        ArrayList<Penalty> penaltys = new ArrayList<>();
        try {
            String sql = "select * from penalty";
            PreparedStatement ps;
            ResultSet rs;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                penaltys.add(toPenalty(rs));
            }
        } catch (SQLException ex) {

        }
        return penaltys;
    }

    public static void main(String[] args) {
        PenaltyDao penaltyDao = new PenaltyDao();
        List<Penalty> penalty = penaltyDao.findAll();
        for (Penalty p : penalty) {
            System.out.println(p);
        }

    }

    // Test getPenList
//        System.out.println("Testing getPenList()...");
//        List<PenaltyList> penalties = penaltyDao.getPenList();
//        for (PenaltyList p : penalties) {
//            System.out.println(p);
//        }
    // Test updatePenalty
//        System.out.println("Testing updatePenalty()...");
//        int penId = 64; // Get the last added penalty ID
//        penaltyDao.updatePenalty("Updated description", "2024-07-06", 1, true, penId);
//        System.out.println("Penalty updated.");
    public List<PenaltyList> getRenterPenList(int renterID) {
        List<PenaltyList> lpen = new ArrayList<>();
        String sql = "SELECT \n"
                + "      a.roomID\n"
                + "      ,description\n"
                + "      ,penDate\n"
                + "      ,c.ruleName\n"
                + "      ,penStatus\n"
                + "  FROM HL_Motel.dbo.penalty a \n"
                + "   INNER JOIN renter b ON a.roomID = b.roomID\n"
                + "	INNER JOIN [rule] c ON a.ruleID = c.ruleID\n"
                + "  where b.renterID = ?";

        try {
            java.sql.Connection conn = connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, renterID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PenaltyList PenaltyList = new PenaltyList();
                PenaltyList.setRoomId(rs.getInt("roomId"));
                PenaltyList.setDescription(rs.getString("description"));
                PenaltyList.setPenDate(rs.getDate("penDate"));
                PenaltyList.setRuleName(rs.getString("ruleName"));
                PenaltyList.setPenStatus(rs.getBoolean("penStatus"));
                lpen.add(PenaltyList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lpen;
    }
}
