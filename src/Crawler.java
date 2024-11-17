import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {
    public static void main(String[] args) {

        String url = "https://en.wikipedia.org/";
        crawl(1, url, new ArrayList<String>());
    }
    private static void crawl(int level, String url, ArrayList<String> visited) {
        if(level <= 5 ){
            Document doc = request(url, visited);
            if(doc != null){
                for(Element e : doc.select("a[href]")){
                    String next_link = e.absUrl("href");
                    if(visited.contains(next_link) == false){
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }
    private static Document request(String url, ArrayList<String> v){
        Document doc = null;
        try{
            Connection con = Jsoup.connect(url);
            doc = con.get();
            if(con.response().statusCode() == 200){
                System.out.println("Link: " + url);
                System.out.println("Title: " + doc.title());
                v.add(url);
                return doc;
            }
        }catch (Exception e){
            return null;
        }
        return doc;
    }
}
