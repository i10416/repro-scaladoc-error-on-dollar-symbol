package com.example

// object A and object A$ at the same time => [error] produce classes that overwrite one another

// - A.class
// - A.tasty
// - A$.class: module class of A
// object A
//
// ---------------------------------------------------------------
// - A$.class: class of A$
// - A$.tasty
// - A$$.class: module class of A$
object A$


package object B {
  // with this line, scaladoc fails to generate doc,
  // but without this line, scaladoc won't fail
  val _ = A$
}


