package func;

import android.provider.BaseColumns;
    public  abstract class TableEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_NAME_ID = "event_id";
        public static final String COLUMN_NAME = "event_name";
        public static final String COLUMN_NAME_TIME = "timings";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_VEN = "venue";
        public static final String COLUMN_NAME_LAT = "latitude";
        public static final String COLUMN_NAME_LON = "longitude";
    }


