package db;

import DataAdapter.CourseData;
import DataAdapter.CourseInfo;
import DataAdapter.GUCourses;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Iterator;
import java.util.Scanner;

public class PortalConnection {
    private static Connection conn;
    private static PortalConnection instance;
            /**
             * Connect to a sample database
             */
            private PortalConnection() throws SQLException, FileNotFoundException {
                    // db parameters
                    String url = "jdbc:sqlite:src/db/Kursplan.db";
                    // create a connection to the database
                    conn = DriverManager.getConnection(url);
                    System.out.println("Connection to SQLite has been established.");


                    }
    public static PortalConnection getInstance() {
        PortalConnection result = instance;
        if (result != null) {
            return result;
        }
        synchronized(PortalConnection.class) {
            if (instance == null) {
                try{
                    instance = new PortalConnection();

                }catch (SQLException | FileNotFoundException e){
                    System.err.println(e.getMessage());
                        }
                    }
                }
        return instance;
            }

            public void insertCourse(CourseData data) throws SQLException {
                    PreparedStatement pst = conn.prepareStatement("INSERT INTO Courses VALUES(?,?,?,?,?);");
                    pst.setString(1,data.getCourseID());
                    pst.setString(2,data.getCourseName());
                    pst.setDouble(3,data.getCourseCredit());
                    pst.setString(4, data.getEducationLevel());
                    pst.setString(5, data.getCourseURL());
                    pst.execute();
            }
            public CourseData getCourse(String courseID) throws SQLException {
                PreparedStatement pst = conn.prepareStatement("SELECT * FROM Courses WHERE code = ?;");
                pst.setString(1,courseID);
                ResultSet rs = pst.executeQuery();
                return new CourseInfo(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("credits"),
                        rs.getString("level"),
                        rs.getString("link"));

            }

            public void insertReadingPeriod(String code, int readingPeriod) throws SQLException {
                PreparedStatement pst = conn.prepareStatement("INSERT INTO HeldIn VALUES(?,?)");
                pst.setString(1,code);
                pst.setInt(2,readingPeriod);
                pst.execute();

            }

            public void buildSetup() throws FileNotFoundException, SQLException {
                Scanner sc = new Scanner(new File("src/db/setup.sql"));
                StringBuilder sb = new StringBuilder();
                Statement setup = conn.createStatement();
                while (sc.hasNext()){
                    String temp = sc.nextLine().trim();
                    if (!temp.contains("--"))
                        sb.append(temp);
                    if (temp.contains(";")){
                        setup.execute(sb.toString());
                        sb = new StringBuilder();
                    }
                }
            }

            public void closeConnection() {
                try{
                    conn.close();
                }
                catch(SQLException e){
                    System.err.println(e.getMessage());
                }

            }

}
