//package com.ismyself.testDmo.configuration;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * package com.ismyself.testDmo.configuration;
// *
// * @auther txw
// * @create 2020-07-06
// * @description：
// */
//@Configuration
//@AutoConfigureAfter(EnableAutoConfiguration.class)
//@EnableConfigurationProperties(TxwConfig.class)
//public class MyDatasoruceTestConfig {
//
//    @Autowired
//    private TxwConfig txwConfig;
//
//    private final static Properties PROPERTIES = new Properties();
//
//    {
//        Map<String, String> dataSourceMap = txwConfig.getDataSourceMap();
//        PROPERTIES.setProperty("druid.name", dataSourceMap.get("driver-class-name"));
//        PROPERTIES.setProperty("druid.url", dataSourceMap.get("url"));
//        PROPERTIES.setProperty("druid.username", dataSourceMap.get("username"));
//        PROPERTIES.setProperty("druid.password", dataSourceMap.get("password"));
//    }
//
//    @Bean(name = "dataSourceCustomer")
//    @Primary
//    public DataSource myDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.configFromPropety(PROPERTIES);
//        try {
//            dataSource.init();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return dataSource;
//    }
//
//    @Bean("jdbcTemplateCustomer")
//    public JdbcTemplate jdbcTemplateCustomer(@Qualifier("dataSourceCustomer") DataSource dataSourceCustomer) {
//        return new JdbcTemplate(dataSourceCustomer);
//    }
//
//    @Bean("beanFactoryCustomer")
//    public SqlSessionFactory beanFactoryCustomer(@Qualifier("dataSourceCustomer") DataSource dataSourceCustomer) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        //换成代理数据源
//        sqlSessionFactoryBean.setDataSource(dataSourceCustomer);
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @ConditionalOnMissingBean
//    @Bean("dataSourceTransactionManagerCustomer")
//    public DataSourceTransactionManager dataSourceTransactionManagerCustomer(@Qualifier("dataSourceCustomer") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//}
