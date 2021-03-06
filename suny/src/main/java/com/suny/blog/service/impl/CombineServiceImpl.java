package com.suny.blog.service.impl;

import com.suny.blog.model.AsirImages;
import com.suny.blog.model.Combine;
import com.suny.blog.service.AsirImagesService;
import com.suny.blog.service.CombineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CombineServiceImpl implements CombineService {

    private static final Logger logger = LoggerFactory.getLogger(CombineServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AsirImagesService asirImagesService;
    @Override
    public List<Combine> getCombineList() {
        String sql = "select * from a_sir_combine";
        List<Combine> cmbList = new ArrayList<Combine>();
        try {
            List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
            for (int i = 0; i < rows.size(); i++) {
                Map<String,Object> cmbMap = rows.get(i);
                Combine cmb = new Combine();
                cmb.setId(Integer.valueOf(cmbMap.get("id")+""));
                cmb.setContent(cmbMap.get("content").toString());
                cmb.setName(cmbMap.get("name").toString());
                cmb.setThumbUrl(cmbMap.get("thumb_url").toString());
                cmb.setParams(cmbMap.get("params").toString());
                cmb.setPrice(Float.valueOf(cmbMap.get("price").toString()));
                cmb.setMemo(cmbMap.get("memo").toString());
                cmbList.add(cmb);
            }
        }catch (DataAccessException e){
            logger.error("查询异常",e);
        }
        return cmbList;
    }

    @Override
    public List<Combine> getCombineListByType(int combineType) {
        String sql = "";
        if(combineType!=1){
            sql = "select * from a_sir_combine WHERE combine_type = "+combineType;
        }else{
            sql = "select * from a_sir_combine ";
        }
        List<Combine> cmbList = new ArrayList<Combine>();
        try {
            List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
            for (int i = 0; i < rows.size(); i++) {
                Map<String,Object> cmbMap = rows.get(i);
                Combine cmb = new Combine();
                cmb.setId(Integer.valueOf(cmbMap.get("id")+""));
                cmb.setContent(cmbMap.get("content").toString());
                cmb.setName(cmbMap.get("name").toString());
                cmb.setThumbUrl(cmbMap.get("thumb_url").toString());
                cmb.setParams(cmbMap.get("params").toString());
                cmb.setPrice(Float.valueOf(cmbMap.get("price").toString()));
                cmb.setMemo(cmbMap.get("memo").toString());
                cmbList.add(cmb);
            }
        }catch (DataAccessException e){
            logger.error("查询异常",e);
        }
        return cmbList;
    }

    @Override
    public Combine getCombineById(int combineId) {
        String sql = "SELECT * FROM a_sir_combine WHERE id = "+combineId ;
        Combine combine = null;
        try{
            combine=jdbcTemplate.queryForObject(sql, new RowMapper<Combine>() {
                @Override
                public Combine mapRow(ResultSet resultSet, int i) throws SQLException {
                    Combine cb = new Combine();
                    cb.setThumbUrl(resultSet.getString("thumb_url"));
                    cb.setContent(resultSet.getString("content"));
                    cb.setId(resultSet.getInt("id"));
                    cb.setMemo(resultSet.getString("memo"));
                    cb.setName(resultSet.getString("name"));
                    cb.setParams(resultSet.getString("params"));
                    cb.setPrice(resultSet.getFloat("price"));

                    AsirImages asirImages = asirImagesService.getImageListsByKey(combineId);
                    if(null!=asirImages){
                        List<String> imageList = new ArrayList<>();
                        imageList.add(null!= asirImages.getImage1() ? asirImages.getImage1():"");
                        imageList.add(null!= asirImages.getImage2() ? asirImages.getImage2():"");
                        imageList.add(null!= asirImages.getImage3() ? asirImages.getImage3():"");
                        imageList.add(null!= asirImages.getImage4() ? asirImages.getImage4():"");
                        imageList.add(null!= asirImages.getImage5() ? asirImages.getImage5():"");
                        imageList.add(null!= asirImages.getImage6() ? asirImages.getImage6():"");
                        cb.setImages(imageList);
                    }
                    return cb;
                }
            });
        }catch (Exception e){
            logger.error("查询异常:"+e);
        }
        return combine;
    }

    @Override
    public int updateCombine(Combine cmb) {
        return 0;
    }

    @Override
    public int delCombine(Combine cmb) {
        return 0;
    }
}
