/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad

import android.content.Context
import be.demillennial.darkpad.Data.DbHelper

val Context.database: DbHelper
    get() = DbHelper.getInstance(applicationContext)