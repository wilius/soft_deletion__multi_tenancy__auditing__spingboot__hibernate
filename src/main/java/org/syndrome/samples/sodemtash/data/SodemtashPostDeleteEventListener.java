package org.syndrome.samples.sodemtash.data;

import org.hibernate.FlushMode;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.stereotype.Component;
import org.syndrome.samples.sodemtash.data.entity.base.TenantBasedSoftDeletedAuditedEntity;
import org.syndrome.samples.sodemtash.tenant.TenantSupplier;

import javax.persistence.EntityManagerFactory;

@Component
public class SodemtashPostDeleteEventListener implements PostDeleteEventListener {
    public SodemtashPostDeleteEventListener(EntityManagerFactory entityManagerFactory) {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_DELETE).prependListener(this);
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Object entity = event.getEntity();
        EntityPersister persister = event.getPersister();

        reassignStatesForHibernateBug(event, entity, persister);

        if (entity instanceof TenantBasedSoftDeletedAuditedEntity) {
            if (!(persister instanceof SingleTableEntityPersister)) {
                throw new RuntimeException("EntityPersister is not instance of SingleTableEntityPersister");
            }

            executeModifiedByColumns((TenantBasedSoftDeletedAuditedEntity) entity, event, (SingleTableEntityPersister) persister);
        }
    }

    // soft deletion with SQLDelete does not allow us to update fields parametrically. So we had to update
    // the modifiedBy and modifiedDateTime columns of the base object to prevent the misunderstanding and
    // misusage of the data.
    private void executeModifiedByColumns(TenantBasedSoftDeletedAuditedEntity entity,
                                          PostDeleteEvent event,
                                          SingleTableEntityPersister persister) {

        //noinspection SqlResolve
        String builder = "update " +
                persister.getTableName() +
                " set " +
                " deleted = :deleted " +
                " where id = :id and tenant_id = :tenantId";

        int count = event.getSession().createNativeQuery(builder)
                .setParameter("id", entity.getId())
                .setParameter("tenantId", TenantSupplier.get())
                .setParameter("deleted", true)
                .setFlushMode(FlushMode.COMMIT)
                .executeUpdate();

        if (count != 1) {
            throw new RuntimeException(String.format("No record found to delete with id %s for entity %s", entity.getId(), entity.getClass().getName()));
        }
    }

    // code below is coded because of a bug in org.hibernate.action.internal.EntityDeleteAction.
    // it assigns the state array during the initialization of the object and it remains as is without update
    // and the rest of the code blocks that uses this objects have to use the old values of the entity.
    // we added a new listener before of the all listeners to fix the state object with their updated values.
    // we found this bug during the integration of envers auditing and soft deletion
    private void reassignStatesForHibernateBug(PostDeleteEvent event, Object entity, EntityPersister persister) {
        Object[] deletedStates = event.getDeletedState();
        for (int i = 0; i < deletedStates.length; i++) {
            deletedStates[i] = persister.getPropertyValue(entity, i);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}
