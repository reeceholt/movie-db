package utils;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultPrinter {
    public static void print(ResultSet result) throws SQLException {
        ResultSetMetaData metaData =result.getMetaData();
        int colCount = metaData.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            System.out.print("%-10.10s | ".formatted(metaData.getColumnName(i)));
        }
        System.out.println("\n-----------------------------------------");
        while (result.next()) {
            for (int i = 1; i <= colCount; i++) {
                String value = result.getString(i);
                System.out.print("%-10.10s | ".formatted(value));
            }//for
            System.out.println();
        }//while
    }
}
