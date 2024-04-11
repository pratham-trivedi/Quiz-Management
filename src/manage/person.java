package manage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class person {
    protected boolean is_stud_present(ResultSet stud) {
        try {
            return stud.isBeforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        protected  boolean match_pass(ResultSet res, String pass){
            try {
                res.next();
                return pass.equals(res.getString(4));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
}
