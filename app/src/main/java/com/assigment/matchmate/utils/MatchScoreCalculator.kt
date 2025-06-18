package com.assigment.matchmate.utils

object MatchScoreCalculator {

    private const val REFERENCE_AGE = 26 // Ideal age
    private const val REFERENCE_CITY = "Mumbai" // My hometown

    fun calculateScore(age: Int, city: String): Int {
        val ageScore = calculateAgeScore(age)
        val cityScore = calculateCityScore(city)

        // we will be considering 60% of age and 40% of city
        return ((ageScore * 0.6) + (cityScore * 0.4)).toInt()
    }

    private fun calculateAgeScore(age: Int): Int {
        val ageDifference = kotlin.math.abs(age - REFERENCE_AGE)
        return when {
            ageDifference <= 2 -> 100
            ageDifference <= 5 -> 85
            ageDifference <= 8 -> 70
            ageDifference <= 12 -> 55
            else -> 40
        }
    }

    private fun calculateCityScore(city: String): Int {
        return when {
            city.equals(REFERENCE_CITY, ignoreCase = true) -> 100
            isNearbyCity(city) -> 85
            isSameState(city) -> 75
            isMetroCity(city) -> 65
            else -> 50
        }
    }

    private fun isNearbyCity(city: String): Boolean {
        val nearbyCities = listOf(
            "Pune", "Nashik", "Aurangabad", "Thane", "Navi Mumbai",
            "Panvel", "Kalyan", "Vasai", "Virar", "Bhiwandi",
            "Ulhasnagar", "Ambernath", "Badlapur", "Karjat"
        )
        return nearbyCities.any { it.equals(city, ignoreCase = true) }
    }

    private fun isSameState(city: String): Boolean {
        val maharashtraCities = listOf(
            "Nagpur", "Solapur", "Ahmednagar", "Kolhapur", "Sangli",
            "Satara", "Jalgaon", "Akola", "Latur", "Dhule",
            "Nanded", "Parbhani", "Jalna", "Beed", "Osmanabad"
        )
        return maharashtraCities.any { it.equals(city, ignoreCase = true) }
    }

    private fun isMetroCity(city: String): Boolean {
        val metroCities = listOf(
            "Delhi", "Bangalore", "Hyderabad", "Chennai", "Kolkata",
            "Ahmedabad", "Surat", "Jaipur", "Lucknow", "Kanpur",
            "Indore", "Bhopal", "Patna", "Vadodara", "Coimbatore",
            "Ludhiana", "Kochi", "Visakhapatnam", "Agra", "Varanasi"
        )
        return metroCities.any { it.equals(city, ignoreCase = true) }
    }
}
