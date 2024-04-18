package com.berkerkoyuncu.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Text;

import java.util.Random;

import sun.java2d.pipe.SpanShapeRenderer;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch ;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float veloCity = 0;
	float gravity= 0.5f;
	float enemyVelocity = 8;
	Random random;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scoreEnemy = 0;
	BitmapFont font;
	BitmapFont font2;



	int numberOfEnemies = 4;
	float[] enemyX =  new float[numberOfEnemies];
	float[] enemyOffSet1 = new float[numberOfEnemies];
	float[] enemyOffSet2 = new float[numberOfEnemies];
	float[] enemyOffSet3= new float[numberOfEnemies];
	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;

	float distance = 0;
	
	@Override
	public void create () {
	batch = new SpriteBatch();
	background =  new Texture("full-background.png");
	bird = new Texture("frame.png");
	enemy1 =  new Texture("enemy.png");
	enemy2 =  new Texture("enemy.png");
	enemy3 =  new Texture("enemy.png");
	birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight()/2;
	birdY = Gdx.graphics.getHeight()/3;
	distance =Gdx.graphics.getWidth()/2;
	random = new Random();
	birdCircle = new Circle();
	enemyCircle1 = new Circle[numberOfEnemies];
	enemyCircle2 = new Circle[numberOfEnemies];
	enemyCircle3 = new Circle[numberOfEnemies];
	shapeRenderer = new ShapeRenderer();
	font = new BitmapFont();
	font.setColor(Color.WHITE);
	font.getData().setScale(4);
	font2 = new BitmapFont();
	font2.setColor(Color.CORAL);
	font2.getData().setScale(6);





	for (int i=0;i<numberOfEnemies;i++){
		enemyX[i]=Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;
		enemyOffSet1[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
		enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
		enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);

		enemyCircle1[i]= new Circle();
		enemyCircle2[i]= new Circle();
		enemyCircle3[i]= new Circle();
	}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1){

			if(enemyX[scoreEnemy]<Gdx.graphics.getWidth()/2 - bird.getHeight()/2){
				score++;
				if(scoreEnemy<numberOfEnemies-1){
					scoreEnemy++;
				}else{
					scoreEnemy = 0;
				}
			}


			if(Gdx.input.justTouched()){
			veloCity += -15;
			}

			for (int i=0;i<numberOfEnemies ;i++){
				if (enemyX[i]<Gdx.graphics.getWidth()/15){
					enemyX[i] += numberOfEnemies * distance;
					enemyOffSet1[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
				}else{
					enemyX[i]-=enemyVelocity;
				}
				enemyX[i] -= enemyVelocity;
				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet1[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				enemyCircle1[i] = new Circle( enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2 +enemyOffSet1[i]+ Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircle2[i] = new Circle( enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2 +enemyOffSet2[i]+ Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircle3[i] = new Circle( enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2 +enemyOffSet3[i]+ Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			}



			if(birdY > 0 ){
				veloCity += gravity;
				birdY -= veloCity;
			}else{
				gameState = 2;
			}

		}else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		} else if (gameState == 2) {
			font2.draw(batch,"Game Over!! Top to contiune...",250,Gdx.graphics.getHeight()/2);
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
			birdY = Gdx.graphics.getHeight()/3;
			for (int i=0;i<numberOfEnemies;i++){
				enemyX[i]=Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;
				enemyOffSet1[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
				enemyOffSet2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);
				enemyOffSet3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);

				enemyCircle1[i]= new Circle();
				enemyCircle2[i]= new Circle();
				enemyCircle3[i]= new Circle();
			}
			veloCity = 0;
			score = 0;
			scoreEnemy = 0;
		}

		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch, String.valueOf(score),100,200);
	batch.end();
	birdCircle.set(birdX+Gdx.graphics.getWidth()/30,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
	//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	//shapeRenderer.setColor(Color.BLUE);
	//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

	for (int i=0;i<numberOfEnemies;i++) {

	//	shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2 +enemyOffSet1[i]+ Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
	//	shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2 +enemyOffSet2[i]+ Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
	//	shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2 +enemyOffSet3[i]+ Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);


		if(Intersector.overlaps(birdCircle,enemyCircle1[i]) || Intersector.overlaps(birdCircle,enemyCircle2[i]) || Intersector.overlaps(birdCircle,enemyCircle3[i])){
			gameState = 2;
		}




	}

	//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}
