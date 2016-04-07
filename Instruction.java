import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author MI ONIM
 *
 */
public class Instruction
{
	protected Mnemonic _mnemonic;
	protected ArrayList<String> _operands;
	
	public Instruction(Mnemonic mnemonic, String operands) throws AssemblerException
	{
		_mnemonic = mnemonic;
		_operands = new ArrayList<String>(5);
		
		String[] parts = operands.split(Utils.WHITESPACE);
		for (String part : parts) {
			part = Utils.removeTrailingComma(part);
			if (!part.isEmpty()) {
				_operands.add(part);
			} //if
		} //for
		
		isValid();
	}
	
	public int numberOfOperands()
	{
		return _operands.size();
	}
	
	public String getBinary(HashMap<String, Integer> symbolTable) throws AssemblerException
	{
		String binary = _mnemonic.getOpcode();
		int arrayListIndex = 0;
		
		if (_mnemonic.operandDNeeded()) {
			binary = new Operand(_operands.get(arrayListIndex), 'd').replaceOpcode(binary);
			arrayListIndex++;
		} //if
		if (_mnemonic.operandRNeeded()) {
			binary = new Operand(_operands.get(arrayListIndex), 'r').replaceOpcode(binary);
			arrayListIndex++;
		} //if
		if (_mnemonic.operandKNeeded()) {
			binary = new OperandConstant(_operands.get(arrayListIndex), symbolTable).replaceOpcode(binary);
		} //if
		
		return binary;
	}
	
	public void isValid() throws AssemblerException
	{
		if (numberOfOperands() > 2) {
			throw new AssemblerException("Invalid number of operands");
		} //if
		if (numberOfOperands() != _mnemonic.countOperandsNeeded()) {
			throw new AssemblerException("Invalid number of operands");
		} //if
	}
}
