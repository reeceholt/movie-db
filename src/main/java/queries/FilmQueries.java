package queries;

import utils.ResultPrinter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmQueries {
    public static void getAllFilmLengths(Connection conn, String searchTerm) {
        String sqlStatement = searchTerm;
        try{
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            try( ResultSet result = stmt.executeQuery();) {
               ResultPrinter.print(result);
            }
        }catch (SQLException e){
            System.err.println("ERROR in getAll Films");
            throw new RuntimeException(e);
        }
    }

    public static void getTop10Under60(Connection conn, String searchTerm) {
        try{
            PreparedStatement stmt = conn.prepareStatement(searchTerm);
            try (ResultSet result = stmt.executeQuery();){
                ResultPrinter.print(result);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }//method

    public static void getAvgCostOfFilmsInLangs(Connection conn, String searchTerm){
        try{
            PreparedStatement stmt = conn.prepareStatement(searchTerm);
            try(ResultSet result = stmt.executeQuery();){
                System.out.println("Average replacement costs of films in each language");
                System.out.println("-----------------------------------------");
                while (result.next()) {
                    String language = result.getString("lang");
                    double cost = result.getDouble("avg_replacement_cost");
                    System.out.println(language + ") $" + cost);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void getAllFilmsWithTrailers(Connection conn, String searchTerm){
        try{
            PreparedStatement stmt = conn.prepareStatement(searchTerm);
            try(ResultSet result = stmt.executeQuery();){
                System.out.println("All films with trailers");
                System.out.println("-----------------------------------------");
                int k = 1;
                while (result.next()) {
                    String title = result.getString("title");
                    System.out.println(k + ".) " + title);
                    k++;
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
