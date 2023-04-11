package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.FieldError;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
/*
    MessageCodesResolver : 검증 오류 코드로 메시지 코드들을 생성한다
    MessageCodesResolver 인터페이스이고 DefaultMessageCodesResolver는 기본 구현체!
    주로 ObjectError와 FieldError와 함께 사용
    자세한 순서 -> 덜 자세한 순서
*/

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        Assertions.assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        Assertions.assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.item",
                "required.java.lang.String",
                "required"
        );
    }

}
