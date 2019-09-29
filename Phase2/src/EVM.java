import EVM.*;
import Utilities.*;
import Utilities.Error;
import Value.*;
import OperandStack.*;
import Instruction.*;
import java.util.*;
import java.io.*;

public class EVM {
    
    static private void binOp(int opcode, int type, OperandStack operandStack) {
	    Value o1, o2;

        o1 = operandStack.pop();
        o2 = operandStack.pop();

        // Check that the operands have the right type
        if (!(o1.getType() == type && o2.getType() == type))
            Error.error("Error: Type mismatch - operands do not match operator.");

        switch (opcode) {
        //double
        case RuntimeConstants.opc_dadd: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() + ((DoubleValue)o1).getValue())); break;
        case RuntimeConstants.opc_dsub: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() - ((DoubleValue)o1).getValue())); break;
        case RuntimeConstants.opc_ddiv: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() / ((DoubleValue)o1).getValue())); break;
        case RuntimeConstants.opc_dmul: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() * ((DoubleValue)o1).getValue())); break;
        case RuntimeConstants.opc_drem: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() % ((DoubleValue)o1).getValue())); break;
        //float
        case RuntimeConstants.opc_fadd: operandStack.push(new FloatValue(((FloatValue)o2).getValue() + ((FloatValue)o1).getValue())); break;
        case RuntimeConstants.opc_fsub: operandStack.push(new FloatValue(((FloatValue)o2).getValue() - ((FloatValue)o1).getValue())); break;
        case RuntimeConstants.opc_fdiv: operandStack.push(new FloatValue(((FloatValue)o2).getValue() / ((FloatValue)o1).getValue())); break;
        case RuntimeConstants.opc_fmul: operandStack.push(new FloatValue(((FloatValue)o2).getValue() * ((FloatValue)o1).getValue())); break;
        case RuntimeConstants.opc_frem: operandStack.push(new FloatValue(((FloatValue)o2).getValue() % ((FloatValue)o1).getValue())); break;
        //long
        case RuntimeConstants.opc_ladd: operandStack.push(new LongValue(((LongValue)o2).getValue() + ((LongValue)o1).getValue())); break;
        case RuntimeConstants.opc_lsub: operandStack.push(new LongValue(((LongValue)o2).getValue() - ((LongValue)o1).getValue())); break;
        case RuntimeConstants.opc_ldiv: operandStack.push(new LongValue(((LongValue)o2).getValue() / ((LongValue)o1).getValue())); break;
        case RuntimeConstants.opc_lmul: operandStack.push(new LongValue(((LongValue)o2).getValue() * ((LongValue)o1).getValue())); break;
        case RuntimeConstants.opc_lrem: operandStack.push(new LongValue(((LongValue)o2).getValue() % ((LongValue)o1).getValue())); break;
        //integer
        case RuntimeConstants.opc_iadd: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() + ((IntegerValue)o1).getValue())); break;
        case RuntimeConstants.opc_isub: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() - ((IntegerValue)o1).getValue())); break;
        case RuntimeConstants.opc_idiv: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() / ((IntegerValue)o1).getValue())); break;
        case RuntimeConstants.opc_imul: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() * ((IntegerValue)o1).getValue())); break;
        case RuntimeConstants.opc_irem: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() % ((IntegerValue)o1).getValue())); break;
        }
    }
    
    static private void swap(OperandStack operandStack) {
        Value o1, o2;
	
        o1 = operandStack.pop();
        o2 = operandStack.pop();
        
        if (o1.getType() == Value.s_double || o2.getType() == Value.s_double || o1.getType() == Value.s_long || o2.getType() == Value.s_long) {
            Error.error("Cannot swap doubles or floats.");
        }

        operandStack.push(o2);
        operandStack.push(o1);
    }
    
    static private void negate(int type, OperandStack operandStack) {
        Value o = operandStack.pop();

        if (o.getType() != type)
            Error.error("Error: Type mismatch - operands do not match operator.");
	
        switch (type)
        {
            case Value.s_integer: operandStack.push(new IntegerValue(-1*((IntegerValue)o).getValue())); break;
            case Value.s_long: operandStack.push(new LongValue(-1*((LongValue)o).getValue())); break;
            case Value.s_float: operandStack.push(new FloatValue(-1*((FloatValue)o).getValue())); break;
            case Value.s_double: operandStack.push(new DoubleValue(-1*((DoubleValue)o).getValue())); break;
        }
    }
    
    static private void cmp(int type, OperandStack operandStack) {

        Value o1, o2;
        o1 = operandStack.pop();
        o2 = operandStack.pop();
	
	    if (!(o1.getType() == type && o2.getType() == type))
            Error.error("Error: Type mismatch - operands do not match operator.");

        switch (type)
        {
            case Value.s_integer: 
                if (((IntegerValue)o2).getValue() == ((IntegerValue)o1).getValue())
                    operandStack.push(new IntegerValue(0));  
                else if (((IntegerValue)o2).getValue() > ((IntegerValue)o1).getValue())
                    operandStack.push(new IntegerValue(1));  
                else
                    operandStack.push(new IntegerValue(-1));  
                break;
            case Value.s_long: 
                if (((LongValue)o2).getValue() == ((LongValue)o1).getValue())
                    operandStack.push(new IntegerValue(0));  
                else if (((LongValue)o2).getValue() > ((LongValue)o1).getValue())
                    operandStack.push(new IntegerValue(1));  
                else
                    operandStack.push(new IntegerValue(-1));  
                break;
            case Value.s_float: 
                if (((FloatValue)o2).getValue() == ((FloatValue)o1).getValue())
                    operandStack.push(new IntegerValue(0));  
                else if (((FloatValue)o2).getValue() > ((FloatValue)o1).getValue())
                    operandStack.push(new IntegerValue(1));  
                else
                    operandStack.push(new IntegerValue(-1));  
                break;
            case Value.s_double: 
                if (((DoubleValue)o2).getValue() == ((DoubleValue)o1).getValue())
                    operandStack.push(new IntegerValue(0));  
                else if (((DoubleValue)o2).getValue() > ((DoubleValue)o1).getValue())
                    operandStack.push(new IntegerValue(1));  
                else
                    operandStack.push(new IntegerValue(-1));  
                break;
        }
    }
    
    static private void two(int from, int to, OperandStack operandStack) {
	
	    Value e = operandStack.pop();
        if (e.getType() != from)
            Error.error("OperandStack.two: Type mismatch.");
	
        switch (from) {
        case Value.s_integer:
            int iv = ((IntegerValue)e).getValue();
            switch (to) {
            case Value.s_byte:   operandStack.push(new IntegerValue((int)((byte) iv))); break;
            case Value.s_char:   operandStack.push(new IntegerValue((int)((char) iv))); break;
            case Value.s_short:  operandStack.push(new IntegerValue((int)((short)iv))); break;
            case Value.s_double: operandStack.push(new DoubleValue((double)iv)); break;	
            case Value.s_float:  operandStack.push(new FloatValue((float)iv)); break;	
            case Value.s_long:   operandStack.push(new LongValue((long)iv)); break;		
            }
            break;

        case Value.s_double:
            double dv = ((DoubleValue)e).getValue();
            switch (to) {
            case Value.s_integer: operandStack.push(new IntegerValue((int)dv)); break;	
            case Value.s_float:   operandStack.push(new FloatValue((float)dv)); break;	
            case Value.s_long:    operandStack.push(new LongValue((long)dv)); break;
            }
            break;

        case Value.s_float:
            float fv = ((FloatValue)e).getValue();
            switch (to) {
            case Value.s_integer: operandStack.push(new IntegerValue((int)fv)); break;	
            case Value.s_double:  operandStack.push(new DoubleValue((double)fv)); break;	
            case Value.s_long:    operandStack.push(new LongValue((long)fv)); break;
            }
            break;

        case Value.s_long:
            long lv = ((LongValue)e).getValue();
            switch (to) {
            case Value.s_integer: operandStack.push(new IntegerValue((int)lv)); break;	
            case Value.s_double:  operandStack.push(new DoubleValue((double)lv)); break;	
            case Value.s_float:   operandStack.push(new FloatValue((float)lv)); break;
            }
            break;
        }
    }
    
    static private void dup(int opCode, OperandStack operandStack) {
	// In real JVM a Double or a Long take up 2 stack words, but EVM Doubles and Longs
        // do not, so since dup2 can be used to either duplicate 2 single word values or
        // 1 double word value, we need to check the type of what is on the stack before
        // we decide if we should duplicate just one value or two.
        switch (opCode) {
        case RuntimeConstants.opc_dup:   operandStack.push(operandStack.peek(1)); break;
        case RuntimeConstants.opc_dup2: {
            Value o1 = operandStack.peek(1);
            Value o2;
            if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue))
                operandStack.push(o1);
            else {
                o2 = operandStack.peek(2);
                operandStack.push(o2);
                operandStack.push(o1);
            }
        }
            break;
        case RuntimeConstants.opc_dup_x1: {
            Value o1 = operandStack.pop();
            Value o2 = operandStack.pop();
            if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue) ||
                (o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                Error.error("Error: dup_x1 cannot be used on value of type Double or Long.");
            operandStack.push(o1);
            operandStack.push(o2);
            operandStack.push(o1);
        }
            break;
        case RuntimeConstants.opc_dup_x2: {
            Value o1 = operandStack.pop();
            Value o2 = operandStack.pop();
            if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue))
                Error.error("Error: dup_x2 cannot be used on value of type Double or Long.");
            if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue)) {
                operandStack.push(o1);
                operandStack.push(o2);
                operandStack.push(o1);
            } else {
                Value o3 = operandStack.pop();
                if ((o3 instanceof DoubleValue) || (o3 instanceof LongValue))
                    Error.error("Error: word3 of dup_x2 cannot be  of type Double or Long.");
                operandStack.push(o1);
                operandStack.push(o3);
                operandStack.push(o2);
                operandStack.push(o1);
            }
        }
            break;
        case RuntimeConstants.opc_dup2_x1: {
            Value o1 = operandStack.pop();
            if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue)) {
                Value o2 = operandStack.pop();
                if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                    Error.error("Error: word3 of dup2_x1 cannot be of type Double or Long.");
                operandStack.push(o1);
                operandStack.push(o2);
                operandStack.push(o1);
            } else {
                Value o2 = operandStack.pop();
                if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                    Error.error("Error: word2 of dup2_x1 cannot be of type Double or Long when word1 is not.");
                Value o3 = operandStack.pop();
                if ((o3 instanceof DoubleValue) || (o3 instanceof LongValue))
                    Error.error("Error: word3 of dup2_x1 cannot be of type Double or Long.");
                operandStack.push(o2);
                operandStack.push(o1);
                operandStack.push(o3);
                operandStack.push(o2);
                operandStack.push(o1);
            }
        }
            break;
        case RuntimeConstants.opc_dup2_x2: {
            Value o1 = operandStack.pop();
            if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue)) {
                Value o2 = operandStack.pop();
                if (!((o2 instanceof DoubleValue) || (o2 instanceof LongValue)))
                    Error.error("Error: word3 of dup2_x2 must be of type Double or Long.");
                operandStack.push(o1);
                operandStack.push(o2);
                operandStack.push(o1);
            } else {
                Value o2 = operandStack.pop();
                if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                    Error.error("Error: word2 of dup2_x2 cannot be of type Double or Long when word1 is not.");
                Value o3 = operandStack.pop();
                if (!((o3 instanceof DoubleValue) || (o3 instanceof LongValue)))
                    Error.error("Error: word3/4 of dup2_x2 must be of type Double or Long.");
                operandStack.push(o2);
                operandStack.push(o1);
                operandStack.push(o3);
                operandStack.push(o2);
                operandStack.push(o1);
            }
        }
            break;
        }
    }
    
    
    static private void logic(int inst, OperandStack operandStack) {
        Value o1, o2;
        o1 = operandStack.pop();
        o2 = operandStack.pop();

        switch (inst) {
        case RuntimeConstants.opc_iand:
            operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() & ((IntegerValue)o1).getValue()));
            break;
        
        case RuntimeConstants.opc_ior:
            operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() | ((IntegerValue)o1).getValue()));
            break;

        case RuntimeConstants.opc_ixor:
            operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() ^ ((IntegerValue)o1).getValue()));
            break;

        case RuntimeConstants.opc_land:
            operandStack.push(new LongValue(((LongValue)o2).getValue() & ((LongValue)o1).getValue()));
            break;

        case RuntimeConstants.opc_lor:
            operandStack.push(new LongValue(((LongValue)o2).getValue() | ((LongValue)o1).getValue()));
            break;

        case RuntimeConstants.opc_lxor:
            operandStack.push(new LongValue(((LongValue)o2).getValue() ^ ((LongValue)o1).getValue()));
            break;
        }
    }
    
    static private void shift(int opCode, OperandStack operandStack) {
        Value o1, o2;
        o1 = operandStack.pop();
        o2 = operandStack.pop();
    
        switch (opCode) {
            case RuntimeConstants.opc_ishl:
                operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() << (31&((IntegerValue)o1).getValue())));
                break;

            case RuntimeConstants.opc_ishr:
                operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() >> (31&((IntegerValue)o1).getValue())));
                break;

            case RuntimeConstants.opc_iushr:
            operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() >>> (31&((IntegerValue)o1).getValue())));
                break;

            case RuntimeConstants.opc_lshl:
                operandStack.push(new LongValue(((LongValue)o2).getValue() << (63&((IntegerValue)o1).getValue())));
                break;

            case RuntimeConstants.opc_lshr:
                operandStack.push(new LongValue(((LongValue)o2).getValue() >> (63&((IntegerValue)o1).getValue())));
                break;

            case RuntimeConstants.opc_lushr:
                operandStack.push(new LongValue(((LongValue)o2).getValue() >>> (63&((IntegerValue)o1).getValue())));
                break;
        }
    }

    public static void main(String argv[]) {
	OperandStack operandStack = new OperandStack(100, "Phase 2");
	Value v1, v2;
	IntegerValue v3, v4;
	
	operandStack.push(new IntegerValue(100));

	v1 = Value.makeValue((double)5.7);
	v2 = new DoubleValue(6);
	operandStack.push(v1);
	operandStack.push(v2);

	System.out.println(operandStack);

	binOp(RuntimeConstants.opc_dadd, Value.s_double, operandStack);
    System.out.println(operandStack.pop()); // ==> 11.7
    
    ///// QUESTION 3

    System.out.println("///// binOP Test /////");

    v1 = new IntegerValue(13);
    v2 = new IntegerValue(8);
    operandStack.push(v1);
    operandStack.push(v2);

    // multiply integer values
    binOp(RuntimeConstants.opc_imul, Value.s_integer, operandStack);
    //Resultof8*13
    System.out.println("Integer Result of 8 * 13 = " + operandStack.pop());

    v1 = new LongValue(23);
    v2 = new LongValue(7);
    operandStack.push(v1);
    operandStack.push(v2);

    // divide the two long values
    binOp(RuntimeConstants.opc_ldiv, Value.s_long, operandStack);
    //Resultof23/7
    System.out.println("Long Result of 23 / 7 = " + operandStack.pop());

    v1 = new FloatValue(34.5f);
    v2 = new FloatValue(44.1f);
    operandStack.push(v1);
    operandStack.push(v2);

    //add float values
    binOp(RuntimeConstants.opc_fadd, Value.s_float, operandStack); 
    // Result of 34.5f + 44.1f
    System.out.println("Float Result of 34.5f + 44.1f = " + operandStack.pop());

    ////////////////////////////// QUESTION 4 ////////////////////////////////
    System.out.println("///// Swap Test /////");
    System.out.println("Integer Values: Value 1 = 45 Value 2 = 78"); v1 = new IntegerValue(45);
    v2 = new IntegerValue(78);
    operandStack.push(v1);
    operandStack.push(v2);
    // ======================= IntegerValues in Stack
    //  78
    //  45
    // +----+
    swap(operandStack);
    // pop() and print swaped IntegerValues in stack
    System.out.println("The values before swap are 45 78"); System.out.println("The values after swapping are " + operandStack.pop() + " " + operandStack.pop());
    v1 = new FloatValue(66.6f); v2 = new FloatValue(82.8f); operandStack.push(v1); operandStack.push(v2);
    // ======================== FloatValues in stack
    //  82.8
    //  66.6
    // +------+
    System.out.println("Float Values: Value 1 = 66.6 Value 2 = 82.8");
    swap(operandStack);
    // pop() and print swapped FloatValues in stack
    System.out.println("The values before swap are 66.6 82.8"); System.out.println("The values after swapping are " + operandStack.pop() + " " + operandStack.pop());

    ////////////////////////////// QUESTION 5 ////////////////////////////////
    System.out.println("///// Negate Test /////"); 

    v1 = new DoubleValue(4.7);
    operandStack.push(v1);

    // negate the DoubleValue 4.7
    negate(Value.s_double, operandStack);
    // Print negated DoubleValue
    System.out.println("DoubleValue 4.7 negated: " + operandStack.pop());

    v1 = new IntegerValue(45);
    operandStack.push(v1);

    // negate the IntegerValue 45
    negate(Value.s_integer, operandStack);
    // Print negated IntegerValue
    System.out.println("IntegerValue 45 negated: " + operandStack.pop());

    v1 = new FloatValue(66.6f);
    operandStack.push(v1);

    // negate the FloatValue 66.6f
    negate(Value.s_float, operandStack);
    // Print negated FloatValue
    System.out.println("FloatValue 66.6f negated: " + operandStack.pop());

    v1 = new LongValue(661);
    operandStack.push(v1);

    // negate the LongValue 661
    negate(Value.s_long, operandStack);
    // Print negated LongValue
    System.out.println("LongValue 661 negated: " + operandStack.pop());

    ////////////////////////////// QUESTION 6 ////////////////////////////////

    Value a;
    IntegerValue va;
        
    System.out.println("///// CMP TEST /////");
    // ========================================= LongValue
    operandStack.push(new LongValue(100));
    operandStack.push(new LongValue(50));
        
    cmp(Value.s_long, operandStack);

    a = operandStack.pop();
    va = (IntegerValue) a;

    System.out.println("Long Values: Value 1 = 100, Value 2 = 50");
    switch (va.getValue()) {
    case 0:
    System.out.println("Equal values.");
    break;
    case 1:
    System.out.println("Value 1 is greater than Value 2.");
    break;
    case -1:
    System.out.println("Value 1 is less than Value 2.");
    break;
    default:
    break;
    }

    // ========================================= DoubleValue
    operandStack.push(new DoubleValue(50.45));
    operandStack.push(new DoubleValue(100.45));

    cmp(Value.s_double, operandStack);
    a = operandStack.pop();
    va = (IntegerValue) a;

    System.out.println("Double Values: Value 1 = 50.45, Value 2 = 100.45");
    switch (va.getValue()) {
    case 0:
    System.out.println("Equal values.");
    break;
    case 1:
    System.out.println("Value 1 is greater than Value 2.");
    break;
    case -1:
    System.out.println("Value 1 is less than Value 2.");
    break;
    default:
    break;
    }

    // ========================================= FloatValue
    operandStack.push(new FloatValue(10.56f));
    operandStack.push(new FloatValue(10.56f));


    cmp(Value.s_float, operandStack);
    a = operandStack.pop();
    va = (IntegerValue) a;

    System.out.println("Float Values: Value 1 = 10.56f, Value 2 = 10.56f");
    switch (va.getValue()) {
    case 0:
    System.out.println("Equal values.");
    break;
    case 1:
    System.out.println("Value 1 is greater than Value 2.");
    break;
    case -1:
    System.out.println("Value 1 is less than Value 2.");
    break;
    default:
    break;
    }
    ////////////////////////////// QUESTION 7 ////////////////////////////////

    Value a7;
    IntegerValue ai;
    FloatValue af;
    DoubleValue ad;

    System.out.println("///// TWO TEST /////");

    // ========================================= IntegerValue
    operandStack.push(new IntegerValue(40));

    two(Value.s_integer, Value.s_float, operandStack);
    a7 = operandStack.pop();
    af = (FloatValue) a7;

    System.out.println("IntegerValue = 40 to FloatValue = " + af.getValue());
    // ========================================= FloatValue

    operandStack.push(new FloatValue(af.getValue()));

    two(Value.s_float, Value.s_double, operandStack);
    a7 = operandStack.pop();
    ad = (DoubleValue) a7;

    
    System.out.println("FloatValue = 40.0f to DoubleValue = " +
    ad.getValue());
    // ========================================= DoubleValue
    operandStack.push(new DoubleValue(ad.getValue()));
    two(Value.s_double, Value.s_integer, operandStack);
    a7 = operandStack.pop();
    ai = (IntegerValue) a7;
    System.out.println("DoubleValue = 40.0 to IntegerValue = " + ai.getValue());

    ////////////////////////////// QUESTION 8 ////////////////////////////////
    Value va8;
    IntegerValue a8;
    System.out.println("///// LOGIC TEST /////");
    // ========================================= IntegerValue
    operandStack.push(new IntegerValue(60)); operandStack.push(new IntegerValue(13));
    logic(RuntimeConstants.opc_iand, operandStack);
    va8 = operandStack.pop();
    System.out.println("IntegerValue: 60 & 13 = " + ((IntegerValue)va8).getValue());
    operandStack.push(new IntegerValue(60)); operandStack.push(new IntegerValue(13));
    logic(RuntimeConstants.opc_ior, operandStack);
    va8 = operandStack.pop();
    System.out.println("IntegerValue: 60 | 13 = " + ((IntegerValue)va8).getValue());
    operandStack.push(new IntegerValue(60)); operandStack.push(new IntegerValue(13));
    logic(RuntimeConstants.opc_ixor, operandStack);
    va8 = operandStack.pop();
    System.out.println("IntegerValue: 60 ˆ 13 = " + ((IntegerValue)va8).getValue());
    // ========================================= LongValue
    operandStack.push(new LongValue(60)); operandStack.push(new LongValue(13));
    logic(RuntimeConstants.opc_land, operandStack);
    va8 = operandStack.pop();
    System.out.println("LongValue: 60 & 13 = " + ((LongValue)va8).getValue());
    operandStack.push(new LongValue(60)); operandStack.push(new LongValue(13));
    logic(RuntimeConstants.opc_lor, operandStack);
    va8 = operandStack.pop();
    System.out.println("LongValue: 60 | 13 = " + ((LongValue)va8).getValue());
    operandStack.push(new LongValue(60)); operandStack.push(new LongValue(13));
    logic(RuntimeConstants.opc_lxor, operandStack);
    va8 = operandStack.pop();
    System.out.println("LongValue: 60 ˆ 13 = " + ((LongValue)va8).getValue());
    ////////////////////////////// QUESTION 9 ////////////////////////////////
    System.out.println("///// SHIFT TEST /////");
    // ========================================= IntegerValue
    operandStack.push(new IntegerValue(60)); operandStack.push(new IntegerValue(13));
    shift(RuntimeConstants.opc_ishl, operandStack);
    va8 = operandStack.pop();
    System.out.println("IntegerValue: 60 << 13 = " + ((IntegerValue)va8).getValue());
    operandStack.push(new IntegerValue(60)); operandStack.push(new IntegerValue(13));
    shift(RuntimeConstants.opc_ishr, operandStack);
    va8 = operandStack.pop();
    System.out.println("IntegerValue: 60 >> 13 = " + ((IntegerValue)va8).getValue());
    operandStack.push(new IntegerValue(60)); operandStack.push(new IntegerValue(13));
    shift(RuntimeConstants.opc_iushr, operandStack);
    va8 = operandStack.pop();
    System.out.println("IntegerValue: 60 >>> 13 = " + ((IntegerValue)va8).getValue());
    // ========================================= LongValue
    operandStack.push(new LongValue(60)); operandStack.push(new IntegerValue(13));
    shift(RuntimeConstants.opc_lshl, operandStack);
    va8 = operandStack.pop();
    System.out.println("LongValue: 60 << 13 = " + ((LongValue)va8).getValue());
    operandStack.push(new LongValue(60)); operandStack.push(new IntegerValue(13));
    shift(RuntimeConstants.opc_lshr, operandStack);
    va8 = operandStack.pop();
    System.out.println("LongValue: 60 >> 13 = " + ((LongValue)va8).getValue());
    operandStack.push(new LongValue(60)); operandStack.push(new IntegerValue(13));
    shift(RuntimeConstants.opc_lushr, operandStack);
    va8 = operandStack.pop();
    System.out.println("LongValue: 60 >>> 13 = " + ((LongValue)va8).getValue());
    }
}
