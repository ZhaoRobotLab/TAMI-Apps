package com.muse.data

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import java.io.InputStream

class DialogflowDataSource constructor(credentialsStream : InputStream) {
    private val credentials : ServiceAccountCredentials
            = ServiceAccountCredentials.fromStream(credentialsStream)

    fun detectIntentTexts(
        text: String,
        sessionId: String,
        languageCode: String
    ): String? {
        //val projectId = credentials.projectId
        //println(projectId)
        val sessionsSettings = SessionsSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build()
        val sessionsClient : SessionsClient =  SessionsClient.create(sessionsSettings)
        //println(projectId)
        val session = SessionName.of(credentials.projectId, sessionId)
        val textInput = TextInput.newBuilder()
            .setText(text).setLanguageCode(languageCode)
        val queryInput = QueryInput.newBuilder().setText(textInput).build()
        println("test")
        val response = sessionsClient.detectIntent(session, queryInput)
        println("test2")
        sessionsClient.shutdownNow()
        println(response.queryResult.fulfillmentText)
        return response.queryResult.fulfillmentText
    }
}