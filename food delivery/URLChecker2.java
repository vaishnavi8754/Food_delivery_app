import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class URLChecker2 {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/com/fooddelivery/dao/FoodItemDAO.java");
        String content = new String(Files.readAllBytes(path), "UTF-8");
        
        // Find each item definition to map URLs to item names
        Pattern itemPattern = Pattern.compile("addMock\\(mocks, restaurantId, \\d+, \"([^\"]+)\"[\\s\\S]*?\"(https://images\\.unsplash\\.com/[^\"]+)\"");
        Matcher m = itemPattern.matcher(content);
        
        Set<String> uniqueUrls = new HashSet<>();
        Map<String, List<String>> urlToItems = new HashMap<>(); // URL -> list of items using it
        
        while (m.find()) {
            String itemName = m.group(1);
            String url = m.group(2);
            uniqueUrls.add(url);
            urlToItems.putIfAbsent(url, new ArrayList<>());
            urlToItems.get(url).add(itemName);
        }
        
        System.out.println("Checking " + uniqueUrls.size() + " unique URLs...");
        Map<String, String> replacements = new HashMap<>();
        
        for (String urlStr : uniqueUrls) {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                if (conn.getResponseCode() == 404) {
                    System.out.println("BROKEN URL: " + urlStr);
                    System.out.println("  Items using this URL: " + String.join(", ", urlToItems.get(urlStr)));
                    
                    // Assign specific fallback URLs based on keywords in item names
                    String fallback = assignFallback(urlToItems.get(urlStr));
                    replacements.put(urlStr, fallback);
                    System.out.println("  -> Replaced with: " + fallback);
                }
            } catch (Exception e) {}
        }
        
        // Always fix Paneer Tacos with a specific Taco ID, even if its original URL wasn't technically 404
        Pattern pTacosurl = Pattern.compile("\"([^\"]+)\"\\);\\s*// End Paneer Tacos"); // Just match it manually later
        System.out.println("Found " + replacements.size() + " broken URLs.");
        
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        // Force replace Paneer Tacos image explicitly:
        // Current Paneer Tacos URL: https://images.unsplash.com/photo-1641024241941-bf99aca531c7?w=400 (this is the dummy one)
        content = content.replace("https://images.unsplash.com/photo-1641024241941-bf99aca531c7?w=400", "https://images.unsplash.com/photo-1564834724105-918b73d1b9e0?w=400");
        
        Files.write(path, content.getBytes("UTF-8"));
        System.out.println("Done repairing URLs.");
    }
    
    private static String assignFallback(List<String> items) {
        String names = String.join(" ", items).toLowerCase();
        
        if (names.contains("burger")) {
            if (names.contains("chicken")) return "https://images.unsplash.com/photo-1615719413546-198b25453f85?w=400";
            if (names.contains("fish")) return "https://images.unsplash.com/photo-1603064752734-4c48eff53d05?w=400";
            return "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?w=400";
        }
        if (names.contains("samosa")) return "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400";
        if (names.contains("fries")) return "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400";
        
        // Chinese stuff
        if (names.contains("noodle")) return "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400";
        if (names.contains("fried rice")) return "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400";
        if (names.contains("manchurian") || names.contains("chilli") || names.contains("dragon") || names.contains("pepper")) return "https://images.unsplash.com/photo-1598514983318-2f64f8f4796c?w=400";
        if (names.contains("soup")) return "https://images.unsplash.com/photo-1547928501-136fec61901a?w=400";
        if (names.contains("spring roll")) return "https://images.unsplash.com/photo-1544650039-34e9ce140960?w=400";
        
        return "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400"; // Random tasty salad/asian meal
    }
}
