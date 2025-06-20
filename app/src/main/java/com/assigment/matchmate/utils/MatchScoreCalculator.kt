package com.assigment.matchmate.utils

object MatchScoreCalculator {

    private const val MY_AGE = 28

    fun calculateScore(age: Int, city: String): Int {
        val agePoints = getAgeCompatibility(age)
        val locationPoints = getLocationScore(city)

        return ((agePoints * 0.6) + (locationPoints * 0.4)).toInt()
    }

    private fun getAgeCompatibility(theirAge: Int): Int {
        val diff = kotlin.math.abs(theirAge - MY_AGE)

        if (diff <= 2) return 100
        if (diff <= 5) return 85
        if (diff <= 8) return 70
        if (diff <= 12) return 55
        return 40
    }

    private fun getLocationScore(city: String): Int {
        val cityLower = city.lowercase()

        if (cityLower == "helsinki") return 100

        // close to home
        if (isHelsinkiArea(cityLower)) return 90

        // decent sized cities
        if (isBigCity(cityLower)) return 80

        // smaller places but still ok
        if (isDecentPlace(cityLower)) return 70

        // same region at least
        if (isUusimaa(cityLower)) return 60

        return 50
    }

    private fun isHelsinkiArea(city: String): Boolean {
        val nearby = arrayOf(
            "espoo", "vantaa", "kauniainen", "kerava", "kirkkonummi",
            "järvenpää", "tuusula", "sipoo", "nurmijärvi", "hyvinkää",
            "porvoo", "lohja", "karkkila", "vihti"
        )
        return city in nearby
    }

    private fun isBigCity(city: String): Boolean {
        val cities = arrayOf(
            "tampere", "turku", "oulu", "jyväskylä", "lahti",
            "kuopio", "pori", "joensuu", "lappeenranta", "hämeenlinna",
            "vaasa", "seinäjoki", "rovaniemi", "mikkeli", "kotka",
            "salo", "kouvola", "rauma", "tornio", "savonlinna"
        )
        return city in cities
    }

    private fun isDecentPlace(city: String): Boolean {
        // bunch of smaller cities that are still fine
        val places = arrayOf(
            "kajaani", "iisalmi", "ylivieska", "raahe", "kokkola",
            "pietarsaari", "kemi", "imatra", "riihimäki", "forssa",
            "valkeakoski", "nokia", "ylöjärvi", "kangasala", "lempäälä",
            "naantali", "kaarina", "lieto", "mynämäki", "siilinjärvi",
            "töysä", "pälkäne", "jomala", "tohmajärvi", "halsua",
            "posio", "taivalkoski", "lumparland"
        )
        return city in places
    }

    private fun isUusimaa(city: String): Boolean {
        val uusimaaPlaces = arrayOf(
            "hanko", "inkoo", "raasepori", "loviisa", "askola",
            "lapinjärvi", "myrskylä", "pukkila", "mäntsälä", "pornainen"
        )
        return city in uusimaaPlaces
    }
}