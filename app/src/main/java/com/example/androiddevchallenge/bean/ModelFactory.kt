/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.bean

import com.example.androiddevchallenge.R

object ModelFactory {
    private val mAvatarImages = arrayOf(
        R.drawable.ic_sample_avatar_1,
        R.drawable.ic_sample_avatar_2,
        R.drawable.ic_sample_avatar_3,
        R.drawable.ic_sample_avatar_4
    )

    private val mVarietyImages = arrayOf(
        R.drawable.img_variety_0,
        R.drawable.img_variety_1,
        R.drawable.img_variety_2,
        R.drawable.img_variety_3,
        R.drawable.img_variety_4,
        R.drawable.img_variety_5,
        R.drawable.img_variety_6,
        R.drawable.img_variety_7,
        R.drawable.img_variety_8,
        R.drawable.img_variety_9
    )

    private val mDogImages = arrayOf(
        R.drawable.img_dogs_1,
        R.drawable.img_dogs_2,
        R.drawable.img_dogs_3,
        R.drawable.img_dogs_4,
        R.drawable.img_dogs_5,
        R.drawable.img_dogs_6,
        R.drawable.img_dogs_7,
        R.drawable.img_dogs_8,
        R.drawable.img_dogs_9,
        R.drawable.img_dogs_10
    )
    private val mUserNames = arrayOf(
        "只在身后",
        "怜悯与她",
        "孤独星球",
        "梦沁人心",
        "清风弄发",
        "十号爱人",
        "你的姿态"
    )

    private val mVarietyTexts = arrayOf(
        "比格猎犬",
        "柯基犬",
        "惠比特犬",
        "腊肠犬",
        "边牧犬",
        "柴犬",
        "中华田园犬",
        "刚毛猎狐梗",
        "哈士奇",
        "中华细犬"
    )

    private val mTitles = arrayOf(
        "七个月大小王子找麻麻",
        "可爱猫猫待领养",
        "小泰迪们求领养啦",
        "北京2岁短毛狗狗",
        "短腿柯基找爱心领养",
        "无锡成年哈士奇",
        "免费领养白色灰斑",
        "哈士奇等待拆家",
        "可爱宝宝等你来",
        "诚信给旺仔找下家"
    )

    private val mCache: HashMap<Long, DogModel> = HashMap()

    fun sampleDogModel(): DogModel {
        mDogImages.shuffle()
        mTitles.shuffle()
        mVarietyTexts.shuffle()
        return DogModel(
            title = mTitles[0],
            descriptor = "奶狗性格特别亲人，活泼，不挑食。正在长牙，喜欢啃东西。希望找到能耐心教导的主人。\n" +
                "领养人要求：有耐心和责任感，能够做到耐心教导，不打不弃养。科学喂养，生病及时就医，定期打针驱虫，不因结婚生育出差搬家等原因抛弃小狗。如果实在不能养了，也请及时联系我。需要回访。",
            picture = mDogImages[0],
            variety = mVarietyTexts[0],
            age = (1..10).random().toString() + "岁",
            gender = when ((0..1).random() == 1) {
                true -> "母"
                else -> "公"
            },
            weight = (1..20).random().toString() + "Kg",
            grade = "血统级",
            terilize = when ((0..1).random() == 1) {
                true -> "是"
                else -> "否"
            },
            price = "¥" + (200..999).random().toString(),
            isLiked = (0..1).random() == 1,
            user = sampleUserModel()
        )
    }

    private fun sampleUserModel(): UserModel {
        mUserNames.shuffle()
        mAvatarImages.shuffle()
        return UserModel(
            name = mUserNames[0],
            avatar = mAvatarImages[0]
        )
    }

    fun sampleVarietyModels(): List<VarietyModel> {
        var index = 0
        return mVarietyImages.map {
            VarietyModel(mVarietyTexts[index++], it)
        }
    }

    fun sampleHottestModels(): List<DogModel> {
        return (1..10).map {
            val model = sampleDogModel()
            mCache[model.id] = model
            model
        }
    }

    fun sampleNewestModels(): List<DogModel> {
        return (1..30).map {
            val model = sampleDogModel()
            mCache[model.id] = model
            model
        }
    }

    fun find(id: Long): DogModel? {
        return mCache[id]
    }
}
