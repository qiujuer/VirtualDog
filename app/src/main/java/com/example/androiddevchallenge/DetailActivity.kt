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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.bean.DogModel
import com.example.androiddevchallenge.bean.ModelFactory
import com.example.androiddevchallenge.bean.getBaseInfo
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.TextPrice
import com.example.androiddevchallenge.ui.theme.TextPrimary
import com.example.androiddevchallenge.ui.theme.TextPrimaryLight
import com.example.androiddevchallenge.ui.theme.TextSecondary
import com.example.androiddevchallenge.ui.theme.typography

class DetailActivity : AppCompatActivity() {
    companion object {
        const val KEY = "ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model = intent.extras?.getLong(KEY)?.let {
            ModelFactory.find(it)
        }

        if (model == null) {
            finish()
            return
        }

        setContent {
            MyTheme {
                MyAppForDetail(model) {
                    finish()
                }
            }
        }
    }
}

@Composable
fun MyAppForDetail(model: DogModel, onBackClick: () -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        DetailRoot(model, onBackClick)
    }
}

@Composable
fun DetailRoot(model: DogModel, onBackClick: () -> Unit) {
    Box {
        Image(
            painter = painterResource(id = model.picture),
            contentDescription = "preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(250.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(185.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sample_indicator),
                contentDescription = "indicator",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(46.dp, 6.dp)
            )
        }
        Card(
            modifier = Modifier
                .padding(top = 200.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            backgroundColor = Color(0xFFF8F8F8)
        ) {
            Column {
                Spacer(Modifier.size(15.dp))
                DetailBaseInfoView(model)
                Spacer(Modifier.size(20.dp))
                DetailOwnerUserCard(model)
                Spacer(Modifier.size(20.dp))
                DetailContentInfoView(model)
                Spacer(Modifier.size(80.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            DetailBottomOptionView()
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            DetailNavigationView(onBackClick)
        }
    }
}

@Composable
fun DetailNavigationView(onClick: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.ic_icon_back),
        contentDescription = "back",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(55.dp)
            .clip(CircleShape)
            .clickable { onClick.invoke() }
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            painter = painterResource(R.drawable.ic_icon_share),
            contentDescription = "share",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(55.dp)
        )
    }
}

@Composable
fun DetailBaseInfoView(model: DogModel) {
    Column(modifier = Modifier.padding(20.dp, 0.dp)) {
        Text(
            text = model.price,
            color = TextPrice,
            style = typography.h5,
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = getBaseInfo(model),
            color = TextPrimary,
            style = typography.body1,
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "疫苗：第1针  2019-01-23  辉瑞卫佳伍",
            color = TextSecondary,
            style = typography.caption,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "驱虫：第1次  2019-01-23  百球清",
            color = TextSecondary,
            style = typography.caption,
        )
    }
}

@Composable
fun DetailContentInfoView(model: DogModel) {
    Column(modifier = Modifier.padding(20.dp, 0.dp)) {
        Text(
            text = model.title,
            color = TextPrimary,
            style = typography.subtitle1,
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = model.descriptor,
            color = TextPrimary,
            style = typography.body1,
        )
    }
}

@Composable
fun DetailOwnerUserCard(model: DogModel) {
    Card(
        modifier = Modifier.padding(16.dp, 0.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = Color(color = 0xFFFFFFFF)
    ) {
        Row(
            modifier = Modifier.padding(20.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = model.user.avatar),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.size(10.dp))
            Column {
                Text(
                    text = model.user.name,
                    color = TextPrimary,
                    style = typography.subtitle2,
                )
                Text(
                    text = "狗狗主人",
                    color = TextSecondary,
                    style = typography.caption,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                arrayOf(
                    R.drawable.ic_contact_phone,
                    R.drawable.ic_contact_chat,
                    R.drawable.ic_contact_video
                ).forEach {
                    Spacer(Modifier.size(6.dp))
                    Image(
                        painter = painterResource(it),
                        contentDescription = "contact",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailBottomOptionView() {
    Row(modifier = Modifier.padding(20.dp, 15.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_like_big_on),
            contentDescription = "love",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(color = Color(0xFF00A0F0))
        )
        Spacer(Modifier.size(15.dp))
        Text(
            text = "我要领养",
            color = TextPrimaryLight,
            style = typography.button,
            modifier = Modifier
                .requiredHeight(45.dp)
                .fillMaxWidth()
                .background(color = Color(0xFF00A0F0), CircleShape)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
fun PreviewDetailOwnerUserCard() {
    DetailOwnerUserCard(ModelFactory.sampleDogModel())
}
