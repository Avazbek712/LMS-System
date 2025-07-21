package uz.imv.lmssystem.mapper;

import java.sql.Date;
import java.sql.Timestamp;

public class TimeMapperUtils {

    public static Long toLong(Timestamp timestamp) {
        if (timestamp == null)
            return null;
        return timestamp.getTime();
    }

    public static Timestamp toTimestamp(Long millis) {
        if (millis == null)
            return null;
        return new Timestamp(millis);
    }

    public static Long toLong(Date date) {
        if (date == null)
            return null;
        return date.getTime();
    }

    public static Date toDate(Long time) {
        if (time == null)
            return null;
        return new Date(time);
    }

}
