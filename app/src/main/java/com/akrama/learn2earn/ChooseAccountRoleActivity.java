package com.akrama.learn2earn;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChooseAccountRoleActivity extends BaseActivity implements ChooseAccountRoleView {

    @BindView(R.id.choose_role_next_btn) Button mNextButton;

    private ChooseAccountRolePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account_role);
        ButterKnife.bind(this);
        mPresenter = new ChooseAccountRolePresenter(this);
    }

    @OnClick({R.id.choose_role_student_btn, R.id.choose_role_parent_btn, R.id.choose_role_teacher_btn})
    public void onRoleClicked(RadioButton button) {
        int index = Integer.parseInt((String)button.getTag());
        mPresenter.onRoleClicked(index);
    }

    @OnClick(R.id.choose_role_next_btn)
    public void onNextClicked() {
        mPresenter.onNextClicked();
    }

    @Override
    public void enableNextButton() {
        mNextButton.setEnabled(true);
    }

    @Override
    public void launchStudentHomeScreen() {
        Toast.makeText(this, "STUDENT HOME SCREEN", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void launchParentHomeScreen() {
        Toast.makeText(this, "PARENT HOME SCREEN", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void launchTeacherHomeScreen() {
        Toast.makeText(this, "TEACHER HOME SCREEN", Toast.LENGTH_SHORT).show();
        finish();
    }

}
