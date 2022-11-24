package br.edu.ifsp.ads.pdm.mycontacts.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @NonNull
    var name: String,
    var item: String,
    @NonNull
    var amountPaid: Double,
    var amountToPay: Double?,
    var amountReceivable: Double?,

    ): Parcelable
