package org.eclipse.equinox.ds.tests.tb23;

import java.util.Dictionary;

import org.eclipse.equinox.ds.tests.tbc.ComponentContextProvider;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

public class BindRegistrator implements ComponentContextProvider {
	private Dictionary properties;
	private ComponentContext ctxt;
	private static final int BIND = 1 << 0;
	private static final int UNBIND = 1 << 1;
	private static final int ACTIVATE = 1 << 2;
	private static final int DEACTIVATE = 1 << 3;

	protected void activate(ComponentContext ctxt) {
		this.ctxt = ctxt;
		properties = ctxt.getProperties();
		setDataBits(ACTIVATE);
	}

	protected void deactivate(ComponentContext ctxt) {
		setDataBits(DEACTIVATE);
	}

	protected void bind(ServiceReference sr) {
		setDataBits(BIND);
		throw new RuntimeException("Test method throwException(ComponentContext) is called!");
	}
	
	protected void bind_ex(ServiceReference sr) {
    throw new RuntimeException("Test method bind_ex(ServiceReference) is called!");
  }

	protected void unbind(ServiceReference sr) {
    setDataBits(UNBIND);
  }

	public Dictionary getProperties() {
		return properties;
	}

	private void setDataBits(int value) {
		if (properties == null) {
			return;
		}
		Object prop = properties.get("config.base.data");
		int data = (prop instanceof Integer) ? ((Integer) prop).intValue() : 0;
		properties.put("config.base.data", new Integer(data | value));
	}

	public ComponentContext getComponentContext() {
		return ctxt;
	}
}