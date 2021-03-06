package cn.zhihang.image.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import com.sun.image.codec.jpeg.*;

public class DrawImage {
    private int width;
    private int height;
    private Image img;
   
    public DrawImage(String url) throws IOException,IllegalArgumentException {

        img = javax.imageio.ImageIO.read(new URL(url)); // 构造Image对象
        width = img.getWidth(null); // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }
   
    /**
     * /**
     *
     * @param args
     */
    @SuppressWarnings("restriction")
    public void resize(int w, int h, OutputStream os) throws IOException {
        try {
            BufferedImage _image = new BufferedImage(w, h,
                    BufferedImage.TYPE_INT_RGB);
            _image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
            
            
            
            /*
             * JPEGImageEncoder 将图像缓冲数据编码为 JPEG 数据流。该接口的用户应在 Raster 或
             * BufferedImage 中提供图像数据，在 JPEGEncodeParams 对象中设置必要的参数， 并成功地打开
             * OutputStream（编码 JPEG 流的目的流）。JPEGImageEncoder 接口可 将图像数据编码为互换的缩略
             * JPEG 数据流，该数据流将写入提供给编码器的 OutputStream 中。
             * 注意：com.sun.image.codec.jpeg 包中的类并不属于核心 Java API。它们属于 Sun 发布的 JDK
             * 和 JRE 产品的组成部分。虽然其它获得许可方可能选择发布这些类，但开发人员不能寄 希望于从非 Sun
             * 实现的软件中得到它们。我们期望相同的功能最终可以在核心 API 或标准扩 展中得到。
             */
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(_image); // 近JPEG编码
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
    /**
     * 按照固定的比例缩放图片
     *
     * @param t
     *            double 比例
     * @throws IOException
     */
    public void resize(double t, OutputStream os) throws IOException {
        int w = (int) (width * t);
        int h = (int) (height * t);
        resize(w, h, os);
    }
   
    /**
     * 以宽度为基准，等比例放缩图片
     *
     * @param w
     *            int 新宽度
     * @throws IOException
     */
    public void resizeByWidth(int w, OutputStream os) throws IOException {
        int h = (int) (height * w / width);
        resize(w, h, os);
    }
   
    /**
     * 以高度为基准，等比例缩放图片
     *
     * @param h
     *            int 新高度
     * @throws IOException
     */
    public void resizeByHeight(int h, OutputStream os) throws IOException {
        int w = (int) (width * h / height);
        resize(w, h, os);
    }
   
    /**
     * 按照最大高度限制，生成最大的等比例缩略图
     *
     * @param w
     *            int 最大宽度
     * @param h
     *            int 最大高度
     * @throws IOException
     */
    public void resizeFix(int w, int h, OutputStream os) throws IOException {
        if (width / height > w / h) {
            resizeByWidth(w, os);
        } else {
            resizeByHeight(h,os);
        }
    }
   
    /**
     * 获取图片原始宽度 getSrcWidth
     */
    public int getSrcWidth() {
        return width;
    }
   
    /**
     * 获取图片原始高度 getSrcHeight
     */
    public int getSrcHeight() {
        return height;
    }
}