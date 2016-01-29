# KoreanLunarCalendar (한국 양음력 변환)
## Getting Started

#### Overview
Here is a library to convert Korean lunar-calendar to Gregorian calendar.

Korean calendar and Chinese calendar is same lunar calendar but have different date.

This follow the KARI(Korea Astronomy and Space Science Institute)

한국 양음력 변환 (한국천문연구원 기준) - 네트워크 연결 불필요
````
Gregorian calendar (1391. 2. 5. ~ 2050. 12. 31) <--> Korean lunar-calendar (1391. 1. 1. ~ 2050. 11. 18)
````

#### To use
Only requires the single java file.

```java
import com.usingsky.calendar.KoreanLunarCalendar
```

#### Sample code
1. 양력 1391년 2월 23일을 음력으로 변환
2. 음력 1956년 1월 21일을 양력으로 변환
```java
KoreanLunarCalendar klcal = KoreanLunarCalendar.getInstance();

System.out.println("1. solar date to Korean lunar date");
klcal.setSolarDate(1393, 2, 23);
System.out.println("Solar Date : " +klcal.getSolarYear()+". "+klcal.getSolarMonth()+". "+klcal.getSolarDay());
System.out.println("Korean Lunar Date : " +klcal.getLunarYear()+". "+klcal.getLunarMonth()+". "+klcal.getLunarDay()+" "
                   +klcal.getGapjaString()+" ("+(klcal.isIntercalation() ? "윤달)" : "평달)"));
System.out.println("\n2. Korean lunar date to solar date");
klcal.setLunarDate(1956, 1, 21, false);
System.out.println("Korean Lunar Date : " +klcal.getLunarYear()+". "+klcal.getLunarMonth()+". "+klcal.getLunarDay()+" "
      +klcal.getGapjaString()+" ("+(klcal.isIntercalation() ? "윤달)" : "평달)"));
System.out.println("Solar Date : " +klcal.getSolarYear()+". "+klcal.getSolarMonth()+". "+klcal.getSolarDay());

```

#### Result
```
1. solar date to Korean lunar date
Solar Date : 1393. 2. 23
Korean Lunar Date : 1393. 1. 12 계유년 갑인월 무오일 (평달)

2. Korean lunar date to solar date
Korean Lunar Date : 1956. 1. 21 병신년 경인월 기사일 (평달)
Solar Date : 1956. 3. 3

```
