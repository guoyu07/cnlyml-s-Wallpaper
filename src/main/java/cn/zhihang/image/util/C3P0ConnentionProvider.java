package cn.zhihang.image.util;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.DataSources;

/**
 * C3P0连接池管理
 * @author cnlyml
 * @date 2014-8-20
 */
public class C3P0ConnentionProvider{
    private static final String JDBC_DRIVER = "driverClass";
    private static final String JDBC_URL = "jdbcUrl";
    private static Logger logger = LoggerFactory
            .getLogger(C3P0ConnentionProvider.class);

    private static DataSource ds;

    /**
     * 初始化连接池代码块
     */
    static {
        try {
            initDBSource();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private static final void initDBSource() throws Exception {
        Properties c3p0Pro = new Properties();

        String c3p0Path = C3P0ConnentionProvider.class.getClassLoader().getResource("c3p0.properties").getPath();
        System.out.println(c3p0Path);
        File f = new File(c3p0Path);

        if (!f.exists()) {
            throw new Exception("c3p0.properties文件不存在......");
        }

        c3p0Pro.load(FileUtils.openInputStream(f));

        String driverClass = c3p0Pro.getProperty(JDBC_DRIVER);
        if (driverClass != null) {
            try {
                // 加载驱动类
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            throw new Exception("driverClass不能为空......");
        }

        Properties jdbcpropes = new Properties();
        Properties c3propes = new Properties();
        for (Object key : c3p0Pro.keySet()) {
            String skey = (String) key;
            if (skey.startsWith("c3p0.")) {
                c3propes.put(skey, c3p0Pro.getProperty(skey));
            } else {
                jdbcpropes.put(skey, c3p0Pro.getProperty(skey));
            }
        }

        try {
            // 建立连接池
            DataSource unPooled = DataSources.unpooledDataSource(
                    c3p0Pro.getProperty(JDBC_URL), jdbcpropes);
            ds = DataSources.pooledDataSource(unPooled, c3propes);

        } catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * 获取数据库连接对象
     * 
     * @return 数据连接对象
     * @throws SQLException
     */
    public static synchronized Connection getConnection() throws SQLException {
        final Connection conn = ds.getConnection();
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return conn;
    }
    
    public static void main(String[] args) throws SQLException {
        Connection conn = C3P0ConnentionProvider.getConnection();
        
        System.out.println(conn);
    }
}
