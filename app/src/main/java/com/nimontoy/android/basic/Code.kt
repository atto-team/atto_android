package com.nimontoy.android.basic

/**
 * Created by leekijung on 2019. 8. 25..
 */

enum class Code(var code: Int) {
    LOGIN_REQUEST(101),
    LOGIN_SUCCESS(102),
    LOGIN_FAILED(103),
    LOGOUT_REQUEST(201),
    LOGOUT_SUCCESS(202),
    LOGOUT_FAILED(203),
    LEAVE_REQUEST(901),
    LEAVE_SUCCESS(902),
    LEAVE_FAILED(903),
    GOOGLE_SIGN_IN(9001)
}
