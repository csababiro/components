package com.mobiversal.popupwindowcustom

/**
 * Created by Csaba on 8/19/2019.
 */
class PopupWindowItem {
    var id: Int = 0
    var value: String = ""
    var isSelected: Boolean = false
    var valueResource: Int = 0
    var iconResource: Int = 0
    var selectedIconResource: Int = 0

    constructor(value: String) {
        this.value = value
    }


    constructor(value: String, iconResource: Int) {
        this.value = value
        this.iconResource = iconResource
    }
}