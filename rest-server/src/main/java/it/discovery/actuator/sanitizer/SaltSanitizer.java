package it.discovery.actuator.sanitizer;

import org.springframework.boot.actuate.endpoint.SanitizableData;
import org.springframework.boot.actuate.endpoint.SanitizingFunction;
import org.springframework.stereotype.Component;

@Component
public class SaltSanitizer implements SanitizingFunction {
    @Override
    public SanitizableData apply(SanitizableData data) {
        if (data.getKey().contains(".salt")) {
            return data.withSanitizedValue();
        }

        return data;
    }
}
