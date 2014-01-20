package pl.ais.commons.application.feature.spring;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Arrays;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.ais.commons.application.feature.DefaultFeaturesHolder;
import pl.ais.commons.application.feature.FeaturesHolder;
import pl.ais.commons.application.feature.FeaturesHolderFactory;
import pl.ais.commons.application.feature.internal.DefaultFeatureA;
import pl.ais.commons.application.feature.internal.FeatureA;
import pl.ais.commons.application.feature.internal.FeatureB;
import pl.ais.commons.application.feature.internal.FeatureC;
import pl.ais.commons.application.feature.internal.OperationalFeaturesHolder;
import pl.ais.commons.application.feature.spring.internal.ExampleController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Verifies {@link FeaturesHolderMethodArgumentResolver} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class FeaturesHolderMethodArgumentResolverExpectations {

    /**
     * @return created principal having given set of granted authorities
     */
    private static Principal createPrincipal(final GrantedAuthority... authorities) {
        return new UsernamePasswordAuthenticationToken("principal", "credentials", Arrays.asList(authorities));
    }

    /**
     * @param resultActions result actions
     * @return FeaturesHolder used while request handling
     */
    private static FeaturesHolder extractFeaturesHolder(final ResultActions resultActions) {
        return (FeaturesHolder) resultActions.andReturn().getModelAndView().getModelMap()
            .get(ExampleController.FEATURES_HOLDER);
    }

    /**
     * @param context application context to initialize
     */
    private static void initializeApplicationContext(final StaticApplicationContext context) {
        context.registerSingleton("featureA", DefaultFeatureA.class);
    }

    private final GrantedAuthority adminUser = new SimpleGrantedAuthority("AdminUser");

    private final GrantedAuthority ordinaryUser = new SimpleGrantedAuthority("OrdinaryUser");

    /**
     * @return mapping between granted authority and corresponding features
     */
    private ImmutableMap<GrantedAuthority, Set<Class<?>>> featuresMap() {
        return ImmutableMap.<GrantedAuthority, Set<Class<?>>> builder()
            .put(adminUser, ImmutableSet.<Class<?>> of(FeatureA.class, FeatureB.class))
            .put(ordinaryUser, ImmutableSet.<Class<?>> of(FeatureA.class, FeatureC.class)).build();
    }

    /**
     * @param context application context
     * @return created and initialized instance of {@link FeaturesHolderMethodArgumentResolver}
     */
    private FeaturesHolderMethodArgumentResolver methodArgumentResolver(final ApplicationContext context) {
        final FeaturesHolderMethodArgumentResolver argumentResolver = new FeaturesHolderMethodArgumentResolver();
        argumentResolver.setApplicationContext(context);
        argumentResolver.setFeaturesMap(featuresMap());
        return argumentResolver;
    }

    /**
     * @param factory factory to be used by argument resolver
     * @param context application context
     * @return created and initialized instance of {@link FeaturesHolderMethodArgumentResolver}
     */
    private FeaturesHolderMethodArgumentResolver methodArgumentResolver(
        final FeaturesHolderFactory factory, final ApplicationContext context) {
        final FeaturesHolderMethodArgumentResolver argumentResolver = methodArgumentResolver(context);
        argumentResolver.setFeaturesHolderFactory(factory);
        return argumentResolver;
    }

    /**
     * Verifies if properly configured application (based on Spring Framework), will allow resolving handler parameters
     * of custom {@link FeaturesHolder} type.
     *
     * @throws Exception in case of any problems
     */
    @Test
    public void shouldAllowResolvingParametersOfCustomFeaturesHolderType() throws Exception {
        try (final StaticApplicationContext context = new StaticApplicationContext()) {

            // Given custom FeaturesHolder type factory, ...
            final FeaturesHolderFactory factory = new FeaturesHolderFactory(OperationalFeaturesHolder.class);

            // ... and application with support for resolving parameters of this custom FeaturesHolder type, ...
            initializeApplicationContext(context);

            final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController())
                .setCustomArgumentResolvers(methodArgumentResolver(factory, context)).build();

            // ... and authorized user, having ordinary user authority.
            final Principal principal = createPrincipal(ordinaryUser);

            // When we perform the request, handled by controller's method declaring FeaturesHolder as one
            // of the parameters.
            final ResultActions resultActions = mockMvc.perform(get("/example.html").principal(principal));

            // Then the request should succeed, ...
            resultActions.andExpect(status().isOk());

            // ... with FeaturesHolder created for us, ...
            final FeaturesHolder featuresHolder = extractFeaturesHolder(resultActions);

            // ... having appropriate type, ...
            assertThat("Features Holder should be instance of custom type.", featuresHolder,
                CoreMatchers.instanceOf(OperationalFeaturesHolder.class));

            // ... and the features set determined by authorities granted
            // to authorized user.
            for (final Class<?> feature : featuresMap().get(ordinaryUser)) {
                assertTrue("Features Holder should have feature.", featuresHolder.hasFeature(feature));
            }
        }

    }

    /**
     * Verifies if properly configured application (based on Spring Framework), will allow resolving handler parameters
     * of default {@link FeaturesHolder} type ({@link DefaultFeaturesHolder}).
     *
     * @throws Exception in case of any problems
     */
    @Test
    public void shouldAllowResolvingParametersOfDefaultFeaturesHolderType() throws Exception {
        try (final StaticApplicationContext context = new StaticApplicationContext()) {

            // Given application with support for resolving parameters of default FeaturesHolder type, ...
            initializeApplicationContext(context);

            final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController())
                .setCustomArgumentResolvers(methodArgumentResolver(context)).build();

            // ... and authorized user, having ordinary user authority.
            final Principal principal = createPrincipal(ordinaryUser);

            // When we perform the request, handled by controller's method declaring FeaturesHolder as one
            // of the parameters.
            final ResultActions resultActions = mockMvc.perform(get("/example.html").principal(principal));

            // Then the request should succeed, ...
            resultActions.andExpect(status().isOk());

            // ... with FeaturesHolder created for us, ...
            final FeaturesHolder featuresHolder = extractFeaturesHolder(resultActions);

            // ... having appropriate type, ...
            assertThat("Features Holder should have default type.", featuresHolder,
                CoreMatchers.instanceOf(DefaultFeaturesHolder.class));

            // ... and the features set determined by authorities granted to authorized user.
            for (final Class<?> feature : featuresMap().get(ordinaryUser)) {
                assertTrue("Features Holder should have feature.", featuresHolder.hasFeature(feature));
            }
        }
    }
}