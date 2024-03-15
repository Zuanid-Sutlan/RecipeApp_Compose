package com.example.recipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipeapp.model.Category
import com.example.recipeapp.repository.MainViewModel


@Composable
fun RecipeAppNavigation(navController: NavHostController){
    val mainViewModel: MainViewModel = viewModel()
    val recipeState by mainViewModel.categoryState

    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route){
        composable(Screen.RecipeScreen.route){
            RecipeScreen(viewState = recipeState, navigateToDetails = {
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                navController.navigate(Screen.DetailScreen.route)
            })
        }

        composable(Screen.DetailScreen.route){
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat") ?:
            Category("","","","")
            RecipeDescriptionScreen(category = category)
        }
    }
}