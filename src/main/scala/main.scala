package com.example

// - A$.class: class of A$
// - A$.tasty
// - A$$.class: module class of A$
object A$


package object B {
  // with this line, scaladoc fails to generate doc,
  // but without this line, scaladoc won't fail
  val _ = A$
}


