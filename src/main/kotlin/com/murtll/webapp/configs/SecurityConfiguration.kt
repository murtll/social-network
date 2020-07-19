package com.murtll.webapp.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    private lateinit var dataSource: DataSource

    @Autowired
    fun setDataSource (_dataSource: DataSource) {
        dataSource = _dataSource
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.jdbcAuthentication()
                ?.dataSource(dataSource)
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/register", "/logout", "/login").permitAll()
                .anyRequest().hasAnyRole("ADMIN", "USER")
                .and().formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/check_user")
    }
}