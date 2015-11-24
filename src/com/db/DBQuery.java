package com.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.utils.ConfigUtil;

public class DBQuery {
	public String table_name = "";
	private String insert_prefix = "insert";
	private String[][] sql_where = null;
	private String sql_mɑrk = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,";
	private Connection db_dsn_conn;
	private static HashMap<String, DBQuery> db_instance = new HashMap<String, DBQuery>();
	private static String dsn = "default_dsn";

	public DBQuery(String sDsn) throws SQLException, IOException, Exception {
		dsn = sDsn;
		Class.forName("com.mysql.jdbc.Driver");
		this.db_dsn_conn = DriverManager.getConnection(new ConfigUtil().getProperties("properties/jdbc.properties", dsn, null));
	}

	public static DBQuery instance() throws Exception {
		return instance(null);
	}

	public static DBQuery instance(String sDsn) throws Exception {
		if (sDsn == null) {
			sDsn = dsn;
		}
		DBQuery instance = db_instance.get(sDsn);
		if (instance != null) {
			return instance;
		}
		instance = new DBQuery(sDsn);
		db_instance.put(sDsn, instance);
		return instance;
	}

	public DBQuery setTable(String tableName) throws Exception {
		this.table_name = tableName;
		return instance(dsn);
	}

	public DBQuery setInsert_prefix(String insertPrefix) throws Exception {
		this.insert_prefix = insertPrefix;
		return instance(dsn);
	}

	public DBQuery setSql_where(String[][] sqlwhere) throws Exception {
		this.sql_where = sqlwhere;
		return instance(dsn);
	}

	public int getCount(String field) throws Exception {
		String sql = "SELECT COUNT(" + field + ") FROM " + this.table_name;
		PreparedStatement psStatement = db_dsn_conn.prepareStatement(sql);
		ResultSet rsRsult = psStatement.executeQuery();
		return rsRsult.getInt(0);
	}

	public ArrayList<HashMap<String, Object>> getList(String field) throws Exception {
		StringBuilder sql = new StringBuilder("SELECT " + field + " FROM " + this.table_name + " where 1=1 ");
		ResultSet RSquery = null;
		if (this.sql_where != null) {
			int length1 = sql_where[0].length;
			for (int j = 0; j < length1; j++) {
				if (!this.sql_where[1][j].isEmpty()) {
					sql.append(" AND " + this.sql_where[0][j] + " = ? ");
				}
			}
		}
		PreparedStatement PStatement = db_dsn_conn.prepareStatement(sql.toString());
		if (this.sql_where != null) {
			int length2 = sql_where[1].length;
			for (int j = 0; j < length2; j++) {
				if (!this.sql_where[1][j].isEmpty()) {
					PStatement.setString(j + 1, this.sql_where[0][j]);
				}
			}
		}
		RSquery = PStatement.executeQuery();
		ResultSetMetaData rsmd = PStatement.getMetaData();

		// 取得结果集列数
		int columnCount = rsmd.getColumnCount();

		// 构造泛型结果集 Mdata LMdata
		HashMap<String, Object> Mdata = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> LMdata = new ArrayList<HashMap<String, Object>>();
		// 循环结果集
		while (RSquery.next()) {
			// 每循环一条将列名和列值存入Map
			for (int i = 1; i < columnCount; i++) {
				Mdata.put(rsmd.getColumnLabel(i), RSquery.getObject(rsmd.getColumnLabel(i)));
			}
			// 将整条数据的Map存入到List中
			LMdata.add(Mdata);
			Mdata = null;
		}
		return LMdata;
	}

	// 插入
	public DBQuery insert(String fleid, String[][] RowValue) throws Exception {
		String sql = this.insert_prefix + " INTO " + this.table_name + "(" + fleid + ") VALUES (" + sql_mɑrk.substring(0, (RowValue[0].length * 2) - 1) + ")";
		PreparedStatement PStatement = db_dsn_conn.prepareStatement(sql);
		int rowV1 = RowValue.length;
		int rowV2;
		for (int i = 0; i < rowV1; i++) {
			rowV2 = RowValue[i].length;
			for (int j = 0; j < rowV2; j++) {
				PStatement.setString(j + 1, RowValue[i][j]);
			}
			PStatement.addBatch();
			if ((i + 1) % 300 == 0) {
				PStatement.executeBatch();
				PStatement.clearBatch();
			}
		}
		if (rowV1 % 300 != 0) {
			PStatement.executeBatch();
			PStatement.clearBatch();
		}
		return instance(dsn);
	}

	public String getLastInsterId() throws SQLException {
		return db_dsn_conn.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery().getString(0);
	}

	public void close() throws SQLException {
		db_dsn_conn.close();
		db_instance.remove(dsn);
	}

	public static void main(String[] args) throws Exception {
		// String<String, String>[] RowValuue = {{"yy347y","zzz","11"}};
		String[][] RowValue = { { "yy3r407y", "zzz", "11" } };
		System.out.println(RowValue[0][0]);
		DBQuery.instance().setTable("user").insert("name, password, addtime", RowValue);
		// System.out.println(id);
		// "dat:username", "xml"));
	}
}
