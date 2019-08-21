package connection;

import base.DatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.sql.SQLException;

public class DatabaseSetupTest {

    @RegisterExtension
    static DatabaseSetup db = new DatabaseSetup();

    @DisplayName("Should connect database correctly")
    @Test
    void test() throws SQLException {
        db.getDatasource().getConnection().createStatement().execute("SELECT 1");
    }

}
