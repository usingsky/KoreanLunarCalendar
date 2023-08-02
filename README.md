# KoreanLunarCalendar (한국 양음력 변환)
> Library to convert Korean lunar-calendar to Gregorian calendar

#### Overview
Korean calendar and Chinese calendar is same lunar calendar but have different date.
This follow the KARI(Korea Astronomy and Space Science Institute)
한국 양음력 변환 (한국천문연구원 기준) - 네트워크 연결 불필요
```
음력 지원 범위 (1000년 01월 01일 ~ 2050년 11월 18일)
Korean Lunar Calendar (1000-01-01 ~ 2050-11-18)

양력 지원 범위 (1000년 02월 13일 ~ 2050년 12월 31일)
Gregorian Calendar (1000-02-13 ~ 2050-12-31)
```
[Example Site](https://usingsky.github.io/korean_lunar_calendar_js)

## Docs

- [Repo](#repo)
- [Import](#import)
- [Example](#example)
- [Validation](#validation)
- [Other languages](#other-languages)

## Repo

Maven

```xml
<repositories>
  ...
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

```xml
<dependency>
  <groupId>com.github.usingsky</groupId>
  <artifactId>KoreanLunarCalendar</artifactId>
  <version>0.3.1</version>
</dependency>
```

Gradle

```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

```groovy
dependencies {
    ...
    compile 'com.github.usingsky:KoreanLunarCalendar:0.3.1'
    ...
}
```
## Import

```java
import com.github.usingsky.calendar.KoreanLunarCalendar
```

## Example

Korean Solar Date -> Korean Lunar Date (양력 -> 음력)

```java
KoreanLunarCalendar calendar = KoreanLunarCalendar.getInstance();

// param : year(년), month(월), day(일)
calendar.setSolarDate(2017, 6, 24);

// Lunar Date (ISO format)
System.out.println(calendar.getLunarIsoFormat());
// Korean GapJa String
System.out.println(calendar.getGapjaString());
// Chinese GapJa String
System.out.println(calendar.getChineseGapJaString());
```

Result

```java
2017-05-01 Intercalation
정유년 병오월 임오일 (윤월)
丁酉年 丙午月 壬午日 (閏月)
```

Korean Lunar Date -> Korean Solar Date (음력 -> 양력)

```java
// param : year(년), month(월), day(일), intercalation(윤달여부)
calendar.setLunarDate(1956, 1, 21, false);

// Solar Date (ISO format)
System.out.println(calendar.getSolarIsoFormat());
// Korean GapJa String
System.out.println(calendar.getGapjaString());
// Chinese GapJa String
System.out.println(calendar.getChineseGapJaString());
```

Result

```java
1956-03-03
병신년 경인월 기사일
丙申年 庚寅月 己巳日
```

## Validation

Check for invalid date input

```java
KoreanLunarCalendar calendar = KoreanLunarCalendar.getInstance();

// Invalid date
calendar.setLunarDate(99, 1, 1, false); // => return false
calendar.setSolarDate(2051, 1, 1); // => return false

// OK
calendar.setLunarDate(1000, 1, 1, false); // => return true
calendar.setSolarDate(2050, 12, 31); // => return true
```

## Other languages

- Java : [https://github.com/usingsky/KoreanLunarCalendar](https://github.com/usingsky/KoreanLunarCalendar)
- Python : [https://github.com/usingsky/korean_lunar_calendar_py](https://github.com/usingsky/korean_lunar_calendar_py)
- Javascript : [https://github.com/usingsky/korean_lunar_calendar_js](https://github.com/usingsky/korean_lunar_calendar_js)
