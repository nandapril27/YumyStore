package com.napa.foodstore.data.dummy

import com.napa.foodstore.model.Menu

interface DummyMenuDataSource {
    fun getMenuList(): List<Menu>
}

class DummyMenuDataSourceImpl() : DummyMenuDataSource {
    override fun getMenuList(): List<Menu> = listOf()
}
