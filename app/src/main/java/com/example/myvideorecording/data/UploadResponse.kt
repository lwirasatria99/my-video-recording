package com.example.myvideorecording.data

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("error")
    val errorMessage: ErrorMessage? = null,
    @SerializedName("asset_id")
    val assetId: String? = null
)

data class ErrorMessage(
    val message: String
)