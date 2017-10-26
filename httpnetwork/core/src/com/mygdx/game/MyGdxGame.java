package com.mygdx.game;

/*
https://github.com/libgdx/libgdx/wiki/Reading-&-writing-JSON
https://stackoverflow.com/questions/25837013/switching-between-screens-libgdx
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

public class MyGdxGame extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture img;

	private HttpRequest GetRequest = new HttpRequest(HttpMethods.GET);
	//private String GetURL = "http://smashtracker.azurewebsites.net/api/SmashApi/GetTest";
	private String GetURL = "https://smashtracker.azurewebsites.net/api/SmashApi/GetAllPlayers";

	private HttpRequest PostRequest = new HttpRequest(HttpMethods.POST);
	private String PostURL = "https://smashtracker.azurewebsites.net/api/SmashApi/PostTest";


	private HttpResponseListener GetResponseListener;
	private HttpResponseListener PostResponseListener;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		GetRequest.setUrl(GetURL);//Set the url to go to. This does not trigger the request: it just builds it.
		GetResponseListener = BuildGetResponseListener();//Create the listener for when we get the response. All calls are nonblocking, so the game keeps going!
		Gdx.net.sendHttpRequest(GetRequest, GetResponseListener);//Actually send the request. Requires a request to send, and a listener to get the response.

		PostRequest.setUrl(PostURL);
		PostRequest.setMethod(HttpMethods.POST);
		PostRequest.setHeader("Content-Type", "application/json");//This has to be set, it won't default to anything, and it must be correctly formatted.
		PostRequest.setContent("{Name: \"Rich\", Id: 1}");//Usually this would be JSON, but it can really just be anything
		PostResponseListener = BuildPostResponseListener();
		Gdx.net.sendHttpRequest(PostRequest, PostResponseListener);
	}

	/***
	 * A listener must be created with all logic already in place: you can't instantiate an empty one.
	 * For each listener you want, find a way to create the functions and code for it from the start.
	 * @return
	 */
	private HttpResponseListener BuildGetResponseListener() {
		return new HttpResponseListener() {
			/***
			 * If success of any sort, this will be called immediately (mid render update or on next frame?)
			 * @param httpResponse
			 */
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {

				int status = httpResponse.getStatus().getStatusCode();
				String result = httpResponse.getResultAsString();//You can only call this function once for some reason. Value is deleted.
				Gdx.app.log("MyTag", "Got a response for GET!");
				Gdx.app.log("MyTag", "GET Status code: " + status);
				Gdx.app.log("MyTag", "GET Response: ~" + result + "~");

				Json json = new Json();
				SmashTrackerPlayer[] ActualResult = json.fromJson(SmashTrackerPlayer[].class, result);
				Gdx.app.log("MyTag", "An attempt at parsing. Name: " + ActualResult[0].Name + " Id: " + ActualResult[0].Id);
			}

			/***
			 * handleHttpResponse is in a hidden try catch. If an error is thrown, it will call this function.
			 * @param t
			 */
			@Override
			public void failed(Throwable t) {
				Gdx.app.log("MyTag", "Failed the http request! Message: " + t.getMessage());
				Gdx.app.log("MyTag", "Additional Message: " + t.getLocalizedMessage());
			}

			/***
			 * If we manually choose to call Gdx.net.cancelRequest() this will run in response
			 */
			@Override
			public void cancelled() {
				Gdx.app.log("MyTag", "Cancelled the http request!");
			}
		};
	}

	private HttpResponseListener BuildPostResponseListener() {
		return new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {

				int status = httpResponse.getStatus().getStatusCode();
				String result = httpResponse.getResultAsString();
				Gdx.app.log("MyTag", "Got a response for POST!");
				Gdx.app.log("MyTag", "POST Status code: " + status);
				Gdx.app.log("MyTag", "POST Response: ~" + result + "~");

			}

			@Override
			public void failed(Throwable t) {
				Gdx.app.log("MyTag", "Failed the http request! Message: " + t.getMessage());
				Gdx.app.log("MyTag", "Additional Message: " + t.getLocalizedMessage());
			}

			@Override
			public void cancelled() {
				Gdx.app.log("MyTag", "Cancelled the http request!");
			}
		};
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
