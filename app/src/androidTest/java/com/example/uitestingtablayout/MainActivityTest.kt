package com.example.uitestingtablayout

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.uitestingtablayout.R.id
import com.google.android.material.tabs.TabLayout
import org.hamcrest.*
import org.hamcrest.core.AllOf.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get : Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    val arrayOfFragmentsRootLayouts = arrayOf(
        id.FragmentOne_rootLayout,
        id.FragmentTwo_rootLayout,
        id.FragmentThree_rootLayout
    )

    val arrayOfIcons = arrayOf(
        R.drawable.ic_copyright, R.drawable.ic_credit_card,
        R.drawable.ic_delete
    )

    val arrayOfText = arrayOf("Tab 1", "Tab 2", "Tab 3")

    @Test
    fun test_fragment_inViewOnStartUp() {

        onView(
            allOf(
                withId(arrayOfFragmentsRootLayouts[0]),
                isDescendantOfA(withId(id.MainActivity_viewPager))
            )
        )
    }

    @Test
    fun test_visibility_tabLayoutIcons() {

        onView(withId(R.id.MainActivity_tabLayout)).check(matches(isDisplayed()))

        //Check if tab icons are displayed
        for (x in 0..2) {
            onView(CoreMatchers.allOf(withId(arrayOfIcons[x]), isDescendantOfA(withId(id.MainActivity_tabLayout))))
        }
    }

    @Test
    fun test_visibility_tabLayoutText() {
        onView(withId(R.id.MainActivity_tabLayout)).check(matches(isDisplayed()))


        //Check if tab text is displayed
        for (x in 0..2) {
            onView(CoreMatchers.allOf(withText(arrayOfText[x]), isDescendantOfA(withId(id.MainActivity_tabLayout))))
        }

    }

    @Test
    fun test_navigation_tabLayout() {

        for (x in 0..2) {
            onView(withId(id.MainActivity_tabLayout)).perform(selectTabAtPosition(x))
            onView(
                allOf(
                    withId(arrayOfFragmentsRootLayouts[x]),
                    isDescendantOfA(withId(id.MainActivity_viewPager))
                )
            )
                .check(matches(withEffectiveVisibility(VISIBLE)))
        }
    }

    // Function to Select a tab on position given.
    fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"
            override fun getConstraints() =
                allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }
}