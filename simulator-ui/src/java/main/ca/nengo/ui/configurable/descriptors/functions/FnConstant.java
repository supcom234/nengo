/*
The contents of this file are subject to the Mozilla Public License Version 1.1
(the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS" basis, WITHOUT
WARRANTY OF ANY KIND, either express or implied. See the License for the specific
language governing rights and limitations under the License.

The Original Code is "FnConstant.java". Description:
""

The Initial Developer of the Original Code is Bryan Tripp & Centre for Theoretical Neuroscience, University of Waterloo. Copyright (C) 2006-2008. All Rights Reserved.

Alternatively, the contents of this file may be used under the terms of the GNU
Public License license (the GPL License), in which case the provisions of GPL
License are applicable  instead of those above. If you wish to allow use of your
version of this file only under the terms of the GPL License and not to allow
others to use your version of this file under the MPL, indicate your decision
by deleting the provisions above and replace  them with the notice and other
provisions required by the GPL License.  If you do not delete the provisions above,
a recipient may use your version of this file under either the MPL or the GPL License.
 */

package ca.nengo.ui.configurable.descriptors.functions;

import java.util.Map;

import ca.nengo.math.Function;
import ca.nengo.math.impl.ConstantFunction;
import ca.nengo.ui.configurable.ConfigException;
import ca.nengo.ui.configurable.Property;
import ca.nengo.ui.configurable.descriptors.PFloat;
import ca.nengo.ui.configurable.descriptors.PInt;

/**
 * A constant function
 * 
 * @author Shu Wu
 */
public class FnConstant extends AbstractFn {

    private final PInt pDimension;
    private final PFloat pValue = new PFloat(
    		"Value", "Constant value for the function", 0);

    /**
     * @param dimension Dimensionality of the constant function
     */
    public FnConstant(int dimension) {
        super(ConstantFunction.class);
        pDimension = new PInt("Dimensionality",
        		"Number of dimensions that will be output", dimension);
        pDimension.setEditable(false);
    }

    @Override protected Function createFunction(Map<Property, Object> props) throws ConfigException {
        return new ConstantFunction((Integer) props.get(pDimension),
        		(Float) props.get(pValue));
    }

    public Property[] getSchema() {
        if (getFunction() != null) {
            if (pDimension.isEditable()) {
                pDimension.setDefaultValue(getFunction().getDimension());
            }
            pValue.setDefaultValue(getFunction().getValue());
        }

        return new Property[] { pDimension, pValue };
    }

    @Override public ConstantFunction getFunction() {
        return (ConstantFunction) super.getFunction();
    }

}
