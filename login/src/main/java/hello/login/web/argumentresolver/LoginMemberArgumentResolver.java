package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    log.info("supportsParameter 실행");
    boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
    boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

    return hasLoginAnnotation && hasMemberType; // 둘 다 만족해야 아래 resolceArgument() 실행

  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    log.info("resolceArgument 실행");

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    HttpSession session = request.getSession(false);// true로 하면 세션이 만들어지기 때문에 false! (의미없는 세션 만들어지면 안돼!)

    if(session == null){
      return null;
    }

//    Object member = session.getAttribute(SessionConst.LOGIN_MEMBER);
//    return member;
    return session.getAttribute(SessionConst.LOGIN_MEMBER);
  }
}