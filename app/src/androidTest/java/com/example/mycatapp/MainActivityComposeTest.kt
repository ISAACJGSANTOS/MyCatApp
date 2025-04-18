package com.example.mycatapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mycatapp.domain.DashboardViewModel
import com.example.mycatapp.domain.repositories.model.OperationStateResult
import com.example.mycatapp.ui.theme.MyCatAppTheme
import com.example.networking.models.Breed
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mycatapp.navigation.NavGraph

@RunWith(AndroidJUnit4::class)
class MainActivityComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockedDashboardViewModel: DashboardViewModel = mockk(relaxed = true)
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        every { mockedDashboardViewModel.breedsFlow } returns MutableStateFlow(
            OperationStateResult.Success(
                arrayOf(
                    Breed(
                        id = "siamese",
                        name = "Siamese",
                        imageId = "https://example.com/siamese.jpg",
                        origin = "Thailand",
                        lifespan = "15 years",
                        temperament = "Affectionate",
                        description = "A lively, social and friendly cat.",
                        isUserFavorite = false
                    )
                )
            )
        )

        composeTestRule.setContent {
            MyCatAppTheme {
                navController = rememberNavController()
                // Pass our mocked view model explicitly.
                NavGraph(navController = navController, viewModel = mockedDashboardViewModel)
            }
        }
    }

    @Test
    fun testTitleIsDisplayed() {
        composeTestRule.onNodeWithText("My Cats App")
            .assertExists("Title 'My Cats App' should be visible")
            .assertIsDisplayed()
    }

    @Test
    fun testBottomNavigationSwitchesToFavorite() {
        composeTestRule.onNodeWithText("Favorite")
            .assertExists("The 'Favorite' navigation item should exist")
            .performClick()

        composeTestRule.onNodeWithTag("SearchBar")
            .assertDoesNotExist()

        composeTestRule.onNodeWithText("Favorite")
            .assertIsSelected()
    }

    @Test
    fun testBottomNavigationSwitchesToBreeds() {
        composeTestRule.onNodeWithText("Favorite")
            .assertExists("The 'Favorite' navigation item should exist")
            .performClick()

        composeTestRule.onNodeWithTag("SearchBar")
            .assertDoesNotExist()

        composeTestRule.onNodeWithText("Favorite")
            .assertIsSelected()
    }

    @Test
    fun testNavigateToBreedDetail() {

        composeTestRule.onNodeWithText("Siamese")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.runOnIdle {
            val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""
            assert(currentRoute.contains("breedDetails")) {
                "Expected route to contain 'breedDetails', but was: $currentRoute"
            }
        }
    }

    @Test
    fun testErrorAlertDialogIsDisplayed() {
        (mockedDashboardViewModel.breedsFlow as MutableStateFlow).value =
            OperationStateResult.Error("Something went wrong")

        composeTestRule.onNodeWithTag("ErrorAlertDialog")
            .assertExists("Error alert dialog should be displayed when there's an error")
            .assertIsDisplayed()
    }
}
