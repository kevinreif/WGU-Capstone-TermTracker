package com.c196.termtracker.database;

import androidx.room.TypeConverter;

import com.c196.termtracker.utilities.CourseStatus;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromCourseStatus(CourseStatus courseStatus) {
        return courseStatus == null ? null : courseStatus.name();
    }

    @TypeConverter
    public static CourseStatus toCourseStatus(String string) {
        return string.isEmpty() ? null : CourseStatus.valueOf(string);
    }
}
