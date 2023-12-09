package com.napa.foodstore.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.napa.foodstore.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    suspend fun isUsingGridPref(): Boolean
    fun isUsingGridPrefFlow(): Flow<Boolean>
    suspend fun setUsingGridPref(isUsingGrid: Boolean)
}

class UserPreferenceDataSourceImpl(
    private val preferenceHelper: PreferenceDataStoreHelper
) : UserPreferenceDataSource {
    override suspend fun isUsingGridPref(): Boolean {
        return preferenceHelper.getFirstPreference(PREF_USING_GRID, false)
    }

    override fun isUsingGridPrefFlow(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_USING_GRID, false)
    }

    override suspend fun setUsingGridPref(isUsingGrid: Boolean) {
        return preferenceHelper.putPreference(PREF_USING_GRID, isUsingGrid)
    }

    companion object {
        val PREF_USING_GRID = booleanPreferencesKey("PREF_USING_GRID")
    }
}
