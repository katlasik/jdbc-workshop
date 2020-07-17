package sqli;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import jdbc.exception.RepositoryException;
import jdbc.utils.JDBCUrlBuilder;

import java.io.File;
import java.sql.*;

class LoginHandler {

    class Result {
        public final boolean success;
        public final String sql;

        public Result(boolean success, String sql) {
            this.success = success;
            this.sql = sql;
        }
    }

    private final MysqlDataSource dataSource = new MysqlDataSource();


    public LoginHandler() {
        Config config = ConfigFactory.parseFile(new File("src/test/resources/application.properties"));
        dataSource.setUser(config.getString("username"));
        dataSource.setPassword(config.getString("password"));
        dataSource.setUrl(
                JDBCUrlBuilder.build(
                        config.getString("host"), config.getString("port"), config.getString("database")));

    }

    Result login(String name, String password) {
        String sql = "SELECT id FROM secret.users WHERE login = '" + name + "' AND password = '" + password + "'";

        System.out.println("Using sql: " + sql + ".");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            return new Result(rs.next(),sql);

        } catch (SQLException exception) {
            throw new RepositoryException(exception);
        }
    }

}
