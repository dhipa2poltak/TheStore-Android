package com.dpfht.testproductlist.ext

import java.text.NumberFormat
import java.util.Locale

fun Double.toRupiahString(): String {
  val nf = NumberFormat.getNumberInstance(Locale.GERMAN)

  return "Rp. ${nf.format(this)}"
}
