# Java Date-Time API - Modern Date and Time Handling

## Overview

The Java Date-Time API, introduced in Java 8 and standardized in `java.time` package, provides a comprehensive and flexible framework for working with dates, times, time zones, and intervals. It replaces the legacy `java.util.Date` and `java.util.Calendar` classes with an immutable, thread-safe, and easier-to-use API.

## Key Advantages Over Legacy APIs

1. **Immutable**: All date-time classes are immutable, ensuring thread safety
2. **Fluent API**: Method chaining allows elegant composition
3. **Timezone Support**: First-class timezone handling with `ZonedDateTime`
4. **Clear Intent**: Explicit separation of date, time, and combined representations
5. **Easy Formatting**: Powerful `DateTimeFormatter` for parsing and formatting
6. **Chronology**: Support for different calendar systems beyond Gregorian

## Core Classes and Concepts

### LocalDate - Date Without Time
Represents a date without time information (year, month, day).

### LocalTime - Time Without Date
Represents a time without date information (hour, minute, second, nanosecond).

### LocalDateTime - Date and Time
Combines LocalDate and LocalTime. Represents date and time without timezone information.

### ZonedDateTime - Date-Time with Timezone
LocalDateTime with explicit timezone information. Handles daylight saving time transitions automatically.

### Instant - Machine Time
Represents a specific point in time on the universal timeline, independent of timezone. Measured in seconds and nanoseconds since epoch (January 1, 1970, 00:00:00 UTC).

### Period - Date-Based Duration
Represents a span of time in terms of years, months, and days. Used for human-based time periods.

### Duration - Time-Based Duration
Represents a span of time in seconds and nanoseconds. Used for machine-based intervals.

### ZoneId - Timezone Information
Represents a timezone region. Can be a ZoneOffset (fixed offset from UTC) or a ZoneId (region like "America/New_York").

### DateTimeFormatter - Formatting and Parsing
Used to convert between date-time objects and strings. Provides predefined formats and supports custom patterns.

## Usage Patterns

### 1. **Use LocalDate for Date-Only Operations**
When working with dates without time concerns (birthdays, contract dates), use LocalDate. It's simpler and avoids timezone confusion.

### 2. **Use LocalDateTime for Local Time-Based Operations**
For operations in a single timezone or when timezone isn't relevant, use LocalDateTime. Common in server-side processing within a single region.

### 3. **Use ZonedDateTime for Multi-Timezone Operations**
When dealing with multiple timezones, scheduled events across regions, or calendar integration, use ZonedDateTime.

### 4. **Use Instant for Timestamps**
Store timestamps in Instant format for database records or log entries. Instant is timezone-independent and perfect for machine timestamps.

### 5. **Use Period for Human Time Spans**
When calculating age, contract durations, or business time periods, use Period.

### 6. **Use Duration for System Time Measurements**
When measuring elapsed time, operation durations, or timeout intervals, use Duration.

### 7. **Avoid Modifying Dates**
Since date-time objects are immutable, operations like `plusDays()` return new instances. Always capture the result.

## Performance Considerations

1. **Timezone Conversion Overhead**: ZonedDateTime is more expensive than LocalDateTime due to timezone calculations. Use LocalDateTime when timezone information is unnecessary.

2. **Formatter Thread Safety**: DateTimeFormatter is thread-safe, but creating new formatters repeatedly has overhead. Cache formatters as static final fields.

3. **Parsing Performance**: Custom format parsing is slower than predefined formats. Use standard DateTimeFormatter formats when possible.

4. **Instant Operations**: Instant operations are generally faster than ZonedDateTime since they don't involve timezone calculations.

5. **Database Storage**: Store dates as LocalDate, times as LocalTime, and timestamps as Instant in databases for best performance and clarity.

## Best Practices

1. **Be Explicit About Timezone Intent**
   - Use LocalDateTime when timezone doesn't apply
   - Use ZonedDateTime when timezone matters
   - Always document timezone assumptions

2. **Cache DateTimeFormatters**
   ```java
   private static final DateTimeFormatter ISO_FORMATTER = 
       DateTimeFormatter.ISO_LOCAL_DATE_TIME;
   ```

3. **Use Predefined Formatters**
   - ISO_LOCAL_DATE, ISO_LOCAL_TIME, ISO_LOCAL_DATE_TIME
   - More efficient and standardized

4. **Store in UTC**
   - Convert ZonedDateTime to Instant for storage
   - Convert back to ZonedDateTime with user's timezone when needed

5. **Use Null-Safe Methods**
   - Avoid `null` for missing dates, use Optional instead
   - The API itself doesn't use null in factory methods

6. **Leverage Immutability**
   - Don't worry about concurrent modifications
   - Thread-safe by design

## Practical Code Examples

### Example 1: Basic LocalDate, LocalTime, LocalDateTime
```java
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

public class BasicDateTime {
    
    public static void main(String[] args) {
        System.out.println("--- Creating LocalDate ---");
        
        // 1. Creating LocalDate instances
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        
        LocalDate specificDate = LocalDate.of(2025, Month.DECEMBER, 25);
        System.out.println("Specific date: " + specificDate);
        
        LocalDate fromString = LocalDate.parse("2025-01-15");
        System.out.println("From string: " + fromString);
        
        System.out.println("\n--- Creating LocalTime ---");
        
        // 2. Creating LocalTime instances
        LocalTime now = LocalTime.now();
        System.out.println("Current time: " + now);
        
        LocalTime specificTime = LocalTime.of(14, 30, 45);
        System.out.println("Specific time: " + specificTime);
        
        LocalTime fromString2 = LocalTime.parse("15:45:30");
        System.out.println("From string: " + fromString2);
        
        System.out.println("\n--- Creating LocalDateTime ---");
        
        // 3. Creating LocalDateTime instances
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current date-time: " + currentDateTime);
        
        LocalDateTime specificDateTime = LocalDateTime.of(2025, 6, 15, 14, 30, 45);
        System.out.println("Specific date-time: " + specificDateTime);
        
        LocalDateTime combined = LocalDateTime.of(today, now);
        System.out.println("Combined: " + combined);
        
        System.out.println("\n--- Extracting Components ---");
        
        // 4. Extracting date and time components
        LocalDateTime dt = LocalDateTime.of(2025, 3, 15, 14, 30, 45);
        System.out.println("Year: " + dt.getYear());
        System.out.println("Month: " + dt.getMonth());
        System.out.println("Day: " + dt.getDayOfMonth());
        System.out.println("Hour: " + dt.getHour());
        System.out.println("Minute: " + dt.getMinute());
        System.out.println("Second: " + dt.getSecond());
        System.out.println("Day of week: " + dt.getDayOfWeek());
    }
}
```

### Example 2: Manipulating Dates and Times
```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeManipulation {
    
    public static void main(String[] args) {
        System.out.println("--- Adding/Subtracting with Fluent API ---");
        
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        
        // 1. Adding days
        LocalDate nextWeek = today.plusDays(7);
        System.out.println("Next week: " + nextWeek);
        
        // 2. Subtracting months
        LocalDate lastMonth = today.minusMonths(1);
        System.out.println("Last month: " + lastMonth);
        
        // 3. Adding years
        LocalDate nextYear = today.plusYears(1);
        System.out.println("Next year: " + nextYear);
        
        System.out.println("\n--- Using ChronoUnit ---");
        
        // 4. Using ChronoUnit for flexibility
        LocalDate date1 = LocalDate.of(2025, 1, 1);
        LocalDate date2 = LocalDate.of(2025, 3, 15);
        
        long daysApart = ChronoUnit.DAYS.between(date1, date2);
        System.out.println("Days between: " + daysApart);
        
        long monthsApart = ChronoUnit.MONTHS.between(date1, date2);
        System.out.println("Months between: " + monthsApart);
        
        System.out.println("\n--- DateTime Arithmetic ---");
        
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current: " + now);
        
        // 5. DateTime operations
        LocalDateTime oneHourLater = now.plusHours(1);
        System.out.println("One hour later: " + oneHourLater);
        
        LocalDateTime thirtyMinutesEarlier = now.minusMinutes(30);
        System.out.println("30 minutes earlier: " + thirtyMinutesEarlier);
        
        LocalDateTime twoHoursThirtyMinutesLater = now
            .plusHours(2)
            .plusMinutes(30);
        System.out.println("2 hours 30 minutes later: " + twoHoursThirtyMinutesLater);
        
        System.out.println("\n--- Comparing Dates ---");
        
        // 6. Date comparisons
        LocalDate date3 = LocalDate.of(2025, 6, 1);
        LocalDate date4 = LocalDate.of(2025, 6, 15);
        
        System.out.println("date3.isBefore(date4): " + date3.isBefore(date4));
        System.out.println("date3.isAfter(date4): " + date3.isAfter(date4));
        System.out.println("date3.isEqual(date4): " + date3.isEqual(date4));
    }
}
```

### Example 3: Working with ZonedDateTime
```java
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.Instant;

public class ZonedDateTimeExample {
    
    public static void main(String[] args) {
        System.out.println("--- Creating ZonedDateTime ---");
        
        // 1. Creating ZonedDateTime instances
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("Current (system timezone): " + now);
        
        // 2. Creating with specific timezone
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime tokyoTime = ZonedDateTime.now(tokyoZone);
        System.out.println("Tokyo time: " + tokyoTime);
        
        // 3. Converting between timezones
        ZoneId newYorkZone = ZoneId.of("America/New_York");
        ZonedDateTime newYorkTime = tokyoTime.withZoneSameInstant(newYorkZone);
        System.out.println("Converted to New York: " + newYorkTime);
        
        System.out.println("\n--- Creating from LocalDateTime ---");
        
        // 4. Creating ZonedDateTime from LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.of(2025, 3, 15, 14, 30);
        ZonedDateTime utcDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        System.out.println("UTC DateTime: " + utcDateTime);
        
        System.out.println("\n--- Converting to Instant ---");
        
        // 5. Converting to Instant (machine time)
        Instant instant = now.toInstant();
        System.out.println("Current as Instant: " + instant);
        
        // 6. Converting Instant back to ZonedDateTime
        ZonedDateTime fromInstant = instant.atZone(ZoneId.of("Europe/London"));
        System.out.println("Instant as London time: " + fromInstant);
        
        System.out.println("\n--- Timezone Information ---");
        
        // 7. Extracting timezone information
        System.out.println("Zone ID: " + now.getZone());
        System.out.println("Zone offset: " + now.getOffset());
        System.out.println("Is DST: " + now.getDaylightSavings());
        
        System.out.println("\n--- Multi-timezone Operations ---");
        
        // 8. Operations across timezones
        ZonedDateTime conference = ZonedDateTime.of(
            2025, 12, 25, 14, 0, 0, 0,
            ZoneId.of("America/Los_Angeles")
        );
        
        System.out.println("Conference in LA: " + conference);
        System.out.println("Same moment in Tokyo: " + 
            conference.withZoneSameInstant(tokyoZone));
        System.out.println("Same moment in London: " + 
            conference.withZoneSameInstant(newYorkZone));
    }
}
```

### Example 4: Period and Duration
```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class PeriodAndDuration {
    
    static class Person {
        String name;
        LocalDate birthDate;
        
        public Person(String name, int year, int month, int day) {
            this.name = name;
            this.birthDate = LocalDate.of(year, month, day);
        }
        
        public int getAge() {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
        
        public Period getTimeToBirthday() {
            LocalDate thisYearBirthday = birthDate.withYear(LocalDate.now().getYear());
            if (thisYearBirthday.isBefore(LocalDate.now())) {
                thisYearBirthday = thisYearBirthday.plusYears(1);
            }
            return Period.between(LocalDate.now(), thisYearBirthday);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("--- Using Period ---");
        
        // 1. Creating Period
        Period period = Period.of(1, 2, 3);  // 1 year, 2 months, 3 days
        System.out.println("Period: " + period);
        System.out.println("Days: " + period.getDays());
        System.out.println("Months: " + period.getMonths());
        System.out.println("Years: " + period.getYears());
        
        // 2. Creating Period between dates
        System.out.println("\n--- Calculating Age with Period ---");
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthDate, today);
        System.out.println("Birth date: " + birthDate);
        System.out.println("Age: " + age.getYears() + " years, " + 
                          age.getMonths() + " months, " + 
                          age.getDays() + " days");
        
        // 3. Using Person class
        System.out.println("\n--- Person Age Calculation ---");
        Person person = new Person("Alice", 1990, 5, 15);
        System.out.println(person.name + " age: " + person.getAge());
        Period untilBirthday = person.getTimeToBirthday();
        System.out.println("Days until birthday: " + 
            (untilBirthday.getMonths() * 30 + untilBirthday.getDays()));
        
        System.out.println("\n--- Using Duration ---");
        
        // 4. Creating Duration
        Duration duration = Duration.ofHours(2).plusMinutes(30).plusSeconds(45);
        System.out.println("Duration: " + duration);
        System.out.println("Seconds: " + duration.getSeconds());
        System.out.println("Nanos: " + duration.getNano());
        
        // 5. Duration between LocalDateTime
        System.out.println("\n--- Duration Between Times ---");
        LocalDateTime start = LocalDateTime.of(2025, 3, 15, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 15, 14, 30, 45);
        
        Duration elapsed = Duration.between(start, end);
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        System.out.println("Duration: " + elapsed);
        System.out.println("Total seconds: " + elapsed.getSeconds());
        System.out.println("Hours: " + elapsed.toHours());
        System.out.println("Minutes: " + elapsed.toMinutes());
        
        System.out.println("\n--- Using Duration for Operations ---");
        
        // 6. Adding Duration to LocalDateTime
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureTime = now.plus(duration);
        System.out.println("Now: " + now);
        System.out.println("After duration: " + futureTime);
        
        // 7. Timeout checking
        System.out.println("\n--- Timeout Simulation ---");
        LocalDateTime operationStart = LocalDateTime.now();
        Duration timeout = Duration.ofSeconds(5);
        
        // Simulate operation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        LocalDateTime operationEnd = LocalDateTime.now();
        Duration operationTime = Duration.between(operationStart, operationEnd);
        boolean timedOut = operationTime.compareTo(timeout) > 0;
        System.out.println("Operation time: " + operationTime.toMillis() + "ms");
        System.out.println("Timeout: " + timeout.toMillis() + "ms");
        System.out.println("Timed out: " + timedOut);
    }
}
```

### Example 5: DateTimeFormatter and Custom Formatting
```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeFormatting {
    
    // Cache formatters as static final for thread safety and performance
    private static final DateTimeFormatter ISO_FORMATTER = 
        DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter CUSTOM_DATE_FORMAT = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter CUSTOM_DATETIME_FORMAT = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter FRENCH_FORMATTER = 
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                         .withLocale(Locale.FRANCE);
    
    public static void main(String[] args) {
        System.out.println("--- Predefined Formatters ---");
        
        LocalDate date = LocalDate.of(2025, 3, 15);
        LocalDateTime dateTime = LocalDateTime.of(2025, 3, 15, 14, 30, 45);
        
        // 1. ISO formatters (standard)
        System.out.println("ISO Date: " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("ISO DateTime: " + dateTime.format(ISO_FORMATTER));
        
        // 2. Localized formatters
        System.out.println("\n--- Localized Formatters ---");
        System.out.println("Short US format: " + 
            date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                        .withLocale(Locale.US)));
        System.out.println("Full US format: " + 
            date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                                        .withLocale(Locale.US)));
        
        System.out.println("\n--- Custom Formatters ---");
        
        // 3. Custom patterns
        System.out.println("Custom date (dd/MM/yyyy): " + date.format(CUSTOM_DATE_FORMAT));
        System.out.println("Custom datetime: " + dateTime.format(CUSTOM_DATETIME_FORMAT));
        
        // 4. Complex custom patterns
        DateTimeFormatter complex = DateTimeFormatter.ofPattern(
            "'Date: 'EEEE, MMMM dd, yyyy 'at' HH:mm:ss"
        );
        System.out.println("Complex format: " + dateTime.format(complex));
        
        System.out.println("\n--- Parsing Strings to DateTime ---");
        
        // 5. Parsing ISO format
        LocalDate parsedDate = LocalDate.parse("2025-03-15");
        System.out.println("Parsed ISO date: " + parsedDate);
        
        LocalDateTime parsedDateTime = LocalDateTime.parse("2025-03-15T14:30:45");
        System.out.println("Parsed ISO datetime: " + parsedDateTime);
        
        // 6. Parsing custom format
        LocalDate customParsed = LocalDate.parse("15/03/2025", CUSTOM_DATE_FORMAT);
        System.out.println("Parsed custom date: " + customParsed);
        
        System.out.println("\n--- Internationalization ---");
        
        // 7. Different locales
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("English: " + 
            now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                                       .withLocale(Locale.ENGLISH)));
        System.out.println("German: " + 
            now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                                       .withLocale(Locale.GERMAN)));
        System.out.println("French: " + now.format(FRENCH_FORMATTER));
        System.out.println("Japanese: " + 
            now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                                       .withLocale(Locale.JAPAN)));
        
        System.out.println("\n--- Combining Date and Time with Custom Format ---");
        
        // 8. Manual formatting components
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime appointment = LocalDateTime.of(2025, 3, 20, 14, 30);
        
        System.out.println("Appointment: " + appointment.format(dayFormatter) + 
                          " at " + appointment.format(timeFormatter));
        
        System.out.println("\n--- Timezone in Formatting ---");
        
        // 9. Formatting with timezone
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter tzFormatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss z"
        );
        System.out.println("With timezone: " + zdt.format(tzFormatter));
    }
}
```

### Example 6: Practical Application - Event Scheduling
```java
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventScheduling {
    
    static class Event {
        String name;
        ZonedDateTime startTime;
        Duration duration;
        
        public Event(String name, ZonedDateTime startTime, Duration duration) {
            this.name = name;
            this.startTime = startTime;
            this.duration = duration;
        }
        
        public ZonedDateTime getEndTime() {
            return startTime.plus(duration);
        }
        
        public boolean hasConflict(Event other) {
            return !(getEndTime().isBefore(other.startTime) || 
                    startTime.isAfter(other.getEndTime()));
        }
        
        public String displayInTimezone(ZoneId zoneId) {
            ZonedDateTime localStart = startTime.withZoneSameInstant(zoneId);
            ZonedDateTime localEnd = getEndTime().withZoneSameInstant(zoneId);
            
            DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");
            return name + ": " + localStart.format(formatter) + 
                   " - " + localEnd.format(formatter);
        }
        
        @Override
        public String toString() {
            DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");
            return name + ": " + startTime.format(formatter) + 
                   " - " + getEndTime().format(formatter);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("--- Event Scheduling System ---\n");
        
        ZoneId laZone = ZoneId.of("America/Los_Angeles");
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        
        // Create events in LA timezone
        List<Event> events = new ArrayList<>();
        
        ZonedDateTime event1Start = ZonedDateTime.of(
            2025, 3, 20, 9, 0, 0, 0, laZone
        );
        events.add(new Event("Team Standup", event1Start, Duration.ofMinutes(30)));
        
        ZonedDateTime event2Start = ZonedDateTime.of(
            2025, 3, 20, 10, 0, 0, 0, laZone
        );
        events.add(new Event("Project Review", event2Start, Duration.ofHours(1)));
        
        ZonedDateTime event3Start = ZonedDateTime.of(
            2025, 3, 20, 14, 0, 0, 0, laZone
        );
        events.add(new Event("Client Call", event3Start, Duration.ofMinutes(45)));
        
        // Display all events in LA timezone
        System.out.println("--- Events in LA Timezone ---");
        for (Event event : events) {
            System.out.println(event.displayInTimezone(laZone));
        }
        
        // Display same events in Tokyo timezone
        System.out.println("\n--- Same Events in Tokyo Timezone ---");
        for (Event event : events) {
            System.out.println(event.displayInTimezone(tokyoZone));
        }
        
        // Check for conflicts
        System.out.println("\n--- Checking for Conflicts ---");
        for (int i = 0; i < events.size(); i++) {
            for (int j = i + 1; j < events.size(); j++) {
                Event e1 = events.get(i);
                Event e2 = events.get(j);
                if (e1.hasConflict(e2)) {
                    System.out.println("CONFLICT: " + e1.name + " and " + e2.name);
                } else {
                    System.out.println("OK: " + e1.name + " and " + e2.name + 
                                     " don't conflict");
                }
            }
        }
        
        // Calculate business hours between events
        System.out.println("\n--- Time Between Events ---");
        for (int i = 0; i < events.size() - 1; i++) {
            Event current = events.get(i);
            Event next = events.get(i + 1);
            Duration gap = Duration.between(current.getEndTime(), next.startTime);
            System.out.println("Between " + current.name + " and " + next.name + 
                             ": " + gap.toMinutes() + " minutes");
        }
        
        // Find next event
        System.out.println("\n--- Next Event ---");
        ZonedDateTime now = ZonedDateTime.now(laZone);
        events.stream()
            .filter(e -> e.startTime.isAfter(now))
            .findFirst()
            .ifPresent(e -> System.out.println("Next event: " + e.toString()));
    }
}
```

## Summary

The Java Date-Time API is the modern, standard way to work with dates and times. Key takeaways:

- **Use LocalDate** for date-only operations without timezone concerns
- **Use LocalTime** for time-only operations
- **Use LocalDateTime** for combined date and time in a single timezone
- **Use ZonedDateTime** for multi-timezone operations and storing timezone information
- **Use Instant** for machine timestamps independent of timezone
- **Use Period** for human-based time spans (years, months, days)
- **Use Duration** for machine-based time measurements (seconds, nanoseconds)
- **Use DateTimeFormatter** for formatting and parsing, cache instances for performance

This API provides everything needed for proper date-time handling in modern Java applications.
