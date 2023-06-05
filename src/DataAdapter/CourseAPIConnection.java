package DataAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CourseAPIConnection {
    private String request;
    private final String url = "https://www.gu.se/api/search/rest/apps/external_web/searchers/education_sv?hits=200&";

    public CourseAPIConnection(){
        this("");
    }
    private CourseAPIConnection(String path) {
        StringBuilder info = new StringBuilder();
        try{
            URL restAPIkey = new URL(url + path);
            HttpURLConnection conn = (HttpURLConnection) restAPIkey.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            //200 = success, everything else is a failure
            if (responseCode != 200)
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            else {
                System.out.println("Succesfully connected with path: " + url + path);
                Scanner scanner =  new Scanner(restAPIkey.openStream());

                while(scanner.hasNext())
                    info.append(scanner.nextLine());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        request = info.toString();
    }

    public String getRequest() {
        return request;
    }
    private JsonObject getFilter(int setting, int specification){
        //lol wtf
        return JsonParser.parseString(request)
                .getAsJsonObject()
                .getAsJsonArray("facets")
                .get(setting)
                .getAsJsonObject()
                .get("filters")
                .getAsJsonArray()
                .get(specification)
                .getAsJsonObject();
    }
    public CourseAPIConnection getQueryPath(int setting, int specification){
        String path = getFilter(setting,specification).get("query").getAsString();
        return new CourseAPIConnection(path);
    }
   public String getCourseSubject(Subject subject){
        return getFilter(2,subject.ordinal()).get("displayName").getAsString();
   }

    private String removeQuotation(String string){
        return string.substring(1,string.length()-1);
    }
}
