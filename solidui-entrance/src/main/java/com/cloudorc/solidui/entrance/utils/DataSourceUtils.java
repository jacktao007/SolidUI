package com.cloudorc.solidui.entrance.utils;


import com.cloudorc.solidui.common.constants.Constants;
import com.cloudorc.solidui.common.utils.JSONUtils;
import com.cloudorc.solidui.dao.entity.DataSource;
import com.cloudorc.solidui.entrance.exceptions.ServiceException;
import com.cloudorc.solidui.plugin.jdbc.ConnectDTO;
import com.cloudorc.solidui.plugin.jdbc.JdbcClient;
import com.cloudorc.solidui.plugin.jdbc.JdbcClientFactory;
import com.cloudorc.solidui.spi.ConstantsSPI;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceUtils {
    private final static Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);
    private final static Map<String, JdbcClientFactory> jdbcClientFactoryInstances = new ConcurrentHashMap<>();
    /**
     * queryJdbcClientFactory
     * @param typeName typeName
     * @return JdbcClientFactory
     */
    public static JdbcClientFactory queryJdbcClientFactory(String typeName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        // MySqlClientFactory ClassLoader
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String[] typeList = ConstantsSPI.DATASOURCE_TYPE_LIST.split(Constants.COMMA);
        if (!Arrays.asList(typeList).contains(typeName)) {
            throw new IllegalArgumentException(typeName + " is not supported");
        }
        JdbcClientFactory jdbcClientFactory = jdbcClientFactoryInstances.get(typeName);
        if(jdbcClientFactory == null){
            String className = String.format(ConstantsSPI.DATASOURCE_CLASSNAME, typeName);
            Class<?> clazz = classLoader.loadClass(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            if (!(instance instanceof JdbcClientFactory)) {
                throw new IllegalArgumentException(className + "JdbcClientFactory implements error");
            }
            jdbcClientFactory = (JdbcClientFactory) instance;
        }

        return jdbcClientFactory;
//        // JdbcClient
//        JdbcClient client = clientFactory.createJdbcClient("117.68.113.63", 3306, "root", "SolidUI@123", "solidui", new HashMap<>());
//        List<String> allDatabase = client.getAllDatabase();
//        allDatabase.stream().forEach(System.out::println);

    }

    public static JdbcClient queryJdbcClient(String typeName,DataSource dataSource) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        JdbcClient jdbcClient = null;
        JdbcClientFactory jdbcClientFactory = DataSourceUtils.queryJdbcClientFactory(typeName);
        DataSourceUtils.mergeParams(dataSource, dataSource.getParameter());
        ConnectDTO connectDTO = jdbcClientFactory.getConnectDTO(dataSource.getConnectParams());
        if(connectDTO != null) {
             jdbcClient = jdbcClientFactory.createJdbcClient(connectDTO);
        }
        return jdbcClient;
    }

    public static DataSource mergeParams(DataSource dataSource, String parameter) {
        dataSource.setParameter(parameter);
        if (StringUtils.isNotBlank(parameter)) {
            Map<String, Object> connectParams = new HashMap<>();
            try {
                connectParams = Objects.requireNonNull(JSONUtils.parseObject(parameter, new TypeReference<Map<String, Object>>() {}));
            } catch (ServiceException e) {
                logger.warn(
                        "Unrecognized the parameter: "
                                + parameter
                                + " in data source, id: ["
                                + dataSource.getId()
                                + "]",
                        e);
                // TODO throws Exception defined Exception
            }
            dataSource.setConnectParams(connectParams);
        }
        return dataSource;
    }
}
