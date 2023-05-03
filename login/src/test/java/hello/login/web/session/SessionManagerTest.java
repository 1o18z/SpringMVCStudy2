package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

public class SessionManagerTest {

  SessionManager sessionManager = new SessionManager();

  @Test
  void sessionTest() {
    // 세션 생성
    Member member = new Member();
    // HttpServletResponse 는 인터페이스라 테스트하기 어려움 -> MockHttpServletResponse 사용!
//    HttpServletResponse response = new MockHttpServletResponse();
    // 그런데 HttpServletResponse에는 getCookies가 없기 때문에 MockHttpServletResponse로!
    MockHttpServletResponse response = new MockHttpServletResponse();
    sessionManager.createSession(member, response);

    // 요청에 응답 쿠키 저장
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(response.getCookies());

    // 세션 조회
    Object result = sessionManager.getSession(request);
    Assertions.assertThat(result).isEqualTo(member);

    // 세션 만료
    sessionManager.expire(request);
    Object expired = sessionManager.getSession(request);
    Assertions.assertThat(expired).isNull();
  }
}
