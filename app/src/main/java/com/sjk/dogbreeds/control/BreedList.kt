package com.sjk.dogbreeds.control

import com.sjk.dogbreeds.model.Breeds

//bit unorthodox here to use reflection
//it saves typing, more importantly, avoid typos

object BreedList {
    fun getAllBreeds(): List<String> {
        var breeds = mutableListOf<String>()
        Breeds::class.java.declaredFields.forEach {
            breeds.add(it.toString().substringAfterLast("."))
        }
        return breeds
    }
}