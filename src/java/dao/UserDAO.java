/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Account;
import model.User;
import model.Room;
import java.math.BigDecimal;
import model.Renter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class UserDAO extends MyDAO {

    //Table - User
    /*
    1.userID - int
    2.userName - String
    3.userGender - String
    4.userBirth - String
    5.userAddress - String
    6.userPhone - String
    7.userAvatar
     */
    //List User Data
    public List<User> getUserList() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User]";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
        return list;
    }

    public List<User> getUserAvailable() {
        List<User> list = new ArrayList<>();
        String sql = "  select * from [user] u \n"
                + "  join account a on u.userID = a.userID \n"
                + "  where a.userRole = 1 and u.userID not in (select userID from renter)";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
        return list;
    }

    public List<User> getOwner() {
        List<User> list = new ArrayList<>();
        String sql = "  SELECT [User].userID, [User].userName, [User].userGender, [User].userBirth, [User].userAddress, [User].userPhone, [User].userAvatar\n"
                + "     FROM [User]\n"
                + "     INNER JOIN Account ON [User].userID = Account.userID\n"
                + "     WHERE Account.userRole = 3";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
        return list;
    }

    public int isRenter(int userID) {
        int recordNumber = 0;
        String sql = "select count(*) as [recordNumber]\n"
                + "from [user] u \n"
                + "join renter r\n"
                + "on u.userID = r.userID\n"
                + "where u.userID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                recordNumber = rs.getInt("recordNumber");
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
        return recordNumber;
    }

    public int getUserRoleByEmailAndPassword(String email, String password) {
        String sql = "select userRole from account where userMail = ? and userPassword = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
        }
        return -1;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        List<User> getUserAvailable = dao.getUserAvailable();

        for (User user : getUserAvailable) {
            System.out.println(user.getUserID());
        }

    }

}
