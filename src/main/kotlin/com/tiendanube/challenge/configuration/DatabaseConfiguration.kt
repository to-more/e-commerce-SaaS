package com.tiendanube.challenge.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource



@Configuration
@EnableJdbcRepositories
class DatabaseConfiguration {

  @Value("\${datasource.postgre.query-timeout}")
  var queryTimeout = 0

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "datasource.postgre")
  fun dataSourcePostgre() = DataSourceBuilder.create().build()


  @Bean
  fun jdbcTemplatePostgre(@Qualifier("dataSourcePostgre") dataSource: DataSource): JdbcTemplate {
    val template = JdbcTemplate(dataSource)
    template.queryTimeout = queryTimeout
    return template
  }

  @Bean
  fun namedParametersJdbcTemplate(@Qualifier("jdbcTemplatePostgre") jdbcTemplatePostgre: JdbcTemplate) =
    NamedParameterJdbcTemplate(jdbcTemplatePostgre)
}