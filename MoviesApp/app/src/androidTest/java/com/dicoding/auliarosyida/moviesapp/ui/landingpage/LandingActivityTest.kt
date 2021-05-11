package com.dicoding.auliarosyida.moviesapp.ui.landingpage

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.auliarosyida.moviesapp.R
import com.dicoding.auliarosyida.moviesapp.utils.DataMovies
import org.junit.Rule
import org.junit.Test

class LandingActivityTest {
    private val dummyMovies = DataMovies.generateMovies()
    private val dummyTvShows = DataMovies.generateTvShows()

    @get:Rule
    var activityRule = ActivityScenarioRule(LandingActivity::class.java)

    @Test
    fun loadMovies() {
        delayTwoSecond()
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadDetailMovie() {
        delayTwoSecond()
        delayTwoSecond()
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delayTwoSecond()
        delayTwoSecond()
        delayTwoSecond()
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyMovies[0].title)))

        onView(withId(R.id.text_quote)).check(matches(isDisplayed()))
        onView(withId(R.id.text_quote)).check(matches(withText(dummyMovies[0].quote)))

        onView(withId(R.id.text_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).check(matches(withText(dummyMovies[0].overview)))

        onView(withId(R.id.text_year)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year)).check(matches(withText(dummyMovies[0].releaseYear)))

        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(withText(dummyMovies[0].genre)))

        onView(withId(R.id.text_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.text_duration)).check(matches(withText(dummyMovies[0].duration)))
    }

    @Test
    fun loadTvShows() {
        onView(withText(R.string.tvshow)).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShows.size))
    }

    @Test
    fun loadDetailTvShow() {
        delayTwoSecond()
        onView(withText(R.string.tvshow)).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        delayTwoSecond()
        delayTwoSecond()
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyTvShows[0].title)))

        onView(withId(R.id.text_quote)).check(matches(isDisplayed()))
        onView(withId(R.id.text_quote)).check(matches(withText(dummyTvShows[0].quote)))

        onView(withId(R.id.text_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.text_overview)).check(matches(withText(dummyTvShows[0].overview)))

        onView(withId(R.id.text_year)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year)).check(matches(withText(dummyTvShows[0].releaseYear)))

        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(withText(dummyTvShows[0].genre)))

        onView(withId(R.id.text_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.text_duration)).check(matches(withText(dummyTvShows[0].duration)))
    }

    private fun delayTwoSecond() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}