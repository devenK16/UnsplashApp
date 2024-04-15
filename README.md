# Installation

* Download and install Android Studio.
* Clone the repository to your local machine:
    git clone https://github.com/devenK16/UnsplashApp.git.
* Open the project in Android Studio.

# Features
* MVVM Setup
* HILT
* Coroutines
* API Integration using Retrofit
* Image loading with Coroutines
* Memory Cache with LinkedHashMap and disk caching
* Error Handling

# Dependencies

* Coroutines
  <br>
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")<br>
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1") <br>

* Coroutine Lifecycle Scopes
 <br>
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")<br>
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

* Dagger - Hilt
  <br>
implementation("com.google.dagger:hilt-android:2.48")<br>
kapt("com.google.dagger:hilt-android-compiler:2.48")<br>
kapt("androidx.hilt:hilt-compiler:1.2.0")<br>
implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

* Retrofit
  <br>
implementation("com.squareup.retrofit2:retrofit:2.10.0")<br>
implementation("com.squareup.retrofit2:converter-gson:2.9.0")<br>
implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")<br>
implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

