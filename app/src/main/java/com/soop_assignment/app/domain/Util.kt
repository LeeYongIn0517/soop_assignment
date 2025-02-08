package com.soop_assignment.app.domain

fun extractNextKey(linkHeader: String): Int? {
    val nextUrl = linkHeader
        .split(",")
        .find { it.contains("rel=\"next\"") }
        ?.substringAfter("<")
        ?.substringBefore(">")

    val nextKey = nextUrl?.let { url ->
        Regex("page=(\\d+)").find(url)?.groupValues?.get(1)?.toInt()
    }
    return nextKey
}

fun extractLastKey(linkHeader: String): Int? {
    val nextUrl = linkHeader
        .split(",")
        .find { it.contains("rel=\"last\"") }
        ?.substringAfter("<")
        ?.substringBefore(">")

    val nextKey = nextUrl?.let { url ->
        Regex("page=(\\d+)").find(url)?.groupValues?.get(1)?.toInt()
    }
    return nextKey
}
