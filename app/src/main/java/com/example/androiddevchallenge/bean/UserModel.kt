package com.example.androiddevchallenge.bean

data class UserModel(
    // ID
    val id: Long = USER_ID++,
    // 名称
    var name: String,
    // 头像：使用资源代替
    var avatar: Int
)

var USER_ID = 1L
