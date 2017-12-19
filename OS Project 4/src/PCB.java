import java.util.Random;

public class PCB implements Comparable<PCB>
{
	private String 	PCB_state;
	private int		PCB_ID ;
	private int		CPU_used;
	private int		CPU_max;
	private int		timeWaiting;
	private int     memLimit;
	private int     memBase;
	
	private static int	PCB__K;
	Random random__X = new Random();
		
	public PCB ()
	{
		PCB__K+= 1 ;
		PCB_state	= "Ready" ;
		PCB_ID		= PCB__K ; 
		CPU_used	= 0 ;
		CPU_max		= random__X.nextInt(100) + 1;
		memBase     = 0;
		memLimit   = random__X.nextInt(50) + 26;
		timeWaiting	= 0 ;
	}	

	public PCB (int memLim)
	{
		PCB_state	= "MEMEROY" ;
		PCB_ID		= 0; 
		CPU_used	= 0;
		CPU_max		= 0;
		memBase     = 0;
		memLimit   = memLim;
		timeWaiting	= 0 ;
	}	
	
	public String showPCB()
	{
		return "State: " + PCB_state
			+ "\tID: "	+ Integer.toString(PCB_ID)
			+ "\tCPU used: "	+ Integer.toString(CPU_used)	
			+ "\tCPU max: "	+ Integer.toString(CPU_max)					
			+ "\tWait: "	+ Integer.toString(timeWaiting)
			;
	}
	
	public int get_Limit()
	{
		return memLimit;
	}
	
	public int get_Base()
	{
		return memBase;
	}
	
	public void set_Limit(int limit)
	{
		memLimit = limit;
	}
	
	public void set_Base(int base)
	{
		memBase = base;
	}
	
	public String get_state()
	{
		return PCB_state;
	}
	
	public int get_ID()
	{
		return PCB_ID;
	}

	public int get_CPU_used()
	{
		return CPU_used;
	}
	
	public int get_CPU_max()
	{
		return CPU_max;
	}	

	public int get_timeWaiting()
	{
		return timeWaiting;
	}	

	public void set_state(String state0)
	{
		PCB_state = state0;
	}

	public void set_CPU_used(int CPU0)
	{
		CPU_used = CPU0;
	}
	
	public void set_CPU_max(int CPU0)
	{
		CPU_max	= CPU0;
	}

	public void set_timeWaiting(int timeWaiting0)
	{
		timeWaiting	+= timeWaiting0;
	}
	
	public void addCPU(int CPU)
	{
		CPU_used += CPU;
	}
	
	public int compareTo(PCB tempPCB)
	{
		int memTemp = tempPCB.get_Base();
		return this.memBase - memTemp;
	}
}
