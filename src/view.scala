package painttool

import scala.math.{abs,min}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.{Button,ToggleButton, ToolBar, ToggleGroup}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Ellipse, Line, Rectangle}
import scalafx.geometry._
import scalafx.scene.{Group,Node,Scene}
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.layout.{BorderPane,HBox,Priority,Pane}

object Main extends JFXApp {

  val W = 500; val H = 500

  var x:Double = 0
  var y:Double = 0

  var tool:Updator = null

  type Updator = (Point2D, Point2D) => Unit

  val canvas = new Canvas(W, H) {
      onMousePressed  = (e: MouseEvent) => {x = e.x; y = e.y;}
      onMouseDragged = (e: MouseEvent) => drawfree(new Point2D(x,y),new Point2D(e.x,e.y))
      //onMouseReleased = (e: MouseEvent) => { onDragFinish(e); draw() }
    }

    val gc = canvas.graphicsContext2D

    val pw = gc.pixelWriter

    def drawfree: Updator = { (p1: Point2D, p2: Point2D) =>
    gc.strokeLine(p1.x,p1.y,p2.x,p2.y)
    x = p2.x
    y = p2.y
  }

  stage = new PrimaryStage {
    title = "PaintScala"
    scene = new Scene {
      root = new BorderPane {
        hgrow = Priority.Always
        vgrow = Priority.Always
        center = canvas
        /*top = new HBox {
          children = List(quitButton, backwardButton, forwardButton, favoriteButton)
        }*/
      }
    }
  }

}