# Cara Penggunaan
- ```Import/Create Database``` pada server lokal 
  - Buatlah ```database``` dengan nama ```todo```
  - Buatlah ```tabel``` dengan nama ```pengguna_tb``` dengan kriteria dibawah ini :
    ```
        id : Int(11) (Auto increment)
        nama : VARCHAR(100) 
        alamat : VARCHAR(150)
        email : VARCHAR(100)
        image : VARCHAR(200)
        password : VARCHAR(150)
        id_device : VARCHAR(250)
    ```
- Clone repository API dari https://github.com/ramdhanjr11/RestAPI-TodoApp lalu simpan pada direktori ```C:\xampp\htdocs``` dengan nama folder ```todoAPI```
- Ubahlah ```Base URL``` pada direktori dibawah ini sesuai dengan ```IP Address``` milik anda : 
  - ```/di/NetworkModule.kt```
    ```kotlin
        @Provides
        fun provideApiService(client: OkHttpClient): ApiService {
          val retrofit = Retrofit.Builder()
              .baseUrl("http:/Masukan IP Address anda disini/todoAPI/")
              .client(client)
              .addConverterFactory(GsonConverterFactory.create())
              .build()

          return retrofit.create(ApiService::class.java)
        }
     ```
     
  - ```/adapter/HomeAdapter.kt```
    ```kotlin
       Glide.with(itemView.context)
                .load("http://Masukan IP Address anda disini/todoAPI/gambar/${data.image}")
                .into(binding.imgUser)
    ```
    
  - ```/ui/add/AddUpdateFragment.kt```
    ```kotlin
       Glide.with(requireContext()).load("http://Masukan IP Address anda disini/todoAPI/gambar/${dataFormulir.image}").into(binding.imgUser)
    ```

- Nyalakan ```Apache``` dan ```MySql``` pada ```XAMPP```
- Tambahkan data default pengguna melalui server lokal ```PhpMyAdmin``` agar dapat login 
- Run aplikasi melalui emulator atau pun device pada Android Studio

# Komponen yang digunakan dalam aplikasi

- Clean Architecture Pattern
- [Android Jetpack Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle#kts)
- [Dagger Hilt (Depedency Injection)](https://developer.android.com/training/dependency-injection/hilt-android?hl=id)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Retrofit](https://square.github.io/retrofit/)
- [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)
- [Glide](https://github.com/bumptech/glide)
- [Circle Image View by hdodenhof](https://github.com/hdodenhof/CircleImageView)
- [EasyPermission](https://github.com/vmadalin/easypermissions-ktx)
- [Image Picker](https://github.com/Dhaval2404/ImagePicker)
- [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/android/client?authuser=0)
- [Biometric Auth](https://developer.android.com/training/sign-in/biometric-auth)
