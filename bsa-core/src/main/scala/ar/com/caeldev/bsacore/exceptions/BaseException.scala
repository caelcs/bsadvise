package ar.com.caeldev.bsacore.exceptions

class BaseException(message: String, nestedException: Throwable) extends Exception(message, nestedException) {
}