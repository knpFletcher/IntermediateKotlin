package com.example.solarsystem

import kotlinx.coroutines.experimental.*
import org.junit.Test

class CoroutinesAsyncTest {

    @Test
    fun usingAsync() {
        println("Starting...")

        //works same as launch function
        async(CommonPool) {
            delay(1_000L)
            println("Inside...")
        }

        Thread.sleep(1_500L)

        println("Stopping...")

    }

    @Test
    fun usingAsyncWithDeferred() {

        println("Starting...")

        //needs to be wrapped in another coroutine to get at result in print statement
        launch(CommonPool) {
            val result: Deferred<Int> = async(CommonPool) {
                delay(1_000L)
                println("Inside...")
                42
            }

            println("the result: ${result.await()}")

        }
        Thread.sleep(1_500L)

        println("Stopping...")

    }

    @Test
    fun usingAsyncTwice() { //and combine results

        println("Starting...")

        launch(CommonPool) {
            val result: Deferred<Int> = async(CommonPool) {
                delay(2_000L)
                println("Inside first...")
                42
            }

            val result2: Deferred<Int> = async(CommonPool) {
                delay(1_000L)
                println("Inside second...")
                8
            }

            //await() allows printing to be suspended until both async coroutines are completed
            println("the sum: ${result.await() + result2.await()}")

        }
        Thread.sleep(3_500L)

        println("Stopping...")

    }

    @Test
    fun usingAsyncWithSupsend() {

        println("Starting...")

        launch(CommonPool) {
            val result: Deferred<Int> = async(CommonPool) {
                ourWork()
            }

            println("the result: ${result.await()}")

        }
        Thread.sleep(3_500L)

        println("Stopping...")

    }

    private suspend fun ourWork(): Int {
        delay(2_000L)
        println("Inside ...")
        return 88
    }

}
