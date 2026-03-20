import java.net.*;
import java.util.*;

public class CheckImages {
    public static void main(String[] args) {
        String[] urls = {
            "https://images.unsplash.com/photo-1544650039-34e9ce140960?w=400", // Spring rolls / Asian
            "https://images.unsplash.com/photo-1563227812-0ea4c22e6cc8?w=400", // Asian / Rice / Dish
            "https://images.unsplash.com/photo-1626083884339-6523177ebc6f?w=400", // Chicken Wings
            "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400", // Fried Rice (previously worked)
            "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400", // Noodles (previously worked)
            "https://images.unsplash.com/photo-1564834724105-918b73d1b9e0?w=400", // Tacos (previously worked)
            "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400", // Pizza Peperoni (Alternative)
            "https://images.unsplash.com/photo-1512058564366-18510be2db19?w=400", // Fried Rice
            "https://images.unsplash.com/photo-1534422298391-e4f8c172dd36?w=400", // Dumplings/momos
            "https://images.unsplash.com/photo-1528736235302-52922df5c122?w=400", // Samosa / pastry alternative
            "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400", // Indian food
            "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400", // Samosa working
            "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400", // Briyani
            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400"  // Salad / Asian Bowl
        };
        for (String u : urls) {
            try {
                HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection();
                c.setRequestMethod("HEAD");
                int code = c.getResponseCode();
                System.out.println(code + " : " + u);
            } catch (Exception e) {}
        }
    }
}
