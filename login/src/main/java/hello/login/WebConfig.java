package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
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

  @Bean
  public FilterRegistrationBean logCheckFilter() { // WAS를 띄울 때 필터를 같이 넣어줌!
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LoginCheckFilter());
    filterRegistrationBean.setOrder(2); // 순서 정해주는 것
    filterRegistrationBean.addUrlPatterns("/*"); // 경로를 ("/items/. . .") 이렇게 해줘도 되지만 나중에 추가할 기능이 생겨도 설정을 바꾸지 않기 위해 여기는 전체 경로로 하고 LogCheckFilter에 whiteList 추가한 거!
  // 어? 필터를 한 번 더 호출하면 성능이 떨어지는 거 아냐?! => 티도 안남 걱정 놉

    return filterRegistrationBean;
  }
}