package org.jboss.bpmn2.editor.core.diagram

import NiceObject._
import org.jboss.bpmn2.editor.core._
import org.eclipse.bpmn2.util._

import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.CoreException

import org.eclipse.emf.common.util.URI
import org.eclipse.bpmn2.impl._

import org.junit.runner.RunWith
import org.specs._
import org.specs.matcher._
import org.specs.runner.{ JUnitSuiteRunner, JUnit };

import scala.xml._
import java.io.File

@RunWith(classOf[JUnitSuiteRunner])
class ModelHandlerTests extends Specification with JUnit {
  val path = URI.createURI("file:///tmp/test.bpmn2")

  def initHandler(): ModelHandler = {
    new java.io.File("/tmp/test.bpmn2").delete

    ModelHandler.releaseModel(path);

    val r:Bpmn2ResourceImpl = new Bpmn2ResourceFactoryImpl().createAndInitResource(path).eResource().asInstanceOf[Bpmn2ResourceImpl]
    ModelHandler.createModelHandler(path, r) 
  }

  "ModelHandler" should {
	  
    "be a factory" in {
      val handler = initHandler
      handler must notBeNull

      val resource = handler.getResource
      resource must notBeNull

      "have correct path for resouce" in {
        resource.getURI must_== path
      }

      "and have document root set" in {
        val documentRoot = resource.getContents.get(0)
        classOf[DocumentRootImpl] must_== documentRoot.niceClass

        "that contains BPMN2 definitions" in {
          val definitions = documentRoot.eContents.get(0)
          classOf[DefinitionsImpl] must_== definitions.niceClass
        }
      }

      "be able to get definitions" in {
        val definitions = handler.getDefinitions
        classOf[DefinitionsImpl] must_== definitions.niceClass
      }

    }

    "be able to create Task" in {
      val handler = initHandler
      val task = handler.createTask
      task.isInstanceOf[TaskImpl] must beTrue

      "that is placed to model Resources" in {
        val resource = handler.getResource

        "have document root set" in {
          val documentRoot = resource.getContents.get(0)
          classOf[DocumentRootImpl] must_== documentRoot.niceClass

          "that contains BPMN2 definitions" in {
            val definitions = documentRoot.eContents.get(0)
            classOf[DefinitionsImpl] must_== definitions.niceClass

            "definitions must contain at least one process" in {
              val process = definitions.eContents.get(0)
              process.isInstanceOf[ProcessImpl] must beTrue

              "process must contain one task" in {
                process.eContents.size must_== 1
                val task2 = process.eContents.get(0)
                task.isInstanceOf[TaskImpl] must beTrue
                task2 must_== task
              }
            }
          }
        }
      }
      "creating another task must be also in the same process" in {
        val task2 = handler.createTask
        task2 must_!= task

        val process = handler.getFirstProcess
        "process must contain two tasks" in {
          val elements = process.getFlowElements
          elements.size must_== 2

          "firtly created task must be first" in {
            elements.get(0) must_== task
          }

          "secondly created task must be second" in {
            elements.get(1) must_== task2
          }
        }
      }
    }

    "save model correctly" in {
      val handler = initHandler

      "when initialized" in {
        handler.save
        val defXml = XML.loadFile(new File(path.toFileString))
        defXml.child must_== Nil

        "have process and task when task is created" in {
          handler.createTask
          handler save

          val taskXml = XML.loadFile(new File(path.toFileString))

          val process = taskXml \\ "process"
          process must notBeNull

          val task = process \ "task"
          task must notBeNull

          "have two tasks when second task is created" in {
            handler.createTask
            handler save

            val twoTaskXml = XML.loadFile(new File(path.toFileString))

            val tasks = twoTaskXml \\ "task"
            tasks.size must_== 2
          }
        }
      }
    }
  }
}

object ModelHandlerMain {
  def main(args: Array[String]) {
    new ModelHandlerTests().main(args)
  }
}