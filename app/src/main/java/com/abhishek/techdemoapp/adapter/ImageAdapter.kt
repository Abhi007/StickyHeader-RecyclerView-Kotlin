package com.abhishek.techdemoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.techdemoapp.ConstantsData
import com.abhishek.techdemoapp.R
import com.abhishek.techdemoapp.model.Demo2Img
import com.abhishek.techdemoapp.model.DemoImg
import com.abhishek.techdemoapp.model.DemoText
import com.abhishek.techdemoapp.utils.StickyHeaderItemDecoration
import java.util.*

class ImageAdapter(private val context: Context, private val imgDataList: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderItemDecoration.StickyHeaderInterface {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun setData(context: Context, demoImg: DemoImg){
            imageView.setImageResource(demoImg.image)
        }

        fun setDataFor2Images(demo2Img: Demo2Img){
            imageView.setImageResource(demo2Img.image2)
        }
    }

    class MyTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.txt_section)

        fun setTextHeader(demotext: DemoText){
            textView.text = demotext.txtSection;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ConstantsData.TEXT_SECTION) {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.section_header, parent, false)
            MyTextHolder(view)
        } else {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.section_image, parent, false)
            MyHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return imgDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ConstantsData.TEXT_SECTION){
            if(imgDataList[position] is DemoText) {
                 (holder as MyTextHolder).setTextHeader(imgDataList[position] as DemoText)
            }
        } else if(getItemViewType(position) == ConstantsData.ONE_IMG_SECTION){
            if(imgDataList[position] is DemoImg) {
                (holder as MyHolder).setData(context, imgDataList[position] as DemoImg)
            }
        } else {
            if(imgDataList[position] is Demo2Img) {
                (holder as MyHolder).setDataFor2Images(imgDataList[position] as Demo2Img)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (imgDataList[position]) {
            is DemoText -> ConstantsData.TEXT_SECTION
            is DemoImg -> ConstantsData.ONE_IMG_SECTION
            else -> ConstantsData.MULTI_IMG_SECTION
        }
    }

    override fun isHeader(itemPosition: Int): Boolean {1
        return imgDataList[itemPosition] is DemoText
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        if (imgDataList[headerPosition] is DemoText) {
            ((header as ConstraintLayout).getChildAt(0) as TextView).text = (imgDataList[headerPosition] as DemoText).txtSection
        }
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return R.layout.section_header
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = 0
        var position = itemPosition
        do {
            if (this.isHeader(position)) {
                headerPosition = position
                break
            }
            position -= 1
        } while (position >= 0)
        return headerPosition
    }
}