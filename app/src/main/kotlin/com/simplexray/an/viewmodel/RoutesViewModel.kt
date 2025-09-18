package com.simplexray.an.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simplexray.an.data.model.routing.RouteModel
import com.simplexray.an.data.source.RoutesManager
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val TAG = "LogViewModel"

@OptIn(FlowPreview::class, ExperimentalUuidApi::class)
class RoutesViewModel(application: Application): AndroidViewModel(application) {
    private val routesManager = RoutesManager(application)

    private val _routes = MutableStateFlow<List<RouteModel>>(emptyList())
    val routes: StateFlow<List<RouteModel>> = _routes

    fun loadRoutes() {
        _routes.value = routesManager.loadRoutes()
    }

    fun changeUserOrder(routeUuid: Uuid, newOrder: Int) {
        routesManager.changeUserOrder(routeUuid, newOrder)
        loadRoutes()
    }
}

class RoutesViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoutesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
