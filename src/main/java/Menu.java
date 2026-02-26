import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static queries.FilmQueries.*;

public class Menu {

    public static void run(Connection conn) {
        String prompt = """
                <1> List all films titles and lengths
                <2> List the Top 10 films under 60 minutes
                <3> List films with rating ____ (G, PG, PG-13, R, NC-17)
                <4> List all films with trailers
                <5> List the average replacement cost of films in each language
                <0> Exit
                """;
        try {
            boolean user = true;
            while (user) {
                System.out.println(prompt);
                Scanner input = new Scanner(System.in);
                int userNum = parseInt(input.nextLine());


                String sql = "";
                PreparedStatement stmt = null;
                ResultSet result = null;
                switch (userNum) {
                    case 0:
                        System.out.println("Goodbye");
                        user = false;
                        break;

                    case 1:
                        getAllFilmLengths(conn, "SELECT title, length FROM film;");
                        break;
                    case 2:
                        sql = """
                                SELECT title, length FROM film
                                WHERE length < 60
                                LIMIT 10
                                """;
                        getTop10Under60(conn, sql);
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
                        String userRating = ratingInput.nextLine();
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
                                WHERE special_features LIKE '%Trailers%'
                                """;
                        getAllFilmsWithTrailers(conn, sql);
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
                        getAvgCostOfFilmsInLangs(conn, sql);
                        break;
                }//switch
            }//while
        } catch (SQLException e) {
            System.err.println("Error!");
        }//catch
    }//psvr

}//class

