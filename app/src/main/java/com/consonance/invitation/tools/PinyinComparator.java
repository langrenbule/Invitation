package com.consonance.invitation.tools;


import com.consonance.invitation.entity.UserLetterBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<UserLetterBean> {

	public int compare(UserLetterBean o1, UserLetterBean o2) {
		if (o1.getLetter().equals("@")
				|| o2.getLetter().equals("#")) {
			return -1;
		} else if (o1.getLetter().equals("#")
				|| o2.getLetter().equals("@")) {
			return 1;
		} else {
			return o1.getLetter().compareTo(o2.getLetter());
		}
	}

}
