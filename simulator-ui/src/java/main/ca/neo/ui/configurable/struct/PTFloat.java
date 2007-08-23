package ca.neo.ui.configurable.struct;

import ca.neo.ui.configurable.PropertyInputPanel;
import ca.neo.ui.configurable.inputPanels.FloatInputPanel;

public class PTFloat extends PropDescriptor {

	private static final long serialVersionUID = 1L;

	public PTFloat(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PropertyInputPanel createInputPanel() {
		// TODO Auto-generated method stub
		return new FloatInputPanel(this);
	}

	@Override
	public Class<Float> getTypeClass() {
		return Float.class;
	}

	@Override
	public String getTypeName() {
		return "Float";
	}

}

