package cn.zhihang.image.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zhihang.image.domain.Image;
import cn.zhihang.image.service.ImageService;
import cn.zhihang.image.util.DrawImage;

/**
 * ImageController
 * @author cnlyml
 * @date 2014-8-21
 */

@Controller
@RequestMapping("image.html")
public class ImageController {
    @Autowired
    private ImageService imageService;
    
    private Logger logger = LoggerFactory.getLogger(ImageController.class);
    
    @RequestMapping(params="getImage")
    @ResponseBody
    public String getImage(){
        List<Image> images = null;
        
        try {
            images = imageService.getImageList(21);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return e.getMessage();
        }
        
        return JSONArray.fromObject(images).toString();
    }
    
    @RequestMapping(params = "getSmallImage")
    public void getSmallImage(HttpServletRequest request, HttpServletResponse response, @RequestParam int w, @RequestParam String key){
        if(w == 0 || key == null || key.length() == 0){
            return;
        }
        
        try{
            DrawImage di = new DrawImage(imageService.getImageUrl(key));
            di.resizeByWidth(w, response.getOutputStream());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }
}
