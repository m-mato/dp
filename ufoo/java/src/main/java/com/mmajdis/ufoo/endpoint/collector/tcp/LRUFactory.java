package com.mmajdis.ufoo.endpoint.collector.tcp;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Matej Majdis
 */
class LRUFactory {

    public static <K, V> Map<K, V> createLRUMap(final int maxEntries) {

        return new LinkedHashMap<K, V>(maxEntries * 10 / 7, 0.7f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxEntries;
            }
        };
    }

    public static <V> Set<V> createLRUSet(final int maxEntries) {

        return Collections.newSetFromMap(new LinkedHashMap<V, Boolean>() {
            protected boolean removeEldestEntry(Map.Entry<V, Boolean> eldest) {
                return size() > maxEntries;
            }
        });
    }
}
