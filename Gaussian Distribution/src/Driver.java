
public class Driver 
{
	public static void main(String[] args)
	{
		getEvent Event = new getEvent();
		int eventNum;
		int timesDone = 1000;
		int event1 = 0;
		int event2 = 0;
		int event3 = 0;
		int event4 = 0;
		int event5 = 0;
		
		for(int i = 0; i < timesDone; i++)
		{
			eventNum = Event.Event();
			
			if(eventNum == 1)
			{
				event1++;
			}
			if(eventNum == 2)
			{
				event2++;
			}
			if(eventNum == 3)
			{
				event3++;
			}
			if(eventNum == 4)
			{
				event4++;
			}
			if(eventNum == 5)
			{
				event5++;
			}
		}
		
		System.out.println("Event 1: " + ((event1 * 100) / timesDone) + "%");
		System.out.println("Event 2: " + ((event2 * 100) / timesDone) + "%");
		System.out.println("Event 3: " + ((event3 * 100) / timesDone) + "%");
		System.out.println("Event 4: " + ((event4 * 100) / timesDone) + "%");
		System.out.println("Event 5: " + ((event5 * 100) / timesDone) + "%");
	}
}

