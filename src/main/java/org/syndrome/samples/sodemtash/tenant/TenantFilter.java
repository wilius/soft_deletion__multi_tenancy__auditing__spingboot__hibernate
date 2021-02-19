package org.syndrome.samples.sodemtash.tenant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.syndrome.samples.sodemtash.tenant.annotations.TenantAwareContext;
import org.syndrome.samples.sodemtash.util.Utils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class TenantFilter extends OncePerRequestFilter {

    public static final String TENANT_HEADER = "TENANT-ID";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Object method = Utils.findProcessorMethod(httpServletRequest);
        Utils.extractAnnotationFromProcessorMethod(method, TenantAwareContext.class)
                .ifPresent(annotation -> TenantSupplier.set(getTenantId(httpServletRequest)));

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            TenantSupplier.clear();
        }
    }

    private UUID getTenantId(HttpServletRequest request) {
        String value = request.getHeader(TenantFilter.TENANT_HEADER);
        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException(String.format("%s header not specified ", TenantFilter.TENANT_HEADER));
        }

        return UUID.fromString(value);
    }

    @Override
    public void destroy() {
    }

}
