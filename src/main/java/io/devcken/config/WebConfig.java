package io.devcken.config;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
  @Override
  protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    converters.add(mappingJackson2HttpMessageConverter(customObjectMapper()));

    super.configureMessageConverters(converters);
  }

  @Bean
  MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper customObjectMapper) {
    MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter();

    mc.setObjectMapper(customObjectMapper);

    return mc;
  }

  @Bean
  ObjectMapper customObjectMapper() {
    ObjectMapper om = new ObjectMapper();

    om.registerModule(new JtsModule());

    return om;
  }
}
