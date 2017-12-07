import java.util.*;

public class Driver 
{
	public static void main(String args[]) 	
	{
	int QREADY__T = 25;

	Random random__X = new Random();
    LinkedList<PCB> QReady = new LinkedList<PCB>();	
    LinkedList<PCB> QWaiting = new LinkedList<PCB>();
	PCB PCB_Running; 
	CPU_Event randEvent = new CPU_Event();
	int CPU;
	int Event;
	int count = 0;
	
	for (int ii = 0; ii < QREADY__T; ii++)	
	{
		QReady.add(new PCB()) ;
	}
	while(QReady.size() > 0 || QWaiting.size() > 0)
	{	
		count++;
		System.out.println("\n\n\t\t*****\t" + "Cycle " + count + "\t  *****"	+ "\n");
		
		// Displays the Ready List
		if(QReady.size() > 0)
		{
			System.out.println("\n*****\t" + "Ready Queue" + "\t*****"	+ "\n");
			for(int ii = 0; ii < QReady.size(); ii++)
			{
				System.out.println(QReady.get(ii).showPCB() + "\n");
			}
		}
		// Displays the Waiting List
		if(QWaiting.size() > 0)
		{
			System.out.println("\n*****\t" + "Waiting Queue" + "\t*****"	+ "\n");
			for(int ii = 0; ii < QWaiting.size(); ii++)
			{
				System.out.println(QWaiting.get(ii).showPCB() + "\n");
			}
		}
		
		if(count % 4 == 0)
		{
			if(QWaiting.size() > 0)
			{
				System.out.println("\t\tProcess " + QWaiting.get(0).get_ID() + ": I/O completed");
				QReady.addFirst(QWaiting.removeFirst());
			}
		}
		
		System.out.println("\t\tRemove PCB to run.");
		
		// Checks Ready Lists size to see if something needs to be removed
		if(QReady.size() > 0)
		{
			// Removes first PCB and changes state and CPU used
			PCB_Running = QReady.removeFirst();
			PCB_Running.set_state("Running");
			CPU = random__X.nextInt(50) + 1;
			PCB_Running.addCPU(CPU);
			// Checks Waiting and Ready Lists and changes their PCBs waiting time
			if(QReady.size() > 0)
			{
				for(int ii = 0; ii < QReady.size(); ii++)
				{
					QReady.get(ii).set_timeWaiting(CPU);
				}
			}
			if(QWaiting.size() > 0)
			{
				for(int ii = 0; ii < QWaiting.size(); ii++)
				{
					QWaiting.get(ii).set_timeWaiting(CPU);
				}
			}
			// Displays the PCB running
			System.out.println("\n*****\t" + "Running Queue" + "\t*****"	+ "\n");
			System.out.println(PCB_Running.showPCB());
			
			if(PCB_Running.get_CPU_used() < PCB_Running.get_CPU_max())
			{
				// Calls for a random event
				Event = randEvent.getEvent();
				// Event 5 result
				if(Event == 5)
				{
					System.out.println("\t\tSystem Event: Other");
					PCB_Running.set_state("Ready");
					if(PCB_Running.get_CPU_max() - PCB_Running.get_CPU_used() == 1)
					{
						QReady.addFirst(PCB_Running);
					}
					else if(PCB_Running.get_CPU_max() - PCB_Running.get_CPU_used() <= (PCB_Running.get_CPU_max()/2))
					{
						QReady.add((QReady.size()/2), PCB_Running);
					}
					else
						QReady.add(PCB_Running);
				}
				// Event 4 result
				else if(Event == 4)
				{
					System.out.println("\t\tSystem Event: Block I/O");
					PCB_Running.set_state("Waiting");
					QWaiting.add(PCB_Running);
				}
				// Event 3 result
				else if(Event == 3)
				{
					System.out.println("\t\tSystem Event: Page I/O");
					PCB_Running.set_state("Ready");
					if(PCB_Running.get_CPU_max() - PCB_Running.get_CPU_used() == 1)
					{
						QReady.addFirst(PCB_Running);
					}
					else if(PCB_Running.get_CPU_max() - PCB_Running.get_CPU_used() <= (PCB_Running.get_CPU_max()/2))
					{
						QReady.add((QReady.size()/2), PCB_Running);
					}
					else
						QReady.add(PCB_Running);
				}
				// Event 2 result
				else if(Event == 2)
				{
					System.out.println("\t\tSystem Event: Interrupt");
					PCB_Running.set_state("Ready");
					if(PCB_Running.get_CPU_max() - PCB_Running.get_CPU_used() == 1)
					{
						QReady.addFirst(PCB_Running);
					}
					else if(PCB_Running.get_CPU_max() - PCB_Running.get_CPU_used() <= (PCB_Running.get_CPU_max()/2))
					{
						QReady.add((QReady.size()/2), PCB_Running);
					}
					else
						QReady.add(PCB_Running);
				}
				// Event 1 result
				else if(Event == 1)
				{
					System.out.println("\t\tSystem Event: Process terminated");
				}
			}
			// PCB has completed
			else
			{
				System.out.println("\t\tProcess " + PCB_Running.get_ID() + " Completed");
			}
		}
	}
	
	System.out.println("\n\t\tAll Processes Completed");
  }
}
