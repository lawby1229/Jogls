package keg.thu.heatmap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ReadData {
	List<List<Double>> tMap = new ArrayList<List<Double>>();
	HashMap<String, Integer> cellMap = new HashMap<String, Integer>();

	public ReadData() {
		for (int i = 0; i < 48; i++) {
			tMap.add(new ArrayList<Double>());
		}
	}

	public void viewTable(Connection conn) throws Exception {
		// 读取数据
		Statement stmt = null;
		int t;

		// PrintWriter out=new PrintWriter("temp");
		String query = "select Latitude,Longitude,ConnectTime,Traffic "
				+ " from dbo.Traffic5";
		int i = 0;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			double Latitude;
			double Longitude;
			int cellid = -1;
			int timezone = -1;
			String ConnectionTime = "none";
			double Traffic;
			while (rs.next()) {
				// System.out.println(i+"条数据读入!");
				Latitude = rs.getDouble("Latitude");
				Longitude = rs.getDouble("Longitude");
				if (!cellMap.containsKey(Latitude + "," + Longitude)) {
					cellMap.put(Latitude + "," + Longitude, cellMap.size());
				}
				cellid = cellMap.get(Latitude + "," + Longitude);
				Traffic = rs.getDouble("Traffic");
				Calendar CT = Calendar.getInstance();
				ConnectionTime = rs.getString("ConnectTime");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				java.util.Date dt = sdf.parse(ConnectionTime);
				CT.setTime(dt);

				double hour = CT.get(CT.HOUR_OF_DAY);
				hour = hour + CT.get(CT.MINUTE) / 60.0;
				timezone = (int) (hour / 0.5);
				double newTraffic = tMap.get(timezone).get(cellid) + Traffic;
				tMap.get(timezone).set(cellid, newTraffic);
				// int day = CT.get(CT.DAY_OF_YEAR);

				// long FRT_ms=FRT.getTime();
				// long LPT_ms=LPT.getTime();
				// String UserAgent=rs.getString("UserAgent");
				int timeSegment = (int) (hour / 0.5);
				i++;
				// System.out.println(i+":已经快了...");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(i + "个记录时工作日");
		// if(stmt!=null)
		// stmt.close();

		// out.close();
	}

	/*
	 * 数据库连接
	 */
	public static Connection getConnection(String databaseName) {
		// 建立连接
		Connection conn = null;
		try {
			conn = DriverManager
					.getConnection("jdbc:sqlserver://localhost:1433;databaseName="
							+ databaseName + ";integratedSecurity=true;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to database");
		return conn;
	}
}
