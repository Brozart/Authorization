package brozart.authorization.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    private final Environment env;

    @Autowired
    public DatabaseConfig(final Environment env) {
        this.env = env;
    }

    @Bean
    DataSource dataSource() {
        final HikariConfig configuration = new HikariConfig();
        configuration.setInitializationFailTimeout(-1);
        configuration.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        configuration.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        configuration.setUsername(env.getRequiredProperty("spring.datasource.username"));
        configuration.setPassword(env.getRequiredProperty("spring.datasource.password"));
        configuration.setConnectionTimeout(env.getRequiredProperty("spring.datasource.hikari.connection-timeout", Long.class));

        return new HikariDataSource(configuration);
    }
}
