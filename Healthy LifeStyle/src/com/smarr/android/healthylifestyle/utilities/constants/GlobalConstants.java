package com.smarr.android.healthylifestyle.utilities.constants;

public class GlobalConstants {

	public static final String MY_GLOBAL_CONSTANT_TEST="Global Constant Test";

	public static final String ERROR_DIRECTORY="Uduwa_Errors";

	public static String DEFAULT_DATE_FORMAT="MM/dd/yyyy";
	public static String DATE_ONLY_LONG_FORMAT="MM/dd/yyyy";
	public static String DATE_ONLY_SHORT_FORMAT="MM/dd/yy";
	public static String DATE_TIME_DEFAULT_FORMAT="MM/dd/yyyy h:mm a";


	public static final String  EOL = "\n";
	public static final String NEW_LINE="\n";

	public static final String SINGLE_SPACE=" ";
	public static final String DEFAULT_DATE_SEPARATOR="/";
	public static final String DEFAULT_EMAIL_SEPARATOR=",";
	public static final String DEFAULT_TIME_SEPARATOR=":";

	public static final String DB_DELIMITER = "$";
	public static final String COMMA_DELIMITER = ",";

	public static final boolean DEBUG_OUTPUT=false;
	public static final boolean DEBUG_SCREEN_OUTPUT=false;

	public static final String APP_SUPPORT_EMAIL_DEFAULT="appsupport@uduwa.com";

	public static final String APP_SHARED_PREFERENCES_KEY="uduwaAppSharedPref";

	// GPS Constants
	public static final int GPS_CHECK_CHANGE_METER_RATE=500; // 500 meters difference to register GPS auto change
	public static final int GPS_CHECK_FREQUENCY_RATE=450000;//milliseconds (7.5 min = 450000 ms)
	public static final float GPS_IN_STORE_RANGE=1200; // 1500 meters difference from location and your in the same location

	public static final int MAXIMUM_ERROR_DIALOG_MESSAGE_LENGTH_IN_CHARACTERS=250;

	// Screen constants
	public static final int SCREEN_SMALL_SIZE_MIN=1000; // in pixels
	public static final float SCREEN_SMALL_SIZE_MIN_IN=5.9f; // in pixels

	public static final int SQL_COlUMN_NOT_FOUND=-1;
	public static final int INT_CONSTANT_INDICATING_INVALID_OR_NOT_SET=-1;


	// Regex validations
	public static String VALIDATON_US_CURRENCY="^[\\-]{0,1}[\\$]{0,1}\\d{1,12}(\\.\\d{2})$";
	public static String VALIDATON_SINGLE_DECIMAL_PRECISION="^\\d{1,12}(\\.\\d{1})$";
	public static String VALIDATON_WHOLE_NUMBER="([-]{1}[0-9]*|[0-9]*)";
	public static String VALIDATON_USERNAME_PASSWORD_VALIDATE_PATTERN="[a-zA-Z]{1}[a-zA-Z0-9_#]{4,12}$";

	// ACTIVITY CONSTANTS OR MESSAGES


	// GENERAL CONSTANTS
	public static final int ITEM_VALUE_YES = 1;
	public static final int ITEM_VALUE_NO = 0;
	public static final int ITEM_VALUE_INVALID = -1;

	public static final int BOOLEAN_TRUE_VALUE_AS_INT = 1;
	public static final int BOOLEAN_FALSE_VALUE_AS_INT = 0;

	public static final int TRISTATE_VALUE_POSITIVE = 1;
	public static final int TRISTATE_VALUE_UNKNOWN = 0;
	public static final int TRISTATE_VALUE_NEGATIVE = -1;
	// DO NOT CHANGE BELOW UNLESS THINGS ARE CHANGED ON SERVER
	public static final String TABLET_UNIQUE_MSG_MARKER="*-*-!";   // primarily used for stripping out message from server (marked with this marker) for presentation to user as an error message
	public static final String TABLET_MARKER_BEGIN_ACTUAL_MSG="- ";

	// IN APP ACTIVITY PASSED PARAMS - SHARED PREFERENCE KEYS
	public static final String PREF_KEY_SMALL_SCREEN = "SmallScreen";

	public static final String MONDAY="MONDAY";
	public static final String TUESDAY="TUESDAY";
	public static final String WEDNESDAY="WEDNESDAY";
	public static final String THURSDAY="THURSDAY";
	public static final String FRIDAY="FRIDAY";
	public static final String SATURDAY="SATURDAY";
	public static final String SUNDAY="SUNDAY";
	public static final int MONDAY_VAL=1;
	public static final int TUESDAY_VAL=2;
	public static final int WEDNESDAY_VAL=3;
	public static final int THURSDAY_VAL=4;
	public static final int FRIDAY_VAL=5;
	public static final int SATURDAY_VAL=6;
	public static final int SUNDAY_VAL=0;


}
