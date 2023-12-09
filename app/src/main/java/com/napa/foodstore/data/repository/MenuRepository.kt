package com.napa.foodstore.data.repository

import com.napa.foodstore.data.network.api.datasource.FoodStoreDataSource
import com.napa.foodstore.data.network.api.model.category.toCategoryList
import com.napa.foodstore.data.network.api.model.menu.toMenuList
import com.napa.foodstore.model.Category
import com.napa.foodstore.model.Menu
import com.napa.foodstore.utils.ResultWrapper
import com.napa.foodstore.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val apiDataSource: FoodStoreDataSource
) : MenuRepository {

    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            apiDataSource.getMenus(category).data?.toMenuList() ?: emptyList()
        }
    }
}
