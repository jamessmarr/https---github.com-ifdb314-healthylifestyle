package com.smarr.android.healthylifestyle.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class UpdateWeights extends Fragment {

	private RadioButton current_image_1, current_image_2, current_image_3,
			current_image_4, current_image_5;

	private RadioButton desired_image_1, desired_image_2, desired_image_3,
			desired_image_4, desired_image_5;

	private Button update;

	private int current_image_checked, desired_image_checked, currentWeight,
			desiredWeight;

	private boolean desiredChecked, currentChecked, currentWeightEntered,
			desiredWeightEntered;

	private EditText current_weight, desired_weight;

	private StoreAppInfo storage;

	public UpdateWeights() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_update_weights, container,
				false);

		storage = new StoreAppInfo(getActivity());
		//edittext for curent weight entry
		current_weight = (EditText) v.findViewById(R.id.currentWeightUpdate);
		current_weight.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) throws NumberFormatException {
				try {
					currentWeight = Integer.parseInt(s.toString());
					currentWeightEntered = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//edittext for desired weight entry
		desired_weight = (EditText) v.findViewById(R.id.desiredWeightUpdate);
		desired_weight.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) throws NumberFormatException {
				try {
					desiredWeight = Integer.parseInt(s.toString());
					desiredWeightEntered = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Radio Buttons
		current_image_1 = (RadioButton) v
				.findViewById(R.id.currentImageUpdate1);
		current_image_2 = (RadioButton) v
				.findViewById(R.id.currentImageUpdate2);
		current_image_3 = (RadioButton) v
				.findViewById(R.id.currentImageUpdate3);
		current_image_4 = (RadioButton) v
				.findViewById(R.id.currentImageUpdate4);
		current_image_5 = (RadioButton) v
				.findViewById(R.id.currentImageUpdate5);
		// Radio Buttons
		desired_image_1 = (RadioButton) v
				.findViewById(R.id.desiredImageUpdate1);
		desired_image_2 = (RadioButton) v
				.findViewById(R.id.desiredImageUpdate2);
		desired_image_3 = (RadioButton) v
				.findViewById(R.id.desiredImageUpdate3);
		desired_image_4 = (RadioButton) v
				.findViewById(R.id.desiredImageUpdate4);
		desired_image_5 = (RadioButton) v
				.findViewById(R.id.desiredImageUpdate5);

		// controls Radios
		current_image_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_image_checked = 1;
				currentChecked = true;
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_image_checked = 2;
				currentChecked = true;
				current_image_1.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_image_checked = 3;
				currentChecked = true;
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_image_checked = 4;
				currentChecked = true;
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_image_checked = 5;
				currentChecked = true;
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
			}
		});
		// Controls Radios
		desired_image_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_image_checked = 1;
				desiredChecked = true;
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_image_checked = 2;
				desiredChecked = true;
				desired_image_1.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_image_checked = 3;
				desiredChecked = true;
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_image_checked = 4;
				desiredChecked = true;
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_image_checked = 5;
				desiredChecked = true;
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
			}
		});
		// controls Next Button
		update = (Button) v.findViewById(R.id.updateInfo);
		update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentWeightEntered) {
					current_weight.setError(null);
				} else {
					current_weight.setError("Required");
					Toast.makeText(getActivity(), "All fields are required",
							Toast.LENGTH_SHORT).show();
				}
				if (desiredWeightEntered) {
					desired_weight.setError(null);
				} else {
					desired_weight.setError("Required");
					Toast.makeText(getActivity(), "All fields are required",
							Toast.LENGTH_SHORT).show();
				}
				if (currentChecked) {
					current_image_5.setError(null);
				} else {
					current_image_5.setError("Required");
					Toast.makeText(getActivity(), "All fields are required",
							Toast.LENGTH_SHORT).show();
				}
				if (desiredChecked) {
					desired_image_5.setError(null);
				} else {
					desired_image_5.setError("Required");
					Toast.makeText(getActivity(), "All fields are required",
							Toast.LENGTH_SHORT).show();
				}
				if (currentWeightEntered && desiredWeightEntered
						&& currentChecked && desiredChecked) {
					storage.putInt("current_Weight", currentWeight);
					storage.putInt("desired_Weight", desiredWeight);
					storage.putInt("current_body_image", current_image_checked);
					storage.putInt("desired_body_image", desired_image_checked);
					storage.putBoolean("weight_updated", true);
					// do some kind of post to server

					// go back to myStatus
					Toast.makeText(getActivity(), "Info Updated",
							Toast.LENGTH_SHORT).show();
					getFragmentManager().popBackStackImmediate();

				}

			}
		});

		return v;
	}

}
