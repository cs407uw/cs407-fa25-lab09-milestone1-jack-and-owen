package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = backgroundWidth / 2
    var posY = backgroundHeight / 2
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            velocityX = getVelocity(velocityX, 0f, dT, 0f, xAcc)
            velocityY = getVelocity(velocityY, 0f, dT, 0f, yAcc)
            posX = posX + getDistanceTraveled(0f, dT, velocityX, 0f, xAcc)
            posY = posY + getDistanceTraveled(0f, dT, velocityY, 0f, yAcc)
            return
        }
        // If not first update, find previous acceleration and velocity and update
        else {
            val previousAccX = accX
            val previousAccY = accY
            val previousVelocityX = velocityX
            val previousVelocityY = velocityY
            velocityX = getVelocity(previousVelocityX, 0f, dT, previousAccX, xAcc)
            velocityY = getVelocity(previousVelocityY, 0f, dT, previousAccY, yAcc)
            posX = posX + getDistanceTraveled(0f, dT, previousVelocityX, previousAccX, xAcc)
            posY = posY + getDistanceTraveled(0f, dT, previousVelocityY, previousAccY, yAcc)
            accX = xAcc
            accY = yAcc
            return
        }

    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // Check left wall
        if (posX < 0) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }
        // Check right wall
        else if (posX + ballSize > backgroundWidth) {
            posX = backgroundWidth - ballSize
            velocityX = 0f
            accX = 0f
        }
        
        // Check top wall
        if (posY < 0) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }
        // Check bottom wall
        else if (posY + ballSize > backgroundHeight) {
            posY = backgroundHeight - ballSize
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // TODO: implement the reset function
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)

        // Set posX and posY to center of screen
        posX = backgroundWidth / 2
        posY = backgroundHeight / 2
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }

    private fun getVelocity(v0: Float, t0: Float, t1: Float, a0: Float, a1: Float): Float {
        return v0 + 0.5f * (a1 + a0) * (t1 - t0)
    }

    private fun getDistanceTraveled(t0: Float, t1: Float, v0: Float, a0: Float, a1: Float): Float {
        return v0 * (t1 - t0) + 0.16666666666666666f * ((t1 - t0) * (t1 - t0)) * (3 * a0 + a1)
    }
}