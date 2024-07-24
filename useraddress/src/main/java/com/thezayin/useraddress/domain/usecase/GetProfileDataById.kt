package com.thezayin.useraddress.domain.usecase

import com.thezayin.entities.ProfileModel
import com.thezayin.framework.utils.Response
import com.thezayin.useraddress.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

interface GetProfileDataById : suspend (Int) -> Flow<Response<ProfileModel>>
class GetProfileDataByIdImpl(private val profileRepository: ProfileRepository) :
    GetProfileDataById {
    override suspend fun invoke(id: Int): Flow<Response<ProfileModel>> =
        profileRepository.getProfileById(id)
}