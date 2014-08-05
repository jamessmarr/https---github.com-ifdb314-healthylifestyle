package com.smarr.android.healthylifestyle.misc_class;

import java.util.ArrayList;

public class LifeStylePlan {
	private int plan_version_number;
	private int plan_strikes;
	private int plan_duration_days;
	private int plan_level;

	private ArrayList<PlanItem> planItems;

	public LifeStylePlan(int version, int strikes, int duration, int level){
		this.plan_version_number = version;
		this.plan_strikes = strikes;
		this.plan_duration_days = duration;
		this.plan_level = level;
		//need to add a way to create an arraylist of Plan Items 
	}
}
