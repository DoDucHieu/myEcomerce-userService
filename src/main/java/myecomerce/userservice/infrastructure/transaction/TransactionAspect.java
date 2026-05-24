package myecomerce.userservice.infrastructure.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionAspect {
    private final TransactionTemplate tx;
    @Around("@within(myecomerce.userservice.application.annotationCustom.CommandUseCase)")
    public Object transactional(ProceedingJoinPoint joinPoint) {
        return tx.execute(
                status -> {

                    try {

                        return joinPoint.proceed();

                    } catch (
                            Throwable e
                    ) {

                        throw new RuntimeException(
                                e
                        );

                    }

                }
        );

    }
}
