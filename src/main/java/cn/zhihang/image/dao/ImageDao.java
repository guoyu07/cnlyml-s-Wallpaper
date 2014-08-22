package cn.zhihang.image.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import cn.zhihang.image.domain.Image;

@Component
public class ImageDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Image> getImageList(int num){
        String sql = "SELECT *" +
                " FROM `image` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `image`)-(SELECT MIN(id) FROM " +
                " `image`))+(SELECT MIN(id) FROM `image`)) AS id) AS t2 WHERE t1.id >= t2.id " +
                " ORDER BY t1.id LIMIT ?;";
        
        Object[] args = {num};
        
        final List<Image>images = new ArrayList<Image>();
        
        jdbcTemplate.query(sql, args,new RowCallbackHandler(){
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                while(rs.next()){
                    Image image = new Image();
                    image.setId(rs.getInt("id"));
                    image.setImage_name(rs.getString("image_name"));
                    image.setImage_md5(rs.getString("image_md5"));
                    image.setImage_size(rs.getLong("image_size"));
                    images.add(image);
                }
            }
            
        });
        
        return images;
    }
}
