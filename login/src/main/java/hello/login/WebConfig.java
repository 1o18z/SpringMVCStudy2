package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig {

  @Bean
  public FilterRegistrationBean logFilter() { // WAS를 띄울 때 필터를 같이 넣어줌!
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LogFilter());
    filterRegistrationBean.setOrder(1); // 순서 정해주는 것
    filterRegistrationBean.addUrlPatterns("/*"); // 모든 URI에 다 적용됨

    return filterRegistrationBean;
  }
}
