import java.sql.Connection;
import java.sql.SQLException;

public class App {

    public static void run() throws SQLException{

        try (
                Database db = new Database("jdbc:sqlite:/Users/reeceholt/SoftwareEngineering/CSHonors25-26/Databases/Sakila");
        ) {
            db.connect();
            Connection conn = db.getConnection();
            Menu.run(conn);
        }
    }
}
