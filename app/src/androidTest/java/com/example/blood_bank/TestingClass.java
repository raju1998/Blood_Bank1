package com.example.blood_bank;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestingClass {

    @Rule
    public ActivityTestRule<MainActivity> activityTestingActivityTestRule=new ActivityTestRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<donerandreciver> activityTestingActivityTestRule1=new ActivityTestRule<>(donerandreciver.class);

    MainActivity mainActivity;
    donerandreciver donor;
    @Before
    public void setup() throws Exception
    {
        mainActivity=activityTestingActivityTestRule.getActivity();
        donor=activityTestingActivityTestRule1.getActivity();
    }

    @Test
    public void activityTester(){
        View view=mainActivity.findViewById(R.id.login_button);
        assertNotNull(view);
        View view1=donor.findViewById(R.id.donate_button);
        assertNotNull(view1);
    }


}