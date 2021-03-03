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

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.bean.DogModel
import com.example.androiddevchallenge.bean.ModelFactory
import com.example.androiddevchallenge.bean.VarietyModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.TextPrice
import com.example.androiddevchallenge.ui.theme.TextPrimary
import com.example.androiddevchallenge.ui.theme.TextPrimaryLight
import com.example.androiddevchallenge.ui.theme.TextSecondary
import com.example.androiddevchallenge.ui.theme.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(
                    function = object : Function1<DogModel, Void?> {
                        override fun invoke(p: DogModel): Void? {
                            jump(p)
                            return null
                        }
                    }
                )
            }
        }
    }

    private fun jump(model: DogModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.KEY, model.id)
        startActivity(intent)
    }
}

// Start building your app here!
@Composable
fun MyApp(function: Function1<DogModel, Void?>) {
    Surface(color = MaterialTheme.colors.background) {
        MainRoot(function)
    }
}

@Composable
fun MainRoot(function: Function1<DogModel, Void?>) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_sample_banner),
            contentDescription = "banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(210.dp)
        )
        LazyColumn(
            Modifier
                .padding(top = 210.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFF8F8F8))
        ) {
            item {
                MainTitleView("品种")
                MainVarietyView()
                MainTitleView("热门")
                MainHottestView(function)
                MainTitleView("最新")
            }

            // Data
            val models = ModelFactory.sampleNewestModels()
            models.forEach {
                item {
                    MainNewestItemView(it, function)
                    Spacer(Modifier.size(5.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            MainBottomNavView()
        }
    }
}

@Composable
fun MainTitleView(title: String) {
    Text(
        modifier = Modifier.padding(20.dp, 10.dp),
        text = title,
        color = TextPrimary,
        style = typography.subtitle2
    )
}

@Composable
fun MainVarietyView() {
    val models: List<VarietyModel> = ModelFactory.sampleVarietyModels()
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        Spacer(Modifier.size(12.5.dp))

        models.forEach {
            MainVarietyItemView(it.picture, it.variety)
        }

        Spacer(Modifier.size(12.5.dp))
    }
}

@Composable
fun MainHottestView(function: Function1<DogModel, Void?>) {
    val models = ModelFactory.sampleHottestModels()
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        Spacer(Modifier.size(12.5.dp))

        models.forEach {
            MainHottestItemView(it, function)
        }

        Spacer(Modifier.size(12.5.dp))
    }
}

@Composable
fun MainBottomNavView() {
    Image(
        painter = painterResource(id = R.drawable.ic_sample_bottom_nav),
        contentDescription = "navigation",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun MainVarietyItemView(img: Int, text: String) {
    Column(
        modifier = Modifier.padding(7.5.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = text,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color(0xFFFFFFFF)),
        )
        Spacer(Modifier.size(5.dp))
        Text(
            text = text,
            color = TextSecondary,
            style = typography.caption
        )
    }
}

@Composable
fun MainHottestItemView(model: DogModel, function: Function1<DogModel, Void?>) {
    Box(
        modifier = Modifier
            .padding(7.5.dp, 0.dp)
            .size(140.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { function.invoke(model) }
    ) {
        Image(
            painter = painterResource(id = model.picture),
            contentDescription = "DogPicture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        )
        Text(
            text = model.title,
            color = TextPrimaryLight,
            style = typography.caption,
            modifier = Modifier
                .padding(top = 110.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0x59000000))
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(8.dp, 0.dp)
        )
        Image(
            painter = painterResource(id = if (model.isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off),
            contentDescription = "like",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(start = 110.dp, top = 5.dp)
                .size(25.dp)
        )
    }
}

@Composable
fun MainNewestItemView(model: DogModel, function: Function1<DogModel, Void?>) {
    Card(
        modifier = Modifier
            .padding(10.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { function.invoke(model) },
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = Color(color = 0xFFFFFFFF)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = model.picture),
                contentDescription = "picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(Modifier.size(15.dp))
            Column {
                Text(
                    text = model.title,
                    color = TextPrimary,
                    style = typography.body1,
                )
                Text(
                    text = model.variety + " " + model.gender,
                    color = TextSecondary,
                    style = typography.caption
                )
                Text(
                    text = "发布人: " + model.user.name,
                    color = TextSecondary,
                    style = typography.caption
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(if (model.isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off),
                    contentDescription = "like",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color(0xFF2A2A2A)),
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = model.price,
                    color = TextPrice,
                    style = typography.h6,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainNewestItemView() {
    MainNewestItemView(
        ModelFactory.sampleDogModel(),
        object : Function1<DogModel, Void?> {
            override fun invoke(p: DogModel): Void? {
                return null
            }
        }
    )
}
