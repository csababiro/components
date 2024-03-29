package com.mobiversal.componentsdemo

import android.graphics.Color
import android.os.Bundle
import com.mobiversal.popupwindowcustom.PopupWindowResponse
import com.mobiversal.popupwindowcustom.PopupWindowCustom
import com.mobiversal.popupwindowcustom.PopupWindowItem
import kotlinx.android.synthetic.main.activity_popup_window_custom.*

class PopupWindowCustomActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_window_custom)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items1 = getPopupWindowItems()
        imgOption1.setOnClickListener {
            val popupWindowCustom = PopupWindowCustom(items1, object : PopupWindowResponse {
                override fun selectedResourceId(popupWindowItem: PopupWindowItem) {

                }
            })

            popupWindowCustom.showPopupWindowAtCoordinates(50, 50, this, imgOption1)
        }


        val items2 = getPopupWindowItems()
        imgOption2.setOnClickListener {
            val popupWindowCustom = PopupWindowCustom(items2, object : PopupWindowResponse {
                override fun selectedResourceId(popupWindowItem: PopupWindowItem) {

                }
            })

            popupWindowCustom.showSelectedCheck = true
            //popupWindowCustom.topMaring = 24.dpToPx()

            popupWindowCustom.showPopupWindow(imgOption2)
        }

        val items3 = getPopupWindowItems()
        imgOption3.setOnClickListener {
            val popupWindowCustom = PopupWindowCustom(items3, object : PopupWindowResponse {
                override fun selectedResourceId(popupWindowItem: PopupWindowItem) {

                }
            })

            popupWindowCustom.largeView = true

            popupWindowCustom.showPopupWindow(imgOption3)
        }

        val items4 = getPopupWindowItemsUnselectedItems()
        imgOption4.setOnClickListener {
            val popupWindowCustom = PopupWindowCustom(items4, object : PopupWindowResponse {
                override fun selectedResourceId(popupWindowItem: PopupWindowItem) {

                }
            })

            popupWindowCustom.showSelectedCheck = true
            //popupWindowCustom.largeView = true

            popupWindowCustom.showPopupWindow(imgOption4)
        }

        val items5 = getPopupWindowItemsWithIcons()
        imgOption5.setOnClickListener {
            val popupWindowCustom = PopupWindowCustom(items5, object : PopupWindowResponse {
                override fun selectedResourceId(popupWindowItem: PopupWindowItem) {

                }
            })

            popupWindowCustom.showSelectedCheck = true

            popupWindowCustom.showPopupWindow(imgOption5)
        }

        val items6 = getPopupWindowItemsWithIcons()
        imgOption6.setOnClickListener {
            val popupWindowCustom = PopupWindowCustom(items6, object : PopupWindowResponse {
                override fun selectedResourceId(popupWindowItem: PopupWindowItem) {

                }
            })

            popupWindowCustom.showSelectedCheck = true
            popupWindowCustom.largeView = true

            //popupWindowCustom.textColorResource = R.color.colorAccent
            popupWindowCustom.textColor = Color.RED
            popupWindowCustom.textSizeResource = 24f
            popupWindowCustom.fontResource = R.style.BoldTextAppearance

            popupWindowCustom.showPopupWindow(imgOption5)
        }
    }

    fun getPopupWindowItems(): List<PopupWindowItem> {

        val popupWindowItems = mutableListOf<PopupWindowItem>()

        popupWindowItems.add(PopupWindowItem(1, "First option").apply { isSelected = true })
        popupWindowItems.add(PopupWindowItem(2, "Second optiosd fsdf sdfsn"))
        popupWindowItems.add(PopupWindowItem(3, "Third option"))
        popupWindowItems.add(PopupWindowItem(4, "Fourth option"))
        popupWindowItems.add(PopupWindowItem(5, "Fifth option"))

        return popupWindowItems
    }

    fun getPopupWindowItemsUnselectedItems(): List<PopupWindowItem> {

        val popupWindowItems = mutableListOf<PopupWindowItem>()

        popupWindowItems.add(PopupWindowItem(1, "First option"))
        popupWindowItems.add(PopupWindowItem(2, "Second optiosd fsdf sdfsn"))
        popupWindowItems.add(PopupWindowItem(3, "Third option"))
        popupWindowItems.add(PopupWindowItem(4, "Fourth option"))
        popupWindowItems.add(PopupWindowItem(5, "Fifth option"))

        return popupWindowItems
    }


    fun getPopupWindowItemsWithIcons(): List<PopupWindowItem> {

        val popupWindowItems = mutableListOf<PopupWindowItem>()

        popupWindowItems.add(PopupWindowItem(1, "First option", iconResource = R.drawable.job_pin_completed).apply { isSelected = true })
        popupWindowItems.add(PopupWindowItem(2, "Second optiosd fsdf sdfsn dafjg sdl glksdjgl sdg", iconResource = R.drawable.job_pin_in_progress))
        popupWindowItems.add(PopupWindowItem(3, "Third option", iconResource = R.drawable.job_pin_to_do))
        popupWindowItems.add(PopupWindowItem(4, "Fourth option", iconResource = R.drawable.job_pin_urgent))
        popupWindowItems.add(PopupWindowItem(5, "Fifth option", iconResource = R.drawable.on_the_way_bullet))

        return popupWindowItems
    }
}
