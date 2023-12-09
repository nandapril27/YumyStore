package com.napa.foodstore.data.dummy

import com.napa.foodstore.model.Category

interface DummyCategoriesDataSource {
    fun getMenuCategories(): List<Category>
}

class DummyCategoriesDataSourceImpl() : DummyCategoriesDataSource {
    override fun getMenuCategories(): List<Category> =
        listOf()
}
