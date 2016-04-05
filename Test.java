package assembler;

public class Test
{
	public static void main(String[] args)
	{
		try {
			Assembler test = new Assembler(args[0]);
			test.run();
		}
		catch (AssemblerException e) {
			System.err.println(e.getMessage());
		}
		catch (Exception e) {
			System.err.println("Unexpected error encountered. Please try again.");
		}
	}
}
