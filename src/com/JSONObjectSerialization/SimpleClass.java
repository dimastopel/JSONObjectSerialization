package com.JSONObjectSerialization;

public class SimpleClass 
{
    /**
     * @return the doubleVal
     */
    public double getDoubleVal() {
        return _doubleVal;
    }
    /**
     * @param doubleVal the doubleVal to set
     */
    public void setDoubleVal(double doubleVal) {
        this._doubleVal = doubleVal;
    }
    /**
     * @return the intVal
     */
    public int getIntVal() {
        return _intVal;
    }
    /**
     * @param intVal the intVal to set
     */
    public void setIntVal(int intVal) {
        this._intVal = intVal;
    }
    /**
     * @return the boolValue
     */
    public boolean isBoolValue() {
        return _boolVal;
    }
    /**
     * @param boolValue the boolValue to set
     */
    public void setBoolValue(boolean boolValue) {
        this._boolVal = boolValue;
    }
    /**
     * @return the stringVal
     */
    public String getStringVal() {
        return _stringVal;
    }
    /**
     * @param stringVal the stringVal to set
     */
    public void setStringVal(String stringVal) {
        this._stringVal = stringVal;
    }
    
    public SimpleClass(double doubleVal, int intVal, boolean boolValue,
            String stringVal) {
        super();
        this._doubleVal = doubleVal;
        this._intVal = intVal;
        this._boolVal = boolValue;
        this._stringVal = stringVal;
    }

    public SimpleClass() {
        super();
        this._doubleVal = 0.0;
        this._intVal = 0;
        this._boolVal = false;
        this._stringVal = "";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SimpleClass [doubleVal=" + _doubleVal + ", intVal=" + _intVal
                + ", boolValue=" + _boolVal + ", stringVal=" + _stringVal + "]";
    }

    private double _doubleVal = 0;
    private int _intVal = 0;
    private boolean _boolVal = false;
    private String _stringVal = "";

}
