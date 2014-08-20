package cn.zhihang.image.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhihang.image.service.ImageService;
import cn.zhihang.image.util.DrawImage;

/**
 * 图片获取及处理Servlet
 * @author cnlyml
 * @date 2014-8-20
 */
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1720782459160491553L;
    private static final int IMANGE_NUM = 40;
    
    private static Logger logger = LoggerFactory.getLogger(ImageServlet.class);
    private ImageService imageService = new ImageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       process(request, response);
    }
    
    private void process(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameterMap().size() == 0){
            return;
        }
        
        if(request.getParameter("getImage") != null){
            
        } else if(request.getParameter("getSmallImage") != null){
            String imageName = request.getParameter("getSmaillImage");
            Integer w = null, h = null;
            try{
                w = Integer.valueOf(request.getParameter("w"));
                h = Integer.valueOf(request.getParameter("h"));
            }catch(Exception e){
                if(logger.isErrorEnabled()){logger.error(e.getMessage(), e);}
            }
            
            
            try{
                DrawImage di = new DrawImage(imageService.getImageUrl(imageName));
                di.resize(w, h, response.getOutputStream());
            }catch(Exception e){
                if(logger.isErrorEnabled()){logger.error(e.getMessage(), e);}
            }
        }
    }
    
}
