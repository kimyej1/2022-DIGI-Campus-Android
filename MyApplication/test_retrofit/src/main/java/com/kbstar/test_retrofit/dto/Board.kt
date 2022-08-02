package com.kbstar.test_retrofit.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


// 서버 연동 데이터를 추상화 (VO, DTO, ..)
@Parcelize
class Board(          // body 불필요
    // DB Table과 순서가 동일해야함
    val idx: Int,
    var subject: String,
    var content: String,
    var writer: String,
    var regDate: Date,
    var cnt: Int
): Parcelable        // intent의 extra data에 추가할 것이다 -> Parcelable 구현해야..