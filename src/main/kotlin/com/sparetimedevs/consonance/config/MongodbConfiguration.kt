package com.sparetimedevs.consonance.config

private const val DB_USERNAME = "mongodb.config.username"
private const val DB_PASSWORD = "mongodb.config.password"
private const val DB_HOST = "mongodb.config.host"
private const val DB_PORT = "mongodb.config.port"
private const val DB_AUTH_SOURCE = "mongodb.config.authsource"
private const val DB_NAME = "mongodb.config.dbname"
private const val AUTH_SOURCE = "authSource"
private const val MONGO_DB = "mongodb"


object MongodbConfiguration {

    private val dotenv = EnvConfig.dotenv

    fun getDbName(): String {
        return dotenv[DB_NAME] ?: ""
    }

    private fun getDbUsername(): String {
        return dotenv[DB_USERNAME] ?: ""
    }

    private fun getDbPassword(): String {
        return dotenv[DB_PASSWORD] ?: ""
    }

    private fun getDbHost(): String {
        return dotenv[DB_HOST] ?: ""
    }

    private fun getDbPort(): String {
        return dotenv[DB_PORT] ?: ""
    }

    private fun getDbAuthSource(): String {
        return dotenv[DB_AUTH_SOURCE] ?: ""
    }

    fun getDbConnectionUrl(): String {
        return "$MONGO_DB://${getDbUsername()}:${getDbPassword()}@${getDbHost()}:${getDbPort()}/?$AUTH_SOURCE=${getDbAuthSource()}"
    }

}
