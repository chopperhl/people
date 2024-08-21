package com.chopperhl.people

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

val apolloClient =
    ApolloClient.Builder().serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index")
        .okHttpClient(
            OkHttpClient.Builder()
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .connectTimeout(8, TimeUnit.SECONDS)
                .build()
        ).build()