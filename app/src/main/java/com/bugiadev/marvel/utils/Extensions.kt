package com.bugiadev.marvel.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.net.SocketTimeoutException

/*
fun <T> Single<T>.mapNetworkErrors(): Single<T> =
        this.onErrorResumeNext { error ->
            when (error) {
                is SocketTimeoutException -> Single.error(NoNetworkException(error))
                else -> Single.error(error)
            }
        }
 */

fun <T> Single<T>.prepareForUI(): Single<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.subscribe(
    disposables: CompositeDisposable,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit = {}
) {
    disposables.add(subscribe(onSuccess, onError))
}

@Suppress("NOTHING_TO_INLINE")
inline fun Retrofit.Builder.delegatingCallFactory(
    delegate: dagger.Lazy<OkHttpClient>
): Retrofit.Builder =
    callFactory {
        delegate.get().newCall(it)
    }

inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline factory: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            factory() as T
    }
}

/*
val Activity.injector
get() = (application as ApplicationComponentProvider).provideApplicationComponent()
 */

