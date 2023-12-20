package com.bangkit.sehatin.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Detail(

	@field:SerializedName("Lemak")
	val lemak: String? = null,

	@field:SerializedName("Karbohidrat")
	val karbohidrat: String? = null,

	@field:SerializedName("Protein")
	val protein: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("kalori")
	val kalori: String? = null,

	@field:SerializedName("porsi")
	val porsi: String? = null,

	@field:SerializedName("detail")
	val detail: Detail? = null
) : Parcelable
