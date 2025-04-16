package org.itmo.rnbbackend.config

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class AppConfig {
    private val dotenv: Dotenv = Dotenv.load()

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()

        dataSource.url = dotenv.get("POSTGRES_URL")
        dataSource.username = dotenv.get("POSTGRES_USER")
        dataSource.password = dotenv.get("POSTGRES_PASSWORD")
        dataSource.schema = dotenv.get("POSTGRES_SCHEMA")

        return dataSource
    }
}