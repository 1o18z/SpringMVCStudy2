package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
            .order(1)
            .addPathPatterns("/**") // 전체 경로 다 되지만 (1)
            .excludePathPatterns("/css/**", "/*.ico", "/error"); // 이 경로는 로그인 체크 인터셉터 적용하지 마! (2)
          // -> 인터셉터가 서블릿 필터보다 정밀한 기능을 제공한다는 이유!
          /*
          *은 경로(/) 안에서 0개 이상의 문자가 일치할 경우를 뜻하고,,
          **은 경로 끝까지 0개 이상의 경로(/)가 일치할 경우!
           */
    registry.addInterceptor(new LoginCheckInterceptor())
            .order(2)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/members/add", "/login", "/logout",
                    "/css/**", "/*.ico", "/error");
  }

//  @Bean
  public FilterRegistrationBean logFilter() { // WAS를 띄울 때 필터를 같이 넣어줌!
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LogFilter());
    filterRegistrationBean.setOrder(1); // 순서 정해주는 것
    filterRegistrationBean.addUrlPatterns("/*"); // 모든 URI에 다 적용됨

    return filterRegistrationBean;
  }

//  @Bean
  public FilterRegistrationBean logCheckFilter() { // WAS를 띄울 때 필터를 같이 넣어줌!
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LoginCheckFilter());
    filterRegistrationBean.setOrder(2); // 순서 정해주는 것
    filterRegistrationBean.addUrlPatterns("/*"); // 경로를 ("/items/. . .") 이렇게 해줘도 되지만 나중에 추가할 기능이 생겨도 설정을 바꾸지 않기 위해 여기는 전체 경로로 하고 LogCheckFilter에 whiteList 추가한 거!
  // 어? 필터를 한 번 더 호출하면 성능이 떨어지는 거 아냐?! => 티도 안남 걱정 놉

    return filterRegistrationBean;
  }
}