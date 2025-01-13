package com.zerobase.wifiservice.dao;

import com.zerobase.wifiservice.JdbcManager;
import com.zerobase.wifiservice.dao.vo.HistoryVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao extends JdbcManager {

    public int saveData(Double lat, Double lnt) {
        Connection con = null;
        PreparedStatement ps = null;

        String insertQuery = "insert into history values(lat,lnt,retrieveDate)" +
                "values(?,?,now())";

        try {
            con = getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(insertQuery);
            ps.setDouble(1, lat);
            ps.setDouble(2, lnt);
            int resultCount = ps.executeUpdate();
            con.commit();
            return resultCount;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            closeStatement(ps);
            closeConnection(con);
        }

    }

    public List<HistoryVo> HistorySelectAll() {
        String sql = "select * from history" + "order by id desc";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<HistoryVo> historyVoList = new ArrayList<>();
            while (rs.next()) {
                historyVoList.add(new HistoryVo(
                        rs.getString("id"), rs.getDouble("lat"),
                        rs.getDouble("int"), rs.getString("retrieveDate"))
                );
            }
            return historyVoList;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(con);
        }
    }

    public int removeAllData() {
        Connection con = null;
        PreparedStatement ps = null;

        String removeOneQuery = "delete from history";

        try {
            con = getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(removeOneQuery);

            int resultCount = ps.executeUpdate();
            con.commit();
            return resultCount;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            closeStatement(ps);
            closeConnection(con);
        }
    }

    public int removeOneData(int id) {
        Connection con = null;
        PreparedStatement ps = null;

        String removeOneQuery = "delete from history" +"where id = ?";

        try {
            con = getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(removeOneQuery);
            ps.setInt(1, id);
            int resultCount = ps.executeUpdate();
            con.commit();
            return resultCount;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            closeStatement(ps);
            closeConnection(con);
        }
    }


}
