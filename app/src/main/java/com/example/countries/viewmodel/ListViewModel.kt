package com.example.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.di.DaggerApiComponent
import com.example.countries.model.CountriesService
import com.example.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/*
    ViewModel will have 3 variable, countries,countryLoadError and loading
    LiveData , when these variable update those subscriber will not notified.
 */
class ListViewModel : ViewModel()
{
    /*
        Double injection, this class is injected with a injected class
     */
    @Inject
    lateinit var countriesService : CountriesService

    init
    {
        DaggerApiComponent.create().inject(this)
    }

    // Anyone who subscribe to these LiveData will be notify when this is updated
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    fun refresh()
    {
        fetchCountries()
    }


    private fun fetchCountries()
    {

        loading.value = true

        /*
            In order not to BLOCK all connection while we are fetching data from the API,
            we have to do it all in a background Thread.
            subscribeOn a new Thread
            But getting the information on the main thread
            subscribeWith will define the functionality that we will do when we get the data
         */

        /*
            RxJavaAdapter is to transform the country variable
            to something that is observable
         */

        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Country>>()
                {
                    override fun onSuccess(value: List<Country>?)
                    {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?)
                    {
                        countryLoadError.value = true
                        loading.value = false
                    }

                })
        )


    }

    override fun onCleared()
    {
        super.onCleared()
        disposable.clear()
    }
}