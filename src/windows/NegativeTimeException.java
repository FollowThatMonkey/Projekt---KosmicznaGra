package windows;
//Z
//exception - choosing time limit
public class NegativeTimeException extends Exception
{

	public NegativeTimeException()
	{
		
	}

	public String toString()
	{
		return "Time has to be a positive integer!";
	}
}
