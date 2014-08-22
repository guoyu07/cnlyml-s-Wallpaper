package cn.zhihang.image.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zhihang.image.dao.ImageDao;
import cn.zhihang.image.domain.Image;

/**
 * 图片处理Service
 * @author cnlyml
 * @date 2014-8-20
 */
@Service
public class ImageService {
    @Autowired
    private ImageDao imageDao;
    
    private static Logger logger = LoggerFactory.getLogger(ImageService.class);
    
    /**
     * 随机从数据库中获取num张图片
     * @param num
     * @return
     * @throws SQLException 
     */
    public List<Image> getImageList(int num) throws SQLException{
        
        if(num > 1000 || num < 0){
            logger.warn("num超出界限...");
            return null;
        }
        
        return imageDao.getImageList(num);
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
