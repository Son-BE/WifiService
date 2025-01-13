package com.zerobase.wifiservice.dao;

import com.zerobase.wifiservice.JdbcManager;
import com.zerobase.wifiservice.dao.vo.WifiVo;
import com.zerobase.wifiservice.dto.RowDto;
import com.zerobase.wifiservice.dto.WifiDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao extends JdbcManager {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void saveAllWifiList(List<RowDto> list) throws Exception {

        String sql = "insert into wifi(x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, \n" +
                "                 x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor, \n" +
                "                 x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, \n" +
                "                 x_swifi_cmcwr, x_swifi_cnstc_year, x_swifi_inout_door, \n" +
                "                 x_swifi_remars3, lat, lnt, work_dttm) \n" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);
            int batchSize = 1000;
            int count = 0;

            for (int i = 0; i < list.size(); i++) {
                RowDto rowDto = list.get(i);
                ps.setString(1, rowDto.getMgrNo());
                ps.setString(2, rowDto.getWrdofc());
                ps.setString(3, rowDto.getMainNm());
                ps.setString(4, rowDto.getAdres1());
                ps.setString(5, rowDto.getAdres2());
                ps.setString(6, rowDto.getFloor());
                ps.setString(7, rowDto.getTy());
                ps.setString(8, rowDto.getMby());
                ps.setString(9, rowDto.getSvcSe());
                ps.setString(10, rowDto.getCmcwr());
                ps.setString(11, rowDto.getYear());
                ps.setString(12, rowDto.getDoor());
                ps.setString(13, rowDto.getRemars3());
                ps.setString(14, rowDto.getLat());
                ps.setString(15, rowDto.getLnt());
                ps.setString(16, rowDto.getDttm());

                ps.addBatch();
                count++;

                if (batchSize == count) {
                    ps.executeBatch();
                    ps.clearBatch();
                    count = 0;
                }

            }
            ps.executeBatch();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            closeStatement(ps);
            closeConnection(conn);
        }
    }

    public List<WifiVo> search(Double lat, Double lnt) {
        String selectNearWifiQuery = "select * " +
                ", format((6371 * acos(cos(radians(" + lnt + ")) * cos(radians(lat)) * cos(radians(lnt) - radians(" + lat + ")) " +
                "+ sin(radians(" + lnt + ")) * sin(radians(lat)))), 4) as distance " +
                " from wifi " +
                " order by distance , X_SWIFI_MGR_NO" +
                " limit 20";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(selectNearWifiQuery);
            rs = ps.executeQuery(); // 20개씩

            List<WifiVo> list = new ArrayList<WifiVo>();
            while (rs.next()) {
                WifiVo wifiVO = new WifiVo(
                        rs.getString("distance"),
                        rs.getString("X_SWIFI_MGR_NO"),
                        rs.getString("X_SWIFI_WRDOFC"),
                        rs.getString("X_SWIFI_MAIN_NM"),
                        rs.getString("X_SWIFI_ADRES1"),
                        rs.getString("X_SWIFI_ADRES2"),
                        rs.getString("X_SWIFI_INSTL_FLOOR"),
                        rs.getString("X_SWIFI_INSTL_TY"),
                        rs.getString("X_SWIFI_INSTL_MBY"),
                        rs.getString("X_SWIFI_SVC_SE"),
                        rs.getString("X_SWIFI_CMCWR"),
                        rs.getString("X_SWIFI_CNSTC_YEAR"),
                        rs.getString("X_SWIFI_INOUT_DOOR"),
                        rs.getString("X_SWIFI_REMARS3"),
                        rs.getString("LAT"),
                        rs.getString("LNT"),
                        rs.getString("WORK_DTTM")
                );

                list.add(wifiVO);


            }
            return list;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(conn);

        }
    }

    public int removeAllData() {
        Connection connection = null;
        PreparedStatement ps = null;
        String removeOneQuery = "delete from wifi";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(removeOneQuery);

            int resultCount = ps.executeUpdate();
            connection.commit();
            return resultCount;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            closeStatement(ps);
            closeConnection(connection);
        }
    }

}
