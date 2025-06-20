# MatchMate - Matrimonial Android App

## Screen Recording


https://github.com/user-attachments/assets/4f273274-e242-46e4-b461-1c11127e99cc




## 📱 Project Overview

**MatchMate** is an Android app that simulates a Matrimonial App by displaying
match cards similar to Shaadi.com's format. The app should fetch user data, allow
Accept/Decline interactions, persist the data locally, and work offline. Additionally,
demonstrate original problem-solving and thoughtful design decisions.

---

## ✨ Key Features

- **Accept / Decline Options**: Simple, interactive buttons give instant feedback on user choices.
- **Offline-First**: The app works even when there’s no internet connection.
- **Local Storage**: Profile data is stored locally using Room database.
- **Match Scoring**: Profiles are ranked based on age and location compatibility.
---

## 🧠 Architecture

The app follows the **MVVM (Model-View-ViewModel)** pattern for better maintainability and scalability.

- **Separation of Concerns**: Keeps UI, business logic, and data layers independent.
- **Lifecycle Awareness**: ViewModels handle configuration changes automatically.
- **Improved Testability**: ViewModels can be tested in isolation.
- **LiveData + Data Binding**: Keeps UI in sync with data changes seamlessly.

---

## 📊 Match Score Algorithm

A simple, transparent scoring system ranks matches based on:

### 🎂 Age Proximity (60% weight)
- 0–2 years difference → 100 points  
- 3–5 years → 85 points  
- 6–8 years → 70 points  
- 9–12 years → 55 points  
- 12+ years → 40 points  

### 📍 Location Match (40% weight)
- Same city → 100 points  
- Different city → 90-60 points  
---

## 🎓 Additional Fields

To improve matchmaking relevance, two key fields were added:

- **Education**: A major factor in compatibility, especially in matrimonial contexts.
- **Occupation**: Gives insights into lifestyle and social background.
---

## 🛠 Libraries & Tools

- **Retrofit + Gson**: REST API and JSON parsing  
- **Room**: Local database  
- **Glide**: Fast image loading and caching  
- **Material Design Components**: For a modern UI look and feel  
- **LiveData**: Reactive UI updates  
- **RecyclerView**: Smooth, efficient list rendering  
---

## 🌐 Offline Strategy

- **Local-First**: Data is always available from the local Room DB.
- **Smart Caching**: API responses are cached for offline use.
- **Graceful Degradation**: App remains usable even with no connectivity.
- **User Feedback**: Network errors are handled with clear, user-friendly messages.

---

## 🚨 Error Handling

- **Retry with Backoff**: Automatic retries when a network call fails.
- **Loading States**: Visual indicators show progress during data fetch.
- **Fallback Logic**: Switches to offline data if needed.
- **User Prompts**: Helpful error messages and retry options are shown.

---

## 🚀 Future Improvements

Given more time, here’s what could be added:

- **Advanced Filters**: Filter by age, location, education, etc.
- **Smarter Matching**: Use ML models to score compatibility.
- **In-App Chat**: Real-time messaging between matched users.
- **In-App Calling**: We can also integrate in-app calling using agro calling.
- **Push Notifications**: Get notified when a new match is available.
---
