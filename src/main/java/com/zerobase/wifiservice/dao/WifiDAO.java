package com.zerobase.wifiservice.dao;

import com.zerobase.wifiservice.JDBCRepository;
import com.zerobase.wifiservice.dao.vo.WifiVo;
import com.zerobase.wifiservice.dto.RowDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class WifiDAO extends JDBCRepository {
    // DB 연결, PreparedStatement 및 ResultSet을 멤버 변수로 선언
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void saveAllWifiList(List<RowDto> list) throws Exception {
        String sql = "INSERT INTO wifi_service(x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, " +
                "x_swifi_adres1, x_swifi_adres2, x_swifi_instl_floor, " +
                "x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, " +
                "x_swifi_cmcwr, x_swifi_cnstc_year, x_swifi_inout_door, " +
                "x_swifi_remars3, lat, lnt, work_dttm) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            conn = createConnection(); // 데이터베이스 연결 가져오기
            conn.setAutoCommit(false); // 자동 커밋 비활성화

            ps = conn.prepareStatement(sql); // PreparedStatement 생성

            // DTO 리스트를 순회하며 데이터베이스에 저장
            for (RowDto wifi : list) {
                ps.setString(1, wifi.getMgrNo());
                ps.setString(2, wifi.getWrdofc());
                ps.setString(3, wifi.getMainNm());
                ps.setString(4, wifi.getAdres1());
                ps.setString(5, wifi.getAdres2());
                ps.setString(6, wifi.getFloor());
                ps.setString(7, wifi.getTy());
                ps.setString(8, wifi.getMby());
                ps.setString(9, wifi.getSvcSe());
                ps.setString(10, wifi.getCmcwr());
                ps.setString(11, wifi.getYear());
                ps.setString(12, wifi.getDoor());
                ps.setString(13, wifi.getRemars3());
                ps.setString(14, wifi.getLat());
                ps.setString(15, wifi.getLnt());
                ps.setString(16, wifi.getDttm());

                ps.addBatch(); // 배치에 추가
            }

            ps.executeBatch(); // 배치 실행
            conn.commit(); // 커밋
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // 예외 발생 시 롤백
            }
            e.printStackTrace();
            throw new Exception("데이터 저장 중 오류가 발생했습니다.", e);
        } finally {
            closeStatement(ps); // PreparedStatement 닫기
            closeConnection(conn); // 연결 닫기
        }
    }

    public int removeAllData() {
        Connection connection = null;
        PreparedStatement ps = null;
        String removeOneQuery = "delete from wifi";

        try {
            connection = createConnection();
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

    public List<WifiVo> search(Double lat, Double lnt) {
        String selectNearWifiQuery = "select * " +
                ", format((6371 * acos(cos(radians(" + lnt + ")) * cos(radians(lat)) * cos(radians(lnt) - radians(" + lat + ")) " +
                "+ sin(radians(" + lnt + ")) * sin(radians(lat)))), 4) as distance " +
                " from wifi " +
                " order by distance , X_SWIFI_MGR_NO" +
                " limit 20";

        try {
            conn = createConnection();
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


    public static void main(String[] args) {

    }
}