package com.abhishek.techdemoapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.techdemoapp.adapter.ImageAdapter
import com.abhishek.techdemoapp.model.*
import com.abhishek.techdemoapp.utils.StickyHeaderItemDecoration
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var rvsections: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var imgAdapter: ImageAdapter
    var imageListD: ArrayList<Any> = ArrayList<Any>()
    var myImages = arrayOf<Int>(
        R.drawable.nature_flowers, R.drawable.red_flower, R.drawable.white_flower,
        R.drawable.white_flower, R.drawable.red_flower, R.drawable.nature_flowers,
        R.drawable.nature_flowers, R.drawable.white_flower, R.drawable.red_flower,
        R.drawable.red_flower, R.drawable.nature_flowers, R.drawable.white_flower,
        R.drawable.white_flower, R.drawable.red_flower, R.drawable.nature_flowers,
        R.drawable.nature_flowers, R.drawable.white_flower, R.drawable.red_flower,
        R.drawable.red_flower, R.drawable.nature_flowers, R.drawable.white_flower
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView(){
        rvsections = findViewById(R.id.rv_sections)
        gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (imgAdapter.getItemViewType(position)) {
                    ConstantsData.TEXT_SECTION -> 2
                    ConstantsData.ONE_IMG_SECTION -> 1
                    else -> 2
                }
            }
        }
        rvsections.layoutManager = gridLayoutManager
        rvsections.setHasFixedSize(true)
        addData()
    }

    private fun addData() {
        var singleImageList: ArrayList<DemoImg> = ArrayList<DemoImg>()
        var multiImageList: ArrayList<Demo2Img> = ArrayList<Demo2Img>()
        var textList: ArrayList<DemoText> = ArrayList<DemoText>()

        for (it in 0..myImages.size){
            if(it<myImages.size) {
                if (it % 2 == 0) {
                    var demo2Img = Demo2Img()
                    demo2Img!!.image2 = myImages[it]
                    multiImageList.add(demo2Img)
                } else {
                    var demoImg = DemoImg()
                    demoImg!!.image = myImages[it]
                    singleImageList.add(demoImg)
                }
            }
        }

        var k: Int = 0;
        for (i in 0..myImages.size){
            if (i%3==0) {
                k += 1;
                var demoText = DemoText();
                demoText.txtSection = "Section $k"
                textList.add(demoText)
            }
        }

        for (i in 0..textList.size) {
            if (i < textList.size) {
                imageListD.add(textList[i])
                imageListD.add(singleImageList[i+1])
                imageListD.add(singleImageList[i])
                imageListD.add(multiImageList[i])
            }
        }

        imgAdapter = ImageAdapter(this, imageListD)
        rvsections.adapter = imgAdapter

        rvsections.addItemDecoration(StickyHeaderItemDecoration(rvsections, imgAdapter as ImageAdapter))
    }
}