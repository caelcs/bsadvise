package ar.com.caeldev.bsacore.serializer

trait Serializer[T, V] {

  def serialize(entity: T): V

  def deserialize(target: V): T

}
