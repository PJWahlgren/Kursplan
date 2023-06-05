package DataAdapter;

public class CourseInfo implements CourseData{
    private final String courseID;
    private final String courseName;
    private final double courseCredit;

    private final String courseLevel;
    private final String courseURL;

    @Override
    public String toString() {
        return courseID + " | " +
                courseName;
    }

    public CourseInfo(String courseID, String courseName, double courseCredit, String courseLevel, String courseURL) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseCredit = courseCredit;
        this.courseLevel = courseLevel;
        this.courseURL = courseURL;
    }

    @Override
    public String getCourseID() {
        return courseID;
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    @Override
    public String getEducationLevel() {
        return courseLevel;
    }
    @Override
    public String getCourseURL() {
        return courseURL;
    }

    @Override
    public double getCourseCredit() {
        return courseCredit;
    }
}
