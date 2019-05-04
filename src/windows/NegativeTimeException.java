package windows;

public class NegativeTimeException extends Exception
{

	public NegativeTimeException()
	{
		
	}

	public String toString()
	{
		return "Time can't be negative!";
	}
}
