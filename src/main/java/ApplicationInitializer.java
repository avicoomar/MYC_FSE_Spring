import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.DispatcherType;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

import config.AppConfig;
import filter.JWTFilter;
import filter.RBACFilter;
import filter.CorsFilter;

public class ApplicationInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
		
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(AppConfig.class);
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("frontcontroller", new DispatcherServlet(appContext));
		dispatcher.addMapping("/frontcontroller/*");  

		//Filter 0 (optional - refer CorsFilter comments):
		FilterRegistration.Dynamic corsFilter = servletContext.addFilter("CorsFilter", new CorsFilter());
		corsFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/frontcontroller/*");
		
		//Filter 1:
		FilterRegistration.Dynamic jwtFilter = servletContext.addFilter("JWTFilter", new JWTFilter());
		jwtFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/frontcontroller/*");
		
		//Filter 2:
		FilterRegistration.Dynamic rbacFilter = servletContext.addFilter("RBACFilter", new RBACFilter());
		rbacFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/frontcontroller/*");
		
	}
}
