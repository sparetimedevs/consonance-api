package com.sparetimedevs.consonance.config

import io.github.cdimascio.dotenv.dotenv

internal object EnvConfig {

    val dotenv = dotenv {
        directory = "."
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }
}