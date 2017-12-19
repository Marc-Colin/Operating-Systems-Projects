import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class DiskScheduler
{
	
	Random random__X = new Random();
	private List<Integer> diskRequests = new ArrayList<Integer>();
	private int startPosition = 0;
	private int neededPosition = 0;
	private int movement = 0;
	
	public DiskScheduler()
	{
		startPosition = random__X.nextInt(500) + 1;
		
		for(int i = 0; i < 10; i++)
		{
			Integer tempValue = random__X.nextInt(500) + 1;
			while(diskRequests.contains(tempValue))
			{
				tempValue = random__X.nextInt(500) + 1;
			}
			diskRequests.add(tempValue);
		}
		
		neededPosition = diskRequests.get(diskRequests.size() - 1);
		
		if(startPosition != neededPosition)
		{
			SSTF(startPosition, neededPosition, diskRequests);
		}
	}
	
	public void SSTF(int startPosition, int neededPosition, List<Integer> diskRequests)
	{
		int totalMovement = 0;
		boolean complete = false;
		List<Integer> tempList = new ArrayList<Integer>(diskRequests);
		Collections.sort(tempList);
		Integer headPosition = startPosition;
		Integer required = null;
		Integer tempPosition = null;
		
		while(!complete)
		{
			for(Integer position : tempList)
			{
				tempPosition = position;
				if(required == null)
				{
					required = tempPosition;
				}
				else if(Math.abs(headPosition - tempPosition) < Math.abs(headPosition - required))
				{
					required = tempPosition;
				}
			}
			
			totalMovement += Math.abs(headPosition - required);
			headPosition = required;
			tempList.remove(required);
			required = null;
			
			if(headPosition.intValue() == neededPosition)
			{
				complete = true;
			}
		}
		
		
		movement = totalMovement;
	}
	
	public String toString()
	{
		return "Start Position: " + startPosition 
				+ "\nNeeded Position: " + neededPosition 
				+ "\nSSTF: " + movement;
	}

}
