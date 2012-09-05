package chb.mods.mffs.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ForceFieldBlockStack {
	private int x;
	private int y;
	private int z;
	private boolean sync;
	public Queue<ForceFieldBlock> blocks = new LinkedList<ForceFieldBlock>();

	public ForceFieldBlockStack(int x,int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		sync = false;
	}

	public int getsize()
	{
		return blocks.size();
	}

	public void removeBlock()
	{
		blocks.poll();
	}

	public synchronized  void removebyProjector(int projectorid)
	{
		ArrayList<ForceFieldBlock> tempblock = new ArrayList<ForceFieldBlock>();

	  for (ForceFieldBlock ffblock : blocks)
	  {
		  if(ffblock.Projektor_ID == projectorid){
				  tempblock.add(ffblock);
		  }
	  }
	  if(!tempblock.isEmpty())
	  blocks.removeAll(tempblock);
	}

	public int getGenratorID(){
		ForceFieldBlock ffblock =blocks.peek();
		if(ffblock != null){
			return ffblock.Generator_Id;
		}
		return 0;
	}

	public int getProjectorID(){
		ForceFieldBlock ffblock =blocks.peek();
		if(ffblock != null){
			return ffblock.Projektor_ID;
		}
		return 0;
	}

	public int getTyp(){
		ForceFieldBlock ffblock =blocks.peek();
		if(ffblock != null){
			return ffblock.typ;
		}
		return -1;
	}

	public void setSync(boolean sync){
		this.sync = sync;
	}

	public boolean isSync(){
		return sync;
	}

	public boolean isEmpty(){
		return blocks.isEmpty();
	}

    public ForceFieldBlock get()
    {
    		return blocks.peek();
    }

    public void add(int Generator_Id, int Projektor_ID, int typ)
    {
    	blocks.offer(new ForceFieldBlock(Generator_Id,Projektor_ID,typ));
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}
