package cga.exercise.components.geometry

import cga.exercise.components.light.PointLight
import cga.exercise.components.shader.ShaderProgram
import org.joml.Math
import org.joml.Vector3f
import kotlin.math.pow
import kotlin.math.sqrt

class Star(light : PointLight, collectableObject: Renderable, material: Material) {
    private var collectableObject : Renderable
    private var pointLight: PointLight
    private var collected : Boolean
    private var material : Material


    init {
        this.collectableObject = collectableObject
        this.pointLight = light
        this.collected = false
        this.material = material

    }

    fun x() = this.collectableObject.getWorldPosition().x
    fun y() = this.collectableObject.getWorldPosition().y
    fun z() = this.collectableObject.getWorldPosition().z


    // rendering a Star Object with its Light and Renderable
    fun render(shader : ShaderProgram, name : String) {
        if (!collected) {
            shader.use()
            shader.setUniform("farbe", Vector3f(1.0f))
            pointLight.bind(shader, name)
            collectableObject.render(shader)
        }
    }

    // function to calculate the distance between player and collectable
    fun distance(other : Renderable) : Float {
        val pos1 = other.getWorldPosition()
        val pos2 = this.collectableObject.getWorldPosition()

        val distance = sqrt(
                   (pos1.x() - pos2.x()).toDouble().pow(2.0) +
                   (pos1.y() - pos2.y()).toDouble().pow(2.0) +
                   (pos1.z() - pos2.z()).toDouble().pow(2.0))

        return distance.toFloat()
    }

    // setting Position of Renderable and Pointlight
     fun setPosition(x: Float, y: Float, z: Float) {
        this.collectableObject.setPosition(x, y, z)
        this.pointLight.setPosition(x + 4 , y + 4, z + 4)
    }

    fun rotate(amount: Float) {
      collectableObject.rotateLocal(amount, 0.0f, 0.0f)
    }


    // When taking the center of the planet as the center:
    // yaw: left / right from player
    // roll: front / back from player
    fun rotateAroundPoint(pitch: Float, yaw: Float, roll: Float, altMidpoint: Vector3f) {
        this.collectableObject.rotateAroundPoint(pitch, yaw, roll, altMidpoint)
    }

    // function to define if star is collected or not
    fun collect() : Boolean {
        return if (collected) {
            false
        } else {
            this.collected = true
            true
        }
    }

    fun translate(dir: Vector3f) {
        collectableObject.translateLocal(dir)
    }

    fun getXDir() = this.collectableObject.getXDir()

    fun getYDir() = this.collectableObject.getYDir()

    fun getZDir() = this.collectableObject.getZDir()

    fun getPosition() = this.collectableObject.getWorldPosition()
}