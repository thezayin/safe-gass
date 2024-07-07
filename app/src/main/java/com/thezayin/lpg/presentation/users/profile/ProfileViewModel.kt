package com.thezayin.lpg.presentation.users.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.framework.utils.Response
import com.thezayin.usecase.AddProfile
import com.thezayin.usecase.DeleteAllProfiles
import com.thezayin.usecase.DeleteProfileById
import com.thezayin.usecase.GetAllProfiles
import com.thezayin.usecase.GetAreaList
import com.thezayin.usecase.GetCityList
import com.thezayin.usecase.GetProfileDataById
import com.thezayin.usecase.UpdateProfileById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val addProfile: com.thezayin.usecase.AddProfile,
    private val getAllProfiles: com.thezayin.usecase.GetAllProfiles,
    private val updateProfileById: com.thezayin.usecase.UpdateProfileById,
    private val deleteProfileById: com.thezayin.usecase.DeleteProfileById,
    private val getProfileDataById: com.thezayin.usecase.GetProfileDataById,
    private val deleteAllProfiles: com.thezayin.usecase.DeleteAllProfiles,
    private val getCityList: com.thezayin.usecase.GetCityList,
    private val getAreaList: com.thezayin.usecase.GetAreaList
) : ViewModel() {
    private val _getProfileList = MutableStateFlow(GetProfileState())
    val getProfileList = _getProfileList.asStateFlow()

    private val _isLoading = MutableStateFlow(GetLoadingState())
    val isLoading = _isLoading.asStateFlow()

    private val _isAddSuccess = MutableStateFlow(GetSuccessState())
    val isAddSuccess = _isAddSuccess.asStateFlow()

    private val _isDeleteSuccess = MutableStateFlow(GetSuccessState())
    val isDeleteSuccess = _isDeleteSuccess.asStateFlow()

    private val _isDeleteAllSuccess = MutableStateFlow(GetSuccessState())
    val isDeleteAllSuccess = _isDeleteAllSuccess.asStateFlow()

    private val _isUpdateSuccess = MutableStateFlow(GetSuccessState())
    val isUpdateSuccess = _isUpdateSuccess.asStateFlow()

    private val _isQueryError = MutableStateFlow(GetErrorState())
    val isQueryError = _isQueryError.asStateFlow()

    private val _getProfileById = MutableStateFlow(GetProfileByIdState())
    val getProfileById = _getProfileById.asStateFlow()

    private val _getAreaState = MutableStateFlow(GetAreaState())
    val getAreaState = _getAreaState.asStateFlow()

    private val _getCityState = MutableStateFlow(GetCityState())
    val getCityState = _getCityState.asStateFlow()

    init {
        fetchAllProfiles()
        fetchCityList()
    }

    private fun fetchCityList() = viewModelScope.launch {
        getCityList().collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _getCityState.update { it.copy(data = response.data) }
                }

                is Response.Error -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchAreaList(city: String) = viewModelScope.launch {
        getAreaList(city).collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _getAreaState.update { it.copy(data = response.data) }
                }

                is Response.Error -> {

                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun fetchAllProfiles() = viewModelScope.launch {
        getAllProfiles().collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _getProfileList.update { it.copy(data = response.data) }
                }

                is Response.Error -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun addNewProfile(
        name: String,
        phoneNumber: String,
        address: String,
        area: String,
        city: String,
        email: String
    ) = viewModelScope.launch {
        addProfile(name, phoneNumber, address, area, city, email).collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isAddSuccess.update { it.copy(isSuccess = response.data) }
                    fetchAllProfiles()
                }

                is Response.Error -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun updateProfile(
        id: Int,
        name: String,
        phoneNumber: String,
        address: String,
        email: String
    ) = viewModelScope.launch {
        updateProfileById(id, name, phoneNumber, address, email).collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isUpdateSuccess.update { it.copy(isSuccess = response.data) }
                }

                is Response.Error -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchProfileById(id: Int) = viewModelScope.launch {
        getProfileDataById(id).collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _getProfileById.update { it.copy(data = response.data) }
                }

                is Response.Error -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun deleteProfile(id: Int) = viewModelScope.launch {
        deleteProfileById(id).collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isDeleteSuccess.update { it.copy(isSuccess = response.data) }
                    fetchAllProfiles()
                }

                is Response.Error -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun deleteAll() = viewModelScope.launch {
        deleteAllProfiles().collect { response ->
            when (response) {
                is Response.Success -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isDeleteAllSuccess.update { it.copy(isSuccess = response.data) }
                }

                is Response.Error -> {
                    _isLoading.update { it.copy(isLoading = false) }
                    _isQueryError.update { it.copy(isError = true, errorMessage = response.e) }
                }

                is Response.Loading -> {
                    _isLoading.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    data class GetAreaState(val data: List<String> = emptyList())
    data class GetCityState(val data: List<String> = emptyList())
    data class GetLoadingState(val isLoading: Boolean = false)
    data class GetSuccessState(val isSuccess: Boolean = false)
    data class GetProfileState(val data: List<com.thezayin.entities.ProfileModel> = emptyList())
    data class GetProfileByIdState(val data: com.thezayin.entities.ProfileModel = com.thezayin.entities.ProfileModel())
    data class GetErrorState(val isError: Boolean = false, val errorMessage: String = "")
}