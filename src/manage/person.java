package manage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class person {
    protected boolean does_Element_exist(ResultSet rs) {
        if (rs != null) {
            try {
                return rs.isBeforeFirst();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }{
            return false;
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
