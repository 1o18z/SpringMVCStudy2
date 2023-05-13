package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("log filter init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.info("log filter doFilter");

    // ServletRequest는 HttpServletRequest의 부모 인터페이스인데 기능이 별로 없어서 다운캐스팅 해서 사용!
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();

    // 사용자 요청 온 거를 구분하기 위해서 uuid 받음
    String uuid = UUID.randomUUID().toString();

    try {
      log.info("REQUEST [{}][{}]", uuid, requestURI);
      chain.doFilter(request, response);
      // 있으면 다음 필터를, 없으면 서블릿이 호출된다
    } catch (Exception e) {
      throw e;
    } finally {
      log.info("RESPONSE[{}][{}]", uuid, requestURI);

    }
  }

  @Override
  public void destroy() {
    log.info("log filter destroy");
  }
}
