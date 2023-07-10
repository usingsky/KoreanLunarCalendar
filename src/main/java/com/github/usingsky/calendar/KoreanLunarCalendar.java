/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 usingsky(usingsky@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.usingsky.calendar;

public class KoreanLunarCalendar {

    private int lunarYear = 0;
    private int lunarMonth = 0;
    private int lunarDay = 0;
    private boolean isIntercalation = false;

    private int solarYear = 0;
    private int solarMonth = 0;
    private int solarDay = 0;

    private int[] gapjaYearInx = { 0, 0, 0 };
    private int[] gapjaMonthInx = { 0, 0, 1 };
    private int[] gapjaDayInx = { 0, 0, 2 };

    private static int KOREAN_LUNAR_MIN_VALUE = 10000101;
    private static int KOREAN_LUNAR_MAX_VALUE = 20501118;
    private static int KOREAN_SOLAR_MIN_VALUE = 10000213;
    private static int KOREAN_SOLAR_MAX_VALUE = 20501231;

    private static int KOREAN_LUNAR_BASE_YEAR = 1000;
    private static int SOLAR_LUNAR_DAY_DIFF = 43;

    private static int LUNAR_SMALL_MONTH_DAY = 29;
    private static int LUNAR_BIG_MONTH_DAY = 30;
    private static int SOLAR_SMALL_YEAR_DAY = 365;
    private static int SOLAR_BIG_YEAR_DAY = 366;

    private static int[] SOLAR_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 29 };
    private static char[] KOREAN_CHEONGAN = { 0xac11, 0xc744, 0xbcd1, 0xc815, 0xbb34, 0xae30, 0xacbd, 0xc2e0, 0xc784, 0xacc4 };
    private static char[] KOREAN_GANJI = { 0xc790, 0xcd95, 0xc778, 0xbb18, 0xc9c4, 0xc0ac, 0xc624, 0xbbf8, 0xc2e0, 0xc720, 0xc220, 0xd574 };
    private static char[] KOREAN_GAPJA_UNIT = { 0xb144, 0xc6d4, 0xc77c };

    private static char[] CHINESE_CHEONGAN = { 0x7532, 0x4e59, 0x4e19, 0x4e01, 0x620a, 0x5df1, 0x5e9a, 0x8f9b, 0x58ec, 0x7678 };
    private static char[] CHINESE_GANJI = { 0x5b50, 0x4e11, 0x5bc5, 0x536f, 0x8fb0, 0x5df3, 0x5348, 0x672a, 0x7533, 0x9149, 0x620c, 0x4ea5 };
    private static char[] CHINESE_GAPJA_UNIT = { 0x5e74, 0x6708, 0x65e5 };

    private static char[] INTERCALATION_STR = { 0xc724, 0x958f };

    // 1000 ~ 2050
    private static int[] KOREAN_LUNAR_DATA = {
            0x82c60a57, 0x82fec52b, 0x82c40d2a, 0x82c60d55, 0xc30095ad, 0x82c4056a, 0x82c6096d, 0x830054dd, 0xc2c404ad, 0x82c40a4d,
            0x83002e4d, 0x82c40b26, 0xc300ab56, 0x82c60ad5, 0x82c4035a, 0x8300697a, 0xc2c6095b, 0x82c4049b, 0x83004a9b, 0x82c40a4b,
            0xc301caa5, 0x82c406aa, 0x82c60ad5, 0x830092dd, 0xc2c402b5, 0x82c60957, 0x82fe54ae, 0x82c60c97, 0xc2c4064b, 0x82ff254a,
            0x82c60da9, 0x8300a6b6, 0xc2c6066d, 0x82c4026e, 0x8301692e, 0x82c4092e, 0xc2c40c96, 0x83004d95, 0x82c40d4a, 0x8300cd69,
            0xc2c40b58, 0x82c80d6b, 0x8301926b, 0x82c4025d, 0xc2c4092b, 0x83005aab, 0x82c40a95, 0x82c40b4a, 0xc3021eab, 0x82c402d5,
            0x8301b55a, 0x82c604bb, 0xc2c4025b, 0x83007537, 0x82c4052b, 0x82c40695, 0xc3003755, 0x82c406aa, 0x8303cab5, 0x82c40275,
            0xc2c404b6, 0x83008a5e, 0x82c40a56, 0x82c40d26, 0xc3005ea6, 0x82c60d55, 0x82c405aa, 0x83001d6a, 0xc2c6096d, 0x8300b4af,
            0x82c4049d, 0x82c40a4d, 0xc3007d2d, 0x82c40aa6, 0x82c60b55, 0x830045d5, 0xc2c4035a, 0x82c6095d, 0x83011173, 0x82c4045b,
            0xc3009a4f, 0x82c4064b, 0x82c40aa5, 0x83006b69, 0xc2c606b5, 0x82c402da, 0x83002ab6, 0x82c60937, 0xc2fec497, 0x82c60c97,
            0x82c4064b, 0x82fe86aa, 0xc2c60da5, 0x82c405b4, 0x83034a6d, 0x82c402ae, 0xc2c40e61, 0x83002d2e, 0x82c40c96, 0x83009d4d,
            0x82c40d4a, 0x82c60d65, 0x83016595, 0x82c6055d, 0xc2c4026d, 0x83002a5d, 0x82c4092b, 0x8300aa97, 0xc2c40a95, 0x82c40b4a,
            0x83008b5a, 0x82c60ad5, 0xc2c6055b, 0x830042b7, 0x82c40457, 0x82c4052b, 0xc3001d2b, 0x82c40695, 0x8300972d, 0x82c405aa,
            0xc2c60ab5, 0x830054ed, 0x82c404b6, 0x82c60a57, 0xc2ff344e, 0x82c40d26, 0x8301be92, 0x82c60d55, 0xc2c405aa, 0x830089ba,
            0x82c6096d, 0x82c404ae, 0xc3004a9d, 0x82c40a4d, 0x82c40d25, 0x83002f25, 0xc2c40b54, 0x8303ad69, 0x82c402da, 0x82c6095d,
            0xc301649b, 0x82c4049b, 0x82c40a4b, 0x83004b4b, 0xc2c406a5, 0x8300bb53, 0x82c406b4, 0x82c60ab6, 0xc3018956, 0x82c60997,
            0x82c40497, 0x83004697, 0xc2c4054b, 0x82fec6a5, 0x82c60da5, 0x82c405ac, 0xc303aab5, 0x82c4026e, 0x82c4092e, 0x83006cae,
            0xc2c40c96, 0x82c40d4a, 0x83002f4a, 0x82c60d55, 0xc300b56b, 0x82c6055b, 0x82c4025d, 0x8300793d, 0xc2c40927, 0x82c40a95,
            0x83015d15, 0x82c40b4a, 0xc2c60b55, 0x830112d5, 0x82c604db, 0x82fe925e, 0xc2c60a57, 0x82c4052b, 0x83006aab, 0x82c40695,
            0xc2c406aa, 0x83003baa, 0x82c60ab5, 0x8300b4b7, 0xc2c404ae, 0x82c60a57, 0x82fe752e, 0x82c40d26, 0xc2c60e93, 0x830056d5,
            0x82c405aa, 0x82c609b5, 0xc300256d, 0x82c404ae, 0x8301aa4d, 0x82c40a4d, 0xc2c40d26, 0x83006d65, 0x82c40b52, 0x82c60d6a,
            0xc30026da, 0x82c6095d, 0x8301c49d, 0x82c4049b, 0xc2c40a4b, 0x83008aab, 0x82c406a5, 0x82c40b54, 0xc3004bb4, 0x82c60ab6,
            0x82c6095b, 0x83002537, 0xc2c40497, 0x8300964f, 0x82c4054b, 0x82c406a5, 0xc30176c5, 0x82c405ac, 0x82c60ab6, 0x8301386e,
            0xc2c4092e, 0x8300cc97, 0x82c40c96, 0x82c40d4a, 0xc3008daa, 0x82c60b55, 0x82c4056a, 0x83025adb, 0xc2c4025d, 0x82c4092e,
            0x83002d2b, 0x82c40a95, 0xc3009d4d, 0x82c40b2a, 0x82c60b55, 0x83007575, 0xc2c404da, 0x82c60a5b, 0x83004557, 0x82c4052b,
            0xc301ca93, 0x82c40693, 0x82c406aa, 0x83008ada, 0xc2c60ae5, 0x82c404b6, 0x83004aae, 0x82c60a57, 0xc2c40527, 0x82ff2526,
            0x82c60e53, 0x8300a6cb, 0xc2c405aa, 0x82c605ad, 0x830164ad, 0x82c404ae, 0xc2c40a4e, 0x83004d4d, 0x82c40d26, 0x8300bd53,
            0xc2c40b52, 0x82c60b6a, 0x8301956a, 0x82c60557, 0xc2c4049d, 0x83015a1b, 0x82c40a4b, 0x82c40aa5, 0xc3001ea5, 0x82c40b52,
            0x8300bb5a, 0x82c60ab6, 0xc2c6095b, 0x830064b7, 0x82c40497, 0x82c4064b, 0xc300374b, 0x82c406a5, 0x8300b6b3, 0x82c405ac,
            0xc2c60ab6, 0x830182ad, 0x82c4049e, 0x82c40a4d, 0xc3005d4b, 0x82c40b25, 0x82c40b52, 0x83012e52, 0xc2c60b5a, 0x8300a95e,
            0x82c6095b, 0x82c4049b, 0xc3006a57, 0x82c40a4b, 0x82c40aa5, 0x83004ba5, 0xc2c406d4, 0x8300cad6, 0x82c60ab6, 0x82c60937,
            0x8300849f, 0x82c40497, 0x82c4064b, 0x82fe56ca, 0xc2c60da5, 0x82c405aa, 0x83001d6c, 0x82c60a6e, 0xc300b92f, 0x82c4092e,
            0x82c40c96, 0x83007d55, 0xc2c40d4a, 0x82c60d55, 0x83013555, 0x82c4056a, 0xc2c60a6d, 0x83001a5d, 0x82c4092b, 0x83008a5b,
            0xc2c40a95, 0x82c40b2a, 0x83015b2a, 0x82c60ad5, 0xc2c404da, 0x83001cba, 0x82c60a57, 0x8300952f, 0xc2c40527, 0x82c40693,
            0x830076b3, 0x82c406aa, 0xc2c60ab5, 0x83003575, 0x82c404b6, 0x8300ca67, 0xc2c40a2e, 0x82c40d16, 0x83008e96, 0x82c40d4a,
            0xc2c60daa, 0x830055ea, 0x82c6056d, 0x82c404ae, 0xc301285d, 0x82c40a2d, 0x8300ad17, 0x82c40aa5, 0xc2c40b52, 0x83007d74,
            0x82c60ada, 0x82c6055d, 0xc300353b, 0x82c4045b, 0x82c40a2b, 0x83011a2b, 0xc2c40aa5, 0x83009b55, 0x82c406b2, 0x82c60ad6,
            0xc3015536, 0x82c60937, 0x82c40457, 0x83003a57, 0xc2c4052b, 0x82feaaa6, 0x82c60d95, 0x82c405aa, 0xc3017aac, 0x82c60a6e,
            0x82c4052e, 0x83003cae, 0xc2c40a56, 0x8300bd2b, 0x82c40d2a, 0x82c60d55, 0xc30095ad, 0x82c4056a, 0x82c60a6d, 0x8300555d,
            0xc2c4052b, 0x82c40a8d, 0x83002e55, 0x82c40b2a, 0xc300ab56, 0x82c60ad5, 0x82c404da, 0x83006a7a, 0xc2c60a57, 0x82c4051b,
            0x83014a17, 0x82c40653, 0xc301c6a9, 0x82c405aa, 0x82c60ab5, 0x830092bd, 0xc2c402b6, 0x82c60a37, 0x82fe552e, 0x82c40d16,
            0x82c60e4b, 0x82fe3752, 0x82c60daa, 0x8301b5b4, 0xc2c6056d, 0x82c402ae, 0x83007a3d, 0x82c40a2d, 0xc2c40d15, 0x83004d95,
            0x82c40b52, 0x8300cb69, 0xc2c60ada, 0x82c6055d, 0x8301925b, 0x82c4045b, 0xc2c40a2b, 0x83005aab, 0x82c40a95, 0x82c40b52,
            0xc3001eaa, 0x82c60ab6, 0x8300c55b, 0x82c604b7, 0xc2c40457, 0x83007537, 0x82c4052b, 0x82c40695, 0xc3014695, 0x82c405aa,
            0x8300cab5, 0x82c60a6e, 0xc2c404ae, 0x83008a5e, 0x82c40a56, 0x82c40d2a, 0xc3006eaa, 0x82c60d55, 0x82c4056a, 0x8301295a,
            0xc2c6095d, 0x8300b4af, 0x82c4049b, 0x82c40a4d, 0xc3007d2d, 0x82c40b2a, 0x82c60b55, 0x830045d5, 0xc2c402da, 0x82c6095b,
            0x83011157, 0x82c4049b, 0xc3009a4f, 0x82c4064b, 0x82c406a9, 0x83006aea, 0xc2c606b5, 0x82c402b6, 0x83002aae, 0x82c60937,
            0xc2ffb496, 0x82c40c96, 0x82c60e4b, 0x82fe76b2, 0xc2c60daa, 0x82c605ad, 0x8300336d, 0x82c4026e, 0xc2c4092e, 0x83002d2d,
            0x82c40c95, 0x83009d4d, 0xc2c40b4a, 0x82c60b69, 0x8301655a, 0x82c6055b, 0xc2c4025d, 0x83002a5b, 0x82c4092b, 0x8300aa97,
            0xc2c40695, 0x82c4074a, 0x83008b5a, 0x82c60ab6, 0xc2c6053b, 0x830042b7, 0x82c40257, 0x82c4052b, 0xc3001d2b, 0x82c40695,
            0x830096ad, 0x82c405aa, 0xc2c60ab5, 0x830054ed, 0x82c404ae, 0x82c60a57, 0xc2ff344e, 0x82c40d2a, 0x8301bd94, 0x82c60b55,
            0x82c4056a, 0x8300797a, 0x82c6095d, 0x82c404ae, 0xc3004a9b, 0x82c40a4d, 0x82c40d25, 0x83011aaa, 0xc2c60b55, 0x8300956d,
            0x82c402da, 0x82c6095b, 0xc30054b7, 0x82c40497, 0x82c40a4b, 0x83004b4b, 0xc2c406a9, 0x8300cad5, 0x82c605b5, 0x82c402b6,
            0xc300895e, 0x82c6092f, 0x82c40497, 0x82fe4696, 0xc2c40d4a, 0x8300cea5, 0x82c60d69, 0x82c6056d, 0xc301a2b5, 0x82c4026e,
            0x82c4092e, 0x83006cad, 0xc2c40c95, 0x82c40d4a, 0x83002f4a, 0x82c60b59, 0xc300c56d, 0x82c6055b, 0x82c4025d, 0x8300793b,
            0xc2c4092b, 0x82c40a95, 0x83015b15, 0x82c406ca, 0xc2c60ad5, 0x830112b6, 0x82c604bb, 0x8300925f, 0xc2c40257, 0x82c4052b,
            0x82fe6aaa, 0x82c60e95, 0xc2c406aa, 0x83003baa, 0x82c60ab5, 0x8300b4b7, 0xc2c404ae, 0x82c60a57, 0x82fe752d, 0x82c40d26,
            0xc2c60d95, 0x830055d5, 0x82c4056a, 0x82c6096d, 0xc300255d, 0x82c404ae, 0x8300aa4f, 0x82c40a4d, 0xc2c40d25, 0x83006d69,
            0x82c60b55, 0x82c4035a, 0xc3002aba, 0x82c6095b, 0x8301c49b, 0x82c40497, 0xc2c40a4b, 0x83008b2b, 0x82c406a5, 0x82c406d4,
            0xc3034ab5, 0x82c402b6, 0x82c60937, 0x8300252f, 0xc2c40497, 0x82fe964e, 0x82c40d4a, 0x82c60ea5, 0xc30166a9, 0x82c6056d,
            0x82c402b6, 0x8301385e, 0xc2c4092e, 0x8300bc97, 0x82c40a95, 0x82c40d4a, 0xc3008daa, 0x82c60b4d, 0x82c6056b, 0x830042db,
            0xc2c4025d, 0x82c4092d, 0x83002d2b, 0x82c40a95, 0xc3009b4d, 0x82c406aa, 0x82c60ad5, 0x83006575, 0xc2c604bb, 0x82c4025b,
            0x83013457, 0x82c4052b, 0xc2ffba94, 0x82c60e95, 0x82c406aa, 0x83008ada, 0xc2c609b5, 0x82c404b6, 0x83004aae, 0x82c60a4f,
            0xc2c20526, 0x83012d26, 0x82c60d55, 0x8301a5a9, 0xc2c4056a, 0x82c6096d, 0x8301649d, 0x82c4049e, 0xc2c40a4d, 0x83004d4d,
            0x82c40d25, 0x8300bd53, 0xc2c40b54, 0x82c60b5a, 0x8301895a, 0x82c6095b, 0xc2c4049b, 0x83004a97, 0x82c40a4b, 0x82c40aa5,
            0xc3001ea5, 0x82c406d4, 0x8302badb, 0x82c402b6, 0xc2c60937, 0x830064af, 0x82c40497, 0x82c4064b, 0xc2fe374a, 0x82c60da5,
            0x8300b6b5, 0x82c6056d, 0xc2c402ae, 0x8300793e, 0x82c4092e, 0x82c40c96, 0xc3015d15, 0x82c40d4a, 0x82c60da5, 0x83013555,
            0xc2c4056a, 0x83007a7a, 0x82c60a5d, 0x82c4092d, 0xc3006aab, 0x82c40a95, 0x82c40b4a, 0x83004baa, 0xc2c60ad5, 0x82c4055a,
            0x830128ba, 0x82c60a5b, 0xc3007537, 0x82c4052b, 0x82c40693, 0x83015715, 0xc2c406aa, 0x82c60ad5, 0x830035b5, 0x82c404b6,
            0xc3008a5e, 0x82c40a4e, 0x82c40d26, 0x83006ea6, 0xc2c40d52, 0x82c60daa, 0x8301466a, 0x82c6056d, 0xc2c404ae, 0x83003a9d,
            0x82c40a4d, 0x83007d2b, 0xc2c40b25, 0x82c40d52, 0x83015d54, 0x82c60b5a, 0xc2c6055d, 0x8300355b, 0x82c4049b, 0x83007657,
            0x82c40a4b, 0x82c40aa5, 0x83006b65, 0x82c406d2, 0xc2c60ada, 0x830045b6, 0x82c60937, 0x82c40497, 0xc3003697, 0x82c4064d,
            0x82fe76aa, 0x82c60da5, 0xc2c405aa, 0x83005aec, 0x82c60aae, 0x82c4092e, 0xc3003d2e, 0x82c40c96, 0x83018d45, 0x82c40d4a,
            0xc2c60d55, 0x83016595, 0x82c4056a, 0x82c60a6d, 0xc300455d, 0x82c4052d, 0x82c40a95, 0x83013c95, 0xc2c40b4a, 0x83017b4a,
            0x82c60ad5, 0x82c4055a, 0xc3015a3a, 0x82c60a5b, 0x82c4052b, 0x83014a17, 0xc2c40693, 0x830096ab, 0x82c406aa, 0x82c60ab5,
            0xc30064f5, 0x82c404b6, 0x82c60a57, 0x82fe452e, 0xc2c40d16, 0x82c60e93, 0x82fe3752, 0x82c60daa, 0xc30175aa, 0x82c6056d,
            0x82c404ae, 0x83015a1d, 0xc2c40a2d, 0x82c40d15, 0x83004da5, 0x82c40b52, 0xc3009d6a, 0x82c60ada, 0x82c6055d, 0x8301629b,
            0xc2c4045b, 0x82c40a2b, 0x83005b2b, 0x82c40a95, 0xc2c40b52, 0x83012ab2, 0x82c60ad6, 0x83017556, 0xc2c60537, 0x82c40457,
            0x83005657, 0x82c4052b, 0xc2c40695, 0x83003795, 0x82c405aa, 0x8300aab6, 0xc2c60a6d, 0x82c404ae, 0x83006a6e, 0x82c40a56,
            0xc2c40d2a, 0x83005eaa, 0x82c60d55, 0x82c405aa, 0xc3003b6a, 0x82c60a6d, 0x830074bd, 0x82c404ab, 0xc2c40a8d, 0x83005d55,
            0x82c40b2a, 0x82c60b55, 0xc30045d5, 0x82c404da, 0x82c6095d, 0x83002557, 0xc2c4049b, 0x83006a97, 0x82c4064b, 0x82c406a9,
            0x83004baa, 0x82c606b5, 0x82c402ba, 0x83002ab6, 0xc2c60937, 0x82fe652e, 0x82c40d16, 0x82c60e4b, 0xc2fe56d2, 0x82c60da9,
            0x82c605b5, 0x8300336d, 0xc2c402ae, 0x82c40a2e, 0x83002e2d, 0x82c40c95, 0xc3006d55, 0x82c40b52, 0x82c60b69, 0x830045da,
            0xc2c6055d, 0x82c4025d, 0x83003a5b, 0x82c40a2b, 0xc3017a8b, 0x82c40a95, 0x82c40b4a, 0x83015b2a, 0xc2c60ad5, 0x82c6055b,
            0x830042b7, 0x82c40257, 0xc300952f, 0x82c4052b, 0x82c40695, 0x830066d5, 0xc2c405aa, 0x82c60ab5, 0x8300456d, 0x82c404ae,
            0xc2c60a57, 0x82ff3456, 0x82c40d2a, 0x83017e8a, 0xc2c60d55, 0x82c405aa, 0x83005ada, 0x82c6095d, 0xc2c404ae, 0x83004aab,
            0x82c40a4d, 0x83008d2b, 0xc2c40b29, 0x82c60b55, 0x83007575, 0x82c402da, 0xc2c6095d, 0x830054d7, 0x82c4049b, 0x82c40a4b,
            0xc3013a4b, 0x82c406a9, 0x83008ad9, 0x82c606b5, 0xc2c402b6, 0x83015936, 0x82c60937, 0x82c40497, 0xc2fe4696, 0x82c40e4a,
            0x8300aea6, 0x82c60da9, 0xc2c605ad, 0x830162ad, 0x82c402ae, 0x82c4092e, 0xc3005cad, 0x82c40c95, 0x82c40d4a, 0x83013d4a,
            0xc2c60b69, 0x8300757a, 0x82c6055b, 0x82c4025d, 0xc300595b, 0x82c4092b, 0x82c40a95, 0x83004d95, 0xc2c40b4a, 0x82c60b55,
            0x830026d5, 0x82c6055b, 0xc3006277, 0x82c40257, 0x82c4052b, 0x82fe5aaa, 0xc2c60e95, 0x82c406aa, 0x83003baa, 0x82c60ab5,
            0x830084bd, 0x82c404ae, 0x82c60a57, 0x82fe554d, 0xc2c40d26, 0x82c60d95, 0x83014655, 0x82c4056a, 0xc2c609ad, 0x8300255d,
            0x82c404ae, 0x83006a5b, 0xc2c40a4d, 0x82c40d25, 0x83005da9, 0x82c60b55, 0xc2c4056a, 0x83002ada, 0x82c6095d, 0x830074bb,
            0xc2c4049b, 0x82c40a4b, 0x83005b4b, 0x82c406a9, 0xc2c40ad4, 0x83024bb5, 0x82c402b6, 0x82c6095b, 0xc3002537, 0x82c40497,
            0x82fe6656, 0x82c40e4a, 0xc2c60ea5, 0x830156a9, 0x82c605b5, 0x82c402b6, 0xc30138ae, 0x82c4092e, 0x83017c8d, 0x82c40c95,
            0xc2c40d4a, 0x83016d8a, 0x82c60b69, 0x82c6056d, 0xc301425b, 0x82c4025d, 0x82c4092d, 0x83002d2b, 0xc2c40a95, 0x83007d55,
            0x82c40b4a, 0x82c60b55, 0xc3015555, 0x82c604db, 0x82c4025b, 0x83013857, 0xc2c4052b, 0x83008a9b, 0x82c40695, 0x82c406aa,
            0xc3006aea, 0x82c60ab5, 0x82c404b6, 0x83004aae, 0xc2c60a57, 0x82c40527, 0x82fe3726, 0x82c60d95, 0xc30076b5, 0x82c4056a,
            0x82c609ad, 0x830054dd, 0xc2c404ae, 0x82c40a4e, 0x83004d4d, 0x82c40d25, 0xc3008d59, 0x82c40b54, 0x82c60d6a, 0x8301695a,
            0xc2c6095b, 0x82c4049b, 0x83004a9b, 0x82c40a4b, 0xc300ab27, 0x82c406a5, 0x82c406d4, 0x83026b75, 0xc2c402b6, 0x82c6095b,
            0x830054b7, 0x82c40497, 0xc2c4064b, 0x82fe374a, 0x82c60ea5, 0x830086d9, 0xc2c605ad, 0x82c402b6, 0x8300596e, 0x82c4092e,
            0xc2c40c96, 0x83004e95, 0x82c40d4a, 0x82c60da5, 0xc3002755, 0x82c4056c, 0x83027abb, 0x82c4025d, 0xc2c4092d, 0x83005cab,
            0x82c40a95, 0x82c40b4a, 0xc3013b4a, 0x82c60b55, 0x8300955d, 0x82c404ba, 0xc2c60a5b, 0x83005557, 0x82c4052b, 0x82c40a95,
            0xc3004b95, 0x82c406aa, 0x82c60ad5, 0x830026b5, 0xc2c404b6, 0x83006a6e, 0x82c60a57, 0x82c40527, 0xc2fe56a6, 0x82c60d93,
            0x82c405aa, 0x83003b6a, 0xc2c6096d, 0x8300b4af, 0x82c404ae, 0x82c40a4d, 0xc3016d0d, 0x82c40d25, 0x82c40d52, 0x83005dd4,
            0xc2c60b6a, 0x82c6096d, 0x8300255b, 0x82c4049b, 0xc3007a57, 0x82c40a4b, 0x82c40b25, 0x83015b25, 0xc2c406d4, 0x82c60ada,
            0x830138b6,};

    private KoreanLunarCalendar() {
    }

    /**
    * Constructs a KoreanLunarCalendar instance.
    */
    public static KoreanLunarCalendar getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final KoreanLunarCalendar INSTANCE = new KoreanLunarCalendar();
    }

    private int getLunarData(int year) {
        return KOREAN_LUNAR_DATA[year - KOREAN_LUNAR_BASE_YEAR];
    }

    private int getLunarIntercalationMonth(int lunarData) {
        return (lunarData >> 12) & 0x000F;
    }

    private int getLunarDays(int year) {
        int lunarData = getLunarData(year);
        return (lunarData >> 17) & 0x01FF;
    }

    private int getLunarDays(int year, int month, boolean isIntercalation) {
        int days = 0;
        int lunarData = getLunarData(year);
        if (isIntercalation && getLunarIntercalationMonth(lunarData) == month) {
            days = ((lunarData >> 16) & 0x01) > 0 ? LUNAR_BIG_MONTH_DAY : LUNAR_SMALL_MONTH_DAY;
        } else {
            days = ((lunarData >> (12 - month)) & 0x01) > 0 ? LUNAR_BIG_MONTH_DAY : LUNAR_SMALL_MONTH_DAY;
        }
        return days;
    }

    private int getLunarDaysBeforeBaseYear(int year) {
        int days = 0;
        for (int baseYear = KOREAN_LUNAR_BASE_YEAR; baseYear < year + 1; baseYear++) {
            days += getLunarDays(baseYear);
        }
        return days;
    }

    private int getLunarDaysBeforeBaseMonth(int year, int month, boolean isIntercalation) {
        int days = 0;
        if (year >= KOREAN_LUNAR_BASE_YEAR && month > 0) {
            for (int baseMonth = 1; baseMonth < month + 1; baseMonth++) {
                days += getLunarDays(year, baseMonth, false);
            }

            if (isIntercalation) {
                int intercalationMonth = getLunarIntercalationMonth(getLunarData(year));
                if (intercalationMonth > 0 && intercalationMonth < month + 1) {
                    days += getLunarDays(year, intercalationMonth, true);
                }
            }
        }
        return days;
    }

    private int getLunarAbsDays(int year, int month, int day, boolean isIntercalation) {
        int days = 0;
        days = getLunarDaysBeforeBaseYear(year - 1) + getLunarDaysBeforeBaseMonth(year, month - 1, true) + day;

        if (isIntercalation && getLunarIntercalationMonth(getLunarData(year)) == month) {
            days += getLunarDays(year, month, false);
        }
        return days;
    }

    private boolean isSolarIntercalationYear(int lunarData) {
        return ((lunarData >> 30) & 0x01) > 0;
    }

    private int getSolarDays(int year) {
        int days = 0;
        int lunarData = getLunarData(year);
        days = isSolarIntercalationYear(lunarData) ? SOLAR_BIG_YEAR_DAY : SOLAR_SMALL_YEAR_DAY;
        /*
         * if(year == 1582){ days -= 10; }
         */
        return days;
    }

    private int getSolarDays(int year, int month) {
        int days = 0;
        int lunarData = getLunarData(year);
        if (month == 2 && isSolarIntercalationYear(lunarData)) {
            days = SOLAR_DAYS[12];
        } else {
            days = SOLAR_DAYS[month - 1];
        }
        return days;
    }

    private int getSolarDayBeforeBaseYear(int year) {
        int days = 0;
        for (int baseYear = KOREAN_LUNAR_BASE_YEAR; baseYear < year + 1; baseYear++) {
            days += getSolarDays(baseYear);
        }
        return days;
    }

    private int getSolarDaysBeforeBaseMonth(int year, int month) {
        int days = 0;
        for (int baseMonth = 1; baseMonth < month + 1; baseMonth++) {
            days += getSolarDays(year, baseMonth);
        }
        return days;
    }

    private int getSolarAbsDays(int year, int month, int day) {
        int days = 0;
        days = getSolarDayBeforeBaseYear(year - 1) + getSolarDaysBeforeBaseMonth(year, month - 1) + day;
        days -= SOLAR_LUNAR_DAY_DIFF;
        return days;
    }

    private void setSolarDateByLunarDate(int lunarYear, int lunarMonth, int lunarDay, boolean isIntercalation) {
        int absDays = getLunarAbsDays(lunarYear, lunarMonth, lunarDay, isIntercalation);
        int solarYear = 0;
        int solarMonth = 0;
        int solarDay = 0;

        solarYear = (absDays < getSolarAbsDays(lunarYear + 1, 1, 1)) ? lunarYear : lunarYear + 1;

        for (int month = 12; month > 0; month--) {
            int absDaysByMonth = getSolarAbsDays(solarYear, month, 1);
            if (absDays >= absDaysByMonth) {
                solarMonth = month;
                solarDay = absDays - absDaysByMonth + 1;
                break;
            }
        }

        setSolarYear(solarYear);
        setSolarMonth(solarMonth);
        setSolarDay(solarDay);
    }

    private void setLunarDateBySolarDate(int solarYear, int solarMonth, int solarDay) {
        int absDays = getSolarAbsDays(solarYear, solarMonth, solarDay);
        int lunarYear = 0;
        int lunarMonth = 0;
        int lunarDay = 0;
        boolean isIntercalation = false;

        lunarYear = (absDays >= getLunarAbsDays(solarYear, 1, 1, false)) ? solarYear : solarYear - 1;

        for (int month = 12; month > 0; month--) {
            int absDaysByMonth = getLunarAbsDays(lunarYear, month, 1, false);
            if (absDays >= absDaysByMonth) {
                lunarMonth = month;

                if (getLunarIntercalationMonth(getLunarData(lunarYear)) == month) {
                    isIntercalation = absDays >= getLunarAbsDays(lunarYear, month, 1, true);
                }

                lunarDay = absDays - getLunarAbsDays(lunarYear, lunarMonth, 1, isIntercalation) + 1;
                break;
            }
        }

        setLunarYear(lunarYear);
        setLunarMonth(lunarMonth);
        setLunarDay(lunarDay);
        setIntercalation(isIntercalation);
    }

    private boolean checkValidDate(boolean isLunar, boolean isIntercalation, int year, int month, int day) {
        boolean isValid = false;
        int dateValue = year * 10000 + month * 100 + day;
        // 1582. 10. 5 ~ 1582. 10. 14 is not enable
        if ((isLunar ? KOREAN_LUNAR_MIN_VALUE : KOREAN_SOLAR_MIN_VALUE) <= dateValue
                && (isLunar ? KOREAN_LUNAR_MAX_VALUE : KOREAN_SOLAR_MAX_VALUE) >= dateValue) {
            if (month > 0 && month < 13 && day > 0) {
                int dayLimit = isLunar ? getLunarDays(year, month, isIntercalation) : getSolarDays(year, month);
                if (!isLunar && year == 1582 && month == 10) {
                    if (day > 4 && day < 15) {
                        return false;
                    } else {
                        dayLimit += 10;
                    }
                }
                if (day <= dayLimit) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    /**
     * 
     * @param lunarYear
     * @param lunarMonth
     * @param lunarDay
     * @param isIntercalation
     * @return
     */
    public boolean setLunarDate(int lunarYear, int lunarMonth, int lunarDay, boolean isIntercalation) {
        boolean isValid = false;
        if (checkValidDate(true, isIntercalation, lunarYear, lunarMonth, lunarDay)) {
            setLunarYear(lunarYear);
            setLunarMonth(lunarMonth);
            setLunarDay(lunarDay);
            setIntercalation(isIntercalation && (getLunarIntercalationMonth(getLunarData(lunarYear)) == lunarMonth));
            setSolarDateByLunarDate(lunarYear, lunarMonth, lunarDay, isIntercalation);
            isValid = true;
        }
        return isValid;
    }

    /**
     * 
     * @param solarYear
     * @param solarMonth
     * @param solarDay
     * @return
     */
    public boolean setSolarDate(int solarYear, int solarMonth, int solarDay) {
        boolean isValid = false;
        if (checkValidDate(false, false, solarYear, solarMonth, solarDay)) {
            setSolarYear(solarYear);
            setSolarMonth(solarMonth);
            setSolarDay(solarDay);
            setLunarDateBySolarDate(solarYear, solarMonth, solarDay);
            isValid = true;
        }
        return isValid;
    }

    private void getGapJa() {
        int absDays = getLunarAbsDays(lunarYear, lunarMonth, lunarDay, isIntercalation);
        if (absDays > 0) {
            gapjaYearInx[0] = ((lunarYear + 6) - KOREAN_LUNAR_BASE_YEAR) % KOREAN_CHEONGAN.length;
            gapjaYearInx[1] = ((lunarYear + 0) - KOREAN_LUNAR_BASE_YEAR) % KOREAN_GANJI.length;

            int monthCount = this.lunarMonth;
            monthCount += 12 * (lunarYear - KOREAN_LUNAR_BASE_YEAR);
            gapjaMonthInx[0] = (monthCount + 3) % KOREAN_CHEONGAN.length;
            gapjaMonthInx[1] = (monthCount + 1) % KOREAN_GANJI.length;

            gapjaDayInx[0] = (absDays + 4) % KOREAN_CHEONGAN.length;
            gapjaDayInx[1] = (absDays + 2) % KOREAN_GANJI.length;
        }
    }

    /**
     * 
     * @return
     */
    public String getGapjaString() {
        getGapJa();

        StringBuilder gapjaString = new StringBuilder();
        gapjaString.append(KOREAN_CHEONGAN[gapjaYearInx[0]]);
        gapjaString.append(KOREAN_GANJI[gapjaYearInx[1]]);
        gapjaString.append(KOREAN_GAPJA_UNIT[gapjaYearInx[2]]);
        gapjaString.append(" ");
        gapjaString.append(KOREAN_CHEONGAN[gapjaMonthInx[0]]);
        gapjaString.append(KOREAN_GANJI[gapjaMonthInx[1]]);
        gapjaString.append(KOREAN_GAPJA_UNIT[gapjaMonthInx[2]]);
        gapjaString.append(" ");
        gapjaString.append(KOREAN_CHEONGAN[gapjaDayInx[0]]);
        gapjaString.append(KOREAN_GANJI[gapjaDayInx[1]]);
        gapjaString.append(KOREAN_GAPJA_UNIT[gapjaDayInx[2]]);

        if (isIntercalation()) {
            gapjaString.append(" (");
            gapjaString.append(INTERCALATION_STR[0]);
            gapjaString.append(KOREAN_GAPJA_UNIT[1]);
            gapjaString.append(")");
        }
        return gapjaString.toString();
    }

    /**
     * 
     * @return
     */
    public String getChineseGapJaString() {
        getGapJa();

        StringBuilder gapjaString = new StringBuilder();
        gapjaString.append(CHINESE_CHEONGAN[gapjaYearInx[0]]);
        gapjaString.append(CHINESE_GANJI[gapjaYearInx[1]]);
        gapjaString.append(CHINESE_GAPJA_UNIT[gapjaYearInx[2]]);
        gapjaString.append(" ");
        gapjaString.append(CHINESE_CHEONGAN[gapjaMonthInx[0]]);
        gapjaString.append(CHINESE_GANJI[gapjaMonthInx[1]]);
        gapjaString.append(CHINESE_GAPJA_UNIT[gapjaMonthInx[2]]);
        gapjaString.append(" ");
        gapjaString.append(CHINESE_CHEONGAN[gapjaDayInx[0]]);
        gapjaString.append(CHINESE_GANJI[gapjaDayInx[1]]);
        gapjaString.append(CHINESE_GAPJA_UNIT[gapjaDayInx[2]]);

        if (isIntercalation()) {
            gapjaString.append(" (");
            gapjaString.append(INTERCALATION_STR[1]);
            gapjaString.append(CHINESE_GAPJA_UNIT[1]);
            gapjaString.append(")");
        }
        return gapjaString.toString();
    }

    /**
     * 
     * @return
     */
    public String getLunarIsoFormat() {
        String isoStr = String.format("%04d-%02d-%02d", lunarYear, lunarMonth, lunarDay);
        if (isIntercalation())
            isoStr += " Intercalation";

        return isoStr;
    }

    /**
     * 
     * @return
     */
    public String getSolarIsoFormat() {
        String isoStr = String.format("%04d-%02d-%02d", solarYear, solarMonth, solarDay);

        return isoStr;
    }

    /**
     * 
     * @return
     */
    public int getLunarYear() {
        return lunarYear;
    }

    private void setLunarYear(int lunarYear) {
        this.lunarYear = lunarYear;
    }

    /**
     * 
     * @return
     */
    public int getLunarMonth() {
        return lunarMonth;
    }

    private void setLunarMonth(int lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    /**
     * 
     * @return
     */
    public int getLunarDay() {
        return lunarDay;
    }

    private void setLunarDay(int lunarDay) {
        this.lunarDay = lunarDay;
    }

    /**
     * 
     * @return
     */
    public boolean isIntercalation() {
        return isIntercalation;
    }

    private void setIntercalation(boolean isIntercalation) {
        this.isIntercalation = isIntercalation;
    }

    /**
     * 
     * @return
     */
    public int getSolarYear() {
        return solarYear;
    }

    private void setSolarYear(int solarYear) {
        this.solarYear = solarYear;
    }

    /**
     * 
     * @return
     */
    public int getSolarMonth() {
        return solarMonth;
    }

    private void setSolarMonth(int solarMonth) {
        this.solarMonth = solarMonth;
    }
    
    /**
     * 
     * @return
     */
    public int getSolarDay() {
        return solarDay;
    }

    private void setSolarDay(int solarDay) {
        this.solarDay = solarDay;
    }
}
