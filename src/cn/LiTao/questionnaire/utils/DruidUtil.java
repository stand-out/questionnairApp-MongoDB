//package cn.LiTao.questionnaire.utils;
//
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//
//import javax.sql.DataSource;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Properties;
//
//public class DruidUtil {
//
//    private static DataSource ds;
//
//    static {
//        InputStream is = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
//        Properties pop = new Properties();
//
//        try {
//
//            pop.load(is);
//
//            ds = DruidDataSourceFactory.createDataSource(pop);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static DataSource getDataSource() {
//        return ds;
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }
//}
