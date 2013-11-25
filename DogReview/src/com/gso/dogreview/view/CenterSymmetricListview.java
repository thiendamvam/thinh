package com.gso.dogreview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.gso.dogreview.R;

public class CenterSymmetricListview extends ListView {

	private int mIndentOffset = 0;

	public CenterSymmetricListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CenterSymmetricListview, 0, 0);
		try {
			mIndentOffset = ta.getDimensionPixelSize(
					R.styleable.CenterSymmetricListview_indent_offset,
					0x00000000);
		} finally {
			ta.recycle();
		}
	}

	public CenterSymmetricListview(Context context) {
		super(context);
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		final int childCount = getChildCount();
		if (childCount == 0) {
			return super.drawChild(canvas, child, drawingTime);
		}

		final int width = getMeasuredWidth();
		final int center = childCount / 2;
		int i = indexOfChild(child);
		final int newWidth = width - (Math.abs(center - i) * mIndentOffset);
		canvas.save();
		canvas.clipRect(new Rect(child.getLeft(), child.getTop(), child
				.getLeft() + newWidth, child.getBottom()));
		boolean result = super.drawChild(canvas, child, drawingTime);
		canvas.restore();
		return result;
	}
}
