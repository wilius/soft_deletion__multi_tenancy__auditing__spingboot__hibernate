package org.syndrome.samples.sodemtash.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.syndrome.samples.sodemtash.data.entity.base.TenantBasedSoftDeletedAuditedEntity;
import org.syndrome.samples.sodemtash.tenant.TenantSupplier;
import org.syndrome.samples.sodemtash.aspect.annotation.SoftDeletes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
public class RepositoryFilter extends BaseAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Before("within(org.springframework.data.jpa.repository.JpaRepository+) && execution(* *(..))")
    public void forceFilter(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        SoftDeletes annotation = method.getDeclaredAnnotation(SoftDeletes.class);
        if (annotation == null) {
            annotation = method.getDeclaringClass().getDeclaredAnnotation(SoftDeletes.class);
        }

        boolean deleted = false;
        if (annotation != null) {
            deleted = annotation.deleted();
        }

        // joinPoint.getTarget().getClass().getInterfaces()[0].getGenericInfo().getSuperInterfaces()[0].getTypeName()

        // in the no session case, we should initialize one
        Session hibernateSession = entityManager.unwrap(Session.class);
        UUID tenantId = TenantSupplier.get();
        hibernateSession.enableFilter(TenantBasedSoftDeletedAuditedEntity.DEFAULT_FILTER)
                .setParameter("tenantId", tenantId)
                .setParameter("deleted", deleted);
    }
}
