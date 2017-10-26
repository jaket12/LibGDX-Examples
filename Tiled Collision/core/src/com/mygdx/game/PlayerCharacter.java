package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * A simple player character that only uses a Box2D object.
 * On creation it will make it's BodyDef, which must be passed into the
 * Box2D world in order to make this object exist in the world.
 * Once it's in the world, you can call CreateFixture() to finalize the Body.
 * This class listens for keyboard input on its own to allow it to move around.
 */
public class PlayerCharacter {

    public Body PlayerBody;
    public BodyDef PlayerBodyDef;
    public FixtureDef PlayerFixtureDef;
    public PolygonShape PlayerShape;
    public Fixture PlayerFixture;

    public float PlayerSpeed = 200f;
    public float VelocityDifferenceX;
    public float VelocityDifferenceY;
    public float CurrentVelocityX;
    public float CurrentVelocityY;
    public float PlayerWidth = 32;
    public float PlayerHeight = 32;

    public boolean KeyboardControls = false;//Set to true if trying to move with keyboard. False for using mouse.

    public PlayerCharacter(float locationx, float locationy) {

        CreatePlayerBox2D(locationx, locationy);

    }

    private void CreatePlayerBox2D(float locationx, float locationy) {
        PlayerBodyDef = new BodyDef();
        PlayerBodyDef.type = BodyDef.BodyType.DynamicBody;
        PlayerBodyDef.position.set(locationx, locationy);

        PlayerFixtureDef = new FixtureDef();
        PlayerFixtureDef.density = 1;
        PlayerFixtureDef.friction = 1;
        PlayerFixtureDef.restitution = 1;

        PlayerShape = new PolygonShape();
        PlayerShape.setAsBox(PlayerWidth/2f, PlayerHeight/2f);

        PlayerFixtureDef.shape = PlayerShape;
    }

    public BodyDef GetBodyDef() {
        return PlayerBodyDef;
    }

    public void SetBody(Body body) {
        PlayerBody = body;
    }

    public void CreateFixture() {
        PlayerFixture = PlayerBody.createFixture(PlayerFixtureDef);
        PlayerShape.dispose();
    }

    public void Update() {

        if (KeyboardControls) {
            ComputeVelocity();
        }
    }

    private void ComputeVelocity() {

        StopMovement();

        //Given player input, apply velocity
        if (Gdx.input.isKeyPressed(Input.Keys.A) && PlayerBody.getLinearVelocity().x > -PlayerSpeed) {
            VelocityDifferenceX = -PlayerSpeed - PlayerBody.getLinearVelocity().x;
            CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
            PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && PlayerBody.getLinearVelocity().x < PlayerSpeed) {
            VelocityDifferenceX = PlayerSpeed - PlayerBody.getLinearVelocity().x;
            CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
            PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && PlayerBody.getLinearVelocity().y > -PlayerSpeed) {
            VelocityDifferenceY = -PlayerSpeed - PlayerBody.getLinearVelocity().y;
            CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
            PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && PlayerBody.getLinearVelocity().y < PlayerSpeed) {
            VelocityDifferenceY = PlayerSpeed - PlayerBody.getLinearVelocity().y;
            CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
            PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
        }
    }

    public void MoveLeft() {
        VelocityDifferenceX = -PlayerSpeed - PlayerBody.getLinearVelocity().x;
        CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
        PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
    }

    public void MoveRight() {
        VelocityDifferenceX = PlayerSpeed - PlayerBody.getLinearVelocity().x;
        CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
        PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
    }

    public void MoveDown() {
        VelocityDifferenceY = PlayerSpeed - PlayerBody.getLinearVelocity().y;
        CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
        PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
    }

    public void MoveUp() {
        VelocityDifferenceY = -PlayerSpeed - PlayerBody.getLinearVelocity().y;
        CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
        PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
    }

    public void StopMovement() {
        //Negate the previous frame velocity for X
        VelocityDifferenceX = 0 - PlayerBody.getLinearVelocity().x;
        CurrentVelocityX = PlayerBody.getMass() * VelocityDifferenceX;
        PlayerBody.applyLinearImpulse(CurrentVelocityX, 0, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
        //Negate the previous frame velocity for Y
        VelocityDifferenceY = 0 - PlayerBody.getLinearVelocity().y;
        CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
        PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
    }
}
