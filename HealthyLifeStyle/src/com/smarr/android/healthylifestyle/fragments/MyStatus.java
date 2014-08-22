package com.smarr.android.healthylifestyle.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class MyStatus extends Fragment {

	private StoreAppInfo storage;

	public MyStatus() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_mystatus, container,
				false);
		getActivity().setTitle("My Status");

		storage = new StoreAppInfo(getActivity());

		createWeightGraph(rootView);

		return rootView;
	}

	public void createWeightGraph(View rootView) {

		int weightCounter = storage.getInt("weightCounter", 0);
		ArrayList<Integer> weightList = new ArrayList<Integer>();

		for (int i = 0; i <= weightCounter; i++) {
			int holder = storage.getInt("current_Weight" + i, 0);
			weightList.add(holder);
		}

		GraphViewData[] data = new GraphViewData[weightList.size()];
		for (int i = 0; i != weightList.size(); i++) {
			data[i] = new GraphViewData(i, weightList.get(i));

		}

		LineGraphView graphView = new LineGraphView(getActivity(),
				"Weight Loss Progress");
		GraphViewSeries weight = new GraphViewSeries(
				"Weight Progress Over Time in weeks", null, data);
		graphView.setViewPort(0, 9);

		// graphView.setShowHorizontalLabels(false);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.setDrawDataPoints(true);
		graphView.setCustomLabelFormatter(new CustomLabelFormatter() {

			@Override
			public String formatLabel(double arg0, boolean arg1) {
				// TODO Auto-generated method stub

				return "" + ((int) arg0);
			}
		});
		// add data
		graphView.addSeries(weight);
		// set view port, start=2, size=40
		
			graphView.setScrollable(true);
		
		// optional - activate scaling / zooming

		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.lineGraph);
		layout.addView(graphView);

	}

}
