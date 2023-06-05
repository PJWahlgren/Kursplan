import DataAdapter.CourseAPIConnection;
import DataAdapter.CourseData;
import DataAdapter.GUCourses;
import DataAdapter.HTMLReader;
import db.PortalConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws SQLException, Exception {
        Iterator<CourseData> courses = new GUCourses();
        PortalConnection db = PortalConnection.getInstance();
        db.buildSetup();
        while (courses.hasNext()){
            CourseData course = courses.next();
            db.insertCourse(course);
            HTMLReader read = new HTMLReader(course);
            for (Integer period: read.getReadingPeriod())
                db.insertReadingPeriod(course.getCourseID(),period);
        }
    }
}