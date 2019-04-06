/*
 * Copyright (c) Fatih Babacan 2019.
 * DarkPad is created by Fatih Babacan and released under the GPL3 license.
 * Please refer to the GPL3 license for additional information.
 */

package be.demillennial.darkpad.Notes

import java.util.*
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize




@Parcelize
data class Note(val id: Long, val title: String, val text: String): Parcelable{
    @IgnoredOnParcel
    var date: Date =Date()
}