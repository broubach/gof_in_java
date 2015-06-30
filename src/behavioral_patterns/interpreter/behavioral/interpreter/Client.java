package behavioral.interpreter;

import java.util.HashMap;
import java.util.Map;

class Context {
	public Map<String, Boolean> variables = new HashMap<String, Boolean>();

	public Context() {
		variables.put(Boolean.TRUE.toString(), Boolean.TRUE);
		variables.put(Boolean.FALSE.toString(), Boolean.FALSE);
	}

	public boolean lookup(String name) {
		return variables.get(name);
	}

	public void assign(VariableExpression variable, boolean value) {
		variables.put(variable.getName(), value);
	}
}

abstract class BooleanExpression {
	public static VariableExpression TRUE = new VariableExpression("true");
	public static VariableExpression FALSE = new VariableExpression("false");

	public abstract boolean evaluate(Context context);
	public abstract BooleanExpression replace(String name, BooleanExpression expression);
	public abstract BooleanExpression copy();
}

class VariableExpression extends BooleanExpression {
	private String name;
	
	public VariableExpression(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean evaluate(Context context) {
		return context.lookup(name);
	}

	@Override
	public BooleanExpression copy() {
		return new VariableExpression(name);
	}

	@Override
	public BooleanExpression replace(String name, BooleanExpression expression) {
		if (this.name.equals(name)) {
			return expression.copy();
		}
		return new VariableExpression(name);
	}
}

class AndExpression extends BooleanExpression {
	
	private BooleanExpression operand1;
	private BooleanExpression operand2;

	public AndExpression(BooleanExpression exp1, BooleanExpression exp2) {
		this.operand1 = exp1;
		this.operand2 = exp2;
	}

	@Override
	public boolean evaluate(Context context) {
		return operand1.evaluate(context) && operand2.evaluate(context);
	}

	@Override
	public BooleanExpression replace(String name, BooleanExpression expression) {
		return new AndExpression(operand1.replace(name, expression), operand2.replace(name, expression));
	}

	@Override
	public BooleanExpression copy() {
		return new AndExpression(operand1.copy(), operand2.copy());
	}
}

class OrExpression extends BooleanExpression {
	
	private BooleanExpression operand1;
	private BooleanExpression operand2;

	public OrExpression(BooleanExpression exp1, BooleanExpression exp2) {
		this.operand1 = exp1;
		this.operand2 = exp2;
	}

	@Override
	public boolean evaluate(Context context) {
		return operand1.evaluate(context) || operand2.evaluate(context);
	}

	@Override
	public BooleanExpression replace(String name, BooleanExpression expression) {
		return new AndExpression(operand1.replace(name, expression), operand2.replace(name, expression));
	}

	@Override
	public BooleanExpression copy() {
		return new AndExpression(operand1.copy(), operand2.copy());
	}
}

class NotExpression extends BooleanExpression {
	
	private BooleanExpression operand1;

	public NotExpression(BooleanExpression exp1) {
		this.operand1 = exp1;
	}

	@Override
	public boolean evaluate(Context context) {
		return !operand1.evaluate(context);
	}

	@Override
	public BooleanExpression replace(String name, BooleanExpression expression) {
		return new NotExpression(operand1.replace(name, expression));
	}

	@Override
	public BooleanExpression copy() {
		return new NotExpression(operand1.copy());
	}
}

// an abstract syntax tree (AST) is a representation of a grammar
public class Client {

	public static void main(String[] args) {
		VariableExpression x = new VariableExpression("X");
		VariableExpression y = new VariableExpression("Y");
		VariableExpression z = new VariableExpression("Z");
		NotExpression notZ = new NotExpression(z);

		Context context = new Context();
		context.assign(x, false);
		context.assign(y, true);
		context.assign(z, true);

		BooleanExpression expression = new OrExpression(new AndExpression(BooleanExpression.TRUE, x),
				new AndExpression(y, new NotExpression(x)));
		System.out.println("Result of evaluation: " + expression.evaluate(context));
		
		BooleanExpression replacement = expression.replace("Y", notZ);
		System.out.println("Result of replacement evaluation: " + replacement.evaluate(context));
		
	}
}
