package com.assigment.matchmate.data.api

// Matches RandomUser API format
data class RandomUserResponse(
    val results: List<UserResult>,
    val info: Info
)

// Main user data
data class UserResult(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: DateOfBirth,
    val phone: String,
    val picture: Picture,
    val nat: String
)

// Name is split in parts — combine later for display
data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Location(
    val city: String,
    val state: String,
    val country: String
)

// Includes ISO date string and derived age
data class DateOfBirth(
    val date: String,
    val age: Int
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)
