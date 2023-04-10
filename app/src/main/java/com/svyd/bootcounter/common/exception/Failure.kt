package com.svyd.bootcounter.common.exception

sealed class Failure {
    object DataBaseError :  Failure()
    object UnexpectedError : Failure()
}