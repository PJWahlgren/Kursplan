package DataAdapter;

import com.google.gson.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GUCourses implements Iterator<DataAdapter.CourseData>, CourseData {

    private JsonArray jsonData;
    private List<CourseData> courseData;
    private Iterator<CourseData> courseDataIterator;
    private JsonElement currentElement;
    private Subject currentSubject;
    CourseAPIConnection conn;

    public GUCourses() {
        conn = new CourseAPIConnection().getQueryPath(0,0);
        courseData = new ArrayList<>();
        addCourses(Subject.IT);
        courseDataIterator = courseData.iterator();

    }
    private void addCourses(Subject subject){
        currentSubject = subject;
        String courses = getCourses(subject).getRequest();
        jsonData = getDocuments(courses);
        currentElement = null;
        for (JsonElement element : jsonData){
            currentElement = element;
            courseData.add(
                    new CourseInfo(getCourseID(),
                            getCourseName(),
                            getCourseCredit(),
                            getEducationLevel(),
                            getCourseURL()
                    ));
        }
    }

    private CourseAPIConnection getCourses(Subject subject){
        return conn.getQueryPath(2,subject.ordinal());
    }

    private JsonArray getDocuments(String request){
        return JsonParser.parseString(request).getAsJsonObject()
                .getAsJsonObject("documentList")
                .getAsJsonArray("documents");

    }
    private String getQuery(JsonElement element, String query){
        if (element.getAsJsonObject().get(query) == null)
            return "EMPTY";
        String q = element.getAsJsonObject().get(query).toString();
        return q.substring(1,q.length()-1);
    }

    public String getCourseID() {
        return getQuery(currentElement,"education_course_code");
    }

    public String getCourseName() {
        return getQuery(currentElement,"title");
    }

    public String getEducationLevel() {
        return getQuery(currentElement,"education_label");
    }

    public String getCourseURL() {
        return "https://www.gu.se" + getQuery(currentElement,"url");
    }

    public double getCourseCredit() {
        return Double.parseDouble(getQuery(currentElement,"academic_credits"));
    }

    public int getSize() {
        return jsonData.size();
    }

    @Override
    public boolean hasNext() {
        return courseDataIterator.hasNext();
    }

    @Override
    public CourseData next() {
        return courseDataIterator.next();
    }
}
