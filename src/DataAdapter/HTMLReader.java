package DataAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;

public class HTMLReader {
    private CourseData course;
    private Set<Integer> readingPeriod;

    public HTMLReader(CourseData course) throws Exception {
        this.course = course;
        Document doc = Jsoup.connect(course.getCourseURL()).get();
        List<String> times = new ArrayList<>();
        doc.select("time").forEach(e -> times.add(e.text()));
        System.out.println(course.getCourseName());
        readingPeriod = new HashSet<>();
        for (int i = 0; i < times.size()-1; i = i + 4) {
            readingPeriod.add(getReadingPeriod(times.get(i)));
        }
        readingPeriod.forEach(System.out::println);


    }
    public List<Integer> getReadingPeriod(){
        return readingPeriod.stream().toList();
    }
    private int getReadingPeriod(String date){
        return switch (date.split(" ")[1]) {
            case "aug" -> 1;
            case "okt" -> 2;
            case "jan" -> 3;
            case "mar" -> 4;
            default -> 0;
        };
    }
}
