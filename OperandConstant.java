import java.util.HashMap;

/**
 * 
 * @author MI ONIM
 *
 */
public class OperandConstant extends Operand
{
	/* An Operand constant can be either a label or a constant hexadecimal number. */
	protected boolean _isLabel;
	
	public OperandConstant(String operand, HashMap<String, Integer> symbolTable) throws AssemblerException
	{
		super(operand, 'k');
		
		if (_isLabel) {
			if (!symbolTable.containsKey(operand)) {
				throw new AssemblerException("Undefined label");
			} //if
			_operand = String.valueOf(symbolTable.get(operand));
		} //if
	}
	
	@Override
	public void setOperand(String operand) throws AssemblerException
	{
		if (operand.startsWith("$")) {
			_isLabel= false;
			_operand = operand.substring(1);   //remove '$' from the beginning
			_operand = String.valueOf(intValue(16));  //store as int value of the hex number
		} //if
		else {
			_isLabel = true;
			_operand = operand;  //no need to remove any character from the beginning
		} //else
	}
}
