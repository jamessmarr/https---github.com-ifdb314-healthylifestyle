package com.smarr.android.healthylifestyle.misc_class;

import android.text.format.Time;

public class PlanItem {
	
	private Time startTime, endTime;
	private String displayText;
	private boolean actionRequired;
	private boolean correctActionIndicator;
	
	private int strikeValue;
	private boolean enableNotification;
	private String notificationMessage;
	private String itemType;// assigned the static values below
	private String itemSubType;// assigned the static values below

	// private static final String itemKey = "";//needs to be 45 char commented
	// out for now not sure the use here
	private static final  String actionIndicatorLabelKeyValue = "YN";// no idea what this is for
	private static final String ACTION_ITEM = "A";
	private static final String TIP = "T";
	private static final String PREP_ITEM = "P";
	private static final String PRE_PLAN_ITEM = "PRE";
	
	public PlanItem(Time startTime, Time endTime, String displayText,
			boolean actionRequired, boolean correctActionIndicator,
			int strikeValue, boolean enableNotification,
			String notificationMessage, String itemType, String itemSubType) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.displayText = displayText;
		this.actionRequired = actionRequired;
		this.correctActionIndicator = correctActionIndicator;
		this.strikeValue = strikeValue;
		this.enableNotification = enableNotification;
		this.notificationMessage = notificationMessage;
		this.itemType = itemType;
		this.itemSubType = itemSubType;
	}
}
