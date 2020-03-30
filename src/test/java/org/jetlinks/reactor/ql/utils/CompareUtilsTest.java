package org.jetlinks.reactor.ql.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CompareUtilsTest {

    @Test
    void testNull() {
        assertTrue(doCompare(null, null));
        assertFalse(doCompare(null, 1));
        assertFalse(doCompare(null, "1"));
        assertFalse(doCompare(null, new Date()));
        assertFalse(doCompare(null, TestEnum.enabled));

    }

    boolean doCompare(Object source, Object target) {
        return CompareUtils.compare(source, target) &&
                CompareUtils.compare(target, source);
    }

    @Test
    void testCompareNumber() {
        assertTrue(doCompare(1, 1D));
        assertTrue(doCompare(1, 1F));
        assertTrue(doCompare(1, 1L));
        assertTrue(doCompare(1, (byte) 1));
        assertTrue(doCompare(1, (char) 1));

        assertTrue(doCompare(1, new BigDecimal("1")));
        assertTrue(doCompare(49, '1'));
        assertTrue(doCompare(1, "1E0"));
    }

    @Test
    void testCompareDate() {
        long now = System.currentTimeMillis();
        assertTrue(doCompare(new Date(now), now));
        assertTrue(doCompare(new Date(now).toInstant(), now));
        assertTrue(doCompare(LocalDateTime.ofInstant(Instant.ofEpochMilli(now), ZoneId.systemDefault()), now));

        assertTrue(doCompare(LocalDate.now(), LocalDate.now()));

    }

    @Test
    void testCompareEnum() {
        assertTrue(doCompare(TestEnum.enabled, 0));
        assertTrue(doCompare(TestEnum.enabled, "enabled"));
        assertFalse(doCompare(TestEnum.enabled, "0"));

    }


    enum TestEnum {
        enabled, disabled;
    }
}