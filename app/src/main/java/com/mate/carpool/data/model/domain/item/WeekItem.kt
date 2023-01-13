package com.mate.carpool.data.model.domain.item

/*
월~금 버튼의 리사이클러뷰 어댑터 내의 items
weekName = 월~금의 이름
weekFlag = 버튼활성화 여부
 */
data class WeekItem(val weekName:String, var weekFlag:Boolean)
