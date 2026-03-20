import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class ListItems {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/com/fooddelivery/dao/FoodItemDAO.java");
        String content = new String(Files.readAllBytes(path), "UTF-8");
        
        System.out.println("Items in FoodItemDAO:");
        Pattern itemPattern = Pattern.compile("addMock\\(mocks, restaurantId, \\d+, \"([^\"]+)\"[\\s\\S]*?\"([^\"]*)\"");
        Matcher m = itemPattern.matcher(content);
        
        while (m.find()) {
            System.out.println(m.group(1) + "  |||  " + m.group(2));
        }
    }
}
