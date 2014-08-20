package cn.zhihang.image.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhihang.image.domain.Image;
import cn.zhihang.image.util.C3P0ConnentionProvider;

/**
 * 图片处理Service
 * @author cnlyml
 * @date 2014-8-20
 */
public class ImageService {
    
    private static Logger logger = LoggerFactory.getLogger(ImageService.class);
    
    /**
     * 随机从数据库中获取num张图片
     * @param num
     * @return
     * @throws SQLException 
     */
    public List<Image> getImageList(int num) throws SQLException{
        
        Connection conn = C3P0ConnentionProvider.getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT *" +
        		"FROM `image` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `image`)-(SELECT MIN(id) FROM" +
        		"`image`))+(SELECT MIN(id) FROM `image`)) AS id) AS t2 WHERE t1.id >= t2.id" +
        		"ORDER BY t1.id LIMIT "+num+";";
        
        ResultSet rs = st.executeQuery(sql);
        List<Image> list = new ArrayList<Image>();
        
        while(rs.next()){
            Image image = new Image();
            image.setId(rs.getInt("id"));
            image.setImage_name(rs.getString("image_name"));
            image.setImage_md5(rs.getString("image_md5"));
            image.setImage_size(rs.getLong("image_size"));
            
            list.add(image);
        }
        
        
        return list;
    }
    
    /**
     * 通过Image获取该图片的访问URL
     * @param image
     * @return
     */
    public String getImageUrl(String imageName){
        return "http://cnlyml.oss-cn-hangzhou.aliyuncs.com/" + imageName;
    }
}
