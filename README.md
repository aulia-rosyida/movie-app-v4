# movie-app-v4
Movie app v4 is an app to show popular movies and make list for your favorited one

# language programming
Kotlin

# Implementation

3 main features:

1. Theme and API using open source TMDB
- Terdapat halaman list item, detail item, dan list favorite (menggunakan database).
- Semua fitur berjalan dengan lancar tanpa ada force close.

2. Menerapkan Modularization
- Membuat 1 Android Library untuk core dan 1 dynamic feature untuk favorite

3. Menerapkan Clean Architecture
- Tidak melanggar dependency rule dari Clean Architecture.
- Memisahkan model untuk domain dengan model untuk data (separation model).


4. Menerapkan Dependency Injection
- Menggunakan library Dependency Injection :Koin
- Menerapkan dengan tepat untuk melakukan injection untuk semua fungsionalitas.


4. Menerapkan Reactive Programming
- Menggunakan Flow 
- Menerapkan dengan tepat untuk mengambil data dari network dan database.
