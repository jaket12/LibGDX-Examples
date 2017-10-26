package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

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
    public float PlayerWidth = 16;
    public float PlayerHeight = 16;

    public Array<FlatTiledNode> PathNodesToFollow;
    public int CurrentNodeIndex;
    public Boolean IsFollowingPath;
    public Vector2 TargetPosition;

    public Texture PlayerImage;

    public PlayerCharacter(float locationx, float locationy) {

        PlayerImage = new Texture("AwesomeSmall.png");
        IsFollowingPath = false;
        TargetPosition = new Vector2((4 * 32f) - 16f, (8 * 32f) - 16f);
        CreatePlayerBox2D(locationx, locationy);

    }

    private void CreatePlayerBox2D(float locationx, float locationy) {
        PlayerBodyDef = new BodyDef();
        PlayerBodyDef.type = BodyDef.BodyType.DynamicBody;
        PlayerBodyDef.fixedRotation = true;
        PlayerBodyDef.position.set(locationx, locationy);

        PlayerFixtureDef = new FixtureDef();
        PlayerFixtureDef.density = 1;
        PlayerFixtureDef.friction = 0;
        PlayerFixtureDef.restitution = 0;

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

        if (IsFollowingPath) {
            MoveTowardPathNode();
        } else {
            ComputeVelocity();
        }

    }

    public void Draw(SpriteBatch batch) {
        batch.draw(PlayerImage, PlayerBody.getPosition().x - 8f, PlayerBody.getPosition().y - 8f);
    }

    private void ComputeVelocity() {

        StopMovement();

        //Given player input, apply velocity
        if (Gdx.input.isKeyPressed(Input.Keys.A) && PlayerBody.getLinearVelocity().x > -PlayerSpeed) {
            MoveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && PlayerBody.getLinearVelocity().x < PlayerSpeed) {
            MoveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && PlayerBody.getLinearVelocity().y > -PlayerSpeed) {
            MoveDown();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && PlayerBody.getLinearVelocity().y < PlayerSpeed) {
            MoveUp();
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
        VelocityDifferenceY = -PlayerSpeed - PlayerBody.getLinearVelocity().y;
        CurrentVelocityY = PlayerBody.getMass() * VelocityDifferenceY;
        PlayerBody.applyLinearImpulse(0, CurrentVelocityY, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
    }

    public void MoveUp() {
        VelocityDifferenceY = PlayerSpeed - PlayerBody.getLinearVelocity().y;
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

    public Vector2 GetPosition() {
        //return PlayerBody.getLocalCenter();
        return PlayerBody.getPosition();
    }

    public void FollowPath(Array<FlatTiledNode> nodes) {
        if (nodes.size > 0) {
            IsFollowingPath = true;
            PathNodesToFollow = nodes;
            CurrentNodeIndex = 0;
        }
    }

    public void MoveTowardPathNode() {
        StopMovement();//Reset velocity for this frame
        if (PathNodesToFollow.size > CurrentNodeIndex) {
            FlatTiledNode currentnode = PathNodesToFollow.get(CurrentNodeIndex);

            //Move in the direction of the node tile. Normalize the speed so it doesn't exceed our PlayerSpeed limit
            float playerx = PlayerBody.getPosition().x;
            float playery = PlayerBody.getPosition().y;
            TargetPosition = new Vector2(((currentnode.x + 1) * 32f) - 16f, ((currentnode.y + 1) * 32f) - 16f);

            //float distance = Vector2.dst(PlayerBody.getPosition().x, PlayerBody.getPosition().y, TargetPosition.x, TargetPosition.y);//Distance between the current location and the target location
            Vector2 direction = TargetPosition.cpy().sub(PlayerBody.getPosition().cpy()).nor().scl(PlayerSpeed, PlayerSpeed);//Subtract the current location from the target location, and then normalize the result. Copy it to a new variable so it doesn't overwrite
            direction.x = PlayerBody.getMass() * direction.x;
            direction.y = PlayerBody.getMass() * direction.y;

            MoveByVelocity(direction);

            float proximityx = Math.abs(PlayerBody.getPosition().x - TargetPosition.x);
            float proximityy = Math.abs(PlayerBody.getPosition().y - TargetPosition.y);
            if (proximityx < 2f && proximityy < 2f) {
                //We are close enough to say that we are in the target node tile.
                CurrentNodeIndex++;
            }
        } else {
            //Reached the end of the path
            IsFollowingPath = false;
        }
    }

    public void MoveByVelocity(Vector2 velocity) {
        PlayerBody.applyLinearImpulse(velocity.x, velocity.y, PlayerBody.getPosition().x, PlayerBody.getPosition().y, true);
    }
}