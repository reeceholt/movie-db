//public class Database {
//    public void close(){}
//    public sum getConnection(){}
//    public void open(){}
//
//}
/*
*   Constructor (connection String) implement AutoCloseable
*   Connect method
*       Creates Connection
*       getConnection()
*               returns connection
*    close connection
*
*
* Database.java
*   connect void
*   getConnection Connection
*   close void
    * Database db = new Database("jdbc:sqlite:/Users/...")
    * db.connect()
    * Connection conn = db.getConneciton()
    * conn.prepareStatement
    * ...
    * db.close()
*
*
* */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class Database implements AutoCloseable{
    private Connection connection;
    private String URL;

    public Database(String URL) {
        this.URL = URL;
    }


    public void connect() throws SQLException{
        if(connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void close(){
        if(connection!=null){
            try {
                connection.close();
            }
            catch(SQLException s){
                System.err.println("There was an error in your program");
            }
        }

    }

}