package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {

  @Test
  void conversionService() {
    // 등록
    // 등록할 때는 타입 컨버터를 명확하게 알아야 함!
    DefaultConversionService conversionService = new DefaultConversionService();
    conversionService.addConverter(new StringToIntegerConverter());
    conversionService.addConverter(new IntegerToStringConverter());
    conversionService.addConverter(new StringToIpPortConverter());
    conversionService.addConverter(new IpPortToStringConverter());

    // 사용
    // 사용하는 입장에서는 타입 컨버터를 전혀 몰라도 됨! (타입 컨버터들은 컨버전 서비스 내부에 숨어서 제공됨)
    Assertions.assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
    Assertions.assertThat(conversionService.convert(10, String.class)).isEqualTo("10");

    IpPort result = conversionService.convert("127.0.0.1:8080", IpPort.class);
    Assertions.assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));

    String ipPortString = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
    Assertions.assertThat(ipPortString).isEqualTo("127.0.0.1:8080");
  }
}
