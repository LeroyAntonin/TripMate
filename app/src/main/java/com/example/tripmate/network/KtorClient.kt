package com.example.tripmate.network

import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.HttpClient as KtorHttpClient

// Client Ktor identique à celui de Netfloux
object KtorClient {
    private const val TIMEOUT_MILLIS = 15_000L

    @PublishedApi
    internal val client: KtorHttpClient by lazy {
        KtorHttpClient(OkHttp) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_MILLIS
                connectTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend inline fun <reified T> get(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T = client.get(url, block).body()

    suspend inline fun <reified T> post(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T = client.post(url, block).body()

    suspend inline fun <reified T> put(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T = client.put(url, block).body()

    suspend inline fun <reified T> patch(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T = client.patch(url, block).body()

    suspend fun delete(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ) {
        // Une suppression peut répondre sans contenu.
        client.delete(url, block)
    }
}
