package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import model.Account;
import model.Renter;
import model.Room;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.News;
import model.UserDetail;
import model.Renter;
import java.sql.CallableStatement;


/**
 * Data Access Object for Renter-related operations.
 *
 * Author: creep
 */
public class RenterDAO extends MyDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public UserDetail RenterBasicDetail(String accountInput, String passwordInput) {
        UserDetail userDetail = null;
        String sql = "SELECT \n"
                + "a.userID, u.userName, u.userGender, u.userBirth, u.userAddress, u.userPhone, u.userAvatar, \n"
                + "a.userMail, a.userPassword, a.userRole\n"
                + "FROM \n"
                + "[user] u \n"
                + "JOIN \n"
                + "account a ON u.userID = a.userID \n"
                + "WHERE \n"
                + "a.userMail = ?  AND a.userPassword = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountInput);
            ps.setString(2, passwordInput);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userID = rs.getInt(1);
                    String userName = rs.getString(2);
                    String userGender = rs.getString(3);
                    String userBirth = rs.getString(4);
                    String userAddress = rs.getString(5);
                    String userPhone = rs.getString(6);
                    String userAvatar = rs.getString(7);
                    String userMail = rs.getString(8);
                    String userPassword = rs.getString(9);
                    int userRole = rs.getInt(10);

                    userDetail = new UserDetail(userID, userName, userGender, userBirth,
                            userAddress, userPhone, userAvatar, userMail, userPassword, userRole);
                }
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
        return userDetail;
    }
    
    
     // Retrieve user by ID
    public User getUserByID(int userID) {
        String sql = "SELECT * FROM [dbo].[user] WHERE userID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("userID"),
                        rs.getString("userName"),
                        rs.getString("userGender"),
                        rs.getString("userBirth"),
                        rs.getString("userAddress"),
                        rs.getString("userPhone"),
                        rs.getString("userAvatar")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            Logger.getLogger(RenterDAO.class.getName()).log(Level.SEVERE, "Failed to get user by ID", e);
            return null;
        }
    }
}
