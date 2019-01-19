package com.sparetimedevs.consonance.config

import java.io.File
import java.io.FileInputStream
import java.util.*

private const val USER_DIR = "user.dir"
private const val USER_HOME = "user.home"

private const val MONGODB_CONFIG_USERNAME = "mongodb.config.username"
private const val MONGODB_CONFIG_PASSWORD = "mongodb.config.password"
private const val MONGODB_CONFIG_HOST = "mongodb.config.host"
private const val MONGODB_CONFIG_PORT = "mongodb.config.port"
private const val MONGODB_CONFIG_AUTHSOURCE = "mongodb.config.authsource"
private const val MONGODB_CONFIG_DBNAME = "mongo.config.dbname"

object MongodbConfiguration {

    private val properties = Properties()
    private val pathFromUserDir = "${File.separator}src${File.separator}main${File.separator}resources${File.separator}config.properties"
    private val pathFromUserHome = "${File.separator}.con${File.separator}config${File.separator}config.properties"
    private val configFileFromProject = System.getProperty(USER_DIR) + pathFromUserDir
    private val configFileFromUserHome = System.getProperty(USER_HOME) + pathFromUserHome

    fun load(): String {
        readConfigFromProject()
        //TODO should make this also load values from readConfigFromUserHome() and override values when present in the user home config.
        return getMongodbConnectionString()
    }

    private fun readConfigFromProject() {
        readConfig(configFileFromProject)
    }

    private fun readConfigFromUserHome() {
        readConfig(configFileFromUserHome)
    }

    private fun readConfig(configFile: String) {
        val inputStream = FileInputStream(configFile)
        properties.load(inputStream)
    }

    private fun getMongodbConfigUsername(): String {
        return properties.getProperty(MONGODB_CONFIG_USERNAME)
    }

    private fun getMongodbConfigPassword(): String {
        return properties.getProperty(MONGODB_CONFIG_PASSWORD)
    }

    private fun getMongodbConfigHost(): String {
        return properties.getProperty(MONGODB_CONFIG_HOST)
    }

    private fun getMongodbConfigPort(): String {
        return properties.getProperty(MONGODB_CONFIG_PORT)
    }

    fun getMongodbDatabaseName(): String {
        return properties.getProperty(MONGODB_CONFIG_DBNAME)
    }

    private fun getMongodbConfigAuthSource(): String {
        return properties.getProperty(MONGODB_CONFIG_AUTHSOURCE)
    }

    private fun getMongodbConnectionString(): String {
        return "mongodb://${getMongodbConfigUsername()}:${getMongodbConfigPassword()}" +
                "@${getMongodbConfigHost()}:${getMongodbConfigPort()}/?authSource=${getMongodbConfigAuthSource()}"
    }
}
