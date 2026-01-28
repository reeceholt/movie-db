import java.sql.DriverManager;
import java.sql.*;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:/Users/reeceholt/SoftwareEngineering/CSHonors25-26/Databases/Sakila";
        boolean user = true;
        String prompt = """
                <1> List all films titles and lengths
                <2> List the Top 10 films under 60 minutes
                <3> List films with rating ____ (G, PG, PG-13, R, NC-17)
                <4> List all films with trailers
                <5> List the average replacement cost of films in each language
                <0> Exit
                """;

        if(user) {
            System.out.println(prompt);
            Scanner input = new Scanner(System.in);
            int userNum = parseInt(input.nextLine());


            Connection conn = DriverManager.getConnection(url);
            String sql = "";
            PreparedStatement stmt = null;
            ResultSet result = null;
            switch (userNum) {
                case 0:
                    System.out.println("Goodbye");
                    user = false;
                    break;

                case 1:
                    sql = "SELECT title, length FROM film;";
                    stmt = conn.prepareStatement(sql);
                    result = stmt.executeQuery();
                    System.out.println("Title" + " ->         " + " Length: ");
                    System.out.println("-----------------------------------------");
                    while (result.next()) {
                        int length = result.getInt("length");
                        String title = result.getString("title");
                        System.out.println(title + ": " + length + " mins");
                    }
                    break;
                case 2:
                    sql = """
                            SELECT title FROM film
                            WHERE length < 60
                            LIMIT 10
                            """;
                    stmt = conn.prepareStatement(sql);
                    result = stmt.executeQuery();
                    System.out.println("Top 10 films under 90 minutes: ");
                    System.out.println("-----------------------------------------");
                    int i = 1;
                    while (result.next()) {
                        String title = result.getString("title");
                        System.out.println(i + ".) " + title);
                        i++;
                    }
                    break;
                case 3:
                    System.out.println("""
                            (G, PG, PG-13, R, NC-17)
                            Enter Rating -> """);

                    sql = """
                            SELECT title, rating FROM film
                            WHERE rating = ?
                            """;
                    Scanner ratingInput = new Scanner(System.in);
                    String userRating = input.nextLine();
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, userRating);
                    result = stmt.executeQuery();


                    System.out.println("Films with " + userRating + " rating:");
                    System.out.println("-----------------------------------------");

                    int j = 1;
                    while (result.next()) {
                        String title = result.getString("title");
                        System.out.println(j + ".) " + title);
                        j++;

                    }
                    break;
                case 4:
                    sql = """
                            SELECT title, special_features FROM film
                            WHERE special_features LIKE "%Trailers%"
                            """;

                    stmt = conn.prepareStatement(sql);
                    result = stmt.executeQuery();
                    System.out.println("All films with trailers");
                    System.out.println("-----------------------------------------");
                    int k = 1;
                    while (result.next()) {
                        String title = result.getString("title");
                        System.out.println(k + ".) " + title);
                        k++;
                    }
                    break;
                case 5:
                    sql = """
                            SELECT l.name AS lang, AVG(f.replacement_cost) AS avg_replacement_cost
                            FROM film AS f
                            JOIN "language" l
                            ON f.language_id = l.language_id
                            GROUP BY lang
                            ORDER BY avg_replacement_cost
                            """;
                    System.out.println("Average replacement costs of films in each language");
                    System.out.println("-----------------------------------------");

                    stmt = conn.prepareStatement(sql);
                    result = stmt.executeQuery();

                    while (result.next()) {
                        String language = result.getString("lang");
                        double cost = result.getDouble("avg_replacement_cost");
                        System.out.println(language + ") $" + cost);
                    }
                    break;


            }
        }
    }
}
