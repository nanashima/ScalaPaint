package painttool

import scala.math.{abs,min}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.{Button,ToggleButton, ToolBar, ToggleGroup}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.shape._
import scalafx.geometry._
import scalafx.scene.layout.{Pane, BorderPane}
import scalafx.scene.{Group,Node,Scene}
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.layout.{BorderPane,HBox,Priority,Pane,VBox}

object Main extends JFXApp {

  val W = 500; val H = 500

  val tool_freehand = 1

  var tool:Int = tool_freehand

  type Updator = (Point2D, Point2D) => Shape
  type Factory = (Shape,Shape) => Shape

  val straightLine:Updator = (p1,p2)=>{
    new Line { startX = p1.x; startY = p1.y
    endX = p2.x; endY   = p2.y
    stroke = currentColor; strokeWidth = 1 }
  }

  val identityFactory:Factory = (s,temp)=>{s}

  val unionFactory:Factory = (s,temp)=>{
    val shape = Shape.union(s,temp)
    shape.setFill(currentColor)
    shape
  }

  var update:Updator = straightLine
  var factory:Factory = unionFactory


  var temp:Point2D = new Point2D(0,0)
  var tempShapes:List[Shape] = Nil

  var defaultColor:Color = Color.rgb(255,255,255)

  var currentColor:Color = Color.rgb(0,0,255)

/*
  var canvas = new Canvas(W, H) {
    onMousePressed  = (e: MouseEvent) => {x = e.x; y = e.y;}
    onMouseDragged = (e: MouseEvent) => drawfree(new Point2D(x,y),new Point2D(e.x,e.y))
    onMouseReleased = (e: MouseEvent) => { saveHistory(this) }
  }*/

  /*val gc = canvas.graphicsContext2D

  val pw = gc.pixelWriter*/

  val drawingPane = new Pane{}

  drawingPane.onMousePressed = { e: MouseEvent =>
    temp = new Point2D(e.x, e.y)
    println(new Point2D(e.x, e.y))
  }

  drawingPane.onMouseDragged = { e: MouseEvent =>
    tempShapes match {
      case Nil => {
        tempShapes = List(update(temp, new Point2D(e.x, e.y)))
        drawingPane.children ++= Seq(tempShapes.head)
      }
      case s::rest => {
        tempShapes = factory(update(temp, new Point2D(e.x, e.y)),s)::rest
        val n = drawingPane.children.length
        drawingPane.children.remove(n-1)
        drawingPane.children ++= Seq(tempShapes.head)
      }
    }
    temp = new Point2D(e.x, e.y)
    //println(temp)
  }

  drawingPane.onMouseReleased = { e: MouseEvent =>
    tempShapes = Nil
  }

  

  val backB = new Button("←") { onAction =
      () => { val n = drawingPane.children.length 
        if(n>0) drawingPane.children.remove(n-1)
      }
  }

  def UpdateTool(toolId:Int):Unit = {
    toolId match {
      case tool_freehand => {update = straightLine; factory = unionFactory} 
    }
  }

  stage = new PrimaryStage {
    title = "Scalaペイント"
    scene = new Scene(H,W) {
      root = new BorderPane {
        center = drawingPane
        bottom = new ToolBar{content = List(backB)}
      }
    }
  }

}