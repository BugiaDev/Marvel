package com.bugiadev.marvel.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiadev.marvel.ApplicationComponentProvider
import com.bugiadev.marvel.R
import com.bugiadev.marvel.ui.viewmodel.NoNetworkException
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.math.BigInteger
import java.net.SocketTimeoutException
import java.security.MessageDigest

fun Int.Companion.empty() = 0

fun String.Companion.empty() = ""

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun ImageView.loadFromUrl(url: String, context: Context) =
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.marvel_placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun <T> Single<T>.mapNetworkErrors(): Single<T> =
        this.onErrorResumeNext { error ->
            when (error) {
                is SocketTimeoutException -> Single.error(NoNetworkException(error))
                else -> Single.error(error)
            }
        }

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

val Activity.injector
get() = (application as ApplicationComponentProvider).provideApplicationComponent()

