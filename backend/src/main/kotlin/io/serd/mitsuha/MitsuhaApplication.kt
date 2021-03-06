package io.serd.mitsuha

import io.serd.mitsuha.dao.TableInitializer
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.sql.DataSource


@SpringBootApplication
class MitsuhaApplication : WebMvcConfigurer {
    @Bean
    fun transactionManager(@Qualifier("dataSource") dataSource: DataSource) = SpringTransactionManager(dataSource)

    @Bean
    fun init(tableInitializer: TableInitializer) = CommandLineRunner {
        tableInitializer.createTables()
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MitsuhaApplication::class.java, *args)
}
