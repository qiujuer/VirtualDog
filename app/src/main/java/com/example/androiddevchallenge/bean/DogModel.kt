package com.example.androiddevchallenge.bean

data class DogModel(
    // ID
    val id: Long = DOG_ID++,
    // 标题
    var title: String,
    // 描述
    var descriptor: String,
    // 图片：使用资源代替
    var picture: Int,
    // 品种
    var variety: String,
    // 年龄
    var age: String,
    // 性别
    var gender: String,
    // 体重
    var weight: String,
    // 品级
    var grade: String,
    // 绝育状态
    var terilize: String,
    // 售价
    var price: String,
    // 是否点赞关注
    var isLiked: Boolean,

    // 用户
    var user: UserModel
)

fun getBaseInfo(model: DogModel): String {
    return "品种：${model.variety}（${model.gender}）\n" +
            "年龄：${model.age}\n" +
            "体重：${model.weight}\n" +
            "绝育：${model.terilize}\n" +
            "品级：${model.grade}"
}

var DOG_ID = 1L