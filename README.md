# KoreanLunarCalendar (한국 양음력 변환)
## Getting Started

#### Overview
Here is a library to convert Korean lunar-calendar to Gregorian calendar.

Korean calendar and Chinese calendar is same lunar calendar but have different date.

This follow the KARI(Korea Astronomy and Space Science Institute)

한국 양음력 변환 (한국천문연구원 기준) - 네트워크 연결 불필요

음력 변환은 1000년 1월 1일 부터 2050년 11월 18일까지 지원

````
Gregorian calendar (1391. 2. 5. ~ 2050. 12. 31) <--> Korean lunar-calendar (1000. 2. 13. ~ 2050. 11. 18)
````

#### To use
Only requires the single java file.

```java
import com.usingsky.calendar.KoreanLunarCalendar
```

#### Sample code
1. 양력 2017년 6월 24일을 음력으로 변환
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

[Result]
```java
2017-05-01 Intercalation
정유년 병오월 임오일 (윤월)
丁酉年 丙午月 壬午日 (閏月)
```

2. 음력 1956년 1월 21일을 양력으로 변환
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

[Result]
```java
1956-03-03
병신년 경인월 기사일
丙申年 庚寅月 己巳日
```
