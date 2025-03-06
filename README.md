## About
This project reproduces suspicious scaladoc behavior
when there are both an object and a package object of the same name with `$` suffix
__whose body has a reference to the object__

Since Scala 3.6.3, scaladoc fails to generate doc due to the following error.

```
Loading Scala 3 binary from /path/to/target/scala-3.6.3/classes/com/example/A$.class.
It should have been loaded from `.tasty` file. This `.tasty` file is missing. Try cleaning the project to fix this issue.
```

Scaladoc(Scala 3.4.0~3.6.2) won't fail though it emits the same error log.

In the same situation, scaladoc(Scala 3.3.1) also crashes and emits the following stack traces.

```
[error] Caused by: java.lang.AssertionError: NoDenotation.owner
[error]         at dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2582)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler.dotty$tools$dotc$core$tasty$TreeUnpickler$TreeReader$$_$_$$anonfun$13(TreeUnpickler.scala:612)
[error]         at scala.collection.immutable.Set$Set2.find(Set.scala:232)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.createMemberSymbol(TreeUnpickler.scala:612)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.createSymbol(TreeUnpickler.scala:560)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.symbolAtCurrent(TreeUnpickler.scala:282)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.indexStats(TreeUnpickler.scala:764)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.indexStats$$anonfun$1$$anonfun$1(TreeUnpickler.scala:774)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.processPackage(TreeUnpickler.scala:794)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.processPackage(TreeUnpickler.scala:790)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler$TreeReader.indexStats(TreeUnpickler.scala:774)
[error]         at dotty.tools.dotc.core.tasty.TreeUnpickler.enter(TreeUnpickler.scala:107)
[error]         at dotty.tools.dotc.core.tasty.DottyUnpickler.enter(DottyUnpickler.scala:57)
[error]         at dotty.tools.dotc.core.classfile.ClassfileParser.unpickleTASTY$1(ClassfileParser.scala:923)
[error]         at dotty.tools.dotc.core.classfile.ClassfileParser.unpickleOrParseInnerClasses(ClassfileParser.scala:991)
[error]         at dotty.tools.dotc.core.classfile.ClassfileParser.parseClass(ClassfileParser.scala:189)
[error]         at dotty.tools.dotc.core.classfile.ClassfileParser.$anonfun$1(ClassfileParser.scala:87)
[error]         at dotty.tools.dotc.core.classfile.ClassfileParser.run(ClassfileParser.scala:82)
[error]         at dotty.tools.dotc.core.ClassfileLoader.load(SymbolLoaders.scala:412)
[error]         at dotty.tools.dotc.fromtasty.ReadTasty.readTASTY(ReadTasty.scala:66)
[error]         at dotty.tools.dotc.fromtasty.ReadTasty.runOn$$anonfun$1(ReadTasty.scala:25)
[error]         at scala.collection.immutable.List.flatMap(List.scala:294)
[error]         at dotty.tools.dotc.fromtasty.ReadTasty.runOn(ReadTasty.scala:25)
[error]         at dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:246)
[error]         at scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
[error]         at scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
[error]         at scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1323)
[error]         at dotty.tools.dotc.Run.runPhases$1(Run.scala:262)
[error]         at dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:270)
[error]         at dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:279)
[error]         at dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:67)
[error]         at dotty.tools.dotc.Run.compileUnits(Run.scala:279)
[error]         at dotty.tools.dotc.Run.compileUnits(Run.scala:200)
[error]         at dotty.tools.dotc.fromtasty.TASTYRun.compile(TASTYRun.scala:15)
[error]         at dotty.tools.dotc.Driver.doCompile(Driver.scala:37)
[error]         at dotty.tools.dotc.Driver.process(Driver.scala:197)
[error]         at scala.tasty.inspector.OldTastyInspector.inspectFilesInContext(OldTastyInspector.scala:72)
[error]         at scala.tasty.inspector.OldTastyInspector.inspectFilesInContext$(OldTastyInspector.scala:22)
[error]         at scala.tasty.inspector.DocTastyInspector.inspectFilesInContext(DocTastyInspector.scala:5)
[error]         at dotty.tools.scaladoc.tasty.ScaladocTastyInspector.result(TastyParser.scala:147)
[error]         at dotty.tools.scaladoc.ScalaModuleProvider$.mkModule(ScalaModuleProvider.scala:11)
[error]         at dotty.tools.scaladoc.Scaladoc$.run(Scaladoc.scala:236)
```

After quick investigation, there are some issues presumably relevant to this case.

- https://github.com/scala/scala3/issues/19702
- https://github.com/scala/scala3/issues/22447
