package com.github.usingsky.calendar;

import com.github.usingsky.calendar.KoreanLunarCalendar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KoreanLunarCalendarTest {

    @Test
    public void testSolarToLunar() {
        KoreanLunarCalendar calendar = KoreanLunarCalendar.getInstance();
        calendar.setSolarDate(2017, 6, 24);

        assertEquals(calendar.getLunarIsoFormat(), "2017-05-01 Intercalation");
        assertEquals(calendar.getGapjaString(), "\uC815\uC720\uB144 \uBCD1\uC624\uC6D4 \uC784\uC624\uC77C (\uC724\uC6D4)");
        assertEquals(calendar.getChineseGapJaString(), "\u4E01\u9149\u5E74 \u4E19\u5348\u6708 \u58EC\u5348\u65E5 (\u958F\u6708)");
    }

    @Test
    public void testLunarToSolar() {
        
    }


}