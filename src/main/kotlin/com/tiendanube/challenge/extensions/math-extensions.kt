package com.tiendanube.challenge.extensions

/**
 * Created by tomReq on 6/3/19.
 */

infix fun Number.percentage(coefficient: Number?) = (this.toDouble() * (coefficient?.toDouble() ?: 0.0)) / 100
