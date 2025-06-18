package com.assigment.matchmate.data.repository

import androidx.lifecycle.LiveData
import com.assigment.matchmate.data.api.ApiService
import com.assigment.matchmate.data.database.UserDao
import com.assigment.matchmate.data.database.UserEntity
import com.assigment.matchmate.data.database.UserStatus
import com.assigment.matchmate.utils.MatchScoreCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*
import kotlin.random.Random

class UserRepository(private val userDao: UserDao) {

    private val apiService: ApiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                // Simulate 30% network failure
                if (Random.nextFloat() < 0.3f) {
                    throw java.io.IOException("Simulated network failure")
                }
                chain.proceed(chain.request())
            }
            .build()

        Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getAllUsers(): LiveData<List<UserEntity>> = userDao.getAllUsers()

    suspend fun fetchAndStoreUsers(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getRandomUsers(10)
            if (response.isSuccessful) {
                val users = response.body()?.results?.map { userResult ->
                    UserEntity(
                        id = UUID.randomUUID().toString(),
                        firstName = userResult.name.first,
                        lastName = userResult.name.last,
                        age = userResult.dob.age,
                        city = userResult.location.city,
                        state = userResult.location.state,
                        country = userResult.location.country,
                        imageUrl = userResult.picture.large,
                        email = userResult.email,
                        phone = userResult.phone,
                        gender = userResult.gender,
                        education = generateEducation(),
                        occupation = generateOccupation(),
                        matchScore = MatchScoreCalculator.calculateScore(
                            userResult.dob.age,
                            userResult.location.city
                        )
                    )
                } ?: emptyList()

                userDao.insertUsers(users)
                Result.success(Unit)
            } else {
                Result.failure(Exception("API call failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserStatus(userId: String, status: UserStatus) {
        userDao.updateUserStatus(userId, status)
    }

    suspend fun getUserCount(): Int = userDao.getUserCount()

    private fun generateEducation(): String {
        val educationOptions = listOf(
            "Bachelor's Degree", "Master's Degree", "PhD", "Diploma",
            "High School", "Engineering", "MBA", "Medical"
        )
        return educationOptions.random()
    }

    private fun generateOccupation(): String {
        val occupationOptions = listOf(
            "Software Engineer", "Doctor", "Teacher", "Business Analyst",
            "Marketing Manager", "Designer", "Consultant", "Engineer",
            "Lawyer", "Architect", "Nurse", "Entrepreneur"
        )
        return occupationOptions.random()
    }
}