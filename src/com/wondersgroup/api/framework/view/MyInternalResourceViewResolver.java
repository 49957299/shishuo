package com.wondersgroup.api.framework.view;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class MyInternalResourceViewResolver extends InternalResourceViewResolver{

	@Override
	public void setAlwaysInclude(boolean alwaysInclude) {
		// TODO Auto-generated method stub
		super.setAlwaysInclude(alwaysInclude);
	}

	@Override
	public void setExposeContextBeansAsAttributes(
			boolean exposeContextBeansAsAttributes) {
		// TODO Auto-generated method stub
		super.setExposeContextBeansAsAttributes(exposeContextBeansAsAttributes);
	}

	@Override
	public void setExposedContextBeanNames(String... exposedContextBeanNames) {
		// TODO Auto-generated method stub
		super.setExposedContextBeanNames(exposedContextBeanNames);
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		return super.buildView(viewName);
	}

}
