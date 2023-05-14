package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

  private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"}; // 로그인 안 된 사용자 접근 가능 경로!

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();

    HttpServletResponse httpResponse = (HttpServletResponse) response;

    try {
      log.info("인증 체크 필터 시작{}", requestURI);

      if (isLoginCheckPath(requestURI)) {
        log.info("인증 체크 로직 실행{}", requestURI);
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) { // 세션이 없거나(로그인이 안됐거나) 세션 상수의 값도 없으면
          log.info("미인증 사용자 요청{}", requestURI);
          // 로그인으로 redirect
          httpResponse.sendRedirect("/login?redirectURL=" + requestURI); // 로그인 페이지 가서 로그인 하면 다시 원래 있던 페이지로 redirect
          return;
        }
      }
      chain.doFilter(request, response);
    }catch (Exception e){
      throw e; // 예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함 (서블릿에서 예외가 터져서 올라오는데 여기서 먹어버리면 정상처럼 동작 해버림! -> 서블릿 컨테이너까지 (WAS까지) 올려줘야 함)
    } finally {
      log.info("인증 체크 필터 종료{}", requestURI);
    }
  }
  /*
  화이트 리스트의 경우 인증 체크X
   */

  private boolean isLoginCheckPath(String requesetURI) {
    return !PatternMatchUtils.simpleMatch(whiteList, requesetURI); // 단순히 whiteList와 요청URI가 매칭 되는가 확인!
  }
}
