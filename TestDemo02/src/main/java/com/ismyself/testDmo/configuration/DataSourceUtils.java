package com.ismyself.testDmo.configuration;


import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.util.DataSourceUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * package com.ismyself.testDmo.configuration;
 *
 * @auther txw
 * @create 2020-03-30
 * @description：
 */


@EnableConfigurationProperties(TxwConfig.class)
public class DataSourceUtils {

    @Autowired
    private TxwConfig txwConfig;

    public DataSource getDataSource() {
        try {
            String dataSourceClassName = txwConfig.getClassName();
            System.out.println(dataSourceClassName + "sssssssssssss");
            Map<String, Object> configMap = txwConfig.getDataSourceMap();
            System.out.println("+++++++++++" + configMap);
            DataSource dataSource = DataSourceUtil.getDataSource(dataSourceClassName, configMap);
            init(dataSource, dataSourceClassName);
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void init(DataSource dataSource, String dataSourceClassName) throws SQLException {
        if ("com.alibaba.druid.pool.DruidDataSource".equals(dataSourceClassName)) {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;
            druidDataSource.setName(dataSourceClassName);
            druidDataSource.init();
        }
    }


}
