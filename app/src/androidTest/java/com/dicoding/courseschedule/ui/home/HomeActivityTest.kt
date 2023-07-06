package com.dicoding.courseschedule.ui.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Using JUnit4
class HomeActivityTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun whenClickAddCourse_ShouldDisplayAddCourseActivity() {
        Intents.init() // Initialize Intents for capturing and verifying intents

        // Perform a click on the FloatingActionButton (fab) in the TaskActivity
        Espresso.onView(ViewMatchers.withId(R.id.action_add)).perform(ViewActions.click())

        // Verify that an intent to launch AddTaskActivity is sent
        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))

        Intents.release() // Release Intents resources
    }
}