/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import Model.Member;
import Model.MemberDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wishwa
 */
public class MemberPersistantDAO implements MemberDAO {

    @Override
    public void addMember(Member member) {

        String sqlAdd = "INSERT INTO member VALUES(?,?,?,?,?)";
        Connection con = null;
        PreparedStatement pst = null;
        boolean state;

        try {

            con = DBconnectionProject.connect();
            pst = con.prepareStatement(sqlAdd);
            pst.setInt(1, member.getMemberId());
            pst.setString(2, member.getMemberName());
            pst.setString(3, member.getMemberContactNo());
            pst.setString(4, member.getMemberAddress());
            pst.setBoolean(5,true); 
            pst.executeUpdate();

            state = true;

        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            state = false;

        } finally {

            try {
                pst.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public Member searchMemberById(int Id) {
        
        String sqlSearch = "SELECT * FROM member WHERE memberId=?";
        Connection con = null;
        PreparedStatement pst = null;
        Member memberSearchID = null;

        ResultSet rs = null;

        try {
            con = DBconnectionProject.connect();
            pst = con.prepareStatement(sqlSearch);
            pst.setInt(1, Id);
            rs = pst.executeQuery();

            if (rs.next()) {
                Id = rs.getInt("memberId");
                String Name = rs.getString("memberName");
                String Contact = rs.getString("memberContactNo");
                String Address = rs.getString("memberAddress");

                memberSearchID = new Member(Id, Name, Contact, Address);

            }

        } catch (SQLException ex) {
            memberSearchID = null;
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                pst.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MemberPersistantDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return memberSearchID;
    }

    @Override
    
    public ArrayList<Member> searchMemberByName(String Name) {

        String sqlSearch = "SELECT * FROM member WHERE memberName=?";
        Connection con = null;
        PreparedStatement pst = null;
        Member memberSearchID = null;
        ArrayList<Member> list = new ArrayList<>();
        ResultSet rs = null;

        try {
            con = DBconnectionProject.connect();
            pst = con.prepareStatement(sqlSearch);
            pst.setString(1, Name);
            rs = pst.executeQuery();

            while (rs.next()) {
                int Id = rs.getInt("memberId");
                Name = rs.getString("memberName");
                String Contact = rs.getString("memberContactNo");
                String Address = rs.getString("memberAddress");
                memberSearchID = new Member(Id, Name, Contact, Address);
                list.add(memberSearchID);
            }

        } catch (SQLException ex) {
            memberSearchID = null;
        } finally {
            try {
                rs.close();
                pst.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MemberPersistantDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;
    }

    @Override
    public boolean updateMember(Member member) {

        String sqlUpdate = "UPDATE member SET memberName=?,memberContactNo=? , memberAddress=? WHERE memberId=?";
        Connection con = null;
        PreparedStatement pst = null;
        boolean state;

        try {
            con = DBconnectionProject.connect();
            pst = con.prepareStatement(sqlUpdate);
            state = true;
            pst.setString(1, member.getMemberName());
            pst.setString(2, member.getMemberContactNo());
            pst.setString(3, member.getMemberAddress());
            pst.setInt(4, member.getMemberId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            member = null;
            state = false;

        } finally {

            try {
                pst.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MemberPersistantDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return state;
    }

    @Override
    public boolean deleteMember(int Id) {

        String sqlUpdate = "DELETE from member where memberId=?";
        Connection con = null;
        PreparedStatement pst = null;
        boolean state;

        try {
            con = DBconnectionProject.connect();
            pst = con.prepareStatement(sqlUpdate);
            pst.setInt(1, Id);
            pst.executeUpdate();
            state = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            state = false;

        } finally {

            try {

                pst.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MemberPersistantDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return state;
    }

}