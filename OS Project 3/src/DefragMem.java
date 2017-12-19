import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class DefragMem 
{
	public static boolean defrag(LinkedList<PCB> OpenMem)
	{
		boolean defragged = false;
		Iterator<PCB> MemIterator = OpenMem.iterator();
		List<PCB> removedList = new LinkedList<PCB>();
		PCB head = OpenMem.getFirst();
		PCB tail = null;
		LinkedList<PCB> OpenList = new LinkedList<PCB>();
		
		if(OpenMem.isEmpty())
		{
			defragged = true;
			return defragged;
		}
		
		while(MemIterator.hasNext())
		{
			tail = MemIterator.next();
			if(head.get_Limit() == tail.get_Base())
			{
				head.set_Limit(tail.get_Limit());
				removedList.add(tail);
			}
			else
			{
				OpenList.add(head);
				head = tail;
			}
		}
		
		if(!removedList.isEmpty())
		{
			OpenMem.removeAll(removedList);
			defragged = true;
		}
		
		return defragged;
	}
}
