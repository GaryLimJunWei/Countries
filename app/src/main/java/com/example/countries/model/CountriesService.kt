package com.example.countries.model

import com.example.countries.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountriesService
{

    @Inject
    lateinit var api : CountriesApi

    init
    {
        // Dagger will inject the component of the module into here
        DaggerApiComponent.create().inject(this)
    }

    /*
        This function help to get the data we need.
     */
    fun getCountries() : Single<List<Country>>
    {
        return api.getCountries()
    }
}