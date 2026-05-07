package com.example.frontend

import android.app.Application
import com.example.frontend.data.AppGraph

class FrontendApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppGraph.init(this)
    }
}

