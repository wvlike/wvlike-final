package com.ismyself.testDmo.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * package com.ismyself.testDmo.configuration;
 *
 * @auther txw
 * @create 2020-03-22
 * @description：
 */

@Configuration
public class DataSourceConfig {


    @MapperScan(basePackages = {"com.ismyself.testDmo.mapper.single"}, sqlSessionFactoryRef = "beanFactoryCustomer")
    @Import(DataSourceUtils.class)
    @AutoConfigureBefore({DataSourceAutoConfiguration.class})
    @Configuration
    public class MyBatisConfigCustomer {

     /*   @Autowired
        private Environment env;*/

        @Autowired
        private DataSourceUtils dataSourceUtils;

        @Bean("dataSourceCustomer")
        @Primary
        public DataSource dataSourceCustomer() {
            return dataSourceUtils.getDataSource();
        }

        @Bean("jdbcTemplateCustomer")
        public JdbcTemplate jdbcTemplateCustomer(@Qualifier("dataSourceCustomer") DataSource dataSourceCustomer) {
            return new JdbcTemplate(dataSourceCustomer);
        }

        @Bean("beanFactoryCustomer")
        @Primary
        public SqlSessionFactory beanFactoryCustomer(@Qualifier("dataSourceCustomer") DataSource dataSourceCustomer) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            //换成代理数据源
            sqlSessionFactoryBean.setDataSource(dataSourceCustomer);
            return sqlSessionFactoryBean.getObject();
        }

        @ConditionalOnMissingBean
        @Bean("dataSourceTransactionManagerCustomer")
        public DataSourceTransactionManager dataSourceTransactionManagerCustomer(@Qualifier("dataSourceCustomer") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

//        @Bean
//        public PlatformTransactionManager platformTransactionManager() {
//            return new DataSourceTransactionManager(dataSourceCustomer());
//        }

    }


    @org.mybatis.spring.annotation.MapperScan(basePackages = {"com.ismyself.testDmo.mapper.tables"})
    @Import(DataSourceUtils.class)
    @AutoConfigureBefore({DataSourceAutoConfiguration.class})
    @Configuration
    public class MyBatisConfigCustomerCopy {

     /*   @Autowired
        private Environment env;*/

        @Autowired
        private DataSourceUtils dataSourceUtils;

        @Bean("dataSourceCustomerCopy")
        public DataSource dataSourceCustomerCopy() {
            return dataSourceUtils.getDataSource();
        }

        @Bean("jdbcTemplateCustomerCopy")
        public JdbcTemplate jdbcTemplateCustomerCopy(@Qualifier("dataSourceCustomerCopy") DataSource dataSourceCustomer) {
            return new JdbcTemplate(dataSourceCustomer);
        }

        @Bean("beanFactoryCustomerCopy")
        public SqlSessionFactory beanFactoryCustomerCopy(@Qualifier("dataSourceCustomerCopy") DataSource dataSourceCustomer) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            //换成代理数据源
            sqlSessionFactoryBean.setDataSource(dataSourceCustomer);
            return sqlSessionFactoryBean.getObject();
        }

        @ConditionalOnMissingBean
        @Bean("dataSourceTransactionManagerCustomerCopy")
        public DataSourceTransactionManager dataSourceTransactionManagerCustomerCopy(@Qualifier("dataSourceCustomerCopy") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

//        @Bean
//        public PlatformTransactionManager platformTransactionManager() {
//            return new DataSourceTransactionManager(dataSourceCustomer());
//        }

    }


}