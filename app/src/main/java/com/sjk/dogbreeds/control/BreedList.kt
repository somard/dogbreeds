package com.sjk.dogbreeds.control

import com.sjk.dogbreeds.model.Breeds

//unorthodox usage of reflection to deal with list of list
// ::class.java.declaredFields return all the fields, each one like this:
// private final java.util.List Breeds.affenpinscher
// taking portion after the last "." gives the bread name
//the loop collects all the breeds,
// thus it avoids errors if one does manual copy & paste

object BreedList {
    fun getAllBreeds(): List<String> {
        var breeds = mutableListOf<String>()
        Breeds::class.java.declaredFields.forEach {
            breeds.add(it.toString().substringAfterLast("."))
        }
        return breeds
    }
}