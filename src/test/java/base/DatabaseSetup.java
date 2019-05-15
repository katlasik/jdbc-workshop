package base;

import static com.ninja_squad.dbsetup.Operations.*;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.time.LocalDate;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import jdbc.utils.JDBCUrlBuilder;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DatabaseSetup implements BeforeAllCallback, BeforeEachCallback {

  private DbSetupTracker dbSetupTracker = new DbSetupTracker();
  private Config config = ConfigFactory.load();
  private DataSource dataSource = createDataSource();
  private DbSetup dbSetup = createDbSetup();

  private DataSource createDataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUser(config.getString("username"));
    dataSource.setPassword(config.getString("password"));
    dataSource.setUrl(
        JDBCUrlBuilder.build(
            config.getString("host"), config.getString("port"), config.getString("database")));
    return dataSource;
  }

  public DataSource getDatasource() {
    return dataSource;
  }

  private DbSetup createDbSetup() {
    Operation operation =
        sequenceOf(
            deleteAllFrom("school_class_students"),
            deleteAllFrom("school_classes"),
            deleteAllFrom("students"),
            deleteAllFrom("teachers"),
            insertInto("students")
                .columns("id", "first_name", "last_name", "birthdate")
                .values(1L, "Szymon", "Kowalski", LocalDate.parse("1999-02-03"))
                .values(2L, "Krystian", "Nowak", LocalDate.parse("1999-02-03"))
                .values(3L, "Krystyna", "Kowal", LocalDate.parse("1996-03-11"))
                .values(4L, "Błażej", "Rudnicki", LocalDate.parse("1998-12-03"))
                .build(),
            insertInto("teachers")
                .columns("id", "first_name", "last_name")
                .values(1L, "Damian", "Lewandowski")
                .values(2L, "Beata", "Woźniak")
                .values(3L, "Artur", "Wójcik")
                .build(),
            insertInto("school_classes")
                .columns("id", "teacher_id", "name")
                .values(1L, 1L, "Matematyka")
                .values(2L, 2L, "Fizyka")
                .values(3L, 3L, "Chemia")
                .build(),
            insertInto("school_class_students")
                .columns("school_class_id", "student_id")
                .values(1L, 1L)
                .values(2L, 1L)
                .values(2L, 2L)
                .values(3L, 3L)
                .values(3L, 4L)
                .build());

    return new DbSetup(new DataSourceDestination(dataSource), operation);
  }

  @Override
  public void beforeAll(ExtensionContext extensionContext) throws Exception {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    dbSetupTracker.launchIfNecessary(dbSetup);
  }
}
