package pl.ais.commons.application.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public final class ArrayUtils {

    private ArrayUtils() {
        throw new AssertionError("This class shouldn't be instantiated!");
    }

    public static <T> T[] arrayOf(final Class<T> componentType, final T first, final T... rest) {
        final T[] combined = (T[]) Array.newInstance(componentType, rest.length + 1);
        combined[0] = first;
        System.arraycopy(rest, 0, combined, 1, rest.length);
        return combined;
    }

    public static <T> Stream<T> streamOf(final Class<T> componentType, final T first, final T... rest) {
        return Arrays.stream(arrayOf(componentType, first, rest));
    }

}
