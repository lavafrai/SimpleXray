package com.simplexray.an.data.model.routing

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class RouteModel(
    val uuid: Uuid,
    val userOrder: Int,
    val name: String,
    val enabled: Boolean,
    val domainRoutes: String,
    val ipRoutes: String,
    val port: Int,
    val sourcePort: Int,
    val network: RouteNetworkType,
    val source: String,
    val protocol: String,
    val outbound: RouteOutbound,
)