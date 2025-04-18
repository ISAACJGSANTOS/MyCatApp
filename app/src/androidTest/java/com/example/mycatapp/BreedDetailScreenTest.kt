import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.mycatapp.domain.DashboardViewModel
import com.example.mycatapp.ui.screens.BreedDetailScreen
import com.example.networking.models.Breed
import com.google.gson.Gson
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BreedDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeViewModel = mockk<DashboardViewModel>(relaxed = true)

    @Test
    fun testFavoriteIconClickCallsViewModel() {
        val sampleBreed = Breed(
            id = "test_id",
            name = "Test Breed",
            imageId = "https://example.com/test.jpg",
            origin = "Test Origin",
            lifespan = "10 years",
            temperament = "Calm",
            description = "Test description",
            isUserFavorite = false
        )
        val sampleBreedJson = Gson().toJson(sampleBreed, Breed::class.java)

        composeTestRule.setContent {
            BreedDetailScreen(
                viewModel = fakeViewModel,
                breed = sampleBreedJson,
                onPressBack = {  }
            )
        }

        composeTestRule.onNodeWithContentDescription("Favorite")
            .assertIsDisplayed()
            .performClick()

        verify { fakeViewModel.onFavoriteButtonClicked(sampleBreed) }
    }

    @Test
    fun breedDetailScreenDisplaysDataAndBackWorks() {
        var backPressed = false

        val sampleBreed = Breed(
            id = "test_id",
            name = "Test Breed",
            imageId = "https://example.com/test.jpg",
            origin = "Test Origin",
            lifespan = "10 years",
            temperament = "Calm",
            description = "Test description",
            isUserFavorite = false
        )
        val sampleBreedJson = Gson().toJson(sampleBreed, Breed::class.java)

        composeTestRule.setContent {
            BreedDetailScreen(
                viewModel = fakeViewModel,
                breed = sampleBreedJson,
                onPressBack = { backPressed = true }
            )
        }

        composeTestRule.onNodeWithText("Test Breed")
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Back")
            .performClick()

        composeTestRule.runOnIdle {
            assertTrue("Back callback should be triggered", backPressed)
        }
    }
}
