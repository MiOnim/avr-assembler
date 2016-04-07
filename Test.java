public class Test
{
	public static void main(String[] args)
	{
		try {
			Assembler test = new Assembler("testAssembler.txt");
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
