package com.ismyself.testDmo.configuration;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * package com.ismyself.testDmo.configuration;
 *
 * @auther txw
 * @create 2020-03-22  18:30
 * @description：
 */
//@Component
public class DbShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {

        Integer index = preciseShardingValue.getValue() % 2;
        for (String dataSourceName : collection) {
            if (dataSourceName.endsWith(index + "")) {
                return dataSourceName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
