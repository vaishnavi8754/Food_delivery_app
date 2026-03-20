import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class URLChecker {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/com/fooddelivery/dao/FoodItemDAO.java");
        String content = new String(Files.readAllBytes(path));
        Pattern p = Pattern.compile("https://images\\.unsplash\\.com/photo-[A-Za-z0-9\\-]+(\\?auto=[^&]+&fit=[^&]+&w=[0-9]+&q=[0-9]+|\\?w=[0-9]+)");
        Matcher m = p.matcher(content);
        
        Set<String> uniqueUrls = new HashSet<>();
        while (m.find()) {
            uniqueUrls.add(m.group());
        }
        
        List<String> brokenUrls = new ArrayList<>();
        System.out.println("Checking " + uniqueUrls.size() + " unique URLs...");
        for (String urlStr : uniqueUrls) {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                if (conn.getResponseCode() == 404) {
                    brokenUrls.add(urlStr);
                }
            } catch (Exception e) {}
        }
        
        System.out.println("Found " + brokenUrls.size() + " broken URLs.");
        
        // Replacement maps for specific items (as per your request)
        String samosaUrl = "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400";
        String friesUrl = "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400";
        String backupPizzaUrl = "https://images.unsplash.com/photo-1564936281291-294551497d81?w=400"; // margherita pizza
        
        for (String broken : brokenUrls) {
            if (broken.contains("1601050633622-3f5ef970fd66")) {
                content = content.replace(broken, samosaUrl);
            } else if (broken.contains("1573014167391-aa4d132dd04b")) {
                content = content.replace(broken, friesUrl);
            } else {
                content = content.replace(broken, backupPizzaUrl);
            }
        }
        
        Files.write(path, content.getBytes());
        System.out.println("Done repairing URLs.");
    }
}
