package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    try {
      if (ex instanceof IllegalArgumentException) { // IllegalArgumentException 예외 터지면
        log.info("IllegalArgumentException resolver to 400");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); // 여기서 먹어버림!
        return new ModelAndView();
      }
    } catch (IOException e) {
      log.error("resolver ex", e);
    }
    return null; // null로 해두면 예외가 터져서 날라감
  }
  /**
   * 빈 ModelAndView : 뷰 렌더링하지 않고, 정상 흐름으로 서블릿 리턴
   * ModelAndView : 뷰 렌더링
   * null : 다음 ExceptionResolver를 찾아서 실행. 없으면 예외처리 X, 기존에 발생한 예외를 서블릿 밖으로 던짐
   */
}