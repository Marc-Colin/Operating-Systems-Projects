import java.util.LinkedList;

public class OpenMem 
{

	public OpenMem ()
	{}
	
	public boolean findOpenMem	(PCB PCB_Ready, LinkedList<PCB> QMemOpen, LinkedList<PCB> QMemUsed	)
	{
		boolean memFound__B = false;
		
		for(PCB current:QMemUsed)
		{
			if(PCB_Ready.get_ID() == current.get_ID())
			{
				memFound__B = true;
				return memFound__B;
			}
		}
			
		int memNeed = PCB_Ready.get_Limit();
		
		for (int ii = 0; ii < QMemOpen.size(); ii ++)
		{
			PCB memOpen = QMemOpen.get(ii);
			int base = memOpen.get_Base();
			int limit = memOpen.get_Limit();
			if ((limit - base) > memNeed)
			{
				memFound__B = true;
				
				//Split the open memory	in QMemOpen 
				memOpen.set_Base(base + memNeed);
				
				//Replace the open memory
				PCB_Ready.set_Base(base);
				PCB_Ready.set_Limit(base + memNeed);
				
				//Allocate the used memory in QMemUsed
				QMemUsed.add(PCB_Ready);	
	
				//Push the used memory
				System.out.println("\n-----\t\t\tBase added:" + PCB_Ready.get_Base() + " limit: " + PCB_Ready.get_Limit() + " to Used Memory");	
					
				break ;	// exit out of the FOR loop for memory
			}
		}
		return memFound__B ;
	}
}