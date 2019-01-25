package com.bunk.github

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.bunk.github.okreplay.OkReplayConfigurator
import com.bunk.github.view.list.RepositoryListActivity
import okreplay.OkReplay
import okreplay.TapeMode
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepositoryList {
    init {
        // FIXME needed until https://github.com/mockito/mockito/pull/1473 gets merged and deployed
        System.setProperty(
            "org.mockito.android.target",
            ApplicationProvider.getApplicationContext<Context>().cacheDir.path
        )
    }

    private val activityTestRule = ActivityTestRule<RepositoryListActivity>(
        RepositoryListActivity::class.java,
        false,
        false
    )

    @get:Rule
    var permissionRule = RuleChain
        .outerRule(activityTestRule)
        .around(OkReplayConfigurator(this::class.java).getRuleChain())

    @Before
    fun setup() {
        activityTestRule.launchActivity(Intent())
    }

    @After
    fun teardown() {
        activityTestRule.finishActivity()
    }

    @Test
    @OkReplay(mode = TapeMode.READ_ONLY)
    fun list_should_show_items() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withText("freeCodeCamp")).check(matches(isDisplayed()))
    }

    @Test
    @OkReplay(mode = TapeMode.READ_ONLY)
    fun click_on_list_item_should_open_details() {
        onView(withText("freeCodeCamp")).perform(click())
        onView(withId(R.id.languageTextView)).check(matches(isDisplayed()))
    }
}