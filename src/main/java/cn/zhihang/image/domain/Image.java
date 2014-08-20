package cn.zhihang.image.domain;

/**
 * 图片Domain，对应数据表中一行数据
 * @author cnlyml
 * @date 2014-8-20
 */
public class Image {
    private int id;
    private String image_name;
    private String image_md5;
    private long image_size;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getImage_name() {
        return image_name;
    }
    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
    public String getImage_md5() {
        return image_md5;
    }
    public void setImage_md5(String image_md5) {
        this.image_md5 = image_md5;
    }
    public long getImage_size() {
        return image_size;
    }
    public void setImage_size(long image_size) {
        this.image_size = image_size;
    }
    
    
}
