package com.mpierucci.android.redux.ristretto.mockwebserver

import okhttp3.mockwebserver.MockResponse
import java.io.InputStreamReader


fun createResponse(
    code: Int = 200,
    filePath: String? = null
): MockResponse {
    return MockResponse().setResponseCode(code).apply {
        if (filePath != null) loadBodyFromResource(filePath)
    }
}

private fun MockResponse.loadBodyFromResource(filePath: String): MockResponse =
    apply {
        val bodyString =
            InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(filePath)!!)
                .use { it.readText() }

        setBody(bodyString)
    }