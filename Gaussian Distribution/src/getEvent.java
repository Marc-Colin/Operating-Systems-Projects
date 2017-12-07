import java.util.*;

public class getEvent 
{
	Random rand = new Random();
	double randNum;
	int returnNum;
	
	public int Event()
	{
		randNum = rand.nextGaussian();
		
		if(randNum >= 1.645)
		{
			returnNum = 1;
		}
		else if(randNum >= 1.036)
		{
			returnNum = 2;
		}
		else if(randNum >= 0.524)
		{
			returnNum = 3;
		}
		else if(randNum >= 0)
		{
			returnNum = 4;
		}
		else 
		{
			returnNum = 5;
		}
		return returnNum;
	}
}
