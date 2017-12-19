import java.util.Collections;
import java.util.LinkedList;
import java.util.Random; 
import java.util.List;

public class ProcessMgmt 
{
	public static void main(String args[]) 	
	{
		//Initialization of fields and data structures
		int count = 1;
		int QREADY__T = 5;
		final int RAM = 256;
		final int COMPLETED	= 1; 
		final int INTERRUPT	= 2;
		final int BLOCKIO	= 3;						
		final int BLOCKPAGE	= 4;
				
		Random random__X	= new Random();
		CPU_Event event		= new CPU_Event();
		
		int CPU_runtime;		
		int event__X;
		
		//Create the List for QReady
		LinkedList<PCB> QReady	= new LinkedList<PCB>();	
		//Create the List for QWaiting
		LinkedList<PCB> QWaiting = new LinkedList<PCB>();
		//Create the list for QMemOpen
		LinkedList<PCB> QMemOpen = new LinkedList<PCB>();
		//Create the list for QMemUsed
		LinkedList<PCB> QMemUsed = new LinkedList<PCB>();
		//Create a field for PCB_Running
		PCB PCB_Running = null;
		//Create a field for PCB_Switch
		PCB PCB_Switch = null;
		//Set the QMemOpen
		QMemOpen.add(new PCB(RAM));
		//Create an OpenMem object
		OpenMem memOpen = new OpenMem();
		
		for (int ii = 0; ii < QREADY__T; ii++)
		{
			//Add a new PCB object onto QReady
			QReady.add(new PCB()) ;		 
		}

		//Display the QReady List
		System.out.println("\n*****\t\t\tReady Queue\t\t\t*****");  
		for (PCB pcbLoop : QReady)
		{
			System.out.println(pcbLoop.showPCB());
		}
		
		//Loops until all PCBs are completed
		while (!QReady.isEmpty() || !QWaiting.isEmpty())
		{
			//Run the next process
			if(!QReady.isEmpty())
			{
				//Assign the first node from QReady to Running
				PCB_Running = QReady.removeFirst();
				if(memOpen.findOpenMem(PCB_Running, QMemOpen, QMemUsed))
				{
					//Set the state value to "Running"
					PCB_Running.set_state("Running") ;			
			
					//Get a random number between 0 and 20
					CPU_runtime	= random__X.nextInt(20) + 1 ;	
					//Tracks the CPU used for the process
					PCB_Running.addCPU(CPU_runtime);

					System.out.println("\n*****\t\t\tRunning Process\t\t\t*****");   	  
					System.out.println(PCB_Running.showPCB());

					//Tracks the wait times for the processes in QReady and in QWaiting
					for(PCB pcbLoop : QReady)
					{
						pcbLoop.set_timeWaiting(CPU_runtime);
					}
					for(PCB pcbLoop : QWaiting)
					{
						pcbLoop.set_timeWaiting(CPU_runtime);
					}	
			
					//Check if the process completed (CPU used exceeds the CPU max)
					if (PCB_Running.get_CPU_used() > PCB_Running.get_CPU_max())
					{
						PCB_Running.set_state("Completed");
						System.out.println("\n>>>>>\tProcess\t" + PCB_Running.showPCB() + "\t<<<<<" );
						continue;	//Iterate to the next in the WHILE loop
					}
			
					//If PCB did not complete then CPU event initiated
					event__X = event.getEvent() ;
			
					if (event__X == COMPLETED)
					{
						System.out.println("CPU Event: Completed");
						PCB_Running.set_state("Completed");
						System.out.println("\n>>>>>\tProcess\t" + PCB_Running.showPCB() + "\t<<<<<" );
					}
					else
					{
						//Set the state to "Ready" from "Running"
						PCB_Running.set_state("Ready");	
			
						switch (event__X)
						{
							case 2 :
							{
								//Add the PCB to the START of the QReady
								System.out.println("*****\t\t\tContext Switch\tINTERRUPT event\t\t\t*****");
								QReady.addFirst(PCB_Running);
								break;
							}				
							case 3 :
							{	
								//Add the PCB to the middle of QReady	
								System.out.println("*****\t\t\tContext Switch\tPAGE event\t\t\t*****");		
								QReady.add((QReady.size()/2), PCB_Running);
								break;
							}
							case 4 :
							{
								//Add to QWaiting
								System.out.println("*****\t\t\tContext Switch\tI/O event\t\t\t*****");
								PCB_Running.set_state("Waiting");
								PCB_Running.setDiskRequest(new DiskScheduler());
								QWaiting.add(PCB_Running);
								break;
							}
							default :
							{
								System.out.println("*****\t\t\tContext Switch\tTIME event\t\t\t*****");
								QReady.addLast(PCB_Running);
								break;
							}
						}
					}
				}
				//If no memory is available
				else
				{
					System.out.printf("##### No Memory Available for Process %d\tneeding:%d\n", PCB_Running.get_ID(), PCB_Running.get_Limit());
					QReady.addLast(PCB_Running);
				}
				//Remove PCB from QWaiting
				if((count % 4 == 0) && !QWaiting.isEmpty())
				{
					PCB_Switch = QWaiting.removeFirst();
					PCB_Switch.set_state("Ready");
					System.out.println("********Process " + PCB_Switch.get_ID() + " finished Disk Request********");
					System.out.println(PCB_Switch.showPCB());
					System.out.println(PCB_Switch.getDiskRequest().toString());
					QReady.addFirst(PCB_Switch);
				}
				//Display QReady
				System.out.println("\n*****\t\t\t\tReady Queue\t\t\t*****");   
				for (PCB pcbLoop : QReady)
				{
					System.out.println(pcbLoop.showPCB());
				}
				//Display QWaiting
				System.out.println("\n*****\t\t\t\tWaiting Queue\t\t\t*****");   
				for (PCB pcbLoop : QWaiting)
				{
					System.out.println(pcbLoop.showPCB());
				}
				count++;
			}
			//If QReady is empty
			else if(!QWaiting.isEmpty())
			{
				PCB_Switch = QWaiting.removeFirst();
				PCB_Switch.set_state("Ready");
				QReady.addFirst(PCB_Switch);
			}
		}
		//All Processes are completed
		System.out.println("\n*****\t\t\tALL PROCESSES COMPLETED\t\t\t*****");
	}
	
	//Method to allocate memory
	private static void AllocateMem(PCB PCB_Running, LinkedList<PCB> QMemOpen, LinkedList<PCB> QMemUsed)
	{
		QMemUsed.remove(PCB_Running);
		QMemOpen.add(PCB_Running);
		System.out.println("\n-----\t\t\tAdded Process: " + PCB_Running.get_ID() + " Base:" + PCB_Running.get_Base() + " Limit: " + PCB_Running.get_Limit() + " to Open Memory");
		Collections.sort((List)QMemOpen);
		
		for(PCB pcb : QMemOpen)
		{
			System.out.println("QMemOpen Sorted");
		}
		
		if(DefragMem.defrag(QMemOpen))
		{
			System.out.println("QMemOpen Defragmented");
			for(PCB pcb : QMemOpen)
			{
				System.out.println("QMemOpen Sorted");
			}
		}
		
		System.out.println("\n*****\t\t\tMemory Used\t\t\t*****");
		for(PCB pcb : QMemUsed)
		{
			System.out.println("Used: \n" + pcb.showPCB());
		}
	}
}