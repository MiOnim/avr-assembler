/**
 * 
 * @author MI ONIM
 *
 */
public class Mnemonic
{
	protected String _name;
	protected String _opcode;
	
	public Mnemonic(String name, String opcode)
	{
		_name = name;
		_opcode = opcode;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getOpcode()
	{
		return _opcode;
	}
	
	public boolean operandDNeeded()
	{
		return (_opcode.indexOf('d') >= 0);
	}
	
	public boolean operandRNeeded()
	{
		return (_opcode.indexOf('r') >= 0);
	}
	
	public boolean operandKNeeded()
	{
		return (_opcode.indexOf('k') >= 0);
	}
	
	public int countOperandsNeeded()
	{
		return (operandDNeeded() ? 1 : 0) +
			   (operandRNeeded() ? 1 : 0) +
			   (operandKNeeded() ? 1 : 0);
	}
}
