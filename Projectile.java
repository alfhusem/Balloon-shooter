package app;

public interface Projectile {
	
	public static final int gravity = 10;
	public static final int terminalV = 100;
	public static final double timeConst = 0.02;

	public double getSpeedX();

	public void setSpeedX(double speedX);

	public double getSpeedY();

	public void setSpeedY(double speedY);

	public double getPosX();

	public void setPosX(double posX);

	public double getPosY();

	public void setPosY(double posY);

	public void fall();
	
	public void fly();

}
