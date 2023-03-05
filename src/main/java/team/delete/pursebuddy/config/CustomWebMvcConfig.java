package team.delete.pursebuddy.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.delete.pursebuddy.interceptor.LogInterceptor;

import java.util.List;
import java.util.TimeZone;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Configuration
@AllArgsConstructor
public class CustomWebMvcConfig implements WebMvcConfigurer {
    private final LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        for (int i = 0; i < converters.size(); i++) {
//            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
//                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//                converter.setObjectMapper(objectMapper);
//                converters.set(i, converter);
//                break;
//            }
//        }
//    }
}