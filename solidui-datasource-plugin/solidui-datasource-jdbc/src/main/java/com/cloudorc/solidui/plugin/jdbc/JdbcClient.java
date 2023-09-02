/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudorc.solidui.plugin.jdbc;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface JdbcClient {

    /**
     * Generates a SQL query string to select all data from a specified table.
     */
    String generateSelectAllDataSql(String database, String tableName);

    List<String> getAllDatabase() throws SQLException;

    List<String> getAllTables(String database) throws SQLException ;

    List<List<String>> getSelectResult(String sql) throws SQLException;

    void close() throws IOException;

    Connection getConn();
}
