package com.example

// object A and object A$ at the same time => [error] produce classes that overwrite one another

// - A.class
// - A.tasty
// - A$.class
// object A
//
// ---------------------------------------------------------------
// - A$.class
// - A$.tasty
// - A$$.class
object A$

// ---------------------------------------------------------------
// - A/package.class
// - A/package.tasty
// - A/package$.class
package object A {
  // with this line, scaladoc fails to generate doc,
  // but without this line, scaladoc won't fail
  val _ = A$
}


