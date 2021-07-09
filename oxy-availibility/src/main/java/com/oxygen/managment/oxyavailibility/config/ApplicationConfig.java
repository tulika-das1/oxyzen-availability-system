package com.oxygen.managment.oxyavailibility.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer  {
	@Value( "${jdbc.username}" )
	private String jdbcUsername;
	@Value( "${jdbc.password}" )
	private String jdbcPassword;
	
	
	@Bean
	  public InternalResourceViewResolver getViewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("/WEB-INF/view/");
	    viewResolver.setSuffix(".jsp");
	    return viewResolver;
	  }

	  @Override
	  public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("index");
	  }
	  
	  
	  @Override
	  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	  }

	  @Bean
	    public DataSource mysqlDataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        dataSource.setUrl("jdbc:mysql://localhost:3306/oxy_avail");
	        dataSource.setUsername(jdbcUsername);
	        dataSource.setPassword(jdbcPassword);

	        return dataSource;
	    }
	  
//	  @Bean(name="userDetailsService")
//	    public UserDetailsService userDetailsService(){
//		/*
//		 * User user = null ; user.getAuthorities();
//		 */
//		  //TODO: override loadUserByUsername of JdbcDaoImpl and also create an class that extends User
//	     JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
//	     jdbcImpl.setDataSource(mysqlDataSource());
//	     jdbcImpl.setUsersByUsernameQuery("select oau_mobile_no,oau_password, enabled from oxy_avail.oau_users where oau_mobile_no=?");
//	     jdbcImpl.setAuthoritiesByUsernameQuery("select b.oau_mobile_no, a.oaa_authority from oxy_avail.oaa_authorities a, oxy_avail.oau_users b where b.oau_mobile_no=? and a.oaa_mobileno=b.oau_mobile_no");
//	     return jdbcImpl;
//	    }

}
