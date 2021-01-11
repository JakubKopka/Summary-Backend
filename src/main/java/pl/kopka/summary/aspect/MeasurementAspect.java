package pl.kopka.summary.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kopka.summary.constant.EmailConst;
import pl.kopka.summary.service.EmailService;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class MeasurementAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final EmailService emailService;

    @Autowired
    public MeasurementAspect(EmailService emailService) {
        this.emailService = emailService;
    }

    @Around("@annotation(MeasureTimeAspect)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant startTime = Instant.now();
        Object proceed = joinPoint.proceed();
        Instant endTime = Instant.now();
        Long executionTime = Duration.between(startTime, endTime).getSeconds();
        String message = joinPoint.getSignature() + " executed in " + executionTime + " seconds!";
        LOGGER.info(message);
        if (executionTime >= 5L) {
            emailService.sendEmail(EmailConst.ADMIN_MAIL, "Measure Execution Time", message);
            LOGGER.info("Message was sent to admin");
        }
        return proceed;
    }
}
