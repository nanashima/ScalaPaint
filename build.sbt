// 重要な注意：各設定の間に必ず空行を挿入して下さい。これをしないと sbt が起動しません。

name := "painttool"      // プロジェクトの名称

version := "0.1.0"       // プロジェクトのバージョン番号

scalaVersion := "2.11.7" // コンパイルに使う scalac のバージョン

scalacOptions ++=        // scalac に与えるオプション
  Seq("-optimize",
    "-feature",
    "-unchecked",
    "-deprecation",
    "-Ylog-classpath")

javaOptions in run ++=   // 仮想機械に与えるオプション
  Seq( "-Xmx2G", "-verbose:gc")    

// プロジェクトで使う非標準 Scala ライブラリ

libraryDependencies +=  // テストのために使う ScalaTest ライブラリ
  "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test"

// libraryDependencies +=  // JSONデータを扱うためのライブラリ
//   "net.liftweb" % "lift-json_2.11" % "3.0-M6"                      

/**
 * scalafx 8.0系が本筋のはずだが，
 *   - OSX Yosemite 10.10.5
 *   - SBT 0.13.9
 *   - Java 1.8.0_25, SBT
 * という環境では以下は動作しなかった．(2015.11.22)
 * そこで，止むなく，Java 7用の設定の 2.2.76 系を利用．2014年度の授業では8.0系は
 * 動作していたのだけど．．．
 **/

// For Java 8
// libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.60-R9"
// For Java 7
libraryDependencies += "org.scalafx" %% "scalafx" % "2.2.76-R11"

unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar")

// addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)

// libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2"

// sbt の挙動の設定

fork := true

connectInput := true

// ソースコードの在処を非標準の場所に設定

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test"

// コンパイル結果を非標準の場所に設定

// target := Path.userHome / "tmp" / "cs1f" / "lx06"