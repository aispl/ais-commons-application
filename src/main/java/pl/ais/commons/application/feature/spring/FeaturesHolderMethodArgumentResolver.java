package pl.ais.commons.application.feature.spring;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import pl.ais.commons.application.feature.FeaturesHolder;
import pl.ais.commons.application.feature.FeaturesHolderFactory;

/**
 * Provides possibility of resolving handler method arguments of type {@link FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public final class FeaturesHolderMethodArgumentResolver implements ApplicationContextAware,
    HandlerMethodArgumentResolver {

    private transient ApplicationContext context;

    private transient FeaturesHolderFactory factory;

    private transient Map<GrantedAuthority, Set<Class<?>>> featuresMap;

    private void addFeaturesForAuthority(final GrantedAuthority authority, final FeaturesHolderBuilder builder) {
        final Collection<Class<?>> features = featuresMap.get(authority);
        if (null != features) {
            for (final Class<?> feature : features) {
                builder.addFeature(feature);
            }
        }
    }

    /**
     * @return the factory
     */
    private FeaturesHolderFactory getFactory() {
        return null == factory ? FeaturesHolderFactory.getInstance() : factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object resolveArgument(
        final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory) throws Exception {
        final Principal principal = webRequest.getUserPrincipal();
        FeaturesHolder result = null;
        if (principal instanceof Authentication) {
            final FeaturesHolderBuilder builder = new FeaturesHolderBuilder(getFactory(), context);
            for (final GrantedAuthority authority : ((Authentication) principal).getAuthorities()) {
                addFeaturesForAuthority(authority, builder);
            }
            result = builder.build();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(final ApplicationContext context) throws BeansException {
        this.context = context;
    }

    /**
     * @param factory the factory to set
     */
    public void setFeaturesHolderFactory(final FeaturesHolderFactory factory) {
        this.factory = factory;
    }

    /**
     * @param featuresMap mapping between granted authority and set of features
     */
    public void setFeaturesMap(final Map<GrantedAuthority, Set<Class<?>>> featuresMap) {
        this.featuresMap = featuresMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return FeaturesHolder.class.isAssignableFrom(parameter.getParameterType());
    }

}
