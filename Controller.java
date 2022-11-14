package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Controller {

	private static Arrow arrow;
	private static Bow bow;
	private static Player player;
	private Target target;
	private Target bomb;
	private Platform platform;
	private Counter counter;
	private Save save;
	private List<Target> targetList;
	private List<ImageView> imageList;

	private double power;
	private boolean arrowShot = false;
	private double pressX;
	private double pressY;
	private double releasedX;
	private double releasedY;
	private double dragX;
	private double dragY;
	private double mouseAngle;
	private double arrowStartAngle = 162.5;
	private double arrowAngle;
	private double trigValue;
	private boolean bowSelected = false;
	private int highscore;
	
	private double startPosX = 100;
	private double startPosY = 400;

	private double targetWidth = 100; // 100
	private double targetHeight = 140; // 140
	private double targetX = 500;
	private double targetY = 0;
	private double rangeX = 780 - targetWidth - 10;;
	private double rangeY = 720 - targetHeight * 2;

	private double platformWidth = 1280;
	private double platformHeight = 79;
	private double platformX = 0;
	private double platformY = 561;

	@FXML
	ImageView arrowImage, bowImage, targetImage, bombImage, playerImage, redArrowImage;

	@FXML
	Button button;
	
	@FXML
	ToggleButton bowButton, playerButton;

	@FXML
	Label textField, highscoreLabel;

	@FXML
	Rectangle ground;
	
	@FXML
	Rectangle rect1, rect2, rect3, rect4, rect5;

	private Target[] targetArr = new Target[] { target, bomb };
	private ImageView[] imageArr = new ImageView[] { targetImage, bombImage };
	
	@FXML
	public void initialize() {
		
		redArrowImage.setVisible(false);
		rect1.setVisible(false);
		rect2.setVisible(false);
		rect3.setVisible(false);
		rect4.setVisible(false);
		rect5.setVisible(false);
		
		//Create arrow
		arrowImage.setVisible(false);
		arrow = new Arrow();
		
		//Create bow
		bow = new Bow();
		bowImage.toFront();
		
		//Create player
		player = new Player();
		player.setPosX(startPosX);
		player.setPosY(startPosY);
		
		// Create new target
		target = new Target(targetX + rangeX * Math.random(), targetY + rangeY * Math.random(), targetWidth,
				targetHeight);
		targetImage.setLayoutX(target.getPosX());
		targetImage.setLayoutY(target.getPosY());

		// Create bomb
		bomb = new Target(targetX + rangeX * Math.random(), targetY + rangeY * Math.random(), targetWidth,
				targetHeight);
		bombImage.setLayoutX(bomb.getPosX());
		bombImage.setLayoutY(bomb.getPosY());

		counter = new Counter(-1);

		targetList = new ArrayList<>(Arrays.asList(targetArr));
		imageList = new ArrayList<>(Arrays.asList(imageArr));
		targetList.add(target);
		targetList.add(bomb);
		imageList.add(targetImage);
		imageList.add(bombImage);

		// Create platform
		platform = new Platform(platformX, platformY, platformWidth, platformHeight);
		ground.setLayoutX(platform.getPosX());
		ground.setLayoutY(platform.getPosY());
		
		// New save
		save = new Save();
		try {
			counter.setHighscore(save.loadFromFile("savefile.txt"));
		} catch (IOException e) {
			counter.setHighscore(0);
		}
		highscoreLabel.setText("Highscore: " + counter.getHighscore());
		
//		List<Platform> platformList = new ArrayList<Platform>();
//		
//		Platform platform1 = new Platform(80, 141, 200, 75);
//        rect1.setLayoutX(platform1.getPosX());
//		rect1.setLayoutY(platform1.getPosY());
//		platformList.add(platform1);
//		Platform platform2 = new Platform(409, 391, 143, 171);
//        rect2.setLayoutX(platform2.getPosX());
//		rect2.setLayoutY(platform2.getPosY());
//		platformList.add(platform2);
//		Platform platform3 = new Platform(551, 178, 200, 384);
//        rect3.setLayoutX(platform3.getPosX());
//		rect3.setLayoutY(platform3.getPosY());
//		platformList.add(platform3);
//		Platform platform4 = new Platform(750, 391, 143, 171);
//        rect4.setLayoutX(platform4.getPosX());
//		rect4.setLayoutY(platform4.getPosY());
//		platformList.add(platform4);
//		Platform platform5 = new Platform(1000, 141, 200, 75);
//        rect5.setLayoutX(platform5.getPosX());
//		rect5.setLayoutY(platform5.getPosY());
//		platformList.add(platform5);

		new AnimationTimer() {

			@Override
			public void handle(long arg0) {

				if (arrowShot) {
					arrow.fall();
					arrowAngle = Math.atan(arrow.getSpeedY() / arrow.getSpeedX()) + trigValue;
					arrowImage.setRotate(arrowStartAngle + arrowAngle * 180 / Math.PI);

					// if target hit
					if (arrow.getPosX() > target.getPosX() && arrow.getPosX() < target.getPosX() + target.getWidth()
							&& arrow.getPosY() > target.getPosY()
							&& arrow.getPosY() < target.getPosY() + target.getHeight()) {
						targetImage.setVisible(false);
						// targetHit = true;
						resetTarget(target, targetImage);
						count();
					}
					if (arrow.getPosX() > bomb.getPosX() && arrow.getPosX() < bomb.getPosX() + bomb.getWidth()
							&& arrow.getPosY() > bomb.getPosY()
							&& arrow.getPosY() < bomb.getPosY() + bomb.getHeight()) {
						bombImage.setVisible(false);
						resetButton();
					}

				}

				arrow.fly();

				// Set bow to player
				bow.setPosX(player.getPosX() + player.getWidth()/3);
				bow.setPosY(player.getPosY() );

				// Update image/ animate
				arrowImage.setLayoutX(arrow.getPosX());
				arrowImage.setLayoutY(arrow.getPosY());

				playerImage.setLayoutX(player.getPosX());
				playerImage.setLayoutY(player.getPosY());

				bowImage.setLayoutX(bow.getPosX());
				bowImage.setLayoutY(bow.getPosY());

				// vertical collision
				player.handleVerticalCollision(platform);
				
				//horisontal collision
				
//				for(Platform p: platformList) {
//					player.handleHorisontalCollision(p);
//					player.handleVerticalCollision(p);
//				}
				
				
				//when player is not in the wall
				if(player.isPlayerFree()) {
					player.fall();
					player.fly();
				}

			}
		}.start();

	}

	@FXML
	public void buttonPress(MouseEvent event) {
		pressX = event.getSceneX();
		pressY = event.getSceneY();
		if (bowSelected) {
			bow.cockBow(arrow);
			arrowImage.setVisible(true);
			arrowShot = false;
		} 
	}

	@FXML
	public void buttonReleased(MouseEvent event) {
		releasedX = event.getSceneX();
		releasedY = event.getSceneY();
		power = Math.sqrt((pressX - releasedX) * (pressX - releasedX) + (pressY - releasedY) * (pressY - releasedY));

		if (bowSelected) {
			bow.shootBow(arrow, power, mouseAngle);
			arrowShot = true;
		} else {
			if (power > 100) {
				power = 100;
			}

			player.launchPlayer(power, mouseAngle);
			player.fall();
		}

	}

	@FXML
	public void mouseDragged(MouseEvent event) {
		dragX = event.getSceneX();
		dragY = event.getSceneY();
		if (dragX < pressX) {
			trigValue = 0;
		} else {
			trigValue = Math.PI;
		}
		mouseAngle = Math.atan((pressY - dragY) / (pressX - dragX)) + trigValue;
		
//		redArrowImage.setPreserveRatio(false);
//		redArrowImage.setFitWidth(pressX - dragX);

		if (bowSelected) {
			arrowAngle = Math.atan(arrow.getSpeedY() / arrow.getSpeedX()) + trigValue;
			bowImage.setRotate(mouseAngle * 180 / Math.PI);
			arrowImage.setRotate(arrowStartAngle + mouseAngle * 180 / Math.PI);
		} else {

		}
	}

	@FXML
	public void count() {
		counter.count();
		textField.setText("Balloons hit: " + counter.getCounter());
		if(counter.getCounter() > counter.getHighscore()) {
			counter.setHighscore(counter.getCounter());
			try {
				save.writeToFile("savefile.txt", this, counter);
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		

	}

	@FXML
	public void resetButton() {
//		for (int i = 0; i < targetList.length-1; i++) {
//		    resetTarget(targetList[0], imageList[0] );
//		}
		resetTarget(target, targetImage);
		resetTarget(bomb, bombImage);
		
		counter.setCounter(0);
		textField.setText("Balloons hit: " + counter.getCounter());
		highscoreLabel.setText("Highscore: "+ counter.getHighscore());

		player.setPosX(startPosX);
		player.setPosY(startPosY);
		player.setSpeedX(0);
		player.setSpeedY(0);

	}

	@FXML
	public void toggleBow() {
		bowSelected = true;
	}
	
	@FXML
	public void toggleThrow() {
		bowSelected = false;
	}

	public void resetTarget(Target target, ImageView image) {
		target.setPosX(targetX + rangeX * Math.random());
		target.setPosY(targetY + rangeY * Math.random());
		image.setLayoutX(target.getPosX());
		image.setLayoutY(target.getPosY());
		image.setVisible(true);

	}

}