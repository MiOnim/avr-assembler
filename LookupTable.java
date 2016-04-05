package assembler;

import java.util.HashMap;

/**
 * 
 * @author MI ONIM
 *
 */
public class LookupTable
{
	public static final String ERROR_MESSAGE = "Lookup Table contains error.";
	
	protected HashMap<String, String> _opcodes;
	
	public LookupTable() throws AssemblerException
	{
		_opcodes = new HashMap<>(100);  //initial capacity
		putOpcode("add" , "000011rdddddrrrr");
		putOpcode("and" , "001000rdddddrrrr");
		putOpcode("brne", "111101kkkkkkk001");
		putOpcode( "cp" , "000101rdddddrrrr");
		putOpcode("dec" , "1001010ddddd1010");
		putOpcode( "ld" , "1001000ddddd1100");       //without post/pre increment
		putOpcode("ldi" , "1110kkkkddddkkkk");
		putOpcode("mov" , "001011rdddddrrrr");
		putOpcode( "st" , "1001001rrrrr1100");       //store using X register
		putOpcode("sub" , "000110rdddddrrrr");
	}
	
	public void putOpcode(String mnemonic, String opcode) throws AssemblerException
	{
		mnemonic = mnemonic.toLowerCase();
		opcode = opcode.toLowerCase();
		
		String specificMessage = ": \"" + mnemonic + "\"";
		
		if (mnemonic.length() < 2 || mnemonic.length() > 6) {
			specificMessage = "Invalid length for mnemonic" + specificMessage;
			throw new AssemblerException(ERROR_MESSAGE + " " + specificMessage);
		} //if
		
		if (!Utils.isAlpha(mnemonic)) {
			specificMessage = "Mnemonic can contain only letters" + specificMessage;
			throw new AssemblerException(ERROR_MESSAGE + " " + specificMessage);
		} //if
		
		if (opcode.length() != 16) {
			specificMessage = "Invalid Opcode length for mnemonic" + specificMessage;
			throw new AssemblerException(ERROR_MESSAGE + " " + specificMessage);
		} //if
		
		for (int i = 0; i < opcode.length(); i++) {
			char c = opcode.charAt(i);
			if (i < 4 && c != '0' && c != '1') {
				specificMessage = "First four digits of Opcode can contain only " +
				                  "'0' and '1' for mnemonic" + specificMessage;
				throw new AssemblerException(ERROR_MESSAGE + " " + specificMessage);
			} //if
			else if (c != '0' && c != '1' && c != 'd' && c != 'r' && c != 'k') {
				specificMessage = "Opcode can contain only '0', '1', 'd', 'r' and 'k' " + 
								  "for mnemonic" + specificMessage;
				throw new AssemblerException(ERROR_MESSAGE + " " + specificMessage);
			} //elseif
		} //for
		
		_opcodes.put(mnemonic, opcode);
	}
	
	public String getOpcode(String mnemonic)
	{
		return _opcodes.get(mnemonic);
	}
	
	public boolean hasMnemonic(String mnemonic)
	{
		return _opcodes.containsKey(mnemonic);
	}
}
