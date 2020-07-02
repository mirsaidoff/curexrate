package uz.mirsaidoff.curexrate.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.mirsaidoff.curexrate.data.remote.ApiService
import uz.mirsaidoff.curexrate.data.Repository
import uz.mirsaidoff.curexrate.data.local.MainDB
import uz.mirsaidoff.curexrate.ui.DetailsViewModel
import uz.mirsaidoff.curexrate.ui.MainViewModel

val mainModule = module {
    single { provideRetrofit() }
    single { provideApiService(get()) }
    single { providePreferences(get()) }
    single { provideMainDB(get()) }
    single { provideRepository(get(), get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}

fun providePreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.exchangeratesapi.io/")
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(
    ApiService::class.java
)

fun provideMainDB(context: Context) = MainDB.buildDatabase(context)

fun provideRepository(
    apiService: ApiService,
    preferences: SharedPreferences,
    db: MainDB
) = Repository(apiService, preferences, db)