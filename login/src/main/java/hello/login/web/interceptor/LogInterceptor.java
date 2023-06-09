package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

  public static final String LOG_ID = "logId";
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String requestURI = request.getRequestURI();
    String uuid = UUID.randomUUID().toString();

    request.setAttribute(LOG_ID, uuid);

    //@RequestMapping를 사용하는 경우 - HandlerMethod 넘어옴
    // 정적 리소스를 사용하는 경우 - ResourceHttpRequestHandler 넘어옴

    if(handler instanceof HandlerMethod){
      HandlerMethod hm = (HandlerMethod) handler;// 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있음
    }

    log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
    return true; // false 하게되면 다음 컨트롤러 호출하지 않고 여기서 끝내버림!

  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("[{}]", modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    String requestURI = request.getRequestURI();
    Object logId = (String)request.getAttribute(LOG_ID); // preHandle의 uuid를 받음
    log.info("REQUEST [{}][{}][{}]", logId, requestURI, handler);

    if(ex != null){
      log.error("afterCompletion error!!", ex);
    }
  }
}
