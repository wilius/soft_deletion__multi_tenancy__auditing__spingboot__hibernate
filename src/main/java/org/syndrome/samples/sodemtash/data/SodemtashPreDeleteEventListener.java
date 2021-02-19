package org.syndrome.samples.sodemtash.data;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;
import org.syndrome.samples.sodemtash.data.entity.base.TenantBasedSoftDeletedAuditedEntity;

import javax.persistence.EntityManagerFactory;

@Component
public class SodemtashPreDeleteEventListener implements PreDeleteEventListener {
    public SodemtashPreDeleteEventListener(EntityManagerFactory entityManagerFactory) {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_DELETE).prependListener(this);
    }

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        return event.getEntity() instanceof TenantBasedSoftDeletedAuditedEntity;
    }
}
