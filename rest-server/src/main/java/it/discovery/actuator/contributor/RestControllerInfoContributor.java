package it.discovery.actuator.contributor;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
/***
 * This {@link InfoContributor} returns information in {RestController}.{method}:{URI} format
 * //TODO support @PostMapping, @PutMapping, @DeleteMapping
 * support @RequestMapping
 */
public class RestControllerInfoContributor implements InfoContributor {

    private final ApplicationContext applicationContext;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> mappings = new HashMap<>();

        String[] names = applicationContext.getBeanNamesForAnnotation(RestController.class);
        for (String beanName : names) {
            Object bean = applicationContext.getBean(beanName);
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
            for (Method method : methods) {
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                if (getMapping != null) {
                    mappings.put(beanName + "." + method.getName(), getMapping.value());
                }
            }
        }

        builder.withDetail("rest-services", mappings);
    }
}
