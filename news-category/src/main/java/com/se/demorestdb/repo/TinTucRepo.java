package com.se.demorestdb.repo;

import com.se.demorestdb.dto.CatogoryDTO;
import com.se.demorestdb.model.DanhMuc;
import com.se.demorestdb.model.TinTuc;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@ApplicationScoped
public class TinTucRepo {
    private DataSource ds;

    @PostConstruct
    public void init(){
        try{
            InitialContext context = new InitialContext();
            ds = (DataSource) context.lookup("java:comp/env/jdbc/tintucdb");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TinTuc mapTinTuc (ResultSet rs) throws SQLException {
        TinTuc tinTuc = new TinTuc();
        tinTuc.setId(rs.getInt("id"));
        tinTuc.setLienKet(rs.getString("lien_ket"));
        tinTuc.setNoiDung(rs.getString("noi_dung"));
        tinTuc.setTieuDe(rs.getString("tieu_de"));
        int danhMucId = rs.getInt("danhmuc_id");
        if(rs.wasNull()){
            tinTuc.setDanhmucId(null);
        }else {
            tinTuc.setDanhmucId(danhMucId);
        }
        return tinTuc;
    }

    private CatogoryDTO mapCategoryDTO(ResultSet rs) throws SQLException {
        CatogoryDTO catogoryDTO = new CatogoryDTO();
        TinTuc tinTuc = new TinTuc();
        tinTuc.setId(rs.getInt("id"));
        tinTuc.setLienKet(rs.getString("lien_ket"));
        tinTuc.setNoiDung(rs.getString("noi_dung"));
        tinTuc.setTieuDe(rs.getString("tieu_de"));
        int danhMucId = rs.getInt("danhmuc_id");
        if (rs.wasNull()) {
            tinTuc.setDanhmucId(null);
        } else {
            tinTuc.setDanhmucId(danhMucId);
        }
        catogoryDTO.setTinTuc(tinTuc);
        catogoryDTO.setCategoryName(rs.getString("ten_danhmuc"));
        return catogoryDTO;
    }

    public List<TinTuc> getAll(){
        List<TinTuc> list = new ArrayList<>();
        String sql = "SELECT * FROM tintuc ORDER BY id";
        try(Connection con = ds.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery()
        ){
            while (rs.next()){
                list.add(mapTinTuc(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public TinTuc findOne(int id){
        String sql = "SELECT * FROM tintuc WHERE id = ?";
        try(Connection con = ds.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    return mapTinTuc(rs);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public TinTuc insert(TinTuc tinTuc){
        String sql = "INSERT INTO tintuc (tieu_de,noi_dung,lien_ket,danhmuc_id) VALUES (?,?,?,?)";
        try(Connection con = ds.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1,tinTuc.getTieuDe());
            preparedStatement.setString(2,tinTuc.getNoiDung());
            preparedStatement.setString(3,tinTuc.getLienKet());
            if (tinTuc.getDanhmucId() == null) {
                preparedStatement.setNull(4, Types.INTEGER);
            } else {
                preparedStatement.setInt(4, tinTuc.getDanhmucId());
            }
            preparedStatement.executeUpdate();
            try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    tinTuc.setId(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tinTuc;
    }

    public boolean update(TinTuc tinTuc, int id){
        String sql = "UPDATE  tintuc set tieu_de = ?,noi_dung = ?,lien_ket = ?,danhmuc_id = ? WHERE id = ?";
        try(Connection con = ds.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)
        ){
            preparedStatement.setString(1,tinTuc.getTieuDe());
            preparedStatement.setString(2,tinTuc.getNoiDung());
            preparedStatement.setString(3,tinTuc.getLienKet());
            if (tinTuc.getDanhmucId() == null) {
                preparedStatement.setNull(4, Types.INTEGER);
            } else {
                preparedStatement.setInt(4, tinTuc.getDanhmucId());
            }
            preparedStatement.setInt(5,id);
            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id){
        String sql = "DELETE FROM tintuc WHERE id = ?";
        try(Connection con = ds.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)
        ){
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CatogoryDTO> findByCategory(int categoryId) {
        String sql = "SELECT t.id, t.tieu_de, t.noi_dung, t.lien_ket, t.danhmuc_id, d.ten_danhmuc "
                + "FROM tintuc t INNER JOIN danhmuc d ON t.danhmuc_id = d.id "
                + "WHERE d.id = ? ORDER BY t.id ";
        List<CatogoryDTO> list = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapCategoryDTO(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Lấy danh sách tin tức thuộc về một danh mục cụ thể (Ví dụ: "Thời sự")
    public List<TinTuc> getNewsOfCategoryName(String categoryName) {
        String sql = "SELECT t.* "
                + "FROM tintuc t INNER JOIN danhmuc d ON t.danhmuc_id = d.id "
                + "WHERE d.ten_danhmuc = ? ORDER BY t.id ";
        List<TinTuc> list = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, categoryName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTinTuc(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Thống kê số lượng bài viết tin tức có trong mỗi danh mục
    public Map<String, Integer> countNewsOfCategory() {
        String sql = "SELECT d.ten_danhmuc, COUNT(t.id) "
                + "FROM danhmuc d LEFT JOIN tintuc t ON t.danhmuc_id = d.id "
                + "GROUP BY d.ten_danhmuc ORDER BY d.ten_danhmuc";
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    // Liệt kê các danh mục có từ 3 bài viết tin tức trở lên
    public List<Map<String, Object>> getCategoryCountNews() {
        String sql = "SELECT d.id, d.ten_danhmuc, COUNT(t.id) AS so_luong_bai_viet "
                + "FROM danhmuc d INNER JOIN tintuc t ON t.danhmuc_id = d.id "
                + "GROUP BY d.id, d.ten_danhmuc "
                + "HAVING COUNT(t.id) >= 2 ORDER BY d.ten_danhmuc";
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("id", rs.getInt(1));
                m.put("categoryName", rs.getString(2));
                m.put("total", rs.getInt(3));
                list.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Tìm danh mục có số lượng bài viết tin tức nhiều nhất (subquery + MAX)
    public List<Map<String, Object>> getMaxNewsCategory() {
        String sql = "SELECT d.id, d.ten_danhmuc, COUNT(t.id) AS so_luong "
                + "FROM danhmuc d LEFT JOIN tintuc t ON t.danhmuc_id = d.id "
                + "GROUP BY d.id, d.ten_danhmuc "
                + "HAVING COUNT(t.id) = ("
                + "SELECT MAX(SoLuong) FROM ("
                + "SELECT COUNT(t2.id) AS SoLuong "
                + "FROM danhmuc d2 LEFT JOIN tintuc t2 ON t2.danhmuc_id = d2.id "
                + "GROUP BY d2.id"
                + ") AS ThongKeMax)";
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("id", rs.getInt(1));
                m.put("categoryName", rs.getString(2));
                m.put("total", rs.getInt(3));
                list.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
