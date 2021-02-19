package org.syndrome.samples.sodemtash.data;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.syndrome.samples.sodemtash.data.entity.base.SoftDeletableEntity;
import org.syndrome.samples.sodemtash.data.entity.base.TenantAwareEntity;
import org.syndrome.samples.sodemtash.tenant.TenantSupplier;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@NoRepositoryBean
public class SodemtashInterceptorJPARepository<T> extends SimpleJpaRepository<T, Long> {

    private final JpaEntityInformation<T, Long> entityInformation;

    public SodemtashInterceptorJPARepository(JpaEntityInformation<T, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
    }

    @Override
    public @NonNull
    Optional<T> findById(@NonNull Long id) {
        return filterOptional(super.findById(id).orElse(null));
    }

    @Override
    public @NonNull
    T getOne(@NonNull Long id) {
        return throwIfNotExists(findById(id).orElse(null), id);
    }

    @Override
    public boolean existsById(@NonNull Long id) {
        return findById(id).isPresent();
    }

    @Override
    public @NonNull
    List<T> findAllById(@NonNull Iterable<Long> ids) {
        return super.findAllById(ids).stream()
                .filter(this::isValid)
                .collect(Collectors.toList());
    }

    @Override
    public @NonNull
    Optional<T> findOne(@Nullable Specification<T> spec) {
        return filterOptional(super.findOne(spec).orElse(null));
    }

    @Override
    public @NonNull
    <S extends T> Optional<S> findOne(@NonNull Example<S> example) {
        return filterOptional(super.findOne(example).orElse(null));
    }

    private T throwIfNotExists(T result, Long id) {
        if (result == null) {
            throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", this.entityInformation.getJavaType(), id), 1);
        }
        return result;
    }

    private <S extends T> Optional<S> filterOptional(S result) {
        if (result != null) {
            if (isValid(result)) {
                return Optional.of(result);
            }
        }

        return Optional.empty();
    }

    public boolean isValid(T item) {
        if (item instanceof SoftDeletableEntity) {
            SoftDeletableEntity entity = (SoftDeletableEntity) item;
            if (Boolean.TRUE.equals(entity.getDeleted())) {
                return false;
            }
        }

        if (item instanceof TenantAwareEntity) {
            TenantAwareEntity entity = (TenantAwareEntity) item;
            return TenantSupplier.get().equals(entity.getTenantId());
        }

        return true;
    }
}
