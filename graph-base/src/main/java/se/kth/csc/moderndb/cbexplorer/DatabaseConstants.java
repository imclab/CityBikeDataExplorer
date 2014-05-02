package se.kth.csc.moderndb.cbexplorer;

/**
 * Created by mhotan on 5/2/14.
 */
public class DatabaseConstants {


    public static final String DATABASE_PATH = "target/test.db";

    // Labels
    public static final String BIKE_LABEL = "_Bike";
    public static final String STATION_LABEL = "_Station";
    public static final String TRIP_LABEL = "_Trip";

    // Relations
    public static final String USES_RELATION = "USES";
    public static final String STARTED_FROM_RELATION = "STARTED_FROM";
    public static final String ENDED_AT_RELATION = "ENDED_AT";

    // Bike Properties
    public static final String BIKE_ID = "bikeID";

    // Station Properties
    public static final String STATION_ID = "stationID";
    public static final String STATION_NAME = "name";
    public static final String STATION_LOCATION = "location";

    // Trip Properties
    public static final String TRIP_START_TIME = "startTime";
    public static final String TRIP_END_TIME = "endTime";
    public static final String TRIP_USER_TYPE = "userType";
    public static final String TRIP_USER_BIRTH_YEAR = "userBirthYear";
    public static final String TRIP_USER_GENDER = "userGender";

}
