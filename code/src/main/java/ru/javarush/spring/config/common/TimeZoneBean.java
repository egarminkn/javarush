package ru.javarush.spring.config.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by eGarmin
 */
@Component
public class TimeZoneBean {

    private Map<Integer, String> rawOffsetIdMap = new HashMap<>();

    /**
     * Конструкторы
     */
    public TimeZoneBean() {
        // Конструктор по умолчанию
        for (String id : TimeZone.getAvailableIDs()) {
            TimeZone tz = TimeZone.getTimeZone(id);
            if (!id.contains("GMT") && !id.contains("UTC") && !id.contains(" ")) {
                int rawOffest = tz.getRawOffset();
                rawOffsetIdMap.put(rawOffest, id);
            }
        }
    }

    /**
     *  Методы
     */
    public String getIdByRawOffset(int rawOffset) {
        return rawOffsetIdMap.get(rawOffset);
    }

    public String getIdByTimeZone(TimeZone tz) {
        return rawOffsetIdMap.get(tz.getRawOffset());
    }

}
