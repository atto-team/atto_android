package com.nimontoy.android

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by leekijung on 2019. 4. 21..
 */

class Variable<T> {
    private var value: T? = null

    var publishSubject: BehaviorSubject<T>

    constructor(value: T) {
        this.value = value
        publishSubject = BehaviorSubject.create()
        publishSubject.onNext(this.value!!)
    }

    constructor() {
        publishSubject = BehaviorSubject.create()
    }

    @Synchronized
    fun get(): T? {
        return value
    }

    @Synchronized
    fun set() {
        try {
            val voidConstructor = Void::class.java.getDeclaredConstructor()
            voidConstructor.isAccessible = true
            val v = voidConstructor.newInstance()
            publishSubject.onNext(v as T)
        } catch (e: Exception) { e.printStackTrace() }

    }

    @Synchronized
    fun set(value: T) {
        this.value = value
        publishSubject.onNext(this.value!!)
    }

    operator fun next() {
        publishSubject.onNext(this.value!!)
    }

    fun asObservable(): Observable<T> {
        return publishSubject.serialize()
    }
}