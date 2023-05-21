package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FormattingConversionServiceTest {

  @Test
  void formattingConversionService() {
    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
    // 컨버터 등록
    conversionService.addConverter(new StringToIpPortConverter());
    conversionService.addConverter(new IpPortToStringConverter());
    // 포맷터 등록
    conversionService.addFormatter(new MyNumberFormatter());

    /**
     * FormattingConversionService는 ConversionService 관련 기능을 상속 받음!
     * -> 컨버터도 포맷터도 모두 등록 가능가능
     * 사용할 땐 ConversionService가 제공하는 convert 사용하면 됨!
     */

    // 컨버터 사용
    IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
    Assertions.assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

    // 포맷터 사용
    Assertions.assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
    Assertions.assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);
  }
}