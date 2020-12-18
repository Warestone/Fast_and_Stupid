package FastAndStupid.Server;

import java.sql.*;
import java.util.InputMismatchException;

public class ConnectToDB {                                      // class for execute queries to database
    public String executeQuery(String query){                   // return done/error/done:'total score'
        if (query == null) throw new InputMismatchException();
        if (query.equals("")) throw new InputMismatchException();
        try {
            Class.forName("org.sqlite.JDBC");       // get jdbc driver
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+ getClass().getResource("/SCORE.db").getFile());
            PreparedStatement preparedStatement = conn.prepareStatement(query);     // create connection
            preparedStatement.execute();// execute query
            //'INSERT INTO' NOT WORKING, BUT NO ERRORS
            //INSERT SELECT QUERY FOR AUTH
            //INSERT UPDATE QUERY FOR GAME END
            preparedStatement.close();
            conn.close();
            return "done";
        }
        catch (Exception s) {s.printStackTrace();}
        return "error";
    }
}
