package com.smarr.android.healthylifestyle.utilities.validation;

import java.util.regex.Pattern;

import android.widget.EditText;

public class UserValidation {
	private boolean userNameOk, passWordOk;

	public boolean validateUserName(CharSequence s, EditText userName,
			CharSequence tooLong, CharSequence tooShort, CharSequence oneNum,
			CharSequence startLetter) {
		try {
			if (Character.isLetter(s.charAt(0))) {
				if (s.length() > 7) {
					if (s.length() < 16) {
						if (Pattern.compile("[0-9]").matcher(s.toString())
								.find()) {
							userName.setError(null);
							setUserNameOk(true);
							if (this.userNameOk && this.passWordOk) {
								return true;
							}

						} else {
							userName.setError(oneNum);
							setUserNameOk(false);
							return false;
						}

					} else {
						userName.setError(tooLong);
						setUserNameOk(false);
						return false;
					}
				} else {
					userName.setError(tooShort);
					setUserNameOk(false);
					return false;
				}
			} else {
				userName.setError(startLetter);
				setUserNameOk(false);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validatePassWord(CharSequence s, EditText passWord,
			CharSequence tooLong, CharSequence tooShort, CharSequence oneNum,
			CharSequence oneLetter) {
		if (s.length() > 5) {
			if (s.length() < 17) {
				if (Pattern.compile("[a-zA-Z]").matcher(s.toString()).find()) {
					if (Pattern.compile("[0-9]").matcher(s.toString()).find()) {
						passWord.setError(null);
						setPassWordOk(true);
						if (this.userNameOk && this.passWordOk) {
							return true;
						}
					} else {
						passWord.setError(oneNum);
						setPassWordOk(false);
						return false;
					}
				} else {
					passWord.setError(oneLetter);
					setPassWordOk(false);
					return false;
				}
			} else {
				passWord.setError(tooLong);
				setPassWordOk(false);
				return false;
			}
		} else {
			passWord.setError(tooShort);
			setPassWordOk(false);
			return false;
		}
		return false;
	}
	
	public boolean isUserNameOk() {
		return userNameOk;
	}

	public void setUserNameOk(boolean userNameOk) {
		this.userNameOk = userNameOk;
	}

	public boolean isPassWordOk() {
		return passWordOk;
	}

	public void setPassWordOk(boolean passWordOk) {
		this.passWordOk = passWordOk;
	}


}
