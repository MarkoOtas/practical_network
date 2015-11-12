public class StackArrayBased implements StackInterface
{
	final int MAX_SIZE = 5;
	private Object items[];
	private int top;
  
	public StackArrayBased()
	{
		items = new Object[MAX_SIZE]; 
		top = -1; 
	}

	public boolean isEmpty()
	{
		return top < 0;
	}

	public boolean isFull()
	{
		return top == MAX_SIZE-1;
	}

	public void push(Object newItem) throws StackException
	{
		if (!isFull())
			items[++top] = newItem;
		else
			throw new StackException("StackException on " + "push: stack full");
	}

	public void popAll()
	{
		items = new Object[MAX_SIZE]; 
		top = -1; 
	}
  
	public Object pop() throws StackException
	{
		if (!isEmpty())
			return items[top--];
		else
			throw new StackException("StackException on " + "pop: stack empty");
	}

	public Object peek() throws StackException
	{
		if (!isEmpty())
			return items[top];
		else
			throw new StackException("Stack exception on " + "peek - stack empty");
	}
}
