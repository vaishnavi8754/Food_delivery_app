import java.net.HttpURLConnection;
import java.net.URL;

public class CheckImages2 {
    public static void main(String[] args) {
        String[] urls = {
            "https://images.unsplash.com/photo-1527477396000-e27163b481c2?w=400", // Chicken Wings
            "https://images.unsplash.com/photo-1569058242253-92a9c755a0ec?w=400", // Chicken wings
            "https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?w=400", // Current Chicken Lollipop
            "https://images.unsplash.com/photo-1626083884339-6523177ebc6f?w=400", // Another wings
            "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400"
        };
        for (String u : urls) {
            try {
                HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection();
                c.setRequestMethod("HEAD");
                System.out.println(c.getResponseCode() + " : " + u);
            } catch (Exception e) {}
        }
    }
}
