package ar.com.caeldev.bsacore.exceptions

class BaseException(code: String, nestedException: Throwable) extends Exception(code, nestedException) {
}