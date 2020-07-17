package net.simplifiedcoding.di

import net.simplifiedcoding.data.network.MoviesApi
import net.simplifiedcoding.data.repositories.MoviesRepository
import net.simplifiedcoding.ui.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule : Module = module {
    //Singleton
   single {MoviesApi()}

    single { MoviesRepository(get())}

//    factory {  }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
}
