package com.itheima.zhbj81.utils;

import android.content.Context;

public class DensityUtils {

	public static int dip2px(Context ctx, float dp) {
		float density = ctx.getResources().getDisplayMetrics().density;
		//dp = px/density
		int px = (int) (dp * density + 0.5f);
		return px;
	}

	public static float px2dip(Context ctx, int px) {
		float density = ctx.getResources().getDisplayMetrics().density;
		//dp = px/density
		float dp = px / density;
		return dp;
	}
}
