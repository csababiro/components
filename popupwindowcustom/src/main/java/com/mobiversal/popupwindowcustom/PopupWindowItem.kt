package com.mobiversal.popupwindowcustom

/**
 * Created by Csaba on 8/19/2019.
 */
data class PopupWindowItem(
    var id: Int = 0,
    var value: String = "",
    var isSelected: Boolean = false,
    var valueResource: Int = 0,
    var iconResource: Int = 0,
    var selectedIconResource: Int = 0
)