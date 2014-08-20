package cn.zhihang.image.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 首页显示
 * @author cnlyml
 * @date 2014-8-20
 */
public class IndexServlet extends HttpServlet{
    
    private static final long serialVersionUID = 4353372484947421313L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }
    
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
    }
    
}
