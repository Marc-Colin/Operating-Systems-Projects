import java.util.*;

public class getNum 
{
	Random rand = new Random();
	int randNum;
	int returnNum;
	
	
	public int getEvent()
	{
		randNum = rand.nextInt(100) + 1;
		
		if(randNum >= 1 && randNum <= 5)
		{
			returnNum = 1;
		}
		else if(randNum >= 6 && randNum <= 15)
		{
			returnNum = 2;
		}
		else if(randNum >= 16 && randNum <= 30)
		{
			returnNum = 3;
		}
		else if(randNum >= 31 && randNum <= 50)
		{
			returnNum = 4;
		}
		else if(randNum >= 51 && randNum <= 100)
		{
			returnNum = 5;
		}
		return returnNum;
	}
}


