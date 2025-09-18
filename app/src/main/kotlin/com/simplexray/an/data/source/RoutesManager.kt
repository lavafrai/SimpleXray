package com.simplexray.an.data.source

import android.content.Context
import android.util.Log
import com.simplexray.an.data.model.routing.RouteModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RoutesManager(context: Context) {
    val routesFile: File
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
    }

    init {
        val filesDir = context.filesDir
        this.routesFile = File(filesDir, ROUTES_FILE_NAME)
        Log.d(TAG, "Routes file path: " + routesFile.absolutePath)
    }

    fun loadRoutes(): List<RouteModel> {
        if (!routesFile.exists()) {
            return emptyList()
        }
        return json.decodeFromString(routesFile.readText())
    }

    private fun writeRoutes(routes: List<RouteModel>) {
        routesFile.writeText(json.encodeToString(routes))
    }

    fun changeUserOrder(routeUuid: Uuid, newOrder: Int) {
        var routes = loadRoutes().toMutableList()
        val routeIndex = routes.indexOfFirst { it.uuid == routeUuid }
        if (routeIndex == -1) {
            Log.w(TAG, "Route with UUID $routeUuid not found")
            return
        }
        val route = routes.removeAt(routeIndex)
        routes.add(newOrder, route)

        routes = routes.mapIndexed { index, r ->
            r.copy(userOrder = index)
        }.toMutableList()

        writeRoutes(routes)
    }

    companion object {
        private const val TAG = "RoutesManager"
        private const val ROUTES_FILE_NAME = "app_log.txt"
    }
}