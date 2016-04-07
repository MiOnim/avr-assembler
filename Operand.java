/**
 * 
 * @author MI ONIM
 *
 */
public class Operand
{
	public static final String ERROR_MESSAGE = "Invalid Operand format";
	
	protected String _operand;
	protected char _type;   //can be either 'd' or 'r'; or 'k' for OperandConstant
	
	public Operand(String operand, char type) throws AssemblerException
	{
		setOperand(operand);
		_type = type;
	}
	
	public int intValue(int base) throws AssemblerException
	{
		if (!Utils.isInteger(_operand)) {
			throw new AssemblerException(ERROR_MESSAGE);
		} //if
		
		return Integer.parseInt(_operand, base);
	}
	
	public void setOperand(String operand) throws AssemblerException
	{
		if (!operand.toLowerCase().startsWith("r")) {
			throw new AssemblerException(ERROR_MESSAGE);
		} //if
		
		_operand = operand.substring(1);   //remove 'r' from the beginning
		
		if (intValue(10) < 0 || intValue(10) > 31) {
			throw new AssemblerException(ERROR_MESSAGE);
		} //if
	}
	
	public String replaceOpcode(String binary)
	{
		/* number of digits to replace */
		int countBits = binary.length() - binary.replace(String.valueOf(_type), "").length();
		
		/* convert opcode to 16-bit binary number */
		String opcodeBinary = String.format("%16s", Integer.toBinaryString(Integer.valueOf(_operand))).replace(" ", "0");
		
		/* start replacing from the end because the binary number to replace
		 * with contains a number of zeros in the beginning
		 */
		int indexToReplace = opcodeBinary.length() - 1;
		
		for (int i = countBits; i > 0; i--) {
			int lastReg = binary.lastIndexOf(_type);
			
			StringBuilder newString = new StringBuilder(binary);
			newString.setCharAt(lastReg, opcodeBinary.charAt(indexToReplace--));
			binary = newString.toString();
		} //for
		
		return binary;
	}
}
