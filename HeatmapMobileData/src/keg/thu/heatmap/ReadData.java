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
	HashMap<Integer, HashMap<Integer, Double>> tMap = new HashMap<Integer, HashMap<Integer, Double>>();
	HashMap<Integer, String> cellIdMapLoc = new HashMap<Integer, String>();
	HashMap<String, Integer> cellLocMapId = new HashMap<String, Integer>();
	double min, max;

	public ReadData(String dbname) {
		for (int i = 0; i < 48; i++) {
			HashMap<Integer, Double> celIdTraffic = new HashMap<Integer, Double>();
			tMap.put(i, celIdTraffic);
		}
		try {
			viewTable(getConnection(dbname));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void viewTable(Connection conn) throws Exception {
		// 读取数据
		Statement stmt = null;
		int t;

		// PrintWriter out=new PrintWriter("temp");
		String query = "select Longitude,Latitude,ConnectTime,Traffic "
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
			while (rs.next() ) {
				// System.out.println(i+"条数据读入!");
				Longitude = rs.getDouble("Longitude");
				Latitude = rs.getDouble("Latitude");
				String loc = Longitude + "," + Latitude;
				if (!cellIdMapLoc.containsValue(loc)) {
					cellIdMapLoc.put(cellIdMapLoc.size(), loc);
					cellLocMapId.put(loc, cellLocMapId.size());
				}
				cellid = cellLocMapId.get(loc);
				Traffic = rs.getDouble("Traffic");
				Calendar CT = Calendar.getInstance();
				ConnectionTime = rs.getString("ConnectTime");
				System.out.print(ConnectionTime + " ");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				java.util.Date dt = sdf.parse(ConnectionTime);
				CT.setTime(dt);

				double hour = CT.get(CT.HOUR_OF_DAY);
				hour = hour + CT.get(CT.MINUTE) / 60.0;
				timezone = (int) (hour / 0.5);
				System.out.print(hour + " " + timezone + " " + cellid + " ");
				if (cellid == 1) {
					int a = 0;
					a = 1;
				}
				if (!tMap.get(timezone).containsKey(cellid))
					tMap.get(timezone).put(cellid, 0.0);
				double newTraffic = tMap.get(timezone).get(cellid)
						.doubleValue()
						+ Traffic;
				tMap.get(timezone).put(cellid, newTraffic);
				// int day = CT.get(CT.DAY_OF_YEAR);
				System.out.println("   " + i++);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(cellIdMapLoc.size()+","+cellLocMapId.size() + "个基站");
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
