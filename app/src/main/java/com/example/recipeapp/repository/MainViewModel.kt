package com.example.recipeapp.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.RecipeState
import com.example.recipeapp.repository.ApiServices.recipeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categoryState = mutableStateOf(RecipeState())
    var categoryState: State<RecipeState> = _categoryState

    init {
        fetchCategories()
    }
    private fun fetchCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = recipeService.getCategories()
                _categoryState.value = _categoryState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "error fetching data from cloud: ${e.message}"
                )
            }
        }
    }
}