public class Accelerometer
{
  public byte x_value;
  public byte y_value;
  public byte z_value;

  public Accelerometer(byte x_val, byte y_val, byte z_val)
	{
		x_value = x_val;
		y_value = y_val;
		z_value = z_val;
	}

  public void setX(byte newX)
	{
		x_value = newX;
	}

	public byte getX()
	{
		return x_value;
	}

	public void setY(byte newY)
	{
		y_value = newY;
	}

	public byte getY()
  {
    return y_value;
  }

	public void setZ(byte newZ)
	{
		z_value = newZ;
	}

	public byte getZ()
  {
    return z_value;
  }

	public void printDirection()
	{
		String output = "";

    if (z_value > 85)
      output += "UP";
    else if (z_value < 35)
      output += " DOWN ";

    if (x_value > 45)
      output += " LEFT";
    else if (x_value < -45)
      output += " RIGHT";

    if (!output.equals(""))
      System.out.println("Motion deteted in the following directions: " + output);
	}

	public boolean motionDetected()
	{
		return (z_value > 85) || (z_value < 35) || (x_value > 45) || (x_value < -45);
	}
}
