package com.example.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.model.Country
import com.example.countries.util.getProgressDrawable
import com.example.countries.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(val countries:ArrayList<Country>) : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>()
{
    fun updateCountries(newCountries : List<Country>)
    {
        countries.clear()
        countries.addAll(newCountries)

        // Refresh the whole list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false)
    )


    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int)
    {
        holder.bind(countries[position])
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private val progressDrawable = getProgressDrawable(view.context)
        private val imageView = view.imageView
        private val countryCapital = view.capital
        // view is itemcountry, name is the name we give to the textview
        private val countryName = view.name
        fun bind(country:Country)
        {
            countryName.text = country.countryName
            countryCapital.text = country.capital
            /*
                Extension function to extends it's functionality inside another class
             */
            imageView.loadImage(country.flag,progressDrawable)
        }
    }
}