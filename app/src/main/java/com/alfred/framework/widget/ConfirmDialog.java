package com.alfred.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alfred.framework.myframework.R;

public class ConfirmDialog extends Dialog implements OnClickListener {
	private TextView title;
	private View view;
	private TextView message;

	private Button btnSure, btnCancel;

	private OnMyclickListener myclickListener;

	public ConfirmDialog(Context context) {
		super(context, R.style.dialog_untran);
		getWindow().setContentView(R.layout.layout_dialog);
		view = getWindow().findViewById(R.id.view1);
		title = (TextView) getWindow().findViewById(R.id.alertTitle);
		message = (TextView) getWindow().findViewById(R.id.message);
		btnSure = (Button) getWindow().findViewById(R.id.button1);
		btnCancel = (Button) getWindow().findViewById(R.id.button2);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	/**
	 * 设置按钮文字
	 */
	public void setBtnSureText(String text) {
		btnSure.setText(text);
	}

	public void setBtnCancelText(String text) {
		btnCancel.setText(text);
	}

	public void setView(int i) {
		view.setVisibility(i);
	}

	public void setBtnSureGone() {
		btnSure.setVisibility(View.GONE);
	}

	public void setBtnSureVisible() {
		btnSure.setVisibility(View.VISIBLE);
	}

	public void setBtnCancelGone() {
		btnCancel.setVisibility(View.GONE);
	}

	public void setBtnCancelVisible() {
		btnCancel.setVisibility(View.VISIBLE);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setMessage(String message) {
		this.message.setText(message);
	}

	public interface OnMyclickListener {
		public void OnSureListener(View view);

		public void OnCancelListener(View view);
	}

	public OnMyclickListener getMyclickListener() {
		return myclickListener;
	}

	public void setMyclickListener(OnMyclickListener myclickListener) {
		this.myclickListener = myclickListener;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button1:
			if (getMyclickListener() != null)
				getMyclickListener().OnSureListener(view);
			dismiss();
			break;
		case R.id.button2:
			if (getMyclickListener() != null)
				getMyclickListener().OnCancelListener(view);
			dismiss();
			break;
		default:
			break;
		}
	}

}
